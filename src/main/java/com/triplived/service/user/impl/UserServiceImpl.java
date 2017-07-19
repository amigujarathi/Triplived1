package com.triplived.service.user.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.connectme.domain.triplived.UserResponse;
import com.connectme.domain.triplived.badge.Badge;
import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.NotificationBarDTO;
import com.connectme.domain.triplived.dto.NotificationBarParentDTO;
import com.connectme.domain.triplived.dto.PersonDTO;
import com.connectme.domain.triplived.dto.ProfileDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.connectme.domain.triplived.event.TlEvent;
import com.domain.triplived.social.fbcover.FaceBookCoverPageResponse;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.gcm.ClientMessageType;
import com.google.gson.Gson;
import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripMediaDAO;
import com.triplived.dao.user.UserDao;
import com.triplived.entity.PersonDb;
import com.triplived.entity.TripMetaData;
import com.triplived.entity.UserAccountsDb;
import com.triplived.entity.UserAdditionalInfoDb;
import com.triplived.entity.UserFollowerDb;
import com.triplived.rest.client.StaticContent;
import com.triplived.rest.trip.TripClient;
import com.triplived.service.AppUtils;
import com.triplived.service.badge.BadgeService;
import com.triplived.service.notification.GCMNotificationService;
import com.triplived.service.profile.PersonProfile;
import com.triplived.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class );
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TripDAO tripDao;
	
	@Autowired
	private TripMediaDAO tripMediaDao;
	
	@Autowired
	private BadgeService badgesService;
	
	@Autowired
	private StaticContent staticContent;
	
	@Autowired
	private TripClient tripClient;
	
	@Autowired
	private GCMNotificationService gcmService;
	
	
	@Value("${facebookProfileUrl}")
	private String facebookProfileUrl;
	
	@Value("${defaultTriplivedUserPhoto}")
	private String defaultTriplivedUserPhoto;
	
	@Value("${defaultcoverpage}")
	private String defaultcoverpage;
	
	@Value("${facebokcoverpageUrl}")
	private String facebokcoverpageUrl;
	
	@Value("${facebookaccesstoken}")
	private String  facebookaccesstoken;
	
	@Autowired
	private AppUtils apputils;
	
	@Autowired 
	private Environment environment;
	
	
	@Override
	@Transactional(readOnly=true)
	public UserFollowerDb getFollowerDetails(Long fromId, Long toId) {
		UserFollowerDb obj = userDao.getFollowerDetails(fromId, toId);
		return obj;
	}
	
	/*@Override
	@Transactional
	public void followOrUnfollowPerson(UserFollowerDb obj){
		userDao.followOrUnfollowPerson(obj);
	}
	*/
	@Override
	@Transactional
	public void updateFollowerDetails(Long fromId, Long toId, Boolean status){
		UserFollowerDb obj = getFollowerDetails(fromId, toId);
		if(null == obj) {
			obj = new UserFollowerDb();
			obj.setUserFollowId(toId);
			obj.setUserFollowingId(fromId);
			obj.setStatus("A");
			obj.setUpdateDate(new Date());
			userDao.followOrUnfollowPerson(obj);
		}else {
			if(null != status) {
				if(status) {
					obj.setStatus("A");
				}else {
					obj.setStatus("I");
				}
				obj.setUpdateDate(new Date());
				userDao.followOrUnfollowPerson(obj);
			}
		}
		 
		if(status){
			try {
				gcmService.sendUserFollowNotification(fromId.toString(), toId.toString());
			} catch (IOException e) {
				logger.error("error is ", e);
				logger.error("Problem occured while sending follow unfollow notification from {}  to {} ", fromId, toId);
			}
		}
		
		fireFollowFollowerEvent(fromId, toId, status);
		
	}
	
	private void fireFollowFollowerEvent(Long fromId, Long toId, boolean status) {
		TlEvent event =  new TlEvent();
		event.setUser(fromId);
		event.setVerb(TripMetaData.TRIP_SUBMITTED.toString());
		event.setActionDetails(toId+"");
		event.setBody(new Object());
		if(status){
			event.addToHeader(TlEvent.TYPE, TripMetaData.FOLLOWING);
		}else{
			event.addToHeader(TlEvent.TYPE, TripMetaData.UNFOLLOWED);
		}
		apputils.getHandler("userProfileHandler").setState(event);
	}

	@Override
	public ProfileDTO loadUserProfile(String personId){
		ProfileDTO profile = new ProfileDTO();
		PersonDb personDb = userDao.getPersonByUserId(personId);
		profile.setPerson(convertPersonObj(personDb));
		
		List<Object[]>  followersOfAPerson = userDao.getFollowersOfAPerson(Long.parseLong(personId));
		profile.setFollowers(convertPersonFollowerObj(followersOfAPerson));
		List<Object[]>  followedByAPerson = userDao.getListOfPeopleFollowedByPerson(Long.parseLong(personId));
		profile.setFollowing(convertPersonFollowerObj(followedByAPerson));
		
		List<Object[]> userTrips = tripDao.getTripsOfUser(Long.parseLong(personId));
		profile.setTrips(convertTrips(userTrips));
		
		List<Badge> badges = badgesService.getUserBadges(Long.parseLong(personId)) ;
		profile.setBadges(badges);
		
		return profile;
	}
	
	@Override
	public ProfileDTO loadUserProfileForWeb(String personId){
		ProfileDTO profile = new ProfileDTO();
		PersonDb personDb = userDao.getPersonByUserId(personId);
		profile.setPerson(convertPersonObj(personDb));
		
		List<Object[]>  followersOfAPerson = userDao.getFollowersOfAPerson(Long.parseLong(personId));
		profile.setFollowers(convertPersonFollowerObj(followersOfAPerson));
		List<Object[]>  followedByAPerson = userDao.getListOfPeopleFollowedByPerson(Long.parseLong(personId));
		profile.setFollowing(convertPersonFollowerObj(followedByAPerson));
		
		List<Object[]> userTrips = tripDao.getTripsOfUserForWeb(Long.parseLong(personId));
		profile.setTrips(convertTrips(userTrips));
		
		List<Badge> badges = badgesService.getUserBadges(Long.parseLong(personId)) ;
		profile.setBadges(badges);
		
		return profile;
	}
	
	@Override
	public PersonDb loadUserDetails(String personId) {
		PersonDb personDb = userDao.getPersonByUserId(personId);
		return personDb;
	}
	
	@Override
	@Transactional
	public PersonDTO getUserDetails(String personId) {
		PersonDb personDb = userDao.getPersonByUserId(personId);
		PersonDTO person = new PersonDTO();
		String name = null;
		
		if(null != personDb) {
			if(null != personDb.getName()) {
				name = personDb.getName();
				if(null != personDb.getLastName()) {
					name = personDb.getName() + " " + personDb.getLastName();
                }
				person.setName(name);
			}
			if(!CollectionUtils.isEmpty(personDb.getUserAccounts())) {
				Set<UserAccountsDb> accSet = personDb.getUserAccounts();
				for (UserAccountsDb obj : accSet) {
					if(null != obj) {
						if(null != obj.getEmail()) {
							person.setEmail(personDb.getEmail());
							break;
						}
					}
				}
				
			}
		}
		
		return person;
	}
	
	@Override
	public List<TripSearchDTO> convertTrips(List<Object[]> trips) {
		Gson gson = new Gson();
		List<TripSearchDTO> tripList = new ArrayList<TripSearchDTO>();
		for(Object[] oArr : trips) {
			TripSearchDTO trip = new TripSearchDTO();
			
			//Ignore trips that are inactive state
			if(null != oArr[5]) {
				if(oArr[5].toString().equalsIgnoreCase("I")) {
					continue;
				}
			}
			
			if(null != oArr[0]) {
				trip.setOldTripId(oArr[0].toString());
				trip.setTripId(oArr[0].toString());
			}
			if(null != oArr[1]) {
				trip.setTripName(oArr[1].toString());
			}
			if(null != oArr[2]) {
				trip.setTripUri(oArr[2].toString());
			}if(null != oArr[3]) {
				
				TimelineTrip tripObj = gson.fromJson(oArr[3].toString(), TimelineTrip.class);
				List<com.domain.triplived.trip.dto.SubTrip> subTrips = tripObj.getSubTrips();
	    		List<CityResponseDTO> allStops = new ArrayList<CityResponseDTO>();
	    		for(com.domain.triplived.trip.dto.SubTrip s : subTrips) {
	    			if(null != s.getToCityDTO()) {
	    				CityResponseDTO city = new CityResponseDTO();
	    				city.setCityName(s.getToCityDTO().getCityName());
	    				city.setType(s.getToCityDTO().getCityType());
	    				allStops.add(city);
	    			}
	    		}
	    		trip.setTripCities(allStops);
			}
			try {
				TripSearchDTO obj = tripClient.getTripDetails(trip.getTripId());
				if(null != obj) {
					trip.setTripLikes(obj.getTripLikes());
					trip.setTripComments(obj.getTripComments());
					trip.setTripImages(obj.getTripImages());
					trip.setCategories(obj.getCategories());
				}
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Adding public id
			if(null != oArr[4]) {
				trip.setTripId(oArr[4].toString());
			}
			if(null != oArr[6]) {  
				trip.setType(oArr[6].toString());
			}
			
			if(null != oArr[7]) { 
				trip.setSource(oArr[7].toString());
			}
			
			tripList.add(trip);
		}
		return tripList;
	}
	
	private PersonDTO convertPersonObj(PersonDb personDb) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setEmail(personDb.getEmail());
		personDTO.setName(personDb.getName()+" "+personDb.getLastName());
		personDTO.setId(personDb.getPersonId().toString());
		personDTO.setAboutMe(personDb.getAboutMe());
		personDTO.setPhoneNo(personDb.getMobile());
		personDTO.setPersonFbId(personDb.getId().toString());
		personDTO.setLocation(personDb.getLocation());
		personDTO.setBlog(personDb.getWebsite());
		if(StringUtils.isNotEmpty(personDb.getBadges())){
			
			List<String> badgesList = new ArrayList<>();
			String[] badgesArray = personDb.getBadges().split(",");
			for (int i = 0; i < badgesArray.length; i++) {
				badgesList.add(badgesArray[i].trim());
			}
			personDTO.setBadges(badgesList);
		}
		return personDTO;
	}
	
	private List<PersonDTO> convertPersonFollowerObj(List<Object[]>  followersOfAPerson) {
		List<PersonDTO> list = new ArrayList<PersonDTO>();
		for(Object[] oArr : followersOfAPerson) {
			PersonDTO personDTO = new PersonDTO();
			
			if(null != oArr[0]) {
				personDTO.setName(oArr[0].toString());
			}
			if(null != oArr[1]) {
				personDTO.setAboutMe(oArr[1].toString());
			}
			if(null != oArr[2]) {
			    personDTO.setPersonFbId(oArr[2].toString());
			}
			if(null != oArr[3]) {
				personDTO.setId(oArr[3].toString());
			}
			
			list.add(personDTO);
		}
		return list;
	}
	
	@Override
	public String isTripEditableByUser(String userId, String tripId) throws SolrServerException {
		UserResponse user = staticContent.getUserDetails(userId);
		
		if(null != user) {
			if(!CollectionUtils.isEmpty(user.getTripIds())) {
				if(user.getTripIds().contains(tripId)) {
					return "www.triplived.com/triplived/trip/submitTimeline";
				}
				if(null != user.getRole()) {
					if(user.getRole().equalsIgnoreCase("Curator") || user.getRole().equalsIgnoreCase("Admin")) {
						return "www.triplived.com/triplived/trip/saveTimelineForReview";
					}
				}
				return null;
			}
		}
		return null;
	}
	
	@Override
	public UserResponse getUserDetailsFromSolr(String userId) throws SolrServerException {
		UserResponse user = staticContent.getUserDetails(userId);
		return user;
		
	}
	
	@Override
	public UserResponse getUserDetailsByAccountIdFromSolr(String accountId) throws SolrServerException {
		UserResponse user = staticContent.getUserDetailsByAccountId(accountId);
		return user;
		
	}
	
	@Override
	public UserResponse getUserDetailsForAnalytics(String id) throws SolrServerException {
		UserResponse user = staticContent.getUserDetailsForAnalytics(id);
		return user;
		
	}
	
	
	

	@Override
	@Transactional(readOnly=true)
	public String getUserImageUrl(String personId, UserFrom preferenceUserFrom, Map<String, String> params) {
		PersonDb personDb = userDao.getPersonByUserId(personId);
		if(personDb == null) {
			return defaultTriplivedUserPhoto;
		}
		Set<UserAccountsDb> userAccounts = personDb.getUserAccounts();

		if(CollectionUtils.isNotEmpty(userAccounts)){
			for (UserAccountsDb userAccountsDb : userAccounts) {
				
				if(userAccountsDb.getUserFrom()  == UserFrom.SITE) {
					if(StringUtils.isNotEmpty(userAccountsDb.getUserImage())) {
						return userAccountsDb.getUserImage();
					}
				}
				//preference account or Primary account
				if((preferenceUserFrom != null && userAccountsDb.getUserFrom() == preferenceUserFrom ) || ( userAccountsDb.getUserFrom() == personDb.getUserFrom()) ){
					
					if(preferenceUserFrom == null){
						preferenceUserFrom = personDb.getUserFrom();
					}
					
					if(preferenceUserFrom == UserFrom.FACEBOOK ){
						//"https://graph.facebook.com/%s/picture?type={%s}";
						if(StringUtils.isNotEmpty(userAccountsDb.getUserImage()) && userAccountsDb.getUserImage().contains("triplived")){
							return userAccountsDb.getUserImage();
						}else{
							String facebookProfileImageUrl = String.format(facebookProfileUrl, userAccountsDb.getAccountId(), params.get("size")); 
							return facebookProfileImageUrl;
						}
						
					}else if(preferenceUserFrom == UserFrom.GOOGLE){
						//google image are directly served
						if(StringUtils.isNotEmpty(userAccountsDb.getUserImage())) {
							
							if(userAccountsDb.getUserImage().contains("triplived")){
								
								return userAccountsDb.getUserImage();
							}else{
								String dims =environment.getProperty(UserFrom.GOOGLE.toString()+"_"+params.get("size"));
								if(StringUtils.isEmpty(dims)){
									dims = "200";
								}
								return userAccountsDb.getUserImage() +"?sz="+dims;
							}
						}
					}
				}
				
			}
		}
		return defaultTriplivedUserPhoto;
	}


	/**
	 * 	 * FaceBook:
	 *   https://graph.facebook.com/1622279621337427?fields=cover&access_token=529598833847568%7C2c7BTiBtZ4lYx3Oc-gcXuGzclmY
	 * 
	 * 
	 * Facebook response
	 * 
	 * {
		   "cover": {
		      "id": "1664615220437200",
		      "offset_y": 19,
		      "source": "https://fbcdn-photos-b-a.akamaihd.net/hphotos-ak-xap1/t31.0-0/p480x480/11222378_1664615220437200_2519926070579339278_o.jpg"
		   },
		   "id": "1622279621337427"
		}
		
		
		Google
		
		 https://www.googleapis.com/plus/v1/people/102285039049192500465?fields=cover%2FcoverPhoto%2Furl&key={YOUR_API_KEY}
		 
	  Google Response
	  
	     {
			 "cover": {
			  "coverPhoto": {
			   "url": "https://lh3.googleusercontent.com/U5h1y1zTkvthXXxqNFceLqQ8mmuTfb2sJB-07t5nHaJKmZqj_obFW4fvNRIGNNumU-Uv=s630-fcrop64=1,000000b9ffffd81c"
			  }
			 }
		}

	 * @param personId
	 * @param accountId
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public String getUserCoverPageUrl(String personId, String accountId) {
		
		String coverPage = null;
		PersonDb personDb = userDao.getPersonByUserId(personId);
		Set<UserAccountsDb> userAccounts = personDb.getUserAccounts();

		UserAccountsDb account = null;
		if(CollectionUtils.isNotEmpty(userAccounts)){
			
			if( userAccounts.size() > 1){
				for (UserAccountsDb userAccountsDb : userAccounts) {
					if(StringUtils.isNotEmpty(accountId)){
						if(userAccountsDb.getAccountId().equalsIgnoreCase(accountId)){
							account = userAccountsDb; 
							break;
						}
					}else{
						
						if(userAccountsDb.getUserFrom() == UserFrom.FACEBOOK){
							account = userAccountsDb; // favoring user facebook account;
							break;
						}
					}
				}
			}else{
				account = userAccounts.iterator().next();
			}
		}
		
		if(account == null) {
			coverPage = defaultcoverpage;
		}else{
			
			switch (account.getUserFrom()) {
			case FACEBOOK:
				
				try {
					String fbCoverUrl = String.format(facebokcoverpageUrl, account.getAccountId(), facebookaccesstoken);
					RestTemplate restTemplate = new RestTemplate();
					ResponseEntity<FaceBookCoverPageResponse> response = restTemplate.getForEntity(fbCoverUrl, FaceBookCoverPageResponse.class);

					FaceBookCoverPageResponse coverPageBody = response.getBody();

					if (coverPageBody != null && coverPageBody.getCover() != null) {
						coverPage = coverPageBody.getCover().getSource();
					}
				} catch (RestClientException e) {
					e.printStackTrace();
					coverPage = defaultcoverpage;
				}
				break;
				
			case GOOGLE:
				coverPage = defaultcoverpage;
				break;
			case SITE:
			default:
				coverPage = defaultcoverpage;
			}
		}
		
		if(coverPage == null){
			coverPage = defaultcoverpage;
		}
		
		return coverPage;
	}
	
	@Override
	public PersonProfile getUserFromDeviceId(String deviceId){
		
		PersonProfile person = new PersonProfile();
		
		Object[] obj = userDao.getUserDetailsByDeviceId(deviceId);
		if(null != obj) {
			if(null != obj[0]) {
				person.setFname(obj[0].toString());
			}if(null != obj[1]) {
				person.setLname(obj[1].toString());
			}if(null != obj[2]) {
				person.setId(((BigInteger) obj[2] ).longValue());
			}if(null != obj[3]) {
				person.setDeviceId(obj[3].toString());
			}
			return person;
		}
		
		return null;
	}
	
	/**
	 * Provides all activities done on a user's trip, his photos etc. 
	 */
	@Override
	@Transactional
	public NotificationBarParentDTO  notificationBar(String userId) {
		//This needs to be saved in db 
		UserAdditionalInfoDb infoObj = userDao.getUserAdditionalInfo(Long.parseLong(userId));
		Long userLastSeenTime = System.currentTimeMillis() / 1000L;
		if(null != infoObj) {
			userLastSeenTime = infoObj.getLastSeenNotificationTimestamp();
		}
		Integer newMessageCount = 0;
		
		//Get likes on trip
		List<NotificationBarDTO> tripLikedDetails = tripDao.getAllUsersDetailsWhoLikedTripsByTripOwnerId(Long.parseLong(userId));
		Map<String, NotificationBarDTO> tripLikedMap = new HashMap<String, NotificationBarDTO>();
		if(!CollectionUtils.isEmpty(tripLikedDetails)) {
			for(NotificationBarDTO obj : tripLikedDetails) {
				String tripId = obj.getTripId();
				if(tripLikedMap.containsKey(tripId)) {
					NotificationBarDTO nObj = tripLikedMap.get(tripId);
					nObj.setCount(nObj.getCount() + 1);
					if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
						nObj.setMessage(obj.getUserName());
						nObj.setNewMessage(true);
						newMessageCount++;
					}else {
						nObj.setMessage(nObj.getUserName() + " and " + (nObj.getCount() - 1) + " other users liked your trip - " + nObj.getTripName());
					}
				}else {
					NotificationBarDTO nObj = new NotificationBarDTO();
					nObj.setTripId(tripId);
					nObj.setTripIdNew(obj.getTripIdNew());
					nObj.setTripName(obj.getTripName());
					nObj.setEntityType(ClientMessageType.LIKE.toString());
					nObj.setTripCoverUri(obj.getTripCoverUri());
					nObj.setCount(1);
					nObj.setUserId(obj.getUserId());
					nObj.setUserName(obj.getUserName());
					if(null != obj.getTimeStamp()) {
						nObj.setTimeStamp(obj.getTimeStamp() * 1000L);
					}
					
					if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
						nObj.setMessage(nObj.getUserName() + " liked your trip - " + nObj.getTripName());
						nObj.setNewMessage(true);
						newMessageCount++;
					}else {
						nObj.setMessage(nObj.getUserName() + " liked your trip - " + nObj.getTripName());
					}
					
					tripLikedMap.put(tripId, nObj);
				}
			}
		}
		
		//Get comments on trip
		List<NotificationBarDTO> tripCommentDetails = tripDao.getAllUsersDetailsWhoCommentedOnTripsByTripOwnerId(Long.parseLong(userId));
		Map<String, NotificationBarDTO> tripCommentsMap = new HashMap<String, NotificationBarDTO>();
		if(!CollectionUtils.isEmpty(tripCommentDetails)) {
		for(NotificationBarDTO obj : tripCommentDetails) {
			String tripId = obj.getTripId();
			if(tripCommentsMap.containsKey(tripId)) {
				NotificationBarDTO nObj = tripCommentsMap.get(tripId);
				nObj.setCount(nObj.getCount() + 1);
				if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
					nObj.setMessage(obj.getUserName());
					nObj.setNewMessage(true);
					newMessageCount++;
				}else {
					nObj.setMessage(nObj.getUserName() + " and " +  (nObj.getCount() - 1) + " other users commented on trip - " + nObj.getTripName());
				}
			}else {
				NotificationBarDTO nObj = new NotificationBarDTO();
				nObj.setTripId(tripId);
				nObj.setTripIdNew(obj.getTripIdNew());
				nObj.setTripName(obj.getTripName());
				nObj.setEntityType(ClientMessageType.COMMENT.toString());
				nObj.setTripCoverUri(obj.getTripCoverUri());
				nObj.setCount(1);
				nObj.setUserId(obj.getUserId());
				nObj.setUserName(obj.getUserName());
				if(null != obj.getTimeStamp()) {
					nObj.setTimeStamp(obj.getTimeStamp() * 1000L);
				}
				
				if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
					nObj.setMessage(nObj.getUserName() + " commented on trip - " + nObj.getTripName());
					nObj.setNewMessage(true);
					newMessageCount++;
				}else {
					nObj.setMessage(nObj.getUserName() + " commented on trip - " + nObj.getTripName());
				}
				
				tripCommentsMap.put(tripId, nObj);
			}
		}
	   }
		
		Map<String, NotificationBarDTO> tripMediaLikedMap = new HashMap<String, NotificationBarDTO>();
		try {
		//Get likes on trip photo
			if((userId.equals("23")) || (userId.equals("17"))) {
			List<NotificationBarDTO> tripMediaLikedDetails = tripMediaDao.getAllUsersDetailsWhoLikedMediaOfTripsOfParticularUser(Long.parseLong(userId));
			Collections.sort(tripMediaLikedDetails);
			
			if(!CollectionUtils.isEmpty(tripMediaLikedDetails)) {
				for(NotificationBarDTO obj : tripMediaLikedDetails) {
					String tripId = obj.getTripId();
					String mediaId = obj.getMediaId();
					if(tripMediaLikedMap.containsKey(mediaId)) {
						NotificationBarDTO nObj = tripMediaLikedMap.get(mediaId);
						nObj.setCount(nObj.getCount() + 1);
						if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
							nObj.setMessage(obj.getUserName());
							nObj.setNewMessage(true);
							newMessageCount++;
						}else {
							nObj.setMessage(nObj.getUserName() + " and " + (nObj.getCount() - 1) + " other users liked photo your trip - " + nObj.getTripName());
						}
					}else {
						NotificationBarDTO nObj = new NotificationBarDTO();
						nObj.setTripId(tripId);
						nObj.setMediaId(mediaId);
						nObj.setTripIdNew(obj.getTripIdNew());
						nObj.setTripName(obj.getTripName());
						nObj.setEntityType(ClientMessageType.PHOTO_LIKE.toString());
						String imagePath = "http://www.triplived.com/static/timeline/trip/"+mediaId.split("-")[1]+"/"+mediaId+".jpg";
						nObj.setTripCoverUri(imagePath);
						nObj.setCount(1);
						nObj.setUserId(obj.getUserId());
						nObj.setUserName(obj.getUserName());
						if(null != obj.getTimeStamp()) {
							nObj.setTimeStamp(obj.getTimeStamp() * 1000L);
						}
						
						if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
							nObj.setMessage(nObj.getUserName() + " liked your trip photo - " + nObj.getTripName());
							nObj.setNewMessage(true);
							newMessageCount++;
						}else {
							nObj.setMessage(nObj.getUserName() + " liked your trip photo - " + nObj.getTripName());
						}
						
						tripMediaLikedMap.put(mediaId, nObj);
					}
				}
			}
		  }
		}catch(Exception e) {
			logger.error("Notification Bar For Trip Media Like : User - {} , error - {}",userId, e.getMessage());
		}
		
		//Get comments on photo
		Map<String, NotificationBarDTO> tripMediaCommentsMap = new HashMap<String, NotificationBarDTO>();
		    try{
		      if((userId.equals("23")) || (userId.equals("17"))) {
				List<NotificationBarDTO> tripMediaCommentsDetails = tripMediaDao.getAllUsersDetailsWhoCommentedOnMediaOfTripsOfParticularUser(Long.parseLong(userId));
				Collections.sort(tripMediaCommentsDetails);	
				if(!CollectionUtils.isEmpty(tripMediaCommentsDetails)) {
					for(NotificationBarDTO obj : tripMediaCommentsDetails) {
						String tripId = obj.getTripId();
						String mediaId = obj.getMediaId();
						if(tripMediaCommentsMap.containsKey(mediaId)) {
							NotificationBarDTO nObj = tripMediaCommentsMap.get(mediaId);
							nObj.setCount(nObj.getCount() + 1);
							if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
								nObj.setMessage(obj.getUserName());
								nObj.setNewMessage(true);
								newMessageCount++;
							}else {
								nObj.setMessage(nObj.getUserName() + " and " + (nObj.getCount() - 1) + " other users commented on trip photo - " + nObj.getTripName());
							}
						}else {
							NotificationBarDTO nObj = new NotificationBarDTO();
							nObj.setTripId(tripId);
							nObj.setMediaId(mediaId);
							nObj.setTripIdNew(obj.getTripIdNew());
							nObj.setTripName(obj.getTripName());
							nObj.setEntityType(ClientMessageType.PHOTO_COMMENT.toString());
							String imagePath = "http://www.triplived.com/static/timeline/trip/"+mediaId.split("-")[1]+"/"+mediaId+".jpg";
							nObj.setTripCoverUri(imagePath);
							nObj.setCount(1);
							nObj.setUserId(obj.getUserId());
							nObj.setUserName(obj.getUserName());
							if(null != obj.getTimeStamp()) {
								nObj.setTimeStamp(obj.getTimeStamp() * 1000L);
							}
							
							if(null != obj.getTimeStamp() && (obj.getTimeStamp() > userLastSeenTime)) {
								nObj.setMessage(nObj.getUserName() + " commented on your trip photo - " + nObj.getTripName());
								nObj.setNewMessage(true);
								newMessageCount++;
							}else {
								nObj.setMessage(nObj.getUserName() + " commented on your trip photo - " + nObj.getTripName());
							}
							
							tripMediaCommentsMap.put(mediaId, nObj);
						}
					}
			     }		
		    	}
		      }catch(Exception e) {
					logger.error("Notification Bar For Trip Media Comments : User - {} , error - {}",userId, e.getMessage());
			}
				
		//Get Upvotes on entities
		//Also, group the above actions
		List<NotificationBarDTO> list = new ArrayList<NotificationBarDTO>();
		
		Iterator itLiked = tripLikedMap.entrySet().iterator();
	    while (itLiked.hasNext()) {
	        Map.Entry pair = (Map.Entry)itLiked.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        NotificationBarDTO obj = (NotificationBarDTO) pair.getValue();
	        if(null != obj.getTimeStamp()) {
	        	if(!obj.getUserId().equalsIgnoreCase(userId)) {
	        		list.add(obj);
	        	}
	        }
	    }
	    
	    Iterator itComment = tripCommentsMap.entrySet().iterator();
	    while (itComment.hasNext()) {
	        Map.Entry pair = (Map.Entry)itComment.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        NotificationBarDTO obj = (NotificationBarDTO) pair.getValue();
	        if(null != obj.getTimeStamp()) {
	        	if(!obj.getUserId().equalsIgnoreCase(userId)) {
	        		list.add(obj);
	        	}
	        }
	    }
	    
	    Iterator itMediaLiked = tripMediaLikedMap.entrySet().iterator();
	    while (itMediaLiked.hasNext()) {
	        Map.Entry pair = (Map.Entry)itMediaLiked.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        NotificationBarDTO obj = (NotificationBarDTO) pair.getValue();
	        if(null != obj.getTimeStamp()) {
	        	if(!obj.getUserId().equalsIgnoreCase(userId)) {
	        		list.add(obj);
	        	}
	        }
	    }
	    
	    Iterator itMediaComment = tripMediaCommentsMap.entrySet().iterator();
	    while (itMediaComment.hasNext()) {
	        Map.Entry pair = (Map.Entry)itMediaComment.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        NotificationBarDTO obj = (NotificationBarDTO) pair.getValue();
	        if(null != obj.getTimeStamp()) {
	        	if(!obj.getUserId().equalsIgnoreCase(userId)) {
	        		list.add(obj);
	        	}
	        }
	    }
	    
	    Collections.sort(list);
	    NotificationBarParentDTO obj = new NotificationBarParentDTO();
	    obj.setNotifications(list);
	    obj.setNewMsgCount(newMessageCount);
		return obj;
	}
	
	@Override
	@Transactional
	public void notificationBarUpdate(String userId) {
		Long ts = System.currentTimeMillis() / 1000L;
		Long id = Long.parseLong(userId);
		UserAdditionalInfoDb obj = userDao.getUserAdditionalInfo(id);
		if(null == obj) {
			obj = new UserAdditionalInfoDb();
			obj.setUserId(id);
		}
		obj.setLastSeenNotificationTimestamp(ts);
		userDao.updateUserAdditionalInfo(obj);
	}

	@Override
	@Transactional
	public void updateUserProfile(Long id, PersonProfile user) {
		
		PersonDb person =  userDao.getPersonByUserId(id+"");
		
		if(person != null){
			
			Set<UserAccountsDb> accounts = person.getUserAccounts();
			
			if(StringUtils.isNotEmpty(user.getProfileImagePath())){
				for (UserAccountsDb userAccountsDb : accounts) {
					userAccountsDb.setUserImage(user.getProfileImagePath());
					userDao.updateObject(userAccountsDb);
				}
			}
			
			person.setMobile(user.getMobile());
			person.setAboutMe(user.getAboutMe());
			person.setLocation(user.getAddress());
			person.setWebsite(user.getWebSite());
			person.setUpdatedDate(new Date());
			
			userDao.update(person) ;
		}
		
	}
	 
}

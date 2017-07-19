package com.triplived.controller.manual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.trip.dto.TripCityType;
import com.domain.triplived.trip.dto.Event;
import com.domain.triplived.trip.dto.File;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.domain.triplived.trip.dto.Update;
import com.google.gson.Gson;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.user.UserDao;
import com.triplived.dao.user.UserMetaDAO;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripMetaData;
import com.triplived.entity.TripUserDb;
import com.triplived.rest.trip.TripClient;
import com.triplived.service.AppUtils;
import com.triplived.service.image.ImageResizeService;
import com.triplived.service.notification.GCMNotificationService;
import com.triplived.service.trip.TripService;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/manualUtility")
public class TripManualUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(TripManualUtility.class);
	
	@Autowired
	private TripDAO tripDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserMetaDAO userMetaDoa;
	
	@Autowired
	private TripClient tripClient;
	
	@Autowired
	private TripService tripService;
	

    @Autowired
    private GCMNotificationService gcmService;
    
	
	@Autowired
	private AppUtils apputils;
	
	@Autowired
	private ImageResizeService imgResizeServer;
	
	@RequestMapping(value = "/generatePublicId", method = RequestMethod.GET)	
	@Transactional
	public void generateId(@RequestParam("start") String start, 
			@RequestParam("end") String end) {
		
		for(long i = Long.parseLong(start); i <= Long.parseLong(end); i++) {
			try {
				TripDb tripDb = tripDao.getTrip(i);
				if(null != tripDb) {
					String randomPublicId = TripLivedUtil.generatePublicTripId(i);
					tripDb.setTripPublicId(Long.parseLong(randomPublicId));
					tripDao.updateTrip(tripDb);
					System.out.println("Done for trip - " + i);
				}
			}catch(Exception e) {
				System.out.println("Exception is - ");
				e.printStackTrace();
			}
		}
		
	}
	
	@RequestMapping(value = "/trending", method = RequestMethod.GET)	
	public void trendingTripPoints() {
		tripClient.updateTrendingTrips();
	}
	
	@RequestMapping(value = "/get/trending", method = RequestMethod.GET)	
	public void gettrendingTrips() {
		try {
			tripClient.getTrendingTrips();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/break/sidAndMedia", method = RequestMethod.GET)
	@Transactional
	public void breakTripsForSidAndMedia(@RequestParam("status") String status) {
		List<String> tripIds = getAllTripsOfTypeTimeline();
		for(String tripId : tripIds) {
			TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
			Gson gson = new Gson();
			TimelineTrip timelineTrip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
			tripService.populateTimelineTripData(timelineTrip,false,true);
			tripDb.setTripData(gson.toJson(timelineTrip));
			tripDb.setTripDataEdited(gson.toJson(timelineTrip));
			tripDao.updateTrip(tripDb);
			System.out.println("Done for trip - " + tripId);
			
		}
	}
	
	
	private TlEvent getEvent(Long userId, String tripId, TripMetaData type){
		
		TlEvent event = new TlEvent();
		event.setUser(userId);
		event.setVerb(type.toString());
		event.setActionDetails(tripId+"");
		event.addToHeader(TlEvent.TYPE, type);
		
		return event;
	}

	
	
	@RequestMapping(value = "/metaUserProfile/{min}/{max}", method = RequestMethod.GET)
	public void userProfile(@PathVariable("min") Long min, @PathVariable("max") Long max) {
		
		List<Long> users = userDao.getAllUsers(min, max);
		for (Long user : users) {
			
			Long followers  = userDao.getFollowFollowerCount(user, TripMetaData.FOLLOWERS);
			Long following = userDao.getFollowFollowerCount(user, TripMetaData.FOLLOWING);
			
			if(followers != null && followers > 0){
				userMetaDoa.updateMetaDataCount(user, TripMetaData.FOLLOWERS, followers.intValue());
			}
			
			if(following != null && following > 0){
				userMetaDoa.updateMetaDataCount(user, TripMetaData.FOLLOWING, following.intValue());
			}

			List<Object> trips = tripDao.getAllTripsFromUser(user);
			if(CollectionUtils.isNotEmpty(trips)){
				userMetaDoa.updateMetaDataCount(user, TripMetaData.TRIP_SUBMITTED, trips.size());
			}
			
			logger.warn("USERS FOLLOW done for user {} , followers {} folowing {} trips {} ", user, followers, following, trips.size());
		}
		
	}
	
	
	@RequestMapping(value = "/metaLikeAndComment/{min}/{max}", method = RequestMethod.GET)
	@Transactional
	public void metaLikeAndComment(@PathVariable("min") Long min, @PathVariable("max") Long max) {
		List<String> tripIds = getAllTripsOfTypeTimeline(min, max);
		for(String tripId : tripIds) {
			Long tripCommentCount = tripDao.getTripCommentsCount(Long.parseLong(tripId));
			Long tripLikeCount = tripDao.getTripLikes(Long.parseLong(tripId));
			
			TlEvent eventLike = getEvent(23l, tripId, TripMetaData.LIKES);
			eventLike.setUpdateAll(true);
			eventLike.setSecondaryActionDetails(tripLikeCount+"");
			
			TlEvent eventComment = getEvent(23l, tripId, TripMetaData.COMMENTS);
			eventComment.setUpdateAll(true);
			eventComment.setSecondaryActionDetails(tripCommentCount+"");
			
			apputils.getHandler("likeHandler").setState(eventLike);
			apputils.getHandler("commentHandler").setState(eventComment);
			
			logger.warn(" Updating trip {} trips comment count increase for  like {} and comment {}", tripId, tripCommentCount, tripLikeCount);
		}
	}
	
	@RequestMapping(value = "/metaUpdataAndBadges/{min}/{max}", method = RequestMethod.GET)
	@Transactional
	public void metaUpdateAndBadges(@PathVariable("min") Long min, @PathVariable("max") Long max ) {
		List<String> tripIds = getAllTripsOfTypeTimeline(min, max);
		for(String tripId : tripIds) {
			TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
			Gson gson = new Gson();
			TimelineTrip timelineTrip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
			manualBadgesAllocation(timelineTrip);
			System.out.println("Done for trip - " + tripId);
			
		}
	}
	
	public void manualBadgesAllocation(TimelineTrip trip){
		
		Set<String> countCities = new HashSet<String>();
		Set<String> countAttractions = new HashSet<String>() ;
		int countActivities = 0; 
		Set<String> countHotels = new HashSet<String>();
		Set<String> countRestaruants =  new HashSet<String>();
		int countExperiences =  0;
		int countPhotos =  0;
		Set<String> countCountries =  new HashSet<String>();
		
		for(com.domain.triplived.trip.dto.SubTrip st : trip.getSubTrips()) {
			
			if(null != st.getToCityDTO() && st.getToCityDTO().getCityType().equalsIgnoreCase(TripCityType.Destination.toString())) { 
				countCountries.add("1");
				countCities.add(st.getToCityDTO().getCityId());
			}
			
			List<Event> listOfEvents = st.getToCityDTO().getEvents();
			if(!CollectionUtils.isEmpty(listOfEvents)){
				
				for(Event eventlist : listOfEvents){
 
							if("hotel".equalsIgnoreCase(eventlist.getType())) {
								countHotels.add(eventlist.getName());
							}else if("restaurant".equalsIgnoreCase(eventlist.getType())) {
								countRestaruants.add(eventlist.getName());
							}else if("attraction".equalsIgnoreCase(eventlist.getType())) {
								countAttractions.add(eventlist.getName());
							}else if("memory".equalsIgnoreCase(eventlist.getType())) {
								
								if(CollectionUtils.isNotEmpty(eventlist.getUpdates())){
									
									for(Update update :  eventlist.getUpdates()){
										String type = (String) update.getAdditionalProperties().get("type");
										if(StringUtils.isNotEmpty(type) && "activity".equals(type) ){
											countActivities++;
										}else{
											countExperiences++;	
										}
									}
								}
							}else if("activity".equalsIgnoreCase(eventlist.getType())) {
								countActivities++;
							}
							
							if(!"memory".equalsIgnoreCase(eventlist.getType())){
								
								if(CollectionUtils.isNotEmpty(eventlist.getUpdates())){
									
									for(Update update :  eventlist.getUpdates()){
										String type = (String) update.getAdditionalProperties().get("type");
										if(StringUtils.isNotEmpty(type) && "activity".equals(type) ){
											countActivities++;
										}else{
											countExperiences++;	
										}
										
										if(CollectionUtils.isNotEmpty(update.getFiles())) {
											countPhotos += update.getFiles().size();
										}
									}
								}
							}
							
							//SAVE Photos
							countPhotos += eventlist.getFiles().size();
							
							//In case of Memory, all images are stored under Updates
							if(eventlist.getType().equalsIgnoreCase("Memory")) {
								if(CollectionUtils.isNotEmpty(eventlist.getUpdates())) {
									for(Update update : eventlist.getUpdates()) {
										if(CollectionUtils.isNotEmpty(update.getFiles())) {
											countPhotos += update.getFiles().size();
										}
									}
								}
							}
					}//end for loop for entities
			}
			com.domain.triplived.trip.dto.TripTransportDTO transport = st.getTripTransportDTO();
			if(null != transport) {
				if(CollectionUtils.isNotEmpty(transport.getFiles())) {
					countPhotos +=transport.getFiles().size();
			   }
			}//end of transport if
			
			com.domain.triplived.trip.dto.ToCityDTO destCity = st.getToCityDTO();
			if(null != destCity) {
				List<File> files = destCity.getFiles();
				if(CollectionUtils.isNotEmpty(files)) {
					countPhotos +=files.size();
			   }
			}//end of city if
		}//end of for subTrip
		
		Map<TripMetaData, Integer> entityCountMap = new HashMap<>();
		entityCountMap.put(TripMetaData.ACTIVITIES, countActivities);
		entityCountMap.put(TripMetaData.ATTRACTIONS, countAttractions.size());
		entityCountMap.put(TripMetaData.CITIES, countCities.size());
		entityCountMap.put(TripMetaData.COUNTRIES, countCountries.size());
		entityCountMap.put(TripMetaData.EXPERIENCES, countExperiences);
		entityCountMap.put(TripMetaData.HOTELS, countHotels.size());
		entityCountMap.put(TripMetaData.RESTARUANTS, countRestaruants.size());
		entityCountMap.put(TripMetaData.PHOTOS, countPhotos);
		entityCountMap.put(TripMetaData.TRIP_SUBMITTED, 1);
		
		logger.warn("META Data for trip {}  meta is {} ", trip.getTripId(),  entityCountMap);
		tripService.fireTripSubmitEvent(trip, entityCountMap);
	}
	
	@RequestMapping(value = "/break/reviewsAndExp", method = RequestMethod.GET)
	@Transactional
	public void breakTripsForReviews(@RequestParam("status") String status) {
		List<String> tripIds = getAllTripsOfTypeTimeline();
		
		
		for(String tripId : tripIds) {
			TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
			try {	
				tripService.addReviewsOfEvents(tripDb.getId().toString());
				logger.warn("Trip reviews and experiences: Done manually for trip - " + tripId);
			
			}catch(Exception e) {
				logger.error("Trip reviews and experiences: Exception while breaking trip for reviews and experiences for trip - {} - {}", tripDb.getId(), e);
			}
	    }
			
			
		
	}
	
	@RequestMapping(value = "/resize", method = RequestMethod.GET)	
	public void resizeExploreImages(@RequestParam("dir") String dir) {
		
		String directory = dir.replace(".","/");
		java.io.File folder = new java.io.File(directory);
		java.io.File[] listOfFiles = folder.listFiles();
		
			
		for (int i = 0; i < listOfFiles.length; i++) {
			java.io.File file = listOfFiles[i];
			  if (file.isFile()) {
			    String name = file.getName().split("\\.")[0];
			    String destFile = directory + java.io.File.separatorChar + name + "m" + ".jpg";
			    try {
					imgResizeServer.resizeImages(file.getPath(), destFile, 500);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  } 
			}
	}
	
	
	public List<String> getAllTripsOfTypeTimeline() {
		return getAllTripsOfTypeTimeline(0l, 0l);
	} 
	
	public List<String> getAllTripsOfTypeTimeline(Long min, Long max) {
		List<TripDb> trips = new ArrayList<>();
		if (min!= null && max!= null) {
			trips = tripDao.getAllValidTrips(min, max);
		} else {
			trips = tripDao.getAllValidTrips();
		}
		List<String> tripIds = new ArrayList<String>();
		for(TripDb tripDb : trips) {
			tripIds.add(tripDb.getId().toString());
		}
		return tripIds;
	} 
	
	
	 @RequestMapping(value="/reminder", method=RequestMethod.GET)
	    public void sendTripReminderNotificationToUser(@RequestParam("id") String tripId) {
	    	
	    	try {
	    		
				//logger.warn("Processing request for sending trivia notifications to device - {}", deviceId);
				gcmService.sendTripReminderNotification(tripId);
	    	}catch(Exception e) {
	    		logger.error("Error while sending notifications as request from device - {}", e);
	    	}
	    	
	    }

}

package com.triplived.service.notification.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.badge.Badge;
import com.connectme.domain.triplived.dto.BadgeEarnedNotification;
import com.connectme.domain.triplived.dto.CommentNotificationDTO;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.FollowNotificationDTO;
import com.connectme.domain.triplived.dto.LikeNotificationDTO;
import com.connectme.domain.triplived.dto.NotificationDTO;
import com.connectme.domain.triplived.dto.PhotoLikeNotificationDTO;
import com.connectme.domain.triplived.dto.ShareNotificationDTO;
import com.gcm.ClientMessageType;
import com.gcm.GCMNotificationStatus;
import com.triplived.dao.device.DeviceDao;
import com.triplived.dao.device.UserDynamicDao;
import com.triplived.dao.messageDetails.MessageDetailsDao;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripMediaDAO;
import com.triplived.dao.user.UserDao;
import com.triplived.entity.DeviceDb;
import com.triplived.entity.MessageDetailsDb;
import com.triplived.entity.PersonDb;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripEntityMediaDb;
import com.triplived.entity.UserDynamicDb;
import com.triplived.rest.client.StaticContent;
import com.triplived.rest.notification.GCMNotificationClient;
import com.triplived.service.notification.GCMNotificationService;
import com.triplived.service.staticContent.StaticEntityService;

@Service
public class GCMNotificationServiceImpl implements GCMNotificationService {

	
	@Autowired
	private GCMNotificationClient gcmClient;
	
	@Autowired
	private StaticContent sc;
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired
	private UserDynamicDao userDynamicDao;
	
	@Autowired
	private TripDAO tripDao;
	
	@Autowired
	private TripMediaDAO tripMediaDAO;
	
	@Autowired
	private StaticEntityService ses;
	
	@Autowired
	private MessageDetailsDao messageDetailsDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private Environment environment;
	
	private static final Logger logger = LoggerFactory.getLogger(GCMNotificationServiceImpl.class );
	/**
	 * This method is for sending messages specific to attractions from app.
	 * @param msg
	 * @param deviceId
	 * @return
	 * @throws IOException 
	 */
	@Async
	@Override
	public Boolean sendAttractionSpecificNotifications(String deviceId, NotificationDTO msg) throws IOException, Exception {
		
		String registrationId = getRegistrationIdFromDeviceId(deviceId);
		if(null == registrationId) {
			logger.error("Found null RegistrationId while processing request for review for device - {} for entity {} of type {}", deviceId, msg.getName(), msg.getType());
			return false;
		}
		
		List<String> devices = new ArrayList<String>();
		populateListOfDevices(registrationId, devices);
		
		return gcmClient.sendReviewNotificationMulticast(msg, devices);
		
		
	}
	
	@Async
	@Override
	public Boolean sendCitySpecificNotifications(String deviceId, NotificationDTO notification) throws IOException, Exception {
		
		String registrationId = getRegistrationIdFromDeviceId(deviceId);
		if(null == registrationId) {
			logger.error("Found null RegistrationId while processing request for review for device - {} for entity {} of type {}", deviceId, notification.getName(), notification.getType());
			return false;
		}
		NotificationDTO msg = getPunchlineForGCMNotificationsForCity(registrationId, notification.getId());
		msg.setId(notification.getId());
		msg.setLocalId(notification.getLocalId());
		msg.setType("city");
		List<String> devices = new ArrayList<String>();
		populateListOfDevices(registrationId, devices);
		
		return gcmClient.sendTriviaNotificationMulticast(msg, devices);
		
		
	}
	
	@Async
	@Override
	public Boolean sendHotelSpecificNotifications(String deviceId, NotificationDTO msg) throws IOException, Exception {
		
		String registrationId = getRegistrationIdFromDeviceId(deviceId);
		if(null == registrationId) {
			logger.error("Found null RegistrationId while processing request for review for device - {} for entity {} of type {}", deviceId, msg.getName(), msg.getType());
			return false;
		}
		
		List<String> devices = new ArrayList<String>();
		populateListOfDevices(registrationId, devices);
		
		return gcmClient.sendReviewNotificationMulticast(msg, devices);
		
		
	}
	
	
	@Async
	@Override
	public Boolean sendRestaurantSpecificNotifications(String deviceId, NotificationDTO msg) throws IOException, Exception {
		
		String registrationId = getRegistrationIdFromDeviceId(deviceId);
		if(null == registrationId) {
			logger.error("Found null RegistrationId while processing request for review for device - {} for entity {} of type {}", deviceId, msg.getName(), msg.getType());
			return false;
		}
		
		List<String> devices = new ArrayList<String>();
		populateListOfDevices(registrationId, devices);
		
		return gcmClient.sendReviewNotificationMulticast(msg, devices);
		
		
	}
	
	@Async
	@Override
	public Boolean sendUnknowEntityNotifications(String deviceId, NotificationDTO msg) throws IOException, Exception {
		String registrationId = getRegistrationIdFromDeviceId(deviceId);
		if(null == registrationId) {
			logger.error("Found null RegistrationId while processing request for review for device - {} for entity {} of type {}", deviceId, msg.getName(), msg.getType());
			return false;
		}
		
		List<String> devices = new ArrayList<String>();
		populateListOfDevices(registrationId, devices);
		
		return gcmClient.sendReviewNotificationMulticast(msg, devices);
		
	}
	
	/**
	 * This is only for attractions, for cities, there is another method above.
	 */
	@Async
	@Override
	public void sendTriviaNotifications(String deviceId, String lat, String lng, String accuracy) throws IOException, Exception {
		
		String registrationId = getRegistrationIdFromDeviceId(deviceId);
		if(null == registrationId) {
			logger.warn("Found null RegistrationId while processing request for trivia for device - {} ", deviceId);
		}
		List<EntityResponseDTO> entityList = ses.getAttractionExternalEntityByCoordinatesForTriviaNotifications(deviceId, lat, lng, accuracy);
		
		if(!CollectionUtils.isEmpty(entityList)) {
			boolean flag = false;
			for(EntityResponseDTO entity : entityList) {
				NotificationDTO msg = getPunchlineForGCMNotificationsForAttractions(registrationId, entity.getAttractionId());
				
				if(null != msg.getPunchline()) {
					flag = true;
					logger.warn("Found punchline - {} for trivia for device - {} for entity {}", msg.getPunchline(), deviceId, entity.getAttractionId());
					msg.setType("attraction");
					msg.setLocalId("0");//TODO remove this as this is only for time-being, until exception handled at client side. 
					List<String> devices = new ArrayList<String>();
					populateListOfDevices(registrationId, devices);
					
					gcmClient.sendTriviaNotificationMulticast(msg, devices);
					break;
				}
		    }
			if(!flag) {
				logger.warn("No punchline was found for trivia for device - {}", deviceId);
			}
		}
		
	}
	
	
	@Async
	@Override
	public void sendTrendingTripNotifications(String tripId, String message) throws IOException, Exception {
		
		List<UserDynamicDb> deviceDetails = userDynamicDao.getAllDetails();
		Map<Long,UserDynamicDb> validDevices = new HashMap<Long,UserDynamicDb>();
		for(UserDynamicDb obj : deviceDetails) {
			Long id = obj.getUserId();
			if(validDevices.containsKey(id)) {
				UserDynamicDb getObj = validDevices.get(id);
				if(obj.getUpdateDate().after(getObj.getUpdateDate())) {
					validDevices.put(id, obj);
				}
			}else {
				validDevices.put(id, obj);
			}
		}
		
		TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
	    
		if(null != tripDb) {
			NotificationDTO msg = new NotificationDTO();
			msg.setId(tripDb.getId().toString());
			msg.setTripId(tripDb.getId().toString());
			msg.setTripIdNew(tripDb.getTripPublicId().toString());
			msg.setTripName(tripDb.getTripName());
			msg.setName(tripDb.getTripName());
			msg.setTripCoverUri(tripDb.getTripCoverUri());
			msg.setPunchline(message);
			msg.setType("TRIP_VIEW");
			msg.setLocalId("0");
			
			Iterator it = validDevices.entrySet().iterator();
			while(it.hasNext()) {
			//String[] devicess = new String[]{"3c8fe7c2c291a19b","41a16e8d617c1810","492eb500d39d093d","500e888b98fd2cc4","c62baaeda07b017e","eeb22da8221c1956"};
			//for(String deviceId : devicess) {
				Map.Entry pair = (Map.Entry)it.next();
				UserDynamicDb obj = (UserDynamicDb) pair.getValue();
				
				String deviceId = obj.getDeviceId();
				String registrationId = getRegistrationIdFromDeviceId(deviceId);
				
				if(null == registrationId) {
					logger.warn("Found null RegistrationId while processing request for sending Trending trip for device - {} ", deviceId);
					continue;
				}
			
			    List<String> devices = new ArrayList<String>();
				populateListOfDevices(registrationId, devices);
				gcmClient.sendTrendingTripNotificationMulticast(msg, devices);
			}
			
		}
	}
	
	
	
	private void populateListOfDevices(String registrationId, List<String> devices) {
		devices.add(registrationId);
	}
	
	@Override
	public NotificationDTO getPunchlineForGCMNotificationsForAttractions(String registrationId, String attractionId) throws IOException, Exception {
		NotificationDTO msg = sc.getPunchlineForGCMNotificationsForAttractions(attractionId);
		return msg;
	}
	
	
	@Override
	public NotificationDTO getPunchlineForGCMNotificationsForCity(String registrationId, String cityId) throws IOException, Exception {
		NotificationDTO msg = sc.getPunchlineForGCMNotificationsForCity(cityId);
		return msg;
	}
	
	private String getRegistrationIdFromDeviceId(String deviceId) {
		return deviceDao.getRegistrationIdFromDeviceId(deviceId);
	}
	
	@Override
	@Transactional
	public void updateMessageStatus(String deviceId, String messageId, String status) {
		
		if(GCMNotificationStatus.ACCEPTED.getType().equalsIgnoreCase(status) ||
				GCMNotificationStatus.NOT_DELIVERED.getType().equalsIgnoreCase(status) ||
					GCMNotificationStatus.REJECTED.getType().equalsIgnoreCase(status) ||
						GCMNotificationStatus.DELIVERED.getType().equalsIgnoreCase(status) ) {
			
			String registrationId = getRegistrationIdFromDeviceId(deviceId);
			if(null == registrationId) {
				return;
			}
			MessageDetailsDb messageDetails = messageDetailsDao.getMessageDetailsByIds(registrationId, messageId);
			
			if(null != messageDetails) {
				messageDetails.setStatus(status);
				messageDetails.setUpdatedDate(new Date());
				messageDetailsDao.updateMessageDetails(messageDetails);
				logger.warn("Successfully updated status - {} for messageId - {}", status, messageId);
			}
			
		} else {
			logger.warn("Invalid status - {} sent for messageId - {}", status, messageId);
		}
	}
	
	
	@Async
	@Override
	public Boolean notifyOnBadgeEarned(Long userId, List<Badge> badges) throws IOException {
		
		DeviceDb deviceDb = userDao.getDeviceInfoByUserId(userId);
		 
		if(deviceDb != null){
			BadgeEarnedNotification msg = new BadgeEarnedNotification();
			Long id = System.currentTimeMillis();
			msg.setId("badge_"+ userId.toString()+"_"+id.toString());
			Long localId = System.nanoTime();
			msg.setLocalId(localId.toString().substring(0, 5));
			msg.setType("BADGE");
			
			msg.setEarnedBadge(badges);
			
			List<String> deviceList = new ArrayList<String>(1);
			deviceList.add(deviceDb.getRegistrationId());
			Boolean status = gcmClient.sendBadgeEarnedNotification(msg, deviceList);
			return status;
		}
		return false;
	}
	
	@Async
	@Override
	public Boolean notifyVideoGeneration(String tripId, String ytId) throws IOException {
		
		String deviceId = userDao.getUserByTripId(Long.parseLong(tripId));
		TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
		NotificationDTO msg = new NotificationDTO();
		Long id = System.currentTimeMillis();
		msg.setId("video"+id.toString());
		msg.setLocalId(tripId);
		msg.setType("VIDEO");
		
		if(null != tripDb) {
			msg.setTripId(tripDb.getId().toString());
			msg.setTripIdNew(tripDb.getTripPublicId().toString());
			msg.setTripName(tripDb.getTripName());
			msg.setTripCoverUri(tripDb.getTripCoverUri());
		}
		List<String> deviceList = new ArrayList<String>(1);
		deviceList.add(deviceId);
		Boolean status = gcmClient.sendVideoGenerationNotification(msg, deviceList);
		return status;
	}
	
	@Async
	@Override
	public void sendTripLikeNotification(String tripId, String fromUserId) throws IOException{
		
		String deviceId = userDao.getUserByTripId(Long.parseLong(tripId));
		PersonDb fromUser = userDao.getPersonByUserId(fromUserId);
		TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
		
		LikeNotificationDTO msg = new LikeNotificationDTO();
		Long id = System.currentTimeMillis();
		msg.setId("Like"+id.toString());
		
		Long localId = System.nanoTime();
		msg.setLocalId(localId.toString().substring(0, 5));
		
		if(null != tripDb) {
			msg.setTripId(tripDb.getId().toString());
			msg.setTripIdNew(tripDb.getTripPublicId().toString());
			msg.setTripName(tripDb.getTripName());
		}

		if(null != deviceId && null !=fromUser) {
			String fromUserName = fromUser.getName();
			msg.setLikedByUser(fromUserName);
			List<String> deviceList = new ArrayList<String>(1);
			deviceList.add(deviceId);
			gcmClient.sendTripLikeNotification(msg, deviceList);
		}
	}

	@Async
	@Override
	public void sendTripMediaCommentNotification(Long tripId, Long userId, String mediaId) {
		// TODO Auto-generated method stub
		logger.warn("Sending trip Media  comment notification for  trip {} user {} media {} ", tripId.toString(), userId.toString(), mediaId);
		TripEntityMediaDb tripEntityMediaDb = tripMediaDAO.getTripMediaEntity(mediaId);
		if(tripEntityMediaDb == null){
			return ;
		}
		try {
			sendTripMediaLikeNotification(tripId, userId, mediaId, tripEntityMediaDb.getMediaPath(), ClientMessageType.PHOTO_COMMENT);
		} catch (IOException e) {
			logger.error("Error occured while sending trip media comment notification for trip id {} media id {} from user id {}",
					tripId, mediaId, userId);
		}
	}
	
	@Async
	@Override
	public void sendTripMediaLikeNotification(Long tripId, Long fromUserId, String mediaId, String path, ClientMessageType type) throws IOException{
		
		DeviceDb deviceDb = userDao.getUserDeviceByTripId(tripId);
		
		//Lets not send photo like and photo comment notification to version below environment.getProperty("applicationVersionIsRegeistredAboveVersion"
		// Reason these notifications are not being  handled there
		if(type == ClientMessageType.PHOTO_COMMENT || type == ClientMessageType.PHOTO_LIKE){//
			Integer photoLikesAndCommentVersion = environment.getProperty("applicationVersionIsRegistredAboveVersion", Integer.class);
			if(StringUtils.isEmpty(deviceDb.getApplicationVersion()) || Integer.parseInt(deviceDb.getApplicationVersion()) < photoLikesAndCommentVersion ){
				return;
			}
		}
		
		PersonDb fromUser = userDao.getPersonByUserId(fromUserId+"");
		TripDb tripDb = tripDao.getTripById(tripId);
		//TripEntityMediaDb tripEntityMediaDb = tripMediaDAO.getTripMediaEntity(mediaId);
		
		/*if(tripEntityMediaDb == null ){
			return;
		}*/
		PhotoLikeNotificationDTO msg = new PhotoLikeNotificationDTO();
		//Long id = System.currentTimeMillis();
		
		if(type == ClientMessageType.PHOTO_LIKE){
			msg.setId("Like_Photo"+mediaId);
		}else if (type == ClientMessageType.PHOTO_COMMENT){
			msg.setId("Comment_Photo"+mediaId);
		}
		
		Long localId = System.nanoTime();
		msg.setLocalId(localId.toString().substring(0, 5));
		
		
		if(null != tripDb) {
			msg.setTripId(tripDb.getId().toString());
			msg.setTripIdNew(tripDb.getTripPublicId().toString());
			msg.setTripName(tripDb.getTripName());
		}
		
		//Specially for photo
		msg.setUrl(path);
		msg.setPhotoId(mediaId);
		msg.setLikedByUserId(fromUserId);
		msg.setClientMessageType(type);
		
		if(null != deviceDb.getRegistrationId() && null !=fromUser) {
			String fromUserName = fromUser.getName();
			msg.setLikedByUser(fromUserName);
			List<String> deviceList = new ArrayList<String>(1);
			deviceList.add(deviceDb.getRegistrationId());
			gcmClient.sendTripLikeNotification(msg, deviceList);
		}
	}
	
	@Async
	@Override
	public void sendTripCommentNotification(String tripId, String fromUserId, Set<Long> userIds) throws IOException{
		
		String deviceId = userDao.getUserByTripId(Long.parseLong(tripId));
		PersonDb fromUser = userDao.getPersonByUserId(fromUserId);
		TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
		
		Set<String> deviceSet = new HashSet<String>();
		for(Long userId : userIds) {
			String dynamicStr = userDao.getUserDeviceByUserId(userId);
			if(null != dynamicStr) {
				deviceSet.add(dynamicStr);
			}
		}
		
		CommentNotificationDTO msg = new CommentNotificationDTO();
		Long id = System.currentTimeMillis();
		msg.setId("Share"+id.toString());
		
		Long localId = System.nanoTime();
		msg.setLocalId(localId.toString().substring(0, 5));
		
		if(null != tripDb) {
			msg.setTripId(tripDb.getId().toString());
			msg.setTripIdNew(tripDb.getTripPublicId().toString());
			msg.setTripName(tripDb.getTripName());
		}
		
		
		if(null != deviceSet && null !=fromUser) {
			String fromUserName = fromUser.getName();
			msg.setCommentByUser(fromUserName);
			
			for(String deviceIdObj : deviceSet) {
				List<String> regList = new ArrayList<String>(1);
				regList.add(deviceIdObj);
				gcmClient.sendTripCommentNotification(msg, regList);
			}
			
		}
	}
	
	@Async
	@Override
	public void sendTripShareNotification(String tripId, String fromUserId) throws IOException{
		
		String deviceId = userDao.getUserByTripId(Long.parseLong(tripId));
		PersonDb fromUser = userDao.getPersonByUserId(fromUserId);
		TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
		
		ShareNotificationDTO msg = new ShareNotificationDTO();
		Long id = System.currentTimeMillis();
		msg.setId("Share"+id.toString());
		
		Long localId = System.nanoTime();
		msg.setLocalId(localId.toString().substring(0, 5));
		
		if(null != tripDb) {
			msg.setTripId(tripDb.getId().toString());
			msg.setTripName(tripDb.getTripName());
			msg.setTripIdNew(tripDb.getTripPublicId().toString());
		}
		
		
		if(null != deviceId && null !=fromUser) {
			String fromUserName = fromUser.getName();
			msg.setShareByUser(fromUserName);
			List<String> deviceList = new ArrayList<String>(1);
			deviceList.add(deviceId);
			gcmClient.sendTripShareNotification(msg, deviceList);
		}
		
	}
	
	
	
	@Override
	@Async
	public Boolean sendUserFollowNotification(String fromUserId, String toUserId) throws IOException{
		
		//String deviceId = userDao.getUserDeviceByUserId(Long.parseLong(toUserId));
		DeviceDb deviceDb = userDao.getUserDeviceByTripId(Long.parseLong(toUserId));
		
		//Lets not send follow follower notification to version below environment.getProperty("applicationVersionIsRegeistredAboveVersion"
		// Reason these notifications are not being  handled there
		
		Integer photoLikesAndCommentVersion = environment.getProperty("applicationVersionIsRegistredAboveVersion", Integer.class); 
		if(StringUtils.isEmpty(deviceDb.getApplicationVersion()) || Integer.parseInt(deviceDb.getApplicationVersion()) < photoLikesAndCommentVersion){
			return false;
		}
		PersonDb fromUser = userDao.getPersonByUserId(fromUserId);
		
		
		FollowNotificationDTO msg = new FollowNotificationDTO();
		Long id = System.currentTimeMillis();
		msg.setId("Follow"+id.toString());
		
		Long localId = System.nanoTime();
		msg.setLocalId(localId.toString().substring(0, 5));
		
		if(null != deviceDb.getRegistrationId() && null != fromUser) {
			String fromUserName = fromUser.getName();
			msg.setFollowByUser(fromUserName);
			msg.setFollowByUserId(fromUserId);
			List<String> deviceList = new ArrayList<String>(1);
			deviceList.add(deviceDb.getRegistrationId());
			Boolean status = gcmClient.sendUserFollowNotification(msg, deviceList);
			return status;
		}
		return false;
	}
	
	@Async
	@Override
	public Boolean sendTripReminderNotification(String tripId) throws IOException{
		
		String deviceId = userDao.getUserByTripId(Long.parseLong(tripId));
		TripDb tripDb = tripDao.getTripById(Long.parseLong(tripId));
		
		NotificationDTO msg = new NotificationDTO();
		Long id = System.currentTimeMillis();
		msg.setId("Trip"+id.toString());
		msg.setLocalId(tripId);
		msg.setType("TRIP_REMINDER");
		
		if(null != tripDb) {
			msg.setTripId(tripDb.getId().toString());
			msg.setTripIdNew(tripDb.getTripPublicId().toString());
			msg.setTripName(tripDb.getTripName());
			msg.setTripCoverUri(tripDb.getTripCoverUri());
		}

		List<String> deviceList = new ArrayList<String>(1);
		deviceList.add(deviceId);
		Boolean status = gcmClient.sendTripReminderNotification(msg, deviceList);
		return status;
	}
	
	
	

	@Async
	@Override
	public Boolean sendWebLinkAsNotification(String tripId, String webUrl, String imageUrl, String title, String message) throws IOException{
		
		//String deviceId = "3c8fe7c2c291a19b";
		String deviceId = userDao.getUserByTripId(Long.parseLong(tripId));
				
		NotificationDTO msg = new NotificationDTO();
		Long id = System.currentTimeMillis();
		msg.setId("Web"+id.toString());
		msg.setLocalId(id.toString().substring(id.toString().length()-6, id.toString().length()-1));
		
		msg.setTripId("123456");//Dummy
		msg.setTripIdNew("123456");//Dummy
		msg.setTripName("Trip");//Dummy
		msg.setTripCoverUri(imageUrl);
		msg.setPageUrl(webUrl);
		msg.setName(title);
		msg.setPunchline(message);
		

		List<String> deviceList = new ArrayList<String>(1);
		deviceList.add(deviceId);
		Boolean status = gcmClient.sendWebLinkAsNotification(msg, deviceList);
		return status;
	}


}

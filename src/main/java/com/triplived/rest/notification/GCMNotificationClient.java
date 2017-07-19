package com.triplived.rest.notification;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.BadgeEarnedNotification;
import com.connectme.domain.triplived.dto.CommentNotificationDTO;
import com.connectme.domain.triplived.dto.FollowNotificationDTO;
import com.connectme.domain.triplived.dto.LikeNotificationDTO;
import com.connectme.domain.triplived.dto.NotificationDTO;
import com.connectme.domain.triplived.dto.PhotoLikeNotificationDTO;
import com.connectme.domain.triplived.dto.ShareNotificationDTO;
import com.gcm.ClientMessage;
import com.gcm.ClientMessageType;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import com.triplived.dao.device.DeviceDao;
import com.triplived.dao.messageDetails.MessageDetailsDao;
import com.triplived.entity.MessageDetailsDb;

/**
 * Class used to fire GCM messages
 * @author santosh
 *
 */

@Component
public class GCMNotificationClient {
	
	
	final static String GOOGLE_API_KEY ="AIzaSyDyjJQ68kuPg5-AcjjlscdquN_tygNFzbM";
	
	private static final Logger logger = LoggerFactory.getLogger(GCMNotificationClient.class );
	
	@Autowired
	private MessageDetailsDao messageDetailsDao;
	
	@Autowired
	private DeviceDao deviceDao;
	
	
	@Transactional
	public Boolean sendTriviaNotificationMulticast(NotificationDTO message, List<String> devicesList) throws IOException, Exception {
		
		String type = message.getType();
		if(null != message && null == message.getPunchline()) {
			if(type.equalsIgnoreCase("attraction")) {
				return null;
			}
			if(type.equalsIgnoreCase("city")) {
				message.setPunchline("Welcome to your destination");
			}
		}
		
		message.setMessageType("TRIVIA");
		checkIfAlreadySentInADay(message, devicesList);
		
		ClientMessage clientMessage = new ClientMessage();
        clientMessage.setType(ClientMessageType.ENTITY);
        clientMessage.setTitle(message.getName());
        clientMessage.setBody(message.getPunchline());
        
        
        boolean status = sendMessage(clientMessage, devicesList, message);
        if(status) {
        	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
        }else {
        	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
        }
        return status;
        
        
	}
	
	@Transactional
	public Boolean sendTrendingTripNotificationMulticast(NotificationDTO message, List<String> devicesList) throws IOException, Exception {
		
		ClientMessage clientMessage = new ClientMessage();
        clientMessage.setType(ClientMessageType.TRIP_VIEW);
        clientMessage.setTitle(message.getName());
        clientMessage.setImageUrl(message.getTripCoverUri());
        clientMessage.setBody(message.getPunchline());
        
        
        boolean status = sendMessage(clientMessage, devicesList, message);
        if(status) {
        	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
        }else {
        	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
        }
        return status;
        
        
	}
	
	
	@Transactional	
     public Boolean sendReviewNotificationMulticast(NotificationDTO message, List<String> devicesList) throws IOException {
		
		ClientMessage clientMessage = new ClientMessage();
        clientMessage.setType(ClientMessageType.TIMELINE);
        if(null != message.getName()) {
         clientMessage.setTitle(message.getName());
        }else {
        	if(message.getType().equalsIgnoreCase("other")) {
        		clientMessage.setTitle("Triplived");
        	}else {
        		clientMessage.setTitle(message.getType());
        	}
        }
        
        Map<String, String> actionMap = new HashMap<String,String>();
        
        if(message.getType().equalsIgnoreCase("Restaurant")) {
        	clientMessage.setBody("Enjoying your meal? Tell us more about it !!");
        	actionMap.put("affirmative", "Add to timeline");
        	actionMap.put("negative", "Not now");
        }else if(message.getType().equalsIgnoreCase("Hotel")) {
        	clientMessage.setBody("Hope you're having a pleasant stay. Please take a minute to tell us more about it !!");
        	actionMap.put("affirmative", "Add to timeline");
        	actionMap.put("negative", "Not now");
        }else if(message.getType().equalsIgnoreCase("other")) {
        	clientMessage.setBody("Your timeline has captured new locations. Click to know more about them !!");
        	actionMap.put("affirmative", "Open timeline");
        	actionMap.put("negative", "Not now");
        }else {
        	clientMessage.setBody("Having a great time? Share your experience !!");
        	actionMap.put("affirmative", "Add to timeline");
        	actionMap.put("negative", "Not now");
        }
        
        clientMessage.setNotificationActionMap(actionMap);
        message.setMessageType("REVIEW");
        boolean status = sendMessage(clientMessage, devicesList, message);
        if(status) {
        	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
        }else {
        	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
        }
        return status;
	}
	
	
	@Transactional	
    public Boolean sendVideoGenerationNotification(NotificationDTO message, List<String> devicesList) throws IOException {
		
		ClientMessage clientMessage = new ClientMessage();
       clientMessage.setType(ClientMessageType.TIMELINE_VIDEO);
       clientMessage.setTitle("TripLived Video Ready !!");
       clientMessage.setImageUrl(message.getTripCoverUri());
       
       	clientMessage.setBody("Your trip video is ready. Click to view !!");
         //message.setMessageType("VIDEO");
       boolean status = sendMessage(clientMessage, devicesList, message);
       if(status) {
       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }else {
       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }
       return status;
	}
	
	@Transactional	
    public Boolean sendBadgeEarnedNotification(BadgeEarnedNotification message, List<String> devicesList) throws IOException {
		
		ClientMessage clientMessage = new ClientMessage();
        clientMessage.setType(ClientMessageType.BADGE_EARNED);
        clientMessage.setTitle("Congratulations!! You earned some badges.");
        
        if(message.getEarnedBadge().size() > 1){
        	clientMessage.setBody("You earned "+message.getEarnedBadge().size()+"  new badges!!");
        }else{
        	clientMessage.setBody("You earned a "+message.getEarnedBadge().get(0).getName() +" badge!!");
        }
       	
         //message.setMessageType("VIDEO");
        boolean status = sendMessage(clientMessage, devicesList, message);
       if(status) {
       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }else {
       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }
       return status;
	}
	
	
	@Transactional	
    public Boolean sendTripLikeNotification(LikeNotificationDTO notificationDTO, List<String> devicesList) throws IOException {
		
	   ClientMessage clientMessage = new ClientMessage();
      
	   if(notificationDTO instanceof PhotoLikeNotificationDTO){
		  
		   if(((PhotoLikeNotificationDTO) notificationDTO).getClientMessageType() != null &&  
				   ((PhotoLikeNotificationDTO) notificationDTO ).getClientMessageType() == ClientMessageType.PHOTO_LIKE){
			   clientMessage.setType(ClientMessageType.PHOTO_LIKE);
		       clientMessage.setTitle("Your photo has been liked!");
		       clientMessage.setBody(notificationDTO.getLikedByUser() + " liked your photo - " + notificationDTO.getTripName());
		   }else{
			   
			   clientMessage.setType(ClientMessageType.PHOTO_COMMENT);
		       clientMessage.setTitle("Your photo got a comment !");
		       clientMessage.setBody(notificationDTO.getLikedByUser() + " commented on your photo - " + notificationDTO.getTripName());
		   }
	       clientMessage.setImageUrl(((PhotoLikeNotificationDTO) notificationDTO ).getUrl()); 
	       
	   }else{
		   
		   clientMessage.setType(ClientMessageType.LIKE);
	       clientMessage.setTitle("Your trip has been liked !!");
	       clientMessage.setBody(notificationDTO.getLikedByUser() + " liked your trip - " + notificationDTO.getTripName());
	   }

       boolean success = false;
   	
   	   Sender sender = new  Sender(GOOGLE_API_KEY);
   	   Long messageId = System.nanoTime();
   	
   	    Message.Builder messageBuilder = new Message.Builder()
       .collapseKey("6")
       .timeToLive(3000) // time in second
       .delayWhileIdle(true)  //wait for device to become active
       .addData("message", new Gson().toJson(clientMessage))
       .addData("id", messageId.toString())
       .addData("entityId", notificationDTO.getId())
       .addData("localId", notificationDTO.getLocalId())
       .addData("tripId", notificationDTO.getTripId())
       .addData("tripIdNew", notificationDTO.getTripIdNew())
       .addData("tripName", notificationDTO.getTripName())
       .addData("likedByUser", notificationDTO.getLikedByUser());
   	    
   	    if(notificationDTO instanceof PhotoLikeNotificationDTO){
   	    	messageBuilder.addData("mediaId", ((PhotoLikeNotificationDTO) notificationDTO ).getPhotoId());
   	    	messageBuilder.addData("userId", notificationDTO.getLikedByUserId()+"");
   	    	messageBuilder.addData("userName", notificationDTO.getLikedByUser());
   	    	//messageBuilder.addData("mediaPath", ((PhotoLikeNotificationDTO) notificationDTO ).getUrl());   	    	
   	    }
      
   	    Message message = messageBuilder.build();

	   	if(logMessageDetails(devicesList, messageId.toString(), notificationDTO, message.toString())) {
	   		MulticastResult result = sender.send(message, devicesList, 1);
	   		logger.warn("Messages sent to GCM servers. Result is: {} for device ids {}", result.toString(), Arrays.asList(devicesList));
	   		if (result.getResults() != null) {
	   		    int canonicalRegId = result.getCanonicalIds();
	   		    if (canonicalRegId != 0) {
	   		     }
	   		} else {
	   		    int error = result.getFailure();
	   		    logger.error("Error encountered while sending messages to GCM servers. Error is: {}", result.getFailure());
	   		}
	   	    
	   		if(result.getSuccess() == devicesList.size()) {
	   			success = true;
	   		}else {
	   			success = false;
	   			logger.error("Error encountered while sending messages to GCM servers. Error is: {} for devices {}", result.toString(), Arrays.asList(devicesList));
	   		}
	   		handleMessageDeliveries(result, devicesList);
	   		logger.warn("Total success messages: {} and total failure messages: {} for devices: {}", result.getSuccess(), result.getFailure(), Arrays.asList(devicesList));
	   	}

       if(success) {
       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }else {
       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }
       return success;
	}
	
	
	@Transactional	
    public Boolean sendTripCommentNotification(CommentNotificationDTO notificationDTO, List<String> devicesList) throws IOException {
		
	   ClientMessage clientMessage = new ClientMessage();
       clientMessage.setType(ClientMessageType.COMMENT);
       clientMessage.setTitle("A new comment on trip - " + notificationDTO.getTripName());
       //clientMessage.setImageUrl(notificationDTO.getTripCoverUri());
       
       clientMessage.setBody(notificationDTO.getCommentByUser() + " has added a comment on trip - " + notificationDTO.getTripName());
        
       boolean success = false;
   	
   	   Sender sender = new  Sender(GOOGLE_API_KEY);
   	   Long messageId = System.nanoTime();
   	
   	    Message message = new Message.Builder()
       .collapseKey("6")
       .timeToLive(3000) // time in second
       .delayWhileIdle(true)  //wait for device to become active
       .addData("message", new Gson().toJson(clientMessage))
       .addData("id", messageId.toString())
       .addData("entityId", notificationDTO.getId())
       .addData("localId", notificationDTO.getLocalId())
       .addData("tripId", notificationDTO.getTripId())
       .addData("tripIdNew", notificationDTO.getTripIdNew())
       .addData("tripName", notificationDTO.getTripName())
       .addData("commentByUser", notificationDTO.getCommentByUser())
       .build();

	   	if(logMessageDetails(devicesList, messageId.toString(), notificationDTO, message.toString())) {
	   		MulticastResult result = sender.send(message, devicesList, 1);
	   		logger.warn("Messages sent to GCM servers. Result is: {} for device ids {}", result.toString(), Arrays.asList(devicesList));
	   		if (result.getResults() != null) {
	   		    int canonicalRegId = result.getCanonicalIds();
	   		    if (canonicalRegId != 0) {
	   		     }
	   		} else {
	   		    int error = result.getFailure();
	   		    logger.error("Error encountered while sending messages to GCM servers. Error is: {}", result.getFailure());
	   		}
	   	    
	   		if(result.getSuccess() == devicesList.size()) {
	   			success = true;
	   		}else {
	   			success = false;
	   			logger.error("Error encountered while sending messages to GCM servers. Error is: {} for devices {}", result.toString(), Arrays.asList(devicesList));
	   		}
	   		logger.warn("Total success messages: {} and total failure messages: {} for devices: {}", result.getSuccess(), result.getFailure(), Arrays.asList(devicesList));
	   	}

       if(success) {
       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }else {
       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }
       return success;
	}
	
	
	@Transactional	
    public Boolean sendTripShareNotification(ShareNotificationDTO notificationDTO, List<String> devicesList) throws IOException {
		
	   ClientMessage clientMessage = new ClientMessage();
       clientMessage.setType(ClientMessageType.SHARE);
       clientMessage.setTitle("Your trip has been shared - " + notificationDTO.getTripName());
       //clientMessage.setImageUrl(notificationDTO.getTripCoverUri());
       
       clientMessage.setBody(notificationDTO.getShareByUser() + " has shared your trip - " + notificationDTO.getTripName());
        
       boolean success = false;
   	
   	   Sender sender = new  Sender(GOOGLE_API_KEY);
   	   Long messageId = System.nanoTime();
   	
   	    Message message = new Message.Builder()
       .collapseKey("6")
       .timeToLive(3000) // time in second
       .delayWhileIdle(true)  //wait for device to become active
       .addData("message", new Gson().toJson(clientMessage))
       .addData("id", messageId.toString())
       .addData("entityId", notificationDTO.getId())
       .addData("localId", notificationDTO.getLocalId())
       .addData("tripId", notificationDTO.getTripId())
       .addData("tripIdNew", notificationDTO.getTripIdNew())
       .addData("tripName", notificationDTO.getTripName())
       .addData("shareByUser", notificationDTO.getShareByUser())
       .build();

	   	if(logMessageDetails(devicesList, messageId.toString(), notificationDTO, message.toString())) {
	   		MulticastResult result = sender.send(message, devicesList, 1);
	   		logger.warn("Messages sent to GCM servers. Result is: {} for device ids {}", result.toString(), Arrays.asList(devicesList));
	   		if (result.getResults() != null) {
	   		    int canonicalRegId = result.getCanonicalIds();
	   		    if (canonicalRegId != 0) {
	   		     }
	   		} else {
	   		    int error = result.getFailure();
	   		    logger.error("Error encountered while sending messages to GCM servers. Error is: {}", result.getFailure());
	   		}
	   	    
	   		if(result.getSuccess() == devicesList.size()) {
	   			success = true;
	   		}else {
	   			success = false;
	   			logger.error("Error encountered while sending messages to GCM servers. Error is: {} for devices {}", result.toString(), Arrays.asList(devicesList));
	   		}
	   		logger.warn("Total success messages: {} and total failure messages: {} for devices: {}", result.getSuccess(), result.getFailure(), Arrays.asList(devicesList));
	   	}

       if(success) {
       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }else {
       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }
       return success;
	}
	
	/**
	 * This method is used for sending reminder notifications to people whose trip are in ongoing state.
	 * @param notificationDTO
	 * @param devicesList
	 * @return
	 * @throws IOException
	 */
	@Transactional	
    public Boolean sendTripReminderNotification(NotificationDTO message, List<String> devicesList) throws IOException {
		
			
		   ClientMessage clientMessage = new ClientMessage();
	       clientMessage.setType(ClientMessageType.TIMELINE);
	       clientMessage.setTitle("Hope you are having a great time.");
	       //clientMessage.setImageUrl(message.getTripCoverUri());
	       
	       clientMessage.setBody("Take a look at how your timeline is shaping up - " + message.getTripName());
	        
	       boolean status = sendMessage(clientMessage, devicesList, message);
	       if(status) {
	       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
	       }else {
	       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
	       }
	       return status;
	}
	
	
	/**
	 * This method is used for sending web links as notifications ex: blog.
	 * @param notificationDTO
	 * @param devicesList
	 * @return
	 * @throws IOException
	 */
	@Transactional	
    public Boolean sendWebLinkAsNotification(NotificationDTO message, List<String> devicesList) throws IOException {
		
			
		   ClientMessage clientMessage = new ClientMessage();
	       clientMessage.setType(ClientMessageType.WEB_VIEW);
	       clientMessage.setTitle(message.getName());
	       clientMessage.setImageUrl(message.getTripCoverUri());
	       clientMessage.setPageUrl(message.getPageUrl());
	       clientMessage.setBody(message.getPunchline());
	        
	       boolean status = sendMessage(clientMessage, devicesList, message);
	       if(status) {
	       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
	       }else {
	       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
	       }
	       return status;
	}

	
	@Transactional	
    public Boolean sendUserFollowNotification(FollowNotificationDTO notificationDTO, List<String> devicesList) throws IOException {
		
	   ClientMessage clientMessage = new ClientMessage();
       clientMessage.setType(ClientMessageType.FOLLOW);
       clientMessage.setTitle("Triplived");
       //clientMessage.setImageUrl(notificationDTO.getTripCoverUri());
       
       clientMessage.setBody(notificationDTO.getFollowByUser() + " is now following you!!");
        
       boolean success = false;
   	
   	   Sender sender = new  Sender(GOOGLE_API_KEY);
   	   Long messageId = System.nanoTime();
   	
   	    Message message = new Message.Builder()
       .collapseKey("6")
       .timeToLive(3000) // time in second
       .delayWhileIdle(true)  //wait for device to become active
       .addData("message", new Gson().toJson(clientMessage))
       .addData("id", messageId.toString())
       .addData("entityId", notificationDTO.getId())
       .addData("localId", notificationDTO.getLocalId())
       .addData("userName", notificationDTO.getFollowByUser())
       .addData("userId", notificationDTO.getFollowByUserId())
       .build();

	   	if(logMessageDetails(devicesList, messageId.toString(), notificationDTO, message.toString())) {
	   		MulticastResult result = sender.send(message, devicesList, 1);
	   		logger.warn("Messages sent to GCM servers. Result is: {} for device ids {}", result.toString(), Arrays.asList(devicesList));
	   		if (result.getResults() != null) {
	   		    int canonicalRegId = result.getCanonicalIds();
	   		    if (canonicalRegId != 0) {
	   		     }
	   		} else {
	   		    int error = result.getFailure();
	   		    logger.error("Error encountered while sending messages to GCM servers. Error is: {}", result.getFailure());
	   		}
	   	    
	   		if(result.getSuccess() == devicesList.size()) {
	   			success = true;
	   		}else {
	   			success = false;
	   			logger.error("Error encountered while sending messages to GCM servers. Error is: {} for devices {}", result.toString(), Arrays.asList(devicesList));
	   		}
	   		logger.warn("Total success messages: {} and total failure messages: {} for devices: {}", result.getSuccess(), result.getFailure(), Arrays.asList(devicesList));
	   		
	   		handleMessageDeliveries(result, devicesList);
	   	}

       if(success) {
       	logger.warn("All messages successfully delivered to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }else {
       	logger.warn("Error while sending messages to client - {} . Notification message is : {}", Arrays.asList(devicesList), message.toString());
       }
       return success;
	}

	
    private Boolean sendMessage(ClientMessage clientMsg, List<String> devicesList, NotificationDTO notificationDTO) throws IOException {
    	
    	boolean success = false;
    	
    	Sender sender = new  Sender(GOOGLE_API_KEY);
    	Long messageId = System.nanoTime();
    	
    	Message message = new Message.Builder()
        .collapseKey("6")
        .timeToLive(3000) // time in second
        .delayWhileIdle(true)  //wait for device to become active
        .addData("message", new Gson().toJson(clientMsg))
        .addData("id", messageId.toString())
        .addData("entityId", notificationDTO.getId())
        .addData("entityType", notificationDTO.getType())
        .addData("localId", notificationDTO.getLocalId())
        .addData("tripId", notificationDTO.getTripId())
        .addData("tripIdNew", notificationDTO.getTripIdNew())
        .addData("tripName", notificationDTO.getTripName())
        .addData("pageUrl", notificationDTO.getPageUrl())
        .build();

    	if(logMessageDetails(devicesList, messageId.toString(), notificationDTO, message.toString())) {
    		MulticastResult result = sender.send(message, devicesList, 1);
    		logger.warn("Messages sent to GCM servers. Result is: {} for device ids {}", result.toString(), Arrays.asList(devicesList));
    		if (result.getResults() != null) {
    		    int canonicalRegId = result.getCanonicalIds();
    		    if (canonicalRegId != 0) {
    		     }
    		} else {
    		    int error = result.getFailure();
    		    logger.error("Error encountered while sending messages to GCM servers. Error is: {}", result.getFailure());
    		}
    	    
    		if(result.getSuccess() == devicesList.size()) {
    			success = true;
    		}else {
    			success = false;
    			logger.error("Error encountered while sending messages to GCM servers. Error is: {} for devices {}", result.toString(), Arrays.asList(devicesList));
    		}
    		logger.warn("Total success messages: {} and total failure messages: {} for devices: {}", result.getSuccess(), result.getFailure(), Arrays.asList(devicesList));
    		handleMessageDeliveries(result, devicesList);
    	}
    	
    	
    	return success;
    }
    
    
    private boolean logMessageDetails(List<String> devicesList, String messageId, NotificationDTO notificationDTO, String messageContent){
    	
    	boolean success = true;
    	
    	try {
	    	for(String str : devicesList) {
		    	MessageDetailsDb msg = new MessageDetailsDb(messageId, str);
		    	msg.setEntityId(notificationDTO.getId());
		    	msg.setMessageType(notificationDTO.getMessageType());
		    	msg.setMessageContent(messageContent);
		    	MessageDetailsDb msgDb = messageDetailsDao.updateMessageDetails(msg);
		    	logger.warn("Message successfully saved in db for device {} for name {} for type {}", str, notificationDTO.getName(), notificationDTO.getType());
	    	}
    	}catch (Exception e) {
    		logger.error("Exception while saving message in db for device {} for name {} for type {}", Arrays.asList(devicesList), notificationDTO.getName(), notificationDTO.getType());
    		success = false;
    	}
    	
    	return success;
    }
    
    private void checkIfAlreadySentInADay(NotificationDTO notificationDTO, List<String> devices) throws ParseException{
    	
    	Iterator<String> iter = devices.iterator();
        
        while(iter.hasNext()) {
        	String device = iter.next().toString();
	    	if(notificationDTO.getType().equalsIgnoreCase("attraction")) {
				boolean status = messageDetailsDao.checkIfMessageExists(notificationDTO.getMessageType(), notificationDTO.getId(), device, new Date());
				if(status) {
					logger.warn("Removing device from sending message list as a message has already been sent to it in previous 24 hours for device {} for id {} for messageType {}", device, notificationDTO.getId(), notificationDTO.getMessageType());
					iter.remove();
				}
			}
        }
    	
    }
   
    /**
     * https://developers.google.com/cloud-messaging/http#request
     * 
     * Request (IDs 4, 8, 15, 16, 23, and 42 respectively) 
      { "multicast_id": 216,
		  "success": 3,
		  "failure": 3,
		  "canonical_ids": 1,
		  "results": [
		    { "message_id": "1:0408" },
		    { "error": "Unavailable" },
		    { "error": "InvalidRegistration" },
		    { "message_id": "1:1516" },
		    { "message_id": "1:2342", "registration_id": "32" },
		    { "error": "NotRegistered"}
		  ]
		}
		In this example:
		
		First message: success, not required.
		Second message: should be resent (to registration token 8).
		Third message: had an unrecoverable error (maybe the value got corrupted in the database).
		Fourth message: success, nothing required.
		Fifth message: success, but the registration token should be updated in the server database (from 23 to 32).
		Sixth message: registration token (42) should be removed from the server database because the application was uninstalled from the device.
		
 *		<pre>
 *   		- Call {@link #getMessageId()}:
 *     		- {@literal null} means error, call {@link #getErrorCodeName()}
 *     		- non-{@literal null} means the message was created:
 *       	- Call {@link #getCanonicalRegistrationId()}
 *          - if it returns {@literal null}, do nothing.
 *          - otherwise, update the server datastore with the new id.
 *     </pre>
 * 
     * @param result
     * @param devices
     */
    public void handleMessageDeliveries(MulticastResult gcmResult, List<String> devices){

    	try{
    		
			if (CollectionUtils.isNotEmpty(gcmResult.getResults())) {

				List<String> unregistred = new ArrayList<String>();
				int i = 0;
				for (Result result : gcmResult.getResults()) {

					if (StringUtils.isEmpty(result.getMessageId())) {
						if (StringUtils.isNotEmpty(result.getErrorCodeName())) {

							switch (result.getErrorCodeName()) {

							case "NotRegistered":
								// USER uninstalled the APP;
								unregistred.add(devices.get(i));

								break;

							case "Unavailable":
								// RE-SEND message
								break;

							case "InvalidRegistration":

								break;
							default:

								break;
							}
						}
					}
					i++;
				}

				if (unregistred.size() > 0) {
					logger.warn("TOTAL {} device uninstalled ", unregistred.size());
					deviceDao.unregisterUserDevice(unregistred);
				}
			}

    	}catch(Exception ex){
    		logger.error("exception while updating unregistracion status ",ex);
    	}
		
    }


}

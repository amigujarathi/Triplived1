package com.triplived.controller.notification;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.connectme.domain.triplived.VideoStatus;
import com.connectme.domain.triplived.dto.NotificationDTO;
import com.triplived.controller.profile.Person;
import com.triplived.controller.video.VideoController;
import com.triplived.service.notification.GCMNotificationService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/notification")
@SessionAttributes(Constants.LOGIN_USER)
public class GCMNotificationController {


    private static final Logger logger = LoggerFactory.getLogger(GCMNotificationController.class );
    
    @Autowired
    private GCMNotificationService gcmService;
    
    @Autowired
    private VideoController videoController;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public String index(@ModelAttribute(Constants.LOGIN_USER) Person user) {
    	
    	if(user == null){
    		 //redirect to login
    		 return "index";
    	}
    	logger.warn("GCM Messaging Envoked");
    	return "OK";
	}
    
    
    @RequestMapping(value="/review", method=RequestMethod.GET)
    public @ResponseBody Boolean sendReviewNotificationToUser(@RequestParam(value = "deviceId") String deviceId,@RequestParam(value = "name", required=false) String name, 
    		@RequestParam(value="entityId", required=false) String entityId, @RequestParam(value="localId",required=false) String localId,  @RequestParam(value="type",required=false) String type) {
    	
    	if(null == type) {
    		type = "other";
    	}
    	NotificationDTO sourceNotificationDTO = new NotificationDTO();
    	sourceNotificationDTO.setId(entityId);
    	sourceNotificationDTO.setLocalId(localId);
    	sourceNotificationDTO.setType(type);
    	
    	if(null == name) {
    		sourceNotificationDTO.setName("TripLived");	
    	}else {
    		sourceNotificationDTO.setName(name);
    	}
    	
    	logger.warn("Processing request for sending review notifications to device - {} for entity - {} of type {}", deviceId, name, type);
    	try {
	    	
	    		if(type.equalsIgnoreCase("attraction")) {
	    			return gcmService.sendAttractionSpecificNotifications(deviceId, sourceNotificationDTO);
	    		}else if(type.equalsIgnoreCase("city" )) {
	    			return gcmService.sendCitySpecificNotifications(deviceId, sourceNotificationDTO);
	    		}else if(type.equalsIgnoreCase("hotel" )) {
	    			return gcmService.sendHotelSpecificNotifications(deviceId, sourceNotificationDTO);
	    		}else if(type.equalsIgnoreCase("restaurant" )) {
	    			return gcmService.sendRestaurantSpecificNotifications(deviceId, sourceNotificationDTO);
	    		}else if(type.equalsIgnoreCase("other" )) {
	    			return gcmService.sendUnknowEntityNotifications(deviceId, sourceNotificationDTO);
	    		}
	    		
	    	return true;
    	}catch(Exception e) {
    		logger.error("Error while sending notifications as request from device for deviceId: {} - {}", deviceId, e);
    		return false;
    	}
    	
      }
    
    /**
     * This method is limited to a device being near a particular attraction.
     * @param deviceId
     * @param latitude
     * @param longitude
     * @return
     */
    @RequestMapping(value="/trivia", method=RequestMethod.GET)
    public void sendTriviaNotificationToUser(@RequestParam("deviceId") String deviceId, 
    		 @RequestParam("lat") String lat, @RequestParam("lng") String lng, @RequestParam("accuracy") String accuracy) {
    	
    	try {
    		Double tmpAccuracy = (Double.parseDouble(accuracy) * 2.5) ;
			if(tmpAccuracy > 200) {
				tmpAccuracy = new Double(200);
			}
			
			//logger.warn("Processing request for sending trivia notifications to device - {}", deviceId);
			gcmService.sendTriviaNotifications(deviceId, lat, lng, "200");
    	}catch(Exception e) {
    		logger.error("Error while sending notifications as request from device for deviceId: {} - {}", deviceId, e);
    	}
    	
    }
    
    
    /**
     * This method is limited to a device being near a particular attraction.
     * @param deviceId
     * @param latitude
     * @param longitude
     * @return
     */
    @RequestMapping(value="/trending1", method=RequestMethod.GET)
    public void sendTrendingTripNotificationToUser(@RequestParam("id") String tripId, 
    		 @RequestParam("message") String message) {
    	
    	try {
    		
			//logger.warn("Processing request for sending trivia notifications to device - {}", deviceId);
			gcmService.sendTrendingTripNotifications(tripId,message);
    	}catch(Exception e) {
    		logger.error("Error while sending notifications as request from device - {}", e);
    	}
    	
    }
    
    
    /**
     * This is for sending web links. e.g. blog links
     * @param deviceId
     * @param latitude
     * @param longitude
     * @return
     */
    @RequestMapping(value="/webPage", method=RequestMethod.GET)
    public void sendWebLinkAsNotificationToUser(@RequestParam("id") String tripId, @RequestParam("webUrl") String webUrl, 
    		@RequestParam("imageUrl") String imageUrl, @RequestParam("title") String title, @RequestParam("message") String message) {
    	
    	try {
    		
			//logger.warn("Processing request for sending trivia notifications to device - {}", deviceId);
			gcmService.sendWebLinkAsNotification(tripId, "http://"+webUrl,"http://"+imageUrl, title, message);
    	}catch(Exception e) {
    		logger.error("Error while sending notifications as request from device - {}", e);
    	}
    	
    }
    
    @RequestMapping(value = "/update", method=RequestMethod.GET)
    public @ResponseBody String updateMessageStatus(@RequestParam(value = "deviceId") String deviceId,
    		@RequestParam(value = "messageId") String messageId, @RequestParam(value = "status") String status) {
           gcmService.updateMessageStatus(deviceId, messageId, status);	
           
           return "";
    }
    
    @RequestMapping(value = "/video", method=RequestMethod.GET)
    public @ResponseBody Boolean notifyVideoGeneration(@RequestParam(value = "tripId") String tripId,
    		@RequestParam(value = "ytId") String ytId) {
           try {
        	   logger.warn("Video generation received for tripId - {}, and Youtube id - {}", tripId, ytId);  
        	   videoController.publishVideoStatus(tripId, VideoStatus.NOTIFICATION_REQUEST_RECEIVED.toString());
			Boolean status = gcmService.notifyVideoGeneration(tripId, ytId);
			if(status) {
				videoController.publishVideoStatus(tripId, VideoStatus.NOTIFICATION_SENT.toString());
			}
			return status;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error while sending notification for tripd - {}, error is - {}", tripId, e.getStackTrace());
			
		}	
           return false;
    }
    
    
    
    
    
}

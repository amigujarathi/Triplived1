 package com.triplived.controller.social;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.FbImageDTO;
import com.connectme.domain.triplived.dto.FbPostDTO;
import com.triplived.entity.PersonDb;
import com.triplived.entity.UserDynamicDb;
import com.triplived.service.social.SocialService;
import com.triplived.service.trip.TripService;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/social")
 public class SocialController {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialController.class);
	
	@Autowired
	private SocialService socialService;
	
	@Autowired
	private TripService tripService;
	 
	@RequestMapping(value = "/fb/check-in", method = RequestMethod.POST)
	 public @ResponseBody String facebookCheckIn(Model model, HttpServletRequest request,
			 @RequestBody FbPostDTO fbPost) {
		  
		  if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(fbPost.getTripId());
			fbPost.setTripId(internalTripId);
		  }
		  
		  String deviceId = request.getHeader("UserDeviceId");
		  if(null == deviceId) {
			  logger.error("Data not posted to Facebook due to Null DeviceId");
			  return "Null DeviceId";
		  }
		  String placeName = fbPost.getPlaceName();
		  Double lat = fbPost.getLat();
		  Double lng = fbPost.getLng();
		  
		  String tripId = fbPost.getTripId().toString(); 
		  String activity = (String) fbPost.getAdditionalProperties().get("activity");
		  PersonDb person = socialService.getUserFbDetails(Long.parseLong(tripId));
		  
		  String userId = null, fbAccessToken = null;
		  if(null != person && null != person.getId()) {
			  userId = person.getId().toString();
		  }
		  if(null != person.getPersonId()) {
			  UserDynamicDb obj = socialService.getUserDeviceMappingDetails(person.getPersonId(), deviceId);
			  fbAccessToken = obj.getFbAccessToken();
		  }
		  
		  String emotion = fbPost.getEmotion();
		  String customMessage = fbPost.getCustomMessage();
		  
		  if(StringUtils.isNotEmpty(activity) && StringUtils.isNotEmpty(customMessage)){
			  customMessage += " - "+activity;
		  }else if(StringUtils.isNotEmpty(activity) && StringUtils.isEmpty(customMessage)){
			  customMessage = activity;
		  } 
		  
		  //FbImageDTO[] images = (FbImageDTO[]) fbPost.getFbImageDTO().toArray();
		  FbImageDTO[] images  ;
		  if(!CollectionUtils.isEmpty(fbPost.getFbImageDTO())) {
			  
			  String photoCaption = "";
			  if(StringUtils.isNotEmpty(customMessage)){
				  if(StringUtils.isNotEmpty(emotion)){
					  photoCaption  =  customMessage + " - Feeling " + emotion;
				  }else {
					  photoCaption = customMessage;
				  }
			  }else{
				  if(StringUtils.isNotEmpty(emotion)){
					  photoCaption  =  " Feeling " + emotion;
				  }
			  }
			  
			  images = new FbImageDTO[fbPost.getFbImageDTO().size()];
			  int i = 0;
			  for( FbImageDTO imageDTO : fbPost.getFbImageDTO() ){
				  imageDTO.setImageDesc(photoCaption);
				  images[i] = imageDTO;
				  i++;
			  }
			  
			//images = Arrays.copyOf(fbPost.getFbImageDTO().toArray(), fbPost.getFbImageDTO().size(), FbImageDTO[].class);
		  }else {
			  images = null;
		  }
		  
		if((null==userId) || (null==fbAccessToken)) {
			return "UserId or FbAccessToken is null";
		}
		Boolean response = false;
		
		if(StringUtils.isNotEmpty(placeName)) {
			String placeId = socialService.checkIfTimelineEntityMappedToFBEntity(placeName, lat, lng, fbAccessToken);
			
			if(null != placeId) {
				if(null != images) {
					logger.warn("Request for Facebook upload photos with check-in for userId - {} and device id {} ", userId, deviceId);
					response = socialService.uploadPhotosWithCheckInToAlbumOnFb(userId, tripId, placeId, emotion, images, fbAccessToken);
				}else {
					logger.warn("Request for Facebook check-in without images for userId - {} and device id {}", userId ,  deviceId);
					response = socialService.publishFacebookCheckInWithoutImages(tripId,placeId, emotion, customMessage, fbAccessToken);
				}
			}else {
				
				//placeName = placeName;
				if(null != images) {
					logger.warn("Request for Facebook upload photos with custom place check-in for userId - {} and device id {}", userId, deviceId);
					/*if(StringUtils.isNotEmpty(customMessage)){
						placeName =  customMessage + " - "+ placeName;
					}*/
					response = socialService.uploadPhotosWithCustomPlaceToAlbumOnFb(userId, tripId, placeName, emotion, images, fbAccessToken);
				}else {
					logger.warn("Request for Facebook custom place check-in without images for userId - {} and device id {}", userId, deviceId);
					response = socialService.publishFacebookCustomPlaceWithoutImages(tripId, placeName, emotion, customMessage, fbAccessToken);
				}
			}
		} else { // This is only when a user simply wants to share only images in the timeline
			if(null != images && images.length > 0) {
				logger.warn("Request for Facebook only Photo upload for userId - {} and device id {} ", userId, deviceId) ;
				response = socialService.uploadOnlyPhotosToAlbumOnFb(userId, tripId, emotion, customMessage, images, fbAccessToken);
			}else{
				logger.warn("Request for Facebook only Message and emotions for userId - {} and device id {} ", userId, deviceId) ;
				response = socialService.publishOnlyMessageOrEmotionOnFb(userId, tripId, emotion, customMessage, fbAccessToken);
			}
		}
		return response.toString();
	 }
	 
 }

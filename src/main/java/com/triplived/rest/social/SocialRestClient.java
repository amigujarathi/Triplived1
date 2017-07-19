package com.triplived.rest.social;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.connectme.domain.triplived.dto.FbImageDTO;
import com.domain.triplived.social.FbPostType;
import com.domain.triplived.social.findFbEntity.Datum;
import com.domain.triplived.social.findFbEntity.FindFbEntity;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Album;
import com.restfb.types.FacebookType;
import com.triplived.dao.social.FbCheckInPostsDao;
import com.triplived.entity.FbCheckInPostsDb;




@Component
public class SocialRestClient {
	
	@Value("${fbFindEntity}")
	private String fbFindEntity;
	
	@Value("${fbFindEntityWithoutLatLng}")
	private String fbFindEntityWithOutLatLng;
	
	/*@Value("${fbAccessToken}")
	private String fbAccessToken;*/
	
	@Value("${fbAlbumCreate}")
	private String fbAlbumCreate;
	
	@Value("${fbAddPhotos}")
	private String fbAddPhotos;
	
	@Value("${fbalbum}")
	private String fbAlbumUrl;
	
	@Value("${distanceFacebookPlacesToFind}")
	private String distanceFacebookPlacesToFind;
	
	@Autowired
	private FbCheckInPostsDao fbCheckInPostsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SocialRestClient.class);
	
	
	/**
	 * This method takes in latitude and longitude of the entity from the client and searches for that entity's page via Facebook's API.
	 * @param placeName
	 * @param lat
	 * @param lng
	 * @param fbAccessToken
	 * @return
	 */
	public String getFacebookCheckInEntity(String placeName, Double lat, Double lng, String fbAccessToken) {
		
		String placeId = null;
		String fbFindEntityUrl;
		try {
			if(lat != null  && lat !=0 && lng != null && lng !=0){
				String coordinates = lat.toString()+","+lng.toString();
				fbFindEntityUrl = String.format(fbFindEntity+"&fields=category,id,name", coordinates, distanceFacebookPlacesToFind, fbAccessToken, placeName);
			}else{
				fbFindEntityUrl = String.format(fbFindEntityWithOutLatLng+"fields=category,id,name", fbAccessToken, placeName);
			}
			 
			
			RestTemplate rt = new RestTemplate();
			rt.getMessageConverters().clear();
			rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        
			HttpHeaders headers = new HttpHeaders();
	    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	HttpEntity<String> entity = new HttpEntity<String>(headers);
	    	
	    	ResponseEntity<FindFbEntity> response = rt.exchange(fbFindEntityUrl, HttpMethod.GET, entity, FindFbEntity.class);
    	
	    	if(null == response) {
	    		logger.warn("NULL response from Facebook Graph Search API. Request parameters are: placeName - {}, lat - {}, lng - {}, distance - {}, fbAccessToken - {}", placeName, lat, lng, distanceFacebookPlacesToFind, fbAccessToken );
	    	}
		
    	/*for(Datum data : response.getBody().getData()) {
    	  if(null != data.getName()) {
    		  String dataLowerCase = data.getName().toLowerCase();
    		  String placeNameLowerCase = placeName.toLowerCase();
    		  if(dataLowerCase.contains(placeNameLowerCase)) {
    			  placeId = data.getId();
    				logger.warn("Matched response from Facebook Graph Search API. Request parameters are: placeName - {}, lat - {}, lng - {}, distance - {}, fbAccessToken - {}. Response placeId is - {}", placeName, lat, lng, d, fbAccessToken, placeId );
    				break;
    		  }
    	  }
    	}*/
    	
    	List<Datum> results = response.getBody().getData();
    	if(!CollectionUtils.isEmpty(results) &&  lat != null && lng != null) {
    		placeId = results.get(0).getId();
    	}else{
    		
    		// place find based on wild search lat long were not provided
    		
    		if(CollectionUtils.isNotEmpty(results)){
    			for(Datum data  : results){
    			
    					if(placeName.equalsIgnoreCase(data.getName())){ // TODO this needs a category check
    						placeId = data.getId();
    						break;
    					}
    			}
    		}
    	}
    	
    	if(null == placeId) {
    		logger.warn("None of the responses matched from Facebook Graph Search API. Request parameters are: placeName - {}, lat - {}, lng - {}, distance - {}, fbAccessToken - {}", placeName, lat, lng, distanceFacebookPlacesToFind, fbAccessToken );
    	}
    	
    	return placeId;
	   }catch(Exception e) {
			logger.error("Error in response from Facebook Graph Search API. Request parameters are: placeName - {}, lat - {}, lng - {}, distance - {}, fbAccessToken - {}. Exception is : {}", placeName, lat, lng, distanceFacebookPlacesToFind, fbAccessToken, e );
			
	   }
	  return placeId;	
	}
	
	/**
	 * This method is only for creating album. No photos are added here.
	 * @param userId
	 * @param tripId
	 * @param fbAccessToken
	 * @param albumName
	 * @return
	 */
	public String createFbAlbumForTrip(String userId, String tripId, String fbAccessToken, String albumName) {
		
		DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
		String fbAlbumCreateUrl = String.format(fbAlbumCreate, userId);
		
		if(null == albumName) {
			albumName = "My Trip";
		}
		
		try {
		FacebookType publishMessageResponse =  facebookClient.publish(fbAlbumCreateUrl, FacebookType.class,
				Parameter.with("name", albumName));
		logger.warn("Album created on Facebook for userId - {} and tripId - {}. Album Name is - {} , fbAccessToken is - {}", userId, tripId, albumName, fbAccessToken);
		
		    return publishMessageResponse.getId();
		}catch(Exception e) {
			
			logger.error("Error from Facebook while creating album. Request parameters are: userId - {}, tripId - {}, albumName - {}, fbAccessToken - {}. Exception is : {}", userId, tripId, albumName, fbAccessToken, e );
			return null;
		}
	}
	
	
	   public Boolean publishOnlyMessageOrEmotionOnFb(String tripId, String emotion, String customMsg, String fbAccessToken) {
		   
		    DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
			
		    String message = "";
		    if(StringUtils.isEmpty(emotion)) {
				if(StringUtils.isEmpty(customMsg)) {
					
				}else {
					message = customMsg;
				}
			}else {
				if(StringUtils.isEmpty(customMsg)) {
					message = " Feeling " + emotion;
				}else {
					message = customMsg + " - Feeling " + emotion ;
				}
			}

			
				try {
					if(!StringUtils.isEmpty(message)) {
						FacebookType publishMessageResponse =  facebookClient.publish("me/feed", FacebookType.class,
						Parameter.with("message", message));
						
						logger.warn("Message published on Facebook. Published message is : {}", publishMessageResponse);
						
						if(null != publishMessageResponse) {
							logFbPublishMessagesInDb(tripId, null, null, null, fbAccessToken, publishMessageResponse.getId(), "S", FbPostType.ONLY_MESSAGE_OR_EMOTION.getType());
						}else {
							logFbPublishMessagesInDb(tripId, null, null, null, fbAccessToken, null, "F", FbPostType.ONLY_MESSAGE_OR_EMOTION.getType());
						}
						
						return true;
					}
				}catch(Exception e) {
					logger.error("Error from Facebook while publishing only message/emotion. Request parameters are:  fbAccessToken - {}. Exception is : {}",  fbAccessToken, e );					
					logFbPublishMessagesInDb(tripId, null, null, null, fbAccessToken, null, "F", FbPostType.ONLY_MESSAGE_OR_EMOTION.getType());
					return false;
				}		
				return false;
	   }
	
        /**
         * Publish check-ins on facebook without attaching any images	
         * @param placeName
         * @param emotion
         * @param lat
         * @param lng
         * @param fbAccessToken
         * @return
         */
		public Boolean publishFacebookCheckInWithoutImages(String tripId, String placeId, String emotion, String customMsg, String fbAccessToken) {
			
			DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
			
			String message = null;
			if(StringUtils.isEmpty(emotion)) {
				if(StringUtils.isEmpty(customMsg)) {
					
				}else {
					message = customMsg;
				}
			}else {
				
				if(StringUtils.isEmpty(customMsg)) {
					message = " Feeling " + emotion;
				}else {
					message = customMsg + " - Feeling " + emotion;
				}
			}

			if(null != placeId) {
				try {
					FacebookType publishMessageResponse =  facebookClient.publish("me/feed", FacebookType.class,
					Parameter.with("message", message), Parameter.with("place", placeId));
					
					/*FbTripDb fbCheckInObj = new FbTripDb();
					fbCheckInObj.setPublishId(publishMessageResponse.getId());
					fbCheckInObj.setEntityId(Long.parseLong(placeId));
					fbCheckInObj.setUpdateDate(new Date());*/
					
					logger.warn("Message published on Facebook. Published message is : {}", publishMessageResponse);
					
					if(null != publishMessageResponse) {
						logFbPublishMessagesInDb(tripId, null, null, placeId, fbAccessToken, publishMessageResponse.getId(), "S", FbPostType.ENTITY_CHECK_IN_WITHOUT_IMAGES.getType());
					}else {
						logFbPublishMessagesInDb(tripId, null, null, placeId, fbAccessToken, null, "F", FbPostType.ENTITY_CHECK_IN_WITHOUT_IMAGES.getType());
					}
					
					return true;
				}catch(Exception e) {
					logger.error("Error from Facebook while publishing check-in. Request parameters are: placeId - {}, fbAccessToken - {}. Exception is : {}", placeId, fbAccessToken, e );					
					logFbPublishMessagesInDb(tripId, null, null, placeId, fbAccessToken, null, "F", FbPostType.ENTITY_CHECK_IN_WITHOUT_IMAGES.getType());
					return false;
				}
			}

			return false;		
		}
		
        /**
         * Publish check-ins on facebook without attaching any images	
         * @param placeName
         * @param emotion
         * @param lat
         * @param lng
         * @param fbAccessToken
         * @return
         */
		public Boolean publishFacebookCustomPlaceWithoutImages(String tripId, String placeName, String emotion, String customMsg, String fbAccessToken) {
			
			DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
			String message = "";
			if(StringUtils.isEmpty(emotion)) {
				if(StringUtils.isEmpty(customMsg)) {
					message = " at " + placeName;
				}else {
					message = customMsg + " - at " + placeName;
				}
			}else {
				
				if(StringUtils.isEmpty(customMsg)) {
					message = " Feeling " + emotion + " at " + placeName;
				}else {
					message = customMsg + " - Feeling " + emotion + " at " + placeName;
				}
			}
			try {
					FacebookType publishMessageResponse =  facebookClient.publish("me/feed", FacebookType.class,
					Parameter.with("message", message));
					
					/*FbTripDb fbCheckInObj = new FbTripDb();
					fbCheckInObj.setPublishId(publishMessageResponse.getId());
					fbCheckInObj.setEntityId(Long.parseLong(placeId));
					fbCheckInObj.setUpdateDate(new Date());*/
					
					logger.warn("Message published on Facebook. Published message is : {}", publishMessageResponse);
					
					if(null != publishMessageResponse) {
						logFbPublishMessagesInDb(tripId, null, placeName, null, fbAccessToken, publishMessageResponse.getId(), "S", FbPostType.CUSTOM_CHECK_IN_WITHOUT_IMAGES.getType());
					}else {
						logFbPublishMessagesInDb(tripId, null, placeName, null, fbAccessToken, null, "F", FbPostType.CUSTOM_CHECK_IN_WITHOUT_IMAGES.getType());
					}
					
					return true;
				}catch(Exception e) {
					logger.error("Error from Facebook while publishing check-in. Request parameters are: placeName - {}, fbAccessToken - {}. Exception is : {}", placeName, fbAccessToken, e );					
					logFbPublishMessagesInDb(tripId, null, placeName, null, fbAccessToken, null, "F", FbPostType.CUSTOM_CHECK_IN_WITHOUT_IMAGES.getType());
					return false;
				}
		}
		
		
		
		
		/**
		 * Uploading photos with entities/check-in on Fb
		 * @param tripId
		 * @param albumId
		 * @param placeId
		 * @param paths
		 * @param desc
		 * @param fbAccessToken
		 * @return
		 */
		public Boolean uploadPhotosWithCheckInToAlbumOnFb(String tripId, String albumId, String placeId, FbImageDTO[] images, String fbAccessToken) {
			
			DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
			String fbAddPhotosUrl = String.format(fbAddPhotos, albumId);
			
			int counter = 0;
			for(FbImageDTO image : images) {
				
				try{
					FacebookType publishMessageResponse;	
				if(null != image.getImageDesc()) {	
					publishMessageResponse =  facebookClient.publish(fbAddPhotosUrl, FacebookType.class,
						Parameter.with("name", image.getImageDesc()),
						Parameter.with("place", placeId),
						Parameter.with("url", image.getImagePath())
						);
				}else {
					publishMessageResponse =  facebookClient.publish(fbAddPhotosUrl, FacebookType.class,
							Parameter.with("place", placeId),
							Parameter.with("url", image.getImagePath())
							);
				}
				counter++;
				if(null != publishMessageResponse) {
					logFbPublishMessagesInDb(tripId, image.getImagePath(), null, placeId, fbAccessToken, publishMessageResponse.getId(), "S", FbPostType.ENTITY_CHECK_IN_WITH_IMAGES.getType());
				}else {
					logFbPublishMessagesInDb(tripId, image.getImagePath(), null, placeId, fbAccessToken, null, "F", FbPostType.ENTITY_CHECK_IN_WITH_IMAGES.getType());
				}
				
				logger.warn("Image - {} uploaded to album on Facebook successfully for tripId - {}. Album Id is - {} , fbAccessToken is - {}", image.getImagePath(), tripId, albumId, fbAccessToken);
				}catch(Exception e) {
					logger.error("Error while uploading image - {} for tripId - {} and albumId - {}, fbAccessToken is - {}. Exception is {}", image.getImagePath(), tripId, albumId, fbAccessToken, e.getStackTrace());
					logFbPublishMessagesInDb(tripId, image.getImagePath(), null, placeId, fbAccessToken, null, "F", FbPostType.ENTITY_CHECK_IN_WITH_IMAGES.getType());
				}
			}
			
			if(counter == images.length) {
				logger.warn("All {} images successfully uploaded to album - {}", counter, albumId);
				return true;
			}else {
				return false;
			}
		}
		
		/**
		 * Uploading only photos on Fb
		 * @param tripId
		 * @param albumId
		 * @param paths
		 * @param desc
		 * @param fbAccessToken
		 * @return
		 */
		public Boolean uploadOnlyPhotosToAlbumOnFb(String tripId, String albumId, FbImageDTO[] images, String fbAccessToken) {
			
			DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
			String fbAddPhotosUrl = String.format(fbAddPhotos, albumId);
			
			int counter = 0;
			for(FbImageDTO image : images) {
				FacebookType publishMessageResponse;
				try{
					if(null != image.getImageDesc()) {
				       publishMessageResponse =  facebookClient.publish(fbAddPhotosUrl, FacebookType.class,
						Parameter.with("name", image.getImageDesc()),
						Parameter.with("url", image.getImagePath())
						);
					}else {
						publishMessageResponse =  facebookClient.publish(fbAddPhotosUrl, FacebookType.class,
								  Parameter.with("url", image.getImagePath())
								);
					}
				counter++;
				
				if(null != publishMessageResponse) {
					logFbPublishMessagesInDb(tripId, image.getImagePath(), null, null, fbAccessToken, publishMessageResponse.getId(), "S", FbPostType.ONLY_IMAGES.getType());
				}else {
					logFbPublishMessagesInDb(tripId, image.getImagePath(), null, null, fbAccessToken, null, "F", FbPostType.ONLY_IMAGES.getType());
				}
				
				logger.warn("Image - {} uploaded to album on Facebook successfully for tripId - {}. Album Id is - {} , fbAccessToken is - {}", image.getImagePath(), tripId, albumId, fbAccessToken);
				
				}catch(Exception e) {
					logger.error("Error while uploading image - {} for tripId - {} and albumId - {}, fbAccessToken is - {}. Exception is {}", image.getImagePath(), tripId, albumId, fbAccessToken, e.getStackTrace());
					logFbPublishMessagesInDb(tripId, image.getImagePath(), null, null, fbAccessToken, null, "F", FbPostType.ONLY_IMAGES.getType());
				}
			}
			
			if(counter == images.length) {
				logger.warn("All {} images successfully uploaded to album - {}", counter, albumId);
				return true;
			}else {
				return false;
			}
		}


		public boolean checkAlbumExists(String fbAccessToken, String albumId){
			
			try {
				DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
				String fbAlbumURL = String.format(fbAlbumUrl, albumId);
				Album album = facebookClient.fetchObject(fbAlbumURL, Album.class);
				if (album != null) {
					return true;
				}
			} catch (Exception e) {
				logger.warn("Album Does not exists or was deleted by user {} ", albumId);
			}
			return Boolean.FALSE;
			
		}
		public Boolean uploadPhotosWithCustomPlaceToAlbumOnFb(String tripId, String albumId, String placeName, FbImageDTO[] images, String fbAccessToken) {
			
			DefaultFacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_3);
			String fbAddPhotosUrl = String.format(fbAddPhotos, albumId);
			
			int counter = 0;
			for(FbImageDTO image : images) {
				
				try{
				FacebookType publishMessageResponse;
				if(null != image.getImageDesc()) {
				    publishMessageResponse =  facebookClient.publish(fbAddPhotosUrl, FacebookType.class,
						Parameter.with("name", image.getImageDesc() + " - at " + placeName),
						Parameter.with("url", image.getImagePath())
						);
				}else {
					publishMessageResponse =  facebookClient.publish(fbAddPhotosUrl, FacebookType.class,
							Parameter.with("name", " - at " + placeName),
							Parameter.with("url", image.getImagePath())
							);
				}
				counter++;
				
				if(null != publishMessageResponse) {
					logFbPublishMessagesInDb(tripId, image.getImagePath(), placeName, null, fbAccessToken, publishMessageResponse.getId(), "S", FbPostType.CUSTOM_CHECK_IN_WITH_IMAGES.getType());
				}else {
					logFbPublishMessagesInDb(tripId, image.getImagePath(), placeName, null, fbAccessToken, null, "F", FbPostType.CUSTOM_CHECK_IN_WITH_IMAGES.getType());
				}
				
				logger.warn("Image - {} uploaded to album on Facebook successfully for tripId - {}. Album Id is - {} , fbAccessToken is - {}", image.getImagePath(), tripId, albumId, fbAccessToken);
				}catch(Exception e) {
					logger.error("Error while uploading image - {} for tripId - {} and albumId - {}, fbAccessToken is - {}. Exception is {}", image.getImagePath(), tripId, albumId, fbAccessToken, e.getStackTrace());
					logFbPublishMessagesInDb(tripId, image.getImagePath(), placeName, null, fbAccessToken, null, "F", FbPostType.CUSTOM_CHECK_IN_WITH_IMAGES.getType());
				}
			}
			
			if(counter == images.length) {
				logger.warn("All {} images successfully uploaded to album - {}", counter, albumId);
				return true;
			}else {
				return false;
			}
		}

	    @Transactional
		private void logFbPublishMessagesInDb(String tripId, String url, String entityName, String entityId, String fbAccessToken, String responseId, String status, String publishType) {
			
			FbCheckInPostsDb checkInPost = new FbCheckInPostsDb();
			checkInPost.setTripId(Long.parseLong(tripId));
			if(null != entityId) {
				checkInPost.setEntityId(Long.parseLong(entityId));
			}
			
			checkInPost.setEntityName(entityName);
			checkInPost.setPostImageUrl(url);
			checkInPost.setPublishId(responseId);
			checkInPost.setPublishType(publishType);
			checkInPost.setStatus(status);
			checkInPost.setUpdateDate(new Date());
			fbCheckInPostsDao.updateFbCheckIn(checkInPost);
		}
	    
}

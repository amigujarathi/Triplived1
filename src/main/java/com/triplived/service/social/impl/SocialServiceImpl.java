package com.triplived.service.social.impl;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.device.UserDynamicDao;
import com.triplived.dao.social.FbTripDao;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.user.UserDao;
import com.triplived.entity.FbTripDb;
import com.triplived.entity.PersonDb;
import com.triplived.entity.TripUserDb;
import com.triplived.entity.UserDynamicDb;
import com.triplived.rest.social.SocialRestClient;
import com.connectme.domain.triplived.dto.FbImageDTO;
import com.triplived.service.social.SocialService;
import com.triplived.service.trip.TripService;

@Service
public class SocialServiceImpl implements SocialService{
	
	@Autowired
	private SocialRestClient socialRestClient;
	
	@Autowired
	private FbTripDao fbTripDao;
	
	@Autowired
	private TripDAO tripDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserDynamicDao userDeviceMappingDao;
	
	@Autowired
	private TripService tripService;
	
	
	public String getFbAccessToken(String userId) {
		return null;
	}
	
	@Override
	public String checkIfTimelineEntityMappedToFBEntity(String placeName, Double lat, Double lng, String fbAccessToken) {
		String placeId = null;
		placeId = socialRestClient.getFacebookCheckInEntity(placeName, lat, lng, fbAccessToken);
		return placeId;
	}
	
	
	@Override
	public Boolean publishFacebookCheckInWithoutImages(String tripId, String placeId, String emotion, String customMsg, String fbAccessToken) {
		Boolean status = socialRestClient.publishFacebookCheckInWithoutImages(tripId, placeId, emotion, customMsg, fbAccessToken);
		return status;
	}
	
	@Override
	public Boolean publishFacebookCustomPlaceWithoutImages(String tripId, String placeName, String emotion, String customMsg, String fbAccessToken) {
		
			Boolean status = socialRestClient.publishFacebookCustomPlaceWithoutImages(tripId, placeName, emotion, customMsg, fbAccessToken);
			return status;
	}
	
	
	@Override
	public Boolean uploadPhotosWithCheckInToAlbumOnFb(String userId, String tripId, String placeId, String emotion, FbImageDTO[] images, String fbAccessToken) {
		
		String albumId = checkIfFbDetailsExistAndGetAlbumId(tripId, userId, fbAccessToken);
		Boolean status = socialRestClient.uploadPhotosWithCheckInToAlbumOnFb(tripId, albumId, placeId, images, fbAccessToken);
		return status;
	}
	
	
	@Override
	public Boolean uploadPhotosWithCustomPlaceToAlbumOnFb(String userId, String tripId, String placeName, String emotion, FbImageDTO[] images, String fbAccessToken) {
		
		    String albumId = checkIfFbDetailsExistAndGetAlbumId(tripId, userId, fbAccessToken);
		    Boolean status = socialRestClient.uploadPhotosWithCustomPlaceToAlbumOnFb(tripId, albumId, placeName, images, fbAccessToken);
			return status;
	}
	
	
	@Override
	public Boolean uploadOnlyPhotosToAlbumOnFb(String userId, String tripId, String emotion,String customMsg, FbImageDTO[] images, String fbAccessToken) {
		
			String albumId = checkIfFbDetailsExistAndGetAlbumId(tripId, userId, fbAccessToken);
			Boolean status1 = socialRestClient.publishOnlyMessageOrEmotionOnFb(tripId, emotion, customMsg, fbAccessToken);
			Boolean status = socialRestClient.uploadOnlyPhotosToAlbumOnFb(tripId, albumId, images, fbAccessToken);
			
			return status;
	}
	
	
	@Override
	public Boolean publishOnlyMessageOrEmotionOnFb(String userId, String tripId, String emotion,String customMsg,  String fbAccessToken) {
			return socialRestClient.publishOnlyMessageOrEmotionOnFb(tripId, emotion, customMsg, fbAccessToken);
	}
	
	/**
	 * check if album is already created or not
	 * @param tripId
	 * @param userId
	 * @param fbAccessToken
	 * @return
	 */
	@Transactional(readOnly=false)
	public String checkIfFbDetailsExistAndGetAlbumId(String tripId, String userId, String fbAccessToken) {
		String albumId;
		FbTripDb fbTripDb = fbTripDao.checkIfFbDetailsExist(Long.parseLong(tripId));
		if(null == fbTripDb) {
			albumId = createAlbum(tripId, userId, fbAccessToken);
		} else {
			//case where user deleted his album thi will recreate his album
			albumId = fbTripDb.getAlbumId().toString();
			
			boolean existingAlbumId = socialRestClient.checkAlbumExists(fbAccessToken, albumId);
			if(existingAlbumId){
				return albumId;
			}else{
				//ALBUM DOE NOT EXISTS, HENCE CREATE TRIP
				// update album ID
				String tripName = tripService.getTripNameById(Long.parseLong(tripId));
				if(null == tripName) {
					tripName = "Trip"; 
				}
				albumId = socialRestClient.createFbAlbumForTrip(userId, tripId, fbAccessToken, tripName);
				fbTripDb.setAlbumId(Long.parseLong(albumId));
				fbTripDao.updateFbTrip(fbTripDb);
			}
		}
		
		return albumId;
	}

	private String createAlbum(String tripId, String userId, String fbAccessToken) {
		String albumId;
		FbTripDb fbTripDb;
		fbTripDb = new FbTripDb();
		fbTripDb.setTripId(Long.parseLong(tripId));
		fbTripDb.setUpdateDate(new Date());
		String tripName = tripService.getTripNameById(Long.parseLong(tripId));
		if(null == tripName) {
			tripName = "Trip"; 
		}
		albumId = socialRestClient.createFbAlbumForTrip(userId, tripId, fbAccessToken, tripName);
		fbTripDb.setAlbumId(Long.parseLong(albumId));
		fbTripDao.updateFbTrip(fbTripDb);
		return albumId;
	}
	
	@Override
	@Transactional
	public PersonDb getUserFbDetails(Long tripId) {
		TripUserDb tripUserDb = tripDao.getUserIdByTripId(tripId);
		Long personId = null;
		if(null != tripUserDb) {
			personId = tripUserDb.getUserId();
		}
		if(null != personId) {
			PersonDb person = userDao.getPersonByUserId(personId.toString());
			return person;
		}
		return null;
	}
	
	@Override
	@Transactional
	public UserDynamicDb getUserDeviceMappingDetails(Long userId, String deviceId) {
		UserDynamicDb mapping = userDeviceMappingDao.getMapping(userId, deviceId);
		return mapping;		
	}
	

}

package com.triplived.service.social;

import com.connectme.domain.triplived.dto.FbImageDTO;

import com.triplived.entity.PersonDb;
import com.triplived.entity.UserDynamicDb;

public interface SocialService {

	Boolean publishFacebookCheckInWithoutImages(String tripId, String placeId, String emotion,String customMsg, 
			String fbAccessToken);

	String checkIfTimelineEntityMappedToFBEntity(String placeName, Double lat,
			Double lng, String fbAccessToken);

	Boolean uploadPhotosWithCheckInToAlbumOnFb(String userId, String tripId,
			String placeId, String emotion, FbImageDTO[] images, String fbAccessToken);

	Boolean uploadPhotosWithCustomPlaceToAlbumOnFb(String userId,
			String tripId, String placeName, String emotion, FbImageDTO[] images,
			String fbAccessToken);

	Boolean uploadOnlyPhotosToAlbumOnFb(String userId, String tripId,
			String emotion,String customMsg, FbImageDTO[] images, String fbAccessToken);

	Boolean publishFacebookCustomPlaceWithoutImages(String tripId, String placeName,String customMsg, 
			String emotion, String fbAccessToken);

	PersonDb getUserFbDetails(Long tripId);

	UserDynamicDb getUserDeviceMappingDetails(Long userId, String deviceId);

	Boolean publishOnlyMessageOrEmotionOnFb(String userId, String tripId, String emotion, String customMsg,
			String fbAccessToken);


}

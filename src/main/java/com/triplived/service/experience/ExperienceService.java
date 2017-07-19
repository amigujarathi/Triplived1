package com.triplived.service.experience;

import java.util.List;

import com.connectme.domain.triplived.dto.WebExperienceDTO;

public interface ExperienceService {


	List<WebExperienceDTO> getWebExperiencesByTripId(String tripId);

	//String addWebExperienceMedia(String path);


	WebExperienceDTO createOrUpdateExperience(String experienceId,
			String tripId, String location, String emotion, String timestamp,
			String description, String entityType, String cityId,
			String cityName, String orderNo, String day, String userId,
			String activity);


	WebExperienceDTO createOrUpdateTransportExperience(String experienceId,
			String sourceExperienceId, String destinationExperienceId,
			String transportType, String tripId, String description,
			String userId, String ts);

	String updateExperienceStatus(String experienceId, String tripId,
			String status);

	String addWebExperienceMedia(String mediaId, String path,
			String experienceId, Boolean smallVer, Boolean mediumVer);

	String updateWebExperienceMedia(String mediaId, String path,
			String experienceId, String status, String id);

	
}

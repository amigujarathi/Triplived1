package com.triplived.controller.experience;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.WebExperienceDTO;
import com.connectme.domain.triplived.dto.WebTripDTO;
import com.google.gson.Gson;
import com.triplived.service.experience.ExperienceService;
import com.triplived.service.trip.TripService;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/experience")
public class ExperienceController {
	
	@Autowired
	private ExperienceService experienceService;
	
	@Autowired
	private TripService tripService;

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody String createOrUpdateExperience(
			@RequestParam(value = "id") String experienceId,
			@RequestParam(value = "tripId") String tripId,
			@RequestParam(value = "location") String location,
			@RequestParam(value = "emotion" , required = false) String emotion,
			@RequestParam(value = "ts") String timestamp,
			@RequestParam(value = "description" , required = false) String description,
			@RequestParam(value = "entity_type") String entityType,
			@RequestParam(value = "cid" , required = false) String cityId,
			@RequestParam(value = "cname" , required = false) String cityName,
			@RequestParam(value = "order", required = false) String orderNo,
			@RequestParam(value = "day", required = false) String day,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "activity" , required = false) String activity
			) {
		
		WebExperienceDTO exp = experienceService.createOrUpdateExperience(experienceId, tripId, location, emotion, timestamp, description, entityType, cityId, cityName, orderNo, day, userId, activity);
		Gson gson = new Gson();
		return gson.toJson(exp);
		
	}
	
	@RequestMapping(value = "/fetchTripExperiences", method = RequestMethod.GET)
	public @ResponseBody String getAllExperiencesOfATrip(HttpServletRequest request,
			@RequestParam(value = "id") String tripId
			) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			WebTripDTO webTripDetails = tripService.getTripForWebEdit(tripId, internalTripId);

			Gson gson = new Gson();
			return gson.toJson(webTripDetails);
		}else {
			return "Not Authorized. Deprecated web version";
		}
		
		
		
	}
	
	@RequestMapping(value = "/addExperienceMedia", method = RequestMethod.GET)
	public @ResponseBody String addExperienceMedia(@RequestParam(value = "path") String path,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "imageId", required = false) String imageId,
			@RequestParam(value = "experienceId", required = false) String experienceId,
			@RequestParam(value = "mediumVer", required = false) String mediumVer,
			@RequestParam(value = "smallVer", required = false) String smallVer){
		
		    		
		experienceService.addWebExperienceMedia(imageId, path, experienceId, Boolean.parseBoolean(smallVer), Boolean.parseBoolean(mediumVer));
		return "done";
		
	}
	
	@RequestMapping(value = "/updateExperienceMedia", method = RequestMethod.GET)
	public @ResponseBody String updateExperienceMedia(@RequestParam(value = "path") String path,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "imageId", required = false) String imageId,
			@RequestParam(value = "experienceId", required = false) String experienceId){
		
		    		
		experienceService.updateWebExperienceMedia(imageId, path, experienceId, status, imageId);
		return "done";
		
	}
	
	@RequestMapping(value = "/updateTransport", method = RequestMethod.GET)
	public @ResponseBody String createOrUpdateTransportExperience(
			@RequestParam(value = "experienceId") String experienceId,
			@RequestParam(value = "sid") String sourceExperienceId,
			@RequestParam(value = "did") String destinationExperienceId,
			@RequestParam(value = "transport_type") String transportType,
			@RequestParam(value = "ts") String timestamp,
			@RequestParam(value = "trip_id") String tripId,
			@RequestParam(value = "description" , required = false) String description,
			@RequestParam(value = "userId") String userId
			) {
		WebExperienceDTO exp = experienceService.createOrUpdateTransportExperience(experienceId, sourceExperienceId, destinationExperienceId, transportType, tripId, description, userId, timestamp);
		Gson gson = new Gson();
		return gson.toJson(exp);
	}
	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public @ResponseBody String updateExperienceStatus(
			@RequestParam(value = "experienceId") String experienceId,
			@RequestParam(value = "trip_id") String tripId,
			@RequestParam(value = "status") String status
			) {
		
		String statusOfCall = experienceService.updateExperienceStatus(experienceId, tripId, status);
		return statusOfCall;
	}
	
}

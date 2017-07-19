 package com.triplived.controller.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connectme.domain.triplived.dto.NotificationBarParentDTO;
import com.connectme.domain.triplived.dto.ProfileDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.google.gson.Gson;
import com.triplived.controller.profile.UserFrom;
import com.triplived.service.profile.PersonProfile;
import com.triplived.service.user.UserService;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/updateFollower", method = RequestMethod.GET)
	public @ResponseBody void updateFollowerDetails(HttpServletRequest request, 
			@RequestParam(value = "fromId", required = true) String fromId,
			@RequestParam(value = "toId", required = true) String toId,
			@RequestParam(value = "status", required = false) String status){
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Update follower from deviceId - {}. Details : from - {}, to -{}, status - {}", deviceId, fromId, toId, status);

		if(null == status) {
			userService.updateFollowerDetails(Long.parseLong(fromId), Long.parseLong(toId), true);
		}else {
			userService.updateFollowerDetails(Long.parseLong(fromId), Long.parseLong(toId), Boolean.parseBoolean(status));
		}
		
	}
	
	@RequestMapping(value = "/loadProfile", method = RequestMethod.GET)
	public @ResponseBody String loadUserProfile(HttpServletRequest request, 
			@RequestParam(value = "id", required = true) String personId) {
		
		ProfileDTO profile = userService.loadUserProfile(personId);
		
		for(TripSearchDTO dto : profile.getTrips()) {
			dto.setUserId(personId);
			if(!TripLivedUtil.isPublicIdEnabled(request)) {
				dto.setTripId(dto.getOldTripId());
			}
			dto.setOldTripId(null);
		}

		
		Gson gson = new Gson();
		return gson.toJson(profile);
		
	}
	
	/**
	 * Creating a different method here for web, since on web we can save incomplete trips.
	 * @param request
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/loadProfileForWeb", method = RequestMethod.GET)
	public @ResponseBody String loadUserProfileForWeb(HttpServletRequest request, 
			@RequestParam(value = "id", required = true) String personId) {
		
		ProfileDTO profile = userService.loadUserProfileForWeb(personId);
		
		for(TripSearchDTO dto : profile.getTrips()) {
			if(!TripLivedUtil.isPublicIdEnabled(request)) {
				dto.setTripId(dto.getOldTripId());
			}
			dto.setOldTripId(null);
		}

		
		Gson gson = new Gson();
		return gson.toJson(profile);
		
	}
	
	@RequestMapping(value = "/editProfile/{userId}", method = RequestMethod.POST)
	public @ResponseBody String editUserProfile( @RequestBody PersonProfile personProfile, @PathVariable("userId") Long userId){
		
		try {
			if (personProfile != null && userId != null && userId != 0) {
				userService.updateUserProfile(userId, personProfile);
				Gson gson = new Gson();
				return gson.toJson(personProfile);
			}
			return "{}";
		} catch (Exception e) {
			logger.error("Error While updating profile" + e.getMessage(), e);
			return "{}";
		}
		
	}
	
	@RequestMapping(value = "/loadUserActivities", method = RequestMethod.GET)
	public @ResponseBody String loadUserActivities(HttpServletRequest request, 
			@RequestParam(value = "id", required = true) String personId) {
		
		return null;
	}
	
	@RequestMapping(value = "/tripEditStatus", method = RequestMethod.GET)
	public @ResponseBody String isTripEditableByUser(HttpServletRequest request, 
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "tripId", required = true) String tripId) {
		
		try {
			return userService.isTripEditableByUser(userId, tripId);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			logger.error("Error while checking if user is authorized to edit trip. User - {}, trip - {}", userId, tripId);
			e.printStackTrace();
		}
		return null;
	}

	
	@RequestMapping(value = "/picture/{id}", method = RequestMethod.GET)
	public  ModelAndView  loadUserProfilePicture(HttpServletRequest request, 
			@PathVariable("id") Long id,
			@RequestParam(value = "userFrom", required=false) UserFrom userFrom,
			@RequestParam(value = "size", required=true) String size) {
		
		//logger.warn("Image requested for user {}  and  userFrom {} ", id , userFrom );
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("size", size);
		
		String  redirectUrl = userService.getUserImageUrl(id+"", userFrom, map);
		 return new ModelAndView("redirect:" + redirectUrl);
		
	}

	/**
	 * 
	 * @param request
	 * @param id
	 * @param userFrom
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/coverpicture/{id}", method = RequestMethod.GET)
	public  ModelAndView  loadUserCoverPicture(HttpServletRequest request, 
			@PathVariable("id") String id,
			@RequestParam(value = "accountId", required=false) String accountId) {
		
		 logger.warn("cover Image requested for user {}  and  accountId {} ", id , accountId );
		 
		 String redirectUrl = userService.getUserCoverPageUrl(id, accountId);
		 return new ModelAndView("redirect:" + redirectUrl);
		
	}
	
	/**
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/notificationBar", method = RequestMethod.GET)
	public  @ResponseBody String   notificationBar(HttpServletRequest request, 
			@RequestParam("id") String userId) {
		
		 String userDevice = request.getHeader("UserDeviceId");
		 logger.warn("Notification Bar request for userId - {} for userDevice {}", userId, userDevice );
		 NotificationBarParentDTO obj = new NotificationBarParentDTO();
		 obj = userService.notificationBar(userId);
		 Gson gson = new Gson();
		 return gson.toJson(obj);
		
	}
	
	
	/**
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/notificationBarUpdate", method = RequestMethod.GET)
	public  @ResponseBody String   notificationBarUpdate(HttpServletRequest request, 
			@RequestParam("id") String userId) {
		
		 logger.warn("Notification Bar Update request for userId - {}", userId );
		 userService.notificationBarUpdate(userId);	
		 return "true";
		 
	}
	
}

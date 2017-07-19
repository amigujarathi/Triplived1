package com.triplived.controller.trip;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.FeedbackDTO;
import com.connectme.domain.triplived.dto.MiscDataDTO;
import com.connectme.domain.triplived.dto.TravelAgentDetailsDTO;
import com.google.gson.Gson;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.TripTrendingExceptionDb;
import com.triplived.mail.client.SubscribeMailResponse;
import com.triplived.service.profile.PersonProfile;
import com.triplived.service.trip.TravelAgentService;
import com.triplived.service.user.UserService;

@Controller
@RequestMapping("/misc")
public class MiscDataController {

	@Autowired
    private TravelAgentService travelAgentService;
	
	@Autowired
    private TripDAO tripDao;
	
	@Autowired
	private SubscribeMailResponse subscribeMailObj;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(MiscDataController.class);

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public @ResponseBody String getDetailList() {
		MiscDataDTO misc = new MiscDataDTO();
		//List<TravelAgentDetailsDTO> list = travelAgentService.getTravelAgentList();
		List<TravelAgentDetailsDTO> list = new ArrayList<TravelAgentDetailsDTO>();
		misc.setTravelAgentList(list);
		
		Gson gson = new Gson();
		return gson.toJson(misc);
	}
	
	@RequestMapping(value = "/agent", method = RequestMethod.GET)
	public @ResponseBody String getAgentDetail(@RequestParam("id") String id) {
		
		TravelAgentDetailsDTO obj = travelAgentService.getTravelAgentDetailByCustomId(id);
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	@RequestMapping(method= RequestMethod.POST, value="/feedback")
    public @ResponseBody Boolean submitFeedback(Principal principal, final HttpServletResponse response, 
    		@RequestBody FeedbackDTO obj,
			HttpSession session, HttpServletRequest request) {
		
		String deviceId = request.getHeader("UserDeviceId");
		Gson gson = new Gson();
		String feedbackStr = gson.toJson(obj);
		logger.warn("Feedback submitted by user - {} by userDevice {} ", feedbackStr, deviceId);
		
		if(StringUtils.isNotEmpty(deviceId)){
			PersonProfile personProfile = userService.getUserFromDeviceId(deviceId);
			
			if(personProfile != null){
				try {
					subscribeMailObj.sendMail(personProfile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
		
	}
	
	@RequestMapping(value = "/pixel", method = RequestMethod.GET)
	public @ResponseBody String getPixelForDownloads() {
		logger.warn("Pixel fired for download");
		return "true";
	}
	
	@RequestMapping(value = "/addTrendingException", method = RequestMethod.GET)
	public @ResponseBody String addTrendingTripException(@RequestParam("id") String id, @RequestParam("points") String points,
			@RequestParam("status") String status) {
		TripTrendingExceptionDb obj = tripDao.getTrendingTripExceptionObj(Long.parseLong(id));
		if(null == obj) {
			obj = new TripTrendingExceptionDb();
			obj.setTripId(Long.parseLong(id));
		}
		obj.setUpdateDate(new Date());
		obj.setStatus(status);
		obj.setPoints(Long.parseLong(points));
		tripDao.updateTrendingExceptionTrips(obj);
		
		return "true";
	}
}

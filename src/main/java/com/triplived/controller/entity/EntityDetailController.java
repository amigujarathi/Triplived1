package com.triplived.controller.entity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.AttractionDetailsDTO;
import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.HotelDetailsDTO;
import com.google.gson.Gson;
import com.triplived.service.staticContent.StaticEntityService;
import com.triplived.service.trip.TripEntityCuratedService;

@Controller
@RequestMapping("/entity/detail")
public class EntityDetailController {

	@Autowired
	private StaticEntityService staticEntityService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(EntityDetailController.class);
	
	@RequestMapping(value = "/attraction", method = RequestMethod.GET)
	public @ResponseBody String getAttractionDetails(@RequestParam(value = "code") String code, HttpServletRequest request) {
	
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Request received from device - {} for attraction code - {}", deviceId, code);
		try {
			AttractionDetailsDTO obj = staticEntityService.getAttractionDetails(code);
			
			if(null != obj) {
				Gson gson = new Gson();
				return gson.toJson(obj);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Error while requesting attraction code - {} from device - {}. Error is - {}", code, deviceId, e);
		} 
		return null;
		
	}
	
	@RequestMapping(value = "/hotel", method = RequestMethod.GET)
	public @ResponseBody String getHotelDetails(@RequestParam(value = "code") String code, HttpServletRequest request) {
	
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Request received from device - {} for hotel code - {}", deviceId, code);
		try {
			HotelDetailsDTO obj = staticEntityService.getHotelDetails(code);
			Gson gson = new Gson();
			return gson.toJson(obj);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Error while requesting hotel code - {} from device - {}. Error is - {}", code, deviceId, e);
		} 
		return null;
		
	}

}

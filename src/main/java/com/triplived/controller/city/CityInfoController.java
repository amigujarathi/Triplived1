package com.triplived.controller.city;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.CityInfoDTO;
import com.connectme.domain.triplived.dto.TrendingTripDTO;
import com.google.gson.Gson;
import com.triplived.rest.restaurantSearch.RestaurantSearchClient;
import com.triplived.service.city.CityInfoService;
import com.triplived.service.device.DeviceService;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/cityInfo")
public class CityInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(CityInfoController.class);
	
	@Autowired
	private CityInfoService cityInfoService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private RestaurantSearchClient rsc;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping(method= RequestMethod.GET)
    public @ResponseBody String getCityInfo(@RequestParam("name") String cityName,
    										@RequestParam("lat") String lat,
    										@RequestParam("lng") String lng,
    										@RequestParam(value = "accuracy", required = false) String accuracy,
    		 HttpSession session, Model model, HttpServletRequest request,
    		 @RequestHeader(value="UserDeviceId", required=false) String userDevice, 
    		 @RequestHeader(value = "ApplicationVersion", required=false) String appVersion, 
			 @RequestHeader(value="ApplicationVersionString", required=false) String applicationVersion) {
		
		try {
		Gson gson = new Gson();
		CityInfoDTO cityInfoObj = cityInfoService.generateCityInfo(cityName, lat, lng);
		
		
			List<Map<String, List<TrendingTripDTO>>> cityInfoList = cityInfoObj.getCityInfoList();
			Map<String, List<TrendingTripDTO>> map = cityInfoList.get(0);
			List<TrendingTripDTO> tripResponse = map.get("Trending Trips");
			for(TrendingTripDTO dto : tripResponse) {
				if(!TripLivedUtil.isPublicIdEnabled(request)) {
					dto.setTripId(dto.getOldTripId());
				}
				dto.setOldTripId(null);
			}
			
			//Update application version with device
			try {
				Integer appVersionGettingRegisteredAbove = environment.getProperty("applicationVersionIsRegistredAboveVersion", Integer.class); 
				if(StringUtils.isNotEmpty(userDevice) && StringUtils.isNotEmpty(appVersion) && Integer.parseInt(appVersion) < appVersionGettingRegisteredAbove){
					logger.warn("Updating the application version for device  for device {} with version {} ", userDevice, applicationVersion);
					deviceService.updateDeviceVersion(userDevice, appVersion, applicationVersion);
				}
			} catch (Exception e) { 
				logger.error("DEVICE registration failed - {}", e);
				// TODO Auto-generated catch block
			}
		
		return gson.toJson(cityInfoObj);
		}catch(Exception e) {
			logger.error("Error - {}", e);
			return null;
		}
		
   }
	
	
	
}

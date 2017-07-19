package com.triplived.controller.city;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.google.gson.Gson;
import com.triplived.dao.attraction.CityDao;
import com.triplived.entity.CityDb;
import com.triplived.rest.weatherClient.WeatherClient;
import com.triplived.service.cityAutoComplete.CityAutoCompleteService;
import com.triplived.service.staticContent.StaticCityService;

@Controller
@RequestMapping("/citySuggest")
public class CitySuggestController {
	
	private static final Logger logger = LoggerFactory.getLogger(CitySuggestController.class);
	
	@Autowired
	private StaticCityService staticCityService;
	
	@Autowired
	private WeatherClient wc;
	
	@Autowired
	private CityAutoCompleteService cityAutoCompleteGoogleService;
	
	
	private static Map<String,String> similarCityMap = new HashMap<String, String>();
	
	static {
		Properties properties = new Properties();
		
		try {
			properties.load(CitySuggestController.class.getResourceAsStream("/similarCityList.properties"));
			for (String key : properties.stringPropertyNames()) {
			    String value = properties.getProperty(key);
			    similarCityMap.put(key, value);
			}
		}
		catch (Exception e) { 
		}
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/{param}")
    public @ResponseBody String getAttractionsByCity(@PathVariable("param") String param, HttpSession session, Model model, HttpServletRequest request) {
	
		List<CityResponseDTO> cities = staticCityService.suggestCity(param);
		Gson gson = new Gson();
		return gson.toJson(cities);
   }
	
	
	@RequestMapping(method= RequestMethod.GET, value="/code/{param}")
    public @ResponseBody String getCityFromCode(@PathVariable("param") String param, HttpSession session, Model model, HttpServletRequest request) {
	
		//WeatherClient wc = new WeatherClient();
		//wc.getWeatherInfo();
		
		
		List<CityResponseDTO> cities = staticCityService.getCityFromCode(param);
		if(!CollectionUtils.isEmpty(cities)) {
			CityResponseDTO city = cities.get(0);
			if(null == city.getCityImagePath()) {
				city.setCityImagePath("/static/city/main/"+param+"m.jpg");
			}
			Gson gson = new Gson();
			return gson.toJson(city);	
		}
		return null;
   }
	
	@RequestMapping(method= RequestMethod.GET, value="/search/{param}")
	public @ResponseBody String getCitiesforSearch(@PathVariable("param") String param, 
    		HttpSession session, Model model, HttpServletRequest request) {
	
		Gson gson = new Gson();
		List<CityResponseDTO> cities = staticCityService.suggestCity(param);
		
		/*Iterator<CityResponseDTO> itr = cities.iterator();
		while(itr.hasNext()) {
			CityResponseDTO city = itr.next();
			if(!countryCode.equalsIgnoreCase(city.getCityCountry())) {
				itr.remove();
			}
		}*/
		
		return gson.toJson(cities);
		
   }
	
	@RequestMapping(method= RequestMethod.GET, value="/get/{countryCode}/{param}")
	public @ResponseBody String getCities(@PathVariable("param") String param, 
    		@PathVariable("countryCode") String countryCode,
    		HttpSession session, Model model, HttpServletRequest request) {
	    //TODO CountryCode is hardcoded from client side as IN. Have to remove it once static itinerary is complete
		String deviceId = request.getHeader("UserDeviceId");
		Gson gson = new Gson();
		List<CityResponseDTO> cities = staticCityService.suggestCity(param);
		
		Iterator<CityResponseDTO> itr = cities.iterator();
		/*while(itr.hasNext()) {
			CityResponseDTO city = itr.next();
			if(!countryCode.equalsIgnoreCase(city.getCityCountry())) {
				itr.remove();
			}
		}*/
		
		if((null == cities) || (null != cities && cities.size() < 3)) {
			List<CityResponseDTO> gCities = cityAutoCompleteGoogleService.fetchCities(countryCode,param,deviceId, similarCityMap);
			
			
			if(!CollectionUtils.isEmpty(gCities)) {
				List<CityResponseDTO> allCities = cityAutoCompleteGoogleService.mergeCities(cities,gCities);
				String cityResult = gson.toJson(allCities);
				logger.warn("City Response for parameter request - {} is - {}", param, cityResult);
				return gson.toJson(allCities);
			}
			
		}
		
		String cityResult = gson.toJson(cities);
		logger.warn("City Response for parameter request - {} is - {}", param, cityResult);
		return cityResult;
		
   }
	
	@RequestMapping(value = "/geo", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getCity(HttpServletRequest request, 
			@RequestParam(value = "lat", required = true) String lat,
			@RequestParam(value = "lng", required = true) String lng) {
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Request received for geoCode from deviceId - {} for lat - {}, lng = {}", request.getHeader("UserDeviceId"), lat, lng);
		Gson gson = new Gson();
		CityResponseDTO city = staticCityService.suggestCityByGeoCoder(deviceId, lat, lng);
		return gson.toJson(city);
		
	}	
	
	

}

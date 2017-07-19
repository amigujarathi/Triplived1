package com.triplived.controller.entity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.dto.AttractionResponseDTO;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.google.gson.Gson;
import com.triplived.rest.attractionClient.AttractionSearchClient;
import com.triplived.service.staticContent.StaticAttractionService;
import com.triplived.service.staticContent.StaticEntityService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/entitySuggest")
public class EntitySuggestController {
	
	@Autowired
	private StaticEntityService staticEntityService;
	 
	private static final Logger logger = LoggerFactory.getLogger(EntitySuggestController.class);
	
	@RequestMapping(method= RequestMethod.GET, value="/{deviceId}/{lat}/{lng}")
	   public @ResponseBody String getEntityByCoordinates(@PathVariable("deviceId") String deviceId, @PathVariable("lat") String lat,@PathVariable("lng") String lng) {
		
			//List<EntityResponse> entity = staticEntityService.getAttractionEntityByCoordinates(lat, lng);
			List<EntityResponseDTO> entity = staticEntityService.getAttractionExternalEntityByCoordinates(deviceId, lat, lng, "500");
			Gson gson = new Gson();
			return gson.toJson(entity);
	   }
	
	@RequestMapping(method= RequestMethod.GET, value="/hotel/{lat}/{lng}/{d}")
	   public @ResponseBody String getHotelEntityByCoordinates(@PathVariable("lat") String lat,@PathVariable("lng") String lng, 
			   @PathVariable("d") String d) {
		
			List<EntityResponse> entity = staticEntityService.getHotelEntityByCoordinates(lat, lng, d);
			Gson gson = new Gson();
			return gson.toJson(entity);
	   }
	
	@RequestMapping(method= RequestMethod.GET, value="/restaurant//{deviceId}/{lat}/{lng}")
	   public @ResponseBody String getRestaurantEntityByCoordinates(@PathVariable("deviceId") String deviceId,@PathVariable("lat") String lat,@PathVariable("lng") String lng) {
		
			List<EntityResponseDTO> entity = staticEntityService.getRestaurantEntityByCoordinates(null,lat, lng, Constants.RESTAURANT_DISTANCE);
			Gson gson = new Gson();
			return gson.toJson(entity);
	 }
	
	@RequestMapping(method= RequestMethod.GET, value="/all/{lat}/{lng}/{d}")
	   public @ResponseBody String getAllEntityByCoordinates(@PathVariable("lat") String lat,@PathVariable("lng") String lng, 
			   @PathVariable("d") String d) {
		
			List<EntityResponse> entity = staticEntityService.getAllEntityByCoordinates(lat, lng, d);
			Gson gson = new Gson();
			return gson.toJson(entity);
	   }
	
	@RequestMapping(method= RequestMethod.GET, value="/entities/{deviceId}/{lat}/{lng}/{accuracy}")
	   public @ResponseBody String getAllExternalEntityByCoordinates(HttpServletRequest request, @PathVariable("deviceId") String deviceId, @PathVariable("lat") String lat,@PathVariable("lng") String lng, 
			   @PathVariable("accuracy") String accuracy) {
		
		    logger.warn("Request for getting entities recievid from deviceId - {} for parameters, lat - {}, lng - {}, accuracy - {}", deviceId, lat, lng, accuracy);
			/*List<EntityResponseDTO> attractionEntities = staticEntityService.getAttractionExternalEntityByCoordinates(lat, lng, Constants.ATTRACTION_DISTANCE);
			List<EntityResponseDTO> restaurantEntities = staticEntityService.getRestaurantEntityByCoordinates(lat, lng, Constants.RESTAURANT_DISTANCE);
			List<EntityResponseDTO> hotelEntities = staticEntityService.getHotelExternalEntityByCoordinates(lat, lng, Constants.HOTEL_DISTANCE);*/
			
			Double tmpAccuracy = (Double.parseDouble(accuracy) * 2.5);
			if(tmpAccuracy > 200) {
				tmpAccuracy = new Double(200);
			}
			
			List<EntityResponseDTO> attractionEntities = staticEntityService.getAttractionExternalEntityByCoordinatesForTimeline(deviceId,lat, lng, Double.toString(tmpAccuracy));
			List<EntityResponseDTO> restaurantEntities = staticEntityService.getRestaurantEntityByCoordinates(deviceId,lat, lng, Double.toString(tmpAccuracy));
			List<EntityResponseDTO> hotelEntities = staticEntityService.getHotelExternalEntityByCoordinates(deviceId,lat, lng, Double.toString(tmpAccuracy));
			List<EntityResponseDTO> finalList = staticEntityService.orderListByDistance(deviceId,attractionEntities, restaurantEntities, hotelEntities, lat, lng);
			
			Gson gson = new Gson();
			return gson.toJson(finalList);
	   }
	
	@RequestMapping(method= RequestMethod.GET, value="/entitiesDev/{deviceId}/{lat}/{lng}/{accuracy}")
	   public @ResponseBody String getAllExternalEntityByCoordinatesForDev(HttpServletRequest request, @PathVariable("deviceId") String deviceId, @PathVariable("lat") String lat,@PathVariable("lng") String lng, 
			   @PathVariable("accuracy") String accuracy) {
		
		    logger.warn("Request for getting entities recievid from deviceId - {} for parameters, lat - {}, lng - {}, accuracy - {}", deviceId, lat, lng, accuracy);
			/*List<EntityResponseDTO> attractionEntities = staticEntityService.getAttractionExternalEntityByCoordinates(lat, lng, Constants.ATTRACTION_DISTANCE);
			List<EntityResponseDTO> restaurantEntities = staticEntityService.getRestaurantEntityByCoordinates(lat, lng, Constants.RESTAURANT_DISTANCE);
			List<EntityResponseDTO> hotelEntities = staticEntityService.getHotelExternalEntityByCoordinates(lat, lng, Constants.HOTEL_DISTANCE);*/
			
			Double tmpAccuracy = (Double.parseDouble(accuracy) * 2.5) ;
			if(tmpAccuracy > 200) {
				tmpAccuracy = new Double(200);
			}
			
			List<EntityResponseDTO> attractionEntities = staticEntityService.getAttractionExternalEntityByCoordinatesForTimeline(deviceId,lat, lng, Double.toString(tmpAccuracy));
			List<EntityResponseDTO> restaurantEntities = staticEntityService.getRestaurantEntityByCoordinates(deviceId,lat, lng, Double.toString(tmpAccuracy));
			List<EntityResponseDTO> hotelEntities = staticEntityService.getHotelExternalEntityByCoordinates(deviceId,lat, lng, Double.toString(tmpAccuracy));
			List<EntityResponseDTO> finalList = staticEntityService.orderListByDistance(deviceId,attractionEntities, restaurantEntities, hotelEntities, lat, lng);
			
			Gson gson = new Gson();
			return gson.toJson(finalList);
	   }

	@RequestMapping(method= RequestMethod.GET, value="/cityCode/{cityCode}")
	   public @ResponseBody String getAttractionExternalByCityCodeForMapping(@PathVariable("cityCode") String cityCode) {
		
			List<EntityResponseDTO> entity = staticEntityService.getAttractionExternalByCityCodeForMapping(cityCode);
			Gson gson = new Gson();
			return gson.toJson(entity);
	   }
}

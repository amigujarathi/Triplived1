package com.triplived.service.city.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.dto.CityInfoDTO;
import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.TrendingTripDTO;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.domain.triplived.trip.dto.SubTrip;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.google.gson.Gson;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.TripDb;
import com.triplived.rest.trip.TripClient;
import com.triplived.rest.weatherClient.WeatherClient;
import com.triplived.service.city.CityInfoService;
import com.triplived.service.staticContent.StaticCityService;
import com.triplived.service.staticContent.StaticEntityService;
import com.triplived.util.TripLivedUtil;

@Service
public class CityInfoServiceImpl implements CityInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(CityInfoServiceImpl.class);

	@Autowired
	private StaticCityService staticCityService;
	
	@Autowired
	private StaticEntityService staticEntityService;
	
	@Autowired
	private WeatherClient wc;
	
	@Autowired
	private TripDAO tripDao;
	
	@Autowired
	private TripClient tripClient;
	
	@Value("${timelineTripIds}")
	private String timelineTrips;
	
	@Override
	public CityInfoDTO generateCityInfo(String cityName, String lat, String lng) {
		
		CityInfoDTO cityInfoDto = new CityInfoDTO();
		
		List<CityResponseDTO> cities = staticCityService.suggestCity(cityName);
		if(!CollectionUtils.isEmpty(cities)) {
			CityResponseDTO city = cities.get(0);
			cityInfoDto.setCityName(city.getCityName());
			//cityInfoDto.setTemperature(wc.getCityWeatherInfo(lat, lng));
			cityInfoDto.setLandingImagePath("");
			cityInfoDto.setDescription("");
			
			List<Map<String, List<TrendingTripDTO>>> list = null;
			if(null == cityInfoDto.getCityInfoList()) {
				 list = new ArrayList<Map<String, List<TrendingTripDTO>>>();
			}else {
				if(null != cityInfoDto.getCityInfoList()) {
					list = cityInfoDto.getCityInfoList();
				}
			}
			
			
			
			Map<String, List<TrendingTripDTO>> trendingTripsMap = new HashMap<String, List<TrendingTripDTO>>();
			//List<EntityResponseDTO> tripResponse = convertTripToResponse(trendingTrips);
			
			try {
				List<TrendingTripDTO> tripResponse = tripClient.getTrendingTrips();
				trendingTripsMap.put("Trending Trips", tripResponse);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				logger.error("Error while getting trending trips");
				e.printStackTrace();
			}
			
			
			list.add(trendingTripsMap);
			/*list.add(map);
			list.add(hotelMap);
			list.add(restaurantMap);
			*/
			cityInfoDto.setCityInfoList(list);
		}
		
		return cityInfoDto;
		
		
	}
	
	
	private List<EntityResponseDTO> convertTripToResponse(List<TripDb> trips) {
		List<EntityResponseDTO> list = new ArrayList<EntityResponseDTO>();
		Gson gson = new Gson();
		int counter = 0;
		for(TripDb trip : trips) {
			if(++counter > 4) {
				break;
			}
			
			EntityResponseDTO dto = new EntityResponseDTO();
			
    		Trip tripObj = gson.fromJson(trip.getTripData(), Trip.class);
    		dto.setName(tripObj.getTripName());
    		list.add(dto);
		}
		
		return list;
	}
	
	private List<TrendingTripDTO> convertTimelineTripToResponse(List<Object[]> trendingTimelines) {
		List<TrendingTripDTO> list = new ArrayList<TrendingTripDTO>();
		Gson gson = new Gson();
		int counter = 1;
		StringBuilder sb = new StringBuilder();
		
		for(Object[] oArr : trendingTimelines) {
			/*if(null != oArr[5]) {
				tripList.add(oArr[5].toString());
			}*/
			if(counter > 4) {
				counter = 1;
			}
			Long startTime = null, endTime = null;
			TrendingTripDTO dto = new TrendingTripDTO();
			if(null != oArr[0]) {
				dto.setTripName(oArr[0].toString());
			}
			
			if(null != oArr[2]) {
			TimelineTrip tripObj = gson.fromJson(oArr[2].toString(), TimelineTrip.class);
			if(null != tripObj.getTripCoverUri()) {
				dto.setTripUri(tripObj.getTripCoverUri());
			}else {
				dto.setTripUri("http://www.triplived.com/static/triplived/rdTrPh/trip" + counter++ + ".jpg");
			}
    		//dto.setName(tripObj.getTripName());
    		List<SubTrip> subTrips = tripObj.getSubTrips();
    		int subTripCounter = 0;
    		List<CityResponseDTO> allStops = new ArrayList<CityResponseDTO>();
    		
    		for(SubTrip s : subTrips) {
    			if(subTripCounter == 0) {
    				if(null != s.getToCityDTO().getCityType() && 
    						s.getToCityDTO().getCityType().equalsIgnoreCase("source")) {
    					dto.setTripSrc(s.getToCityDTO().getCityName());
    					startTime = s.getToCityDTO().getTimestamp();
    				}
    			}
    			if(subTripCounter == (subTrips.size() - 1)) {
    				if(null != s.getToCityDTO().getCityType() && 
    						s.getToCityDTO().getCityType().equalsIgnoreCase("destination")) {
    					dto.setTripDest(s.getToCityDTO().getCityName());
    					endTime = s.getToCityDTO().getTimestamp();
    				}
    			}
    			
    			if(null != s.getToCityDTO()) {
    				CityResponseDTO city = new CityResponseDTO();
    				city.setCityName(s.getToCityDTO().getCityName());
    				city.setType(s.getToCityDTO().getCityType());
    				allStops.add(city);
    			}
    			subTripCounter++;
    		 }
    		 dto.setTripCities(allStops);
			}
			if(null != oArr[4]) {
				dto.setUserName(oArr[4].toString());
			}
			if(null != oArr[5]) {
				dto.setTripId(oArr[5].toString());
				Long tripLike = tripDao.getTripLikes(Long.parseLong(oArr[5].toString()));
				if(null != tripLike && !(tripLike == 0)) {
					dto.setTripLikes(tripLike.toString());
				}
			}
			if(null != startTime && null != endTime) {
				
			}
			if(null != startTime && null != endTime) {
				long diff = Math.round((endTime - startTime)/(double) 86400000);
				if(diff > 1) {
					dto.setTripDuration(diff + " days");
				}else {
					dto.setTripDuration("Single day trip");
				}
			}
			//dto.setTripDuration("2 days");
			if(null != oArr[6]) {
				dto.setUserId(oArr[6].toString());
			}
			if(null != oArr[7]) {
				dto.setUserFbId(oArr[7].toString());
			}
			if(null != oArr[8]) {
				String categories = oArr[8].toString();
				dto.setCategories(Arrays.asList(categories.split(",")));
			}
			//This has been added to send public id to client
			if(null != oArr[9]) {
				dto.setTripId(oArr[9].toString());
			}
			
    		list.add(dto);
		}
		
	
		List<TrendingTripDTO> entityList = new ArrayList<TrendingTripDTO>();
		for(TrendingTripDTO obj : list) {
			entityList.add(obj);
		}
		
		return entityList;
	}
	
	private void setMetricDistances(List<EntityResponseDTO> list) {
		if(!org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			for(EntityResponseDTO erDto : list) {
				if(null != erDto.getDistanceFromCoordinates()) {
					if(erDto.getDistanceFromCoordinates() > 1000) {
						erDto.setDistanceInMetric(TripLivedUtil.trimValueToOneDecimal(erDto.getDistanceFromCoordinates() / 1000) + " km");
					} else {
						erDto.setDistanceInMetric(erDto.getDistanceFromCoordinates().intValue() + " mtr");
					}
					erDto.setDistanceFromCoordinates(null);
				}
			}
		}
	}
	
	private List<EntityResponseDTO> convertEntityResponseDTO(List<EntityResponse> list) {
		
		List<EntityResponseDTO> listDTO = new ArrayList<EntityResponseDTO>();
		
		for(EntityResponse obj : list) {
			EntityResponseDTO dto = new EntityResponseDTO();
			dto.setId(obj.getId());
			dto.setCityCode(obj.getCityCode());
			dto.setAddress(obj.getAddress());
			dto.setName(obj.getName());
			dto.setType(obj.getType());
			dto.setLocality(obj.getLocality());
			dto.setAddress(obj.getAddress());
			dto.setImageSrc(obj.getImageSrc());
			dto.setStreet(obj.getStreet());
	
			if(obj.getDistanceFromCoordinates() > 1000) {
				dto.setDistanceInMetric(TripLivedUtil.trimValueToOneDecimal(obj.getDistanceFromCoordinates() / 1000) + " km");
			} else {
				dto.setDistanceInMetric(obj.getDistanceFromCoordinates().intValue() + " mtr");
			}
			
			
			listDTO.add(dto);
		}
		
		return listDTO;
	}

}

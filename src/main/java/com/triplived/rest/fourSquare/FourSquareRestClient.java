package com.triplived.rest.fourSquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.connectme.domain.triplived.EntityResponse;
import com.domain.triplived.fourSquare.Category;
import com.domain.triplived.fourSquare.FSSearchEntity;
import com.domain.triplived.fourSquare.Venue;


@Component
public class FourSquareRestClient {
	
	@Value("${fsApi}")
	private String fsApi;
	
	@Value("${client_id_1}")
	private String client_id_1;
	
	@Value("${client_secret_1}")
	private String client_secret_1;
	
	@Value("${categories_1}")
	private String categories_1;
	
	@Value("${subCategory_1}")
	private String subcategory_1;
	
	private static final Logger logger = LoggerFactory.getLogger(FourSquareRestClient.class);
	
	
	/**
	 * This method takes in latitude and longitude of the entity from the client and searches in FourSquare API.
	 * @param deviceId
	 * @param lat
	 * @param lng
	 * @param distance
	 * @return
	 */
	public List<EntityResponse> searchFS(String deviceId, String lat, String lng, String distance) {
		
		List<String> subCategory_1 = Arrays.asList(subcategory_1.split(","));
		String d = distance;
		try {
		String coordinates = lat+","+lng;
		String fsApiUrl = String.format(fsApi, categories_1, d,client_id_1, client_secret_1, coordinates);
		List<EntityResponse> responseList = new ArrayList<EntityResponse>();
		
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().clear();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	ResponseEntity<FSSearchEntity> response = rt.exchange(fsApiUrl, HttpMethod.GET, entity, FSSearchEntity.class);
    	
    	if(null == response) {
    		logger.warn("NULL response from FourSquare API. Request parameters are: deviceId - {}, lat - {}, lng - {}, distance - {}, ", deviceId, lat, lng, d);
    	}else {
    		logger.warn("FourSquare API response received. Request parameters are: deviceId - {}, lat - {}, lng - {}, distance - {}, ", deviceId, lat, lng, d);
    	}
		
    	List<Venue> results = response.getBody().getResponse().getVenues();
    	
    	for(Venue venue : results) {
    		
    		EntityResponse erObj = new EntityResponse();
    		
    		erObj.setType("attraction");
    		erObj.setId(venue.getId());
    		erObj.setName(venue.getName());
    		
    		if(null != venue.getLocation()) {
    			erObj.setLatitude(venue.getLocation().getLat().toString());
    			erObj.setLongitude(venue.getLocation().getLng().toString());
    			
    			if(!CollectionUtils.isEmpty(venue.getLocation().getFormattedAddress())) {
    				StringBuilder sb = new StringBuilder();
    				for(String add : venue.getLocation().getFormattedAddress()) {
    					sb.append(add + ", ");
    				}
    				erObj.setAddress(sb.toString());
    			}
    			erObj.setDistanceFromCoordinates((double) venue.getLocation().getDistance());
    		}
    		
    		if(!CollectionUtils.isEmpty(venue.getCategories())) {
    			StringBuilder sb = new StringBuilder();
    			boolean validCategory = false;
				for(Category cat : venue.getCategories()) {
					sb.append(cat.getName() + ", ");
					if(subCategory_1.contains(cat.getId())) {
						validCategory = true;
					}
				}
				if(!validCategory) {
					continue;
				}
				erObj.setCategory(sb.toString());
    		}else {
    			continue;//Category is necessary as cannot afford any junk data to come.
    		}
    		
    		Map<String, String> miscMap = new HashMap<String, String>();
    		miscMap.put("src", "fs");
    		erObj.setMiscMap(miscMap);
    		erObj.setFoundAtQueryDistance(d);
    		
    		responseList.add(erObj);
    	  }
    	  return responseList;
		}catch(Exception e) {
		  logger.error("Exception while fetching response from FourSquare API for device - {} , lat - {}, lng - {}", deviceId, lat, lng);	
		  return null;	
		}
	}

}

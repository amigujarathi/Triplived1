package com.triplived.rest.attractionClient;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.connectme.domain.triplived.nearbyAttraction.NearbyAttraction;
import com.connectme.domain.triplived.nearbyAttraction.Result;
import com.triplived.entity.AttractionDb;
import com.triplived.util.TripLivedUtil;

@Component
public class AttractionSearchClient {
	
	@Value("${nearbyAttractionBaseUrl}")
	private String nearbyAttractionBaseUrl;
	
	@Value("${nearbyAttractionEstablishmentUrlFirst}")
	private String nearbyAttractionEstablishmentUrlFirst;
	
	@Value("${nearbyAttractionOthersUrlFirst}")
	private String nearbyAttractionOthersUrlFirst;
	
	@Value("${nearbyAttractionUrlNext}")
	private String nearbyAttractionUrlNext;
	
	@Value("${attractionCategory}")
	private String attractionCategory;
	
	@Value("${additionalAttractionReturnTypes}")
	private String additionalAttractionReturnTypes;
	
	private static final Logger logger = LoggerFactory.getLogger(AttractionSearchClient.class );
	
	    public List<EntityResponse> getAttractionSearchInfo(String deviceId, String lat, String lng, String d) {
	    	
	    List<String> attractionCategoryList = Arrays.asList(attractionCategory.split(","));
	    List<String> additionalAttractionReturnTypesList = Arrays.asList(additionalAttractionReturnTypes.split(","));
	    
		
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().clear();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	List<EntityResponse> listOfNearbyAttractions = new ArrayList<EntityResponse>();
    		String url = nearbyAttractionBaseUrl + nearbyAttractionEstablishmentUrlFirst + lat + "," + lng + "&radius=" + d;
			try {
			
			ResponseEntity<NearbyAttraction> response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
			fireAndLogCall(deviceId, "Attraction", 0);
			List<Result> results = response.getBody().getResults();
			
			if(null != response.getBody().getNextPageToken()) {
				url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
				response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
				fireAndLogCall(deviceId, "Attraction", 1);
				results.addAll(response.getBody().getResults());
				
				if(null != response.getBody().getNextPageToken()) {
					url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
					response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
					fireAndLogCall(deviceId, "Attraction", 2);
					results.addAll(response.getBody().getResults());
				}
			}
			
			Iterator<Result> iter = results.iterator();
			while(iter.hasNext()) {
				Result r = iter.next();
				if(null == r.getRating()) {
					iter.remove();
					continue;
				}
				if(r.getTypes().size() > 1) {
					boolean validAttraction = false;
					for(String s : r.getTypes()) {
						if(additionalAttractionReturnTypesList.contains(s)) {
							validAttraction = true;
							break;
						}
					}
					if(!validAttraction) {
						iter.remove();
					}
					continue;
				}
			}
			
			
			url = nearbyAttractionBaseUrl + nearbyAttractionOthersUrlFirst + lat + "," + lng + "&radius=" + d;
			response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
			fireAndLogCall(deviceId, "Attraction", 0);
			results.addAll(response.getBody().getResults());
			
			if(null != response.getBody().getNextPageToken()) {
				url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
				response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
				fireAndLogCall(deviceId, "Attraction", 1);
				results.addAll(response.getBody().getResults());
				
				if(null != response.getBody().getNextPageToken()) {
					url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
					response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
					fireAndLogCall(deviceId, "Attraction", 2);
					results.addAll(response.getBody().getResults());
				}
			}
			
			
			
			for(Result r : results) {
				
				
				EntityResponse obj = new EntityResponse();
					
				if(r.getPlaceId() != null) {
					obj.setId(r.getPlaceId());
				}
				
				if(r.getName() != null) {
					obj.setName(r.getName());
				}
				if(r.getRating() != null) {
					obj.setRating(r.getRating());
				}
				if(r.getGeometry().getLocation().getLat() != null) {
					obj.setLatitude(r.getGeometry().getLocation().getLat().toString());
				}
				if(r.getGeometry().getLocation().getLng() != null) {
					obj.setLongitude(r.getGeometry().getLocation().getLng().toString());
				}
				if(r.getVicinity() != null) {
					obj.setAddress(r.getVicinity());
				}
				if(r.getTypes() != null) {
					obj.setCategory(r.getTypes().toString());
				}
				
				if(null != obj.getLatitude() && null != obj.getLongitude()) {
					double distance = TripLivedUtil.HaversineInMetres(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()),
							Double.parseDouble(lat), Double.parseDouble(lng));
					obj.setDistanceFromCoordinates(TripLivedUtil.trimValue(distance));
				}
				
				
				obj.setType("attraction");
				listOfNearbyAttractions.add(obj);
			}
			
			String x = "Ayan";
				
			}catch(Exception e) {
				logger.error("Exception occured for results returned from Google places API for restaurant, for deviceId - {} ", deviceId);
				e.printStackTrace();
			}
		
		//Logging data for testing purpose temporarily
			/*String requestParams = lat+"/"+lng+"/"+d;
			for(EntityResponse er : listOfNearbyAttractions) {
				String msg = ts + "#" + er.getType() + "#" + er.getName() + "#" + er.getDistanceFromCoordinates() + "#" + er.getCategory() + "#" + er.getLatitude() + "#" + er.getLongitude() + "#" + er.getAddress() + "#" + requestParams;
				logger.warn(msg);
			}*/
    	return listOfNearbyAttractions;		
    	
    	
	}

	    
	    public List<EntityResponse> getAttractionSearchInfoByCityCode(String lat, String lng, String d, AttractionDb a, String cityCode, BufferedWriter brw) {
	    	
		    List<String> attractionCategoryList = Arrays.asList(attractionCategory.split(","));	
			
			RestTemplate rt = new RestTemplate();
			rt.getMessageConverters().clear();
			rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        
			HttpHeaders headers = new HttpHeaders();
	    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	HttpEntity<String> entity = new HttpEntity<String>(headers);
	    	
	    	List<EntityResponse> listOfNearbyAttractions = new ArrayList<EntityResponse>();
	    		String url = nearbyAttractionBaseUrl + nearbyAttractionEstablishmentUrlFirst + lat + "," + lng + "&radius=" + d;
				try {
				
				
				ResponseEntity<NearbyAttraction> response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
				List<Result> results = response.getBody().getResults();
				
				if(null != response.getBody().getNextPageToken()) {
					url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
					response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
					results.addAll(response.getBody().getResults());
					
					if(null != response.getBody().getNextPageToken()) {
						url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
						response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
						results.addAll(response.getBody().getResults());
					}
				}
				
				brw.append("Total results found for establishment category : " + cityCode + "-" + a.getId() + "-" + a.getName() + "-" + results.size() + "-d:" + d);
				
				Iterator<Result> iter = results.iterator();
				while(iter.hasNext()) {
					Result r = iter.next();
					if(null == r.getRating()) {
						iter.remove();
						continue;
					}
					if(r.getTypes().size() > 1) {
						iter.remove();
						continue;
					}
				}
				
				brw.append("Total results after removing rating and more than one : " + cityCode + "-" + a.getId() + "-" + a.getName() + "-" + results.size());
				
				url = nearbyAttractionBaseUrl + nearbyAttractionOthersUrlFirst + lat + "," + lng + "&radius=" + d;
				response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
				results.addAll(response.getBody().getResults());
				
				if(null != response.getBody().getNextPageToken()) {
					url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
					response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
					results.addAll(response.getBody().getResults());
					
					if(null != response.getBody().getNextPageToken()) {
						url = nearbyAttractionBaseUrl + nearbyAttractionUrlNext + response.getBody().getNextPageToken();
						response = rt.exchange(url, HttpMethod.GET, entity, NearbyAttraction.class);
						results.addAll(response.getBody().getResults());
					}
				}
				
				
				brw.append("Total results after adding other category : " + cityCode + "-" + a.getId() + "-" + a.getName() + "-" + results.size());
				
				for(Result r : results) {
					
					EntityResponse obj = new EntityResponse();
						
					if(r.getPlaceId() != null) {
						obj.setId(r.getPlaceId());
					}
					
					if(r.getName() != null) {
						obj.setName(r.getName());
					}
					if(r.getRating() != null) {
						obj.setRating(r.getRating());
					}
					if(r.getGeometry().getLocation().getLat() != null) {
						obj.setLatitude(r.getGeometry().getLocation().getLat().toString());
					}
					if(r.getGeometry().getLocation().getLng() != null) {
						obj.setLongitude(r.getGeometry().getLocation().getLng().toString());
					}
					if(r.getVicinity() != null) {
						obj.setAddress(r.getVicinity());
					}
					if(r.getTypes() != null) {
						obj.setCategory(r.getTypes().toString());
					}
					
					if(null != obj.getLatitude() && null != obj.getLongitude()) {
						double distance = TripLivedUtil.HaversineInMetres(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()),
								Double.parseDouble(lat), Double.parseDouble(lng));
						obj.setDistanceFromCoordinates(TripLivedUtil.trimValue(distance));
					}
					obj.setType("attraction");
					listOfNearbyAttractions.add(obj);
				}
				
				}catch(Exception e) {
					
					e.printStackTrace();
				}
			
			
	    	return listOfNearbyAttractions;		
	    	
	    	
		}
	    
	    private void fireAndLogCall(String deviceId, String entity, Integer iterationNo) {
	    	logger.warn("Google Search call by device - {} for entity - {} in iteration - {}", deviceId, entity, iterationNo);
	    }

	
}

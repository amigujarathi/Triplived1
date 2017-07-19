package com.triplived.rest.hotelClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.connectme.domain.triplived.nearbyHotel.NearbyHotel;
import com.connectme.domain.triplived.nearbyHotel.Result;
import com.triplived.util.TripLivedUtil;


//import com.connectme.domain.triplived.weatherJson.Example;

@Component
public class HotelSearchClient {
	
	@Value("${nearbyHotelBaseUrl}")
	private String nearbyHotelBaseUrl;
	
	@Value("${nearbyHotelUrlFirst}")
	private String nearbyHotelUrlFirst;
	
	@Value("${nearbyHotelUrlNext}")
	private String nearbyHotelUrlNext;
	
	private static final Logger logger = LoggerFactory.getLogger(HotelSearchClient.class );
	
	    public List<EntityResponse> getHotelSearchInfo(String deviceId, String lat, String lng, String d) {
		
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().clear();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	List<EntityResponse> listOfNearbyHotels = new ArrayList<EntityResponse>();
    		String url = nearbyHotelBaseUrl + nearbyHotelUrlFirst + lat + "," + lng + "&radius=" + d;
			try {
			ResponseEntity<NearbyHotel> response = rt.exchange (url, HttpMethod.GET, entity, NearbyHotel.class);
			fireAndLogCall(deviceId, "Hotel", 0);
			List<Result> results = response.getBody().getResults();
			
			if(null != response.getBody().getNextPageToken()) {
				url = nearbyHotelBaseUrl + nearbyHotelUrlNext + response.getBody().getNextPageToken();
				response = rt.exchange(url, HttpMethod.GET, entity, NearbyHotel.class);
				fireAndLogCall(deviceId, "Hotel", 1);
				results.addAll(response.getBody().getResults());
				
				if(null != response.getBody().getNextPageToken()) {
					url = nearbyHotelBaseUrl + nearbyHotelUrlNext + response.getBody().getNextPageToken();
					response = rt.exchange(url, HttpMethod.GET, entity, NearbyHotel.class);
					fireAndLogCall(deviceId, "Hotel", 2);
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
				obj.setType("hotel");
				listOfNearbyHotels.add(obj);
			}
			
			String x = "Ayan";
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
		
			//Logging data for testing purpose temporarily
			/*String requestParams = lat+"/"+lng+"/"+"200";
			for(EntityResponse er : listOfNearbyRestaurants) {
				String msg = ts + "#" + er.getType() + "#" + er.getName() + "#" + er.getDistanceFromCoordinates() + "#" + er.getCategory() + "#" + er.getLatitude() + "#" + er.getLongitude() + "#" + er.getAddress() + "#" + requestParams;
				logger.warn(msg);
			}*/
    	return listOfNearbyHotels;		
    	
    	
	}
	    
	    private void fireAndLogCall(String deviceId, String entity, Integer iterationNo) {
	    	logger.warn("Google Search call by device - {} for entity - {} in iteration - {}", deviceId, entity, iterationNo);
	    }
		
	
}

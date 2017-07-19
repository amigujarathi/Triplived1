package com.triplived.rest.romeToRio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.domain.triplived.romeToRio.RomeToRio;
import com.domain.triplived.romeToRio.Route;
import com.domain.triplived.romeToRio.Segment;
import com.domain.triplived.transport.dto.ValidRouteRestDTO;
import com.domain.triplived.transport.dto.ValidTransportRestDTO;

@Component
public class RomeToRioClient {
	
	
	@Value("${rtrApi}")
	private String rtrApi;
	
	private static final Logger logger = LoggerFactory.getLogger(RomeToRioClient.class);
	
	public List<ValidRouteRestDTO> getModesOfTransportBtnCities(String originCordinates, String destCoordinates){
		
		String rtrApiUrl = String.format(rtrApi, originCordinates, destCoordinates);
		
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().clear();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	ResponseEntity<RomeToRio> response = rt.exchange(rtrApiUrl, HttpMethod.GET, entity, RomeToRio.class);
    	if(null != response) {
    		logger.warn("Response received from Rome2Rio API - {}", response.getBody());
    		List<Route> routes = response.getBody().getRoutes();
    		if(!CollectionUtils.isEmpty(routes)) {
    			List<ValidRouteRestDTO> validRoutes = new ArrayList<ValidRouteRestDTO>();
    			
    			for(Route route : routes) {
    				ValidRouteRestDTO validRoute = new ValidRouteRestDTO();
    				validRoute.setRouteName(route.getName());
    				validRoute.setTotalDistance(route.getDistance());
    				validRoute.setTotalDuration(route.getDuration());
    				
    				List<ValidTransportRestDTO> transportList = new ArrayList<ValidTransportRestDTO>();
    				ValidTransportRestDTO transport = new ValidTransportRestDTO();
    				List<Segment> segments = route.getSegments();
    				if(!CollectionUtils.isEmpty(segments)) {
    					for(Segment segment : segments) {
    						if(null != segment) {
    							if(segment.getIsMajor() == 1) {
    								transport.setTransportType(segment.getKind());
    								transport.setDistance(segment.getDistance());
    								transport.setDuration(segment.getDuration());
    								transportList.add(transport);
    							}
    						}
    					}
    				}
    				validRoute.setValidTransports(transportList);
    				validRoutes.add(validRoute);
    			}
    			return validRoutes;
    			
    		}
    	}
    	return null;
	}

}

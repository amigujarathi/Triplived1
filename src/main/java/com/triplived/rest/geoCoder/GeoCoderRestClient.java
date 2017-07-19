package com.triplived.rest.geoCoder;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.connectme.domain.triplived.dto.GeoCityDTO;
import com.domain.triplived.geoCoder.Address;
import com.domain.triplived.geoCoder.GeoCoder;

@Component
public class GeoCoderRestClient {
	
	
	@Value("${geoCoderApi}")
	private String geoCoderApi;
	
	private static final Logger logger = LoggerFactory.getLogger(GeoCoderRestClient.class);
	
	public GeoCityDTO getGeoCity(String deviceId, String originCordinates, String destCoordinates){
		
		String geoCoderApiUrl = String.format(geoCoderApi, originCordinates, destCoordinates);
		
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().clear();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	logger.warn("Geocoder Request for deviceId - {}", deviceId);
    	ResponseEntity<GeoCoder> response = rt.exchange(geoCoderApiUrl, HttpMethod.GET, entity, GeoCoder.class);
    	if(null != response) {
    		GeoCoder geoCoder = response.getBody();
    		if(null != geoCoder) {
    			Address address = geoCoder.getAddress();
    			
    			if(null != address) {
    				GeoCityDTO geoCity = new GeoCityDTO();
    				boolean cityFilled = false;
    				if(!StringUtils.isEmpty(address.getCity())) {
    				   geoCity.setCityName(address.getCity());
    				   geoCity.setCityId(geoCoder.getPlaceId());
    				   cityFilled = true;
    				}
    				if(!StringUtils.isEmpty(address.getTown())) {
     				   geoCity.setCityName(address.getTown());
     				   geoCity.setCityId(geoCoder.getPlaceId());
     				  cityFilled = true;
     				}
    				if(!StringUtils.isEmpty(address.getCountryCode())) {
      				   geoCity.setCountryCode(address.getCountryCode());
      				}
    				if(!cityFilled && !StringUtils.isEmpty(address.getState_district())) {
    					geoCity.setCityName(address.getState_district());
      				   geoCity.setCityId(geoCoder.getPlaceId());
      				  cityFilled = true;
    				}
    				geoCity.setAddress(geoCoder.getDisplayName());
    				return geoCity;
    			}
    			
    			
    		}
    	}
    	return null;
	}

}


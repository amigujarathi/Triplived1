package com.triplived.rest.cityAutoComplete;

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

import com.connectme.domain.triplived.cityAutoCompleteApi.CityAutoCompleteDTO;
import com.connectme.domain.triplived.cityAutoCompleteApi.Prediction;
import com.connectme.domain.triplived.cityAutoCompleteApi.Term;
import com.connectme.domain.triplived.dto.CityResponseDTO;

@Component
public class CityAutoCompleteRestClient {
	
	@Value("${cityAutoCompleteApi}")
	private String cityAutoCompleteApi;
	
	private static final Logger logger = LoggerFactory.getLogger(CityAutoCompleteRestClient.class);
	
	public List<CityResponseDTO> fetchCities(String countryCode, String param) {
		
		List<CityResponseDTO> cities = new ArrayList<CityResponseDTO>();
		
		String cityAutoCompleteUrl = String.format(cityAutoCompleteApi, param);
		
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().clear();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	ResponseEntity<CityAutoCompleteDTO> response = rt.exchange(cityAutoCompleteUrl, HttpMethod.GET, entity, CityAutoCompleteDTO.class);
    	
    	if(null != response) {
    		List<Prediction> results = response.getBody().getPredictions();
    		
    		for(Prediction p : results) {
    			List<Term> terms = p.getTerms();
    			if(!CollectionUtils.isEmpty(terms)) {
    				int termsLength = terms.size();
	    			String cityName = terms.get(0).getValue();
	    			String countryName = terms.get(termsLength - 1).getValue();
	    			CityResponseDTO city = new CityResponseDTO();
	    			city.setCityName(cityName);
	    			city.setCityCountryName(countryName);
	    			city.setId(p.getPlaceId());
	    			city.setType("Google");
	    			cities.add(city);
    			}
    		}
    	}
    	
    	return cities;
		
	}

}

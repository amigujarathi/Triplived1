package com.triplived.service.cityAutoComplete.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.triplived.controller.city.CitySuggestController;
import com.triplived.rest.cityAutoComplete.CityAutoCompleteRestClient;
import com.triplived.service.cityAutoComplete.CityAutoCompleteService;

@Service
public class CityAutoCompleteServiceImpl implements CityAutoCompleteService {
	
	private static final Logger logger = LoggerFactory.getLogger(CityAutoCompleteServiceImpl.class);
	
	@Autowired
	private CityAutoCompleteRestClient cityRestClient;
	
	private static Map<String,String> similarCityWithDiffNamesMap = new HashMap<String, String>();
	
	static {
		Properties properties = new Properties();
		
		try {
			properties.load(CitySuggestController.class.getResourceAsStream("/similarCitiesWithDifferentNameList.properties"));
			for (String key : properties.stringPropertyNames()) {
			    String value = properties.getProperty(key);
			    similarCityWithDiffNamesMap.put(key, value);
			}
		}
		catch (Exception e) { 
		}
	}
	
	@Override
	public List<CityResponseDTO> fetchCities(String countryCode, String param, String deviceId, Map similarCityMap){
		List<CityResponseDTO> gCitiesList = cityRestClient.fetchCities(countryCode, param);
		Map<String, Integer> gCitiesMap = new HashMap<String, Integer>();
		StringBuilder sb = new StringBuilder();
		Iterator<CityResponseDTO> it = gCitiesList.iterator();
		while(it.hasNext()) {
			CityResponseDTO obj = it.next();
		
			if(similarCityMap.containsKey(obj.getCityName()) || gCitiesMap.containsKey(obj.getCityName())) {
				it.remove();
			}else {
				gCitiesMap.put(obj.getCityName(), 1);
				sb.append(obj.getCityName()+",");
				
				if(similarCityWithDiffNamesMap.containsKey(obj.getCityName())) {
					obj.setId(similarCityWithDiffNamesMap.get(obj.getCityName()));
				}
			}
		}
		
		
		logger.warn("Google City Autocomplete request for deviceId - {} for param - {} and response - {}", deviceId, param, sb.toString());
		return gCitiesList;
	}

	@Override
	public List<CityResponseDTO> mergeCities(List<CityResponseDTO> serverCities, List<CityResponseDTO> gCities){
		
		List<CityResponseDTO> mergeCities = new ArrayList<CityResponseDTO>();
		for(CityResponseDTO obj : serverCities) {
			mergeCities.add(obj);
		}
		
		for(CityResponseDTO gCityObj : gCities) {
			String gCityName = gCityObj.getCityName();
			String gCountryName = gCityObj.getCityCountryName();
			boolean matchCity = false;
			for(CityResponseDTO sCityObj : serverCities) {
				String sCityName = sCityObj.getCityName();
				String cCountryName = sCityObj.getCityCountryName();
				if((gCityName.equalsIgnoreCase(sCityName)) && (gCountryName.equalsIgnoreCase(cCountryName))) {
					matchCity = true;
					break;
				}
			}
			if(!matchCity) {
				mergeCities.add(gCityObj);
			}
		}
		return mergeCities;
	}
	
}

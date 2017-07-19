package com.triplived.service.staticContent.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.CityResponse;
import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.GeoCityDTO;
import com.triplived.rest.client.StaticContent;
import com.triplived.rest.geoCoder.GeoCoderRestClient;
import com.triplived.service.cityAutoComplete.CityAutoCompleteService;
import com.triplived.service.staticContent.StaticCityService;

@Service
public class StaticCityServiceImpl implements StaticCityService {
	
	@Autowired
	private StaticContent staticContent;
	
	@Autowired
	private GeoCoderRestClient geoCoder;
	
	private static final Logger logger = LoggerFactory.getLogger(StaticCityServiceImpl.class);
	
	@Override
	public List<CityResponseDTO> suggestCity(String param) {
		try {
			List<CityResponse> cityList = staticContent.suggestCities(param);
			return convertCityDTO(cityList);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public CityResponseDTO suggestCityByGeoCoder(String deviceId, String lat, String lng) {
		try {
			GeoCityDTO geoCityDto = geoCoder.getGeoCity(deviceId, lat, lng);
			 String countryCode = geoCityDto.getCountryCode();
			if(null != geoCityDto && null != geoCityDto.getCityName()) {
				List<CityResponse> cityList = staticContent.suggestCities(geoCityDto.getCityName());
				List<CityResponseDTO> cities = convertCityDTO(cityList);
				
				Iterator<CityResponseDTO> itr = cities.iterator();
				while(itr.hasNext()) {
					CityResponseDTO city = itr.next();
					if(!countryCode.equalsIgnoreCase(city.getCityCountry())) {
						itr.remove();
					}
				}
				if(!CollectionUtils.isEmpty(cities)) {
					return cities.get(0);
				}else {
					CityResponseDTO city = new CityResponseDTO();
					city.setCityName(geoCityDto.getCityName());
					city.setId("tlgc"+geoCityDto.getCityId());
					city.setType("Geocode");
					city.setAddress(geoCityDto.getAddress());
					city.setCityCountry(geoCityDto.getCountryCode());
					return city;
				}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Error during Geocode call - {}", e);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Error during Geocode call - {}", e);
		}catch (Exception e) {
			logger.warn("Error during Geocode call - {}", e);
		}
		return null;
	}
	
	
	@Override
	public List<CityResponseDTO> getCityFromCode(String cityCode) {
		try {
			List<CityResponse> cities = staticContent.getCityFromCode(cityCode);
			return convertCityDTO(cities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private List<CityResponseDTO> convertCityDTO(List<CityResponse> cities) {
		
		List<CityResponseDTO> listOfCities = new ArrayList<CityResponseDTO>();
		for(CityResponse obj : cities) {
			CityResponseDTO newObj = new CityResponseDTO();
			newObj.setId(obj.getId());
			newObj.setCityName(obj.getCityName());
			newObj.setCityDescription(obj.getDescription());
			newObj.setTemperature(obj.getTemperature());
			newObj.setCityCountry(obj.getCityCountry());
			newObj.setCityCountryName(obj.getCityCountryName());
			newObj.setCityDescription(obj.getDescription());
			newObj.setCityImagePath(obj.getLandingImagePath());
			listOfCities.add(newObj);
		}
		return listOfCities;
	}

}

package com.triplived.service.city;

import com.connectme.domain.triplived.dto.CityInfoDTO;


public interface CityInfoService {

	CityInfoDTO generateCityInfo(String cityName, String lat, String lng);
	
	

}

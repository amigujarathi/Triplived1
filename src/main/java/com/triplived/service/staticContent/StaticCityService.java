package com.triplived.service.staticContent;

import java.util.List;

import com.connectme.domain.triplived.dto.CityResponseDTO;

public interface StaticCityService {
	
	public List<CityResponseDTO> suggestCity(String param);

	List<CityResponseDTO> getCityFromCode(String cityCode);

	CityResponseDTO suggestCityByGeoCoder(String deviceId, String lat,
			String lng);

}

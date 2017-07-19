package com.triplived.service.cityAutoComplete;

import java.util.List;
import java.util.Map;

import com.connectme.domain.triplived.dto.CityResponseDTO;

public interface CityAutoCompleteService {

	List<CityResponseDTO> mergeCities(List<CityResponseDTO> serverCities,
			List<CityResponseDTO> gCities);


	List<CityResponseDTO> fetchCities(String countryCode, String param,
			String deviceId, Map similarCityMap);

}

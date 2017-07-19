package com.triplived.service.staticContent;

import java.util.List;

import com.connectme.domain.triplived.dto.HotelResponseDTO;

public interface StaticHotelService {

	public List<HotelResponseDTO> getHotelByCity(String city);

	List<HotelResponseDTO> suggestHotelByCity(String city, String param);

	List<HotelResponseDTO> getHotelsForPortal(String city, String param);
	
}

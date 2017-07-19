package com.triplived.service.staticContent;

import java.util.List;

import com.connectme.domain.triplived.dto.AttractionResponseDTO;

public interface StaticAttractionService {

	public List<AttractionResponseDTO> suggestAttractionByCity(String city, String param);

	List<AttractionResponseDTO> getPopularAttractionsOfCity(String city);

}

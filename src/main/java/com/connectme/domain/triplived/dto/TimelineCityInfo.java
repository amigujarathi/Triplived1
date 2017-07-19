package com.connectme.domain.triplived.dto;

import java.util.List;

public class TimelineCityInfo {
	
	private String cityUrl;
	private List<AttractionResponseDTO> popularAttractions;
	
	public String getCityUrl() {
		return cityUrl;
	}
	public void setCityUrl(String cityUrl) {
		this.cityUrl = cityUrl;
	}
	public List<AttractionResponseDTO> getPopularAttractions() {
		return popularAttractions;
	}
	public void setPopularAttractions(List<AttractionResponseDTO> popularAttractions) {
		this.popularAttractions = popularAttractions;
	}

}

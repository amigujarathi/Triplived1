package com.connectme.domain.triplived.dto;

import java.util.List;
import java.util.Map;

public class CityInfoDTO {
	
	private String cityName;
	private String temperature;
	private String landingImagePath;
	private String description;
	
	
	private List<Map<String, List<TrendingTripDTO>>> cityInfoList;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getLandingImagePath() {
		return landingImagePath;
	}
	public void setLandingImagePath(String landingImagePath) {
		this.landingImagePath = landingImagePath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Map<String, List<TrendingTripDTO>>> getCityInfoList() {
		return cityInfoList;
	}
	public void setCityInfoList(List<Map<String, List<TrendingTripDTO>>> cityInfoList) {
		this.cityInfoList = cityInfoList;
	}
	

}

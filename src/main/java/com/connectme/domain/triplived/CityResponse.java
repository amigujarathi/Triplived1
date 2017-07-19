package com.connectme.domain.triplived;

public class CityResponse {
	
	private String id;
	private String cityName;
	private String description;
	private String temperature;
	private String landingImagePath;
	private String cityCountry;
	private String cityCountryName;
	
	public String getLandingImagePath() {
		return landingImagePath;
	}
	public void setLandingImagePath(String landingImagePath) {
		this.landingImagePath = landingImagePath;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getCityCountry() {
		return cityCountry;
	}
	public void setCityCountry(String cityCountry) {
		this.cityCountry = cityCountry;
	}
	public String getCityCountryName() {
		return cityCountryName;
	}
	public void setCityCountryName(String cityCountryName) {
		this.cityCountryName = cityCountryName;
	}
	

}

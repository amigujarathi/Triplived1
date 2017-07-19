package com.connectme.domain.triplived.dto;

public class CityResponseDTO {
	     
	private String id;
	private String cityName;
	private String cityDescription;
	private String temperature;
	private String landingImagePath;
	private String type;
	private String cityCountry;
	private String cityCountryName;
	private String address;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityImagePath() {
		return landingImagePath;
	}
	public void setCityImagePath(String cityImagePath) {
		this.landingImagePath = cityImagePath;
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

	public String getCityDescription() {
		return cityDescription;
	}
	
	public void setCityDescription(String cityDescription) {
		this.cityDescription = cityDescription;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

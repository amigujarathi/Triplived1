package com.connectme.domain.triplived.dto;

import com.connectme.domain.triplived.trip.dto.Trip;

public class TripBetweenCityDTO {
	
	private String id;
	private String sourceCityCode;
	private Integer userId;
	private String destinationCityCode;
	private String userName;
	private Trip tripData;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceCityCode() {
		return sourceCityCode;
	}
	public void setSourceCityCode(String sourceCityCode) {
		this.sourceCityCode = sourceCityCode;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getDestinationCityCode() {
		return destinationCityCode;
	}
	public void setDestinationCityCode(String destinationCityCode) {
		this.destinationCityCode = destinationCityCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Trip getTripData() {
		return tripData;
	}
	public void setTripData(Trip tripData) {
		this.tripData = tripData;
	}

}

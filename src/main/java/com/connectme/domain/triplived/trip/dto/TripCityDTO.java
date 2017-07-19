package com.connectme.domain.triplived.trip.dto;

import java.util.List;

public class TripCityDTO {
	
	private Long subTripId;
	private String cityId;
	private Long timestamp;
    private TripCityType cityType;
    private List<TripPhotoDTO> files;
    private List<TripReviewDTO> reviews;
    private List<TripEventDTO> events;
	private List<TripActivityDTO> activities;
	private List<TripHotelDTO> hotels;
	private String type;
	private String cityName;
	
	public TripCityDTO() {
		// TODO Auto-generated constructor stub
		this.type = "city";
	}
	
    public Long getSubTripId() {
		return subTripId;
	}
	public void setSubTripId(Long subTripId) {
		this.subTripId = subTripId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public TripCityType getCityType() {
		return cityType;
	}
	public void setCityType(TripCityType cityType) {
		this.cityType = cityType;
	}
	public List<TripPhotoDTO> getFiles() {
		return files;
	}
	public void setFiles(List<TripPhotoDTO> files) {
		this.files = files;
	}
	public List<TripReviewDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<TripReviewDTO> reviews) {
		this.reviews = reviews;
	}
	public List<TripEventDTO> getEvents() {
		return events;
	}
	public void setEvents(List<TripEventDTO> events) {
		this.events = events;
	}
	public List<TripActivityDTO> getActivities() {
		return activities;
	}
	public void setActivities(List<TripActivityDTO> activities) {
		this.activities = activities;
	}
	public List<TripHotelDTO> getHotels() {
		return hotels;
	}
	public void setHotels(List<TripHotelDTO> hotels) {
		this.hotels = hotels;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}



	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}

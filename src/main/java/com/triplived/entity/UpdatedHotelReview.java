package com.triplived.entity;

import com.connectme.domain.triplived.RequiredStatus;

public class UpdatedHotelReview{
	
	String tripId;
	String hotelId;
	String hotelName;
	String curate;
	String review;
	String suggestion;
	Long id;
	String timestamp;
	Long hotelDetailsId;
	
	
	
	public Long getHotelDetailsId() {
		return hotelDetailsId;
	}
	public void setHotelDetailsId(Long hotelDetailsId) {
		this.hotelDetailsId = hotelDetailsId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getCurate() {
		return curate;
	}
	public void setCurate(String curate) {
		this.curate = curate;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}

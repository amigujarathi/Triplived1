package com.triplived.entity;

public class CuratedHotelReview{
	
	Long id;
	String tripId;
	String hotelId;
	String review;
	String suggestion;
	String status;
	String source;
	String updateDate;
	String curateBy;
	

	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCurate() {
		return curateBy;
	}
	public void setCurate(String curate) {
		this.curateBy = curate;
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
	public String getCurateBy() {
		return curateBy;
	}
	public void setCurateBy(String curateBy) {
		this.curateBy = curateBy;
	}
	
	
	
}

package com.connectme.domain.triplived.dto;


public class HotelReviewDTO {
	
	private Long tripId;
	private String hotelId;
	private String review;
	private String suggestion;
	private String hotelName;
	private String curate;
	private String timestamp;
	private String hotelDetailsId;
	private String source;
	private String status;
	private String rank;
	
	
	
	
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
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getHotelDetailsId() {
		return hotelDetailsId;
	}
	public void setHotelDetailsId(String hotelDetailsId) {
		this.hotelDetailsId = hotelDetailsId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
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
	
	
	
	
}

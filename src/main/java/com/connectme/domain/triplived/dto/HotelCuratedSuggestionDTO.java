package com.connectme.domain.triplived.dto;

import java.util.Date;

public class HotelCuratedSuggestionDTO {
	
	private Long tripId;
	private String hotelId;
	private String review;
	private String suggestion;
	private String curateBy;
	private Date updateDate;
	private String status;
	private String rank;
	private String source;
	private Long id;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
		this.tripId = tripId;
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
	public String getCurateBy() {
		return curateBy;
	}
	public void setCurateBy(String curateBy) {
		this.curateBy = curateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	
}

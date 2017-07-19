package com.triplived.entity;

public class CuratedAttractionReview{
	
	String tripId;
	String attractionId;
	String curateBy;
	String review;
	String suggestion;
	String source;
	String status;
	Long id;
	String updateDate;
	

	
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCurateBy() {
		return curateBy;
	}
	public void setCurateBy(String curateBy) {
		this.curateBy = curateBy;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(String attractionId) {
		this.attractionId = attractionId;
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
	
	
	
}

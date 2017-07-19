package com.triplived.entity;

import com.connectme.domain.triplived.RequiredStatus;

public class UpdatedAttractionReview{
	
	String tripId;
	String attractionId;
	String attractionName;
	String curate;
	String review;
	String suggestion;
	Long id;
	String timestamp;
	Long attractionDetailsId;
	
	
	
	public Long getAttractionDetailsId() {
		return attractionDetailsId;
	}
	public void setAttractionDetailsId(Long attractionDetailsId) {
		this.attractionDetailsId = attractionDetailsId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	public String getCurate() {
		return curate;
	}
	public void setCurate(String curate) {
		this.curate = curate;
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

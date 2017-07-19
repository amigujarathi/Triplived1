package com.connectme.domain.triplived.dto;

public class AttractionReviewDTO {
	
	private Long tripId;
	private String attractionId;
	private String review;
	private String suggestion;
	private String attractionName;
	private String curate;
	private String timestamp;
	private String attractionDetailsId;
	private String status;
	private String rank;
	private String source;
	
	
	
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
	public String getAttractionDetailsId() {
		return attractionDetailsId;
	}
	public void setAttractionDetailsId(String attractionDetailsId) {
		this.attractionDetailsId = attractionDetailsId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getCurate() {
		return curate;
	}
	public void setCurate(String curate) {
		this.curate = curate;
	}
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
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

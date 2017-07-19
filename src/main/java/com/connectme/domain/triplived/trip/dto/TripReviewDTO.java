package com.connectme.domain.triplived.trip.dto;

public class TripReviewDTO {

	private String review;
	private Long timestamp;
	private TripEntityType entityType;
	
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public TripEntityType getEntityType() {
		return entityType;
	}
	public void setEntityType(TripEntityType entityType) {
		this.entityType = entityType;
	}
}

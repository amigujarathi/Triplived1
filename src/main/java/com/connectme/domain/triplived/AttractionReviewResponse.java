package com.connectme.domain.triplived;

import java.util.List;
import java.util.Map;

import com.connectme.domain.triplived.trip.dto.TripEventDTO;

public class AttractionReviewResponse {

	private Long tripId;
	private String attractionId;
	private List<String> suggestion;
	private String review;
	private String recommendation;
	private String timestamp;
	
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
	public List<String> getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(List<String> suggestion) {
		this.suggestion = suggestion;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
		
	
}

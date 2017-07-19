package com.connectme.domain.triplived.dto;

import java.util.List;

public class HotelCrowdSourcedDTO {
	
	private Long tripId;
	private String id;
	private List<HotelCrowdSourcedReviewDTO> reviews;
	private List<String> suggestions;
	private String timestamp;
	private Long recommended;
	private Long nonRecommended;
	
	public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<HotelCrowdSourcedReviewDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<HotelCrowdSourcedReviewDTO> reviews) {
		this.reviews = reviews;
	}
	public List<String> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Long getRecommended() {
		return recommended;
	}
	public void setRecommended(Long recommended) {
		this.recommended = recommended;
	}
	public Long getNonRecommended() {
		return nonRecommended;
	}
	public void setNonRecommended(Long nonRecommended) {
		this.nonRecommended = nonRecommended;
	}
	
	
		
}

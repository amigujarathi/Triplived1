package com.connectme.domain.triplived.dto;

import java.util.List;

public class AttractionCrowdSourcedDTO {
	
	private Long tripId;
	private String id;
	private List<AttractionCrowdSourcedReviewDTO> reviews;
	private List<String> suggestions;
	private String attractionName;
	private String timestamp;
	private String attractionDetailsId;
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
	public List<AttractionCrowdSourcedReviewDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<AttractionCrowdSourcedReviewDTO> reviews) {
		this.reviews = reviews;
	}
	public List<String> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAttractionDetailsId() {
		return attractionDetailsId;
	}
	public void setAttractionDetailsId(String attractionDetailsId) {
		this.attractionDetailsId = attractionDetailsId;
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

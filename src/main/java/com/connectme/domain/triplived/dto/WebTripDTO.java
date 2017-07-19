package com.connectme.domain.triplived.dto;

import java.util.List;

public class WebTripDTO {

	private List<WebExperienceDTO> experiences;
	private String tripName;
	private String tripSummary;
	private List<String> tripCategory;
	private String tripCoverUri;
	private Long userId;
	
	public List<WebExperienceDTO> getExperiences() {
		return experiences;
	}
	public void setExperiences(List<WebExperienceDTO> experiences) {
		this.experiences = experiences;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public String getTripSummary() {
		return tripSummary;
	}
	public void setTripSummary(String tripSummary) {
		this.tripSummary = tripSummary;
	}
	public List<String> getTripCategory() {
		return tripCategory;
	}
	public void setTripCategory(List<String> tripCategory) {
		this.tripCategory = tripCategory;
	}
	public String getTripCoverUri() {
		return tripCoverUri;
	}
	public void setTripCoverUri(String tripCoverUri) {
		this.tripCoverUri = tripCoverUri;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}

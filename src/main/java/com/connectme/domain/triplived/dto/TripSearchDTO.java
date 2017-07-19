package com.connectme.domain.triplived.dto;

import java.util.List;

public class TripSearchDTO {
	
	private String tripName;
	private String userName;
	private String tripId;
	private String oldTripId;
	private String tripUri;
	private String userId;
	private String userFbId;
	private List<CityResponseDTO> tripCities;
	private String tripLikes;
	private String tripComments;
	private String tripImages;
	private String tripDuration;
	private List<String> categories;
	private Boolean tripSearchable;
	private String type;
	private String source;
	
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTripComments() {
		return tripComments;
	}
	public void setTripComments(String tripComments) {
		this.tripComments = tripComments;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getOldTripId() {
		return oldTripId;
	}
	public void setOldTripId(String oldTripId) {
		this.oldTripId = oldTripId;
	}
	public String getTripUri() {
		return tripUri;
	}
	public void setTripUri(String tripUri) {
		this.tripUri = tripUri;
	}
	public String getTripImages() {
		return tripImages;
	}
	public void setTripImages(String tripImages) {
		this.tripImages = tripImages;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserFbId() {
		return userFbId;
	}
	public void setUserFbId(String userFbId) {
		this.userFbId = userFbId;
	}
	public List<CityResponseDTO> getTripCities() {
		return tripCities;
	}
	public void setTripCities(List<CityResponseDTO> tripCities) {
		this.tripCities = tripCities;
	}
	public String getTripLikes() {
		return tripLikes;
	}
	public void setTripLikes(String tripLikes) {
		this.tripLikes = tripLikes;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public String getTripDuration() {
		return tripDuration;
	}
	public void setTripDuration(String tripDuration) {
		this.tripDuration = tripDuration;
	}
	public Boolean getTripSearchable() {
		return tripSearchable;
	}
	public void setTripSearchable(Boolean tripSearchable) {
		this.tripSearchable = tripSearchable;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}

}

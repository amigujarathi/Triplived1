package com.connectme.domain.triplived.dto;

import java.util.List;

public class TrendingTripDTO implements Comparable<TrendingTripDTO>{

	private String tripName;
	private String userName;
	private String userId;
	private String userFbId;
	private String tripLikes;
	private String tripComments;
	private String tripImages;
	private String tripUri;
	private String tripSrc;
	private String tripDest;
	private String tripId;
	private String oldTripId;
	private String tripDuration;
	private Long points;
	private String type;
	private List<CityResponseDTO> tripCities;
	private List<String> categories;
	private String tripSummary;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUserFbId(String userFbId) {
		this.userFbId = userFbId;
	}
	public String getTripComments() {
		return tripComments;
	}
	public void setTripComments(String tripComments) {
		this.tripComments = tripComments;
	}
	public String getOldTripId() {
		return oldTripId;
	}
	public void setOldTripId(String oldTripId) {
		this.oldTripId = oldTripId;
	}
	public String getTripLikes() {
		return tripLikes;
	}
	public void setTripLikes(String tripLikes) {
		this.tripLikes = tripLikes;
	}
	public String getTripUri() {
		return tripUri;
	}
	public void setTripUri(String tripUri) {
		this.tripUri = tripUri;
	}
	
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public String getTripSrc() {
		return tripSrc;
	}
	public void setTripSrc(String tripSrc) {
		this.tripSrc = tripSrc;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public String getTripDest() {
		return tripDest;
	}
	public void setTripDest(String tripDest) {
		this.tripDest = tripDest;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getTripDuration() {
		return tripDuration;
	}
	public void setTripDuration(String tripDuration) {
		this.tripDuration = tripDuration;
	}
	public List<CityResponseDTO> getTripCities() {
		return tripCities;
	}
	public void setTripCities(List<CityResponseDTO> tripCities) {
		this.tripCities = tripCities;
	}
	public String getTripSummary() {
		return tripSummary;
	}
	public void setTripSummary(String tripSummary) {
		this.tripSummary = tripSummary;
	}
	
	@Override
	public int compareTo(TrendingTripDTO o) {
		if(this.points < o.points) {
			return 1;
		} else if (this.points > o.points) {
			return -1;
		}else {
			return 0;
		}
	}
	
	
	}

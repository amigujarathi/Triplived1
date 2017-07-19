package com.connectme.domain.triplived.trip.dto;

import java.util.List;

public class Trip {

	private Long userId;
	private Long tripId;
	private List<SubTrip> subTrips;
	private String tripName;
	private Long tripStartTime;
	private Long tripEndTime;
	private Boolean complete;
	private String tripCoverUri;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	public List<SubTrip> getSubTrips() {
		return subTrips;
	}
	public void setSubTrips(List<SubTrip> subTrips) {
		this.subTrips = subTrips;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public Long getTripStartTime() {
		return tripStartTime;
	}
	public void setTripStartTime(Long tripStartTime) {
		this.tripStartTime = tripStartTime;
	}
	public Long getTripEndTime() {
		return tripEndTime;
	}
	public void setTripEndTime(Long tripEndTime) {
		this.tripEndTime = tripEndTime;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	public String getTripCoverUri() {
		return tripCoverUri;
	}
	public void setTripCoverUri(String tripCoverUri) {
		this.tripCoverUri = tripCoverUri;
	}
	
}

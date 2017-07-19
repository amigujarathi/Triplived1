package com.connectme.domain.triplived.dto;


public class TripSummaryDTO {
	
	private String srcCity;
	
	
	private String summary;
	private String category;
	//private String travelAgent;
	private String tripName;
	private String coverUri;
	private String tripId;
	
	public String getSrcCity() {
		return srcCity;
	}
	public void setSrcCity(String srcCity) {
		this.srcCity = srcCity;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	/*public String getTravelAgent() {
		return travelAgent;
	}
	public void setTravelAgent(String travelAgent) {
		this.travelAgent = travelAgent;
	}*/
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	/*public String getTripCoverUri() {
		return tripCoverUri;
	}
	public void setTripCoverUri(String tripCoverUri) {
		this.tripCoverUri = tripCoverUri;
	}*/
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getCoverUri() {
		return coverUri;
	}
	public void setCoverUri(String coverUri) {
		this.coverUri = coverUri;
	}
	

}

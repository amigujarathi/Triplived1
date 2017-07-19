package com.connectme.domain.triplived.dto;

	
public class NotificationDTO {
	private String id;
	private String name;
	private String punchline;
	private String type;
	private String localId;
	private String messageType;
	private String tripCoverUri;
	private String tripId;
	private String tripIdNew;
	private String tripName;
	private String pageUrl;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPunchline() {
		return punchline;
	}
	public void setPunchline(String punchline) {
		this.punchline = punchline;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getTripCoverUri() {
		return tripCoverUri;
	}
	public void setTripCoverUri(String tripCoverUri) {
		this.tripCoverUri = tripCoverUri;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getTripIdNew() {
		return tripIdNew;
	}
	public void setTripIdNew(String tripIdNew) {
		this.tripIdNew = tripIdNew;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	@Override
	public String toString() {
		return "NotificationDTO [id=" + id + ", name=" + name + ", punchline="
				+ punchline + ", type=" + type + ", localId=" + localId
				+ ", messageType=" + messageType + ", tripCoverUri="
				+ tripCoverUri + ", tripId=" + tripId + ", tripName="
				+ tripName + "]";
	}
	

}

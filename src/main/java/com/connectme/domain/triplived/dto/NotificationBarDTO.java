package com.connectme.domain.triplived.dto;

import java.util.Date;

public class NotificationBarDTO implements Comparable<NotificationBarDTO>{
	
	private String userName;
	private String userId;
	private String type;
	private String entityType;
	private String message;
	private String tripId;
	private String tripIdNew;
	private String tripName;
	private Boolean newMessage;
	private Date updatedDate;
	private Long timeStamp;
	private Integer count;
	private String tripCoverUri;
	private String mediaId;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTripIdNew() {
		return tripIdNew;
	}
	public void setTripIdNew(String tripId) {
		this.tripIdNew = tripId;
	}
	public Boolean getNewMessage() {
		return newMessage;
	}
	public void setNewMessage(Boolean newMessage) {
		this.newMessage = newMessage;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTripCoverUri() {
		return tripCoverUri;
	}
	public void setTripCoverUri(String tripCoverUri) {
		this.tripCoverUri = tripCoverUri;
	}
	public Integer getCount() {
		return count;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@Override
	public int compareTo(NotificationBarDTO o) {
		if(null != this.timeStamp && null != o.timeStamp) {
			if(this.timeStamp < o.timeStamp) {
				return 1;
			} else if (this.timeStamp > o.timeStamp) {
				return -1;
			}else {
				return 0;
			}
		}else {
			return 0;
		}
	}
	

}

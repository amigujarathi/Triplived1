package com.connectme.domain.triplived.dto;

public class FollowNotificationDTO extends NotificationDTO {

	private String followByUser;
	private String followByUserId;

	public String getFollowByUser() {
		return followByUser;
	}

	public void setFollowByUser(String followByUser) {
		this.followByUser = followByUser;
	}
	
	public String getFollowByUserId() {
		return followByUserId;
	}
	
	public void setFollowByUserId(String followByUserId) {
		this.followByUserId = followByUserId;
	}
	
}

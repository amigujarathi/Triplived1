package com.connectme.domain.triplived.dto;

public class LikeNotificationDTO extends NotificationDTO {

	private Long likedByUserId;
	private String likedByUser;

	
	public Long getLikedByUserId() {
		return likedByUserId;
	}
	public String getLikedByUser() {
		return likedByUser;
	}

	public void setLikedByUserId(Long likedByUserId) {
		this.likedByUserId = likedByUserId;
	}
	
	public void setLikedByUser(String likedByUser) {
		this.likedByUser = likedByUser;
	}
}

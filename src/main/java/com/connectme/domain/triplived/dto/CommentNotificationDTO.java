package com.connectme.domain.triplived.dto;

public class CommentNotificationDTO extends NotificationDTO {

	private String commentByUser;

	public String getCommentByUser() {
		return commentByUser;
	}

	public void setCommentByUser(String commentByUser) {
		this.commentByUser = commentByUser;
	}
}

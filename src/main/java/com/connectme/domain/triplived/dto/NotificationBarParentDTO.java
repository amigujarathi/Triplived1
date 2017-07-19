package com.connectme.domain.triplived.dto;

import java.util.List;

public class NotificationBarParentDTO {
	
	private Integer newMsgCount;
	private List<NotificationBarDTO> notifications;
	
	public Integer getNewMsgCount() {
		return newMsgCount;
	}
	public void setNewMsgCount(Integer newMsgCount) {
		this.newMsgCount = newMsgCount;
	}
	public List<NotificationBarDTO> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<NotificationBarDTO> notifications) {
		this.notifications = notifications;
	}
	
	

}

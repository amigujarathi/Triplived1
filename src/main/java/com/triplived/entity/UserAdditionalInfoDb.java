package com.triplived.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_additional_info")
public class UserAdditionalInfoDb {

	@Id
	@Column(name="USER_ID")
    private Long userId;
	
	@Column(name="LAST_SEEN_MODIFICATION_TIME")
	private Long lastSeenNotificationTimestamp;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getLastSeenNotificationTimestamp() {
		return lastSeenNotificationTimestamp;
	}
	public void setLastSeenNotificationTimestamp(Long lastSeenNotificationTimestamp) {
		this.lastSeenNotificationTimestamp = lastSeenNotificationTimestamp;
	}
	
	 
	
}
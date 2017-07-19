package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="user_dynamic")
public class UserDynamicDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="USER_ID")
	private Long userId;
	
	@Column(name="DEVICE_ID")
	private String deviceId;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createDate;
	
	@Column(name = "FB_ACCESS_TOKEN")
	private String fbAccessToken;
	
	
	public String getFbAccessToken() {
		return fbAccessToken;
	}

	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	

}

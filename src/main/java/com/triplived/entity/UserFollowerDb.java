package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_followers")
public class UserFollowerDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="FOLLOW_USER_ID")
	private Long userFollowId;
	
	@Column(name="FOLLOWING_USER_ID")
	private Long userFollowingId;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getUserFollowId() {
		return userFollowId;
	}

	public void setUserFollowId(Long userFollowId) {
		this.userFollowId = userFollowId;
	}

	public Long getUserFollowingId() {
		return userFollowingId;
	}

	public void setUserFollowingId(Long userFollowingId) {
		this.userFollowingId = userFollowingId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

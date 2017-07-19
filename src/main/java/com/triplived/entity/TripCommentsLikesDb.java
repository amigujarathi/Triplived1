package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Storing likes associated with trip comments
 * 
 * @author santosh Joshi
 *
 */
@Entity
@Table(name="trip_comments_likes")
public class TripCommentsLikesDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="USER_ID")
	private Long userID;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="COMMENT_ID")
	private Long commentId;
	
	//public private active deleted 
	@Column(name="STATUS")
	private String status;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	
	 public Long getCommentId() {
		return commentId;
	}
	 
	 public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	 
}

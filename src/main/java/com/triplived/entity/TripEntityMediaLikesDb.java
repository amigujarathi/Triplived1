package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.domain.triplived.trip.dto.Status;

/**
 * Storing likes associated with photos
 * 
 * @author santosh Joshi
 *
 */
@Entity
@Table(name="trip_media_likes")
public class TripEntityMediaLikesDb {

	
	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="USER_ID")
	private Long userID;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="MEDIA_ID")
	private String mediaId;
	
	//public private active deleted 
	@Column(name = "STATUS", length = 20)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="TIMESTAMP")
	private Long timeStamp;

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

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	 
}

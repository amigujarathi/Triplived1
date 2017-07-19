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
 * Storing likes associated with entities
 * 
 * @author santosh Joshi
 *
 */
@Entity
@Table(name="trip_entity_likes")
public class TripEntityLikesDb {

	
	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="USER_ID")
	private Long userID;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="TRIP_ENTITY_ID")
	private Long tripEntityId;
	
	@Column(name = "STATUS", length = 20)
	@Enumerated(EnumType.STRING)
	private Status status;
	
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

	public Long getTripEntityId() {
		return tripEntityId;
	}
	
	public void setTripEntityId(Long tripEntityId) {
		this.tripEntityId = tripEntityId;
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
	 
}

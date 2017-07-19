package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.connectme.domain.triplived.RequiredStatus;

@Entity
@Table(name="trip_experiences")
public class TripExperiencesDb {

	@Id
	@Column(name="TRIP_ENTITY_ID")
	private Long tripEntityId;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="ENTITY_ID")
	private String entityId;
		
	@Column(name="EXPERIENCE")
	private String experience;
	
	@Column(name="TIMESTAMP")
	private Long timeStamp;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "STATUS", length = 2)
	private String status;
	
	@Column(name="ENTITY_TYPE")
	private String entityType;

	public Long getTripEntityId() {
		return tripEntityId;
	}

	public void setTripEntityId(Long tripEntityId) {
		this.tripEntityId = tripEntityId;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

}


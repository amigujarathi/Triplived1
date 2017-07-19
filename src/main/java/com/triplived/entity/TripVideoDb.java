package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.connectme.domain.triplived.VideoStatus;
import com.triplived.controller.profile.UserFrom;

@Entity
@Table(name="trip_video")
public class TripVideoDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name = "STATUS", length = 50)
	@Enumerated(EnumType.STRING)
	private VideoStatus status;
	
	public Date getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getTripId() {
		return tripId;
	}



	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	
	public VideoStatus getVideoStatus() {
		return status;
	}
	public void setvideoStatus(VideoStatus status) {
		this.status = status;
	}

	public VideoStatus getStatus() {
		return status;
	}



}

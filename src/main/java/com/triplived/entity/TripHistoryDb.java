package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="trip_history")
public class TripHistoryDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="TRIP_DATA")
	private String tripData;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
    @Column(name = "CREATED_DATE")
	private Date createDate;
	
    @Column(name = "PUBLIC_ID")
    private Long tripPublicId;
    
    @Column(name = "UPDATED_USER_ID")
    private Long updatedUserId;
    
    @Column(name = "SUBMIT_TYPE")
    private String submitType;//OWNER OR REVIEWER

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getTripData() {
		return tripData;
	}

	

	public Date getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Long getTripPublicId() {
		return tripPublicId;
	}

	public void setTripPublicId(Long tripPublicId) {
		this.tripPublicId = tripPublicId;
	}

	public Long getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public void setTripData(String tripData) {
		this.tripData = tripData;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

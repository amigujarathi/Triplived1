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
@Table(name="travel_partner_trip_mapping")
public class TravelPartnerTripMappingDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	
    @Column(name="TRAVEL_PARTNER_ID")
    private Long travelPartnerId;
    

    @Column(name="TRIP_ID")
    private Long tripId;
	
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

	public Long getTravelPartnerId() {
		return travelPartnerId;
	}

	public void setTravelPartnerId(Long travelPartnerId) {
		this.travelPartnerId = travelPartnerId;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
	
	

}

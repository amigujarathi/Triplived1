package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="trip_city")
public class TripCityDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="FROM_CITY_ID")
	private String fromCityId;
	
	@Column(name="TO_CITY_ID")
	private String toCityId;
	
	@Column(name="STOPOVER_CITY_IDs")
	private String stopoverCityIds;
	
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

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getFromCityId() {
		return fromCityId;
	}

	public void setFromCityId(String fromCityId) {
		this.fromCityId = fromCityId;
	}

	public String getToCityId() {
		return toCityId;
	}

	public void setToCityId(String toCityId) {
		this.toCityId = toCityId;
	}

	public String getStopoverCityIds() {
		return stopoverCityIds;
	}

	public void setStopoverCityIds(String stopoverCityIds) {
		this.stopoverCityIds = stopoverCityIds;
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

	
}

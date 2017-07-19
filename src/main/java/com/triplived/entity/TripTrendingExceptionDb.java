package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="trip_trending_exception")
public class TripTrendingExceptionDb {
   	
    @Id
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="POINTS")
	private Long points;

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

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}


}

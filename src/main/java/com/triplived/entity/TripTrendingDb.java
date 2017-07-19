package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="trip_trending")
public class TripTrendingDb {

	
   	
    @Id
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="TRIP_LIKE_COUNT")
	private Long tripLikeCount;
	
	
	@Column(name="TRIP_COMMENT_COUNT")
	private Long tripCommentCount;
	
	
	@Column(name="TRIP_SHARE_COUNT")
	private Long tripShareCount;
	

	@Column(name="POINTS")
	private Long points;
	
	@Column(name="TYPE")
	private String type;

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

	public Long getTripLikeCount() {
		return tripLikeCount;
	}

	public void setTripLikeCount(Long tripLikeCount) {
		this.tripLikeCount = tripLikeCount;
	}

	public Long getTripCommentCount() {
		return tripCommentCount;
	}

	public void setTripCommentCount(Long tripCommentCount) {
		this.tripCommentCount = tripCommentCount;
	}

	public Long getTripShareCount() {
		return tripShareCount;
	}

	public void setTripShareCount(Long tripShareCount) {
		this.tripShareCount = tripShareCount;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

package com.triplived.entity;

import com.connectme.domain.triplived.RequiredStatus;

public class TripReviewData{
	
	String tripId;
	String reviewer_id;
	String review;
	String status;
	String updatedDate;
	String remark;
	Long id;
	String entityName;
	
	
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getReviewer_id() {
		return reviewer_id;
	}
	public void setReviewer_id(String reviewer_id) {
		this.reviewer_id = reviewer_id;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

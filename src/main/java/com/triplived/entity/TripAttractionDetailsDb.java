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
@Table(name="trip_attraction_details")
public class TripAttractionDetailsDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="TRIP_ENTITY_ID")
	private Long tripEntityId;
	
	@Column(name="ATTRACTION_ID")
	private String attractionId;
	
	@Column(name="REVIEW")
	private String review;
	
	@Column(name="SUGGESTION")
	private String suggestion;
	
	@Column(name="EXPERIENCE")
	private String experience;
	
	@Column(name="TIMESTAMP")
	private Long timeStamp;
	
	@Column(name="REVIEW_COUNT")
	private Long reviewCount;
	
	@Column(name="VISIT_TIME")
	private String visitTime;
	
	@Column(name = "RECOMMENDED", length = 2)
	@Enumerated(EnumType.STRING)
	private RequiredStatus recommended;
	
	@Column(name = "GUIDE_REQUIRED", length = 2)
	@Enumerated(EnumType.STRING)
	private RequiredStatus guideRequired;
	
	@Column(name = "CURATED", length = 2)
	@Enumerated(EnumType.STRING)
	private RequiredStatus curated;
	
	@Column(name="CURATED_BY")
	private String curatedBy;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "STATUS", length = 2)
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

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(String attractionId) {
		this.attractionId = attractionId;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Long getTripEntityId() {
		return tripEntityId;
	}

	public void setTripEntityId(Long tripEntityId) {
		this.tripEntityId = tripEntityId;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public RequiredStatus getRecommended() {
		return recommended;
	}

	public void setRecommended(RequiredStatus recommended) {
		this.recommended = recommended;
	}

	public RequiredStatus getGuideRequired() {
		return guideRequired;
	}

	public void setGuideRequired(RequiredStatus guideRequired) {
		this.guideRequired = guideRequired;
	}

	public RequiredStatus getCurated() {
		return curated;
	}

	public void setCurated(RequiredStatus curated) {
		this.curated = curated;
	}

	public String getCuratedBy() {
		return curatedBy;
	}

	public void setCuratedBy(String curatedBy) {
		this.curatedBy = curatedBy;
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

}


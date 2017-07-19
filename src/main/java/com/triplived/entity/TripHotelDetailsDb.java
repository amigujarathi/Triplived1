package com.triplived.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.connectme.domain.triplived.RequiredStatus;

@Entity
@Table(name="trip_hotel_details")
public class TripHotelDetailsDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="TRIP_ENTITY_ID")
	private Long tripEntityId;
	
	@Column(name="HOTEL_ID")
	private String hotelId;
	
	@Column(name="TIMESTAMP")
	private Long timestamp;
	
	@Column(name="REVIEW")
	private String review;
	
	@Column(name="SUGGESTION")
	private String suggestion;
	
	@Column(name="EXPERIENCE")
	private String experience;
		
	@Column(name = "RECOMMENDED", length = 2)
	@Enumerated(EnumType.STRING)
	private RequiredStatus recommended;
	
	@Column(name="RECOMMENDED_REASON")
	private String recommendedReason;
	
	@Column(name = "CURATED", length = 2)
	@Enumerated(EnumType.STRING)
	private RequiredStatus curated;
	
	@Column(name="CURATED_BY")
	private String curatedBy;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="REVIEW_COUNT")
	private Long reviewCount;

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

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getTripEntityId() {
		return tripEntityId;
	}

	public void setTripEntityId(Long tripEntityId) {
		this.tripEntityId = tripEntityId;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getRecommendedReason() {
		return recommendedReason;
	}

	public void setRecommendedReason(String recommendedReason) {
		this.recommendedReason = recommendedReason;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	
	public RequiredStatus getRecommended() {
		return recommended;
	}

	public void setRecommended(RequiredStatus recommended) {
		this.recommended = recommended;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

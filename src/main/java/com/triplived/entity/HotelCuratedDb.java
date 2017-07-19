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
@Table(name = "hotel_curated_suggestions")
public class HotelCuratedDb {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column(name = "TRIP_ID")
	private Long tripId;

	@Column(name = "HOTEL_ID")
	private String hotelId;
	
	@Column(name = "HOTEL_REVIEW_ID")
	private String hotelReviewId;
	
	@Column(name = "REVIEW")
	private String review;

	@Column(name = "SUGGESTION")
	private String suggestion;

	@Column(name = "CURATED_BY")
	private String curatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updateDate;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "STATUS", length = 2)
	private String status;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "RANK")
	private Long rank;

	
	
	
	public String getHotelReviewId() {
		return hotelReviewId;
	}

	public void setHotelReviewId(String hotelReviewId) {
		this.hotelReviewId = hotelReviewId;
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

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	
}

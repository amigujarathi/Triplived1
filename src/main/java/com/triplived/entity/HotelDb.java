package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="hotel")
public class HotelDb {

	@Id
    @Column(name="HOTEL_ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TA_HOTEL_ID")
    private String taHotelId;
	
	@Column(name="CITY_CODE")
	private String cityCode;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="STREET")
	private String street;
	
	@Column(name="LATITUDE")
	private Double latitude;
	
	@Column(name="LONGITUDE")
	private Double longitude;
	
	@Column(name="PINCODE")
	private String pinCode;
	
	@Column(name="TA_HOTEL_RANK")
	private Double taHotelRank;
	
	@Column(name="OFFICIAL_DESCRIPTION")
	private String description;
	
	@Column(name="star_rating")
	private String starRating;
	
	@Column(name="LOCALITY")
	private String locality;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="IMAGES")
	private String images;
	
	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@Column(name="STATUS")
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Double getTaHotelRank() {
		return taHotelRank;
	}

	public void setTaHotelRank(Double taHotelRank) {
		this.taHotelRank = taHotelRank;
	}

	public String getTaHotelId() {
		return taHotelId;
	}

	public void setTaHotelId(String taHotelId) {
		this.taHotelId = taHotelId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStarRating() {
		return starRating;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
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

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
}

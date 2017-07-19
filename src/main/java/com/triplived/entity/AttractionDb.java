package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="attraction")
public class AttractionDb {
	
	@Id
    @Column(name="ATTRACTION_ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="LATITUDE")
	private Double latitude;
	
	@Column(name="LONGITUDE")
	private Double longitude;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="PUNCHLINE")
	private String attractionPunchLine;
	
	@Column(name="CITY_CODE")
	private String cityCode;


	@Column(name = "STATUS", length = 2)
	private String status;
	
	@Column(name = "UPDATED_BY", length = 60)
	private String updatedBy;
	
	
	@Column(name="state")
	private String state;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="ADDRESS1")
	private String address;
	
	@Column(name="TIMINGS")
	private String timings;
	
	@Column(name="BESTTIME")
	private String bestTime;
	
	@Column(name="REQTIME")
	private String reqTime;
	
	@Column(name="PHONE_NUMBER")
	private String phone;
	
	@Column(name="WEBSITE")
	private String webSite;
	
	@Column(name="TICKET_PRICE")
	private String ticket;
	
	@Column(name="TA_ATTRACTION_ID")
	private String ta_attraction_id;
	
	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getTimings() {
		return timings;
	}

	public void setTimings(String timings) {
		this.timings = timings;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTa_attraction_id() {
		return ta_attraction_id;
	}

	public void setTa_attraction_id(String ta_attraction_id) {
		this.ta_attraction_id = ta_attraction_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAttractionPunchLine() {
		return attractionPunchLine;
	}
	
	public void setAttractionPunchLine(String attractionPunchLine) {
		this.attractionPunchLine = attractionPunchLine;
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}

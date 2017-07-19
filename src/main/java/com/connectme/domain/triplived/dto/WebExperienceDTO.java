package com.connectme.domain.triplived.dto;

import java.util.Date;
import java.util.Set;

import com.domain.triplived.trip.dto.File;

public class WebExperienceDTO {
	
	private String experienceId;
	private Long tripId;
	private Long userId;
	private String location;
	private String city;
	private String cityId;
	private String emotion;
	private String activity;
	private String experience;
	private Long timeStamp;
	private Date updateDate;
	private Date createdDate;
	private String status;
	private String entityType;
	private Long orderNo;
	private Long day;
	private String readableDate;
	private Set<WebImageDTO> media;
	private String sourceExperienceId;
	private String destExperienceId;
	private String transportType;
	private String heading;
	private String shortDescription;
	
	public String getExperienceId() {
		return experienceId;
	}
	public void setExperienceId(String experienceId) {
		this.experienceId = experienceId;
	}
	public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
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
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getDay() {
		return day;
	}
	public void setDay(Long day) {
		this.day = day;
	}
	public Set<WebImageDTO> getMedia() {
		return media;
	}
	public void setMedia(Set<WebImageDTO> media) {
		this.media = media;
	}
	public String getReadableDate() {
		return readableDate;
	}
	public void setReadableDate(String readableDate) {
		this.readableDate = readableDate;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getSourceExperienceId() {
		return sourceExperienceId;
	}
	public void setSourceExperienceId(String sourceExperienceId) {
		this.sourceExperienceId = sourceExperienceId;
	}
	public String getDestExperienceId() {
		return destExperienceId;
	}
	public void setDestExperienceId(String destExperienceId) {
		this.destExperienceId = destExperienceId;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
}

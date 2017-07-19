package com.triplived.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.connectme.domain.triplived.RequiredStatus;

/**
 * These experiences are the ones that are entered from the trip creation widget on web
 * @author ayan
 *
 */

@Entity
@Table(name="web_experiences")
public class WebExperiencesDb implements Comparable<WebExperiencesDb>{

	@Id
	@Column(name="EXPERIENCE_ID")
	private String experienceId;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="USER_ID")
	private Long userId;
	
	@Column(name="LOCATION")
	private String location;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="CITY_ID")
	private String cityId;
	
	@Column(name="EMOTION")
	private String emotion;
	
	@Column(name="ACTIVITY")
	private String activity;
		
		
	@Column(name="EXPERIENCE")
	private String experience;
	
	@Column(name="TIMESTAMP")
	private Long timeStamp;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "STATUS", length = 2)
	private String status;
	
	@Column(name="ENTITY_TYPE")
	private String entityType;
	
	@Column(name="ORDER_NO")
	private Long orderNo;
	
	@Column(name="DAY")
	private Long day;
	
	@Column(name="S_ID")
	private String sourceExperienceId;
	
	@Column(name="D_ID")
	private String destExperienceId;
	
	@Column(name="TRANSPORT_TYPE")
	private String transportType;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "webExperiencesDb", cascade= CascadeType.ALL)
	private Set<WebExperiencesMediaDb> media = new HashSet<WebExperiencesMediaDb>(0);
	

	public Set<WebExperiencesMediaDb> getMedia() {
		return media;
	}

	public void setMedia(Set<WebExperiencesMediaDb> media) {
		this.media = media;
	}

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long day) {
		this.day = day;
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

	@Override
	public int compareTo(WebExperiencesDb obj) {
		if(this.timeStamp >= obj.timeStamp) {
			return 1;
		} else {
			return -1;
		}
	}
	

}


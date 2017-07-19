package com.triplived.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="trip_entity")
public class TripEntityDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="ENTITY_ID")
	private String entityId;
	
	@Column(name="ENTITY_TIMESTAMP")
	private Long entityTimestamp;
	
	@Column(name="ENTITY_TYPE")
	private String entityType;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="ENTITY_CITYCODE")
	private String entityCityCode;
	
	@Column(name="ENTITY_NAME")
	private String entityName;
	
	@Column(name="ENTITY_LAT")
	private Double entityLat;
	
	@Column(name="ENTITY_LNG")
	private Double entityLng;
	
	@Column(name="STATUS")
	private String status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tripEntityDb", cascade= CascadeType.ALL)
	private Set<TripEntityMediaDb> tripMedia = new HashSet<TripEntityMediaDb>(0);
	
	 public Set<TripEntityMediaDb> getTripMedia() {
		return tripMedia;
	}
	
	 public void setTripMedia(Set<TripEntityMediaDb> tripMedia) {
		this.tripMedia = tripMedia;
	}
	 
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String string) {
		this.entityName = string;
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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Long getEntityTimestamp() {
		return entityTimestamp;
	}

	public void setEntityTimestamp(Long entityTimestamp) {
		this.entityTimestamp = entityTimestamp;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getEntityCityCode() {
		return entityCityCode;
	}

	public void setEntityCityCode(String entityCityCode) {
		this.entityCityCode = entityCityCode;
	}


	public Double getEntityLat() {
		return entityLat;
	}

	public void setEntityLat(Double entityLat) {
		this.entityLat = entityLat;
	}

	public Double getEntityLng() {
		return entityLng;
	}

	public void setEntityLng(Double entityLng) {
		this.entityLng = entityLng;
	}

	@Override
	public String toString() {
		return "TripEntityDb [id=" + id + ", tripId=" + tripId + ", entityId="
				+ entityId + ", entityTimestamp=" + entityTimestamp
				+ ", entityType=" + entityType + ", createdDate=" + createdDate
				+ ", updateDate=" + updateDate + ", entityCityCode="
				+ entityCityCode + ", entityName=" + entityName
				+ ", entityLat=" + entityLat + ", entityLng=" + entityLng
				+ ", status=" + status + ", tripMedia=" + tripMedia + "]";
	}
	
	

	
}

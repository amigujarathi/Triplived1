package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Storing photos associated with entities 
 * 
 * @author santosh Joshi
 *
 */
@Entity
@Table(name="trip_media")
public class TripEntityMediaDb {

	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="ENTITY_ID")
	private String entityId;
	
	@Id
	@Column(name="MEDIA_ID")
	private String mediaId;
	
	@Column(name="PATH")
	private String mediaPath;
	
	@Column(name="CAPTION")
	private String caption;
	
	@Column(name="TIMESTAMP")
	private Long entityTimestamp;
	
	@Column(name="ENTITY_TYPE")
	private String entityType;
	
	@Column(name="LAT")
	private Double latitude;
	
	@Column(name="LNG")
	private Double longitude;
	
	//public private active deleted 
	@Column(name="STATUS")
	private String status;
	
	//Image of video
	@Column(name="MEDIA_TYPE")
	private String mediaType;
	
	@Column(name="OTHERS_DESC")
	private String others;

	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRIP_ENTITY_ID", nullable = false)
	private TripEntityDb tripEntityDb;

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
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaPath() {
		return mediaPath;
	}

	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
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
	
	public void setTripEntityDb(TripEntityDb tripEntityDb) {
		this.tripEntityDb = tripEntityDb;
	}
	
	public TripEntityDb getTripEntityDb() {
		return tripEntityDb;
	}

	@Override
	public String toString() {
		return "TripEntityMediaDb [tripId=" + tripId + ", entityId=" + entityId
				+ ", mediaId=" + mediaId + ", mediaPath=" + mediaPath
				+ ", caption=" + caption + ", entityTimestamp="
				+ entityTimestamp + ", entityType=" + entityType
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", status=" + status + ", mediaType=" + mediaType
				+ ", others=" + others + ", updateDate=" + updateDate
				+ ", createdDate=" + createdDate + ", tripEntityDb="
				+ tripEntityDb + "]";
	}
	
	
 
}

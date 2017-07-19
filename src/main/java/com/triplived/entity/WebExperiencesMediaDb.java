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
 * Storing photos associated with web experiences 
 * 
 * 
 *
 */
@Entity
@Table(name="web_experiences_media")
public class WebExperiencesMediaDb {

	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Id
	@Column(name="MEDIA_ID")
	private String mediaId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXPERIENCE_ID", nullable = false)
	private WebExperiencesDb webExperiencesDb;
	
	@Column(name="PATH")
	private String mediaPath;
	
	@Column(name="CAPTION")
	private String caption;
	
	@Column(name="TIMESTAMP")
	private Long entityTimestamp;
	
	@Column(name="ENTITY_TYPE")
	private String entityType;
	
	//public private active deleted 
	@Column(name="STATUS")
	private String status;
	
	@Column(name="SMALL")
	private Boolean small;
	
	@Column(name="MEDIUM")
	private Boolean medium;
	
	//Image of video
	@Column(name="MEDIA_TYPE")
	private String mediaType;

	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
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

	public WebExperiencesDb getWebExperiencesDb() {
		return webExperiencesDb;
	}

	public void setWebExperiencesDb(WebExperiencesDb webExperiencesDb) {
		this.webExperiencesDb = webExperiencesDb;
	}

	public Boolean getSmall() {
		return small;
	}

	public void setSmall(Boolean small) {
		this.small = small;
	}

	public Boolean getMedium() {
		return medium;
	}

	public void setMedium(Boolean medium) {
		this.medium = medium;
	}

 
}

package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fb_checkin_posts")
public class FbCheckInPostsDb {

	
	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	
	@Column(name="PUBLISH_ID")
	private String publishId;
	
	@Column(name="PUBLISH_TYPE")
	private String publishType;
	
	@Column(name="ENTITY_ID")
	private Long entityId;
	
	@Column(name="ENTITY_NAME")
	private String entityName;
	
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="TRIP_ID")
	private Long tripId;
	
	@Column(name="POST_IMAGE_URL")
	private String postImageUrl;
	
	@Column(name="STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getPostImageUrl() {
		return postImageUrl;
	}

	public void setPostImageUrl(String postImageUrl) {
		this.postImageUrl = postImageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

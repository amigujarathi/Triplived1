package com.triplived.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="trip_status")
public class TripStatusDb {
	
	@Id
	@Column(name="TRIP_ID")
	private Long id;
	
	
	@Column(name="PUBLIC_ID")
	private Long publicId;
	 
	
	@Column(name="LAST_UPDATED_TS")
	private Long lastUpdatedTs;
	
	@Column(name="UPDATE_TYPE")
	private String updateType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPublicId() {
		return publicId;
	}

	public void setPublicId(Long publicId) {
		this.publicId = publicId;
	}

	public Long getLastUpdatedTs() {
		return lastUpdatedTs;
	}

	public void setLastUpdatedTs(Long lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	 
	
}

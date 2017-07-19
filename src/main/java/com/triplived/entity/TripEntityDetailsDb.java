package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="trip_entity_details")
public class TripEntityDetailsDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	
	@Column(name="ENTITY_ID")
	private String entityId;
	
	@Column(name="ENTITY_NAME")
	private String entityName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
	

	
}

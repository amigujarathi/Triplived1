package com.triplived.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Trip Meta data
 * 
 * @author Santosh Joshi
 *
 */
@Embeddable
public class MetaDbId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MetaDbId() {
		
	}
	
	public MetaDbId(Long id, String meta){
		this.id = id;
		this.meta = meta;
	}
	
	@Column(name="ID")
	private Long id;
	
	/**
	 * Should Follow from Specific Items listed in  {@link TripMetaData}
	 */
	@Column(name="META")
	private String meta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	
	
}

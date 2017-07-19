package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Trip Meta data
 * 
 * @author santosh Joshi
 *
 */
@Entity
@Table(name="user_meta")
public class UserMetaDb {
	
	@Id
	private MetaDbId id;
	 
	@Column(name="META_COUNT")
	private Integer metaCount;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;

	public MetaDbId getId() {
		return id;
	}
	
	public void setId(MetaDbId id) {
		this.id = id;
	}
	
	public Integer getMetaCount() {
		return metaCount;
	}

	public void setMetaCount(Integer metaCount) {
		this.metaCount = metaCount;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	 
}

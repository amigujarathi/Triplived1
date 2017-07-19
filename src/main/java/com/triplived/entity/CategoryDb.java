package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name="category")
public class CategoryDb {
	
	@Id
    @Column(name="CATEGORY_SEQ")
    @GeneratedValue
	private Long category_seq;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CATEGORY_GROUP")
	private String category_group;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	
	@Column(name = "UPDATED_BY", length = 60)
	private String updatedBy;
	
	
	
	public Long getCategory_seq() {
		return category_seq;
	}



	public void setCategory_seq(Long category_seq) {
		this.category_seq = category_seq;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCategory_group() {
		return category_group;
	}



	public void setCategory_group(String category_group) {
		this.category_group = category_group;
	}



	public Date getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	public String getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	

}

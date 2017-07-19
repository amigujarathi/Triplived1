package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.connectme.domain.triplived.RequiredStatus;

@Entity
@Table(name="explore_tags")
public class ExploreTagsDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	
	@Column(name="TEXT")
	private String text;
	
	@Column(name="IMAGE_URL")
	private String image;
	
	@Column(name="TYPE")
	private String type;
	
		
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "STATUS", length = 2)
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}
package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="attraction_image")
public class AttractionImageDb {

	@Id
    @Column(name="IMAGE_ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="ATTRACTION_ID")
	private Long attractionId;
	
	@Column(name="SRC")
	private String attractionSrc;
	
	@Column(name="IMAGE_NAME")
	private String imageName;

	@Column(name="AUTHOR")
	private String author;
	
	@Column(name="IMAGE_TITLE")
	private String imageTitle;
	
	@Column(name="STATUS")
	private String status;

	@Column(name="FILE_FROM")
	private String fileFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", length = 19)
	private Date updatedDate;

	@Column(name = "UPDATED_BY", length = 60)
	private String updatedBy;
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public AttractionImageDb() {}
	
	public AttractionImageDb(Long id, Long attractionId, String attractionSrc) {
		this.id = id;
		this.attractionId = attractionId;
		this.attractionSrc = attractionSrc;
	}
	
	public AttractionImageDb(Long attractionId, String attractionSrc) {
		this.attractionId = attractionId;
		this.attractionSrc = attractionSrc;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(Long attractionId) {
		this.attractionId = attractionId;
	}
	public String getAttractionSrc() {
		return attractionSrc;
	}
	public void setAttractionSrc(String attractionSrc) {
		this.attractionSrc = attractionSrc;
	}
	
	public String getFileFrom() {
		return fileFrom;
	}
	
	public void setFileFrom(String fileFrom) {
		this.fileFrom = fileFrom;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getImageTitle() {
		return imageTitle;
	}
	
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
}

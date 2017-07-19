package com.connectme.domain.triplived.dto;

import com.google.gson.annotations.Expose;

public class AttractionImageDTO {

	
	@Expose
	private String name;
	@Expose
	private Integer size;
	@Expose
	private String url;
	@Expose
	private String thumbnailUrl;
	@Expose
	private String deleteUrl;
	
	private String fileFrom;
	private String filetitle;
	private String fileAuthor;
	
	@Expose
	private String desc;
	
	@Expose
	private String deleteType;
	
	@Expose
	private String type;
	
	private String updatedBy;


	public String getFileAuthor() {
		return fileAuthor;
	}
	
	public void setFileAuthor(String fileAuthor) {
		this.fileAuthor = fileAuthor;
	}
	
	public String getFiletitle() {
		return filetitle;
	}
	public void setFiletitle(String filetitle) {
		this.filetitle = filetitle;
	}
	
	public String getFileFrom() {
		return fileFrom;
	}
	
	public void setFileFrom(String fileFrom) {
		this.fileFrom = fileFrom;
	}
	
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getDeleteUrl() {
		return deleteUrl;
	}
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	public String getDeleteType() {
		return deleteType;
	}
	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "AttractionImageDTO [name=" + name + ", size=" + size + ", url="
				+ url + ", deleteType=" + deleteType + ", type=" + type + "]";
	}
	
	
	
}

package com.connectme.domain.triplived.dto;

public class NewAttractionDataAddDTO {
	
	private Double latitude;
	private Double longitude;
	private String googlePlaceId;
	private String attractionName;
	private String categoryName;
	private String address;
	private String state;
	private String city_Id;
	private Long category_seq;
	private String createdBy;
	private String updatedBy;
	
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Long getCategory_seq() {
		return category_seq;
	}
	public void setCategory_seq(Long category_seq) {
		this.category_seq = category_seq;
	}
	public String getCity_Id() {
		return city_Id;
	}
	public void setCity_Id(String city_Id) {
		this.city_Id = city_Id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getGooglePlaceId() {
		return googlePlaceId;
	}
	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

}

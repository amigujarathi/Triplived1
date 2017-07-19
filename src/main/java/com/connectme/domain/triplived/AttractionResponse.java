package com.connectme.domain.triplived;

import java.util.ArrayList;
import java.util.List;

public class AttractionResponse {
	private String phoneNmber;
	private String address1;
	private String address2;
	private Integer pincode;
	private String email;
	private String id;
	private double latitude;
	private double longitude;
	private String attractionName;
	private String description;
	private String punchLine;

	private String googlePlaceId;
	private String googleplaceName;

	private String timings;
	private String bestTime;
	private String reqTime;
	private String ticket;
	private String webSite;

	private String updatedDate;
	private String updatedBy;

	
	private List<String> images = new ArrayList<String>();
	private List<Category> categories = new ArrayList<Category>();
	
	public String getTimings() {
		return timings;
	}

	public void setTimings(String timings) {
		this.timings = timings;
	}

	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getGooglePlaceId() {
		return googlePlaceId;
	}
	
	public String getGoogleplaceName() {
		return googleplaceName;
	}
	
	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}
	
	public void setGoogleplaceName(String googleplaceName) {
		this.googleplaceName = googleplaceName;
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public String getPunchLine() {
		return punchLine;
	}
	
	public void setPunchLine(String punchLine) {
		this.punchLine = punchLine;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPhoneNmber() {
		return phoneNmber;
	}
	public void setPhoneNmber(String phoneNmber) {
		this.phoneNmber = phoneNmber;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}

}

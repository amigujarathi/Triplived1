package com.connectme.domain.triplived.dto;

import java.util.ArrayList;
import java.util.List;

import com.connectme.domain.triplived.Category;

public class AttractionResponseDTO implements Comparable<AttractionResponseDTO>{

	private String id;
	private double latitude;
	private double longitude;
	private String attractionName;
	
	private String description;
	private List<String> images = new ArrayList<String>();
	private String punchLine;
	
	private List<Category> categories;
	private String googlePlaceId;
	private String googleplaceName;
	
	private String timings;
	private String bestTime;
	private String reqTime;
	private String address;
	
	private String ticket;
	private String webSite;
	private String phoneNumber;
	
	private String updatedDate;
	private String updatedBy;

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPunchLine(String punchLine) {
		this.punchLine = punchLine;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	
	public String getPunchLine() {
		return punchLine;
	}
	
	public void setPunchline(String punchLine) {
		this.punchLine = punchLine; 
	}
	
	@Override
	public int compareTo(AttractionResponseDTO obj) {
		return this.getAttractionName().compareToIgnoreCase(obj.getAttractionName());
	}
	
}

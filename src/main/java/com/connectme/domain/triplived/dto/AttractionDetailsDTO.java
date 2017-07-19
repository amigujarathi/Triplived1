package com.connectme.domain.triplived.dto;

import java.util.List;

public class AttractionDetailsDTO {
	
	private String name;
	private String description;
	private String reqTime;
	private String timings;
	private String bestTime;
	private List<String> images;
	private String address;
	private String cityName;
	private String id;
	private String latitude;
	private String longitude;
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
	AttractionCrowdSourcedDTO crowdSourcedContent;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
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
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public AttractionCrowdSourcedDTO getCrowdSourcedContent() {
		return crowdSourcedContent;
	}
	public void setCrowdSourcedContent(AttractionCrowdSourcedDTO crowdSourcedContent) {
		this.crowdSourcedContent = crowdSourcedContent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}

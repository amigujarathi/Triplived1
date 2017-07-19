package com.connectme.domain.triplived.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class AttractionDataUploadDTO {
	
	private Long attractionId;
	
	private String cityCode;
	
	private String attractionDescription;
	
	private Double latitude;
	
	private Double longitude;
	
	private String attractionPunchLine;
	
	private String cityDescription;
	
    private String cityId; // to be changed
	
	private Long timeStamp;
	
	private String googlePlaceId;
	
	private String googleplaceName;
	
	private String attractionTiming;
	
	private String attractionAddress;
	
	private Long subTripId;
	
	private String createdBy;
	
	private String bestTime;
	
	private String reqTime;
	
	private String phone;
	
	private String webSite;
	
	private String ticket;
	
	
	
	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getAttractionTiming() {
		return attractionTiming;
	}

	public void setAttractionTiming(String attractionTiming) {
		this.attractionTiming = attractionTiming;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getAttractionAddress() {
		return attractionAddress;
	}

	public void setAttractionAddress(String attractionAddress) {
		this.attractionAddress = attractionAddress;
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
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Expose
	private List<AttractionImageDTO> files = new ArrayList<AttractionImageDTO>();

	
	public Long getAttractionId() {
		return attractionId;
	}
	
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setAttractionId(Long attractionId) {
		this.attractionId = attractionId;
	}

	public String getAttractionDescription() {
		return attractionDescription;
	}

	public void setAttractionDescription(String attractionDescription) {
		this.attractionDescription = attractionDescription;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	/**
	 * 
	 * @return The files
	 */
	public List<AttractionImageDTO> getFiles() {
		return files;
	}

	/**
	 * 
	 * @param files
	 *            The files
	 */
	public void setFiles(List<AttractionImageDTO> files) {
		this.files = files;
	}

	public String getAttractionPunchLine() {
		return attractionPunchLine;
	}

	public void setAttractionPunchLine(String attractionPunchLine) {
		this.attractionPunchLine = attractionPunchLine;
	}

	public String getCityDescription() {
		return cityDescription;
	}

	public void setCityDescription(String cityDescription) {
		this.cityDescription = cityDescription;
	}
	
        public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getSubTripId() {
		return subTripId;
	}

	public void setSubTripId(Long subTripId) {
		this.subTripId = subTripId;
	}
	

}

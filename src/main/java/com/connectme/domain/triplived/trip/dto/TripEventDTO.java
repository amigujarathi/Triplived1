package com.connectme.domain.triplived.trip.dto;

import java.util.List;

public class TripEventDTO implements Comparable<TripEventDTO>{
	
	private Long id;
	private String eventName;
	private String cityId;
	private String cityName;
	private Long subTripId;
	private Long timestamp;
	private List<TripPhotoDTO> files;
    private List<TripReviewDTO> reviews;
    private String type;
    private String desc;
    
    public TripEventDTO() {
    	
    }
    
    public TripEventDTO(String type) {
    	this.type = type;
    }
        
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public List<TripPhotoDTO> getFiles() {
		return files;
	}
	public void setFiles(List<TripPhotoDTO> files) {
		this.files = files;
	}
	public List<TripReviewDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<TripReviewDTO> reviews) {
		this.reviews = reviews;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public Long getSubTripId() {
		return subTripId;
	}
	public void setSubTripId(Long subTripId) {
		this.subTripId = subTripId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public int compareTo(TripEventDTO obj) {
		if(this.timestamp >= obj.timestamp) {
			return 1;
		} else {
			return -1;
		}
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}

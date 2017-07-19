package com.connectme.domain.triplived.trip.dto;

import java.util.List;

public class TripTransportDTO {
	
	private Long transportId;
	private Long timestamp;
	private String transportType;
	private List<TripPhotoDTO> photos;
    private List<TripReviewDTO> reviews;
    
	public Long getTransportId() {
		return transportId;
	}
	public void setTransportId(Long transportId) {
		this.transportId = transportId;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public List<TripPhotoDTO> getPhotos() {
		return photos;
	}
	public void setPhotos(List<TripPhotoDTO> photos) {
		this.photos = photos;
	}
	public List<TripReviewDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<TripReviewDTO> reviews) {
		this.reviews = reviews;
	}

}

package com.connectme.domain.triplived.trip.dto;

import java.util.List;

public class TripHotelDTO extends TripEventDTO{
	
	private Long hotelId;
	/*private String cityId;
	private Long subTripId;
	
	private List<TripPhotoDTO> photos;
    private List<TripReviewDTO> reviews;
    private TripHotelRatingType rating;
    private String type;*/
    
    public TripHotelDTO() {
		//this.type = "hotel";
	}
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
	
	/*public List<TripPhotoDTO> getPhotos() {
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
	public TripHotelRatingType getRating() {
		return rating;
	}
	public void setRating(TripHotelRatingType rating) {
		this.rating = rating;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}*/
	/*public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}*/
	/*public Long getSubTripId() {
		return subTripId;
	}
	public void setSubTripId(Long subTripId) {
		this.subTripId = subTripId;
	}*/


}

package com.connectme.domain.triplived.dto;

import java.util.List;

public class HotelResponseDTO implements Comparable<HotelResponseDTO>{

	private String name;
	private String id;
	private String address;
	private String locality;
	private String street;
	private String latitude;
	private String longitude;
	private List<String> hotelImages;
	private List<String> amenities;
	private String description;
	private String rating;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
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
	public List<String> getHotelImages() {
		return hotelImages;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public void setHotelImages(List<String> hotelImages) {
		this.hotelImages = hotelImages;
	}
	public List<String> getAmenities() {
		return amenities;
	}
	public void setAmenities(List<String> amenities) {
		this.amenities = amenities;
	}
	
	@Override
	public int compareTo(HotelResponseDTO obj) {
		return this.getName().compareToIgnoreCase(obj.getName());
	}
}

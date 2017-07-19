package com.connectme.domain.triplived.dto;

import java.util.List;
import java.util.Map;

public class EntityResponseDTO implements Comparable<EntityResponseDTO>{

	private String name;
	private String id;
	private String address;
	private String locality;
	private String street;
	private String latitude;
	private String longitude;
	private String type;
	private String cityCode;
	private String cityName;
	private String countryName;
	private String state;
	private List<String> imageSrc;
	private Double distanceFromCoordinates;
	private String category;
	private String distanceInMetric;
	private String foundAtQueryDistance;
	private String attractionId;
	private List<String> categoryGroupIcons;
	private Map<String, String> miscMap;
	
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
	
	/*public String getUserFbId() {
		return userFbId;
	}
	public void setUserFbId(String userFbId) {
		this.userFbId = userFbId;
	}*/
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
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(String attractionId) {
		this.attractionId = attractionId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public List<String> getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(List<String> imageSrc) {
		this.imageSrc = imageSrc;
	}
	public Double getDistanceFromCoordinates() {
		return distanceFromCoordinates;
	}
	public void setDistanceFromCoordinates(Double distanceFromCoordinates) {
		this.distanceFromCoordinates = distanceFromCoordinates;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDistanceInMetric() {
		return distanceInMetric;
	}
	public void setDistanceInMetric(String distanceInMetric) {
		this.distanceInMetric = distanceInMetric;
	}
	public String getFoundAtQueryDistance() {
		return foundAtQueryDistance;
	}
	public void setFoundAtQueryDistance(String foundAtQueryDistance) {
		this.foundAtQueryDistance = foundAtQueryDistance;
	}
	public List<String> getCategoryGroupIcons() {
		return categoryGroupIcons;
	}
	public void setCategoryGroupIcons(List<String> categoryGroupIcons) {
		this.categoryGroupIcons = categoryGroupIcons;
	}
	public Map<String, String> getMiscMap() {
		return miscMap;
	}
	public void setMiscMap(Map<String, String> miscMap) {
		this.miscMap = miscMap;
	}
	@Override
	public int compareTo(EntityResponseDTO obj) {
		if(this.distanceFromCoordinates >= obj.distanceFromCoordinates) {
			return 1;
		} else {
			return -1;
		}
	}
	
}

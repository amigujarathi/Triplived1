package com.connectme.domain.triplived;

import java.util.List;
import java.util.Map;

import com.connectme.domain.triplived.trip.dto.TripEventDTO;

public class EntityResponse implements Comparable<EntityResponse>{

	private String name;
	private String id;
	//private String userFbId;
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
	private Double rating;
	private String category;
	private String foundAtQueryDistance;
	private String attractionId;
	private String reqTime;
	private String bestTime;
	private String timings;
	private String description;
	private List<String> category_group_icons;
	private Map<String, String> miscMap;
	
	public String getName() {
		return name;
	}
	/*public String getUserFbId() {
		return userFbId;
	}
	public void setUserFbId(String userFbId) {
		this.userFbId = userFbId;
	}*/
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
	public String getBestTime() {
		return bestTime;
	}
	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}
	public String getTimings() {
		return timings;
	}
	public void setTimings(String timings) {
		this.timings = timings;
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
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(String attractionId) {
		this.attractionId = attractionId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Map<String, String> getMiscMap() {
		return miscMap;
	}
	public void setMiscMap(Map<String, String> miscMap) {
		this.miscMap = miscMap;
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
	
	
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFoundAtQueryDistance() {
		return foundAtQueryDistance;
	}
	public void setFoundAtQueryDistance(String foundAtQueryDistance) {
		this.foundAtQueryDistance = foundAtQueryDistance;
	}
	public List<String> getCategory_group_icons() {
		return category_group_icons;
	}
	public void setCategory_group_icons(List<String> category_group_icons) {
		this.category_group_icons = category_group_icons;
	}
	@Override
	public int compareTo(EntityResponse obj) {
		if(this.distanceFromCoordinates >= obj.distanceFromCoordinates) {
			return 1;
		} else {
			return -1;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((cityCode == null) ? 0 : cityCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((imageSrc == null) ? 0 : imageSrc.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((locality == null) ? 0 : locality.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityResponse other = (EntityResponse) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (cityCode == null) {
			if (other.cityCode != null)
				return false;
		} else if (!cityCode.equals(other.cityCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageSrc == null) {
			if (other.imageSrc != null)
				return false;
		} else if (!imageSrc.equals(other.imageSrc))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (locality == null) {
			if (other.locality != null)
				return false;
		} else if (!locality.equals(other.locality))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}
	
}

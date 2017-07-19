package com.connectme.domain.triplived.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"placeName",
"lat",
"lng",
"tripId",
"userId",
"emotion",
"fbAccessToken",
"FbImageDTO"
})
public class FbPostDTO {

@JsonProperty("placeName")
private String placeName;
@JsonProperty("lat")
private Double lat;
@JsonProperty("lng")
private Double lng;
@JsonProperty("tripId")
private Long tripId;
@JsonProperty("userId")
private Long userId;
@JsonProperty("emotion")
private String emotion;
@JsonProperty("fbAccessToken")
private String fbAccessToken;

@JsonProperty("message")
private String customMessage;

@JsonProperty("images")
private List<com.connectme.domain.triplived.dto.FbImageDTO> listOfImages = new ArrayList<com.connectme.domain.triplived.dto.FbImageDTO>();

@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The placeName
*/
@JsonProperty("placeName")
public String getPlaceName() {
return placeName;
}

/**
* 
* @param placeName
* The placeName
*/
@JsonProperty("placeName")
public void setPlaceName(String placeName) {
this.placeName = placeName;
}

/**
* 
* @return
* The lat
*/
@JsonProperty("lat")
public Double getLat() {
return lat;
}

/**
* 
* @param lat
* The lat
*/
@JsonProperty("lat")
public void setLat(Double lat) {
this.lat = lat;
}

/**
* 
* @return
* The lng
*/
@JsonProperty("lng")
public Double getLng() {
return lng;
}

/**
* 
* @param lng
* The lng
*/
@JsonProperty("lng")
public void setLng(Double lng) {
this.lng = lng;
}

/**
* 
* @return
* The tripId
*/
@JsonProperty("tripId")
public Long getTripId() {
return tripId;
}

/**
* 
* @param tripId
* The tripId
*/
@JsonProperty("tripId")
public void setTripId(Long tripId) {
this.tripId = tripId;
}

/**
* 
* @return
* The userId
*/
@JsonProperty("userId")
public Long getUserId() {
return userId;
}

/**
* 
* @param userId
* The userId
*/
@JsonProperty("userId")
public void setUserId(Long userId) {
this.userId = userId;
}

/**
* 
* @return
* The emotion
*/
@JsonProperty("emotion")
public String getEmotion() {
return emotion;
}

/**
* 
* @param emotion
* The emotion
*/
@JsonProperty("emotion")
public void setEmotion(String emotion) {
this.emotion = emotion;
}

/**
* 
* @return
* The fbAccessToken
*/
@JsonProperty("fbAccessToken")
public String getFbAccessToken() {
return fbAccessToken;
}

/**
* 
* @param fbAccessToken
* The fbAccessToken
*/
@JsonProperty("fbAccessToken")
public void setFbAccessToken(String fbAccessToken) {
this.fbAccessToken = fbAccessToken;
}

/**
* 
* @return
* The FbImageDTO
*/
@JsonProperty("images")
public List<com.connectme.domain.triplived.dto.FbImageDTO> getFbImageDTO() {
return listOfImages;
}

/**
* 
* @param FbImageDTO
* The FbImageDTO
*/
@JsonProperty("images")
public void setFbImageDTO(List<com.connectme.domain.triplived.dto.FbImageDTO> listOfImages) {
this.listOfImages = listOfImages;
}

@JsonProperty("message")
public String getCustomMessage() {
	return customMessage;
}

@JsonProperty("message")
public void setCustomMessage(String customMessage) {
	this.customMessage = customMessage;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
package com.domain.triplived.trip.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Santosh
 *
 * Deals with trip likes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripLikeInfo {

	@JsonProperty("action")
	private String userAction;
	
	@JsonProperty("additionalProperties")
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	private Long tripId;
	
	private String id;
	
	private Long userId;
	
	private String contentType;
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@JsonProperty("action")
	public String getUserAction() {
		return userAction;
	}

	@JsonProperty("action")
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}


	@JsonProperty("additionalProperties")
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	@JsonProperty("additionalProperties")
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getId() {
		return id;
	}

	public void setId(String mediaId) {
		this.id = mediaId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}

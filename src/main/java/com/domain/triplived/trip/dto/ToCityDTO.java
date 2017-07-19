
package com.domain.triplived.trip.dto;

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
    "subTripId",
    "cityId",
    "type",
    "cityName",
    "cityFullName",
    "cityThumbUri",
    "timestamp",
    "cityType",
    "events",
    "files",
    "reviews"
})
public class ToCityDTO {

    @JsonProperty("subTripId")
    private Long subTripId;
    @JsonProperty("cityId")
    private String cityId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("cityName")
    private String cityName;
    @JsonProperty("cityFullName")
    private String cityFullName;
    @JsonProperty("cityThumbUri")
    private String cityThumbUri;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("cityType")
    private String cityType;
    @JsonProperty("events")
    private List<Event> events = new ArrayList<Event>();
    @JsonProperty("files")
    private List<File> files = new ArrayList<File>();
    /* @JsonProperty("reviews")
    private List<Review> reviews = new ArrayList<Review>();*/
    @JsonProperty("additionalProperties")
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The subTripId
     */
    @JsonProperty("subTripId")
    public Long getSubTripId() {
        return subTripId;
    }

    /**
     * 
     * @param subTripId
     *     The subTripId
     */
    @JsonProperty("subTripId")
    public void setSubTripId(Long subTripId) {
        this.subTripId = subTripId;
    }

    /**
     * 
     * @return
     *     The cityId
     */
    @JsonProperty("cityId")
    public String getCityId() {
        return cityId;
    }

    /**
     * 
     * @param cityId
     *     The cityId
     */
    @JsonProperty("cityId")
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The cityName
     */
    @JsonProperty("cityName")
    public String getCityName() {
        return cityName;
    }

    /**
     * 
     * @param cityName
     *     The cityName
     */
    @JsonProperty("cityName")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 
     * @return
     *     The cityFullName
     */
    @JsonProperty("cityFullName")
    public String getCityFullName() {
        return cityFullName;
    }

    /**
     * 
     * @param cityFullName
     *     The cityFullName
     */
    @JsonProperty("cityFullName")
    public void setCityFullName(String cityFullName) {
        this.cityFullName = cityFullName;
    }

    /**
     * 
     * @return
     *     The cityThumbUri
     */
    @JsonProperty("cityThumbUri")
    public String getCityThumbUri() {
        return cityThumbUri;
    }

    /**
     * 
     * @param cityThumbUri
     *     The cityThumbUri
     */
    @JsonProperty("cityThumbUri")
    public void setCityThumbUri(String cityThumbUri) {
        this.cityThumbUri = cityThumbUri;
    }

    /**
     * 
     * @return
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 
     * @return
     *     The cityType
     */
    @JsonProperty("cityType")
    public String getCityType() {
        return cityType;
    }

    /**
     * 
     * @param cityType
     *     The cityType
     */
    @JsonProperty("cityType")
    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    /**
     * 
     * @return
     *     The events
     */
    @JsonProperty("events")
    public List<Event> getEvents() {
        return events;
    }

    /**
     * 
     * @param events
     *     The events
     */
    @JsonProperty("events")
    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    @JsonProperty("files")
    public List<File> getFiles() {
        return files;
    }
    
    @JsonProperty("files")
    public void setFiles(List<File> files) {
        this.files = files;
    }
    
/*    *//**
     * 
     * @return
     *     The files
     *//*
    @JsonProperty("files")
    public List<File> getFiles() {
        return files;
    }

    *//**
     * 
     * @param files
     *     The files
     *//*
    @JsonProperty("files")
    public void setFiles(List<File> files) {
        this.files = files;
    }

    *//**
     * 
     * @return
     *     The reviews
     *//*
    @JsonProperty("reviews")
    public List<Review> getReviews() {
        return reviews;
    }

    *//**
     * 
     * @param reviews
     *     The reviews
     *//*
    @JsonProperty("reviews")
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }*/

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

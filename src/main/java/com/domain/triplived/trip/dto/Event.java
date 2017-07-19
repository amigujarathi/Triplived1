
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
    "type",
    "id",
    "name",
    "address",
    "cityId",
    "cityName",
    "subTripId",
    "timestamp",
    "checkedIn",
    "files",
    "reviews",
    "ratings",
    "updates"
})
public class Event {

    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonProperty("sid")
    private String sid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;
    @JsonProperty("cityId")
    private String cityId;
    @JsonProperty("cityName")
    private String cityName;
    @JsonProperty("subTripId")
    private Long subTripId;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("checkedIn")
    private Integer checkedIn;
    @JsonProperty("files")
    private List<File> files = new ArrayList<File>();
    @JsonProperty("reviews")
    private List<Review> reviews = new ArrayList<Review>();
    @JsonProperty("ratings")
    private List<Rating> ratings = new ArrayList<Rating>();
    @JsonProperty("updates")
    private List<Update> updates = new ArrayList<Update>();
    @JsonProperty("additionalProperties")
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The address
     */
    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
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

    @JsonProperty("checkedIn")
    public Integer getCheckedIn() {
		return checkedIn;
	}

    @JsonProperty("checkedIn")
	public void setCheckedIn(Integer checkedIn) {
		this.checkedIn = checkedIn;
	}

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
     *     The files
     */
    @JsonProperty("files")
    public List<File> getFiles() {
        return files;
    }

    /**
     * 
     * @param files
     *     The files
     */
    @JsonProperty("files")
    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     * 
     * @return
     *     The reviews
     */
    @JsonProperty("reviews")
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * 
     * @param reviews
     *     The reviews
     */
    @JsonProperty("reviews")
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * 
     * @return
     *     The ratings
     */
    @JsonProperty("ratings")
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * 
     * @param ratings
     *     The ratings
     */
    @JsonProperty("ratings")
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
    @JsonProperty("updates")
    public List<Update> getUpdates() {
		return updates;
	}
    @JsonProperty("updates")
	public void setUpdates(List<Update> updates) {
		this.updates = updates;
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

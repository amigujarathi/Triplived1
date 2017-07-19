
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
    "tripName",
    "tripId",
    "tripCoverUri",
    "user",
    "category",
    "subTrips",
    "tripVideoUrl"
})
public class TimelineTrip {

    @JsonProperty("tripName")
    private String tripName;
    @JsonProperty("tripId")
    private String tripId;
    @JsonProperty("tripCoverUri")
    private String tripCoverUri;
    @JsonProperty("tripVideoUrl")
    private String tripVideoUrl;
    @JsonProperty("user")
    private User user;
    @JsonProperty("tripCategories")
    private List<String> tripCategories;
    @JsonProperty("subTrips")
    private List<SubTrip> subTrips = new ArrayList<SubTrip>();
    @JsonProperty("additionalProperties")
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The tripName
     */
    @JsonProperty("tripName")
    public String getTripName() {
        return tripName;
    }

    /**
     * 
     * @param tripName
     *     The tripName
     */
    @JsonProperty("tripName")
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    /**
     * 
     * @return
     *     The tripId
     */
    @JsonProperty("tripId")
    public String getTripId() {
        return tripId;
    }

    /**
     * 
     * @param tripId
     *     The tripId
     */
    @JsonProperty("tripId")
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    /**
     * 
     * @return
     *     The tripCoverUri
     */
    @JsonProperty("tripCoverUri")
    public String getTripCoverUri() {
        return tripCoverUri;
    }

    /**
     * 
     * @param tripCoverUri
     *     The tripCoverUri
     */
    @JsonProperty("tripCoverUri")
    public void setTripCoverUri(String tripCoverUri) {
        this.tripCoverUri = tripCoverUri;
    }

    /**
     * 
     * @return
     *     The user
     */
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The subTrips
     */
    @JsonProperty("subTrips")
    public List<SubTrip> getSubTrips() {
        return subTrips;
    }

    /**
     * 
     * @param subTrips
     *     The subTrips
     */
    @JsonProperty("subTrips")
    public void setSubTrips(List<SubTrip> subTrips) {
        this.subTrips = subTrips;
    }

    @JsonProperty("tripVideoUrl")
    public String getTripVideoUrl() {
		return tripVideoUrl;
	}

    @JsonProperty("tripVideoUrl")
	public void setTripVideoUrl(String tripVideoUrl) {
		this.tripVideoUrl = tripVideoUrl;
	}

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	public List<String> getTripCategories() {
		return tripCategories;
	}

	public void setTripCategories(List<String> tripCategories) {
		this.tripCategories = tripCategories;
	}

}


package com.domain.triplived.trip.dto;

import java.util.HashMap;
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
    "id",
    "fromCityDTO",
    "tripTransportDTO",
    "toCityDTO",
    "complete"
})
public class SubTrip {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("fromCityDTO")
    private String fromCityDTO;
    @JsonProperty("tripTransportDTO")
    private TripTransportDTO tripTransportDTO;
    @JsonProperty("toCityDTO")
    private ToCityDTO toCityDTO;
    @JsonProperty("complete")
    private Boolean complete;
    @JsonProperty("additionalProperties")
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The fromCityDTO
     */
    @JsonProperty("fromCityDTO")
    public String getFromCityDTO() {
        return fromCityDTO;
    }

    /**
     * 
     * @param fromCityDTO
     *     The fromCityDTO
     */
    @JsonProperty("fromCityDTO")
    public void setFromCityDTO(String fromCityDTO) {
        this.fromCityDTO = fromCityDTO;
    }

    /**
     * 
     * @return
     *     The tripTransportDTO
     */
    @JsonProperty("tripTransportDTO")
    public TripTransportDTO getTripTransportDTO() {
        return tripTransportDTO;
    }

    /**
     * 
     * @param tripTransportDTO
     *     The tripTransportDTO
     */
    @JsonProperty("tripTransportDTO")
    public void setTripTransportDTO(TripTransportDTO tripTransportDTO) {
        this.tripTransportDTO = tripTransportDTO;
    }

    /**
     * 
     * @return
     *     The toCityDTO
     */
    @JsonProperty("toCityDTO")
    public ToCityDTO getToCityDTO() {
        return toCityDTO;
    }

    /**
     * 
     * @param toCityDTO
     *     The toCityDTO
     */
    @JsonProperty("toCityDTO")
    public void setToCityDTO(ToCityDTO toCityDTO) {
        this.toCityDTO = toCityDTO;
    }

    /**
     * 
     * @return
     *     The complete
     */
    @JsonProperty("complete")
    public Boolean getComplete() {
        return complete;
    }

    /**
     * 
     * @param complete
     *     The complete
     */
    @JsonProperty("complete")
    public void setComplete(Boolean complete) {
        this.complete = complete;
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

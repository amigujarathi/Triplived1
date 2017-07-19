
package com.domain.triplived.romeToRio;

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
    "name",
    "distance",
    "duration",
    "totalTransferDuration",
    "indicativePrice",
    "stops",
    "segments"
})
public class Route {

    @JsonProperty("name")
    private String name;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("totalTransferDuration")
    private Integer totalTransferDuration;
    @JsonProperty("indicativePrice")
    private IndicativePrice indicativePrice;
    @JsonProperty("stops")
    private List<Stop> stops = new ArrayList<Stop>();
    @JsonProperty("segments")
    private List<Segment> segments = new ArrayList<Segment>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The distance
     */
    @JsonProperty("distance")
    public Double getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    @JsonProperty("distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The duration
     */
    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The totalTransferDuration
     */
    @JsonProperty("totalTransferDuration")
    public Integer getTotalTransferDuration() {
        return totalTransferDuration;
    }

    /**
     * 
     * @param totalTransferDuration
     *     The totalTransferDuration
     */
    @JsonProperty("totalTransferDuration")
    public void setTotalTransferDuration(Integer totalTransferDuration) {
        this.totalTransferDuration = totalTransferDuration;
    }

    /**
     * 
     * @return
     *     The indicativePrice
     */
    @JsonProperty("indicativePrice")
    public IndicativePrice getIndicativePrice() {
        return indicativePrice;
    }

    /**
     * 
     * @param indicativePrice
     *     The indicativePrice
     */
    @JsonProperty("indicativePrice")
    public void setIndicativePrice(IndicativePrice indicativePrice) {
        this.indicativePrice = indicativePrice;
    }

    /**
     * 
     * @return
     *     The stops
     */
    @JsonProperty("stops")
    public List<Stop> getStops() {
        return stops;
    }

    /**
     * 
     * @param stops
     *     The stops
     */
    @JsonProperty("stops")
    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    /**
     * 
     * @return
     *     The segments
     */
    @JsonProperty("segments")
    public List<Segment> getSegments() {
        return segments;
    }

    /**
     * 
     * @param segments
     *     The segments
     */
    @JsonProperty("segments")
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
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


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
    "serveTime",
    "places",
    "airports",
    "airlines",
    "aircrafts",
    "agencies",
    "routes"
})
public class RomeToRio {

    @JsonProperty("serveTime")
    private Integer serveTime;
    @JsonProperty("places")
    private List<Place> places = new ArrayList<Place>();
    @JsonProperty("airports")
    private List<Airport> airports = new ArrayList<Airport>();
    @JsonProperty("airlines")
    private List<Airline> airlines = new ArrayList<Airline>();
    @JsonProperty("aircrafts")
    private List<Aircraft> aircrafts = new ArrayList<Aircraft>();
    @JsonProperty("agencies")
    private List<Agency> agencies = new ArrayList<Agency>();
    @JsonProperty("routes")
    private List<Route> routes = new ArrayList<Route>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The serveTime
     */
    @JsonProperty("serveTime")
    public Integer getServeTime() {
        return serveTime;
    }

    /**
     * 
     * @param serveTime
     *     The serveTime
     */
    @JsonProperty("serveTime")
    public void setServeTime(Integer serveTime) {
        this.serveTime = serveTime;
    }

    /**
     * 
     * @return
     *     The places
     */
    @JsonProperty("places")
    public List<Place> getPlaces() {
        return places;
    }

    /**
     * 
     * @param places
     *     The places
     */
    @JsonProperty("places")
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    /**
     * 
     * @return
     *     The airports
     */
    @JsonProperty("airports")
    public List<Airport> getAirports() {
        return airports;
    }

    /**
     * 
     * @param airports
     *     The airports
     */
    @JsonProperty("airports")
    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    /**
     * 
     * @return
     *     The airlines
     */
    @JsonProperty("airlines")
    public List<Airline> getAirlines() {
        return airlines;
    }

    /**
     * 
     * @param airlines
     *     The airlines
     */
    @JsonProperty("airlines")
    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    /**
     * 
     * @return
     *     The aircrafts
     */
    @JsonProperty("aircrafts")
    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    /**
     * 
     * @param aircrafts
     *     The aircrafts
     */
    @JsonProperty("aircrafts")
    public void setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    /**
     * 
     * @return
     *     The agencies
     */
    @JsonProperty("agencies")
    public List<Agency> getAgencies() {
        return agencies;
    }

    /**
     * 
     * @param agencies
     *     The agencies
     */
    @JsonProperty("agencies")
    public void setAgencies(List<Agency> agencies) {
        this.agencies = agencies;
    }

    /**
     * 
     * @return
     *     The routes
     */
    @JsonProperty("routes")
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * 
     * @param routes
     *     The routes
     */
    @JsonProperty("routes")
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "RomeToRio [serveTime=" + serveTime + ", places=" + places
				+ ", airports=" + airports + ", airlines=" + airlines
				+ ", aircrafts=" + aircrafts + ", agencies=" + agencies
				+ ", routes=" + routes + ", additionalProperties="
				+ additionalProperties + "]";
	}

}

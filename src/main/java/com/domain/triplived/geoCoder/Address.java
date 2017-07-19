
package com.domain.triplived.geoCoder;

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
    "road",
    "town",
    "city",
    "state_district",
    "county",
    "state",
    "postcode",
    "country",
    "country_code"
})
public class Address {

    @JsonProperty("road")
    private String road;
    @JsonProperty("town")
    private String town;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state_district")
    private String state_district;
    @JsonProperty("county")
    private String county;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The road
     */
    @JsonProperty("road")
    public String getRoad() {
        return road;
    }

    /**
     * 
     * @param road
     *     The road
     */
    @JsonProperty("road")
    public void setRoad(String road) {
        this.road = road;
    }

    /**
     * 
     * @return
     *     The town
     */
    @JsonProperty("town")
    public String getTown() {
        return town;
    }

    @JsonProperty("city")
    public String getCity() {
		return city;
	}

    @JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

    @JsonProperty("state_district")
	public String getState_district() {
		return state_district;
	}

    @JsonProperty("state_district")
	public void setState_district(String state_district) {
		this.state_district = state_district;
	}

	/**
     * 
     * @param town
     *     The town
     */
    @JsonProperty("town")
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * 
     * @return
     *     The county
     */
    @JsonProperty("county")
    public String getCounty() {
        return county;
    }

    /**
     * 
     * @param county
     *     The county
     */
    @JsonProperty("county")
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The postcode
     */
    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    /**
     * 
     * @param postcode
     *     The postcode
     */
    @JsonProperty("postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * 
     * @return
     *     The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The countryCode
     */
    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 
     * @param countryCode
     *     The country_code
     */
    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

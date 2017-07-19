
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
    "place_id",
    "licence",
    "osm_type",
    "osm_id",
    "lat",
    "lon",
    "display_name",
    "address"
})
public class GeoCoder {

    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("licence")
    private String licence;
    @JsonProperty("osm_type")
    private String osmType;
    @JsonProperty("osm_id")
    private String osmId;
    @JsonProperty("lat")
    private String lat;
    @JsonProperty("lon")
    private String lon;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("address")
    private Address address;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The placeId
     */
    @JsonProperty("place_id")
    public String getPlaceId() {
        return placeId;
    }

    /**
     * 
     * @param placeId
     *     The place_id
     */
    @JsonProperty("place_id")
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * 
     * @return
     *     The licence
     */
    @JsonProperty("licence")
    public String getLicence() {
        return licence;
    }

    /**
     * 
     * @param licence
     *     The licence
     */
    @JsonProperty("licence")
    public void setLicence(String licence) {
        this.licence = licence;
    }

    /**
     * 
     * @return
     *     The osmType
     */
    @JsonProperty("osm_type")
    public String getOsmType() {
        return osmType;
    }

    /**
     * 
     * @param osmType
     *     The osm_type
     */
    @JsonProperty("osm_type")
    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    /**
     * 
     * @return
     *     The osmId
     */
    @JsonProperty("osm_id")
    public String getOsmId() {
        return osmId;
    }

    /**
     * 
     * @param osmId
     *     The osm_id
     */
    @JsonProperty("osm_id")
    public void setOsmId(String osmId) {
        this.osmId = osmId;
    }

    /**
     * 
     * @return
     *     The lat
     */
    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     *     The lat
     */
    @JsonProperty("lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * 
     * @return
     *     The lon
     */
    @JsonProperty("lon")
    public String getLon() {
        return lon;
    }

    /**
     * 
     * @param lon
     *     The lon
     */
    @JsonProperty("lon")
    public void setLon(String lon) {
        this.lon = lon;
    }

    /**
     * 
     * @return
     *     The displayName
     */
    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @param displayName
     *     The display_name
     */
    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 
     * @return
     *     The address
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
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


package com.domain.triplived.fourSquare;

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
    "venues",
    "confident"
})
public class Response {

    @JsonProperty("venues")
    private List<Venue> venues = new ArrayList<Venue>();
    @JsonProperty("confident")
    private Boolean confident;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The venues
     */
    @JsonProperty("venues")
    public List<Venue> getVenues() {
        return venues;
    }

    /**
     * 
     * @param venues
     *     The venues
     */
    @JsonProperty("venues")
    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    /**
     * 
     * @return
     *     The confident
     */
    @JsonProperty("confident")
    public Boolean getConfident() {
        return confident;
    }

    /**
     * 
     * @param confident
     *     The confident
     */
    @JsonProperty("confident")
    public void setConfident(Boolean confident) {
        this.confident = confident;
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

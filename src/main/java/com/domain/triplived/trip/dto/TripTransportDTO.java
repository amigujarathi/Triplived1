
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
    "transportType",
    "transportReview",
    "files"
})
public class TripTransportDTO {

    @JsonProperty("transportType")
    private String transportType;
    @JsonProperty("transportReview")
    private String transportReview;
    @JsonProperty("files")
    private List<File> files = new ArrayList<File>();
    @JsonProperty("additionalProperties")
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The transportType
     */
    @JsonProperty("transportType")
    public String getTransportType() {
        return transportType;
    }

    /**
     * 
     * @param transportType
     *     The transportType
     */
    @JsonProperty("transportType")
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
     * 
     * @return
     *     The transportReview
     */
    @JsonProperty("transportReview")
    public String getTransportReview() {
        return transportReview;
    }

    /**
     * 
     * @param transportReview
     *     The transportReview
     */
    @JsonProperty("transportReview")
    public void setTransportReview(String transportReview) {
        this.transportReview = transportReview;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


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
    "name",
    "size",
    "url",
    "thumbnailUrl",
    "type",
    "desc",
    "status"
})
public class File {

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("size")
    private Integer size;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("desc")
    private String desc;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("latitude")
    private String latitude;
    
    @JsonProperty("longitude")
    private String longitude;
    
    
    @JsonProperty("additionalProperties")
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    
    @JsonProperty("latitude")
    public String getLatitude() {
		return latitude;
	}
    
    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
    
    @JsonProperty("longitude")
    public String getLongitude() {
		return longitude;
	}
    
    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
		this.longitude = longitude;
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
     *     The size
     */
    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 
     * @return
     *     The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }
    
    @JsonProperty("status")
    public String getStatus() {
		return status;
	}
    
    @JsonProperty("status")
    public void setStatus(String status) {
		this.status = status;
	}

    /**
     * 
     * @return
     *     The thumbnailUrl
     */
    @JsonProperty("thumbnailUrl")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * 
     * @param thumbnailUrl
     *     The thumbnailUrl
     */
    @JsonProperty("thumbnailUrl")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
     *     The desc
     */
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    /**
     * 
     * @param desc
     *     The desc
     */
    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
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

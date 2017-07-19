
package com.domain.triplived.romeToRio;

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
    "code",
    "name",
    "url",
    "iconPath",
    "iconSize",
    "iconOffset"
})
public class Agency {

    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("iconPath")
    private String iconPath;
    @JsonProperty("iconSize")
    private String iconSize;
    @JsonProperty("iconOffset")
    private String iconOffset;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The code
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
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

    /**
     * 
     * @return
     *     The iconPath
     */
    @JsonProperty("iconPath")
    public String getIconPath() {
        return iconPath;
    }

    /**
     * 
     * @param iconPath
     *     The iconPath
     */
    @JsonProperty("iconPath")
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * 
     * @return
     *     The iconSize
     */
    @JsonProperty("iconSize")
    public String getIconSize() {
        return iconSize;
    }

    /**
     * 
     * @param iconSize
     *     The iconSize
     */
    @JsonProperty("iconSize")
    public void setIconSize(String iconSize) {
        this.iconSize = iconSize;
    }

    /**
     * 
     * @return
     *     The iconOffset
     */
    @JsonProperty("iconOffset")
    public String getIconOffset() {
        return iconOffset;
    }

    /**
     * 
     * @param iconOffset
     *     The iconOffset
     */
    @JsonProperty("iconOffset")
    public void setIconOffset(String iconOffset) {
        this.iconOffset = iconOffset;
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

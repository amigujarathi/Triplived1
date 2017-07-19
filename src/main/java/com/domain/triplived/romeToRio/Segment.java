
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
    "kind",
    "subkind",
    "isMajor",
    "isImperial",
    "distance",
    "duration",
    "transferDuration",
    "sName",
    "sPos",
    "tName",
    "tPos",
    "vehicle",
    "path",
    "indicativePrice"
})
public class Segment {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("subkind")
    private String subkind;
    @JsonProperty("isMajor")
    private Integer isMajor;
    @JsonProperty("isImperial")
    private Integer isImperial;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("transferDuration")
    private Integer transferDuration;
    @JsonProperty("sName")
    private String sName;
    @JsonProperty("sPos")
    private String sPos;
    @JsonProperty("tName")
    private String tName;
    @JsonProperty("tPos")
    private String tPos;
    @JsonProperty("vehicle")
    private String vehicle;
    @JsonProperty("path")
    private String path;
    @JsonProperty("indicativePrice")
    private IndicativePrice_ indicativePrice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The kind
     */
    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    /**
     * 
     * @param kind
     *     The kind
     */
    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * 
     * @return
     *     The subkind
     */
    @JsonProperty("subkind")
    public String getSubkind() {
        return subkind;
    }

    /**
     * 
     * @param subkind
     *     The subkind
     */
    @JsonProperty("subkind")
    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }

    /**
     * 
     * @return
     *     The isMajor
     */
    @JsonProperty("isMajor")
    public Integer getIsMajor() {
        return isMajor;
    }

    /**
     * 
     * @param isMajor
     *     The isMajor
     */
    @JsonProperty("isMajor")
    public void setIsMajor(Integer isMajor) {
        this.isMajor = isMajor;
    }

    /**
     * 
     * @return
     *     The isImperial
     */
    @JsonProperty("isImperial")
    public Integer getIsImperial() {
        return isImperial;
    }

    /**
     * 
     * @param isImperial
     *     The isImperial
     */
    @JsonProperty("isImperial")
    public void setIsImperial(Integer isImperial) {
        this.isImperial = isImperial;
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
     *     The transferDuration
     */
    @JsonProperty("transferDuration")
    public Integer getTransferDuration() {
        return transferDuration;
    }

    /**
     * 
     * @param transferDuration
     *     The transferDuration
     */
    @JsonProperty("transferDuration")
    public void setTransferDuration(Integer transferDuration) {
        this.transferDuration = transferDuration;
    }

    /**
     * 
     * @return
     *     The sName
     */
    @JsonProperty("sName")
    public String getSName() {
        return sName;
    }

    /**
     * 
     * @param sName
     *     The sName
     */
    @JsonProperty("sName")
    public void setSName(String sName) {
        this.sName = sName;
    }

    /**
     * 
     * @return
     *     The sPos
     */
    @JsonProperty("sPos")
    public String getSPos() {
        return sPos;
    }

    /**
     * 
     * @param sPos
     *     The sPos
     */
    @JsonProperty("sPos")
    public void setSPos(String sPos) {
        this.sPos = sPos;
    }

    /**
     * 
     * @return
     *     The tName
     */
    @JsonProperty("tName")
    public String getTName() {
        return tName;
    }

    /**
     * 
     * @param tName
     *     The tName
     */
    @JsonProperty("tName")
    public void setTName(String tName) {
        this.tName = tName;
    }

    /**
     * 
     * @return
     *     The tPos
     */
    @JsonProperty("tPos")
    public String getTPos() {
        return tPos;
    }

    /**
     * 
     * @param tPos
     *     The tPos
     */
    @JsonProperty("tPos")
    public void setTPos(String tPos) {
        this.tPos = tPos;
    }

    /**
     * 
     * @return
     *     The vehicle
     */
    @JsonProperty("vehicle")
    public String getVehicle() {
        return vehicle;
    }

    /**
     * 
     * @param vehicle
     *     The vehicle
     */
    @JsonProperty("vehicle")
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * 
     * @return
     *     The path
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * 
     * @param path
     *     The path
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 
     * @return
     *     The indicativePrice
     */
    @JsonProperty("indicativePrice")
    public IndicativePrice_ getIndicativePrice() {
        return indicativePrice;
    }

    /**
     * 
     * @param indicativePrice
     *     The indicativePrice
     */
    @JsonProperty("indicativePrice")
    public void setIndicativePrice(IndicativePrice_ indicativePrice) {
        this.indicativePrice = indicativePrice;
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

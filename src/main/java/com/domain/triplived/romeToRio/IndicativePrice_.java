
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
    "price",
    "currency",
    "isFreeTransfer"
})
public class IndicativePrice_ {

    @JsonProperty("price")
    private Integer price;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("isFreeTransfer")
    private Integer isFreeTransfer;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The price
     */
    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * @return
     *     The isFreeTransfer
     */
    @JsonProperty("isFreeTransfer")
    public Integer getIsFreeTransfer() {
        return isFreeTransfer;
    }

    /**
     * 
     * @param isFreeTransfer
     *     The isFreeTransfer
     */
    @JsonProperty("isFreeTransfer")
    public void setIsFreeTransfer(Integer isFreeTransfer) {
        this.isFreeTransfer = isFreeTransfer;
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

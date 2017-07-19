
package com.connectme.domain.triplived.placeDetail;

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
    "address_components",
    "adr_address",
    "formatted_address",
    "formatted_phone_number",
    "geometry",
    "icon",
    "id",
    "international_phone_number",
    "name",
    "place_id",
    "reference",
    "scope",
    "types",
    "url",
    "utc_offset",
    "vicinity"
})
public class Result {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents = new ArrayList<AddressComponent>();
    @JsonProperty("adr_address")
    private String adrAddress;
    @JsonProperty("formatted_address")
    private String formattedAddress;
    @JsonProperty("formatted_phone_number")
    private String formattedPhoneNumber;
    @JsonProperty("geometry")
    private Geometry geometry;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("id")
    private String id;
    @JsonProperty("international_phone_number")
    private String internationalPhoneNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("types")
    private List<String> types = new ArrayList<String>();
    @JsonProperty("url")
    private String url;
    @JsonProperty("utc_offset")
    private Integer utcOffset;
    @JsonProperty("vicinity")
    private String vicinity;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The addressComponents
     */
    @JsonProperty("address_components")
    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    /**
     * 
     * @param addressComponents
     *     The address_components
     */
    @JsonProperty("address_components")
    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    /**
     * 
     * @return
     *     The adrAddress
     */
    @JsonProperty("adr_address")
    public String getAdrAddress() {
        return adrAddress;
    }

    /**
     * 
     * @param adrAddress
     *     The adr_address
     */
    @JsonProperty("adr_address")
    public void setAdrAddress(String adrAddress) {
        this.adrAddress = adrAddress;
    }

    /**
     * 
     * @return
     *     The formattedAddress
     */
    @JsonProperty("formatted_address")
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * 
     * @param formattedAddress
     *     The formatted_address
     */
    @JsonProperty("formatted_address")
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     * 
     * @return
     *     The formattedPhoneNumber
     */
    @JsonProperty("formatted_phone_number")
    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    /**
     * 
     * @param formattedPhoneNumber
     *     The formatted_phone_number
     */
    @JsonProperty("formatted_phone_number")
    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    /**
     * 
     * @return
     *     The geometry
     */
    @JsonProperty("geometry")
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * 
     * @param geometry
     *     The geometry
     */
    @JsonProperty("geometry")
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * 
     * @return
     *     The icon
     */
    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    /**
     * 
     * @param icon
     *     The icon
     */
    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The internationalPhoneNumber
     */
    @JsonProperty("international_phone_number")
    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    /**
     * 
     * @param internationalPhoneNumber
     *     The international_phone_number
     */
    @JsonProperty("international_phone_number")
    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
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
     *     The reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    /**
     * 
     * @param reference
     *     The reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * 
     * @return
     *     The scope
     */
    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    /**
     * 
     * @param scope
     *     The scope
     */
    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * 
     * @return
     *     The types
     */
    @JsonProperty("types")
    public List<String> getTypes() {
        return types;
    }

    /**
     * 
     * @param types
     *     The types
     */
    @JsonProperty("types")
    public void setTypes(List<String> types) {
        this.types = types;
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
     *     The utcOffset
     */
    @JsonProperty("utc_offset")
    public Integer getUtcOffset() {
        return utcOffset;
    }

    /**
     * 
     * @param utcOffset
     *     The utc_offset
     */
    @JsonProperty("utc_offset")
    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    /**
     * 
     * @return
     *     The vicinity
     */
    @JsonProperty("vicinity")
    public String getVicinity() {
        return vicinity;
    }

    /**
     * 
     * @param vicinity
     *     The vicinity
     */
    @JsonProperty("vicinity")
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
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

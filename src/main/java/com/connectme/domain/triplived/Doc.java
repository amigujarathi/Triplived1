
package com.connectme.domain.triplived;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Doc {

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @Expose
    private String status;
    @Expose
    private String address1;
    @Expose
    private String address2;
    @SerializedName("ta_path")
    @Expose
    private String taPath;
    @Expose
    private String id;
    @Expose
    private Double pincode;
    @Expose
    private String email;
    @SerializedName("attraction_code")
    @Expose
    private String attractionCode;
    @SerializedName("city_code")
    @Expose
    private String cityCode;
    @Expose
    private String longitude;
    @SerializedName("attraction_name")
    @Expose
    private String attractionName;
    @Expose
    private String latitude;
    @SerializedName("_version_")
    @Expose
    private Integer Version;

    /**
     * 
     * @return
     *     The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 
     * @param phoneNumber
     *     The phone_number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * 
     * @param address1
     *     The address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * 
     * @return
     *     The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * 
     * @param address2
     *     The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * 
     * @return
     *     The taPath
     */
    public String getTaPath() {
        return taPath;
    }

    /**
     * 
     * @param taPath
     *     The ta_path
     */
    public void setTaPath(String taPath) {
        this.taPath = taPath;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The pincode
     */
    public Double getPincode() {
        return pincode;
    }

    /**
     * 
     * @param pincode
     *     The pincode
     */
    public void setPincode(Double pincode) {
        this.pincode = pincode;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The attractionCode
     */
    public String getAttractionCode() {
        return attractionCode;
    }

    /**
     * 
     * @param attractionCode
     *     The attraction_code
     */
    public void setAttractionCode(String attractionCode) {
        this.attractionCode = attractionCode;
    }

    /**
     * 
     * @return
     *     The cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 
     * @param cityCode
     *     The city_code
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 
     * @return
     *     The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 
     * @param longitude
     *     The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return
     *     The attractionName
     */
    public String getAttractionName() {
        return attractionName;
    }

    /**
     * 
     * @param attractionName
     *     The attraction_name
     */
    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    /**
     * 
     * @return
     *     The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude
     *     The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 
     * @return
     *     The Version
     */
    public Integer getVersion() {
        return Version;
    }

    /**
     * 
     * @param Version
     *     The _version_
     */
    public void setVersion(Integer Version) {
        this.Version = Version;
    }

}

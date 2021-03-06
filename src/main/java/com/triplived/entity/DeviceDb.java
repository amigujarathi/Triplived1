package com.triplived.entity;

// Generated Mar 28, 2015 8:38:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Country generated by hbm2java
 */
@Entity
@Table(name = "device_info", catalog = "trip")
public class DeviceDb implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="DEVICE_ID")
	private String deviceId;
	
	@Column(name="REGISTRATION_ID")
	private String registrationId;
	
	@Column(name="PHONE_TYPE")
	private String phoneType;
	
	@Column(name="SDK_VERSION")
	private String sdkVersion;
	
	@Column(name="NETWORK_TYPE")
	private String networkType;
	
	@Column(name="NETWORK_OPERATOR_NAME")
	private String networkOperatorName;
	
	@Column(name="SIM_OPERATOR_NAME")
	private String simOperatorName;
	
	@Column(name="MODEL")
	private String model;
	
	@Column(name="MANUFACTURER")
	private String manufacturer;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="APPLICATION_VERSION")
	private String applicationVersion;
	
	@Column(name="APPLICATION_VERSION_STRING")
	private String applicationVersionString;
	

	public DeviceDb() {
	}

	public DeviceDb(String deviceId, String registrationId) {
		this.deviceId = deviceId;
		this.registrationId = registrationId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getNetworkOperatorName() {
		return networkOperatorName;
	}

	public void setNetworkOperatorName(String networkOperatorName) {
		this.networkOperatorName = networkOperatorName;
	}

	public String getSimOperatorName() {
		return simOperatorName;
	}

	public void setSimOperatorName(String simOperatorName) {
		this.simOperatorName = simOperatorName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getApplicationVersion() {
		return applicationVersion;
	}
	
	public String getApplicationVersionString() {
		return applicationVersionString;
	}
	
	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}
	
	public void setApplicationVersionString(String applicationVersionString) {
		this.applicationVersionString = applicationVersionString;
	}
}

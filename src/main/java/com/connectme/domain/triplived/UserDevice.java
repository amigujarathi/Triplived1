package com.connectme.domain.triplived;

public class UserDevice {

    private String deviceId ;
    private String phoneType;
    private String networkOperatorName;
    private String networkType;
    private String simOperatorName;
    private String manufacturer;
    private String model;
    private String sdkVersion;
    private String gcmRegistrationToken;
    
    private String userName;
    private String userId;

    private String applicationVersion;
    private String applicationVersionString;

    public void setGcmRegistrationToken(String gcmRegistrationToken) {
        this.gcmRegistrationToken = gcmRegistrationToken;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        this.networkOperatorName = networkOperatorName;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public void setSimOperatorName(String simOperatorName) {
        this.simOperatorName = simOperatorName;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

	public String getDeviceId() {
		return deviceId;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public String getNetworkOperatorName() {
		return networkOperatorName;
	}

	public String getNetworkType() {
		return networkType;
	}

	public String getSimOperatorName() {
		return simOperatorName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public String getGcmRegistrationToken() {
		return gcmRegistrationToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}
	
	public void setApplicationVersionString(String applicationVersionString) {
		this.applicationVersionString = applicationVersionString;
	}
	
	public String getApplicationVersion() {
		return applicationVersion;
	}
	
	public String getApplicationVersionString() {
		return applicationVersionString;
	}
	
}

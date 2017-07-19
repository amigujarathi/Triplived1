package com.triplived.service.device;

import com.connectme.domain.triplived.UserDevice;

public interface DeviceService {

	
	void updateDevice(UserDevice device);
	/**
	 * Updates the Application verison for device
	 * 
	 * @param deviceId
	 * @param appVersion
	 * @param appVersionString
	 */
	void updateDeviceVersion(String deviceId, String appVersion, String appVersionString);

}

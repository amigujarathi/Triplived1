package com.triplived.dao.device;

import java.util.List;

import com.triplived.entity.DeviceDb;

public interface DeviceDao {

	void updateDevice(DeviceDb device);

	List<DeviceDb> getAllUsersDeviceInformation(String deviceId);

	String getRegistrationIdFromDeviceId(String deviceId);

	/**
	 * Inactivates the users who has uninstalled the APP
	 * @param registracionIdList
	 */
	public void unregisterUserDevice(List<String> registracionIdList);

	/**
	 * Update Application version for Device;
	 * 
	 * @param deviceId
	 * @param appVersion
	 * @param appVersionString
	 */
	void updateApplicationVersion(String deviceId, String appVersion, String appVersionString);
}

package com.triplived.service.device.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.UserDevice;
import com.triplived.dao.device.DeviceDao;
import com.triplived.entity.DeviceDb;
import com.triplived.service.device.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService{

	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public void updateDevice(UserDevice device) {
		DeviceDb deviceDb = new DeviceDb();
		deviceDb.setDeviceId(device.getDeviceId());
		deviceDb.setRegistrationId(device.getGcmRegistrationToken());
		deviceDb.setManufacturer(device.getManufacturer());
		deviceDb.setModel(device.getModel());
		deviceDb.setNetworkOperatorName(device.getNetworkOperatorName());
		deviceDb.setNetworkType(device.getNetworkType());
		deviceDb.setSdkVersion(device.getSdkVersion());
		deviceDb.setPhoneType(device.getPhoneType());
		deviceDb.setSimOperatorName(device.getSimOperatorName());
		deviceDb.setStatus("A");
		deviceDb.setCreatedDate(new Date());
		
		deviceDb.setApplicationVersion(device.getApplicationVersion());
		deviceDb.setApplicationVersionString(device.getApplicationVersionString());
		
		deviceDao.updateDevice(deviceDb);
	}
	
	
	public String getDeviceId(String userId) {
		return null;
	}


	@Async
	@Override
	public void updateDeviceVersion(String deviceId, String appVersion, String appVersionString) {
	
		deviceDao.updateApplicationVersion(deviceId, appVersion, appVersionString);
	}
	
	
}

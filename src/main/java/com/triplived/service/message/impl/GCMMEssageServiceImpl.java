package com.triplived.service.message.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.connectme.domain.triplived.UserDevice;
import com.triplived.dao.device.DeviceDao;
import com.triplived.dao.user.UserDao;
import com.triplived.entity.DeviceDb;
import com.triplived.service.message.GCMMessageService;

public class GCMMEssageServiceImpl implements GCMMessageService {

	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<UserDevice> getDevicesFor(String deviceId) {
		
		List<DeviceDb> devices = deviceDao.getAllUsersDeviceInformation(deviceId);
		List<UserDevice> userDevices = new ArrayList<UserDevice>();
		for (DeviceDb deviceDb : devices) {
			
			UserDevice userDevice = new UserDevice();

			userDevice.setDeviceId(deviceId);
			userDevice.setGcmRegistrationToken(deviceDb.getRegistrationId());
			
			userDevices.add(userDevice) ;
			
		}
		
		return userDevices;
	}
	

}

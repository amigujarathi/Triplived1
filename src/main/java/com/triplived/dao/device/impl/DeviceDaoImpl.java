package com.triplived.dao.device.impl;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.device.DeviceDao;
import com.triplived.entity.DeviceDb;

@Repository
@Transactional("txManager")
public class DeviceDaoImpl extends GenericHibernateDAO<DeviceDb, Serializable> implements DeviceDao {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceDaoImpl.class );
	
	@Override
	public void updateDevice(DeviceDb device) {
		saveOrUpdate(device);
	}
	
	@Override
	public List<DeviceDb> getAllUsersDeviceInformation(String deviceId ){
		
		List<DeviceDb> devices = (List<DeviceDb>) getSession().createQuery("Select device FROM com.triplived.entity.DeviceDb device where STATUS = 'A' AND DEVICE_ID =:deviceId").setString("deviceId", deviceId).list();
        
        return devices;
		
	}
	
	public String getDeviceIdByUserId(String userId) {
		return null;
	}
	
	@Override
	public String getRegistrationIdFromDeviceId(String deviceId) {
		List<DeviceDb> devices = (List<DeviceDb>) getSession().createQuery("Select device FROM com.triplived.entity.DeviceDb device where STATUS = 'A' "
				+ "AND DEVICE_ID =:deviceId ORDER BY CREATED_DATE DESC").setString("deviceId", deviceId).list();
		
		
		if(!CollectionUtils.isEmpty(devices)) {
			DeviceDb device = devices.get(0);
			if(null != device.getRegistrationId()) {
				return device.getRegistrationId();
			}
		}
		return null;
	}
	
	@Async
	@Transactional("txManager")
	public void unregisterUserDevice(List<String> registracionIdList){
		
		for (String rid : registracionIdList) {
			
			int rowsUpdated = getSession().createSQLQuery("Update device_info set STATUS = 'D', UPDATED_DATE = now() where  REGISTRATION_ID = '"+rid+"' and  STATUS = 'A' ").executeUpdate();
			if(rowsUpdated > 0){
				logger.warn("GCM Device registracion status updated to  {} total updated {}  ", rid , rowsUpdated );
			}
		}
	}
	
	
	@Override
	@Transactional("txManager")
	public void updateApplicationVersion(String deviceId, String appVersion, String appVersionString){
		
		//for (String rid : registracionIdList) {
			
		try{
			int rowsUpdated = getSession().createSQLQuery("Update device_info set APPLICATION_VERSION = '"+appVersion+"',"
					+ " APPLICATION_VERSION_STRING = '"+appVersionString+"', UPDATED_DATE = now() where  DEVICE_ID = '"+deviceId+"' and  STATUS = 'A' ").executeUpdate();
			if(rowsUpdated > 0){
				logger.warn("Updated Application Version for Device {}  veriosn {}, total updated {}  ", deviceId , appVersionString, rowsUpdated );
			}
		}catch(Exception e){
			logger.error("Exception while updating user Device Version for device {} and for version {} error is {}", deviceId, appVersion);
			logger.error("Exception is ", e);
		}
		//}
	}

}

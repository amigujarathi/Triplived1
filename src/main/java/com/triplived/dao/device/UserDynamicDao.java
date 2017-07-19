package com.triplived.dao.device;

import java.util.List;

import com.triplived.entity.UserDynamicDb;



public interface UserDynamicDao {

	void updateUserDeviceMapping(UserDynamicDb userDeviceObj);

	UserDynamicDb getMapping(Long userId, String deviceId);

	List<UserDynamicDb> getAllDetails();

	UserDynamicDb getDetailsByUserId(Long id);

}

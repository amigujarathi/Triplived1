package com.triplived.service.message;

import java.util.List;

import com.connectme.domain.triplived.UserDevice;

public interface GCMMessageService {
	
	List<UserDevice> getDevicesFor(String deviceId);

	 

}

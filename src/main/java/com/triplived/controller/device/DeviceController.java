package com.triplived.controller.device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.UserDevice;
import com.connectme.domain.triplived.dto.AppVersionDTO;
import com.google.gson.Gson;
import com.triplived.service.device.DeviceService;
import com.triplived.util.Constants;


@Controller
@RequestMapping("/device")
public class DeviceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class );
	
	@Autowired
	private DeviceService ds;
	
	@Value("${liveTripIds}")
    private String liveTripIds;
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String updateDevice(@RequestBody UserDevice device, 
			 @RequestHeader("UserDeviceId") String userDevice, 
			 @RequestHeader("ApplicationVersion") String appVersion, 
			 @RequestHeader("ApplicationVersionString") String applicationVersion) {
		
		if(StringUtils.isNotEmpty(applicationVersion)){
			logger.warn("Updating the application version for device  for device {} with version {} ", userDevice, applicationVersion);
			device.setApplicationVersion(appVersion);
			device.setApplicationVersionString(applicationVersion);
		}
		
		ds.updateDevice(device);
		return "updated";
	}
	
	@RequestMapping(value="/vs", method=RequestMethod.GET)
	public @ResponseBody String checkVersionOfDevice( 
			 HttpSession session, Model model, HttpServletRequest request
			 ) {
		
		AppVersionDTO dto = new AppVersionDTO();
		dto.setCurrentVersion(Constants.APP_VERSION.toString());
		dto.setMinVersion(Constants.MIN_SUPPORTED_APP_VERSION.toString());
		dto.setSyncEnabled("syncenabled");//This is for enabling automatic timeline sync
		
		/**
		 * @RequestHeader("UserDeviceId") String userDevice, 
			 @RequestHeader("appVersion") String appVersion, 
			 @RequestHeader("ApplicationVersion") String applicationVersion
			 
		 */
		String userDevice = request.getHeader("UserDeviceId");
		String appVersion = request.getHeader("ApplicationVersion");
		String applicationVersion = request.getHeader("ApplicationVersionString");
		
		if(StringUtils.isNotEmpty(applicationVersion) && StringUtils.isNotEmpty(userDevice)){
			logger.warn("Updating the application version for device  for device {} with version {} ", userDevice, applicationVersion);
			ds.updateDeviceVersion(userDevice, appVersion, applicationVersion);
		}
		
		Gson gson = new Gson();
		return gson.toJson(dto);
		
	}
	
	@RequestMapping(value="/live", method=RequestMethod.GET)
	public @ResponseBody Boolean checkIfLiveEnabled(@RequestParam("id") @Valid String userId) {
		
		/*List<String> enabledUserIdList = Arrays.asList(liveTripIds.split(","));
		if(enabledUserIdList.contains(userId)) {
			return true;
		}else {
			return false;
		}*/
		return true;
		
	}
	
	

}

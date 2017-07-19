package com.triplived.service.logProcessing.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.user.UserDao;
import com.triplived.mail.client.ActiveUsersReportMail;
import com.triplived.rest.logProcessing.ActiveUsersDetailLogClient;
import com.triplived.service.logProcessing.LogProcessingService;

@Service
public class LogProcessingServiceImpl implements LogProcessingService{
	
	@Autowired
	private ActiveUsersDetailLogClient logClient;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ActiveUsersReportMail mail;
	
	private static final Logger logger = LoggerFactory.getLogger(LogProcessingServiceImpl.class);
	
	@Override
	@Transactional
	public void getActiveUsers() throws Exception{

			List<String> activeUserslogList = logClient.execCommandAndGetLogs();
			
			List<String> userDetailList = new ArrayList<String>(activeUserslogList.size());
			for(String user : activeUserslogList) {
				Object[] obj = userDao.getUserDetailsByDeviceId(user);
				StringBuilder sb = new StringBuilder();
				if(null != obj) {
					if(null != obj[0]) {
						sb.append(obj[0].toString()+"-");
					}if(null != obj[1]) {
						sb.append(obj[1].toString()+"-");
					}if(null != obj[2]) {
						sb.append(obj[2].toString()+"-");
					}if(null != obj[3]) {
						sb.append(obj[3].toString());
					}
					if(sb.length() > 0) {
						userDetailList.add(sb.toString());
					}
				}
			}
			
			mail.sendMail(userDetailList);
			//return userDetailList;
	}
	
	
	
	

}

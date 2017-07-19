package com.triplived.service.report.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.ReportDTO;
import com.connectme.domain.triplived.dto.TripCommentDTO;
import com.connectme.domain.triplived.dto.TripSummaryDTO;
import com.connectme.domain.triplived.trip.dto.ReportUser;
import com.triplived.dao.report.ReportDAO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.ReportUserDb;
import com.triplived.entity.TripCommentsDb;
import com.triplived.entity.TripUserDb;
import com.triplived.service.report.ReportService;

@Service("reportService")
@Transactional
public class ReportServiceImpl implements ReportService {

	private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
	
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
	private ReportDAO reportDAO;

    @Override
	public String selectUserFromReport(String username, String password) {
		// TODO Auto-generated method stub
		//report.setUsername(report.getUsername());
		//report.setPassword(report.getPassword());
		
		ReportUserDb newObj = new ReportUserDb();
		newObj.setName(username);
		newObj.setPassword(password);
			
		ReportUserDb tripUser = reportDAO.getReportUser(username, password);
		//newObj.setTripUserId(tripUser.getUserId());
		
		//tripDAO.updateTripShare(newObj);
		
		/*try {
			gcmService.sendTripShareNotification(tripId.toString(), userId.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error while sharing trip - {} by user - {} . Exception is - {}", tripId, userId, e.getStackTrace());
		}*/
		return "Updated";

		//return null;
	}
}


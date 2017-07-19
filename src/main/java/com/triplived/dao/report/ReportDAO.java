package com.triplived.dao.report;

import com.triplived.entity.ReportUserDb;

public interface ReportDAO {
	
	ReportUserDb getReportUser(String username, String password);

}

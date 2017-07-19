package com.triplived.dao.report.impl;

import java.io.Serializable;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.report.ReportDAO;
import com.triplived.entity.ReportUserDb;




@Repository
public class ReportDAOImpl extends GenericHibernateDAO<ReportUserDb, Serializable> implements ReportDAO {
	
	
	@Override
	public ReportUserDb getReportUser(String username, String password) {
		// TODO Auto-generated method stub		
		ReportUserDb reportDb = (ReportUserDb)this.getCurrentSession().createQuery("Select username, password FROM com.triplived.entity.ReportUserDb report where username = :username and password = :password").setString("username", username).setString("password", password).uniqueResult();
		return reportDb;
	}

	
}

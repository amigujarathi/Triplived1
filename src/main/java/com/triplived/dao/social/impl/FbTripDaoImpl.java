package com.triplived.dao.social.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.social.FbTripDao;
import com.triplived.entity.FbTripDb;
import com.triplived.entity.MessageDetailsDb;

@Repository
@Transactional("txManager")
public class FbTripDaoImpl extends GenericHibernateDAO<FbTripDb, Serializable> implements FbTripDao {

	@Override
	public void updateFbTrip(FbTripDb fbTrip) {
		saveOrUpdate(fbTrip);
	}
	
	@Override
	public FbTripDb checkIfFbDetailsExist(Long tripId) {
		
		FbTripDb fbTripDb = (FbTripDb) getSession().createQuery("Select fbTripDb FROM com.triplived.entity.FbTripDb fbTripDb "
				+ "where TRIP_ID =:tripId").setLong("tripId", tripId).uniqueResult();
		
		return fbTripDb;
		
	}
}

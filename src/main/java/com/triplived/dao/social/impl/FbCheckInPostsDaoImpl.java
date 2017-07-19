package com.triplived.dao.social.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.social.FbCheckInPostsDao;
import com.triplived.dao.social.FbTripDao;
import com.triplived.entity.FbCheckInPostsDb;
import com.triplived.entity.FbTripDb;

@Repository
@Transactional("txManager")
public class FbCheckInPostsDaoImpl extends GenericHibernateDAO<FbCheckInPostsDb, Serializable> implements FbCheckInPostsDao {

	@Override
	public void updateFbCheckIn(FbCheckInPostsDb fbCheckIn) {
		saveOrUpdate(fbCheckIn);
	}
}

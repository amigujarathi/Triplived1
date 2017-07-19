package com.triplived.dao.device.impl;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.device.UserDynamicDao;
import com.triplived.entity.PersonDb;
import com.triplived.entity.UserDynamicDb;

@Repository
@Transactional("txManager")
public class UserDynamiceDaoImpl extends GenericHibernateDAO<UserDynamicDb, Serializable> implements UserDynamicDao {
	
	@Override
	public void updateUserDeviceMapping(UserDynamicDb obj) {
		saveOrUpdate(obj);
	}
	
	@Override
	public UserDynamicDb getMapping(Long userId, String deviceId) {
		return (UserDynamicDb) getSession().createQuery("Select user_dynamic from com.triplived.entity.UserDynamicDb user_dynamic where userId = :userId and deviceId = :deviceId").setLong("userId", userId).setString("deviceId",deviceId).uniqueResult();
	}
	
	@Override
	public List<UserDynamicDb> getAllDetails() {
		List<UserDynamicDb> list = (List<UserDynamicDb>) getSession().createQuery("SELECT user_dynamic FROM  com.triplived.entity.UserDynamicDb user_dynamic").list();
		return list;
	}
	
	@Override
	public UserDynamicDb getDetailsByUserId(Long id) {
		List<UserDynamicDb> list = (List<UserDynamicDb>) getSession().createQuery("SELECT user_dynamic FROM  com.triplived.entity.UserDynamicDb user_dynamic where userId = :id ORDER BY UPDATED_DATE DESC").setLong("id", id).list();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
}

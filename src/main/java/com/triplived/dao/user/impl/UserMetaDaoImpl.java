package com.triplived.dao.user.impl;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.user.UserMetaDAO;
import com.triplived.entity.MetaDbId;
import com.triplived.entity.TripMetaData;
import com.triplived.entity.UserMetaDb;

@Repository
public class UserMetaDaoImpl extends GenericHibernateDAO<UserMetaDb, Serializable> implements UserMetaDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserMetaDaoImpl.class );
	
/*	@Autowired
	private SessionFactory factory;*/

	@Override
	@Transactional("txManager")
	public void updateMetaDataCount(Long userId, TripMetaData metaData) {
		
		MetaDbId id = new MetaDbId(userId, metaData.toString());
		UserMetaDb userMeta = getHibernateTemplate().get(UserMetaDb.class, id );
		
		if(userMeta == null) {
			userMeta = new UserMetaDb();
			userMeta.setId(id);
			userMeta.setMetaCount(1);
			userMeta.setUpdateDate(new Date());
		}else{
			userMeta.setMetaCount(userMeta.getMetaCount() + 1 );
			userMeta.setUpdateDate(new Date());
		}
		logger.warn("Updating count of {} for user trip  {} with value {} ", metaData.toString(), userId.toString(), userMeta.getMetaCount() ); 
		saveOrUpdate(userMeta);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFor(Long tripId, TripMetaData metaData){
	
		MetaDbId id = new MetaDbId(tripId, metaData.toString());
		UserMetaDb tripMeta = getHibernateTemplate().get(UserMetaDb.class, id );
		if(tripMeta != null){
			return tripMeta.getMetaCount();
		}
		else {
			return 0;
		}
	}
	
	@Override
	@Transactional("txManager")
	public void updateMetaDataCount(Long userId, TripMetaData metaData, int count) {
		
		MetaDbId id = new MetaDbId(userId, metaData.toString());
		UserMetaDb userMeta = getHibernateTemplate().get(UserMetaDb.class, id );
		
		if(userMeta == null) {
			userMeta = new UserMetaDb();
			userMeta.setId(id);
			userMeta.setMetaCount(count);
		}else{
			userMeta.setMetaCount(userMeta.getMetaCount()+count);
		}
		
		userMeta.setUpdateDate(new Date());
		
		logger.warn("Updating count of {} for trip {} with value {} ", metaData.toString(), userId.toString(), count ); 
		
		saveOrUpdate(userMeta);
	}

}

package com.triplived.dao.trip.impl;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.trip.TripMetaDAO;
import com.triplived.entity.TripMetaData;
import com.triplived.entity.TripMetaDb;
import com.triplived.entity.MetaDbId;

@Repository
public class TripMetaDaoImpl extends GenericHibernateDAO<TripMetaDb, Serializable> implements TripMetaDAO {

	private static final Logger logger = LoggerFactory.getLogger(TripMetaDaoImpl.class );
	
/*	@Autowired
	private SessionFactory factory;*/

	@Override
	@Transactional("txManager")
	public void updateMetaDataCount(Long tripId, TripMetaData metaData) {
		
		MetaDbId id = new MetaDbId(tripId, metaData.toString());
		TripMetaDb tripMeta = getHibernateTemplate().get(TripMetaDb.class, id );
		
		if(tripMeta == null) {
			tripMeta = new TripMetaDb();
			tripMeta.setId(id);
			tripMeta.setMetaCount(1);
		}else{
			tripMeta.setMetaCount(tripMeta.getMetaCount() + 1);			
		}
		
		tripMeta.setUpdateDate(new Date());
		
		logger.warn("Updating count of {} for trip {} with value {} ", metaData.toString(), tripId.toString(), tripMeta.getMetaCount() ); 
		
		saveOrUpdate(tripMeta);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFor(Long tripId, TripMetaData metaData){
	
		MetaDbId id = new MetaDbId(tripId, metaData.toString());
		TripMetaDb tripMeta = getHibernateTemplate().get(TripMetaDb.class, id );
		if(tripMeta != null){
			return tripMeta.getMetaCount();
		}
		else {
			return 0;
		}
	}

	@Override
	@Transactional("txManager")
	public void updateMetaDataCount(Long tripId, TripMetaData metaData, int count) {
		
		MetaDbId id = new MetaDbId(tripId, metaData.toString());
		TripMetaDb tripMeta = getHibernateTemplate().get(TripMetaDb.class, id );
		
		if(tripMeta == null) {
			tripMeta = new TripMetaDb();
			tripMeta.setId(id);
		}
		
		tripMeta.setMetaCount(count);
		tripMeta.setUpdateDate(new Date());
		
		logger.warn("Updating count of {} for trip {} with value {} ", metaData.toString(), tripId.toString(), tripMeta.getMetaCount() ); 
		
		saveOrUpdate(tripMeta);
	}
}

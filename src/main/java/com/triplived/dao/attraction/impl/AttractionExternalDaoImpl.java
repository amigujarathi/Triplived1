package com.triplived.dao.attraction.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.attraction.AttractionExternalDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.AttractionExternalDb;

@Repository
public class AttractionExternalDaoImpl extends GenericHibernateDAO<AttractionExternalDb, Serializable> implements AttractionExternalDao {
	
	@Override
	public void updateAttractionExternal(AttractionExternalDb attraction) {
		saveOrUpdate(attraction);
	}
	
	@Override
	public AttractionExternalDb getAttractionExternal(String code) {
		AttractionExternalDb attractionExternalobj = (AttractionExternalDb) getSession().createQuery("SELECT attractionExternalObj FROM  com.triplived.entity.AttractionExternalDb attractionExternalObj where GCODE = :code").setString("code", code).
				uniqueResult();
		return attractionExternalobj;
	}

	@Override
	public AttractionExternalDb getAttractionExternalByAttractionId(Long attractionId) {
		AttractionExternalDb attractionExternalobj = (AttractionExternalDb) getSession().createQuery("SELECT attractionExternalObj "
				+ "FROM  com.triplived.entity.AttractionExternalDb attractionExternalObj where attractionId = :attractionId").setLong("attractionId", attractionId).
				uniqueResult();
		return attractionExternalobj;
	}
}

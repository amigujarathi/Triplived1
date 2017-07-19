package com.triplived.dao.attraction.impl;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.attraction.AttractionImageDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.AttractionImageDb;


@Repository
@Transactional("txManager")
public class AttractionImageDaoImpl extends GenericHibernateDAO<AttractionImageDb, Serializable> implements AttractionImageDao {

	@Override
	public void addAttractionImage(AttractionImageDb attractionImage) {
		save(attractionImage);
	}

	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public int updateImageStatus(String name, String attractionId) {
		 
		String deleteSQL = "update attraction_image set status= :status, UPDATED_DATE = :updatedate where ATTRACTION_ID = :attractionId and IMAGE_NAME=:imagename";
		int totalRowsUpdate = getSession().createSQLQuery(deleteSQL).setTimestamp("updatedate", new Date()).setString("status", "D").setString("attractionId", attractionId).setString("imagename", name).executeUpdate();
		
		return totalRowsUpdate;
	}
}

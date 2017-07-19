package com.triplived.dao.attraction.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.attraction.AttractionDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.AttractionDb;
import com.triplived.entity.CityDb;

@Repository
@Transactional("txManager")
public class AttractionDaoImpl extends GenericHibernateDAO<AttractionDb, Serializable> implements AttractionDao {
	
	@Override
	public void updateAttraction(AttractionDb attraction) {
		saveOrUpdate(attraction);
	}
	
	@Override
	public int updateInactivateAttraction(String attractionId, String userId) {
		
		String deleteSQL = "update attraction set status= :status, UPDATED_DATE = :updatedate, UPDATED_BY = :userId where ATTRACTION_ID = :attractionId ";
		int totalRowsUpdate = getSession().createSQLQuery(deleteSQL).setTimestamp("updatedate", new Date()).setString("status", "I").setString("userId", userId).setString("attractionId", attractionId).executeUpdate();
		
		return totalRowsUpdate;
	}
	
	@Override
	public List<AttractionDb> getAttractions(String cityCode) {
		List<AttractionDb> attractions = (List<AttractionDb>) getSession().createQuery("Select attraction FROM com.triplived.entity.AttractionDb attraction where STATUS = 'A' AND CITY_CODE =:cityCode").setString("cityCode", cityCode).list();
		return attractions;
	}

	@Override
	public String getAttractionNamebyId(String attractionId) {
		String attractionName = (String) getSession().createQuery("Select name FROM com.triplived.entity.AttractionDb attraction where ATTRACTION_ID =:attractionId").setLong("attractionId", Long.parseLong(attractionId)).uniqueResult();
		return attractionName;
	}

}

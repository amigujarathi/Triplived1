package com.triplived.dao.attraction.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.attraction.CityDao;
import com.triplived.dao.attraction.CityWeatherDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.CityDb;
import com.triplived.entity.CityWeatherDb;
import com.triplived.entity.PersonDb;

/**
 * 
 * @author santosh joshi
 *
 */
@Repository
@Transactional("txManager")
public class CityWeatherDaoImpl extends GenericHibernateDAO<CityWeatherDb, Serializable> implements  CityWeatherDao {
	
	
	@Override
	public void updateCity(CityWeatherDb city){
		getSession().saveOrUpdate(city);
	}
	
	@Override
	@Transactional(readOnly=false)
	public List<CityWeatherDb> getAllCities() {
		@SuppressWarnings("unchecked")
		List<CityWeatherDb> cities = (List<CityWeatherDb>) getSession().createQuery("Select city_weather FROM com.triplived.entity.CityWeatherDb city_weather").list();
		return cities;
	}

}

package com.triplived.dao.attraction.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.attraction.CityDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.CityDb;
import com.triplived.entity.CityImageDb;
import com.triplived.entity.PersonDb;

/**
 * 
 * @author santosh joshi
 *
 */
@Repository
@Transactional("txManager")
public class CityDaoImpl extends GenericHibernateDAO<CityDb, Serializable>
		implements CityDao {

	@Autowired
	private SessionFactory factory;

	@Override
	@Transactional(readOnly = true)
	public CityDb getCityByName(String name) {
		CityDb cityDb = (CityDb) getSession()
				.createQuery(
						"SELECT city FROM  com.triplived.entity.CityDb city where CITYNAME = :name")
				.setString("name", name).uniqueResult();

		return cityDb;

	}
	
	@Override
	@Transactional(readOnly = true)
	public CityDb getCityById(String id) {
		CityDb cityDb = (CityDb) getSession()
				.createQuery(
						"SELECT city FROM  com.triplived.entity.CityDb city where CITY_CODE = :id and status = :status")
				.setString("id", id)
				.setString("status", "A")
				.uniqueResult();

		return cityDb;

	}

	@Override
	@Transactional(readOnly = false)
	public void updateCity(CityDb city) {
		getSession().saveOrUpdate(city);
	}

	@Override
	@Transactional(readOnly = false)
	public List<CityDb> getCitiesWithCoordinates() {
		@SuppressWarnings("unchecked")
		List<CityDb> cities = (List<CityDb>) getSession()
				.createQuery(
						"Select city FROM com.triplived.entity.CityDb city where LATITUDE IS NOT NULL")
				.list();
		return cities;
	}

	@Override
	@Transactional(readOnly = false)
	public List<CityDb> getAllCities() {
		@SuppressWarnings("unchecked")
		List<CityDb> cities = (List<CityDb>) getSession()
				.createQuery(
						"Select city FROM com.triplived.entity.CityDb city where CITY_CODE like 'g%'")
				.list();
		return cities;
	}

	@Override
	@Transactional
	public void updateAllCity(CityDb city) {
		getSession().saveOrUpdate(city);
	}

	@Override
	@Transactional
	public void updateCityDescription(String cityCode, String cityName,
			String cityDescrption, Date updatedDate) {
		this.factory
				.getCurrentSession()
				.createQuery(
						"update com.triplived.entity.CityDb city set DESCRIPTION = :cityDescrption, UPDATED_DATE = :updatedDate where CITY_CODE = :cityCode")
				.setString("cityDescrption", cityDescrption)
				.setString("cityCode", cityCode)
				.setDate("updatedDate", updatedDate).executeUpdate();

	}
	
	@Override
	@Transactional(readOnly = true)
	public CityImageDb getCityImageByCityId(String cityId) {
		CityImageDb cityImageDb = (CityImageDb) getSession()
				.createQuery(
						"SELECT city_image FROM  com.triplived.entity.CityImageDb city_image where CITY_ID = :cityId and STATUS = 'A'")
				.setString("cityId", cityId).uniqueResult();

		return cityImageDb;

	}
	
	@Override
	@Transactional
	public void updateCityImage(CityImageDb cityImage) {
		getSession().saveOrUpdate(cityImage);
	}


}

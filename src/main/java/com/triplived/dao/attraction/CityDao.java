package com.triplived.dao.attraction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.CityDb;
import com.triplived.entity.CityImageDb;
import com.triplived.entity.PersonDb;

/**
 * 
 * @author santosh joshi
 *
 */
public interface CityDao extends GenericDAO<CityDb, Serializable> {

	CityDb getCityByName(String name);

	void updateCity(CityDb city);

	List<CityDb> getCitiesWithCoordinates();

	List<CityDb> getAllCities();

	void updateAllCity(CityDb city);
	

	void updateCityDescription(String cityCode, String cityName,
			String cityDescrption, Date updatedDate);

	CityDb getCityById(String id);

	CityImageDb getCityImageByCityId(String cityId);

	void updateCityImage(CityImageDb cityImage);

}

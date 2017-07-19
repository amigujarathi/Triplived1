package com.triplived.dao.attraction;

import java.io.Serializable;
import java.util.List;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.CityDb;
import com.triplived.entity.CityWeatherDb;
import com.triplived.entity.PersonDb;

/**
 * 
 * @author santosh joshi
 *
 */
public interface CityWeatherDao extends GenericDAO<CityWeatherDb, Serializable> {


	void updateCity(CityWeatherDb city);

	List<CityWeatherDb> getAllCities();

}

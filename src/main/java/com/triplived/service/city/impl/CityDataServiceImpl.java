package com.triplived.service.city.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.attraction.CityDao;
import com.triplived.entity.CityDb;
import com.triplived.entity.CityImageDb;
import com.triplived.service.city.CityDataService;

@Service
public class CityDataServiceImpl implements CityDataService {

	@Autowired
	CityDao cityDao;
	
	@Override
	public void updateCityDescription(String userName,String cityCode, String cityName,
			String cityDescription) {
		
		CityDb cityObj = cityDao.getCityById(cityCode);
		cityObj.setCityDescription(cityDescription);
		cityObj.setUpdatedDate(new Date());
		cityObj.setUpdatedBy(userName);
		cityDao.updateCity(cityObj);
		//cityDao.updateCityDescription(cityCode, cityName, cityDescrption, new Date());
		
	}
	
	@Override
	@Transactional
	public void updateCityImagePath(String cityId, String path, String updatedBy) {
		
		CityDb cityObj = cityDao.getCityById(cityId);
		cityObj.setUpdatedDate(new Date());
		cityDao.updateCity(cityObj);
		
		//Check for city image if it exists from before. If yes, then set status to inactive and update the new one
		CityImageDb cityImageObj = cityDao.getCityImageByCityId(cityId);
		if(null != cityImageObj) {
			cityImageObj.setImagePath(path);
			cityImageObj.setUpdatedDate(new Date());
			cityImageObj.setAddedBy(updatedBy);
		}else {
			cityImageObj = new CityImageDb();
			cityImageObj.setCityId(cityId);
			cityImageObj.setCreatedDate(new Date());
			cityImageObj.setUpdatedDate(new Date());
			cityImageObj.setImagePath(path);
			cityImageObj.setStatus("A");
			cityImageObj.setAddedBy(updatedBy);
		}
		cityDao.updateCityImage(cityImageObj);
		
	}

}

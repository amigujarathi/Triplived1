package com.triplived.service.trip.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.connectme.domain.triplived.dto.TripCategoryDTO;
import com.triplived.dao.trip.TripCategoryDAO;
import com.triplived.entity.TripCategoryDb;
import com.triplived.service.trip.TripCategoryService;


@Service
public class TripCategoryServiceImpl implements TripCategoryService {

	
	@Autowired
	TripCategoryDAO tripCategoryDao;
	
	@Override
	public List<TripCategoryDTO> getTripCategories() {
		List<TripCategoryDb> categoryList = tripCategoryDao.displayTripCategories();
		return ConvertCategoryDTO(categoryList);
	}

	private List<TripCategoryDTO> ConvertCategoryDTO(
			List<TripCategoryDb> categoryList) {
		List<TripCategoryDTO> listOfCategories = new ArrayList<TripCategoryDTO>();
		for(TripCategoryDb obj : categoryList) {
			TripCategoryDTO newObj = new TripCategoryDTO();
			newObj.setCategoryID(obj.getId());
			newObj.setCategoryName(obj.getTripCategoryName());
		
			listOfCategories.add(newObj);
		}
		return listOfCategories;
	}

		
}

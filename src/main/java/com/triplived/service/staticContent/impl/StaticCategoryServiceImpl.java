package com.triplived.service.staticContent.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.AttractionResponse;
import com.connectme.domain.triplived.dto.CategoryResponseDTO;
import com.triplived.dao.category.CategoryDao;
import com.triplived.entity.CategoryDb;
import com.triplived.service.staticContent.StaticCategoryService;


@Service
public class StaticCategoryServiceImpl implements StaticCategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<CategoryResponseDTO> displayCategory(){
		try {
			List<CategoryDb> categoryDocumentList = categoryDao.getCategories();
			return ConvertCategoryDTO(categoryDocumentList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return null;
		
	}

	private List<CategoryResponseDTO> ConvertCategoryDTO(
			List<CategoryDb> categoryDocumentList) {
		
		List<CategoryResponseDTO> listOfCategories = new ArrayList<CategoryResponseDTO>();
		for(CategoryDb obj : categoryDocumentList) {
			CategoryResponseDTO newObj = new CategoryResponseDTO();
			newObj.setCategorySeq(obj.getCategory_seq());
			newObj.setName(obj.getName());
		
			listOfCategories.add(newObj);
		}
		return listOfCategories;
	}

}

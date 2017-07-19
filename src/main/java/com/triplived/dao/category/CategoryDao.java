package com.triplived.dao.category;

import java.io.Serializable;
import java.util.List;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.CategoryDb;

public interface CategoryDao {
	List<CategoryDb> getCategories();

}

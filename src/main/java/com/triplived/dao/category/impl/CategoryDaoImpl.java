package com.triplived.dao.category.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.category.CategoryDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.CategoryDb;

@Repository
public class CategoryDaoImpl implements CategoryDao{

	@Autowired
	private SessionFactory factory;
	
	@Override
	@Transactional
	public List<CategoryDb> getCategories() {
		List<CategoryDb> categories = (List<CategoryDb>)this.factory.getCurrentSession().createQuery("Select category FROM com.triplived.entity.CategoryDb category").list();
		return categories;
	}
	
}

package com.triplived.dao.attraction.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;


import com.triplived.dao.attraction.AttractionCategoryMappingDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.AttractionCategoryMappingDb;


@Repository
public class AttractionCategoryMappingDaoImpl extends GenericHibernateDAO<AttractionCategoryMappingDb, Serializable> implements AttractionCategoryMappingDao {
	
}

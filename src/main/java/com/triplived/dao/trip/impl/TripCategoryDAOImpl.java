package com.triplived.dao.trip.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.trip.TripCategoryDAO;
import com.triplived.entity.TripCategoryDb;

@Repository
public class TripCategoryDAOImpl implements TripCategoryDAO{
	
	@Autowired
	private SessionFactory factory;
	

/*	@Override
	public TripUserDb getTripIdByEmail(Long email) {
		Boolean value = false;
		return (TripUserDb)this.factory.getCurrentSession().createQuery("Select user_trip from com.triplived.entity.TripUserDb user_trip where userId = :userId and complete = :complete").setLong("userId", email).setBoolean("complete", value).uniqueResult();
	}*/
	
	@Override
	@Transactional
	public List<TripCategoryDb> displayTripCategories() {
		List<TripCategoryDb> tripCategoryList = (List<TripCategoryDb>) this.factory.getCurrentSession().createQuery("Select trip_categories from com.triplived.entity.TripCategoryDb trip_categories").list();
		return tripCategoryList;
	}
	
	
	
}

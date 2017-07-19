package com.triplived.dao.trip.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.trip.TripEntityCurationEditDAO;
import com.triplived.dao.trip.TripReviewEditDAO;
import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;

@Repository
public class TripEntityCurationEditDaoImpl implements TripEntityCurationEditDAO {

	@Autowired
	private SessionFactory factory;

	@Override
	@Transactional
	public List<AttractionCuratedSuggestionsDb> getAttractionReviews(String attractionId) {
		List<AttractionCuratedSuggestionsDb> obj = (List<AttractionCuratedSuggestionsDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select attraction_curated_suggestions FROM com.triplived.entity.AttractionCuratedSuggestionsDb attraction_curated_suggestions where ATTRACTION_ID =:attractionId")
				.setString("attractionId", attractionId).list();
		return obj;
	}
	
	@Override
	@Transactional
	public List<HotelCuratedDb> getHotelReviews(String hotelId) {
		List<HotelCuratedDb> obj = (List<HotelCuratedDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select hotel_curated_suggestions FROM com.triplived.entity.HotelCuratedDb hotel_curated_suggestions where HOTEL_ID =:hotelId")
				.setString("hotelId", hotelId).list();
		return obj;
	}

	@Override
	@Transactional
	public void updateCuratedAttractionReviews(AttractionCuratedSuggestionsDb obj) {
		this.factory.getCurrentSession().saveOrUpdate(obj);
	}

	@Override
	@Transactional
	public void updateCuratedHotelReviews(HotelCuratedDb obj) {
		this.factory.getCurrentSession().saveOrUpdate(obj);
	}

}

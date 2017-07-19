package com.triplived.dao.trip.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.trip.TripReviewEditDAO;
import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;

@Repository
public class TripReviewEditDaoImpl implements TripReviewEditDAO {

	@Autowired
	private SessionFactory factory;

	@Override
	@Transactional
	public List<TripAttractionDetailsDb> getAttractionReviews(Long tripId) {
		List<TripAttractionDetailsDb> obj = (List<TripAttractionDetailsDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip_attraction_details FROM com.triplived.entity.TripAttractionDetailsDb trip_attraction_details where TRIP_ID =:id")
				.setLong("id", tripId).list();
		return obj;
	}

	@Override
	@Transactional
	public void saveCuratedAttractionReviews(AttractionCuratedSuggestionsDb attrObj) {
		this.factory.getCurrentSession().saveOrUpdate(attrObj);
	}

	@Override
	@Transactional
	public List<TripHotelDetailsDb> getHotelReviews(Long tripId) {
		List<TripHotelDetailsDb> obj = (List<TripHotelDetailsDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip_hotel_details FROM com.triplived.entity.TripHotelDetailsDb trip_hotel_details where TRIP_ID =:id")
				.setLong("id", tripId).list();
		return obj;
	}

	@Override
	@Transactional
	public void updateCuratedAttractionReviews(TripAttractionDetailsDb obj) {
		String updateCuratedStatus = "update com.triplived.entity.TripAttractionDetailsDb trip_attraction_details set CURATED =:status, UPDATED_DATE =:updatedate, CURATED_BY =:userId , REVIEW =:review, SUGGESTION =:suggestion where ATTRACTION_ID =:attractionId ";
		int totalRowsUpdate = this.factory.getCurrentSession()
				.createQuery(updateCuratedStatus)
				.setTimestamp("updatedate", new Date())
				.setString("status", "Y").setString("userId", obj.getCuratedBy())
				.setString("review", obj.getReview()).setString("suggestion", obj.getSuggestion())
				.setString("attractionId", obj.getAttractionId())
				.executeUpdate();
	}

	@Override
	@Transactional
	public void saveCuratedHotelReviews(HotelCuratedDb attrObj) {
		this.factory.getCurrentSession().saveOrUpdate(attrObj);
	}

	@Override
	@Transactional
	public void updateCuratedHotelReviews(TripHotelDetailsDb obj) {
		String updateCuratedStatus = "update com.triplived.entity.TripHotelDetailsDb trip_hotel_details set CURATED =:status, UPDATED_DATE =:updatedate, CURATED_BY =:userId , REVIEW =:review, SUGGESTION =:suggestion where HOTEL_ID =:hotelId ";
		int totalRowsUpdate = this.factory.getCurrentSession()
				.createQuery(updateCuratedStatus)
				.setTimestamp("updatedate", new Date())
				.setString("status", "Y").setString("userId", obj.getCuratedBy())
				.setString("review", obj.getReview()).setString("suggestion", obj.getSuggestion())
				.setString("hotelId", obj.getHotelId())
				.executeUpdate();
	}

}

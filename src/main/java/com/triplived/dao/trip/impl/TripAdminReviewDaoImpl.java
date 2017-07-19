package com.triplived.dao.trip.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.trip.TripAdminReviewDAO;
import com.triplived.dao.trip.TripReviewEditDAO;
import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAdminReviewCommentDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;

@Repository
public class TripAdminReviewDaoImpl implements TripAdminReviewDAO {

	@Autowired
	private SessionFactory factory;

	@Override
	@Transactional
	public List<TripAdminReviewCommentDb> getTripReviews(Long tripId) {
		List<TripAdminReviewCommentDb> obj = (List<TripAdminReviewCommentDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip_admin_review_comments FROM com.triplived.entity.TripAdminReviewCommentDb trip_admin_review_comments where STATUS = 'A' and TRIP_ID =:id")
				.setLong("id", tripId).list();
		return obj;
	}

	@Override
	@Transactional
	public void saveTripReviews(TripAdminReviewCommentDb obj) {
		this.factory.getCurrentSession().saveOrUpdate(obj);

	}
}

package com.triplived.dao.trip;

import java.util.List;

import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAdminReviewCommentDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;

public interface TripAdminReviewDAO {
	
	List<TripAdminReviewCommentDb>getTripReviews(Long tripId);
	void saveTripReviews(TripAdminReviewCommentDb obj);
}

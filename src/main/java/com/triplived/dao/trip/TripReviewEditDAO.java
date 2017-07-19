package com.triplived.dao.trip;

import java.util.List;

import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;

public interface TripReviewEditDAO {
	List<TripAttractionDetailsDb>getAttractionReviews(Long tripId);
	List<TripHotelDetailsDb>getHotelReviews(Long tripId);
	void saveCuratedAttractionReviews(AttractionCuratedSuggestionsDb attrObj);
	void updateCuratedAttractionReviews(TripAttractionDetailsDb obj);
	void saveCuratedHotelReviews(HotelCuratedDb attrObj);
	void updateCuratedHotelReviews(TripHotelDetailsDb obj);

}

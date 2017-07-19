package com.triplived.dao.trip;

import java.util.List;

import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;

public interface TripEntityCurationEditDAO {
	List<AttractionCuratedSuggestionsDb>getAttractionReviews(String attractionId);
	List<HotelCuratedDb>getHotelReviews(String hotelId);
	void updateCuratedAttractionReviews(AttractionCuratedSuggestionsDb obj);
	void updateCuratedHotelReviews(HotelCuratedDb obj);

}

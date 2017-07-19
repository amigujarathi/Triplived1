package com.triplived.service.trip;

import java.util.List;

import com.connectme.domain.triplived.dto.AttractionCuratedSuggestionDTO;
import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.HotelCuratedSuggestionDTO;
import com.connectme.domain.triplived.dto.HotelReviewDTO;
import com.triplived.entity.CuratedAttractionReview;
import com.triplived.entity.CuratedHotelReview;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.entity.UpdatedHotelReview;

public interface TripEntityCurationEditService {
	List<AttractionCuratedSuggestionDTO> getAttractionReviews(String attractionID);
	
	List<HotelCuratedSuggestionDTO> getHotelReviews(String tripID);
	
	void saveCuratedAttractionReviews(CuratedAttractionReview curatedAttractionReview , String userName);
	
	void saveCuratedHotelReviews(CuratedHotelReview curatedHotelReview, String userName);
	
}

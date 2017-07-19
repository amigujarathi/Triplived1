package com.triplived.service.trip;

import java.util.List;

import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.HotelReviewDTO;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.entity.UpdatedHotelReview;

public interface TripReviewEditService {
	List<AttractionReviewDTO> getAttractionReviews(Long tripID);
	
	List<HotelReviewDTO> getHotelReviews(Long tripID);
	
	void saveCuratedAttractionReviews(UpdatedAttractionReview updatedAttractionReview, String userName);
	
	void saveCuratedHotelReviews(UpdatedHotelReview updatedHOtelReview,String userName);

}

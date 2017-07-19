package com.triplived.service.trip;

import java.util.List;

import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.HotelReviewDTO;
import com.connectme.domain.triplived.dto.TripAdminReviewDTO;
import com.triplived.entity.TripReviewData;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.entity.UpdatedHotelReview;

public interface TripAdminReviewService {
	
	List<TripAdminReviewDTO> getTripReviews(Long TripId);
	void saveTripReviews(TripReviewData tripReviewData , Long userId);

}

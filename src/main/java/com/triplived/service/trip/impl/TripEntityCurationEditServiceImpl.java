package com.triplived.service.trip.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.RequiredStatus;
import com.connectme.domain.triplived.dto.AttractionCuratedSuggestionDTO;
import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.HotelCuratedSuggestionDTO;
import com.connectme.domain.triplived.dto.HotelReviewDTO;
import com.triplived.dao.attraction.AttractionDao;
import com.triplived.dao.hotel.HotelDao;
import com.triplived.dao.trip.TripEntityCurationEditDAO;
import com.triplived.dao.trip.TripReviewEditDAO;
import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.CuratedAttractionReview;
import com.triplived.entity.CuratedHotelReview;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.entity.UpdatedHotelReview;
import com.triplived.service.trip.TripEntityCurationEditService;
import com.triplived.service.trip.TripReviewEditService;

@Service
public class TripEntityCurationEditServiceImpl implements TripEntityCurationEditService {

	private static final Logger logger = LoggerFactory
			.getLogger(TripEntityCurationEditServiceImpl.class);

	@Autowired
	TripEntityCurationEditDAO tripEntityCurationEditDao;
	
	@Autowired
	AttractionDao attractionDao;
	
	@Autowired
	HotelDao hotelDao;

	@Override
	public List<AttractionCuratedSuggestionDTO> getAttractionReviews(String attractionId) {
		List<AttractionCuratedSuggestionsDb> attractionReview = tripEntityCurationEditDao.getAttractionReviews(attractionId);
		return convertAttractionCuratedSuggestionDTO(attractionReview);
	}

	private List<AttractionCuratedSuggestionDTO> convertAttractionCuratedSuggestionDTO(
			List<AttractionCuratedSuggestionsDb> attractionReview) {
		List<AttractionCuratedSuggestionDTO> reviews = new ArrayList<AttractionCuratedSuggestionDTO>();
		for (AttractionCuratedSuggestionsDb obj : attractionReview) {
			AttractionCuratedSuggestionDTO newObj = new AttractionCuratedSuggestionDTO();
			newObj.setTripId(obj.getTripId());
			newObj.setAttractionId(obj.getAttractionId());
			newObj.setReview(obj.getReview());
			if(obj.getSuggestion() == null )
				newObj.setSuggestion("Enter Data");
			else
				newObj.setSuggestion(obj.getSuggestion());
			newObj.setSource(obj.getSource());
			newObj.setStatus(obj.getStatus());
			newObj.setCurateBy(obj.getCuratedBy());
			newObj.setUpdateDate(obj.getUpdateDate());
			newObj.setId(obj.getId());
			reviews.add(newObj);
		}
		return reviews;
	}

	@Override
	public void saveCuratedAttractionReviews(
			CuratedAttractionReview updatedAttractionReview,String userName) {
		
		AttractionCuratedSuggestionsDb ObjDb = new AttractionCuratedSuggestionsDb();
		
		ObjDb.setTripId(Long.parseLong(updatedAttractionReview.getTripId()));
		ObjDb.setAttractionId(updatedAttractionReview.getAttractionId());
		ObjDb.setReview(updatedAttractionReview.getReview());
		ObjDb.setSuggestion(updatedAttractionReview.getSuggestion());
		ObjDb.setCuratedBy(userName);
		ObjDb.setUpdateDate(new Date());
		ObjDb.setCreatedDate(new Date());
		ObjDb.setSource(updatedAttractionReview.getSource()); // This curated data is from user's trip. Hence the source TRIP
		ObjDb.setStatus(updatedAttractionReview.getStatus());
		ObjDb.setRank(3L);
		ObjDb.setId(updatedAttractionReview.getId());
		tripEntityCurationEditDao.updateCuratedAttractionReviews(ObjDb);
	}

	@Override
	public List<HotelCuratedSuggestionDTO> getHotelReviews(String hotelID) {
		List<HotelCuratedDb> hotelReview = tripEntityCurationEditDao.getHotelReviews(hotelID);
		return convertHotelCuratedSuggestionDTO(hotelReview);
	}

	private List<HotelCuratedSuggestionDTO> convertHotelCuratedSuggestionDTO(
			List<HotelCuratedDb> hotelReview) {
		List<HotelCuratedSuggestionDTO> reviews = new ArrayList<HotelCuratedSuggestionDTO>();
		for (HotelCuratedDb obj : hotelReview) {
			HotelCuratedSuggestionDTO newObj = new HotelCuratedSuggestionDTO();
			newObj.setTripId(obj.getTripId());
			newObj.setHotelId(obj.getHotelId());
			newObj.setReview(obj.getReview());
			if(obj.getSuggestion() == null || obj.getSuggestion() == "")
				newObj.setSuggestion("Enter Data");
			else
				newObj.setSuggestion(obj.getSuggestion());
			newObj.setSource(obj.getSource());
			newObj.setStatus(obj.getStatus());
			newObj.setCurateBy(obj.getCuratedBy());
			newObj.setUpdateDate(obj.getUpdateDate());
			newObj.setId(obj.getId());
			reviews.add(newObj);
		}
		return reviews;
	}

	@Override
	public void saveCuratedHotelReviews(CuratedHotelReview updatedHotelReview,String userName) {
		HotelCuratedDb ObjDb = new HotelCuratedDb();
		ObjDb.setTripId(Long.parseLong(updatedHotelReview.getTripId()));
		ObjDb.setHotelId(updatedHotelReview.getHotelId());
		ObjDb.setReview(updatedHotelReview.getReview());
		ObjDb.setSuggestion(updatedHotelReview.getSuggestion());
		ObjDb.setCuratedBy(userName);
		ObjDb.setUpdateDate(new Date());
		ObjDb.setCreatedDate(new Date());
		ObjDb.setSource(updatedHotelReview.getSource()); // This curated data is from user's trip. Hence the source TRIP
		ObjDb.setStatus(updatedHotelReview.getStatus());
		ObjDb.setRank(3L);
		ObjDb.setId(updatedHotelReview.getId());
		tripEntityCurationEditDao.updateCuratedHotelReviews(ObjDb);
		
	}
}

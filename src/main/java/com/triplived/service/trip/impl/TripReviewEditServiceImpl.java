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
import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.HotelReviewDTO;
import com.triplived.dao.attraction.AttractionDao;
import com.triplived.dao.hotel.HotelDao;
import com.triplived.dao.trip.TripReviewEditDAO;
import com.triplived.entity.AttractionCuratedSuggestionsDb;
import com.triplived.entity.HotelCuratedDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.entity.UpdatedHotelReview;
import com.triplived.service.trip.TripReviewEditService;

@Service
public class TripReviewEditServiceImpl implements TripReviewEditService {

	private static final Logger logger = LoggerFactory
			.getLogger(TripReviewEditServiceImpl.class);

	@Autowired
	TripReviewEditDAO tripReviewEditDao;
	
	@Autowired
	AttractionDao attractionDao;
	
	@Autowired
	HotelDao hotelDao;

	@Override
	public List<AttractionReviewDTO> getAttractionReviews(Long tripID) {
		List<TripAttractionDetailsDb> attractionReview = tripReviewEditDao.getAttractionReviews(tripID);
		return convertAttractionReviewDTO(attractionReview);
	}

	private List<AttractionReviewDTO> convertAttractionReviewDTO(
			List<TripAttractionDetailsDb> attractionReview) {
		String attractionName = null;
		List<AttractionReviewDTO> reviews = new ArrayList<AttractionReviewDTO>();
		for (TripAttractionDetailsDb obj : attractionReview) {
			AttractionReviewDTO newObj = new AttractionReviewDTO();
			newObj.setTripId(obj.getTripId());
			newObj.setAttractionId(obj.getAttractionId());
			newObj.setReview(obj.getReview());
			if(obj.getSuggestion() == null )
				newObj.setSuggestion("Enter Data");
			else
				newObj.setSuggestion(obj.getSuggestion());
			attractionName = attractionDao.getAttractionNamebyId(obj.getAttractionId());
			newObj.setAttractionName(attractionName);
			newObj.setCurate(obj.getCurated().toString());
			newObj.setTimestamp(convertTimeStamptoDate(obj.getTimeStamp()));
			newObj.setAttractionDetailsId(obj.getId().toString());
			reviews.add(newObj);
		}
		return reviews;
	}

	private String convertTimeStamptoDate(Long input) {
		Date date = new Date(input);
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd hh:mm:ss z");
        sdf.setCalendar(cal);
        cal.setTime(date);
        return sdf.format(date);
	}

	@Override
	public void saveCuratedAttractionReviews(
			UpdatedAttractionReview updatedAttractionReview,String userName) {
		TripAttractionDetailsDb obj = new TripAttractionDetailsDb();
		AttractionCuratedSuggestionsDb ObjDb = new AttractionCuratedSuggestionsDb();
		
		obj.setCurated(RequiredStatus.valueOf(updatedAttractionReview.getCurate()));
		obj.setTripId(Long.parseLong(updatedAttractionReview.getTripId()));
		obj.setAttractionId(updatedAttractionReview.getAttractionId());
		obj.setReview(updatedAttractionReview.getReview());
		obj.setSuggestion(updatedAttractionReview.getSuggestion());
		obj.setCuratedBy(userName);
		
		
		ObjDb.setTripId(Long.parseLong(updatedAttractionReview.getTripId()));
		ObjDb.setAttractionId(updatedAttractionReview.getAttractionId());
		ObjDb.setReview(updatedAttractionReview.getReview());
		ObjDb.setSuggestion(updatedAttractionReview.getSuggestion());
		ObjDb.setCuratedBy(userName);
		ObjDb.setUpdateDate(new Date());
		ObjDb.setCreatedDate(new Date());
		ObjDb.setSource("TRIP"); // This curated data is from user's trip. Hence the source TRIP
		ObjDb.setStatus("A");
		ObjDb.setRank(3L);
		ObjDb.setAttractionDetailsId(updatedAttractionReview.getAttractionDetailsId().toString());
		
		tripReviewEditDao.saveCuratedAttractionReviews(ObjDb);
		tripReviewEditDao.updateCuratedAttractionReviews(obj);
	}

	@Override
	public List<HotelReviewDTO> getHotelReviews(Long tripID) {
		List<TripHotelDetailsDb> hotelReview = tripReviewEditDao.getHotelReviews(tripID);
		return convertHotelReviewDTO(hotelReview);
	}

	private List<HotelReviewDTO> convertHotelReviewDTO(
			List<TripHotelDetailsDb> hotelReview) {
		String hotelName = null;
		List<HotelReviewDTO> reviews = new ArrayList<HotelReviewDTO>();
		for (TripHotelDetailsDb obj : hotelReview) {
			HotelReviewDTO newObj = new HotelReviewDTO();
			newObj.setTripId(obj.getTripId());
			newObj.setHotelId(obj.getHotelId());
			newObj.setReview(obj.getReview());
			if(obj.getSuggestion() == null || obj.getSuggestion() == "")
				newObj.setSuggestion("Enter Data");
			else
				newObj.setSuggestion(obj.getSuggestion());
			hotelName = hotelDao.getHotelNamebyId(obj.getHotelId());
			newObj.setHotelName(hotelName);
			newObj.setCurate(obj.getCurated().toString());
			newObj.setTimestamp(convertTimeStamptoDate(obj.getTimestamp()));
			newObj.setHotelDetailsId(obj.getId().toString());
			reviews.add(newObj);
		}
		return reviews;
	}

	@Override
	public void saveCuratedHotelReviews(UpdatedHotelReview updatedHotelReview,String userName) {
		TripHotelDetailsDb obj = new TripHotelDetailsDb();
		HotelCuratedDb ObjDb = new HotelCuratedDb();
		
		obj.setCurated(RequiredStatus.valueOf(updatedHotelReview.getCurate()));
		obj.setTripId(Long.parseLong(updatedHotelReview.getTripId()));
		obj.setHotelId(updatedHotelReview.getHotelId());
		obj.setReview(updatedHotelReview.getReview());
		obj.setSuggestion(updatedHotelReview.getSuggestion());
		obj.setCuratedBy(userName);
		
		
		ObjDb.setTripId(Long.parseLong(updatedHotelReview.getTripId()));
		ObjDb.setHotelId(updatedHotelReview.getHotelId());
		ObjDb.setReview(updatedHotelReview.getReview());
		ObjDb.setSuggestion(updatedHotelReview.getSuggestion());
		ObjDb.setCuratedBy(userName);
		ObjDb.setUpdateDate(new Date());
		ObjDb.setCreatedDate(new Date());
		ObjDb.setSource("TRIP"); // This curated data is from user's trip. Hence the source TRIP
		ObjDb.setStatus("A");
		ObjDb.setRank(3L);
		ObjDb.setHotelReviewId(updatedHotelReview.getHotelDetailsId().toString());
		
		tripReviewEditDao.saveCuratedHotelReviews(ObjDb);
		tripReviewEditDao.updateCuratedHotelReviews(obj);
		
	}
}

package com.triplived.service.trip.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.dto.TripAdminReviewDTO;
import com.triplived.dao.trip.TripAdminReviewDAO;
import com.triplived.dao.user.UserDao;
import com.triplived.entity.PersonDb;
import com.triplived.entity.TripAdminReviewCommentDb;
import com.triplived.entity.TripReviewData;
import com.triplived.mail.client.TripReviewCommentMail;
import com.triplived.service.trip.TripAdminReviewService;

@Service
public class TripAdminReviewServiceImpl implements TripAdminReviewService {

	private static final Logger logger = LoggerFactory
			.getLogger(TripAdminReviewServiceImpl.class);

	@Autowired
	TripAdminReviewDAO tripAdminReviewDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	private TripReviewCommentMail tripReviewCommentMail;
	
	@Override
	public List<TripAdminReviewDTO> getTripReviews(Long tripId) {
		List<TripAdminReviewCommentDb> tripAdminReview = tripAdminReviewDao.getTripReviews(tripId);
		return convertTripAdminReviewDTO(tripAdminReview);
	}

	private List<TripAdminReviewDTO> convertTripAdminReviewDTO(
			List<TripAdminReviewCommentDb> tripAdminReview) {
		List<TripAdminReviewDTO> reviews = new ArrayList<TripAdminReviewDTO>();
		for (TripAdminReviewCommentDb obj : tripAdminReview) {
			TripAdminReviewDTO newObj = new TripAdminReviewDTO();
			newObj.setTripId(obj.getTripId());
			newObj.setRemark(obj.getRemark());
			newObj.setReview(obj.getReview());
			newObj.setStatus(obj.getStatus());
			newObj.setEntityName(obj.getEntityName());
			newObj.setUpdatedDate(obj.getUpdateDate());
			PersonDb person = userDao.getPersonByUserId(obj.getReviewerId().toString());
			String reviewerName = person.getName();
			newObj.setReviewer_id(reviewerName);
			reviews.add(newObj);
		}
		return reviews;
	}

	@Override
	public void saveTripReviews(TripReviewData tripReviewData, Long userId) {
		
		TripAdminReviewCommentDb obj = new TripAdminReviewCommentDb();
		obj.setTripId(Long.parseLong(tripReviewData.getTripId()));
		obj.setReviewerId(userId);
		obj.setRemark(tripReviewData.getRemark());
		obj.setReview(tripReviewData.getReview());
		obj.setStatus("A");
		obj.setUpdateDate(new Date());
		obj.setEntityName(tripReviewData.getEntityName());
		tripAdminReviewDao.saveTripReviews(obj);
		
		PersonDb person = userDao.getPersonByUserId(userId.toString());
		String reviewerName = person.getName();
		
		tripReviewCommentMail.sendMail(tripReviewData.getTripId(), reviewerName);
	}	
}

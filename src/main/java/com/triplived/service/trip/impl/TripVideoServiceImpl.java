package com.triplived.service.trip.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.VideoStatus;
import com.connectme.domain.triplived.dto.TripVideoDTO;
import com.connectme.domain.triplived.dto.TripVideoPathDTO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripVideoDao;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripVideoDb;
import com.triplived.service.trip.TripVideoService;

@Service
public class TripVideoServiceImpl implements TripVideoService {

	@Autowired
	TripVideoDao tripVideoDao;
	
	@Autowired
	TripDAO tripDao;

	@Override
	public List<TripVideoDTO> getTripVideoStatus(Long tripID) {
		List<TripVideoDb> categoryList = tripVideoDao
				.getTripVideoStatus(tripID);
		return ConvertVideoStatusDTO(categoryList);
	}

	private List<TripVideoDTO> ConvertVideoStatusDTO(
			List<TripVideoDb> categoryList) {
		List<TripVideoDTO> listOfCategories = new ArrayList<TripVideoDTO>();
		for (TripVideoDb obj : categoryList) {
			TripVideoDTO newObj = new TripVideoDTO();
			newObj.setTripID(obj.getTripId());
			newObj.setTripVideoStatus(obj.getStatus());

			listOfCategories.add(newObj);
		}
		return listOfCategories;
	}

	private TripVideoPathDTO ConvertTripVideoPathDTO(TripDb obj) {
		TripVideoPathDTO tripVideoDetails = new TripVideoPathDTO();
		tripVideoDetails.setServerPath(obj.getVideoServerPath());
		tripVideoDetails.setYouTubePath(obj.getVideoYoutubePath());
		tripVideoDetails.setYouTubeStatus(obj.getVideoYoutubeStatus());
		return tripVideoDetails;
	}

	@Override
	public TripVideoPathDTO getTripVideoPath(Long tripID) {
		TripDb categoryList = tripVideoDao.getTripVideoPath(tripID);
		return ConvertTripVideoPathDTO(categoryList);

	}

	@Override
	public void updateTripVideoPath(String serverPath, String youTubePath,
			String status, String tripId) {
		TripDb tripdb = new TripDb();
		tripdb = tripDao.getTripById(Long.parseLong(tripId));
		tripdb.setVideoServerPath(serverPath);
		tripdb.setVideoYoutubePath(youTubePath);
		tripdb.setVideoYoutubeStatus(status);
		tripdb.setUpdateDate(new Date());
		tripVideoDao.updateTripVideoPath(tripdb);
	}

	@Override
	public void updateTripVideoStatus(Long tripId, String TripPub,
			String VidGen, String VidReq, String YouUp, String YouLive,
			String mailSent, String NotReq, String NotSent) {
		String[] Stages = new String[10];
		Stages[0] = TripPub;
		Stages[1] = VidGen;
		Stages[2] = VidReq;
		Stages[3] = YouUp;
		Stages[4] = YouLive;
		Stages[5] = mailSent;
		Stages[6] = NotReq;
		Stages[7] = NotSent;
		TripVideoDb tripVideoDb1 = new TripVideoDb();
		tripVideoDb1.setTripId(tripId);
		tripVideoDao.deleteTripVideoStatus(tripVideoDb1);
		for (int i = 0; i < Stages.length; i++) {
			if (Stages[i] != null && !Stages[i].isEmpty()) {
				TripVideoDb tripVideoDb = new TripVideoDb();
				VideoStatus status = VideoStatus.valueOf(Stages[i]);
				tripVideoDb.setTripId(tripId);
				tripVideoDb.setvideoStatus(status);
				tripVideoDb.setUpdateDate(new Date());
				tripVideoDao.updateTripVideoStatus(tripVideoDb);
			}
		}

	}

}

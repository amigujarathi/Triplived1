package com.triplived.dao.trip.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.VideoStatus;
import com.triplived.dao.trip.TripVideoDao;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripVideoDb;

@Repository
public class TripVideoDaoImpl implements TripVideoDao {

	@Autowired
	private SessionFactory factory;

	@Override
	public void updateTripVideo(TripVideoDb tripVideo) {
		this.factory.getCurrentSession().saveOrUpdate(tripVideo);
	}

	public TripVideoDb getTripVideoState(Long tripId) {
		// TripVideoDb obj =
		// (TripVideoDb)this.factory.getCurrentSession().createQuery("Select trip_video FROM com.triplived.entity.TripVideoDb trip_video where TRIP_ID =:tripId").setLong("tripId",
		// tripId)
		return null;
	}

	@Override
	@Transactional
	public List<Object[]> getPendingTripsForVideoGeneration() {

		/*
		 * List<TripVideoDb> pendingTripsForVideo =
		 * (List<TripVideoDb>)this.factory.getCurrentSession().createQuery(
		 * "Select trip_video FROM com.triplived.entity.TripVideoDb trip_video where status = :status"
		 * ).setString("status", VideoStatus.MAIL_SENT.toString()).list();
		 * return pendingTripsForVideo;
		 */
		Query query = this.factory.getCurrentSession().createSQLQuery(
				"Select TRIP_ID, STATUS from trip_video GROUP BY TRIP_ID HAVING COUNT(TRIP_ID)"
						+ " = 2 and STATUS IN ('MAIL_SENT','TRIP_PUBLISH')");

		List<Object[]> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TripVideoDb> getTripVideoDetails(Long tripId) {

		List<TripVideoDb> tripDetails = (List<TripVideoDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip_video FROM com.triplived.entity.TripVideoDb trip_video where TRIP_ID = :tripId")
				.setLong("tripId", tripId).list();
		return tripDetails;

		/*
		 * Query query = this.factory.getCurrentSession().createSQLQuery(
		 * "Select TRIP_ID, STATUS from trip_video GROUP BY TRIP_ID HAVING COUNT(TRIP_ID) = 2 and STATUS IN (0,1)"
		 * );
		 * 
		 * List<Object[]> list = query.list(); return list;
		 */
	}

	@Override
	@Transactional
	public List<TripVideoDb> getTripVideoStatus(Long tripID) {
		List<TripVideoDb> obj = (List<TripVideoDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip_video FROM com.triplived.entity.TripVideoDb trip_video where TRIP_ID =:tripId")
				.setLong("tripId", tripID).list();
		return obj;
	}

	@Override
	@Transactional
	public TripDb getTripVideoPath(Long tripID) {
		TripDb obj = (TripDb) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip FROM com.triplived.entity.TripDb trip where ID =:id")
				.setLong("id", tripID).uniqueResult();
		return obj;
	}

	@Override
	@Transactional
	public void updateTripVideoPath(TripDb tripDb) {
		this.factory.getCurrentSession().saveOrUpdate(tripDb);
	}

	@Override
	@Transactional
	public void updateTripVideoStatus(TripVideoDb tripVideo) {
		Long tripId = tripVideo.getTripId();
		String status = tripVideo.getVideoStatus().toString();
		TripVideoDb obj = (TripVideoDb) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip_video FROM com.triplived.entity.TripVideoDb trip_video where TRIP_ID = :tripId and STATUS = :status")
				.setLong("tripId", tripId).setString("status", status)
				.uniqueResult();
		if (obj == null || obj.equals(null)) {
			this.factory.getCurrentSession().saveOrUpdate(tripVideo);
		}
	}

	@Override
	@Transactional
	public void deleteTripVideoStatus(TripVideoDb tripVideoDb) {
		Long tripId = tripVideoDb.getTripId();
		List<TripVideoDb> obj = (List<TripVideoDb>) this.factory
				.getCurrentSession()
				.createQuery(
						"Select trip_video FROM com.triplived.entity.TripVideoDb trip_video where TRIP_ID = :tripId")
				.setLong("tripId", tripId).list();
		if (obj != null) {
			this.factory
					.getCurrentSession()
					.createQuery(
							"delete from com.triplived.entity.TripVideoDb trip_video where TRIP_ID = :tripId ")
					.setLong("tripId", tripId).executeUpdate();
		}
	}

}

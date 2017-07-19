package com.triplived.dao.trip.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.triplived.dao.trip.TravelAgentDao;
import com.triplived.entity.TravelAgentDetailsDb;
import com.triplived.entity.TravelPartnerTripMappingDb;

@Repository
public class TravelAgentDaoImpl implements TravelAgentDao{
	
	@Autowired
	private SessionFactory factory;
	
	@Override
	public void updateTravelAgentDetails(TravelAgentDetailsDb travel){
		this.factory.getCurrentSession().saveOrUpdate(travel);
	}
	
	@Override
	public TravelAgentDetailsDb getTravelAgentDetail(Long id){
				
		TravelAgentDetailsDb travelAgentDetail = (TravelAgentDetailsDb) this.factory.getCurrentSession()
				.createQuery(
						"Select travel_agent_details FROM com.triplived.entity.TravelAgentDetailsDb travel_agent_details where ID = :id")
				.setLong("id", id).uniqueResult();
		return travelAgentDetail;
	}
	

	@Override
	public List<TravelPartnerTripMappingDb> getTravelPartnerTrips(Long id){
				
		List<TravelPartnerTripMappingDb> travelPartnerTrips = (List<TravelPartnerTripMappingDb>) this.factory.getCurrentSession()
				.createQuery(
						"Select travel_partner_trip_mapping FROM com.triplived.entity.TravelPartnerTripMappingDb travel_partner_trip_mapping where TRAVEL_PARTNER_ID = :id")
				.setLong("id", id).list();
		return travelPartnerTrips;
	}
	
	@Override
	public TravelAgentDetailsDb getTravelAgentDetailByCustomId(String id){
				
		TravelAgentDetailsDb travelAgentDetail = (TravelAgentDetailsDb) this.factory.getCurrentSession()
				.createQuery(
						"Select travel_agent_details FROM com.triplived.entity.TravelAgentDetailsDb travel_agent_details where CUSTOM_ID = :id")
				.setString("id", id).uniqueResult();
		return travelAgentDetail;
	}
	
	@Override
	public List<TravelAgentDetailsDb> getTravelAgentList(){
				
		List<TravelAgentDetailsDb> travelAgentList = (List<TravelAgentDetailsDb>) this.factory.getCurrentSession()
				.createQuery(
						"Select travel_agent_details FROM com.triplived.entity.TravelAgentDetailsDb travel_agent_details where STATUS = 'A'").list();
		return travelAgentList;
	}
	

}

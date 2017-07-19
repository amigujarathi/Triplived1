package com.triplived.dao.trip;

import java.util.List;

import com.triplived.entity.TravelAgentDetailsDb;
import com.triplived.entity.TravelPartnerTripMappingDb;

public interface TravelAgentDao {

	void updateTravelAgentDetails(TravelAgentDetailsDb travel);

	TravelAgentDetailsDb getTravelAgentDetail(Long id);

	List<TravelAgentDetailsDb> getTravelAgentList();

	TravelAgentDetailsDb getTravelAgentDetailByCustomId(String id);

	List<TravelPartnerTripMappingDb> getTravelPartnerTrips(Long id);

}

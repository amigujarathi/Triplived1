package com.connectme.domain.triplived.event.command.metaupdate;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.command.AbstractCommand;
import com.triplived.dao.trip.TripMetaDAO;
import com.triplived.dao.user.UserMetaDAO;
import com.triplived.entity.TripMetaData;

/**
 *
 * Update whatever is comming to database
 * 
 * @author santosh
 *
 */
public class TripMetaUpdateDBCommand extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(TripMetaUpdateDBCommand.class );
	
	@Autowired
	private UserMetaDAO userMetaDao;
	
	@Autowired
	private TripMetaDAO tripMetaDAO;
	
	
	public void setUserMetaDao(UserMetaDAO userMetaDao) {
		this.userMetaDao = userMetaDao;
	}
	
	public void setTripMetaDAO(TripMetaDAO tripMetaDAO) {
		this.tripMetaDAO = tripMetaDAO;
	}
	
	@Override
	public void execute(TlEvent event) {
		init(this, event);

		TripMetaData headerType  = (TripMetaData) event.getHeader().get(TlEvent.TYPE);
		Long tripId = Long.parseLong(event.getActionDetails());
		Long userId = event.getUser();
		
		if(headerType == TripMetaData.TRIP_SUBMITTED){
			
			@SuppressWarnings("unchecked")
			Map<TripMetaData, Integer> tripStats = (Map<TripMetaData, Integer>) event.getBody();
			
			//Update Trip Meta for a trip
			if(MapUtils.isNotEmpty(tripStats)){
				
				for( Map.Entry<TripMetaData, Integer> entrySet  :  tripStats.entrySet()){
					
					int count = tripMetaDAO.getCountFor(tripId, entrySet.getKey());
					//get old count if something has changed.
					
					
					//handled below
					if(entrySet.getKey() != TripMetaData.TRIP_SUBMITTED){
						 
						if(count == 0 || count != entrySet.getValue()){
							tripMetaDAO.updateMetaDataCount(tripId, entrySet.getKey(), entrySet.getValue());
							userMetaDao.updateMetaDataCount(userId, entrySet.getKey(), entrySet.getValue() - count);
							logger.warn("Update the trip meta data in database for trip id {} from user {} for key {} and count is   {} ", event.getActionDetails(), event.getUser(), entrySet.getKey(), entrySet.getValue());
						}
					}
				}
				event.addToHeader(TlEvent.MOVE_FORWARD, true);
			}
			
			if(tripStats.containsKey(TripMetaData.TRIP_SUBMITTED)){
				//update user Trip count
				if(tripStats.get(TripMetaData.TRIP_SUBMITTED) > 0 ){
					userMetaDao.updateMetaDataCount(userId, TripMetaData.TRIP_SUBMITTED);
				}
			}
			
		}else{
			logger.warn("Update the trip meta data in database for trip id {} from user {} for action {} ", event.getActionDetails(), event.getUser(), headerType);
			//we will update the database from here. 
			
			if(event.isUpdateAll()){
				
				tripMetaDAO.updateMetaDataCount(tripId, headerType, Integer.parseInt(event.getSecondaryActionDetails())); 
				event.addToHeader(TlEvent.MOVE_FORWARD, true);
			}else{
				tripMetaDAO.updateMetaDataCount(tripId, headerType); 
				event.addToHeader(TlEvent.MOVE_FORWARD, true);
			}
		}
		
		
	}
	
	 
}

package com.triplived.dao.trip;

import java.io.Serializable;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.TripMetaDb;
import com.triplived.entity.TripMetaData;

/**
 * Responsibility: All operation related to Trip META DATA
 * 
 * All META DATA is listed in class {@link TripMetaData}
 * 
 * @author santosh 
 *
 */
public interface TripMetaDAO extends GenericDAO<TripMetaDb, Serializable> {

	
	 /**
	  * Which meta data count to increment
	  */

	public void updateMetaDataCount(Long tripId, TripMetaData metaData);
	
	
	 /**
	  * Which meta data count to increment
	  */

	public void updateMetaDataCount(Long tripId, TripMetaData metaData, int count);

	
	/**
	 * returns the count of ...
	 * 
	 * @param tripId
	 * @param metaData
	 * @return
	 */
	public int getCountFor(Long tripId, TripMetaData metaData);
}
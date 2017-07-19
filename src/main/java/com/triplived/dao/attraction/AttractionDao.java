package com.triplived.dao.attraction;

import java.io.Serializable;
import java.util.List;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.AttractionDb;


public interface AttractionDao extends GenericDAO<AttractionDb, Serializable> {

	void updateAttraction(AttractionDb attraction);

	List<AttractionDb> getAttractions(String cityCode);

	int updateInactivateAttraction(String attractionId, String userName);
	
	String getAttractionNamebyId(String attractionId);

}

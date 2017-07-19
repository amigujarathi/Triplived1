package com.triplived.dao.attraction;

import java.io.Serializable;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.AttractionExternalDb;


public interface AttractionExternalDao extends GenericDAO<AttractionExternalDb, Serializable> {

	void updateAttractionExternal(AttractionExternalDb attraction);
	
	AttractionExternalDb getAttractionExternal(String code);
	
	AttractionExternalDb getAttractionExternalByAttractionId(Long attractionId);

}

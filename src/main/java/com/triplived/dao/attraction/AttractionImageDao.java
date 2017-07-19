package com.triplived.dao.attraction;

import java.io.Serializable;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.AttractionImageDb;


/**
 * 
 * @author santoshjo
 *
 */
public interface AttractionImageDao extends GenericDAO<AttractionImageDb, Serializable> {

	
	/**
	 * Updates the image data in database
	 * @param attractionImage
	 */
	void addAttractionImage(AttractionImageDb attractionImage);

	int updateImageStatus(String name, String attractionId);

}

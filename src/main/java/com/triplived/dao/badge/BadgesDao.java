package com.triplived.dao.badge;

import java.io.Serializable;
import java.util.List;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.BadgeDb;

/**
 * 
 * @author santoshjo
 *
 */
public interface BadgesDao extends GenericDAO<BadgeDb, Serializable> {

	/**
	 * 
	 * @param personId
	 * @return
	 */
	public List<BadgeDb> getPersonBadges(Long personId);
	
	/**
	 * Allocate a badge to a person
	 */
	
	public void allocateBadge(BadgeDb badge);
 

}

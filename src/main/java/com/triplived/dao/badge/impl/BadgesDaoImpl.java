package com.triplived.dao.badge.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.badge.BadgesDao;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.entity.BadgeDb;

/**
 * 
 * @author santoshjo
 *
 */
@Repository
@Transactional("txManager")
public class BadgesDaoImpl extends GenericHibernateDAO<BadgeDb, Serializable> implements BadgesDao {

	@Transactional
	@Override
	public List<BadgeDb> getPersonBadges(Long personId) {
		@SuppressWarnings("unused")
		List<BadgeDb> badges = (List<BadgeDb>) getSession().createQuery("SELECT badge FROM  com.triplived.entity.BadgeDb badge "
				+ "where personId = :personId").setLong("personId", personId).list();
		return badges;
	}

	@Override
	public void allocateBadge(BadgeDb badge) {
		saveOrUpdate(badge) ;
	}

	 
	

}

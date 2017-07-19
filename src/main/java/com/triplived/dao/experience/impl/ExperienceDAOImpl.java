package com.triplived.dao.experience.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.NotificationBarDTO;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.experience.ExperienceDAO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.DeviceDb;
import com.triplived.entity.ExploreTagsDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripCityDb;
import com.triplived.entity.TripCommentsDb;
import com.triplived.entity.TripCommentsLikesDb;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripEntityMediaDb;
import com.triplived.entity.TripExperiencesDb;
import com.triplived.entity.TripFaqDb;
import com.triplived.entity.TripHistoryDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.TripKitListDb;
import com.triplived.entity.TripLikesDb;
import com.triplived.entity.TripShareDb;
import com.triplived.entity.TripStatusDb;
import com.triplived.entity.TripTrendingDb;
import com.triplived.entity.TripTrendingExceptionDb;
import com.triplived.entity.TripUserDb;
import com.triplived.entity.WebExperiencesDb;
import com.triplived.entity.WebExperiencesMediaDb;

@Repository
public class ExperienceDAOImpl extends GenericHibernateDAO<WebExperiencesDb, Serializable> implements ExperienceDAO {
	
	
	@Override
	@Transactional("txManager")
	public void updateWebExperience(WebExperiencesDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	@Transactional("txManager")
	public void updateWebExperienceMedia(WebExperiencesMediaDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	public List<WebExperiencesDb> getWebExperiencesByTripId(Long id){
		List<WebExperiencesDb>  list = (List<WebExperiencesDb>)this.getCurrentSession().createQuery(
				"Select web_experiences FROM com.triplived.entity.WebExperiencesDb web_experiences where TRIP_ID =:id").setLong("id",id).list();
	    return list;
	}
	
	@Override
	public WebExperiencesDb getWebExperienceById(String id){
		WebExperiencesDb  obj = (WebExperiencesDb)this.getCurrentSession().createQuery(
				"Select web_experiences FROM com.triplived.entity.WebExperiencesDb web_experiences where EXPERIENCE_ID =:id").setString("id",id).uniqueResult();
	    return obj;
	}
	
	@Override
	public WebExperiencesMediaDb getWebExperiencesMediaById(String id){
		WebExperiencesMediaDb  obj = (WebExperiencesMediaDb)this.getCurrentSession().createQuery(
				"Select web_experiences_media FROM com.triplived.entity.WebExperiencesMediaDb web_experiences_media where MEDIA_ID =:id").setString("id",id).uniqueResult();
	    return obj;
	}
}

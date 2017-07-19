package com.triplived.service.badge.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.badge.Badge;
import com.triplived.dao.badge.BadgesDao;
import com.triplived.entity.BadgeDb;
import com.triplived.service.badge.BadgeService;

/**
 * 
 * @author santosh
 *
 */
@Service
public class BadgeServiceImpl implements BadgeService {

	@Autowired
	private BadgesDao badgeDao;

	@Autowired 
	private Environment environment;
	
	@Override
	public List<Badge> getUserBadges(Long personId) {
		List<BadgeDb> badgesDb = badgeDao.getPersonBadges(personId);
		
		if(CollectionUtils.isNotEmpty(badgesDb)) {
			
			List<Badge> badgesList = new ArrayList<Badge>();
			
			for (BadgeDb badgeDb : badgesDb) {
				Badge badge = new Badge();
				badge.setName(badgeDb.getName());
				String desc = environment.getProperty(badge.getName());
				badge.setDescription(desc.split("#")[0]);
				if("1".equalsIgnoreCase(desc.split("#")[1])){
					badge.setAwardedType(Badge.AwardedType.SINGLE);
				}else{
					badge.setAwardedType(Badge.AwardedType.MULTIPLE);
				}
				badge.setTotalAwardedOther("NOT_IMPLEMENTED");
				badge.setTotalAwardedUser("NOT_IMPLEMENTED");
				badge.setBadgeType(badgeDb.getBadgeType());
				badgesList.add(badge);
			}
			
			return badgesList;
		}
		
		return null;
	}

	@Override
	public void allocateBadge(Badge badge, Long personId) {
		
		BadgeDb badgeDb = new BadgeDb();
		badgeDb.setBadgeType(badge.getBadgeType());
		badgeDb.setName(badge.getName());
		badgeDb.setCreatedDate(new Date());
		badgeDb.setPersonId(personId);
		badgeDb.setMetaId(badge.getMetaId());
		
		badgeDao.allocateBadge(badgeDb);
	}
	
	@Override
	public void allocateBadge(Set<Badge> badges, Long personId) {
		
		 for (Badge badge : badges) {
			 allocateBadge(badge, personId);
		}
	}
	 
}

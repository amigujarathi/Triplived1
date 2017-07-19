package com.connectme.domain.triplived.event.command.user;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.connectme.domain.triplived.badge.Badge;
import com.connectme.domain.triplived.badge.BadgeType.UserBadgeType;
import com.connectme.domain.triplived.badge.TripLivedBadge;
import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.command.AbstractCommand;
import com.triplived.dao.user.UserMetaDAO;
import com.triplived.entity.TripMetaData;
import com.triplived.service.badge.BadgeService;

/**
 * Badges to compute
 * 
 * NewBie
 * 
 * @author santosh
 *
 */
public class ComputeUserBadge extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(ComputeUserBadge.class );
	
	@Autowired 
	private UserMetaDAO userMetaDAO;
	
	@Autowired 
	private BadgeService badgeService;

	@Override
	public void execute(TlEvent event) {
		init(this, event);
		
		Long userId = event.getUser();
		
		//Get Data from database and compute whether Badge needs to be allocated.
		int count = userMetaDAO.getCountFor(event.getUser(), TripMetaData.PROFILE);
		
		if(count == 1){
			Set<Badge> badges = new HashSet<Badge>();
			logger.warn("Awarding badge to user {} badge is {} ", userId, TripLivedBadge.AUTOBIOGRAPHER);
			//only once for any trip
			Badge badge = new Badge();
			badge.setName(TripLivedBadge.AUTOBIOGRAPHER.getBadgeName());
			badge.setBadgeType(UserBadgeType.BRONZE);
			badgeService.allocateBadge(badge, userId);
			badges.add(badge);
			
			event.setBody(badges);
			event.setUser(userId);
			//send notification
			event.addToHeader(TlEvent.MOVE_FORWARD, true);
		}
	}
	
	@Override
	public boolean executeCurrent(TlEvent event) {
		// TODO Auto-generated method stub
		if(event.getHeader().containsKey(TlEvent.MOVE_FORWARD)){
			boolean move = (Boolean) event.getHeader(TlEvent.MOVE_FORWARD);
			if(move){
				return true;
			}
		}
		
		return false;
	}
	 
}

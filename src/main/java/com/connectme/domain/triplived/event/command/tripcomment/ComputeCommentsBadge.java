package com.connectme.domain.triplived.event.command.tripcomment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.connectme.domain.triplived.badge.Badge;
import com.connectme.domain.triplived.badge.BadgeType.UserBadgeType;
import com.connectme.domain.triplived.badge.TripLivedBadge;
import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.command.AbstractCommand;
import com.triplived.dao.trip.TripMetaDAO;
import com.triplived.entity.TripMetaData;
import com.triplived.service.badge.BadgeService;
import com.triplived.service.trip.TripService;

/**
 * Badges to compute
 * 
 * Discussed Trip
 * Talktive Person on Trip
 * 
 * 
 * @author santosh
 *
 */
public class ComputeCommentsBadge extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(ComputeCommentsBadge.class );
	
	@Autowired
	private TripMetaDAO tripMetaDAO;
	
	@Autowired 
	private BadgeService badgeService;
	
	@Autowired
	private TripService tripService;
	
	@Autowired 
	private Environment environment;
	
	public void setTripMetaDAO(TripMetaDAO tripMetaDAO) {
		this.tripMetaDAO = tripMetaDAO;
	}
	
	@Override
	public void execute(TlEvent event) {
		init(this, event);
		
		Long tripId = Long.parseLong(event.getActionDetails());
		
		//Get Data from database and compute whether Badge needs to be allocated.
		int count = tripMetaDAO.getCountFor(tripId, TripMetaData.COMMENTS);
		
		int creterion = environment.getProperty(TripLivedBadge.DISCUSSED_TRIP.getBadgeName()+"_CRETERION", Integer.class , 0);
		
		if(count >= creterion){
			Set<Badge> badges = new HashSet<Badge>();
			Long userId = tripService.getUserIdByTripId(tripId);Set<String> userBadges = getUserbadges(userId);
			
			if(!userBadges.contains(TripLivedBadge.DISCUSSED_TRIP.getBadgeName())){
				logger.warn("Awarding badge to user {} badge is {} ", userId, TripLivedBadge.DISCUSSED_TRIP);
				//only once for any trip
				Badge badge = new Badge();
				badge.setName(TripLivedBadge.DISCUSSED_TRIP.getBadgeName());
				badge.setBadgeType(UserBadgeType.BRONZE);
				badge.setMetaId(tripId+"");
				
				event.setUser(userId);
				badgeService.allocateBadge(badge, userId);
				badges.add(badge);
				//send notification
				event.setBody(badges);
				event.addToHeader(TlEvent.MOVE_FORWARD, true);
			}
		}
	}
	
	private Set<String> getUserbadges(Long userId) {
		 List<Badge> badges = badgeService.getUserBadges(userId);
		 Set<String> userBadges = new HashSet<>();
		 
		 for (Badge badge : badges) { 
			 userBadges.add(badge.getName()) ;
		}
		 
		return userBadges;
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

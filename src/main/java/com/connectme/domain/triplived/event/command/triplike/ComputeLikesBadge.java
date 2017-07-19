package com.connectme.domain.triplived.event.command.triplike;

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
public class ComputeLikesBadge extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(ComputeLikesBadge.class );
	
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
		int count = tripMetaDAO.getCountFor(tripId, TripMetaData.LIKES);
		int creterion = environment.getProperty(TripLivedBadge.POPULAR_TRIP.getBadgeName()+"_CRETERION", Integer.class , 0);
		
		if(count >= creterion){
			
			Long userId = tripService.getUserIdByTripId(tripId);
			Set<String> userBadges = getUserbadges(userId);
			
			if(!userBadges.contains(TripLivedBadge.POPULAR_TRIP.getBadgeName())){
				Set<Badge> badges = new HashSet<Badge>();
				logger.warn("Awarding badge to user {} badge is {} ", userId, TripLivedBadge.POPULAR_TRIP);
				//only once for any trip
				Badge badge = new Badge();
				badge.setName(TripLivedBadge.POPULAR_TRIP.getBadgeName());
				badge.setBadgeType(UserBadgeType.BRONZE);
				badge.setMetaId(tripId+"");
				
				badgeService.allocateBadge(badge, userId);
				badges.add(badge);
				
				event.setBody(badges);
				event.setUser(userId);
				//send notification
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

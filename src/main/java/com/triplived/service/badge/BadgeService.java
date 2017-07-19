package com.triplived.service.badge;

import java.util.List;
import java.util.Set;

import com.connectme.domain.triplived.badge.Badge;

/**
 * 
 * @author santosh
 *
 */
public interface BadgeService {

	/**
	 * Get badges earned by user;
	 * @param personId
	 */
	public List<Badge> getUserBadges(Long personId);

	/**
	 * Award badge to a person
	 * 
	 * @param badge
	 */
	public void allocateBadge(Badge badge, Long personId);


	/**
	 * 
	 * @param badge
	 * @param personId
	 */
	public void allocateBadge(Set<Badge> badge, Long personId);
}

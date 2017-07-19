package com.connectme.domain.triplived.badge;

import com.connectme.domain.triplived.badge.BadgeType.UserBadgeType;

/**
 * Created by santosh on 19-01-2016.
 */
public class Badge  {

	public static enum AwardedType {SINGLE, MULTIPLE};
	 
	/**
	 * 	@see TriplivedBadge
	 */
    private String name;
  
    private AwardedType awardedType;
    private UserBadgeType badgeType;
    private String totalAwardedOther;
    private String totalAwardedUser;
    private String description;
    /**
     * Awarded against which trip or user or media
     */
    private String metaId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AwardedType getAwardedType() {
		return awardedType;
	}
    
    public void setAwardedType(AwardedType awardedType) {
		this.awardedType = awardedType;
	}
    
    public UserBadgeType getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(UserBadgeType badgeType) {
        this.badgeType = badgeType;
    }

    public String getTotalAwardedOther() {
		return totalAwardedOther;
	}
    
    public void setTotalAwardedOther(String totalAwardedOther) {
		this.totalAwardedOther = totalAwardedOther;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getTotalAwardedUser() {
		return totalAwardedUser;
	}
    
    public void setTotalAwardedUser(String totalAwardedUser) {
		this.totalAwardedUser = totalAwardedUser;
	}
    
    public String getMetaId() {
		return metaId;
	}
    
    public void setMetaId(String metaId) {
		this.metaId = metaId;
	}

    public Badge() {
    }
}
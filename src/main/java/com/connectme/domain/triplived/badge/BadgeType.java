package com.connectme.domain.triplived.badge;

/**
 * Created by santosh on 19-01-2016.
 */
public class BadgeType {

    public static enum UserBadgeType {GOLD, SILVER, BRONZE};

    private UserBadgeType userBadgeType;
    private String description;

    public UserBadgeType getUserBadgeType() {
        return userBadgeType;
    }

    public void setUserBadgeType(UserBadgeType userBadgeType) {
        this.userBadgeType = userBadgeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BadgeType() {
    }
}

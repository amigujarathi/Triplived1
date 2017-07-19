package com.domain.triplived.trip.dto;

/**
 * @author santosh
 */
public class LikeStatus {

    private long totalLikes;
    private long totalComments;
    private String userLikeStatus;

    public long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getUserLikeStatus() {
        return userLikeStatus;
    }

    public void setUserLikeStatus(String userLikeStatus) {
        this.userLikeStatus = userLikeStatus;
    }
    
    public long getTotalComments() {
		return totalComments;
	}
    
    public void setTotalComments(long totalComments) {
		this.totalComments = totalComments;
	}
}

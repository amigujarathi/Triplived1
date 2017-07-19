package com.domain.triplived.trip.dto;

/**
 * 
 * @author santosh
 *
 */
public class UpVoteDownVoteStatus {

	private long totalUpvotes;
	private long totalDownvotes;
	private String userVerdict;

	public long getTotalUpvotes() {
		return totalUpvotes;
	}

	public void setTotalUpvotes(long totalUpvotes) {
		this.totalUpvotes = totalUpvotes;
	}

	public long getTotalDownvotes() {
		return totalDownvotes;
	}

	public void setTotalDownvotes(long totalDownvotes) {
		this.totalDownvotes = totalDownvotes;
	}

	public String getUserVerdict() {
		return userVerdict;
	}

	public void setUserVerdict(String userVerdict) {
		this.userVerdict = userVerdict;
	}

}

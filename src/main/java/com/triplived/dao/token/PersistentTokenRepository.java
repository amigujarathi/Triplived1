package com.triplived.dao.token;

import java.util.Date;

import com.triplived.controller.token.TriplivedPersistentToken;

/**
 * Based on Spring PersistentTokenBasedRememberMeServices
 * 
 * @author santosh
 *
 */
public interface PersistentTokenRepository {

	void createNewToken(TriplivedPersistentToken token);

	void updateToken(String series, String tokenValue, Date lastUsed);

	TriplivedPersistentToken getTokenForSeries(String seriesId);

	void removeUserTokens(String username);
	
}

package com.triplived.dao.token.impl;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.controller.token.TriplivedPersistentToken;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.token.PersistentTokenRepository;
import com.triplived.entity.TriplivedPersistentTokenDb;

/**
 * 
 * @author santosh
 *
 */
@Repository
@Transactional("txManager")
public class PersistentTokenRepositoryImpl extends GenericHibernateDAO<TriplivedPersistentTokenDb, Serializable> implements PersistentTokenRepository {

	private static final Logger logger = LoggerFactory.getLogger(PersistentTokenRepositoryImpl.class );
	
	@Override
	public void createNewToken(TriplivedPersistentToken token) {
		
		TriplivedPersistentTokenDb persistentTokenDb = new TriplivedPersistentTokenDb();
		persistentTokenDb.setDate(new Date(token.getDate()));
		persistentTokenDb.setSeries(token.getSeries());
		persistentTokenDb.setTokenValue(token.getTokenValue());
		persistentTokenDb.setUsername(token.getUsername());
		persistentTokenDb.setStatus("A");
		
		save(persistentTokenDb) ;
		logger.warn("Creating token {}", token.getUsername());
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		
		//TriplivedPersistentTokenDb token = (TriplivedPersistentTokenDb) getSession().get(TriplivedPersistentTokenDb.class, series);
		TriplivedPersistentTokenDb token = (TriplivedPersistentTokenDb) getSession() .createQuery(
				"SELECT token FROM  com.triplived.entity.TriplivedPersistentTokenDb token where series = :series and STATUS = 'A'")
				.setString("series", series).uniqueResult();
				
		if(token != null){
			token.setTokenValue(tokenValue);
			token.setDate(lastUsed); 
			
			update(token);
			
			logger.warn("updating token for {}", token.getUsername());
		}
	}

	@Override
	public TriplivedPersistentToken getTokenForSeries(String series) {
		//TriplivedPersistentTokenDb token = (TriplivedPersistentTokenDb) getSession().get(TriplivedPersistentTokenDb.class, series);
		TriplivedPersistentTokenDb token = (TriplivedPersistentTokenDb) getSession() .createQuery(
				"SELECT token FROM  com.triplived.entity.TriplivedPersistentTokenDb token where series = :series and status = 'A'")
				.setString("series", series).uniqueResult();
		
		if(token != null) {
			TriplivedPersistentToken t = new TriplivedPersistentToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
			return t;
		}
		return (new TriplivedPersistentToken() );
	}

	@Override
	public void removeUserTokens(String username) {
		
		//WE should do a periodic delete as this might increase entries;
		//this will make user logout from all places 
		int removedTokens =   getSession() .createSQLQuery(
						"update  persistent_logins set status= 'I' where username = :username ")
				.setString("username", username).executeUpdate();
		
		logger.warn("removed {} tokens for {}", removedTokens, username);
	}

}

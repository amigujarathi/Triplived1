package com.triplived.controller.token;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.triplived.dao.token.PersistentTokenRepository;

/**
 * 
 * @author santosh
 * 
 * 
 * Updates user Token to database
 * 
 * To move this logic to redis
 * 
 */
@Controller
@RequestMapping(value = "/token")
public class UserTokenController {

	private static final Logger logger = LoggerFactory.getLogger(UserTokenController.class );
	 
	@Autowired
	PersistentTokenRepository repository;
	
	Map<String, TriplivedPersistentToken> tokens = new LinkedHashMap<>();
	
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public @ResponseBody String createNewToken(@RequestBody TriplivedPersistentToken token) {
		logger.warn("Creating new token for user {} ", token.getUsername());
		try {
			repository.createNewToken(token);
		} catch (Exception e) {
			logger.error("Failed to create persistent token ", e);
		}
		return "{}";
		
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update/")
	public @ResponseBody String updateToken(@RequestBody TriplivedPersistentToken token) {
		
		logger.warn("Updating token for series {} ", token.getSeries());
		
		try {
			repository.updateToken(token.getSeries(), token.getTokenValue(), new Date(token.getDate()));
		} catch (Exception e) {
			logger.error("Failed to update persistent token ", e);
		}
		
		return "{}";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/")
	public @ResponseBody TriplivedPersistentToken getTokenForSeries(@RequestParam("series") String series) {
		logger.warn("Get token for series {} ", series);
		try {
			return repository.getTokenForSeries(series);
		} catch (Exception e) {
			logger.error("Failed to get persistent token ", e);
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/remove/")
	public @ResponseBody String removeUserTokens(@RequestParam("username") String username) {
		logger.warn("removing token for user {} ", username);
		try {
			repository.removeUserTokens(username); 
		} catch (Exception e) {
			logger.error("Failed to remove persistent token ", e);
		}
		return "{}";
		
	}
}

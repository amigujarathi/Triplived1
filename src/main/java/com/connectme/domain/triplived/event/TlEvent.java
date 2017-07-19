package com.connectme.domain.triplived.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * ----------------------
 * ABC LIKED TRIP
 * ABC LIKES TRIP MEDIA
 * ABC FOLLOWED XYZ
 * ABC COMMENTD ON TRIP MEDIA
 * ----------------------------
 * 
 * subject verb object1 object2 
 * 
 * @author santosh
 * 
 */
public class TlEvent {

	public static String TYPE = "type";
	public static String MOVE_FORWARD = "move_forward";
	
	/**
	 * WHO  :  subject
	 * 
	 * who performed the action
	 * 
	 */
	private Long user; 
	
	/**
	 * VERB : Action performed
	 * 
	 * what action was performed
	 * 
	 */
	private String verb; 
	
	/**
	 * Object : action performed on this object
	 */
	private String actionDetails;
	
	/**
	 * 
	 */
	private boolean updateAll;
	
	/**
	 * Object : action performed on this object
	 */
	private String secondaryActionDetails;
	
	/**
	 * Body
	 */
	private Object body;

	public boolean isUpdateAll() {
		return updateAll;
	}
	
	public void setUpdateAll(boolean updateAll) {
		this.updateAll = updateAll;
	}
	
	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getActionDetails() {
		return actionDetails;
	}

	public void setActionDetails(String actionDetails) {
		this.actionDetails = actionDetails;
	}

	public String getSecondaryActionDetails() {
		return secondaryActionDetails;
	}

	public void setSecondaryActionDetails(String secondaryActionDetails) {
		this.secondaryActionDetails = secondaryActionDetails;
	}

	@SuppressWarnings("rawtypes")
	private Map header = new HashMap();

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@SuppressWarnings("rawtypes")
	public Map getHeader() {
		return header;
	}

	@SuppressWarnings("unchecked")
	public Object addToHeader(String key, Object value) {
		return header.put(key, value);
	}

	public Object getHeader(String key) {
		return header.get(key);
	}

	@Override
	public String toString() {
		return "TlEvent [body=" + body + ", header=" + header + "]";
	}
	
	

}

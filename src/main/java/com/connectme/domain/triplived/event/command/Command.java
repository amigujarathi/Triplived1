package com.connectme.domain.triplived.event.command;

import com.connectme.domain.triplived.event.TlEvent;

/**
 * 
 * @author santosh
 *
 */
public interface Command {

	/**
	 * Each execution if it want to put any item should/can add
	 * a header which them can be consumed by next commands. 
	 * 
	 * @param event
	 */
	public void execute(TlEvent event);
	
	/**
	 * Whether to execute current command
	 * @param event
	 * @return
	 */
	public boolean executeCurrent(TlEvent event);

}

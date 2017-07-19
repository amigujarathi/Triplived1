package com.connectme.domain.triplived.event.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectme.domain.triplived.event.TlEvent;

/**
 * 
 * @author santosh
 *
 */
public abstract class AbstractCommand implements Command {

	private static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class );
	
	 
	protected void init(Command command, TlEvent event){
		logger.warn(" Current Command is {} ", command.getClass().getName());
	}
	
	
	public boolean executeCurrent(TlEvent event) {
		return true;
	}
}

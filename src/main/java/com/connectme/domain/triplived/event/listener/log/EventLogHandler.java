package com.connectme.domain.triplived.event.listener.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.listener.EventListener;

/**
 * 
 *  Log this event to
 * 		-> ELK
 * 		-> Log files
 * 		-> Database
 * 
 * @author santosh
 *
 */
public class EventLogHandler implements EventListener {

	private static final Logger logger = LoggerFactory.getLogger(EventLogHandler.class );
	
	@Override
	public void handle(TlEvent event) {
		 
		logger.warn("LOG : {}", event.toString());
		//Log command to ELK / Database / File System
	}

}

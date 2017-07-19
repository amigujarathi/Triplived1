package com.connectme.domain.triplived.event.listener;

import com.connectme.domain.triplived.event.TlEvent;

/**
 * 
 * @author santosh
 *
 * @param 
 */
public interface EventListener {

	/**
	 * 
	 * @param event
	 */
	public void handle(TlEvent event);
}

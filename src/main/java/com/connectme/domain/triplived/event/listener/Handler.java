package com.connectme.domain.triplived.event.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;

import com.connectme.domain.triplived.event.TlEvent;

/**
 * Based on Observer design pattern
 * 
 * @author santosh
 *
 */
public class Handler {

	/**
	 * Add all handlers hers
	 */
	private List<EventListener> handlers = new ArrayList<>();
	
	public List<EventListener> getHandlers() {
		return handlers;
	}
	
	public void setHandlers(List<EventListener> handlers) {
		this.handlers = handlers;
	}
	
	public void addToHandler(EventListener handler){
		this.handlers.add(handler);
	}
	
	/**
	 * The event to broadcast/multicast
	 */
	private TlEvent event;

	@Async
	public void setState(TlEvent event) {
		this.event = event;
		notifyAllObservers();
	}

	/**
	 * Delegating to handlers for handling
	 */
	@Async
	public void notifyAllObservers() {
		for (EventListener handler : handlers) {
			handler.handle(event);
		}
	}
}

package com.connectme.domain.triplived.event.listener.trip;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.command.Command;
import com.connectme.domain.triplived.event.listener.EventListener;

/**
 * 
 * @author santosh
 *
 */
public class EventHandler implements EventListener {

	private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
	
	/**
	 * 
	 */
	private List<Command> commands = new ArrayList<>();

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}
	
	public void addCommand(Command command){
		commands.add(command);
	}

	@Override
	public void handle(TlEvent event) {

		logger.warn("Handle");
		
		for (Command command : getCommands()) {
			if(command.executeCurrent(event)){
				command.execute(event);
			}
		}
	}
}

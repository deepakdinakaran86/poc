/**
 * 
 */
package com.pcs.saffron.manager.writeback.notifier;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.writeback.bean.WriteBackCommand;
import com.pcs.saffron.manager.writeback.listener.CommandListener;

/**
 * @author PCSEG171
 *
 */
public class CommandNotifier {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandNotifier.class);
			
	private static HashMap<String, CommandListener> commandListeners = new HashMap<String, CommandListener>();
	private static CommandNotifier commandNotifier = null;

	/**
	 * @return
	 */
	public static CommandNotifier getInstance(){
		if(commandNotifier == null)
			commandNotifier = new CommandNotifier();
		return commandNotifier;
	}


	/**
	 * @param command listener
	 * @param gateway
	 */
	public void addCommandListener(CommandListener commandListener,Gateway gateway){
		if(commandListeners.get(gateway.getId()) != null){
			LOGGER.info("Listener has been added for the gateway");
		}else{
			commandListeners.put(gateway.getId(), commandListener);
			LOGGER.info("New Listener has been added for the gateway id {}",gateway.getId());
		}
	}

	/**
	 * @param Gateway
	 */
	public void removeCommandListener(Gateway gateway){
		if(commandListeners.get(gateway.getId()) != null){
			LOGGER.info("Deregistering command listener for Gateway {}",gateway.getId());
			commandListeners.remove(gateway.getId());
		}else{
			LOGGER.info("No command listerner found for deregistering on Gateway {}",gateway.getId());
		}
	}

	/**
	 * @param Command
	 */
	public void notifyCommand(WriteBackCommand command){
		fireCommandEvent(command);
	}


	
	/**
	 * @param Command
	 */
	protected void fireCommandEvent(WriteBackCommand command){
		LOGGER.info("Sending commands to {}",command.getVersion().getId());
		commandListeners.get(command.getVersion().getId()).handleCommand(command);
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return getInstance();
	}
}

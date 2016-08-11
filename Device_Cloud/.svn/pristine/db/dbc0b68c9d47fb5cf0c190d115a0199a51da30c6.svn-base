package com.pcs.deviceframework.commandprocessor.processor.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.pcs.deviceframework.commandprocessor.manager.CommandManager;
import com.pcs.deviceframework.commandprocessor.manager.delegate.CommandManagerFactory;
import com.pcs.deviceframework.commandprocessor.processor.CommandProcessor;

public class CommandProcessorUtility {

	private static CommandProcessorUtility _utility = null;
	
	private CommandProcessorUtility() {
	
	}
	
	public static CommandProcessorUtility getInstance(){
		if(_utility == null){
			_utility = new CommandProcessorUtility();
		}
		return _utility;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("This is a singleton!!!");
	}
	
	public static CommandProcessor getCommandProcessor(String mode) throws RemoteException, MalformedURLException, NotBoundException{
		return getCommandManager().getCommandProcessor(mode);
	}
	
	private static CommandManager getCommandManager() throws MalformedURLException, RemoteException, NotBoundException{
		return (CommandManager) Naming.lookup(CommandManagerFactory.COMMAND_MANAGER_FACTORY);
	}
	

}

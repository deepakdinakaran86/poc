package com.pcs.deviceframework.commandprocessor.processor.delegate;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.pcs.deviceframework.commandprocessor.beans.Command;
import com.pcs.deviceframework.commandprocessor.processor.CommandProcessor;
import com.pcs.deviceframework.commandprocessor.publisher.CommandPublisher;

public class UDPCommandProcessor implements CommandProcessor,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9113225158898884690L;



	public UDPCommandProcessor() {
		// TODO Auto-generated constructor stub
	}

	

	public void checkExecutionStatus() throws RemoteException {
		System.out.println("evaluation status verified..");

	}

	public void execute(Object sourceId,byte[] payload) {
		System.err.println("Currently On TCP Execute Method");
		try {
			Command command = new Command();
			command.setSourceIdentifier(sourceId);
			command.setPayload(payload);
			CommandPublisher publisher = new CommandPublisher();
			publisher.publish(command);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void executeBulk(Object sourceId,List<byte[]> payload) {
		System.err.println("Currently On TCP bulk Execute Method");
	}
	
	public Boolean register(String name) {
		/*
		 * Will be implemented after defining java.policy

			if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
		 *
		 *
		 */
		try {
			CommandProcessor engine = new UDPCommandProcessor();
			CommandProcessor stub = (CommandProcessor) UnicastRemoteObject.exportObject(engine, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind(name, stub);
			System.out.println("UDP command processor bound");

			return true;
		} catch (Exception e) {
			System.err.println("UDP command processor exception:");
			e.printStackTrace();
		}
		return false;
	}

}

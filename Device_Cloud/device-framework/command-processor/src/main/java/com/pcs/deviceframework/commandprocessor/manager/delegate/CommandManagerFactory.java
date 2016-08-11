package com.pcs.deviceframework.commandprocessor.manager.delegate;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.pcs.deviceframework.commandprocessor.manager.CommandManager;
import com.pcs.deviceframework.commandprocessor.processor.CommandProcessor;
import com.pcs.deviceframework.commandprocessor.processor.delegate.TCPCommandProcessor;
import com.pcs.deviceframework.commandprocessor.processor.delegate.UDPCommandProcessor;
import com.pcs.deviceframework.commandprocessor.processor.mode.ProcessorMode;

public class CommandManagerFactory implements CommandManager {

	private static CommandManagerFactory _factory = null;
	private static boolean registered = false;
	public static final String COMMAND_MANAGER_FACTORY = "COMMAND_MANAGER_FACTORY";

	public CommandProcessor getCommandProcessor(String mode)
			throws RemoteException {
		try {

			switch (ProcessorMode.valueOf(mode)) {

			case TCP:
				return (CommandProcessor) Naming.lookup(ProcessorMode.TCP.name());
			case UDP:
				return (CommandProcessor) Naming.lookup(ProcessorMode.UDP.name());

			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private CommandManagerFactory() {

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException(
				"Connection Manager is a singleton and cannot be cloned!!!");
	}

	public static CommandManagerFactory getInstance() {
		if (_factory == null) {
			_factory = new CommandManagerFactory();
		}
		//initialize();
		return _factory;
	}

	public static final void start(){
		initialize();
	}
	
	private static final void initialize() {
		if (!registered) {
			ProcessorMode[] modes = ProcessorMode.values();
			for (ProcessorMode processorMode : modes) {
				switch (processorMode) {

				case TCP:
					TCPCommandProcessor tcpCommandProcessor = new TCPCommandProcessor();
					if (tcpCommandProcessor.register(processorMode.name())) {
						System.out.println("TCP Command processor registerd");
					}
					tcpCommandProcessor = null;
					break;
				case UDP:
					UDPCommandProcessor udpCommandProcessor = new UDPCommandProcessor();
					if (udpCommandProcessor.register(processorMode.name())) {
						System.out.println("UDP Command processor registerd");
					}
					tcpCommandProcessor = null;
					break;

				default:
					break;
				}
			}

			/*
			 * Will be implemented after defining java.policy
			 * 
			 * if (System.getSecurityManager() == null) {
			 * System.setSecurityManager(new SecurityManager()); }
			 */
			try {
				CommandManager engine = new CommandManagerFactory();
				CommandManager stub = (CommandManager) UnicastRemoteObject
						.exportObject(engine, 0);
				Registry registry = LocateRegistry.getRegistry("localhost",1099);
				registry.rebind(COMMAND_MANAGER_FACTORY, stub);
				engine = null;
				System.out.println("CommandManager bound");
			} catch (Exception e) {
				System.err.println("CommandManager exception:");
				e.printStackTrace();
			}
			registered = true;
		}
	}

}

package com.pcs.deviceframework.commandprocessor.processor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CommandProcessor extends Remote{

	public void checkExecutionStatus() throws RemoteException;
	public void execute(Object sourceId,byte[] command) throws RemoteException;
	public void executeBulk(Object sourceId,List<byte[]> commands) throws RemoteException;
	public Boolean register(String name) throws RemoteException;
	
}


/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
 *
 * This software is the property of Pacific Controls  Software  Services LLC  and  its
 * suppliers. The intellectual and technical concepts contained herein are proprietary 
 * to PCSS. Dissemination of this information or  reproduction  of  this  material  is
 * strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
 * Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
 * MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.pcs.deviceframework.datadist.publisher;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;

/**
 * This class is responsible for providing abstraction for data publishing
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 23 2015
 */
public interface CorePublisher extends Remote,Serializable{

	public void setUrl(String url) throws RemoteException;
	
	public void setProperties(Properties properties) throws RemoteException;

	public String getUrl() throws RemoteException;

	public Properties getProperties() throws RemoteException;

	public abstract void publish(String topicName,Serializable serializable) throws RemoteException;
	
	public abstract void publish(Serializable serializable) throws RemoteException;
	
	public abstract void publish(List<Serializable> messageList) throws RemoteException;
	
	public abstract void publishToQueue(String queueName,Serializable serializable) throws RemoteException;
	
	public abstract void publishToTopic(String topicName,Serializable serializable) throws RemoteException;
	
	public abstract void publishAvro(Serializable serializable, String topicName, String schema) throws RemoteException;
	
}

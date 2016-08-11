
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
package com.pcs.deviceframework.datadist.registry;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.datadist.core.ApplicationConfiguration;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.core.DistributionManagerImpl;
import com.pcs.deviceframework.datadist.enums.RegistryEntry;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.deviceframework.datadist.publisher.JmsBasePublisher;
import com.pcs.deviceframework.datadist.publisher.KafkaBasePublisher;
import com.pcs.deviceframework.datadist.util.DistributionUtil;

/**
 * This class is responsible for creating an RMI registry
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 19 2015
 */
public class DistributionRegistry {
	private static final Logger logger = LoggerFactory.getLogger(DistributionRegistry.class);
	private static final String nameDisMgr = "distributor";
	private static int rmiPort = 0;
	static final DistributionManager distributionManager  =  new DistributionManagerImpl();
	static final CorePublisher JMS_PUBLISHER = new JmsBasePublisher();
	static final CorePublisher KAFKA_PUBLISHER = new KafkaBasePublisher();
	
	private static void createDistributorRegistry(){
		try {
			
			//distributionManager =  new DistributionManagerImpl();
			//exports the remote object so that it can receive invocations 
			//of its remote methods from remote clients
			
			//this was required for Linux server 
			String rmiServerHostName = ApplicationConfiguration.getDistributionConfig().getRmiServerHostName();
			System.setProperty("java.rmi.server.hostname", rmiServerHostName);
			DistributionManager stub = (DistributionManager) UnicastRemoteObject.exportObject(distributionManager,rmiPort);

			//The RMI registry is a simple remote object naming service that enables
			//clients to obtain a reference to a remote object by name
			Registry registry = LocateRegistry.createRegistry(rmiPort);

			//to register a remote object
			// Once a remote object is registered with an RMI registry on the local host,
			//clients on any host can look up the remote object by name,
			//obtain its reference, and then invoke remote methods on the object
			registry.rebind(nameDisMgr, stub);
			logger.info("Registry created");
		} catch (Exception e) {
			logger.error("Error occurred while registering distribution mgr remote object",e);
		}
	}
	
	public static void createPublisherRegistry(){
		try {
			//The RMI registry is a simple remote object naming service that enables
			//clients to obtain a reference to a remote object by name
			Registry registry = LocateRegistry.getRegistry(rmiPort);
			
			CorePublisher jmsStub = (CorePublisher) UnicastRemoteObject.exportObject(JMS_PUBLISHER,rmiPort);
			CorePublisher kakfaStub = (CorePublisher) UnicastRemoteObject.exportObject(KAFKA_PUBLISHER,rmiPort);


			//to register a remote object
			// Once a remote object is registered with an RMI registry on the local host,
			//clients on any host can look up the remote object by name,
			//obtain its reference, and then invoke remote methods on the object
			registry.rebind(RegistryEntry.KAFKA_PUBLISHER.toString(), kakfaStub);
			registry.rebind(RegistryEntry.JMS_PUBLISHER.toString(), jmsStub);
			logger.info("Publish Registry created for " + RegistryEntry.KAFKA_PUBLISHER.toString() +" & "+ RegistryEntry.JMS_PUBLISHER.toString());
		} catch (Exception e) {
			logger.error("Error occurred while registering Publish remote object for " + RegistryEntry.KAFKA_PUBLISHER.toString() +" || "+ RegistryEntry.JMS_PUBLISHER.toString(),e);
		}
	}
	
	
	public static void createRegistry(){
		try{
			  //System.setSecurityManager(new RMISecurityManager());
			rmiPort = DistributionUtil.getRmiPort();
		}catch(Exception ex){
			logger.error("Error occurred while reading the property file",ex);
		}
		createDistributorRegistry();
		createPublisherRegistry();
		logger.info("Registry created info..");
		System.out.println("Registry created");
	}

}

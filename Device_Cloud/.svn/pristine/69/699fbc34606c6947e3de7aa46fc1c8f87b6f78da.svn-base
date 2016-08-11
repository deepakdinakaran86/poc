/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.eventescaltion.core;

import static com.pcs.deviceframework.datadist.enums.ConsumerType.ASYNC;
import static com.pcs.deviceframework.datadist.enums.ConsumerType.HIGH_LEVEL;
import static com.pcs.deviceframework.datadist.enums.DestinationType.QUEUE;
import static com.pcs.deviceframework.datadist.enums.DistributorMode.JMS;
import static com.pcs.deviceframework.datadist.enums.DistributorMode.KAFKA;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.eventescaltion.listeners.JmsAsyncListener;

/**
 * This class is responsible for setting the application configuration for email
 * notification in JMS ASYNC mode
 * 
 * @author pcseg323 (Greeshma)
 * @date April 2015
 */

public class Application {

	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private static CoreConsumer coreConsumer;
	public static Properties props = new Properties();
	private static final String PROPERTY_FILE_NAME = "queue.properties";
	private static final String QUEUE_NAME = "jms.queue.name";
	private static final String DISTRIBUTION_MODE = "queue.distribution.name";

	private static void init() {

		Registry registry;
		String name = "distributor";
		DistributionManager distributionManager;
		loadQueueProperties();
		try {
			registry = LocateRegistry.getRegistry("localhost", 1099);
			distributionManager = (DistributionManager)registry.lookup(name);
			DistributorMode mode = DistributorMode.valueOf(props
			        .getProperty(DISTRIBUTION_MODE));
			switch (mode) {
				case JMS :
					// use async consumer type
					coreConsumer = distributionManager.getConsumer(JMS, ASYNC);
					break;
				case KAFKA :
					coreConsumer = distributionManager.getConsumer(KAFKA,
					        HIGH_LEVEL);
					break;

				default:
					break;
			}
			coreConsumer.setQueue(props.getProperty(QUEUE_NAME));
			coreConsumer.setDestinationType(QUEUE);
			JmsAsyncListener jmsAsyncListener = new JmsAsyncListener();
			jmsAsyncListener.setConsumer(coreConsumer);
			coreConsumer.listen();
		} catch (RemoteException e) {
			LOGGER.error("Error in getConsumer for email notification");
		} catch (NotBoundException e) {
			LOGGER.error("Error in getting Consumer remote object for email notification");
		}

	}

	private static void loadQueueProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader
		        .getResourceAsStream(PROPERTY_FILE_NAME);
		try {
			props.load(resourceStream);
		} catch (IOException e) {
			LOGGER.error("Error in reading queue.properties for email notification");
		}

	}

	public static void main(String[] args) {
		init();
	}
}

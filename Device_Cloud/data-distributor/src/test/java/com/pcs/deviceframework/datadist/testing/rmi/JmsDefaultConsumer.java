
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
package com.pcs.deviceframework.datadist.testing.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DestinationType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.testing.JmsDefaultListenerTesting;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class JmsDefaultConsumer {
	public static void main(String[] args) {
		consumerTesting();
	}

	private static void consumerTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("10.234.31.234",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.JMS, ConsumerType.DEFAULT);
			consumer.setDestinationType(DestinationType.QUEUE);
			consumer.setQueue("NEW_QUEUE");
			JmsDefaultListenerTesting testing  = new JmsDefaultListenerTesting();
			testing.setConsumer(consumer);
			consumer.listen();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

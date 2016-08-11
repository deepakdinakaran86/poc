
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

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kafka.producer.KeyedMessage;

import com.google.gson.Gson;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.deviceframework.datadist.testing.EventMessage;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class KafkaPublish {
	public static void main(String[] args) {
		kafkaPublishTesting();
	}

	private static void kafkaPublishTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("10.234.31.201",1099);
			DistributionManager distributionManager = (DistributionManager) registry.lookup(name);
			CorePublisher corePublisher = distributionManager.getPublisher(DistributorMode.KAFKA);
			List<String> objects = new ArrayList<String>();
			List<Serializable> keyedMessages = new ArrayList<Serializable>();
			for(int i=0;i<=2;i++){
				String historyMessageObj = getLiveMessage();
				objects.add(historyMessageObj);
			}
			for(int i=0;i<=2;i++){
				KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>("analyzed-message",objects.get(i));
				keyedMessages.add(keyedMessage);
			}
			corePublisher.publish(keyedMessages);

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static String getLiveMessage(){
		Gson gson = new Gson();
		EventMessage eventMessage = new EventMessage();
		eventMessage.setEventId(String.valueOf(Calendar.HOUR_OF_DAY));
		eventMessage.setEventSource("Time");
		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.DATE, -3);
		eventMessage.setEventValue(calendar.getTime().toString());

		String jsonString = gson.toJson(eventMessage);
		return jsonString;
	}

}

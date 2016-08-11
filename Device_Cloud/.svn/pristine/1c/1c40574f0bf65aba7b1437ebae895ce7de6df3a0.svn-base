
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
package com.pcs.analytics.util.test;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.transformer.bean.DeviceData;
import com.pcs.data.transformer.distributors.Publisher;
import com.pcs.data.transformer.distributors.PublisherConfig;
import com.pcs.data.transformer.storm.bolts.DeviceDataTransformBolt;

/**
 * This class is responsible for reading actual message from the consumed message
 * 
 * @author pcseg129(Seena Jyothish)
 */
public class MessageUtil implements Runnable{
	
	private KafkaStream kafkaStream;
	private int threadNo;
	
	public MessageUtil(KafkaStream stream,int threadNo){
		kafkaStream = stream;
		this.threadNo = threadNo;
	}
	
	public void run(){
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
		System.out.println(iterator.hasNext());
		//this code reads from Kafka until you stop it
		while(iterator.hasNext()){
			String msg = new String(iterator.next().message());
			System.out.println(" i " + threadNo + " : " + msg);
			
			
			ObjectMapper mapper = new ObjectMapper();
			DeviceData deviceData = new DeviceData();
			try {
				System.out.println("Message in deserialize :" + msg);
				deviceData = mapper.readValue(msg, DeviceData.class);
				System.out.println("Message Object is created from json "+ deviceData);
				
				PublisherConfig publisherConfig = PublisherConfig.getNewInstance("Config//publisherconfig.yaml");
				Publisher publisher = new Publisher(publisherConfig);
				DeviceDataTransformBolt live = new DeviceDataTransformBolt("Config//publisherconfig.yaml","analyzed-message");
				live.test(deviceData);

			} catch (Exception e) {
				System.err.println("Error in Deserializing");
			}
			
		}
	}
	
}

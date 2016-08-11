
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
import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.storm.bolts.DataAnalyzeNPublishBolt;
import com.pcs.data.analyzer.storm.bolts.LivePublishBolt;
import com.pcs.data.analyzer.util.DataAnalyzerUtil;
import com.pcs.saffron.cipher.data.message.Message;

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
			Message message = new Message();
			try {
				msg = msg.replaceAll("\"unit\":\"null\"","\"unit\":\"unitless\"");//need to be fixed, workaround
				msg = msg.replaceAll("generic_quantity", "status_string");//need to be fixed, workaround
				msg = msg.replaceAll("alarmState", "state");//need to be fixed, workaround
				msg = msg.replaceAll("alarmType", "type");//need to be fixed, workaround
				msg = msg.replaceAll("customTag", "displayName");//temporary fix
				System.out.println("Message in deserialize :" + msg);
				message = mapper.readValue(msg, Message.class);
				//message.setSourceId("412526763979");
				System.out.println("Message Object is created from json "+ message);
				
				PublisherConfig publisherConfig = PublisherConfig.getNewInstance("Config//publisherconfig.yaml");
				Publisher publisher = new Publisher(publisherConfig);
				//DataAnalyzeNPublishBolt dataAnalyzerBolt = new DataAnalyzeNPublishBolt("Config//publisherconfig.yaml", "PERSIST_QUEUE");
				//Message msg1=DataAnalyzerUtil.analyzeMessage(message, message.getSourceId());
			//	dataAnalyzerBolt.analyzeMessage(message, message.getSourceId(),"6d8f6138-2378-41b8-bbe0-569e1355f011");
				//publisher.publishToJMSQueue("PERSIST_QUEUE", msg1.toString());
				LivePublishBolt live = new LivePublishBolt("Config//publisherconfig.yaml");
				live.publishData(message.getSourceId().toString(), message);

			} catch (Exception e) {
				System.err.println("Error in Deserializing");
			}
			
		}
	}
	
}

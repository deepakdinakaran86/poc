
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
package com.pcs.data.analyzer.storm.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.storm.persistence.beans.MessageWrapper;
import com.pcs.data.analyzer.storm.persistence.beans.PersistConfig;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * This class is responsible for batch persist
 * 
 * @author pcseg129
 */
public class DataBatchWriter {
	private static Logger LOGGER = LoggerFactory
			.getLogger(DataBatchWriter.class);

	DataBatchConsumer batchConsumer = null;

	private Client httpClient;


	private static Double POINT_INSERT_COUNT = 0d;

	//DataServiceImpl dataServiceImpl ;


	String persistAPIBatchURL ;

	public void init(PersistConfig config){
		//springConfig  = new SpringConfig();
		//dataServiceImpl = springConfig.getContext().getBean(DataServiceImpl.class);

		batchConsumer = new DataBatchConsumer();
		batchConsumer.setJmsHost(config.getJmsHostUrl());
		batchConsumer.setQueueName(config.getQueueName());
		batchConsumer.setQueueSize(config.getQueueSize());

		persistAPIBatchURL = config.getPersistAPIBatchURL();
		this.httpClient = ApacheRestClient.builder()
				.host(config.getPersistAPIHost())
				.port(config.getPersistAPIPort()).scheme("http")
				.build();
		
		try {
	        consumeMessage();
        } catch (JMSException e) {
	        e.printStackTrace();
	        LOGGER.error("Error in consuming messages",e);
        }
	}

	public void consumeMessage() throws JMSException {
		while(true){
			List<Message> messages = new ArrayList<Message>();
			try {
				List<javax.jms.Message> consumeMessages = batchConsumer.receiveMessage();
				for (javax.jms.Message message : consumeMessages) {
					if (message instanceof TextMessage) {
						try {
							TextMessage textMessage = (TextMessage) message;
							Message decodedMessage = new Message();
							ObjectMapper mapper = new ObjectMapper();
							String messgeText = textMessage.getText();
							messgeText = messgeText.replaceAll("alarmState", "state");
							messgeText = messgeText.replaceAll("alarmType", "type");
							decodedMessage = mapper.readValue(messgeText, Message.class);
							messages.add(decodedMessage);
							Thread.sleep(10);
						} catch (JsonParseException e) {
							LOGGER.error("Error in json parsing from message to Message",e);
							e.printStackTrace();
						} catch (JsonMappingException e) {
							LOGGER.error("Error in json mapping from message to Message",e);
							e.printStackTrace();
						} catch (IOException e) {
							LOGGER.error("Error in message conversion to Message",e);
							e.printStackTrace();
						}
						catch (Exception e) {
							System.out.println("Error in MessageConsumer "+e);
						}
					}
				}
				persistBatch(messages);
			}catch (Exception e) {
				LOGGER.error("Check ActiveMQ status",e);
			}finally{
				messages = null;
			}
		}
	}

	private void persistBatch(List<Message> messages){
		if(CollectionUtils.isEmpty(messages))
			return;
		MessageWrapper messageWrapper = new MessageWrapper();
		messageWrapper.setMessages(messages);
		JsonNode post;
		try {
			post = httpClient.post(
					persistAPIBatchURL, null, messageWrapper.getMessages(),
					JsonNode.class);

			if (post == null){
				LOGGER.error("Error in Persist API/Payload :{}", post);
			}else if(post.get("status").asText().equalsIgnoreCase("SUCCESS")) {
				LOGGER.info("Successfully Saved with list size :{} ,time :{}",messages.size(), new Date().getTime());
				POINT_INSERT_COUNT += messages.size();
			} else if(post.get("status").asText().equalsIgnoreCase("PARTIAL")) {
				LOGGER.info("Partialy Saved with list size :{} ,time :{}",messages.size(), new Date().getTime());
				/*for(Message message : messages){
					LOGGER.error("----Partial Msg : {} ",message.toString());
				}*/
				int failedCount = post.get("failedDeviceMsgs") != null? post.get("failedDeviceMsgs").asInt():0;
				LOGGER.info("failedCount count :: {} : {}" ,failedCount,post.asText());
				POINT_INSERT_COUNT += messages.size();
			}else{
				LOGGER.info("Could Not Save data with list size :{} ,time :{}",messages.size(), new Date().getTime());
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}


		LOGGER.info("Total Points Persisted So Far := {}",POINT_INSERT_COUNT);
	}

	/*public void persistNew(List<DeviceMessage> deviceMessages) throws ClientException {

		DeviceMessage deviceMessage = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			deviceMessage = mapper.readValue(deviceMessages.toString(), DeviceMessage.class);
		} catch (JsonParseException e) {
			LOGGER.error("Error in json parsing from message to DeviceMessage",e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOGGER.error("Error in json mapping from message to DeviceMessage",e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("Error in message conversion to DeviceMessage",e);
			e.printStackTrace();
		}
		LOGGER.info(deviceMessage.toString());
		dataServiceImpl.createDeviceData(deviceMessages);
	}*/

}

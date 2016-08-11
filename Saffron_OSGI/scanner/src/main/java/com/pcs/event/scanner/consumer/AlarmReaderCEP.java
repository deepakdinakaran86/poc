
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
package com.pcs.event.scanner.consumer;

import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.event.scanner.beans.IdentifiedCEPAlarm;
import com.pcs.event.scanner.config.ScannerConfiguration;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.manager.provider.EOSDeviceManager;

/**
 * This class is responsible for batch persist
 * 
 * @author pcseg129
 */
public class AlarmReaderCEP {
	private static Logger LOGGER = LoggerFactory
			.getLogger(AlarmReaderCEP.class);

	CEPEventConsumer batchConsumer = null;

	public static Boolean keepConsuming = true;
	String persistAPIBatchURL ;
	private static final AlarmReaderCEP alarmReaderCEP = null;
	
	private AlarmReaderCEP(){
		
	}
	

	public void getEvents(){
		batchConsumer = new CEPEventConsumer();
		batchConsumer.setJmsHost(ScannerConfiguration.getProperty(ScannerConfiguration.REALTIME_DATA_URL));
		batchConsumer.setQueueName(ScannerConfiguration.getProperty(ScannerConfiguration.EVENT_VIOLATION));
		batchConsumer.setQueueSize(10);

		try {
			consumeMessage();
		} catch (JMSException e) {
			e.printStackTrace();
			LOGGER.error("Error in consuming messages",e);
		}
	}

	
	public static AlarmReaderCEP getInstance(){

		if(alarmReaderCEP == null)
			return new AlarmReaderCEP();
		return alarmReaderCEP;
	
	}
	
	public void consumeMessage() throws JMSException {
		while(keepConsuming){
			try {
				List<javax.jms.Message> consumeMessages = batchConsumer.receiveMessage();
				for (javax.jms.Message message : consumeMessages) {
					if (message instanceof TextMessage) {
						try {
							TextMessage textMessage = (TextMessage) message;
							
							ObjectMapper mapper = new ObjectMapper();
							String messgeText = textMessage.getText();
							IdentifiedCEPAlarm cepAlarm = mapper.readValue(messgeText, IdentifiedCEPAlarm.class);
							AlarmMessage alarmMessage = new AlarmMessage();
							
							alarmMessage.setSourceId(cepAlarm.getSourceId());
							alarmMessage.setTime(cepAlarm.getDateTime());
							alarmMessage.setStatus(cepAlarm.getStatus().equalsIgnoreCase("ACTIVE")?true:false);
							alarmMessage.setStatusMessage(cepAlarm.getMessage());
							Point point = Point.getPoint(PointDataTypes.BASE.getType());
							point.setDisplayName(cepAlarm.getDisplayName());
							point.setPointId(cepAlarm.getPointId());
							point.setData(cepAlarm.getData());
							point.setUnit("unitless");
							alarmMessage.addPoint(point);
							alarmMessage.setName(cepAlarm.getName());
							
							EOSDeviceManager.instance().notifyAlarm(alarmMessage);
							
						} catch (JsonMappingException e) {
							LOGGER.error("Error in json mapping from message to Message",e);
							e.printStackTrace();
						} catch (IOException e) {
							LOGGER.error("Error in message conversion to Message",e);
							e.printStackTrace();
						}
						catch (Exception e) {
							LOGGER.error("Error in message conversion to Message",e);
						e.printStackTrace();}
					}
				}
				Thread.sleep(500);
			}catch (Exception e) {
				LOGGER.error("Check ActiveMQ status",e);
			}finally{
				
			}
		}
		LOGGER.info("Consumption terminated");
	}


}

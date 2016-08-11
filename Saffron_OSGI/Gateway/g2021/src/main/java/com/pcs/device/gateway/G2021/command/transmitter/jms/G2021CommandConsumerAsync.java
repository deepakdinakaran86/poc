package com.pcs.device.gateway.G2021.command.transmitter.jms;
import java.util.Calendar;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.command.transmitter.G2021CommandExecutor;
import com.pcs.device.gateway.G2021.utils.SupportedDevices;
import com.pcs.device.gateway.G2021.utils.SupportedDevices.Devices;
import com.pcs.saffron.manager.bean.DefaultConfiguration;


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

/**
 * This class is responsible for consume data from JMS queue
 * 
 * @author pcseg129
 */
public class G2021CommandConsumerAsync implements MessageListener{

	private static Logger LOGGER = LoggerFactory
			.getLogger(G2021CommandConsumerAsync.class);

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				TextMessage textMessage = (TextMessage) message;
				
				ObjectMapper mapper = new ObjectMapper();
				LOGGER.info("Command Received Async :-> {}",textMessage.getText());
				G2021Command command = mapper.readValue(textMessage.getText(), G2021Command.class);
				
				DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getG2021DeviceManager().getConfiguration(command.getSourceId(), SupportedDevices.getGateway(Devices.EDCP));
				G2021CommandExecutor executor = new G2021CommandExecutor();
				command.setSessionId(configuration.getSessionId());
				command.setUnitId(configuration.getUnitId());
				executor.executeCommand(command, configuration);	
				LOGGER.info("sleeping for device to receive the command.{}",Calendar.getInstance().getTimeInMillis());
				Thread.sleep(1500l);//sleeping for device to receive the command.
				LOGGER.info("wakingup now to receive the command.{}",Calendar.getInstance().getTimeInMillis());
				LOGGER.info("Command sent !!");
				mapper = null;
			}catch (Exception e) {
				LOGGER.error("Error in message conversion to Message",e);
			}
		}else{
			LOGGER.info("Message is not of text type but of type {} !!", message.getClass());
		}
	}


}

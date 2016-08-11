
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
package com.pcs.deviceframework.datadist.testing;

import java.io.Serializable;
import java.util.List;

import javax.jms.Message;
import javax.jms.ObjectMessage;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class JmsDefaultListenerTesting extends CoreListenerAdapter implements Serializable{

    private static final long serialVersionUID = -5809088712820520558L;
	private CoreConsumer consumer;

	public CoreConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(CoreConsumer consumer) {
		try{
			consumer.setMessageListener(this);
			this.consumer = consumer;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void consumeData1(List<Message> messages){
		System.out.println("client consume data");
		for(Message message : messages){
			showdata(message);
		}
	}

	private void showdata(Message message){
		ObjectMessage objectMessage = null;
		PublishMessage publishMessage = null;
		try{
			objectMessage = (ObjectMessage) message;
			publishMessage = (PublishMessage) objectMessage.getObject();
			System.out.println("Asset Name : " + publishMessage.getAssetName());
			System.out.println("Temp : " + publishMessage.getTemperature());
		}catch(Exception ex){
			ex.printStackTrace();;
		}
	}
}

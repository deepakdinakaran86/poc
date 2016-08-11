
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

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;
import com.pcs.deviceframework.datadist.consumer.listener.KafkaMessageListenerAdapter;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class KafkaAdvListenerTesting extends CoreListenerAdapter{
	
	private CoreConsumer consumer;

	public CoreConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(CoreConsumer consumer) {
		consumer.setMessageListener(this);
		this.consumer = consumer;
	}
	private static final Logger logger = LoggerFactory.getLogger(KafkaAdvListenerTesting.class);
	
	public  void consumeData(byte[] bytes,long offset){
		try {
	        System.out.println("Data showing from KafkaAdvListenerTesting " + String.valueOf(offset) + ": " + new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
        }
	}
}

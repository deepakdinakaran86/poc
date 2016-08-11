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

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.KafkaStream;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class KafkaHLListenerTesting extends CoreListenerAdapter{

	private CoreConsumer consumer;

	public CoreConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(CoreConsumer consumer) {
		consumer.setMessageListener(this);
		this.consumer = consumer;
	}

	@Override
	public void consumeData(List<KafkaStream<byte[], byte[]>> streams){
		int i=0;
		for(final KafkaStream stream : streams){
			System.out.println("dd");
			ExecutorService executor;
			executor = Executors.newFixedThreadPool(10);
			executor.submit(new MessageUtil(stream,i));
			i++;
		}
	}

	public void init(){
		consumer.listen();
	}
}


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

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

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
			System.out.println(" i " + threadNo + " : " + new String(iterator.next().message()));
		}
	}
	
}


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
package com.pcs.deviceframework.datadist.consumer;

import javax.jms.MessageListener;

import com.pcs.deviceframework.datadist.consumer.delegates.JmsAsynchronousConsumer;
import com.pcs.deviceframework.datadist.consumer.delegates.JmsDefaultConsumer;
import com.pcs.deviceframework.datadist.enums.ConsumerType;

/**
 * This class is responsible for data consumption using JMS implementation
 * 
 * @author pcseg129(Seena Jyothish)
 * @date March 19 2015
 */
public class JmsBaseConsumer extends ConsumerAdapter{

	private static final long serialVersionUID = 7787047897488641199L;

	public JmsBaseConsumer(){
	}

	public void setQueue(String queue){
	}

	public String getQueue(){
		return null;
	}

	public void setType(String type){
	}

	public String getType(){
		return null;
	}

	public void setUrl(String url){
	}

	public String getURL(){
		return null;
	}

	public void setQueueSize(Integer size){
	}

	public Integer getQueueSize(){
		return null;
	}

	public void setDelay(Long delay){
	}

	public Long getDelay(){
		return null;
	}

	public CoreConsumer getConsumer(ConsumerType type){
		switch(type){
			case DEFAULT:
				return new JmsDefaultConsumer();
			case ASYNC:
				return new JmsAsynchronousConsumer();
			default:
				return null;
		}
	}

	public void setMessageListener(MessageListener listener){
	}

	public void listen(){
	}

}

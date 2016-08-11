/**
 * 
 */
package com.pcs.deviceframework.pubsub.consumers;

import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * @author PCSEG171
 *
 */
public abstract class BaseConsumer {

	public  void setQueue(String queue){
		
	}
	public  void setMode(String mode){
		
	}
	public  void setUrl(String url){
		
	}
	
	public void setQueueSize(Integer size){
		
	}
	
	public void setDelay(Long delay){
		
	}
	
	public  String getQueue(){
		return null;
	}
	public  String getURL(){
		return null;
	}
	public  String getMode(){
		return null;
	}
	
	public Integer getQueueSize(){
		return null;
	}
	
	public Long getDelay(){
		return null;
	}
	
	public final void getConsumer(){
		
	}
	
	public abstract void setMessageListener(MessageListener listener);
	
	public abstract List<Message> receiveMessage();
	
	public abstract void listen();
	
	
}

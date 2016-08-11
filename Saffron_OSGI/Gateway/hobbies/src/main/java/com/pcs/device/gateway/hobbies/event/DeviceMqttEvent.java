/**
 * 
 */
package com.pcs.device.gateway.hobbies.event;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import com.pcs.saffron.cipher.data.generic.message.GenericEvent;
import com.pcs.saffron.cipher.data.generic.message.GenericMessage;


/**
 * @author PCSEG171
 *
 */
public class DeviceMqttEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4587438475150855094L;
	/**
	 * 
	 */
	private GenericMessage mqttMessage; 
	private List<GenericMessage> mqttMessages;
	private GenericEvent genericEvent;
	private List<GenericEvent> genericEvents;
	

	
	public DeviceMqttEvent(Object source) {
		super(source);
	}


	public GenericMessage getMqttMessage() {
		return mqttMessage;
	}

	public void setMqttMessage(GenericMessage mqttMessage) {
		this.mqttMessage = mqttMessage;
	}


	public List<GenericMessage> getMqttMessages() {
		return mqttMessages;
	}


	public void setMqttMessages(List<GenericMessage> mqttMessages) {
		this.mqttMessages = mqttMessages;
	}
	
	public void addMqttMessage(GenericMessage mqttMessage){
		if(this.mqttMessages == null){
			mqttMessages = new ArrayList<GenericMessage>();
		}
		mqttMessages.add(mqttMessage);
	}


	public GenericEvent getGenericEvent() {
		return genericEvent;
	}


	public List<GenericEvent> getGenericEvents() {
		return genericEvents;
	}


	public void setGenericEvent(GenericEvent genericEvent) {
		this.genericEvent = genericEvent;
	}


	public void setGenericEvents(List<GenericEvent> genericEvents) {
		this.genericEvents = genericEvents;
	}

}

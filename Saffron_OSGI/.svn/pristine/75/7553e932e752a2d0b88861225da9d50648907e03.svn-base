/**
 * 
 */
package com.pcs.device.gateway.hobbies.event.notifier;

import java.util.ArrayList;
import java.util.List;

import com.pcs.device.gateway.hobbies.event.DeviceMqttEvent;
import com.pcs.device.gateway.hobbies.event.listener.DeviceMqttMessageListener;
import com.pcs.saffron.cipher.data.generic.message.GenericEvent;
import com.pcs.saffron.cipher.data.generic.message.GenericMessage;

/**
 * @author PCSEG171
 *
 */
public class DeviceMqttMessageNotifier {

	private List<DeviceMqttMessageListener> messageListeners = new ArrayList<DeviceMqttMessageListener>();
	private static DeviceMqttMessageNotifier messageNotifier = null;

	/**
	 * @return
	 */
	public static DeviceMqttMessageNotifier getInstance(){
		if(messageNotifier == null)
			messageNotifier = new DeviceMqttMessageNotifier();
		return messageNotifier;
	}


	/**
	 * @param deviceMessageListener
	 */
	public void addMessageListener(DeviceMqttMessageListener deviceMessageListener){
		messageListeners.add(deviceMessageListener);
	}

	/**
	 * @param deviceMessageListener
	 */
	public void removeMessageListener(DeviceMqttMessageListener deviceMessageListener){
		if(messageListeners.contains(deviceMessageListener))
			messageListeners.remove(deviceMessageListener);
	}

	/**
	 * @param messages
	 */
	public void notifyMessageReception(GenericMessage mqttMessage){
		DeviceMqttEvent deviceMessageEvent = new DeviceMqttEvent(this);
		deviceMessageEvent.setMqttMessage(mqttMessage);
		fireMessageEvent(deviceMessageEvent);
	}
	
	public void notifyMessagesReception(List<GenericMessage> mqttMessages){
		DeviceMqttEvent deviceMessageEvent = new DeviceMqttEvent(this);
		deviceMessageEvent.setMqttMessages(mqttMessages);
		fireMessageEvent(deviceMessageEvent);
	}
	

	/**
	 * @param deviceMessageEvent
	 */
	protected void fireMessageEvent(DeviceMqttEvent deviceMessageEvent){
		for (DeviceMqttMessageListener messageListener : messageListeners) {
			messageListener.messageReady(deviceMessageEvent);
		}
		
	}
	
	
	/**
	 * @param messages
	 */
	public void notifyEventReception(GenericEvent mqttEvent){
		DeviceMqttEvent deviceMessageEvent = new DeviceMqttEvent(this);
		deviceMessageEvent.setGenericEvent(mqttEvent);
		fireEvent(deviceMessageEvent);
	}
	
	public void notifyEventsReception(List<GenericEvent> mqttEvents){
		DeviceMqttEvent deviceMessageEvent = new DeviceMqttEvent(this);
		deviceMessageEvent.setGenericEvents(mqttEvents);
		fireEvent(deviceMessageEvent);
	}
	

	/**
	 * @param deviceMessageEvent
	 */
	protected void fireEvent(DeviceMqttEvent deviceMessageEvent){
		for (DeviceMqttMessageListener messageListener : messageListeners) {
			messageListener.messageReady(deviceMessageEvent);
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return getInstance();
	}
}

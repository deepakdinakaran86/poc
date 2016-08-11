/**
 * 
 */
package com.pcs.device.gateway.teltonika.event.notifier;

import java.util.ArrayList;
import java.util.List;

import com.pcs.device.gateway.teltonika.event.DeviceMessageEvent;
import com.pcs.device.gateway.teltonika.event.listener.DeviceMessageListener;
import com.pcs.deviceframework.decoder.data.message.Message;

/**
 * @author PCSEG171
 *
 */
public class MessageNotifier {

	private List<DeviceMessageListener> messageListeners = new ArrayList<DeviceMessageListener>();
	private static MessageNotifier messageNotifier = null;

	/**
	 * @return
	 */
	public static MessageNotifier getInstance(){
		if(messageNotifier == null)
			messageNotifier = new MessageNotifier();
		return messageNotifier;
	}


	/**
	 * @param deviceMessageListener
	 */
	public void addMessageListener(DeviceMessageListener deviceMessageListener){
		messageListeners.add(deviceMessageListener);
	}

	/**
	 * @param deviceMessageListener
	 */
	public void removeMessageListener(DeviceMessageListener deviceMessageListener){
		if(messageListeners.contains(deviceMessageListener))
			messageListeners.remove(deviceMessageListener);
	}

	/**
	 * @param messages
	 */
	public void notifyMessageReception(List<Message> messages){
		DeviceMessageEvent deviceMessageEvent = new DeviceMessageEvent(this);
		deviceMessageEvent.setMessages(messages);
		fireMessageEvent(deviceMessageEvent);
	}
	
	/**
	 * @param message
	 */
	public void notifyNewMessageReception(Message message){
		DeviceMessageEvent deviceMessageEvent = new DeviceMessageEvent(this);
		deviceMessageEvent.setNewMessage(message);
		fireNewMessageEvent(deviceMessageEvent);
	}
	
	
	/**
	 * @param message
	 */
	public void notifyHistoryMessage(Message message){
		DeviceMessageEvent deviceMessageEvent = new DeviceMessageEvent(this);
		deviceMessageEvent.setHistoryMessage(message);
		fireHistoryMessageEvent(deviceMessageEvent);
	}

	/**
	 * @param deviceMessageEvent
	 */
	protected void fireMessageEvent(DeviceMessageEvent deviceMessageEvent){
		for (DeviceMessageListener messageListener : messageListeners) {
			messageListener.messageReady(deviceMessageEvent);
		}
	}
	
	/**
	 * @param deviceMessageEvent
	 */
	protected void fireNewMessageEvent(DeviceMessageEvent deviceMessageEvent){
		for (DeviceMessageListener messageListener : messageListeners) {
			messageListener.newMessageReceived(deviceMessageEvent);
		}
	}

	/**
	 * @param deviceMessageEvent
	 */
	protected void fireHistoryMessageEvent(DeviceMessageEvent deviceMessageEvent){
		for (DeviceMessageListener messageListener : messageListeners) {
			messageListener.historyMessageReceived(deviceMessageEvent);
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

/**
 * 
 */
package com.pcs.device.gateway.G2021.event.notifier;

import java.util.ArrayList;
import java.util.List;

import com.pcs.device.gateway.G2021.event.DeviceMessageEvent;
import com.pcs.device.gateway.G2021.event.listener.DeviceMessageListener;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

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
	public void notifyMessageReception(List<Message> messages,DefaultConfiguration deviceConfiguration,Gateway gateway){
		DeviceMessageEvent deviceMessageEvent = new DeviceMessageEvent(this);
		deviceMessageEvent.setMessages(messages);
		fireMessageEvent(deviceMessageEvent,deviceConfiguration,gateway);
	}
	
	
	/**
	 * @param messages
	 */
	public void notifyAlarmReception(List<Message> messages,DefaultConfiguration deviceConfiguration,Gateway gateway){
		DeviceMessageEvent deviceMessageEvent = new DeviceMessageEvent(this);
		deviceMessageEvent.setMessages(messages);
		fireAlarmEvent(deviceMessageEvent,deviceConfiguration,gateway);
	}
	
	
	/**
	 * @param message
	 */
	public void notifyNewMessageReception(Message message,DefaultConfiguration deviceConfiguration,Gateway gateway){
		DeviceMessageEvent deviceMessageEvent = new DeviceMessageEvent(this);
		deviceMessageEvent.setNewMessage(message);
		fireNewMessageEvent(deviceMessageEvent,deviceConfiguration,gateway);
	}
	
	
	/**
	 * @param message
	 */
	public void notifyHistoryMessage(Message message,DefaultConfiguration deviceConfiguration,Gateway gateway){
		DeviceMessageEvent deviceMessageEvent = new DeviceMessageEvent(this);
		deviceMessageEvent.setHistoryMessage(message);
		fireHistoryMessageEvent(deviceMessageEvent,deviceConfiguration,gateway);
	}

	/**
	 * @param deviceMessageEvent
	 */
	protected void fireMessageEvent(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway){
		for (DeviceMessageListener messageListener : messageListeners) {
			messageListener.messageReady(deviceMessageEvent,deviceConfiguration,gateway);
		}
	}
	
	
	/**
	 * @param deviceMessageEvent
	 */
	protected void fireAlarmEvent(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway){
		for (DeviceMessageListener messageListener : messageListeners) {
			messageListener.alarmReceived(deviceMessageEvent,deviceConfiguration,gateway);
		}
	}
	
	/**
	 * @param deviceMessageEvent
	 */
	protected void fireNewMessageEvent(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway){
		for (DeviceMessageListener messageListener : messageListeners) {
			messageListener.newMessageReceived(deviceMessageEvent,deviceConfiguration,gateway);
		}
	}

	/**
	 * @param deviceMessageEvent
	 */
	protected void fireHistoryMessageEvent(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway){
		for (DeviceMessageListener messageListener : messageListeners) {
			messageListener.historyMessageReceived(deviceMessageEvent,deviceConfiguration,gateway);
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

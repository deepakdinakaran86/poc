/**
 * 
 */
package com.pcs.device.gateway.teltonika.event.listener.adapter;

import com.pcs.device.gateway.teltonika.event.DeviceMessageEvent;
import com.pcs.device.gateway.teltonika.event.handler.MessageHandler;
import com.pcs.device.gateway.teltonika.event.listener.DeviceMessageListener;
import com.pcs.deviceframework.pubsub.publishers.BasePublisher;

/**
 * @author PCSEG171
 *
 */
public class DeviceMessageAdapter implements DeviceMessageListener {

	/* (non-Javadoc)
	 * @see com.pcs.driver.core.events.listeners.DeviceMessageListener#messageReady(com.pcs.driver.core.events.beans.DeviceMessageEvent)
	 */
	@Override
	public void messageReady(DeviceMessageEvent deviceMessageEvent) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.pcs.driver.core.events.listeners.DeviceMessageListener#newMessageReceived(com.pcs.driver.core.events.beans.DeviceMessageEvent)
	 */
	@Override
	public void newMessageReceived(DeviceMessageEvent deviceMessageEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void historyMessageReceived(DeviceMessageEvent deviceMessageEvent) {
		// TODO Auto-generated method stub
		
	}

	public static final DeviceMessageListener getHandler(Integer handler){
		switch (handler) {
			case 1:
				return new MessageHandler();
			default:
				return null;
		}
	}

	@Override
	public void addPublisher(BasePublisher basePublisher) {
		// TODO Auto-generated method stub
		
	}
}

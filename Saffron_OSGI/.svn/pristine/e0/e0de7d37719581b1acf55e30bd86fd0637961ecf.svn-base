/**
 * 
 */
package com.pcs.device.gateway.ruptela.event.listener.adapter;

import com.pcs.device.gateway.ruptela.event.DeviceMessageEvent;
import com.pcs.device.gateway.ruptela.event.handler.MessageHandler;
import com.pcs.device.gateway.ruptela.event.listener.DeviceMessageListener;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;

/**
 * @author PCSEG171
 *
 */
public class DeviceMessageAdapter implements DeviceMessageListener {

	/* (non-Javadoc)
	 * @see com.pcs.driver.core.events.listeners.DeviceMessageListener#messageReady(com.pcs.driver.core.events.beans.DeviceMessageEvent)
	 */
	public void messageReady(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.pcs.driver.core.events.listeners.DeviceMessageListener#newMessageReceived(com.pcs.driver.core.events.beans.DeviceMessageEvent)
	 */
	
	public void newMessageReceived(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway) {
		// TODO Auto-generated method stub

	}

	
	public void historyMessageReceived(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway) {
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

	
	public void addPublisher(CorePublisher basePublisher) {
		// TODO Auto-generated method stub
		
	}

	public void alarmReceived(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration deviceConfiguration,Gateway gateway) {
		// TODO Auto-generated method stub
		
	}
}

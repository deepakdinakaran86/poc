package com.pcs.gateway.meitrack.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.meitrack.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.meitrack.bundle.utils.MessageUtil;
import com.pcs.gateway.meitrack.event.DeviceMessageEvent;
import com.pcs.gateway.meitrack.event.listener.adapter.DeviceMessageAdapter;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.message.status.MessageCompletion;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class MessageHandler extends DeviceMessageAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

	@Override
	public void messageReady(DeviceMessageEvent deviceMessageEvent,
			DefaultConfiguration deviceConfiguration, Gateway gateway) {
		
		try {
			if(MessageUtil.getNotificationHandler() == null){
				LOGGER.error("No message handler found !!");
				return ;
			}
			
			for (Message message : deviceMessageEvent.getMessages()) {
				DeviceManagerUtil.getMeitrackDeviceManager().notifyMessage(message, deviceConfiguration, MessageCompletion.PARTIAL, gateway);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Remote invocation exception",e);
		}
		
	}


}

package com.pcs.device.gateway.G2021.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.event.DeviceMessageEvent;
import com.pcs.device.gateway.G2021.event.listener.adapter.DeviceMessageAdapter;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.message.status.MessageCompletion;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class EventAnalyzerHandler extends DeviceMessageAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(EventAnalyzerHandler.class);

	
	
	@Override
	public void messageReady(DeviceMessageEvent deviceMessageEvent,
			DefaultConfiguration deviceConfiguration, Gateway gateway) {
		
		try {
			for (Message message : deviceMessageEvent.getMessages()) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Remote invocation exception",e);
		}
	}




}

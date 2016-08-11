package com.pcs.device.gateway.hobbies.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.pcs.device.gateway.hobbies.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.hobbies.event.DeviceMqttEvent;
import com.pcs.device.gateway.hobbies.event.listener.adapter.MqttDeviceMessageAdapter;
import com.pcs.device.gateway.hobbies.utils.SupportedDevices;
import com.pcs.device.gateway.hobbies.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.data.generic.message.GenericMessage;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DeviceConfiguration;

public class MqttDeviceMessageEventHandler extends MqttDeviceMessageAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MqttDeviceMessageEventHandler.class);
	private static final Gateway APPLE_GATEWAY = SupportedDevices.getGateway(Devices.IOS);
	private static final Gateway ANDROID_GATEWAY = SupportedDevices.getGateway(Devices.ANDROID);
	@Override
	public void messageReady(DeviceMqttEvent deviceMessageEvent) {

		try {
			Gateway gateway = null;
			if(deviceMessageEvent.getMqttMessage() != null){
				LOGGER.info("New Message Received of type {}",deviceMessageEvent.getMqttMessage().getClass());
				gateway = getGateway(deviceMessageEvent.getMqttMessage());
				if(gateway != null)
					DeviceManagerUtil.getHobbiesDeviceManager().notifyGenericMessage(deviceMessageEvent.getMqttMessage(),gateway);
			}
			if(!CollectionUtils.isEmpty(deviceMessageEvent.getMqttMessages())){
				gateway = getGateway(deviceMessageEvent.getMqttMessages().get(0));
				if(gateway != null)
					DeviceManagerUtil.getHobbiesDeviceManager().notifyGenericMessages(deviceMessageEvent.getMqttMessages(),gateway);
			}
		} catch (Exception e) {
			LOGGER.error("Invalid MQTT message received at the server",e);
		}
	}

	private Gateway getGateway(GenericMessage genericMessage) {
		DeviceConfiguration configuration = DeviceManagerUtil.getHobbiesDeviceManager().getConfiguration(genericMessage.getSourceId(), APPLE_GATEWAY);
		if(configuration == null){
			configuration = DeviceManagerUtil.getHobbiesDeviceManager().getConfiguration(genericMessage.getSourceId(), ANDROID_GATEWAY);
		}else{
			return APPLE_GATEWAY;
		}
		if(configuration == null){
			LOGGER.error("Unknown device sending data {}",genericMessage.getSourceId());
			return null;
		}else{
			return ANDROID_GATEWAY;
		}
	}
}

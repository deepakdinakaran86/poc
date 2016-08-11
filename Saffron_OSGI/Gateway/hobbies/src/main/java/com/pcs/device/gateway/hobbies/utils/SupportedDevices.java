package com.pcs.device.gateway.hobbies.utils;

import com.pcs.device.gateway.hobbies.config.HobbiesGatewayConfiguration;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public class SupportedDevices {
	
	public enum Devices{
		IOS, ANDROID;
	}

	public static final Gateway getGateway(Devices device){
		Gateway gateway = new Gateway();
		switch (device) {
		
		case IOS:
			gateway.setModel(HobbiesGatewayConfiguration.APPLE_DEVICE_MODEL);
			gateway.setProtocol(HobbiesGatewayConfiguration.APPLE_DEVICE_PROTOCOL);
			gateway.setType(HobbiesGatewayConfiguration.APPLE_DEVICE_TYPE);
			gateway.setVendor(HobbiesGatewayConfiguration.APPLE_VENDOR);
			gateway.setVersion(HobbiesGatewayConfiguration.APPLE_OS_VERSION);
			gateway.setAutoclaimEnabled(true);
			return gateway;
			
		case ANDROID:
			gateway.setModel(HobbiesGatewayConfiguration.ANDROID_DEVICE_MODEL);
			gateway.setProtocol(HobbiesGatewayConfiguration.ANDROID_DEVICE_PROTOCOL);
			gateway.setType(HobbiesGatewayConfiguration.ANDROID_DEVICE_TYPE);
			gateway.setVendor(HobbiesGatewayConfiguration.ANDROID_VENDOR);
			gateway.setVersion(HobbiesGatewayConfiguration.ANDROID_OS_VERSION);
			gateway.setAutoclaimEnabled(true);
			return gateway;
			
		default:
			return null;
		}
		
	}
}

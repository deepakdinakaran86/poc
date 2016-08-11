package com.pcs.device.gateway.jace.utils;

import com.pcs.device.gateway.jace.config.JaceConfiguration;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public class SupportedDevices {
	
	public enum Devices{
		JACE_CONNECTOR;
	}

	public static final Gateway getGateway(Devices device){
		switch (device) {
		case JACE_CONNECTOR:
			Gateway gateway = new Gateway();
			gateway.setModel(JaceConfiguration.JACE_CONNECTOR_MODEL);
			gateway.setProtocol(JaceConfiguration.JACE_PROTOCOL);
			gateway.setType(JaceConfiguration.JACE_TYPE);
			gateway.setVendor(JaceConfiguration.JACE_VENDOR);
			gateway.setVersion(JaceConfiguration.JACE_VERSION);
			gateway.setAutoclaimEnabled(true);
			return gateway;

		default:
			return null;
		}
		
	}
}

package com.pcs.device.gateway.ruptela.utils;

import com.pcs.device.gateway.ruptela.config.RuptelaGatewayConfiguration;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public class SupportedDevices {
	
	public enum Devices{
		FMXXX;
	}

	public static final Gateway getGateway(Devices device){
		switch (device) {
		case FMXXX:
			Gateway gateway = new Gateway();
			gateway.setModel(RuptelaGatewayConfiguration.FMXXX_MODEL);
			gateway.setProtocol(RuptelaGatewayConfiguration.FMXXX_PROTOCOL);
			gateway.setType(RuptelaGatewayConfiguration.FMXXX_TYPE);
			gateway.setVendor(RuptelaGatewayConfiguration.FMXXX_VENDOR);
			gateway.setVersion(RuptelaGatewayConfiguration.FMXXX_VERSION);
			gateway.setAutoclaimEnabled(false);
			return gateway;

		default:
			return null;
		}
		
	}
}

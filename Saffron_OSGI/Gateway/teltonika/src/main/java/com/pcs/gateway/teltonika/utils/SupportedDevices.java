package com.pcs.gateway.teltonika.utils;

import com.pcs.gateway.teltonika.config.TeltonikaGatewayConfiguration;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public class SupportedDevices {
	
	public enum Devices{
		FMXXX;
	}

	public static final Gateway getGateway(Devices device){
		switch (device) {
		case FMXXX:
			Gateway gateway = new Gateway();
			gateway.setModel(TeltonikaGatewayConfiguration.FMXXX_MODEL);
			gateway.setProtocol(TeltonikaGatewayConfiguration.FMXXX_PROTOCOL);
			gateway.setType(TeltonikaGatewayConfiguration.FMXXX_TYPE);
			gateway.setVendor(TeltonikaGatewayConfiguration.FMXXX_VENDOR);
			gateway.setVersion(TeltonikaGatewayConfiguration.FMXXX_VERSION);
			gateway.setAutoclaimEnabled(false);
			return gateway;

		default:
			return null;
		}
		
	}
}

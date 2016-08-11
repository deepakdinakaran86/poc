package com.pcs.gateway.g2021.utils;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public class SupportedDevices {
	
	public enum Devices{
		EDCP;
	}

	public static final Gateway getGateway(Devices device){
		switch (device) {
		case EDCP:
			Gateway gateway = new Gateway();
			gateway.setModel(G2021GatewayConfiguration.EDCP_MODEL);
			gateway.setProtocol(G2021GatewayConfiguration.EDCP_PROTOCOL);
			gateway.setType(G2021GatewayConfiguration.EDCP_TYPE);
			gateway.setVendor(G2021GatewayConfiguration.EDCP_VENDOR);
			gateway.setVersion(G2021GatewayConfiguration.EDCP_VERSION);
			return gateway;

		default:
			return null;
		}
		
	}
}

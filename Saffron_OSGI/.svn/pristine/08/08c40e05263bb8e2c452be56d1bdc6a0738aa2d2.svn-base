package com.pcs.device.gateway.enevo.onecollect.utils;

import com.pcs.device.gateway.enevo.onecollect.config.EnevoOnecollectGatewayConfiguration;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public class SupportedDevices {
	
	public enum Devices{
		EDCP;
	}

	public static final Gateway getGateway(Devices device){
		switch (device) {
		case EDCP:
			Gateway gateway = new Gateway();
			gateway.setModel(EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.EDCP_MODEL));
			gateway.setProtocol(EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.EDCP_PROTOCOL));
			gateway.setType(EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.EDCP_TYPE));
			gateway.setVendor(EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.EDCP_VENDOR));
			gateway.setVersion(EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.EDCP_VERSION));
			return gateway;

		default:
			return null;
		}
		
	}
}

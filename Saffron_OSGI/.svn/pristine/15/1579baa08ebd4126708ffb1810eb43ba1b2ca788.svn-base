package com.pcs.device.gateway.meitrack.utils;

import com.pcs.device.gateway.meitrack.config.MeitrackGatewayConfiguration;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public class SupportedDevices {
	
	public enum Devices{
		MVTXXX;
	}

	public static final Gateway getGateway(Devices device){
		switch (device) {
		case MVTXXX:
			Gateway gateway = new Gateway();
			gateway.setModel((MeitrackGatewayConfiguration.MVTXXX_MODEL));
			gateway.setProtocol((MeitrackGatewayConfiguration.MVTXXX_PROTOCOL));
			gateway.setType((MeitrackGatewayConfiguration.MVTXXX_TYPE));
			gateway.setVendor((MeitrackGatewayConfiguration.MVTXXX_VENDOR));
			gateway.setVersion((MeitrackGatewayConfiguration.MVTXXX_VERSION));
			gateway.setAutoclaimEnabled(false);
			return gateway;

		default:
			return null;
		}
		
	}
}

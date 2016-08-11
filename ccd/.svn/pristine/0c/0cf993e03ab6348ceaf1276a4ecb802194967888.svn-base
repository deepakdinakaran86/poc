package com.pcs.util.faultmonitor.util;

import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.util.faultmonitor.configuration.FaultMonitorConfiguration;

public class SupportedDevices {
	
	public enum Devices{
		EDCP;
	}

	public static final Gateway getGateway(Devices device){
		switch (device) {
		case EDCP:
			Gateway gateway = new Gateway();
			gateway.setModel(FaultMonitorConfiguration.EDCP_MODEL);
			gateway.setProtocol(FaultMonitorConfiguration.EDCP_PROTOCOL);
			gateway.setType(FaultMonitorConfiguration.EDCP_TYPE);
			gateway.setVendor(FaultMonitorConfiguration.EDCP_VENDOR);
			gateway.setVersion(FaultMonitorConfiguration.EDCP_VERSION);
			gateway.setAutoclaimEnabled(true);
			return gateway;

		default:
			return null;
		}
		
	}
}

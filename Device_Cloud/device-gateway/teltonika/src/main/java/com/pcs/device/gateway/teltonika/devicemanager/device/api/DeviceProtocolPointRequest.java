package com.pcs.device.gateway.teltonika.devicemanager.device.api;

import com.pcs.device.gateway.teltonika.config.TeltonikaGatewayConfiguration;
import com.pcs.device.gateway.teltonika.devicemanager.auth.BaseRequest;

/**
 * @author pcseg310
 *
 */
public class DeviceProtocolPointRequest extends BaseRequest {

	private String device;
	private String protocol;

	
	public DeviceProtocolPointRequest() {
		super();
	}
	
	public DeviceProtocolPointRequest(String device, String protocol){
		this.device = device;
		this.protocol = protocol;
	}
	
	public String getDevice() {
		return device;
	}


	public void setDevice(String device) {
		this.device = device;
	}

	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		return TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.DEVICE_PROTOCOL_POINTS_URL).replace("{device_name}", this.device).replace("{protocol_name}", this.protocol);
	}
}

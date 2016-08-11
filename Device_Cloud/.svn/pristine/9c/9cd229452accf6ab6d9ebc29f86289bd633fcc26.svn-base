/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.auth;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;

/**
 * @author pcseg310
 *
 */
public class RegistrationRequest extends BaseRequest {

	public RegistrationRequest() {
		super(0);
	}
	
	public String buildRegistrationUrl(String sourceId){
		return getRegistrationUrl();
	}
	
	public String getRegistrationUrl() {
		return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DEVICE_REGISTRATION_URL);
	}
}

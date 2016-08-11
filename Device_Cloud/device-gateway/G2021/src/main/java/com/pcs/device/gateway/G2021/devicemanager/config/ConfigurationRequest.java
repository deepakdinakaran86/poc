/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.config;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.auth.BaseRequest;

/**
 * @author pcseg310
 *
 */
public class ConfigurationRequest extends BaseRequest {

	public ConfigurationRequest() {
		super();
	}
	
	public String buildConfigurationUrl(String pathParam){
		return getConfigurationUrl().replace("{id}", pathParam);
	}
	
	public String getConfigurationUrl() {
		return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.CONFIGURATION_URL);
	}

}

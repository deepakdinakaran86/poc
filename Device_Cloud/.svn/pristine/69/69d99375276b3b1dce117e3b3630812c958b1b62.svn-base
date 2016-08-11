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
public class DatasourceRegisterRequest extends BaseRequest {

	public DatasourceRegisterRequest() {
		super(1);
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATASOURCE_REGISTER_URL);
	}

}

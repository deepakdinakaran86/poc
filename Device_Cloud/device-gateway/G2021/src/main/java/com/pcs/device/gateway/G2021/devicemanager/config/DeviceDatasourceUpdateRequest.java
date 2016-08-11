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
public class DeviceDatasourceUpdateRequest extends BaseRequest {

	public DeviceDatasourceUpdateRequest() {
		super(2);//enables the base request to switch to appropriate service URL.
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DEVICE_DATASOURCE_UPDATE_URL);
	}

}

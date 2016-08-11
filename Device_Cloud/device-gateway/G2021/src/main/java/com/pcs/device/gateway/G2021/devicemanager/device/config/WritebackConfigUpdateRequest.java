/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.device.config;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.auth.BaseRequest;

/**
 * @author pcseg310
 *
 */
public class WritebackConfigUpdateRequest extends BaseRequest {

	private String sourceId;
	
	public WritebackConfigUpdateRequest() {
		//TODO REVERT TO PLATFORM IP (1) AFTER TESTING WITH LOCAL DEPLOYMENT
		super(4);
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DEVICE_WRITEBACK_CONFIG_URL).replace("{source_id}", this.sourceId);
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}


}

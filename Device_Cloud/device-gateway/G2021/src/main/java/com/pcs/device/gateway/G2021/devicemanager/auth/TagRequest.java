package com.pcs.device.gateway.G2021.devicemanager.auth;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;

public class TagRequest extends BaseRequest {

	public TagRequest() {
		super(0);
	}
	
	public String buildConfigurationUrl(String sourceId){
		return getConfigurationUrl().replace("{source_id}", sourceId);
	}
	
	public String getConfigurationUrl() {
		return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATASOURCE_TAG_URL);
	}

}

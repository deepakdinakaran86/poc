/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.auth.BaseRequest;

/**
 * @author pcseg310
 *
 */
public class DatasourcePublishRequest extends BaseRequest {

	public DatasourcePublishRequest() {
		super(1);
	}
	
	public String buildConfigurationUrl(String pathParam){
		try {
			return getConfigurationUrl().replace("{datasource_name}", URLEncoder.encode(pathParam,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public String getConfigurationUrl() {
		return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATASOURCE_PUBLISH_URL);
	}

}

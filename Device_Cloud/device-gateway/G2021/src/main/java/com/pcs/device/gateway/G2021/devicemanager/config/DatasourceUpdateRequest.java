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
public class DatasourceUpdateRequest extends BaseRequest {

	private String datasourceName;
	
	public DatasourceUpdateRequest() {
		super(1);
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		try {
			return G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATASOURCE_UPDATE_URL).replace("{datasource_name}", URLEncoder.encode(this.datasourceName,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

}

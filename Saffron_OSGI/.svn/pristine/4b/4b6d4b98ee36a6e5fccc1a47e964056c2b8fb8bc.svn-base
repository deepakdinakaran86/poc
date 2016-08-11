/**
 * 
 */
package com.pcs.saffron.manager.api.datasource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

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
		return ManagerConfiguration.getProperty(ManagerConfiguration.DATASOURCE_PUBLISH_URL);
	}

}

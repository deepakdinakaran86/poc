/**
 * 
 */
package com.pcs.saffron.manager.api.configuration;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

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
		return ManagerConfiguration.getProperty(ManagerConfiguration.CONFIGURATION_URL);
	}

}

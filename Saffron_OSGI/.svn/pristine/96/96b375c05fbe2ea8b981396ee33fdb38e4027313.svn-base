/**
 * 
 */
package com.pcs.saffron.deviceutil.api.configuration.request;

import com.pcs.saffron.deviceutil.config.ManagerConfiguration;
import com.pcs.saffron.deviceutil.util.BaseRequest;

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

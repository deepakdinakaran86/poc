/**
 * 
 */
package com.pcs.saffron.manager.api.datasource;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

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
		return ManagerConfiguration.getProperty(ManagerConfiguration.DATASOURCE_REGISTER_URL);
	}

}

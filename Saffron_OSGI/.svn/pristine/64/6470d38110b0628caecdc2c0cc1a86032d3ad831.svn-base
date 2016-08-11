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
public class DeviceDatasourceUpdateRequest extends BaseRequest {

	public DeviceDatasourceUpdateRequest() {
		super(2);//enables the base request to switch to appropriate service URL.
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		return ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_DATASOURCE_UPDATE_URL);
	}

}

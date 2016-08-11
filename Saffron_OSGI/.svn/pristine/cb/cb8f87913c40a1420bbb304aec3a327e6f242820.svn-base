/**
 * 
 */
package com.pcs.saffron.manager.registration.api;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

/**
 * @author pcseg310
 *
 */
public class RegistrationRequest extends BaseRequest {

	public RegistrationRequest() {
		super(0);
	}
	
	public String buildRegistrationUrl(String sourceId){
		return getRegistrationUrl();
	}
	
	public String getRegistrationUrl() {
		return ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_REGISTRATION_URL);
	}
}

/**
 * 
 */
package com.pcs.saffron.manager.registration.api;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

/**
 * @author pcseg296
 * 
 */
public class CumminsRegistrationRequest extends BaseRequest {

	public CumminsRegistrationRequest() {
		super(0);
	}

	public String buildRegistrationUrl() {
		return getRegistrationUrl();
	}

	public String getRegistrationUrl() {
		return ManagerConfiguration
		        .getProperty(ManagerConfiguration.CUMMINS_DEVICE_REGISTRATION_URL);
	}
}

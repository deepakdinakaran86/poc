package com.pcs.saffron.manager.authentication;

import com.pcs.saffron.manager.bean.Device;


/**
 * @author pcseg310
 *
 */
public class AuthenticationRequest extends BaseRequest {

	private String authenticationUrl;
	private Device device;
	
	
	public AuthenticationRequest() {
		super();
	}
	

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}


	public Device getDevice() {
		return device;
	}



	public void setDevice(Device device) {
		this.device = device;
	}

}

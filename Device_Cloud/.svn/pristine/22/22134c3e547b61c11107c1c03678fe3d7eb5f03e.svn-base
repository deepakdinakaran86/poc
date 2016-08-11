package com.pcs.device.gateway.G2021.devicemanager.auth;

/**
 * @author pcseg310
 *
 */
public class AuthenticationRequest extends BaseRequest {

	private String sourceIdentfier;

	private String authenticationUrl;
	
	public AuthenticationRequest() {
		super();
	}
	
	public String getSourceIdentfier() {
		return sourceIdentfier;
	}

	public void setSourceIdentfier(String sourceIdentfier) {
		this.sourceIdentfier = sourceIdentfier;
	}

	public String getAuthenticationUrl() {
		authenticationUrl = authenticationUrl.replace("{id}", this.sourceIdentfier);
		return authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}
	
}

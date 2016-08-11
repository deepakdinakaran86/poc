package com.pcs.device.gateway.enevo.onecollect.api.authenticate.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pcs.device.gateway.enevo.onecollect.api.authenticate.bean.Credentials;
import com.pcs.device.gateway.enevo.onecollect.api.bean.BaseRequest;
import com.pcs.device.gateway.enevo.onecollect.config.EnevoOnecollectGatewayConfiguration;

/**
 * 
 * @author PCSEG311
 * 
 */

@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class AuthRequest extends BaseRequest {
	
	private Credentials credentials = null;
	private String accessToken = null;
	private String authenticationUrl = EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.AUTHENTICATION_URL);
	
	public AuthRequest() {
		super();
	}

	
	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}


	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}


	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}

package com.pcs.device.gateway.enevo.onecollect.api.authenticate;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.enevo.onecollect.api.authenticate.request.AuthRequest;
import com.pcs.device.gateway.enevo.onecollect.api.authenticate.request.AuthResponse;
import com.pcs.device.gateway.enevo.onecollect.config.EnevoOnecollectGatewayConfiguration;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * Authentication service implementation for ENEVO system
 *
 * @author pcseg310
 *
 */
public class EnevoAuthenticationService  {

	private static final Logger logger = LoggerFactory.getLogger(EnevoAuthenticationService.class);

	/**
	 * Remotely authenticates the source id with galaxy platform.If the
	 * authentication is successful galaxy would sent 200 OK response,otherwise
	 * authentication is considered to be failed.
	 *
	 */
	public AuthResponse remoteAuthenticate(AuthRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Authentication request should not be null");
		}
		AuthResponse response = null;
		Client client = ApacheRestClient.builder().build();

		try {
			response = client.post(request.getAuthenticationUrl(), null, request.getCredentials(), AuthResponse.class);
		} catch (ClientException e) {
			logger.error("Error occured while processing the authentication request", e);
		}

		return response;
	}
	
	
	/**
	 * Terminates the active session from the enevo's server.
	 * @param request
	 * @return
	 */
	public String logout(AuthRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Authentication request should not be null");
		}
		String response = null;
		Client client = ApacheRestClient.builder().build();

		try {
			Map<String, String> header = new HashMap<String, String>();
			header.put(EnevoOnecollectGatewayConfiguration.HEADER_TOKEN, request.getAccessToken());
			response = client.delete(request.getAuthenticationUrl(), header, String.class);
		} catch (ClientException e) {
			logger.error("Error occured while processing the authentication request", e);
		}

		return response;
	}

}

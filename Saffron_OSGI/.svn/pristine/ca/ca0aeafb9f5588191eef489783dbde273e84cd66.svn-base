package com.pcs.device.gateway.enevo.onecollect.api.snapshot;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.enevo.onecollect.api.snapshot.beans.Snapshot;
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
public class EnevoSnapshotService  {

	private static final Logger logger = LoggerFactory.getLogger(EnevoSnapshotService.class);

	/**
	 * Remotely authenticates the source id with galaxy platform.If the
	 * authentication is successful galaxy would sent 200 OK response,otherwise
	 * authentication is considered to be failed.
	 *
	 * @see com.pcs.device.gateway.g2021.devicemanager.auth.AuthenticationService#remoteAuthenticate(com.pcs.device.gateway.g2021.devicemanager.auth.AuthenticationRequest)
	 */
	public Snapshot getSnapshot(com.pcs.device.gateway.enevo.onecollect.api.snapshot.request.SnapshotRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Authentication request should not be null");
		}
		Snapshot response = null;
		Client client = ApacheRestClient.builder().build();

		try {
			Map<String, String> header = new HashMap<String, String>();
			header.put(EnevoOnecollectGatewayConfiguration.HEADER_TOKEN, request.getAccessToken());
			response = client.get(request.getSnapshotURL(), header, Snapshot.class);
			header = null;
		} catch (ClientException e) {
			logger.error("Error occured while processing the authentication request", e);
		}

		return response;
	}

}

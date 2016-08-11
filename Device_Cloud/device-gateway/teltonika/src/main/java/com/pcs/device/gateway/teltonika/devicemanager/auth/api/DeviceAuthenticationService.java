package com.pcs.device.gateway.teltonika.devicemanager.auth.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;
import com.pcs.device.gateway.teltonika.devicemanager.auth.AuthenticationLifeCycle;
import com.pcs.device.gateway.teltonika.devicemanager.auth.AuthenticationRequest;
import com.pcs.device.gateway.teltonika.devicemanager.auth.AuthenticationService;
import com.pcs.device.gateway.teltonika.devicemanager.auth.PostAuthenticationCallback;
import com.pcs.device.gateway.teltonika.devicemanager.auth.RemoteAuthenticationResponse;
import com.pcs.device.gateway.teltonika.devicemanager.bean.Device;
import com.pcs.device.gateway.teltonika.devicemanager.bean.Status;
import com.pcs.device.gateway.teltonika.devicemanager.bean.StatusType;
import com.pcs.device.gateway.teltonika.devicemanager.bean.TeltonikaDeviceConfiguration;

/**
 * Authentication service implementation.
 *
 * @author pcseg310
 *
 */
public class DeviceAuthenticationService implements AuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceAuthenticationService.class);

	/**
	 * Remotely authenticates the source id with galaxy platform.If the
	 * authentication is successful galaxy would sent 200 OK response,otherwise
	 * authentication is considered to be failed.
	 *
	 * @see com.pcs.device.gateway.g2021.devicemanager.auth.AuthenticationService#remoteAuthenticate(com.pcs.device.gateway.g2021.devicemanager.auth.AuthenticationRequest)
	 */
	@Override
	public RemoteAuthenticationResponse remoteAuthenticate(AuthenticationRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Authentication request should not be null");
		}
		DeviceAuthenticationResponse response = null;
		Client client = ApacheRestClient.builder().host(request.getHostIp()).port(request.getPort())
		        .scheme(request.getScheme()).build();

		try {
			response = client.get(request.getAuthenticationUrl(), null, DeviceAuthenticationResponse.class);
		} catch (ClientException e) {
			logger.error("Error occured while processing the authentication request", e);
		}

		return response;
	}

	/**
	 * Process the {@link RemoteAuthenticationResponse} and resolves the
	 * {@link AuthenticationLifeCycle}.Also it fires
	 * {@link PostAuthenticationCallback} after processing the response if it is
	 * available.
	 *
	 * @see com.pcs.device.gateway.g2021.devicemanager.auth.AuthenticationService#processAuthenticationResponse(java.lang.String,
	 *      com.pcs.device.gateway.g2021.devicemanager.auth.RemoteAuthenticationResponse,
	 *      com.pcs.device.gateway.g2021.devicemanager.auth.PostAuthenticationCallback)
	 */
	@Override
	public AuthenticationLifeCycle processAuthenticationResponse(String sourceIdentifier,
	                                                             RemoteAuthenticationResponse response,
	                                                             PostAuthenticationCallback callback) {
		DeviceAuthenticationResponse galaxyResponse = null;
		if (response instanceof DeviceAuthenticationResponse) {
			galaxyResponse = (DeviceAuthenticationResponse) response;
		}

		AuthenticationLifeCycle lifecycle = null;
		if (galaxyResponse.getDeviceId() == null || galaxyResponse.getConfigurations() == null) {
			logger.warn(
			        "Device with id [{}] is successfully authenticated by the platform, but the device template and other details is not recieved",
			        sourceIdentifier);
		}
		lifecycle = processResponse(sourceIdentifier, galaxyResponse, callback);

		return lifecycle;
	}

	private AuthenticationLifeCycle processResponse(String sourceIdentifier, DeviceAuthenticationResponse response,
	                                                PostAuthenticationCallback callback) {
		Device device = new Device();
		device.setDeviceId(response.getDeviceId());
		device.setSourceId(response.getSourceId());
		device.setDatasourceName(response.getDatasourceName());
		device.setSubscription(response.getSubscription());
		device.setDeviceProtocol(response.getProtocol());
		device.setDeviceComnProtocol(response.getNetworkProtocol());
		device.setTags(response.getTags());
		device.setDeviceType(response.getType());
		device.setIsPublishing(response.getIsPublishing());
		device.setEntityStatus(new Status(0, StatusType.ACTIVE));// TODO
		                                                         // hardcoded
		                                                         // status until
		                                                         // flag comes
		                                                         // in the api
		AuthenticationLifeCycle lifecycle = null;

		switch (device.getEntityStatus().getStatusName()) {
		case ACTIVE:
			TeltonikaDeviceConfiguration config = new TeltonikaDeviceConfiguration();
			config.setDevice(device);
			config.setSourceIdentifier(device.getDeviceId());
			if (response.getConfigurations() != null && !response.getConfigurations().isEmpty()) {
				config.addPointMappings(response.getConfigurations());
				config.setPointConfigurations(response.getConfigurations());
				config.setConfigured(true);
			} else {
				config.setConfigured(false);
			}
			config.setUnitId(response.getUnitId());
			callback.firePostAuthentication(config);

			lifecycle = AuthenticationLifeCycle.FULLY_AUTHENTICATED;
			break;
		case INACTIVE:
			lifecycle = AuthenticationLifeCycle.NEED_REGISTRATION;
			break;
		default:
			break;
		}

		return lifecycle;
	}
}

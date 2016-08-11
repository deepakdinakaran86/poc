package com.pcs.saffron.manager.authentication.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.authentication.AuthenticationLifeCycle;
import com.pcs.saffron.manager.authentication.AuthenticationRequest;
import com.pcs.saffron.manager.authentication.AuthenticationService;
import com.pcs.saffron.manager.authentication.PostAuthenticationCallback;
import com.pcs.saffron.manager.authentication.RemoteAuthenticationResponse;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;

/**
 * Authentication service implementation for galaxy-platform
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
	public RemoteAuthenticationResponse remoteAuthenticate(AuthenticationRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Authentication request should not be null");
		}
		DeviceAuthenticationResponse response = null;
		Client client = ApacheRestClient.builder().host(request.getHostIp()).port(request.getPort())
		        .scheme(request.getScheme()).build();

		try {
			//response = client.get(request.getAuthenticationUrl(), null, DeviceAuthenticationResponse.class);
			response = client.post(request.getAuthenticationUrl(), null, request.getDevice(), DeviceAuthenticationResponse.class);
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
	
	public AuthenticationLifeCycle processAuthenticationResponse(String sourceIdentifier,
	                                                             RemoteAuthenticationResponse response,
	                                                             PostAuthenticationCallback callback) {
		DeviceAuthenticationResponse galaxyResponse = null;
		if (response instanceof DeviceAuthenticationResponse) {
			galaxyResponse = (DeviceAuthenticationResponse) response;
		}

		AuthenticationLifeCycle lifecycle = null;
		lifecycle = processGalaxyAuthenticationResponse(sourceIdentifier, galaxyResponse, callback);
		

		return lifecycle;
	}

	private AuthenticationLifeCycle processGalaxyAuthenticationResponse(String sourceIdentifier,
																			DeviceAuthenticationResponse galaxyResponse,
	                                                                    PostAuthenticationCallback callback) {
		AuthenticationLifeCycle lifecycle = null;
		Device device = galaxyResponse.getDevice();
		switch (galaxyResponse.getGeneralResponse().getStatus()) {
		case SUBSCRIBED:
			DefaultConfiguration config = new DefaultConfiguration();
			config.setDevice(device);
			config.setDeviceIP(device.getIp());
			config.setDeviceConnectedPort(device.getConnectedPort());
			config.setDeviceWritebackPort(device.getWriteBackPort());
			config.setSourceIdentifier(sourceIdentifier);
			
			if(galaxyResponse.getDevice().getConfigurations() != null && !CollectionUtils.isEmpty(galaxyResponse.getDevice().getConfigurations().getConfigPoints())){
				config.addPointMappings(galaxyResponse.getDevice().getConfigurations().getConfigPoints());
				config.setConfigPoints(galaxyResponse.getDevice().getConfigurations().getConfigPoints());
				config.setConfigured(true);
			}else{
				config.setConfigured(false);
			}
			config.setUnitId(galaxyResponse.getDevice().getUnitId());
			callback.firePostAuthentication(config);

			lifecycle = AuthenticationLifeCycle.AUTHENTICATED;
			break;
		case NOT_AVAIALABLE:
			lifecycle = AuthenticationLifeCycle.NEED_REGISTRATION;
			break;
		case UNSUBSCRIBED:
			lifecycle = AuthenticationLifeCycle.NOT_AUTHENTICATED;
		default:
			break;
		}

		return lifecycle;
	}
}

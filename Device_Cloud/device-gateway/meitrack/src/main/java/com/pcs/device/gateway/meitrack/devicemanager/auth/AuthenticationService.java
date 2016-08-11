package com.pcs.device.gateway.meitrack.devicemanager.auth;

/**
 * Defines an authentication contract between device-manager and any platform to
 * authenticate with.
 * 
 * @author pcseg310
 *
 */
public interface AuthenticationService {

	/**
	 * Responsible to authenticate the source with the remote system.
	 * 
	 * @param request
	 * @return
	 */
	RemoteAuthenticationResponse remoteAuthenticate(
	        AuthenticationRequest request);

	/**
	 * Process the authentication response.
	 * 
	 * @param sourceIdentifier
	 * @param response
	 * @param callback
	 * @return
	 */
	AuthenticationLifeCycle processAuthenticationResponse(
            String sourceIdentifier, RemoteAuthenticationResponse response,
            PostAuthenticationCallback callback);
}

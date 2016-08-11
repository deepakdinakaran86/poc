/**
 * 
 */
package com.pcs.device.gateway.meitrack.devicemanager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.pcs.device.gateway.meitrack.config.MeitrackGatewayConfiguration;
import com.pcs.device.gateway.meitrack.devicemanager.auth.AuthenticationLifeCycle;
import com.pcs.device.gateway.meitrack.devicemanager.auth.AuthenticationRequest;
import com.pcs.device.gateway.meitrack.devicemanager.auth.AuthenticationService;
import com.pcs.device.gateway.meitrack.devicemanager.auth.PostAuthenticationCallback;
import com.pcs.device.gateway.meitrack.devicemanager.auth.RemoteAuthenticationResponse;
import com.pcs.device.gateway.meitrack.devicemanager.auth.api.DeviceAuthenticationService;
import com.pcs.device.gateway.meitrack.devicemanager.bean.DeviceProtocolType;
import com.pcs.device.gateway.meitrack.devicemanager.bean.Point;
import com.pcs.device.gateway.meitrack.devicemanager.bean.MeitrackDeviceConfiguration;
import com.pcs.device.gateway.meitrack.devicemanager.cache.CacheManager;
import com.pcs.device.gateway.meitrack.devicemanager.cache.CacheProviderType;
import com.pcs.device.gateway.meitrack.devicemanager.device.api.DeviceTypeService;
import com.pcs.device.gateway.meitrack.devicemanager.session.SessionManager;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.cache.CacheProviderConfiguration;
import com.pcs.deviceframework.cache.hazelcast.HazelCastConfiguration;
import com.pcs.deviceframework.devicemanager.DeviceManager;
import com.pcs.deviceframework.devicemanager.authentication.AuthenticationResponse;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;
import com.pcs.deviceframework.devicemanager.session.SessionInfo;

/**
 * @author pcseg310
 *
 */
public final class MeitrackDeviceManager extends DeviceManager {

	private static final MeitrackDeviceManager instance = new MeitrackDeviceManager();

	private CacheProvider cacheProvider;
	private SessionManager sessionManager;

	private MeitrackDeviceManager() {
		initializeCacheProvider();
		this.sessionManager = new SessionManager(getCacheProvider());
	}

	public static final MeitrackDeviceManager instance() {
		return instance;
	}

	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@Override
	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}

	@Override
	public AuthenticationResponse authenticate(Object sourceId) {

		validateSourceIdentifier(sourceId);

		AuthenticationResponse response = null;
		response = doLocalAuthentication(sourceId);

		if (response == null) {
			response = doRemoteAuthentication(sourceId.toString());
		}

		return response;
	}

	public void refreshDeviceConfiguration(Object identifier, MeitrackDeviceConfiguration configuration) {
		validateSourceIdentifier(identifier);

		Object unitId = resolvePrincipalId(identifier);
		if (unitId == null) {
			return;
		}
		Cache deviceConfigurationCache = getCacheProvider().getCache(
		        MeitrackGatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
		deviceConfigurationCache.put(unitId, configuration);
	}

	@Override
	public DeviceConfiguration getConfiguration(Object sourceId) {
		validateSourceIdentifier(sourceId);
		MeitrackDeviceConfiguration config = getDeviceConfiguration(sourceId);
		return config;
	}

	/**
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#getSessionByPrincipal(java.lang.Object)
	 */
	@Override
	public SessionInfo getSessionByPrincipal(Object principal) {
		if (principal == null) {
			throw new IllegalArgumentException("Principal id should not be null");
		}
		Object principalId = resolvePrincipalId(principal);
		return this.sessionManager.getSessionByPrincipal(principalId);
	}

	/**
	 * 
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#invalidateSession(java.lang.Object)
	 */
	@Override
	public boolean invalidateSession(Object principal) {
		if (principal == null) {
			throw new IllegalArgumentException("Principal id should not be null");
		}
		boolean invalidated = false;
		Object principalId = resolvePrincipalId(principal);
		this.sessionManager.invalidateSession(principalId);
		evictDeviceConfiguration(principal);
		evictAllKeys(principal);
		invalidated = true;
		return invalidated;
	}

	/**
	 * Retrieves all points available for a particular protocol of a device
	 * type.
	 * 
	 * @param protocol
	 * @param deviceType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Point> getPointsByProtocolAndDeviceType(String device, String protocol) {

		Cache protocolPoints = getCacheProvider().getCache(MeitrackGatewayConfiguration.DEVICE_POINTS_PROTOCOL_CACHE);
		HashMap<String, Point> pointMap = new HashMap<String, Point>();
		if (protocolPoints != null) {
			DeviceProtocolType deviceProtocolType = new DeviceProtocolType(protocol, device);
			pointMap = protocolPoints.get(deviceProtocolType, HashMap.class);
			if (!CollectionUtils.isEmpty(pointMap)) {
				return pointMap;
			} else {
				DeviceTypeService deviceTypeService = new DeviceTypeService();
				pointMap = deviceTypeService.getProtocolPoints(device, protocol);
				protocolPoints.put(deviceProtocolType, pointMap);
			}
		}
		return pointMap;
	}

	/**
	 * Triggers the authentication process with the remote system.The delegate
	 * service {@link AuthenticationService} determines which remote system it
	 * needs to authenticate with.
	 * 
	 * @param sourceIdentifier
	 * @return
	 */
	private AuthenticationResponse doRemoteAuthentication(final String sourceIdentifier) {
		AuthenticationResponse response = null;
		AuthenticationRequest request = prepareAuthenticationRequest(sourceIdentifier);

		AuthenticationService authenticationService = new DeviceAuthenticationService();
		RemoteAuthenticationResponse platformResponse = authenticationService.remoteAuthenticate(request);

		if (platformResponse != null) { // 200 OK
			// Authenticated
			PostAuthenticationCallback authenticationCallback = new PostAuthenticationCallback() {
				@Override
				public void firePostAuthentication(MeitrackDeviceConfiguration config) {
					Cache deviceConfigurationCache = getCacheProvider().getCache(
					        MeitrackGatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
					Cache deviceKeysCache = getCacheProvider().getCache(MeitrackGatewayConfiguration.DEVICE_KEYS_CACHE);
					if (config != null) {
						Integer unitId = config.getUnitId();
						String platformId = config.getDevice().getDeviceId();

						// Cache the device configuration against unit id
						deviceConfigurationCache.put(unitId, config);

						Set<Object> keySet = new HashSet<Object>();
						keySet.add(platformId);
						keySet.add(unitId);
						keySet.add(sourceIdentifier);

						// Cache the different keys against each key for this
						// device.
						deviceKeysCache.put(sourceIdentifier, keySet);
						deviceKeysCache.put(platformId, keySet);
						deviceKeysCache.put(unitId, keySet);
					}
				}
			};

			AuthenticationLifeCycle lifecycle = authenticationService.processAuthenticationResponse(sourceIdentifier,
			        platformResponse, authenticationCallback);
			switch (lifecycle) {
			case FULLY_AUTHENTICATED:
				Object unitId = resolvePrincipalId(sourceIdentifier);
				SessionInfo session = this.sessionManager.createNewSession(unitId);
				response = prepareSuccessResponse(sourceIdentifier, session);
				break;
			case NEED_REGISTRATION:
				// TODO initiate registration process
				break;
			default:
				break;
			}

		} else {
			// Authentication failed
			response = prepareFailedResponse(sourceIdentifier);
		}

		return response;
	}

	private AuthenticationResponse doLocalAuthentication(Object sourceIdentifier) {
		AuthenticationResponse response = null;
		Object principalId = resolvePrincipalId(sourceIdentifier);
		if (principalId != null) {
			SessionInfo session = this.sessionManager.refreshSession(principalId);
			if (session != null) {
				response = prepareSuccessResponse(sourceIdentifier, session);
			}
		}
		return response;
	}

	private Object resolvePrincipalId(Object identifier) {
		if (identifier instanceof Integer) {
			return identifier;
		}
		Object unitid = null;
		Cache deviceKeysCache = getCacheProvider().getCache(MeitrackGatewayConfiguration.DEVICE_KEYS_CACHE);
		Set<?> keys = deviceKeysCache.get(identifier, Set.class);
		if (keys != null && !keys.isEmpty()) {
			for (Object key : keys) {
				if (key instanceof Integer) {
					unitid = key;
					break;
				}
			}
		}
		return unitid;
	}

	private AuthenticationRequest prepareAuthenticationRequest(String sourceIdentifier) {
		AuthenticationRequest request = new AuthenticationRequest();
		request.setSourceIdentfier(sourceIdentifier);
		request.setAuthenticationUrl(MeitrackGatewayConfiguration
		        .getProperty(MeitrackGatewayConfiguration.AUTHENTICATION_URL));
		return request;
	}

	private AuthenticationResponse prepareFailedResponse(String sourceIdentifier) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthenticationStatus(false);
		response.setAuthenticationMessage("Device[" + sourceIdentifier.toString() + "]is not authenticated");
		response.setSessionInfo(null);
		return response;
	}

	private AuthenticationResponse prepareSuccessResponse(Object sourceIdentifier, SessionInfo session) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthenticationStatus(true);
		response.setAuthenticationMessage("Device[" + sourceIdentifier.toString() + "]is authenticated");
		response.setSessionInfo(session);

		MeitrackDeviceConfiguration configuration = (MeitrackDeviceConfiguration) getConfiguration(sourceIdentifier);
		configuration.setSessionId(session.getSessionId());
		refreshDeviceConfiguration(sourceIdentifier, configuration);
		return response;
	}

	private MeitrackDeviceConfiguration getDeviceConfiguration(Object identifier) {
		validateSourceIdentifier(identifier);

		Object unitId = resolvePrincipalId(identifier);
		if (unitId == null) {
			return null;
		}
		Cache deviceConfigurationCache = getCacheProvider().getCache(
		        MeitrackGatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
		MeitrackDeviceConfiguration config = deviceConfigurationCache.get(unitId, MeitrackDeviceConfiguration.class);
		return config;
	}

	private void initializeCacheProvider() {
		CacheProviderType providertype = resolveCacheProvider();
		CacheProviderConfiguration config = buildCacheConfiguration(providertype);
		cacheProvider = CacheManager.instance().getProvider(providertype, config);

	}

	private CacheProviderConfiguration buildCacheConfiguration(CacheProviderType providertype) {
		CacheProviderConfiguration config = null;
		switch (providertype) {
		case HZ:
			config = new HazelCastConfiguration();
			String configResourcePath = MeitrackGatewayConfiguration
			        .getProperty(MeitrackGatewayConfiguration.CACHE_PROVIDER_CONFIG_PATH);
			((HazelCastConfiguration) config).setConfigResourcePath(configResourcePath);
			break;
		case InMemory:
			break;
		default:
			break;

		}
		return config;
	}

	private CacheProviderType resolveCacheProvider() {
		CacheProviderType providertype = null;
		String cacheProviderType = MeitrackGatewayConfiguration
		        .getProperty(MeitrackGatewayConfiguration.CACHE_PROVIDER);
		providertype = CacheProviderType.valueOfName(cacheProviderType);
		return providertype;
	}

	private void validateSourceIdentifier(Object sourceIdentifier) {
		if (sourceIdentifier == null || sourceIdentifier.toString().isEmpty()) {
			throw new IllegalArgumentException("Source identifier cant't be NULL or empty");
		}
	}

	public void evictDeviceConfiguration(Object unitId) {
		Cache deviceConfigurationCache = getCacheProvider().getCache(
		        MeitrackGatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
		deviceConfigurationCache.evict(unitId);
	}

	private void evictAllKeys(Object identifier) {
		Cache deviceKeysCache = getCacheProvider().getCache(MeitrackGatewayConfiguration.DEVICE_KEYS_CACHE);
		Set<?> keySet = deviceKeysCache.get(identifier, Set.class);
		for (Object key : keySet) {
			deviceKeysCache.evict(key);
		}
		deviceKeysCache.evict(identifier);
	}

	public static void main(String[] args) {
		MeitrackDeviceManager dm = MeitrackDeviceManager.instance();
		AuthenticationResponse resp = dm.authenticate("351756051523999");
		System.out.println(resp.getAuthenticationMessage());
		System.out.println(resp.getSessionInfo().toString());
	}
}

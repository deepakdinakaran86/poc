package com.pcs.device.gateway.G2021.devicemanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.auth.AuthenticationLifeCycle;
import com.pcs.device.gateway.G2021.devicemanager.auth.AuthenticationRequest;
import com.pcs.device.gateway.G2021.devicemanager.auth.AuthenticationService;
import com.pcs.device.gateway.G2021.devicemanager.auth.PostAuthenticationCallback;
import com.pcs.device.gateway.G2021.devicemanager.auth.RegistrationService;
import com.pcs.device.gateway.G2021.devicemanager.auth.RemoteAuthenticationResponse;
import com.pcs.device.gateway.G2021.devicemanager.auth.TagService;
import com.pcs.device.gateway.G2021.devicemanager.auth.api.DeviceAuthenticationService;
import com.pcs.device.gateway.G2021.devicemanager.auth.api.DeviceRegistrationService;
import com.pcs.device.gateway.G2021.devicemanager.bean.Device;
import com.pcs.device.gateway.G2021.devicemanager.bean.DeviceProtocol;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.bean.Tag;
import com.pcs.device.gateway.G2021.devicemanager.bean.Version;
import com.pcs.device.gateway.G2021.devicemanager.cache.CacheManager;
import com.pcs.device.gateway.G2021.devicemanager.cache.CacheProviderType;
import com.pcs.device.gateway.G2021.devicemanager.config.ConfigurationService;
import com.pcs.device.gateway.G2021.devicemanager.config.galaxy.GalaxyConfigurationService;
import com.pcs.device.gateway.G2021.devicemanager.datasource.publish.DatasourceService;
import com.pcs.device.gateway.G2021.devicemanager.datasource.publish.bean.DatasourceDTO;
import com.pcs.device.gateway.G2021.devicemanager.datasource.publish.bean.Parameter;
import com.pcs.device.gateway.G2021.devicemanager.device.WritebackService;
import com.pcs.device.gateway.G2021.devicemanager.device.bean.WritebackConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.device.bean.WritebackResponse;
import com.pcs.device.gateway.G2021.devicemanager.session.SessionManager;
import com.pcs.device.gateway.G2021.message.AlarmMessage;
import com.pcs.device.gateway.G2021.message.WriteResponseMessage;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.cache.CacheProviderConfiguration;
import com.pcs.deviceframework.cache.hazelcast.HazelCastConfiguration;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.devicemanager.DeviceManager;
import com.pcs.deviceframework.devicemanager.authentication.AuthenticationResponse;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;
import com.pcs.deviceframework.devicemanager.session.SessionInfo;

/**
 * Device - Manager implementation for G2021
 * 
 * @author pcseg310 (Rakesh)
 * 
 */
public final class G2021DeviceManager extends DeviceManager {

	private static final String SUCCESS = "SUCCESS";
	private static final String STATUS = "status";
	private static final G2021DeviceManager instance = new G2021DeviceManager();
	private static final Logger LOGGER = LoggerFactory.getLogger(G2021DeviceManager.class);

	private CacheProvider cacheProvider;
	private SessionManager sessionManager;

	private G2021DeviceManager() {
		this.setCacheProvider();
		sessionManager = new SessionManager(getCacheProvider());
	}

	/**
	 * Static factory method to return the singleton instance
	 * 
	 * @return
	 */
	public static final G2021DeviceManager instance() {
		return instance;
	}

	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * Retrieves the cache provider managed by the device manager.
	 * 
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#getCacheProvider()
	 */
	@Override
	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}

	/**
	 * Retrieves the point configurations of an authenticated device.
	 * 
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#getConfiguration(java.lang.Object)
	 */
	@Override
	public DeviceConfiguration getConfiguration(Object sourceId) {
		validateSourceIdentifier(sourceId);

		G2021DeviceConfiguration g2021Config = getDeviceConfiguration(sourceId);
		return g2021Config;
	}

	/**
	 * Retrieves {@link G2021DeviceConfiguration} for an authenticated device
	 * from cache.
	 * 
	 * @param identifier
	 * @return
	 */
	private G2021DeviceConfiguration getDeviceConfiguration(Object identifier) {
		validateSourceIdentifier(identifier);

		Object unitId = resolvePrincipalId(identifier);
		if (unitId == null) {
			return null;
		}
		Cache deviceConfigurationCache = getCacheProvider().getCache(
				G2021GatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
		G2021DeviceConfiguration config = deviceConfigurationCache.get(unitId,G2021DeviceConfiguration.class);
		if (config == null) {
			AuthenticationResponse authenticationResponse = authenticate(unitId);
			if( authenticationResponse.isAuthenticated()){
				config = deviceConfigurationCache.get(unitId,G2021DeviceConfiguration.class);
			}else{
				LOGGER.error("Unidentified device seeking connection ::"+identifier.toString());
			}
			
		}
		return config;
	}

	/**
	 * Retrieves {@link G2021DeviceConfiguration} for an authenticated device
	 * from cache.
	 * 
	 * @param identifier
	 * @return
	 */
	public void setDeviceConfiguration(Object identifier,
			G2021DeviceConfiguration configuration) {
		validateSourceIdentifier(identifier);
		refreshDeviceConfiguration(identifier, configuration);

		ConfigurationService service = new GalaxyConfigurationService();
		service.updateConfiguration(configuration);
		
		G2021DeviceManager.instance().getCacheProvider().getCache("datasource.cache.device.config").clear();
		G2021DeviceManager.instance().getCacheProvider().getCache("datasource.cache.device").clear();
		LOGGER.info("Device write back cache evicted!!");
		
		registerDatasource(Integer.parseInt(identifier.toString()), configuration);
	}

	/**
	 * Retrieves {@link G2021DeviceConfiguration} for an authenticated device
	 * from cache.
	 * 
	 * @param identifier
	 * @return
	 */
	public void refreshDeviceConfiguration(Object identifier,
			G2021DeviceConfiguration configuration) {
		validateSourceIdentifier(identifier);

		Object unitId = resolvePrincipalId(identifier);
		if (unitId == null) {
			return;
		}
		Cache deviceConfigurationCache = getCacheProvider().getCache(
				G2021GatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
		deviceConfigurationCache.put(unitId, configuration);
	}

	/**
	 * Authenticates the source.It will
	 * 
	 * @throws IllegalArgumentException
	 *             if source identifier is null or empty
	 * 
	 * @param sourceIdentifier
	 */
	@Override
	public AuthenticationResponse authenticate(Object source) {
		String sourceIdentifier = source.toString();
		validateSourceIdentifier(sourceIdentifier);

		AuthenticationResponse response = null;
		response = doLocalAuthentication(sourceIdentifier);

		if (response == null) {
			response = doRemoteAuthentication(sourceIdentifier);
		}

		return response;
	}

	/**
	 * 
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#getSessionByPrincipal(java.lang.Object)
	 */
	@Override
	public SessionInfo getSessionByPrincipal(Object principal) {
		if (principal == null) {
			throw new IllegalArgumentException(
					"Principal id should not be null");
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
			throw new IllegalArgumentException(
					"Principal id should not be null");
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
	 * Validates the source identifier.
	 * 
	 * @throws IllegalArgumentException
	 *             if source identifier is null or empty
	 * 
	 * @param sourceIdentifier
	 */
	private void validateSourceIdentifier(Object sourceIdentifier) {
		if (sourceIdentifier == null || sourceIdentifier.toString().isEmpty()) {
			throw new IllegalArgumentException(
					"Source identifier cant't be NULL or empty");
		}
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

		// Currently only galaxy platform authentication service
		AuthenticationService authenticationService = new DeviceAuthenticationService();
		RemoteAuthenticationResponse platformResponse = authenticationService.remoteAuthenticate(request);

		if (platformResponse != null) {
			// Authenticated
			PostAuthenticationCallback authenticationCallback = new PostAuthenticationCallback() {
				@Override
				public void firePostAuthentication(
						G2021DeviceConfiguration config) {
					Cache deviceConfigurationCache = getCacheProvider()
							.getCache(
									G2021GatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
					Cache deviceKeysCache = getCacheProvider().getCache(
							G2021GatewayConfiguration.DEVICE_KEYS_CACHE);
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

			AuthenticationLifeCycle lifecycle = authenticationService
					.processAuthenticationResponse(sourceIdentifier,
							platformResponse, authenticationCallback);
			switch (lifecycle) {
			case AUTHENTICATED:
				Object unitId = resolvePrincipalId(sourceIdentifier);
				SessionInfo session = this.sessionManager
						.createNewSession(unitId);
				response = prepareSuccessResponse(sourceIdentifier, session);
				break;
			case NEED_REGISTRATION:
				registerDevice(sourceIdentifier);
				response = prepareFailedResponse(sourceIdentifier);
				break;
			case NOT_AUTHENTICATED:
				response = prepareFailedResponse(sourceIdentifier);
			default:
				break;
			}

		} else {
			// Authentication failed
			response = prepareFailedResponse(sourceIdentifier);
		}

		return response;
	}

	public boolean registerDevice(final String sourceIdentifier) {
		Device device = populateDevice(sourceIdentifier);
		RegistrationService registrationService = new DeviceRegistrationService();
		boolean isRegistered = registrationService.register(device);
		return isRegistered;
	}

	private Device populateDevice(String sourceIdentifier) {
		Device device = new Device();
		device.setSourceId(sourceIdentifier);
		
		Version version = new Version();
		version.setDeviceType("Edge Device");
		version.setMake(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DEVICE_TYPE_VENDOR));
		version.setModel(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DEVICE_TYPE_MODEL));
		version.setProtocol("EDCP");
		version.setVersion("1.0.0");
		device.setVersion(version);
		
		DeviceProtocol protocol = new DeviceProtocol();
		// TODO remove this once it became optional in API
		protocol.setName("TCP");
		device.setNetworkProtocol(protocol);
		
		device.setIsPublishing(Boolean.FALSE);
		
		device.setConnectedPort(Integer.valueOf(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_CONTROL_SERVER_PORT)));
		device.setIp(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_IP));
		device.setWriteBackPort(Integer.valueOf(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_COMMAND_PORT)));
		return device;
	}
	/**
	 * Do an authentication locally.
	 * 
	 * @param sourceIdentifier
	 * @return
	 */
	private AuthenticationResponse doLocalAuthentication(Object sourceIdentifier) {
		AuthenticationResponse response = null;
		Object unitId = resolvePrincipalId(sourceIdentifier);
		if (unitId != null) {
			SessionInfo session = this.sessionManager.refreshSession(unitId);
			if (session != null) {
				response = prepareSuccessResponse(sourceIdentifier, session);
			}
		}
		return response;
	}

	/**
	 * @param unitId
	 * @return
	 */

	private AuthenticationRequest prepareAuthenticationRequest(
			String sourceIdentifier) {
		AuthenticationRequest request = new AuthenticationRequest();
		request.setSourceIdentfier(sourceIdentifier);
		request.setAuthenticationUrl(G2021GatewayConfiguration
				.getProperty(G2021GatewayConfiguration.AUTHENTICATION_URL));
		return request;
	}

	private AuthenticationResponse prepareFailedResponse(String sourceIdentifier) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthenticationStatus(false);
		response.setAuthenticationMessage("Device[" + sourceIdentifier
				+ "]is not authenticated by the remote platform");
		response.setSessionInfo(null);
		return response;
	}

	private AuthenticationResponse prepareSuccessResponse(
			Object sourceIdentifier, SessionInfo session) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthenticationStatus(true);
		response.setAuthenticationMessage("Device["
				+ sourceIdentifier.toString()
				+ "]is authenticated by the remote platform");
		response.setSessionInfo(session);
		G2021DeviceConfiguration configuration = (G2021DeviceConfiguration) getConfiguration(sourceIdentifier);
		configuration.setSessionId(session.getSessionId());
		refreshDeviceConfiguration(sourceIdentifier, configuration);
		return response;
	}

	private void setCacheProvider() {
		CacheProviderType providertype = resolveCacheProvider();
		CacheProviderConfiguration config = buildCacheConfiguration(providertype);
		cacheProvider = CacheManager.instance().getProvider(providertype,
				config);
	}

	private CacheProviderConfiguration buildCacheConfiguration(
			CacheProviderType providertype) {
		CacheProviderConfiguration config = null;
		switch (providertype) {
		case HZ:
			config = new HazelCastConfiguration();
			String configResourcePath = G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.CACHE_PROVIDER_CONFIG_PATH);
			((HazelCastConfiguration) config)
					.setConfigResourcePath(configResourcePath);
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
		String cacheProviderType = G2021GatewayConfiguration
				.getProperty(G2021GatewayConfiguration.CACHE_PROVIDER);
		providertype = CacheProviderType.valueOfName(cacheProviderType);
		return providertype;
	}

	/**
	 * Resolves principal id from the given identifier.
	 * 
	 * @param identifier
	 * @return
	 */
	private Object resolvePrincipalId(Object identifier) {
		if (identifier instanceof Integer) {
			return identifier;
		}
		Object unitId = null;
		Cache deviceKeysCache = getCacheProvider().getCache(
				G2021GatewayConfiguration.DEVICE_KEYS_CACHE);
		Set<?> keys = deviceKeysCache.get(identifier, Set.class);
		if (keys != null && !keys.isEmpty()) {
			for (Object key : keys) {
				if (key instanceof Integer) {
					unitId = key;
					break;
				}
			}
		}
		return unitId;
	}

	public void evictDeviceConfiguration(Object unitId) {
		Cache deviceConfigurationCache = getCacheProvider().getCache(
				G2021GatewayConfiguration.DEVICE_CONFIGURATION_CACHE);
		deviceConfigurationCache.evict(unitId);
	}

	private void evictAllKeys(Object identifier) {
		Cache deviceKeysCache = getCacheProvider().getCache(
				G2021GatewayConfiguration.DEVICE_KEYS_CACHE);
		Set<?> keySet = deviceKeysCache.get(identifier, Set.class);
		for (Object key : keySet) {
			deviceKeysCache.evict(key);
		}
		deviceKeysCache.evict(identifier);
	}

	
	
	/**
	 * @param unitId
	 * @param deviceConfiguration
	 * @return 
	 */
	public boolean registerDatasource(Integer unitId,G2021DeviceConfiguration deviceConfiguration) {
		boolean registered = false;
		DatasourceDTO datasourceDTO = new DatasourceDTO();
		datasourceDTO.setDatasourceName(deviceConfiguration.getDevice().getDatasourceName() == null ? deviceConfiguration.getDevice().getDeviceId():
										deviceConfiguration.getDevice().getDatasourceName());
		for (Point point : deviceConfiguration.getPointConfigurations()) {
			Parameter parameter = new Parameter(point.getCustomTag(),
					point.getDataType());
			datasourceDTO.addParameter(parameter);
		}

		//operation is not required as the device gateway would never be required to create a new datasource name
		
		DatasourceService datasourceService = new DatasourceService();
		JsonNode updateResponse = datasourceService.update(datasourceDTO);
		if(updateResponse.get(STATUS) != null && updateResponse.get(STATUS).asText().equalsIgnoreCase(SUCCESS)){
			LOGGER.info("Datasource updated :: "+datasourceDTO.getDatasourceName());
		}else{
			LOGGER.error("Could not update the datasource :: "+datasourceDTO.getDatasourceName());
		}
		
		datasourceService = null;
		refreshDeviceConfiguration(unitId, deviceConfiguration);
		return registered;
	}

	/**
	 * @param message
	 * @param deviceConfiguration
	 */
	public void publishData(Message message,
			G2021DeviceConfiguration deviceConfiguration) {
		LOGGER.info("Publishing to data source : "+deviceConfiguration.getDevice().getDatasourceName());
		
		if(deviceConfiguration.getDevice().getIsPublishing()){
			if(deviceConfiguration.getDevice().getDatasourceName() == null){
				
				LOGGER.error("No data source set for the device !! cannot publish !! :: "+deviceConfiguration.getDevice().getDeviceId());
				//LOGGER.info("Registering datasource for device ::"+deviceConfiguration.getDevice().getDeviceId());
				
				if(registerDatasource(deviceConfiguration.getUnitId(),deviceConfiguration)){
					DatasourceDTO datasourceDTO = new DatasourceDTO();
					datasourceDTO.setDatasourceName(deviceConfiguration.getDevice().getDatasourceName());
					for (Point point : message.getPoints()) {
						Parameter parameter = new Parameter(point.getCustomTag(),
								point.getDataType());
						parameter.setValue(point.getData());
						parameter.setTime(message.getTime());
						datasourceDTO.addParameter(parameter);
					}
					DatasourceService datasourceService = new DatasourceService();
					datasourceService.publish(datasourceDTO);
					datasourceService = null;
				}
			}else{
				DatasourceDTO datasourceDTO = new DatasourceDTO();
				datasourceDTO.setDatasourceName(deviceConfiguration.getDevice().getDatasourceName());
				for (Point point : message.getPoints()) {
					Parameter parameter = new Parameter(point.getCustomTag(),
							point.getDataType());
					parameter.setValue(point.getData());
					parameter.setTime(message.getTime());
					datasourceDTO.addParameter(parameter);
				}
				DatasourceService datasourceService = new DatasourceService();
				datasourceService.publish(datasourceDTO);
				datasourceService = null;
			}
		}else{
			LOGGER.error("Not configured to publish!!");
		}
		
	}

	/**
	 * @param message
	 * @param deviceConfiguration
	 */
	public void publishAlarm(Message message,
			G2021DeviceConfiguration deviceConfiguration) {
		
		if(deviceConfiguration.getDevice().getIsPublishing()){
			if(deviceConfiguration.getDevice().getDatasourceName() != null){
				
				AlarmMessage alarmMessage = (AlarmMessage) message;
				DatasourceDTO datasourceDTO = new DatasourceDTO();
				datasourceDTO.setDatasourceName(deviceConfiguration.getDevice().getDatasourceName());
				Parameter parameter = new Parameter(alarmMessage.getCustomTag(),
						alarmMessage.getAlarmType());
				parameter.setValue(alarmMessage.getStatusMessage());
				alarmMessage.getStatusMessage();
				datasourceDTO.addParameter(parameter);
				//DatasourceService datasourceService = new DatasourceService();
				//datasourceService.publish(datasourceDTO);
				datasourceDTO = null;
				alarmMessage = null;
				parameter = null;
				//datasourceService = null;
			}else{
				LOGGER.error("No data source set for the device !! cannot publish !! :: "+deviceConfiguration.getDevice().getDeviceId());
				//LOGGER.info("Registering datasource for device ::"+deviceConfiguration.getDevice().getDeviceId());
				
				if(registerDatasource(deviceConfiguration.getUnitId(),deviceConfiguration)){
					DatasourceDTO datasourceDTO = new DatasourceDTO();
					datasourceDTO.setDatasourceName(deviceConfiguration.getDevice().getDatasourceName());
					for (Point point : message.getPoints()) {
						Parameter parameter = new Parameter(point.getCustomTag(),
								point.getDataType());
						parameter.setValue(point.getData());
						parameter.setTime(message.getTime());
						datasourceDTO.addParameter(parameter);
					}
					//DatasourceService datasourceService = new DatasourceService();
					//datasourceService.publish(datasourceDTO);
					//datasourceService = null;
				}
			}
		}else{
			LOGGER.error("Not configured to publish!!");
		}
	}

	
	public void sendWriteResponse(WriteResponseMessage writeResponse ) {
		try {
			WritebackService writebackService = new WritebackService();
			WritebackResponse writebackResponse = new WritebackResponse();
			writebackResponse.setRemarks(writeResponse.getStatus().name());
			writebackResponse.setRequestId(writeResponse.getReqId());
			//writebackResponse.setSourceId(((G2021DeviceConfiguration)getConfiguration(writeResponse.getSourceId())).getDevice().getSourceId());//TODO
			writebackResponse.setSourceId(writeResponse.getSourceId().toString());
			writebackResponse.setStatus(writeResponse.getStatus().getStatusIndicator());
			writebackResponse.setTime(writeResponse.getTime());
			writebackService.updateWritebackResponse(writebackResponse);
		} catch (Exception e) {
			LOGGER.error("Exception updating write response!!",e);
		}
	}
	
	public void updateDeviceWritebackConfig(WritebackConfiguration writebackConfiguration ) {
		try {
			WritebackService writebackService = new WritebackService();
			writebackService.updateConfiguration(writebackConfiguration);
			//Updating the cache.
			G2021DeviceConfiguration configuration = (G2021DeviceConfiguration) getConfiguration(writebackConfiguration.getSourceId());
			configuration.getDevice().setIp(writebackConfiguration.getIp());
			configuration.getDevice().setWriteBackPort(writebackConfiguration.getWritebackPort());
			configuration.getDevice().setConnectedPort(writebackConfiguration.getConnectedPort());
			refreshDeviceConfiguration(writebackConfiguration.getSourceId(), configuration);
			configuration = null;
		} catch (Exception e) {
			LOGGER.error("Exception updating write response!!",e);
		}
	}
	
	public void updateDeviceTags(List<String> tags, Object sourceId) {
		if (CollectionUtils.isEmpty(tags)) {
			return;
		}

		try {
			TagService service = new TagService();
			List<Tag> tagList = new ArrayList<Tag>();
			for (String tagStr : tags) {
				if (StringUtils.isNotBlank(tagStr)) {
					Tag t = new Tag();
					t.setName(tagStr);
					tagList.add(t);
				}
			}
			service.updateTags(tagList, sourceId.toString());
		} catch (Exception e) {
			LOGGER.error("Exception updating the tags!!", e);
		}
	}
	
	public static void main(String[] args) {
		
		G2021DeviceManager.instance().authenticate("SourceId_00003");
	}
}

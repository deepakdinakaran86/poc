package com.pcs.saffron.manager.provider;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kafka.producer.KeyedMessage;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cipher.data.generic.message.GenericEvent;
import com.pcs.saffron.cipher.data.generic.message.GenericMessage;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.message.status.MessageCompletion;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.api.configuration.ConfigurationService;
import com.pcs.saffron.manager.api.configuration.bean.ConfigurationSearch;
import com.pcs.saffron.manager.api.configuration.bean.StatusMessage;
import com.pcs.saffron.manager.api.datasource.DatasourceService;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.Parameter;
import com.pcs.saffron.manager.api.devicetype.DeviceTypeService;
import com.pcs.saffron.manager.api.writeback.WritebackService;
import com.pcs.saffron.manager.authentication.AuthenticationLifeCycle;
import com.pcs.saffron.manager.authentication.AuthenticationRequest;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.authentication.AuthenticationService;
import com.pcs.saffron.manager.authentication.PostAuthenticationCallback;
import com.pcs.saffron.manager.authentication.RemoteAuthenticationResponse;
import com.pcs.saffron.manager.authentication.api.DeviceAuthenticationService;
import com.pcs.saffron.manager.autoclaim.AutoClaimService;
import com.pcs.saffron.manager.bean.ActivityMessage;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DataIngestionBean;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;
import com.pcs.saffron.manager.bean.DeviceConfiguration;
import com.pcs.saffron.manager.bean.DeviceProtocol;
import com.pcs.saffron.manager.bean.DeviceProtocolType;
import com.pcs.saffron.manager.bean.GenericIngestionType;
import com.pcs.saffron.manager.bean.Version;
import com.pcs.saffron.manager.bean.WriteResponseMessage;
import com.pcs.saffron.manager.bean.WritebackConfiguration;
import com.pcs.saffron.manager.bean.WritebackResponse;
import com.pcs.saffron.manager.bundle.util.CacheUtil;
import com.pcs.saffron.manager.bundle.util.MessageUtil;
import com.pcs.saffron.manager.config.ManagerConfiguration;
import com.pcs.saffron.manager.registration.bean.DeviceRegistrationData;
import com.pcs.saffron.manager.session.SessionInfo;
import com.pcs.saffron.manager.session.SessionManager;
import com.pcs.saffron.manager.util.CEPMessageConverter;
import com.pcs.saffron.manager.util.GenericMessageIngestionUtil;
import com.pcs.saffron.manager.util.MessageIngestionUtil;
import com.pcs.saffron.manager.writeback.listener.CommandListener;
import com.pcs.saffron.manager.writeback.notifier.CommandNotifier;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;
import com.pcs.saffron.notification.enums.DistributorMode;

/**
 * Device - Manager implementation for G2021
 * 
 * @author pcseg310 (Rakesh)
 * 
 */
public final class EOSDeviceManager extends DeviceManager {

	private static final String SUCCESS = "SUCCESS";
	private static final String STATUS = "status";
	private static final EOSDeviceManager instance = new EOSDeviceManager();
	private static final Logger LOGGER = LoggerFactory.getLogger(EOSDeviceManager.class);

	private SessionManager sessionManager;

	private EOSDeviceManager() {
		sessionManager = new SessionManager(getCacheProvider());
	}

	/**
	 * Static factory method to return the singleton instance
	 * 
	 * @return
	 */
	public static final EOSDeviceManager instance() {
		return instance;
	}

	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}


	/**
	 * Retrieves the point configurations of an authenticated device.
	 * 
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#getConfiguration(java.lang.Object)
	 */
	@Override
	public DeviceConfiguration getConfiguration(Object sourceId,Gateway gateway) {
		validateSourceIdentifier(sourceId);

		DefaultConfiguration g2021Config = getDeviceConfiguration(sourceId,gateway);
		return g2021Config;
	}

	/**
	 * Retrieves {@link DefaultConfiguration} for an authenticated device
	 * from cache.
	 * 
	 * @param identifier
	 * @param gateway 
	 * @return
	 */
	private DefaultConfiguration getDeviceConfiguration(Object identifier, Gateway gateway) {
		validateSourceIdentifier(identifier);

		Object unitId = resolvePrincipalId(identifier,gateway);
		if (unitId == null) {
			return null;
		}

		Cache deviceConfigurationCache = getCacheProvider().getCache(
				getDeviceConfigurationCache(gateway));
		DefaultConfiguration config = deviceConfigurationCache.get(unitId,DefaultConfiguration.class);
		if (config == null && unitId != null){ //ensuring identity is the unit id 
			Device device = new Device();
			device.setUnitId((Integer) identifier);
			AuthenticationResponse authenticationResponse = authenticate(device,gateway);
			if( authenticationResponse.isAuthenticated()){
				config = deviceConfigurationCache.get(identifier,DefaultConfiguration.class);
			}else{
				LOGGER.error("Unidentified device seeking connection ::"+identifier.toString());
			}
			device = null;
		}
		return config;
	}

	/**
	 * Retrieves {@link DefaultConfiguration} for an authenticated device
	 * from cache.
	 * 
	 * @param identifier
	 * @return
	 */
	public void setDeviceConfiguration(DefaultConfiguration configuration,Gateway gateway) {
		validateSourceIdentifier(configuration.getUnitId());

		/*
		 * Add latitude and longitude to all devices TODO fix this with proper thought process
		 */
		ConfigPoint latitude =  null;
		ConfigPoint longitude = null;
		ConfigPoint geoPoint = null;
		try {

			latitude =  new ConfigPoint();
			latitude.setDataType(PointDataTypes.LATITUDE.name());
			latitude.setPointId("Latitude");
			latitude.setPointName("Latitude");
			latitude.setDisplayName("Latitude");
			latitude.setPrecedence("1");
			latitude.setUnit(null);
			latitude.setType(PointDataTypes.LATITUDE.name());
			if(configuration.getConfigPoints().indexOf(latitude)<0){
				configuration.getConfigPoints().add(latitude);
			}else{
				LOGGER.info("Latitude Exists");
			}

			longitude = new ConfigPoint();
			longitude.setDataType(PointDataTypes.LONGITUDE.name());
			longitude.setDisplayName("Longitude");
			longitude.setPointId("Longitude");
			longitude.setPointName("Longitude");
			longitude.setUnit(null);
			longitude.setType(PointDataTypes.LONGITUDE.name());
			longitude.setPrecedence("1");
			if(configuration.getConfigPoints().indexOf(longitude)<0){
				configuration.getConfigPoints().add(longitude);
			}else{
				LOGGER.info("Longitude Exists");
			}
			
			geoPoint = new ConfigPoint();
			geoPoint.setDataType(PointDataTypes.GEOPOINT.name());
			geoPoint.setDisplayName("Location");
			geoPoint.setPointId("Location");
			geoPoint.setPointName("Location");
			geoPoint.setUnit(null);
			geoPoint.setType(PointDataTypes.GEOPOINT.name());
			geoPoint.setPrecedence("1");
			if(configuration.getConfigPoints().indexOf(geoPoint)<0){
				configuration.getConfigPoints().add(geoPoint);
			}else{
				LOGGER.info("Location Exists");
			}
		} catch (Exception e) {
			LOGGER.error("Error adding location latitude and longitude !!",e);
		}

		/*
		 * Add latitude and longitude to all devices TODO fix this with proper thought process
		 */

		LOGGER.info("Configuring device for the first time");
		handleConfigurationChange(configuration, gateway, latitude,longitude,geoPoint);

	}

	private void handleConfigurationChange(DefaultConfiguration configuration,
			Gateway gateway, ConfigPoint latitude, ConfigPoint longitude, ConfigPoint geopoint) {

		AutoClaimService autoClaimService = new AutoClaimService();
		autoClaimService.claimAsset(configuration);			
		LOGGER.info("Map points completed");

		if(latitude != null && longitude != null && geopoint != null){
			Message message = new Message();
			message.setReceivedTime(Calendar.getInstance().getTime());
			message.setTime(Calendar.getInstance().getTimeInMillis());
			message.setSourceId(configuration.getDevice().getSourceId());
			message.setTime(System.currentTimeMillis());
			try {
				latitude.setData(configuration.getDevice().getLatitude());
				longitude.setData(configuration.getDevice().getLongitude());
				Float[] location = {configuration.getDevice().getLatitude(),configuration.getDevice().getLongitude()};
				geopoint.setData(location);
				message.addPoint(geopoint);
				message.addPoint(longitude);
				message.addPoint(latitude);
				
				notifyMessage(message, configuration, MessageCompletion.COMPLETE, gateway);
			} catch (Exception e) {
				LOGGER.error("Error setting latitude and longitude",e);
			}
		}

		ConfigurationService service = new ConfigurationService();
		service.updateConfiguration(configuration);

		if(configuration.getDevice().getIsPublishing())
			registerDatasource(configuration,gateway);

		refreshDeviceConfiguration(configuration,gateway);
	}


	@SuppressWarnings("unchecked")
	public HashMap<String,Point> getPointsByProtocolAndDeviceType(Gateway gateway) {

		Cache protocolPoints = getCacheProvider().getCache(getProtocolPointsCache(gateway));
		HashMap<String, Point> pointMap = new HashMap<String,Point>();
		ConfigurationSearch configurationSearch = new ConfigurationSearch();
		configurationSearch.setDeviceType(gateway.getType());
		configurationSearch.setMake(gateway.getVendor());
		configurationSearch.setModel(gateway.getModel());
		configurationSearch.setProtocol(gateway.getProtocol());
		configurationSearch.setVersion(gateway.getVersion());
		if(protocolPoints != null){
			DeviceProtocolType deviceProtocolType = new DeviceProtocolType(gateway.getProtocol(),gateway.getType());
			pointMap = protocolPoints.get(deviceProtocolType, HashMap.class);
			if( !CollectionUtils.isEmpty(pointMap) ){
				return pointMap;
			}else{
				DeviceTypeService deviceTypeService = new DeviceTypeService();
				pointMap = deviceTypeService.getProtocolPoints(configurationSearch);
				protocolPoints.put(deviceProtocolType, pointMap);
			}
		}
		return pointMap;
	}



	/**
	 * Retrieves {@link DefaultConfiguration} for an authenticated device
	 * from cache.
	 * 
	 * @param identifier
	 * @return
	 */
	public void refreshDeviceConfiguration(
			DefaultConfiguration configuration,Gateway gateway) {
		validateSourceIdentifier(configuration.getUnitId());

		Object unitId = resolvePrincipalId(configuration.getUnitId(),gateway);
		if (unitId == null) {
			LOGGER.info("Cannot find unit id to refresh",configuration.getUnitId());
			return;
		}
		Cache deviceConfigurationCache = getCacheProvider().getCache(
				getDeviceConfigurationCache(gateway));
		LOGGER.info("Updating device configuration on cache for {} with {} points",unitId,configuration.getConfigPoints().size());
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
	public AuthenticationResponse authenticate(Device device,Gateway gateway) {
		AuthenticationResponse response = null;
		try {
			Version version = new Version();
			BeanUtilsBean.getInstance().copyProperties(version, gateway);
			version.setDeviceType(gateway.getType());
			version.setMake(gateway.getVendor());
			device.setVersion(version);
			Object sourceIdentifier = null;

			if(device.getSourceId() != null){
				sourceIdentifier = device.getSourceId().toString();
				validateSourceIdentifier(sourceIdentifier);
			}else{
				sourceIdentifier = device.getUnitId();
			}
			response = doLocalAuthentication(sourceIdentifier,gateway);
			if (response == null) {
				response = doRemoteAuthentication(device,gateway);
			}

		} catch (Exception e) {
			LOGGER.error("Error authenticating device",e);
		}


		return response;
	}

	/**
	 * 
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#getSessionByPrincipal(java.lang.Object)
	 */
	@Override
	public SessionInfo getSessionByPrincipal(Object principal,Gateway gateway) {
		if (principal == null) {
			throw new IllegalArgumentException(
					"Principal id should not be null");
		}
		Object principalId = resolvePrincipalId(principal,gateway);
		return this.sessionManager.getSessionByPrincipal(principalId,gateway);
	}

	/**
	 * 
	 * @see com.pcs.deviceframework.devicemanager.DeviceManager#invalidateSession(java.lang.Object)
	 */
	@Override
	public boolean invalidateSession(Object principal,Gateway gateway) {
		if (principal == null) {
			throw new IllegalArgumentException(
					"Principal id should not be null");
		}
		boolean invalidated = false;
		Object principalId = resolvePrincipalId(principal,gateway);
		this.sessionManager.invalidateSession(principalId,gateway);
		evictDeviceConfiguration(principal,gateway);
		evictAllKeys(principal,gateway);
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
	 * @param device
	 * @param gateway 
	 * @return
	 */
	private AuthenticationResponse doRemoteAuthentication(final Device device, Gateway gateway) {

		AuthenticationResponse response = null;
		AuthenticationRequest request = new AuthenticationRequest();
		request.setDevice(device);
		request.setAuthenticationUrl(ManagerConfiguration.getProperty(ManagerConfiguration.AUTHENTICATION_URL));

		// Currently only galaxy platform authentication service
		AuthenticationService authenticationService = new DeviceAuthenticationService();
		RemoteAuthenticationResponse platformResponse = authenticationService.remoteAuthenticate(request);

		final String configCacheId = getDeviceConfigurationCache(gateway);
		final String keysCacheId = getDeviceKeyCache(gateway);

		if (platformResponse != null) {
			// Authenticated
			PostAuthenticationCallback authenticationCallback = new PostAuthenticationCallback() {
				public void firePostAuthentication(
						DefaultConfiguration config) {
					Cache deviceConfigurationCache = getCacheProvider()
							.getCache(configCacheId);
					Cache deviceKeysCache = getCacheProvider().getCache(
							keysCacheId);
					if (config != null) {
						Integer unitId = config.getUnitId();
						String platformId = config.getDevice().getDeviceId();

						// Cache the device configuration against unit id
						deviceConfigurationCache.put(unitId, config);

						Set<Object> keySet = new HashSet<Object>();
						keySet.add(platformId);
						keySet.add(unitId);
						keySet.add(device.getSourceId());

						// Cache the different keys against each key for this
						// device.
						deviceKeysCache.put(device.getSourceId(), keySet);
						deviceKeysCache.put(platformId, keySet);
						deviceKeysCache.put(unitId, keySet);
					}
				}

			};

			AuthenticationLifeCycle lifecycle = authenticationService
					.processAuthenticationResponse(device.getSourceId(),
							platformResponse, authenticationCallback);
			switch (lifecycle) {
			case AUTHENTICATED:
				Object unitId = resolvePrincipalId(device.getSourceId(),gateway);
				SessionInfo session = this.sessionManager
						.createNewSession(unitId,gateway);
				response = prepareSuccessResponse(device.getSourceId(), session,gateway);
				break;
			case NEED_REGISTRATION:
				registerDevice(device,gateway);
				response = prepareFailedResponse(device.getSourceId());
				break;
			case NOT_AUTHENTICATED:
				response = prepareFailedResponse(device.getSourceId());
			default:
				break;
			}

		} else {
			// Authentication failed
			response = prepareFailedResponse(device.getSourceId());
		}

		return response;
	}

	public boolean registerDevice(final Device device, Gateway gateway) {
		LOGGER.info("Registering device with source id{} and of type Gateway {}",device.getSourceId(),gateway.getDeviceIP());

		/* populateDevice(device,gateway) */

		Version version = new Version();
		version.setDeviceType(gateway.getType());
		version.setMake(gateway.getVendor());
		version.setModel(gateway.getModel());
		version.setProtocol(gateway.getProtocol());
		version.setVersion(gateway.getVersion());
		device.setVersion(version);

		DeviceProtocol protocol = new DeviceProtocol();
		// TODO remove this once it become optional in API
		protocol.setName(gateway.getCommunicationProtocol());
		device.setNetworkProtocol(protocol);

		device.setIsPublishing(Boolean.TRUE);
		device.setDeviceName(device.getSourceId());
		device.setDatasourceName(device.getSourceId());
		device.setConnectedPort(gateway.getConnectedPort());
		device.setIp(gateway.getDeviceIP());
		device.setWriteBackPort(gateway.getWritebackPort());

		/* populateDevice(device,gateway) */

		/*
		 * Populate registeration object
		 */

		DeviceRegistrationData deviceRegistrationData = new DeviceRegistrationData();
		deviceRegistrationData.setAutoclaimEnabled(gateway.getAutoclaimEnabled());
		deviceRegistrationData.setDevice(device);
		if(!CollectionUtils.isEmpty(device.getTags())){
			deviceRegistrationData.setSuperTenant(device.getTags().get(0)!=null?device.getTags().get(0).getName():null);
			deviceRegistrationData.setParentTenant(device.getTags().get(1)!=null?device.getTags().get(1).getName():null);
			deviceRegistrationData.setOwnerTenant(device.getTags().get(2)!=null?device.getTags().get(2).getName():null);
		}

		try {
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);

			List<Serializable> messages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> deviceRegistrationRequest = null;
			ObjectMapper mapper = new ObjectMapper();
			deviceRegistrationRequest = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_REGISTRATION_STREAM),mapper.writeValueAsString(deviceRegistrationData));
			messages.add(deviceRegistrationRequest);

			publisher.publish(messages);

		} catch (Exception e) {
			LOGGER.error("Error filing registration request",e);
			return false;
		}
		//		RegistrationService registrationService = new DeviceRegistrationService();
		//		boolean isRegistered = registrationService.register(device);
		return true;
	}


	/**
	 * Do an authentication locally.
	 * 
	 * @param sourceIdentifier
	 * @param gateway 
	 * @return
	 */
	private AuthenticationResponse doLocalAuthentication(Object sourceIdentifier, Gateway gateway) {
		AuthenticationResponse response = null;
		Object unitId = resolvePrincipalId(sourceIdentifier,gateway);
		if (unitId != null) {
			SessionInfo session = this.sessionManager.refreshSession(unitId,gateway);
			if (session != null) {
				response = prepareSuccessResponse(sourceIdentifier, session,gateway);
			}
		}
		return response;
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
			Object sourceIdentifier, SessionInfo session, Gateway gateway) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthenticationStatus(true);
		response.setAuthenticationMessage("Device["
				+ sourceIdentifier.toString()
				+ "]is authenticated by the remote platform");
		response.setSessionInfo(session);
		DefaultConfiguration configuration = (DefaultConfiguration) getConfiguration(sourceIdentifier,gateway);
		configuration.setSessionId(session.getSessionId());
		refreshDeviceConfiguration(configuration,gateway);
		return response;
	}




	/**
	 * Resolves principal id from the given identifier.
	 * 
	 * @param identifier
	 * @return
	 */
	private Object resolvePrincipalId(Object identifier,Gateway gateway) {
		if (identifier instanceof Integer) {
			return identifier;
		}

		Object unitId = null;
		Cache deviceKeysCache = getCacheProvider().getCache(
				getDeviceKeyCache(gateway));
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

	public void evictDeviceConfiguration(Object unitId,Gateway gateway) {
		Cache deviceConfigurationCache = getCacheProvider().getCache(
				getDeviceConfigurationCache(gateway));
		deviceConfigurationCache.evict(unitId);
	}

	private void evictAllKeys(Object identifier,Gateway gateway) {
		Cache deviceKeysCache = getCacheProvider().getCache(
				getDeviceKeyCache(gateway));
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
	public boolean registerDatasource(DefaultConfiguration deviceConfiguration,Gateway gateway) {
		boolean registered = false;
		DatasourceDTO datasourceDTO = new DatasourceDTO();
		datasourceDTO.setDatasourceName(deviceConfiguration.getDevice().getDatasourceName() == null ? deviceConfiguration.getDevice().getDeviceId():
			deviceConfiguration.getDevice().getDatasourceName());
		for (Point point : deviceConfiguration.getConfigPoints()) {
			Parameter parameter = new Parameter(point.getDisplayName(),
					point.getType());
			parameter.setUnit(point.getUnit());
			datasourceDTO.addParameter(parameter);
		}
		DatasourceService datasourceService = new DatasourceService();
		//LOGGER.info("Request to update data source {}",datasourceDTO.getDatasourceName());
		JsonNode updateResponse = datasourceService.update(datasourceDTO);
		if(updateResponse.get(STATUS) != null && updateResponse.get(STATUS).asText().equalsIgnoreCase(SUCCESS)){
			LOGGER.info("Datasource updated :: "+datasourceDTO.getDatasourceName());
		}else{
			LOGGER.error("Could not update the datasource :: "+datasourceDTO.getDatasourceName());
		}

		datasourceService = null;
		refreshDeviceConfiguration(deviceConfiguration,gateway);
		return registered;
	}

	/**
	 * @param message
	 * @param deviceConfiguration
	 */
	public void publishData(Message message,
			DefaultConfiguration deviceConfiguration,Gateway gateway) {
		LOGGER.info("Publishing to data source : "+deviceConfiguration.getDevice().getDatasourceName());


		/*if(deviceConfiguration.getDevice().getIsPublishing()){
			if(deviceConfiguration.getDevice().getDatasourceName() == null){

				LOGGER.error("No data source set for the device !! cannot publish !! :: "+deviceConfiguration.getDevice().getDeviceId());

				if(registerDatasource(deviceConfiguration.getUnitId(),deviceConfiguration,gateway)){
					DatasourceDTO datasourceDTO = new DatasourceDTO();
					datasourceDTO.setDatasourceName(deviceConfiguration.getDevice().getDatasourceName());
					for (Point point : message.getPoints()) {
						Parameter parameter = new Parameter(point.getDisplayName(),
								point.getType());
						parameter.setValue(point.getData());
						parameter.setTime(message.getTime());
						parameter.setUnit(point.getUnit());
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
					Parameter parameter = new Parameter(point.getDisplayName(),
							point.getType());
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
		}*/

	}

	public void sendWriteResponse(WriteResponseMessage writeResponse ) {
		try {
			WritebackService writebackService = new WritebackService();
			WritebackResponse writebackResponse = new WritebackResponse();
			writebackResponse.setRemarks(writeResponse.getRemark());
			writebackResponse.setRequestId(writeResponse.getReqId());
			writebackResponse.setSourceId(writeResponse.getSourceId().toString());
			writebackResponse.setStatus(writeResponse.getStatus());
			writebackResponse.setTime(writeResponse.getTime());
			writebackService.updateWritebackResponse(writebackResponse);
		} catch (Exception e) {
			LOGGER.error("Exception updating write response!!",e);
		}
	}

	public void updateDeviceWritebackConfig(WritebackConfiguration writebackConfiguration,Gateway gateway ) {
		try {
			WritebackService writebackService = new WritebackService();
			StatusMessage updateConfiguration = writebackService.updateConfiguration(writebackConfiguration);
			LOGGER.info("Status from writeback update is {}",updateConfiguration!=null?updateConfiguration.getStatus().getStatus():"N/A");
		} catch (Exception e) {
			LOGGER.error("Exception updating write response!!",e);
		}
	}


	@Override
	public void notifyMessage(Message message,DefaultConfiguration deviceConfiguration,MessageCompletion messageCompletion, Gateway gateway) {

		try {
			message.setSourceId(deviceConfiguration.getDevice().getSourceId());//Setting source id back to the identifier for publishing.
			prepareBrokerMessage(message, deviceConfiguration, messageCompletion);
			// Sending message to CEP for real time processing.
			prepareCEPMessage(message);

			notify(new ActivityMessage(deviceConfiguration.getDevice().getDeviceId(), message.getReceivedTime().getTime(), deviceConfiguration.getDevice().getDeviceName(),
					deviceConfiguration.getDevice().getDatasourceName()));
		} catch (Exception e) {
			LOGGER.info("Error notifying messages",e);
		}

	}

	private void prepareCEPMessage(Message message) throws Exception {
		if(ManagerConfiguration.getProperty(ManagerConfiguration.ALARM_ENABLED).equalsIgnoreCase("true")) {
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
			KeyedMessage<Object, Object> cepMessage = null;
			List<Serializable> messages = new ArrayList<Serializable>();
			cepMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.CEP_MESSAGE_STREAM), CEPMessageConverter.getCEPInputMessage(message));
			messages.add(cepMessage);
			publisher.publish(messages);
		}
	}

	private void prepareBrokerMessage(Message message,DefaultConfiguration deviceConfiguration,MessageCompletion messageCompletion) {
		try {
			if(MessageUtil.getNotificationHandler() == null){
				LOGGER.error("No message handler found !!");
				return ;
			}
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);

			List<Serializable> messages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> storeMessage = null;
			KeyedMessage<Object, Object> publishMessage = null;


			switch (messageCompletion) {
			case COMPLETE://intelligent devices

				List<DataIngestionBean> ingestionData = MessageIngestionUtil.prepareIngestionMessage(deviceConfiguration.getDevice(), message, true);
				
				if(!CollectionUtils.isEmpty(ingestionData)){
					ObjectMapper mapper = new ObjectMapper();
					
					if(deviceConfiguration.getDevice().getIsPublishing()){
						publishMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.LIVE_MESSAGE_STREAM),mapper.writeValueAsString(ingestionData));
						messages.add(publishMessage);
						LOGGER.info("Will publish messages to kafka topic {}",ManagerConfiguration.getProperty(ManagerConfiguration.LIVE_MESSAGE_STREAM));
					}
					
					for (DataIngestionBean ingestionBean : ingestionData) {
						String ingestionString = mapper.writeValueAsString(ingestionBean);
						storeMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.ANALYZED_MESSAGE_STREAM),ingestionString);
						messages.add(storeMessage);
					}
					LOGGER.info("Will publish {} messages to kafka topic {}",ingestionData.size(),ManagerConfiguration.getProperty(ManagerConfiguration.ANALYZED_MESSAGE_STREAM));						
				}


				break;

			case PARTIAL://dumb devices
				if(deviceConfiguration.getDevice().getIsPublishing()){
					publishMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.DECODED_MESSAGE_STREAM_PUBLISH),message.toString());
					messages.add(publishMessage);
					LOGGER.info("Publishing {} messages to kafka topic {}",messages.size(),ManagerConfiguration.getProperty(ManagerConfiguration.DECODED_MESSAGE_STREAM_PUBLISH));
				}else{
					publishMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.DECODED_MESSAGE_STREAM),message.toString());
					messages.add(publishMessage);
					LOGGER.info("Publishing {} messages to kafka topic {}",messages.size(),ManagerConfiguration.getProperty(ManagerConfiguration.DECODED_MESSAGE_STREAM));
				}

				break;

			default:
				break;
			}

			publisher.publish(messages);
		} catch (Exception e) {
			LOGGER.error("Remote invocation exception",e.getMessage());
			LOGGER.error("Remote invocation exception",e);

		}
	}


	@Override
	public void notifyAlarm(AlarmMessage alarm, Gateway gateway) {

		try {
			if(MessageUtil.getNotificationHandler() == null){
				LOGGER.error("No message handler found !!");
				return ;
			}
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
			List<Serializable> messages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> keyedMessage = null;

			keyedMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.ALARM_STREAM), alarm.toString());
			messages.add(keyedMessage);
			LOGGER.info("Publishing {} messages to kafka topic {}",messages.size(),ManagerConfiguration.getProperty(ManagerConfiguration.ALARM_STREAM));

			publisher.publish(messages);

			DefaultConfiguration deviceConfiguration = getDeviceConfiguration(alarm.getSourceId(), gateway);
			notify(new ActivityMessage(deviceConfiguration.getDevice().getDeviceId(), alarm.getReceivedTime().getTime(), deviceConfiguration.getDevice().getDeviceName() ,
					deviceConfiguration.getDevice().getDatasourceName()));
		} catch (Exception e) {
			LOGGER.error("notifyAlarm | Remote invocation exception",e);
		}


	}

	/**
	 * 
	 */
	public void notify(Object identifier, Gateway gateway) {
		try {
			DefaultConfiguration deviceConfiguration = getDeviceConfiguration((String)identifier, gateway);
			notify(new ActivityMessage(deviceConfiguration.getDevice().getDeviceId(), System.currentTimeMillis(), deviceConfiguration.getDevice().getDeviceName() ,
					deviceConfiguration.getDevice().getDatasourceName()));
		} catch (Exception e) {
			LOGGER.error("EOSDeviceManager | notify | Remote invocation exception", e);
		}
	}

	/**
	 * 
	 * @param message
	 */
	private void notify(final ActivityMessage message) {

		try {
			if(MessageUtil.getNotificationHandler() == null){
				LOGGER.error("EOSDeviceManager | notify - No message handler found!!");
				return ;
			}
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
			List<Serializable> messages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> keyedMessage = null;

			ObjectMapper mapper = new ObjectMapper();
			String messageToPublish = null;
			messageToPublish= mapper.writeValueAsString(message);

			keyedMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_ACTIVITY_STREAM), messageToPublish);
			messages.add(keyedMessage);
			LOGGER.info("Publishing {} messages to kafka topic {}", messages.size(), ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_ACTIVITY_STREAM));

			publisher.publish(messages);
		} catch (Exception e) {
			LOGGER.error("EOSDeviceManager | notify | Remote invocation exception", e);
		}
	}


	@Override
	public CacheProvider getCacheProvider() {
		return CacheUtil.getCacheProvider();
	}


	/**
	 * Creates specific keys cache for each type of devices.
	 * @return
	 */
	private String getDeviceKeyCache(Gateway gateway) {
		return ManagerConfiguration.DEVICE_KEYS_CACHE+gateway.getId();
	}

	/**
	 * Creates specific configuration cache for each type of devices.
	 * @return
	 */
	private String getDeviceConfigurationCache(Gateway gateway) {
		return ManagerConfiguration.DEVICE_CONFIGURATION_CACHE+gateway.getId();
	}

	/**
	 * Creates specific supported points cache for each type of devices.
	 * @return
	 */
	private String getProtocolPointsCache(Gateway gateway) {
		return ManagerConfiguration.DEVICE_POINTS_PROTOCOL_CACHE+gateway.getId();
	}



	@Override
	public void registerCommandListener(CommandListener commandListener,
			Gateway gateway) {
		LOGGER.info("Registering command listener for Gateway {}, with id {}",gateway.getVendor(),gateway.getId());
		CommandNotifier.getInstance().addCommandListener(commandListener, gateway);
	}

	@Override
	public void deregisterCommandListener(Gateway gateway) {
		CommandNotifier.getInstance().removeCommandListener(gateway);
	}


	public static void main(String[] args) {
		Gateway gateway = new Gateway();

		gateway.setModel("Jace");
		gateway.setProtocol("Custom");
		gateway.setType("Gateway");
		gateway.setVendor("PCS");
		gateway.setVersion("1.0.0");

		Device test = new Device();
		test.setSourceId("259642460479873");
		DefaultConfiguration deviceConfiguration = EOSDeviceManager.instance().getDeviceConfiguration(8, gateway);
		EOSDeviceManager.instance().evictDeviceConfiguration(8, gateway);
		//EOSDeviceManager.instance().setDeviceConfiguration(deviceConfiguration, gateway);
		//EOSDeviceManager.instance().notifyMessage(new Message(), deviceConfiguration, MessageCompletion.PARTIAL, gateway);
		System.err.println(deviceConfiguration);
	}

	@Override
	public void notifyMessages(List<Message> messages,
			DefaultConfiguration deviceConfiguration,
			MessageCompletion messageCompletion, Gateway gateway) {
		try {
			if(CollectionUtils.isEmpty(messages)){
				return;
			}else{
				for (Message message : messages) {
					prepareBrokerMessage(message, deviceConfiguration, messageCompletion);
				}

				notify(new ActivityMessage(deviceConfiguration.getDevice().getDeviceId(), messages.get(0).getReceivedTime().getTime(), deviceConfiguration.getDevice().getDeviceName(),
						deviceConfiguration.getDevice().getDatasourceName()));
			}

		} catch (Exception e) {
			LOGGER.info("Error notifying messages",e);
		}

	}

	@Override
	public void notifyAlarms(List<AlarmMessage> alarms, Gateway gateway) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyGenericMessage(GenericMessage genericMessage, Gateway gateway) {

		if(MessageUtil.getNotificationHandler() == null){
			LOGGER.error("No message handler found !!");
			return ;
		}
		try {
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
			List<Serializable> messages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> storeMessage = null;
			KeyedMessage<Object, Object> publishMessages = null;

			DefaultConfiguration configuration = (DefaultConfiguration) getConfiguration(genericMessage.getSourceId(), gateway);
			GenericIngestionType genericMessageType = GenericMessageIngestionUtil.prepareIngestionMessage(configuration, genericMessage, true);

			storeMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.ANALYZED_MESSAGE_STREAM),new ObjectMapper().writeValueAsString(genericMessageType.getIngestionBean()));
			messages.add(storeMessage);

			publishMessages = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.LIVE_MESSAGE_STREAM),(genericMessageType.getMessage().toString()));
			messages.add(publishMessages);

			publisher.publish(messages);
		} catch (Exception e) {
			LOGGER.error("Error notifying MQTT messages",e);
		}

	}

	@Override
	public void notifyGenericMessages(List<GenericMessage> genericMessages, Gateway gateway) {
		if(MessageUtil.getNotificationHandler() == null){
			LOGGER.error("No message handler found !!");
			return ;
		}
		try {

			if(CollectionUtils.isEmpty(genericMessages)){
				LOGGER.info("No messages found");
				return;
			}else{
				CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
				List<Serializable> messages = new ArrayList<Serializable>();
				KeyedMessage<Object, Object> storeMessage = null;
				KeyedMessage<Object, Object> publishMessages = null;

				DefaultConfiguration configuration = (DefaultConfiguration) getConfiguration(genericMessages.get(0).getSourceId(), gateway);
				GenericIngestionType genericDataMessage = GenericMessageIngestionUtil.prepareIngestionMessage(configuration, genericMessages, true);

				List<DataIngestionBean> ingestionData = genericDataMessage.getIngestionData();
				for (DataIngestionBean dataIngestionBean : ingestionData) {
					storeMessage = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.ANALYZED_MESSAGE_STREAM),new ObjectMapper().writeValueAsString(dataIngestionBean));
					messages.add(storeMessage);
				}

				publishMessages = new KeyedMessage<Object, Object>(ManagerConfiguration.getProperty(ManagerConfiguration.LIVE_MESSAGE_STREAM),(genericDataMessage.getMessage().toString()));
				messages.add(publishMessages);
				messages.add(storeMessage);
				publisher.publish(messages);
			}

		} catch (Exception e) {
			LOGGER.error("Error notifying MQTT messages",e);
		}
	}

	@Override
	public void notifyGenericAlarm(GenericEvent genericEvent, Gateway gateway) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyGenericAlarms(List<GenericEvent> genericEvents, Gateway gateway) {
		// TODO Auto-generated method stub

	}


}

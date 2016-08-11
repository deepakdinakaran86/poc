/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.services;

import static com.pcs.datasource.enums.DeviceDataFields.CONNECTED_PORT;
import static com.pcs.datasource.enums.DeviceDataFields.DATASOURCE_NAME;
import static com.pcs.datasource.enums.DeviceDataFields.DEVICE_NAME;
import static com.pcs.datasource.enums.DeviceDataFields.DEVICE_TYPE;
import static com.pcs.datasource.enums.DeviceDataFields.IP;
import static com.pcs.datasource.enums.DeviceDataFields.IS_PUBLISHING;
import static com.pcs.datasource.enums.DeviceDataFields.MAKE;
import static com.pcs.datasource.enums.DeviceDataFields.MODEL;
import static com.pcs.datasource.enums.DeviceDataFields.NAME;
import static com.pcs.datasource.enums.DeviceDataFields.NWPROTOCOL;
import static com.pcs.datasource.enums.DeviceDataFields.PROTOCOL;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.SUBSCRIPTION;
import static com.pcs.datasource.enums.DeviceDataFields.SUB_ID;
import static com.pcs.datasource.enums.DeviceDataFields.UNIT_ID;
import static com.pcs.datasource.enums.DeviceDataFields.VERSION;
import static com.pcs.datasource.enums.DeviceDataFields.WRITEBACK_PORT;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.DEVICE_ALREADY_CLAIMED;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryInnerFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateResult;
import static com.pcs.devicecloud.enums.Status.SUCCESS;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DatasourceDTO;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceAuthentication;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DeviceTag;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceConfigRepository;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.exception.DeviceCloudException.ErrorAppendMode;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;
import com.pcs.devicecloud.enums.Status;

/**
 * This service implementation is responsible for defining all the services
 * related to Device communicating to the system. This class is responsible for
 * authenticate device with galaxy, register device once after the successful
 * authentication and manage services related to configuration templateget
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final String IP_V4_PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DeviceServiceImpl.class);

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private NetworkProtocolService nwProtocolService;

	@Autowired
	private DeviceProtocolService deviceProtocolService;

	@Autowired
	private DeviceTypeService deviceTypeService;

	@Autowired
	private DeviceTagService deviceTagService;

	@Autowired
	DatasourceRegistrationService datasourceRegistrationService;

	@Autowired
	private DeviceConfigRepository deviceConfigRepository;

	@Autowired
	private SystemService systemService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#getAllDevices(java.lang.String,
	 * java.util.List)
	 */
	public List<Device> getAllDevices(Subscription subscription,
	        List<String> tagNames) {
		validateMandatoryField(SUB_ID, subscription.getSubId());
		List<Device> allDevices = deviceRepository.getAllDevices(
		        subscription.getSubId(), tagNames);
		validateResult(allDevices);
		return allDevices;
	}

	public List<Device> getAllDevices() {
		List<Device> allDevices = deviceRepository.getAllDevices();
		validateResult(allDevices);
		return allDevices;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#getDevice(java.lang.String)
	 */
	@Override
	public Device getDevice(String sourceId) {
		validateMandatoryField(SOURCE_ID, sourceId);
		Device device = deviceRepository.getDevice(sourceId);
		validateResult(device);
		Subscription subscription = device.getSubscription();
		if (subscription != null && subscription.getSubId() != null) {
			DeviceConfigTemplate deviceConfTemplate = deviceConfigRepository
			        .getDeviceConfiguration(subscription.getSubId(), sourceId);
			if (deviceConfTemplate != null) {
				device.setConfigurations(deviceConfTemplate);
			}
		}
		return device;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#getDevice(java.lang.String)
	 */
	@Override
	public Device getDevice(String unitId, ConfigurationSearch version) {
		validateMandatoryField(UNIT_ID, unitId);
		Device device = deviceRepository.getDevice(unitId, version);
		validateResult(device);
		Subscription subscription = device.getSubscription();
		if (subscription != null && subscription.getSubId() != null) {
			DeviceConfigTemplate deviceConfTemplate = deviceConfigRepository
			        .getDeviceConfiguration(subscription.getSubId(), unitId);
			if (deviceConfTemplate != null) {
				device.setConfigurations(deviceConfTemplate);
			}
		}
		return device;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#getDevice(java.lang.String)
	 */
	@Override
	public Device getDevice(String sourceId, Subscription subscription) {
		validateMandatoryField(SOURCE_ID, sourceId);
		Device device = deviceRepository.getDevice(sourceId, subscription);
		validateResult(device);
		DeviceConfigTemplate deviceConfTemplate = deviceConfigRepository
		        .getDeviceConfiguration(subscription.getSubId(), sourceId);
		if (deviceConfTemplate != null) {
			device.setConfigurations(deviceConfTemplate);
		}
		return device;
	}

	/**
	 * Service method to register device once after the successful
	 * authentication
	 * 
	 * @return {@link StatusMessageDTO}
	 */
	@Override
	public StatusMessageDTO registerDevice(Device device) {

		validateDeviceRegisterMandatoryFields(device);

		Device deviceWithSourceId = null;
		try {
			deviceWithSourceId = getDevice(device.getSourceId());
		} catch (DeviceCloudException e) {
			LOGGER.info("Source Id {} is unique", device.getSourceId());
		}
		if (deviceWithSourceId != null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_IS_NOT_UNIQUE,
			        SOURCE_ID.getDescription());
		}
		if (device.getIsPublishing()) {
			if (datasourceRegistrationService.isDatasourceExist(device
			        .getDatasourceName())) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.FIELD_IS_NOT_UNIQUE,
				        DATASOURCE_NAME.getDescription());
			}

		} else {
			device.setDatasourceName(null);
		}

		validateDeviceProperties(device);
		// Set deviceId
		device.setDeviceId(UUID.randomUUID().toString());

		device.setUnitId(getUnitId(device.getVersion()));
		deviceRepository.registerDevice(device);
		// Registering datasource name
		if (device.getIsPublishing()) {
			registerDatasourceName(device.getDatasourceName());
		}
		LOGGER.info("device {} is registered", device.getSourceId());
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#getDeviceConfig(java.lang.String
	 * )
	 */
	@Override
	public DeviceConfigTemplate getConfigurations(String sourceId,
	        Subscription subscription) {

		validateMandatoryField(SOURCE_ID, sourceId);

		// Validating device source id
		Device device = null;
		try {
			device = getDevice(sourceId, subscription);
		} catch (DeviceCloudException exception) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		DeviceConfigTemplate configurations = device.getConfigurations();
		validateResult(configurations);

		return configurations;
	}

	@Override
	public DeviceConfigTemplate getConfigurations(String sourceId) {

		validateMandatoryField(SOURCE_ID, sourceId);

		// Validating device source id
		Device device = getDevice(sourceId);

		DeviceConfigTemplate configurations = device.getConfigurations();
		validateResult(configurations);

		return configurations;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#createDevice(com.pcs.datasource
	 * .dto.dc.Device)
	 */
	@Override
	public StatusMessageDTO insertDevice(Device device) {

		validateMandatoryFields(device, SOURCE_ID, DEVICE_NAME);

		validateDeviceMandatoryFields(device);

		Device deviceWithSourceId = null;
		try {
			deviceWithSourceId = getDevice(device.getSourceId());
		} catch (DeviceCloudException e) {
			LOGGER.info("SourceId {} is Unique", device.getSourceId(), e);
		}
		if (deviceWithSourceId != null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_IS_NOT_UNIQUE,
			        SOURCE_ID.getDescription());
		}
		if (device.getIsPublishing()) {
			if (datasourceRegistrationService.isDatasourceExist(device
			        .getDatasourceName())) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.FIELD_IS_NOT_UNIQUE,
				        DATASOURCE_NAME.getDescription());
			}

		} else {
			device.setDatasourceName(null);
		}

		validateDeviceProperties(device);
		// Set deviceId
		device.setDeviceId(UUID.randomUUID().toString());

		device.setUnitId(getUnitId(device.getVersion()));
		deviceRepository.insertDevice(device);
		// Create New Tags or Add New
		deviceTagService.updateDeviceRelation(device);
		// Registering datasource name
		if (device.getIsPublishing()) {
			registerDatasourceName(device.getDatasourceName());
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	private Integer getUnitId(ConfigurationSearch version) {
		Integer availableUnitId = -1;
		try {
			availableUnitId = deviceRepository.generateUnitId(version);
		} catch (NoResultException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION);
		}
		return availableUnitId;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#updateDevice(com.pcs.datasource
	 * .dto.dc.Device)
	 */
	@Override
	public StatusMessageDTO updateDevice(String sourceId, Device device) {

		validateMandatoryFields(device, SOURCE_ID, DEVICE_NAME);

		validateDeviceMandatoryFields(device);

		if (isNotEmpty(device.getSourceId())
		        && !sourceId.equals(device.getSourceId())) {
			throw new DeviceCloudException(
			        "Source Id specified is not matching with the Source Id passed in the payload, ",
			        DeviceCloudErrorCodes.SPECIFIC_DATA_CANT_BE_UPDATED,
			        ErrorAppendMode.SUFFFIX);
		}
		device.setSourceId(sourceId);

		Device existingDevice = null;
		boolean dataSourceRegister = false;
		try {
			existingDevice = getDevice(sourceId, device.getSubscription());
		} catch (DeviceCloudException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED);
		}
		String newDataSourceName = device.getDatasourceName();

		if (device.getIsPublishing()) {
			String existingDSName = deviceRepository.getDatasourceName(
			        device.getSubscription(), sourceId);

			if (existingDSName != null
			        && !existingDSName.equalsIgnoreCase(newDataSourceName)) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.SPECIFIC_DATA_CANT_BE_UPDATED,
				        DATASOURCE_NAME.getDescription());
			} else if (existingDSName == null) {
				if (datasourceRegistrationService
				        .isDatasourceExist(newDataSourceName)) {
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.FIELD_IS_NOT_UNIQUE,
					        DATASOURCE_NAME.getDescription());
				}
				dataSourceRegister = true;
			}
		} else {
			String dataSourceName = deviceRepository.getDatasourceName(
			        device.getSubscription(), sourceId);
			device.setDatasourceName(dataSourceName);
		}

		// Set device_id and unit_id from existing device
		device.setDeviceId(existingDevice.getDeviceId());

		if (!device.getNetworkProtocol().getName()
		        .equals(existingDevice.getNetworkProtocol().getName())) {
			nwProtocolService.updateDeviceRelation(device);
		}

		if (device.getVersion().equals(existingDevice.getVersion())) {
			device.setUnitId(existingDevice.getUnitId());

		} else {
			device.setUnitId(getUnitId(device.getVersion()));
			deviceProtocolService.updateDeviceRelation(device);
			deviceProtocolService.recycleUnitId(existingDevice);
		}
		// Create New Tags or Add New
		deviceTagService.updateDeviceRelation(device);
		deviceRepository.updateDevice(device);
		if (dataSourceRegister) {
			registerDatasourceName(newDataSourceName);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	private void registerDatasourceName(String datasourceName) {
		DatasourceDTO datasource = new DatasourceDTO();
		datasource.setDatasourceName(datasourceName);
		datasourceRegistrationService.registerDatasource(datasource);
	}

	private void validateDeviceMandatoryFields(Device device) {
		validateMandatoryFields(device, IS_PUBLISHING, SUBSCRIPTION, VERSION,
		        NWPROTOCOL);
		if (device.getIsPublishing()) {
			validateMandatoryFields(device, DATASOURCE_NAME);
		}
		validateMandatoryInnerFields(VERSION, device.getVersion(), MAKE,
		        DEVICE_TYPE, MODEL, PROTOCOL, VERSION);
		validateMandatoryInnerFields(NWPROTOCOL, device.getNetworkProtocol(),
		        NAME);

	}

	private void validateDeviceRegisterMandatoryFields(Device device) {
		validateMandatoryFields(device, SOURCE_ID, DEVICE_NAME, IS_PUBLISHING,
		        VERSION, NWPROTOCOL);
		if (device.getIsPublishing()) {
			validateMandatoryFields(device, DATASOURCE_NAME);
		}
		validateMandatoryInnerFields(VERSION, device.getVersion(), MAKE,
		        DEVICE_TYPE, MODEL, PROTOCOL, VERSION);
		validateMandatoryInnerFields(NWPROTOCOL, device.getNetworkProtocol(),
		        NAME);

	}

	private void validateDeviceProperties(Device device) {
		// validate device type
		ProtocolVersion protocolVersion = deviceProtocolService
		        .getDeviceProtocolVersion(device.getVersion());
		if (protocolVersion == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        VERSION.getDescription());
		}

		// validate networkProtocol
		NetworkProtocol networkProtocol = nwProtocolService
		        .getNetworkProtocol(device.getNetworkProtocol().getName());
		if (networkProtocol == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        NWPROTOCOL.getDescription());
		}
	}

	/**
	 * Service Method for update write back configuration of a device
	 * 
	 * @param sourceId
	 * @param device
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateWritebackConf(String sourceId, Device device) {
		validateMandatoryField(SOURCE_ID, sourceId);
		validateMandatoryFields(device, IP, WRITEBACK_PORT, CONNECTED_PORT);
		device = getDevice(sourceId);
		if (device == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		boolean flag = validate(device.getIp());
		if (!flag) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        IP.getDescription());
		}
		deviceRepository.updateWritebackConf(sourceId, device);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * Service Method to get datasource name of a device
	 * 
	 * @param sourceId
	 * @return dataSourceName
	 */
	public String getDatasourceName(Subscription subscription, String sourceId) {
		validateMandatoryField(SOURCE_ID, sourceId);
		String dataSourceName = deviceRepository.getDatasourceName(
		        subscription, sourceId);
		if (dataSourceName == null || dataSourceName.equals("")) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
		return dataSourceName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#getDevicesOfProtocolVersion
	 * (java.lang.String, com.pcs.datasource.dto.ConfigurationSearch)
	 */
	@Override
	public List<Device> getDevicesOfProtocolVersion(Subscription subscription,
	        ConfigurationSearch conSearch) {

		validateMandatoryFields(conSearch, MAKE, DEVICE_TYPE, MODEL, PROTOCOL,
		        VERSION);
		List<Device> allDeviceOfProtocol = deviceRepository
		        .getAllDeviceOfProtocol(subscription, conSearch);
		validateResult(allDeviceOfProtocol);
		return allDeviceOfProtocol;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#updateTags(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public StatusMessageDTO updateTags(String sourceId, List<DeviceTag> tags) {
		validateMandatoryField(SOURCE_ID, sourceId);
		Device device = null;
		try {
			device = getDevice(sourceId);
		} catch (DeviceCloudException e) {
			LOGGER.debug("Device Not Found with sourceId:{}", sourceId);
		}
		if (device == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		device.setTags(tags);
		deviceTagService.updateDeviceRelation(device);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceService#claimDevice(java.lang.String,
	 * com.pcs.datasource.dto.Subscription)
	 */
	@Override
	public StatusMessageDTO claimDevice(String sourceId,
	        Subscription subscription) {

		validateMandatoryField(SOURCE_ID, sourceId);
		validateMandatoryFields(subscription, SUB_ID);

		Device device = null;
		try {
			device = getDevice(sourceId);
		} catch (DeviceCloudException e) {
			LOGGER.debug("Device Not Found with sourceId:{}", sourceId);
		}
		if (device == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}

		Subscription deviceSub = device.getSubscription();
		if (deviceSub != null && deviceSub.getSubId() != null) {
			throw new DeviceCloudException(DEVICE_ALREADY_CLAIMED);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			deviceRepository.claimDevice(sourceId, subscription);
		} catch (PersistenceException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	public static boolean validate(final String ip) {
		Pattern pattern = Pattern.compile(IP_V4_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	@Override
	public List<Device> getAllUnSubscribed() {
		List<Device> devices = deviceRepository.getAllUnSubscribed();
		validateResult(devices);
		return devices;
	}

	@Override
	public DeviceAuthentication authenticateDevice(Device deviceParam) {

		validateMandatoryFields(deviceParam);
		Device device = null;
		if (StringUtils.isNotEmpty(deviceParam.getSourceId())) {
			try {
				device = getDevice(deviceParam.getSourceId());
			} catch (DeviceCloudException e) {
				LOGGER.info("Device source id {} not found",
				        deviceParam.getSourceId());
			}
		} else {
			try {
				validateMandatoryFields(deviceParam, UNIT_ID, VERSION);
				validateMandatoryFields(deviceParam.getVersion(), MAKE,
				        DEVICE_TYPE, MODEL, PROTOCOL, VERSION);
				device = getDevice(deviceParam.getUnitId().toString(),
				        deviceParam.getVersion());
			} catch (DeviceCloudException e) {
				LOGGER.info("Device not found : ", e.getMessage());
			}
		}

		DeviceAuthentication authentication = new DeviceAuthentication();
		GeneralResponse genResponse = new GeneralResponse();
		authentication.setGeneralResponse(genResponse);

		if (device == null) {
			genResponse.setStatus(Status.NOT_AVAIALABLE);
			genResponse.setRemarks("Device not found anywhere");
			return authentication;
		}

		if (device.getSubscription() == null) {
			genResponse.setStatus(Status.UNSUBSCRIBED);
			genResponse.setRemarks("Device is available but unsubscribed");
			authentication.setDevice(device);
			return authentication;
		}

		DeviceConfigTemplate deviceConfTemplate = deviceConfigRepository
		        .getDeviceConfiguration(device.getSubscription().getSubId(),
		                device.getSourceId());
		if (deviceConfTemplate != null) {
			device.setConfigurations(deviceConfTemplate);
		}
		genResponse.setStatus(Status.SUBSCRIBED);
		genResponse.setRemarks("Device is available");
		authentication.setDevice(device);

		return authentication;
	}
}

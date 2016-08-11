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

import static com.pcs.datasource.enums.DeviceConfigFields.CONFIG_POINTS;
import static com.pcs.datasource.enums.DeviceConfigFields.CONFIG_TEMPLATE;
import static com.pcs.datasource.enums.DeviceConfigFields.CONFIG_TEMP_NAME;
import static com.pcs.datasource.enums.DeviceConfigFields.DATA_TYPE;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_MAKE;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_MODEL;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_PROTOCOL;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_PROTOCOL_VERSION;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_TYPE;
import static com.pcs.datasource.enums.DeviceConfigFields.DISPLAY_NAME;
import static com.pcs.datasource.enums.DeviceConfigFields.PHYSICAL_QUANTITY;
import static com.pcs.datasource.enums.DeviceConfigFields.POINT_ID;
import static com.pcs.datasource.enums.DeviceConfigFields.POINT_NAME;
import static com.pcs.datasource.enums.DeviceConfigFields.PRECEDENCE;
import static com.pcs.datasource.enums.DeviceConfigFields.UNIT;
import static com.pcs.datasource.enums.DeviceDataFields.MAKE;
import static com.pcs.datasource.enums.DeviceDataFields.MODEL;
import static com.pcs.datasource.enums.DeviceDataFields.PROTOCOL;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_IDS;
import static com.pcs.datasource.enums.DeviceDataFields.SUBSCRIPTION;
import static com.pcs.datasource.enums.DeviceDataFields.TEMPLATE_NAME;
import static com.pcs.datasource.enums.DeviceDataFields.VERSION;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateResult;
import static com.pcs.devicecloud.enums.Status.FALSE;
import static com.pcs.devicecloud.enums.Status.SUCCESS;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.ConfigureDevice;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.GeneralBatchResponse;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.TemplateAssign;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.repository.DeviceConfigRepository;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.datasource.repository.PointRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.exception.DeviceCloudException.ErrorAppendMode;
import com.pcs.devicecloud.commons.validation.ValidationUtils;
import com.pcs.devicecloud.enums.Status;

/**
 * This class is responsible for defining all the services related to device
 * point configuration
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 02 Jul 2015
 */
@Service
public class DeviceConfigServiceImpl implements DeviceConfigService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeviceConfigServiceImpl.class);

	@Autowired
	private DeviceConfigRepository deviceConfigRepository;

	@Autowired
	private PointRepository pointRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private PhyQuantityService phyQuantityService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private DeviceService deviceService;

	@Override
	public StatusMessageDTO isDeviceConfigTempExist(String subId,
			String tempName) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FALSE);
		validateMandatoryField(CONFIG_TEMP_NAME, tempName);
		try {
			tempName = URLDecoder.decode(tempName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		if (deviceConfigRepository.isDeviceConfigTempNameExist(subId, tempName)) {
			statusMessageDTO.setStatus(Status.TRUE);
		}
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO createDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemp) {
		validateDeviceConfigFields(configTemp);

		if (deviceConfigRepository.isDeviceConfigTempNameExist(subId,
				configTemp.getName())) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					CONFIG_TEMP_NAME.getDescription());
		}
		if (pointRepository
				.doExistPointsInProtocol(getConfigSearch(configTemp))) {
			validateDeviceConfigTemp(subId, configTemp);
			deviceConfigRepository
					.saveDeviceConfigTemp(subId, configTemp, true);
		} else {
			deviceConfigRepository.saveDeviceConfigTemp(subId, configTemp,
					false);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO updateDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemp) {
		validateDeviceConfigFields(configTemp);

		if (!deviceConfigRepository.isDeviceConfigTempNameExist(subId,
				configTemp.getName())) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED,
					"Configuration template " + configTemp.getName()
							+ " don't exist.");
		}
		if (pointRepository
				.doExistPointsInProtocol(getConfigSearch(configTemp))) {
			validateDeviceConfigTemp(subId, configTemp);
			deviceConfigRepository.updateDeviceConfigTemp(subId, configTemp,
					true);
		} else {
			deviceConfigRepository.updateDeviceConfigTemp(subId, configTemp,
					false);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	private void validateDeviceConfigFields(DeviceConfigTemplate configTemplate) {
		validateMandatoryFields(configTemplate, CONFIG_TEMP_NAME, DEVICE_TYPE,
				DEVICE_MAKE, DEVICE_MODEL, DEVICE_PROTOCOL,
				DEVICE_PROTOCOL_VERSION);
		validateCollection(CONFIG_POINTS, configTemplate.getConfigPoints());
		validateConfigPoints(configTemplate.getConfigPoints());
	}

	private void validateConfigPoints(List<ConfigPoint> configPoints) {
		for (int i = 0; i < configPoints.size(); i++) {
			validateMandatoryFields(configPoints.get(i), POINT_ID, POINT_NAME,
					DISPLAY_NAME, DATA_TYPE, PHYSICAL_QUANTITY, PRECEDENCE);

			if (StringUtils.isEmpty(configPoints.get(i).getUnit())) {
				configPoints.get(i).setUnit("unitless");
			}

			validatePhysicalQuantity(configPoints.get(i).getPhysicalQuantity());
			validateUnit(configPoints.get(i).getUnit());

			if (StringUtils.isNotBlank(configPoints.get(i).getUnit())) {
				validatePhysicalQuantity(configPoints.get(i)
						.getPhysicalQuantity(), configPoints.get(i).getUnit(),
						configPoints.get(i).getDataType());
			}

			for (int j = i + 1; j < configPoints.size(); j++) {
				if (configPoints.get(i).getDisplayName()
						.equalsIgnoreCase(configPoints.get(j).getDisplayName())) {
					LOGGER.info(
							"Point id {} and Point id {} has same display name {}",
							configPoints.get(i).getPointId(), configPoints
									.get(j).getPointId(), configPoints.get(i)
									.getDisplayName());
					throw new DeviceCloudException(
							DeviceCloudErrorCodes.DUPLICATE_RECORDS,
							"Point display name "
									+ configPoints.get(i).getDisplayName()
									+ " is ambiguous.");
				}
			}
		}
	}

	private void validateDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemp) {
		ConfigurationSearch configSearch = new ConfigurationSearch();
		configSearch.setMake(configTemp.getDeviceMake());
		configSearch.setDeviceType(configTemp.getDeviceType());
		configSearch.setModel(configTemp.getDeviceModel());
		configSearch.setProtocol(configTemp.getDeviceProtocol());
		configSearch.setVersion(configTemp.getDeviceProtocolVersion());
		String parentNodeId = deviceConfigRepository
				.findParentData(configSearch);
		if (StringUtils.isBlank(parentNodeId)) {
			String errMsg = "Unable to find config template's parent node."
					+ "Either device make or device type or device model or "
					+ "device protocol or protocol version is not matching , response is null";
			LOGGER.error(errMsg);
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
					DEVICE_PROTOCOL_VERSION.getDescription());
		}
		checkConfigValidity(parentNodeId, configTemp, subId);
	}

	/**
	 * Method to check configured points in a configuration template is valid -
	 * by checking it has a valid point in the protocol version and has a valid
	 * parameter of the subscription
	 * 
	 * @param parentNodeId
	 * @param configTemplate
	 */
	private void checkConfigValidity(String parentNodeId,
			DeviceConfigTemplate configTemplate, String subId) {
		for (ConfigPoint configPoint : configTemplate.getConfigPoints()) {
			if (!deviceConfigRepository.isValidPoint(parentNodeId, configPoint)) {
				String errMsg = "Config point " + configPoint.getPointId()
						+ " is not a valid point in protocol "
						+ configTemplate.getDeviceProtocol() + " version "
						+ configTemplate.getDeviceProtocolVersion();
				LOGGER.error(errMsg);
				throw new DeviceCloudException(
						DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
						configPoint.getPointName());
			}
			/*
			 * if(StringUtils.isNotBlank(configPoint.getParameter())){ if
			 * (!deviceConfigRepository.isValidParameter(subId, configPoint)) {
			 * String errMsg = " Parameter " + configPoint.getParameter() +
			 * " is not a valid parameter of subscription " + subId;
			 * LOGGER.error(errMsg); throw new DeviceCloudException(
			 * DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			 * configPoint.getParameter()); } }
			 */
		}
	}

	private void validatePhysicalQuantity(String physicalQuantity) {
		if (!phyQuantityService.isPhysicalQuantityExist(physicalQuantity)) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					PHYSICAL_QUANTITY.getDescription(), physicalQuantity);
		}
	}

	private void validatePhysicalQuantity(String physicalQuantity, String unit) {
		if (!phyQuantityService.isPhysicalQuantityValid(physicalQuantity, unit)) {
			throw new DeviceCloudException(UNIT.getDescription() + " " + unit
					+ " for the physical quantity " + physicalQuantity,
					INVALID_DATA_SPECIFIED, ErrorAppendMode.SUFFFIX);
		}
	}

	private void validatePhysicalQuantity(String physicalQuantity, String unit,
			DataTypes dataType) {
		/*
		 * if (!phyQuantityService.isPhysicalQuantityValid(physicalQuantity,
		 * unit, dataType)) { throw new
		 * DeviceCloudException(UNIT.getDescription() + " " + unit +
		 * " or data type " + dataType.getDataType() +
		 * " for the physical quantity " + physicalQuantity,
		 * INVALID_DATA_SPECIFIED, ErrorAppendMode.SUFFFIX); }
		 */

		if (!phyQuantityService.isPhysicalQuantityValidByDataType(
				physicalQuantity, unit, dataType)) {
			throw new DeviceCloudException(UNIT.getDescription() + " " + unit
					+ " or data type " + dataType.getDataType()
					+ " for the physical quantity " + physicalQuantity,
					INVALID_DATA_SPECIFIED, ErrorAppendMode.SUFFFIX);
		}
	}

	private void validateUnit(String unit) {
		if (!unitService.isUnitExist(unit)) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					UNIT.getDescription());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.services.SystemService#getAllConfTemplates(java.lang
	 * .String, com.pcs.datasource.dto.ConfigurationSearch)
	 */
	@Override
	public List<DeviceConfigTemplate> getAllConfTemplates(String subId,
			ConfigurationSearch conSearch) {
		validateMandatoryField(SUBSCRIPTION, subId);
		if (conSearch != null && conSearch.getMake() != null) {
			validateMandatoryFields(conSearch, MAKE, DEVICE_TYPE, MODEL,
					PROTOCOL, VERSION);
		}
		List<DeviceConfigTemplate> allConfTemplates = deviceConfigRepository
				.getAllConfTemplates(subId, conSearch);
		validateResult(allConfTemplates);
		return allConfTemplates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.services.SystemService#getConfTemplate(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public DeviceConfigTemplate getConfTemplate(String subId,
			String templateName) {
		validateMandatoryField(SUBSCRIPTION, subId);
		validateMandatoryField(CONFIG_TEMP_NAME, templateName);
		String decodedTempName = null;
		try {
			decodedTempName = URLDecoder.decode(templateName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		DeviceConfigTemplate confTemplate = deviceConfigRepository
				.getConfTemplate(subId, decodedTempName);
		validateResult(confTemplate);
		return confTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.services.SystemService#inActivateConfigTemplates(java
	 * .lang.String, java.util.List)
	 */
	@Override
	public StatusMessageDTO inActivateConfigTemplates(String subId,
			List<String> confTemplates) {
		validateMandatoryField(SUBSCRIPTION, subId);
		ValidationUtils.validateCollection(CONFIG_TEMP_NAME, confTemplates);
		deviceConfigRepository.detachAllDevice(subId, confTemplates);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.services.DeviceConfigService#updateDeviceConfiguration
	 * (java.lang.String, java.util.List, com.pcs.datasource.dto.Subscription)
	 */
	@Override
	public GeneralResponse updateDeviceConfiguration(String sourceId,
			List<ConfigPoint> configPoints) {
		validateMandatoryField(SOURCE_ID, sourceId);
		validateCollection(CONFIG_POINTS, configPoints);

		Device device = deviceService.getDevice(sourceId);
		if (device == null) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					SOURCE_ID.getDescription());
		}

		return updateDeviceConfiguration(device, configPoints);
	}

	@Override
	public GeneralResponse updateDeviceConfiguration(Subscription subscription,
			String sourceId, List<ConfigPoint> configPoints) {
		validateMandatoryField(SOURCE_ID, sourceId);
		validateCollection(CONFIG_POINTS, configPoints);
		Device device = null;
		try {
			device = deviceService.getDevice(sourceId, subscription);
		} catch (DeviceCloudException exception) {
			LOGGER.error(SOURCE_ID.getDescription() + " " + sourceId + " is invalid");
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					SOURCE_ID.getDescription());
		}
		return updateDeviceConfiguration(device, configPoints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.services.SystemService#assignConfPointsToDevices(com
	 * .pcs.datasource.dto.DeviceConfigTemplate, java.util.List)
	 */
	@Override
	public GeneralBatchResponse assignConfPointsToDevices(
			ConfigureDevice configureDevice, Subscription subscription) {

		validateMandatoryFields(configureDevice, SOURCE_IDS, CONFIG_TEMPLATE);
		validateCollection(SOURCE_IDS, configureDevice.getSourceIds());

		DeviceConfigTemplate confTemplate = configureDevice.getConfTemplate();

		validateMandatoryFields(confTemplate, CONFIG_POINTS);
		validateCollection(CONFIG_POINTS, confTemplate.getConfigPoints());
		validateConfigPoints(confTemplate.getConfigPoints());

		String templateName = confTemplate.getName();

		if (subscription != null && subscription.getSubId() != null
				&& templateName != null) {
			confTemplate.setSubId(subscription.getSubId());
			StatusMessageDTO deviceConfigTempExist = isDeviceConfigTempExist(
					confTemplate.getSubId(), templateName);
			if (deviceConfigTempExist == null
					|| deviceConfigTempExist.getStatus() == FALSE) {
				throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
						TEMPLATE_NAME.getDescription());
			}
		}

		ConfigurationSearch configSearch = getConfigSearch(confTemplate);

		List<ConfigPoint> configPoints = confTemplate.getConfigPoints();

		GeneralBatchResponse generalBatchResponse = new GeneralBatchResponse();
		List<GeneralResponse> generalResponses = new ArrayList<>();
		generalBatchResponse.setResponses(generalResponses);

		for (String sourceId : configureDevice.getSourceIds()) {
			if (!isDeviceConfigurable(sourceId, configSearch, subscription)) {
				generalResponses.add(addRemark(sourceId,
						"Device source id is invalid"));
			}
		}
		if (CollectionUtils.isNotEmpty(generalResponses)) {
			return generalBatchResponse;
		}

		// Validate configPoints if protocol version does not have points under
		// it,this means the devices are categorized as dump

		List<ConfigPoint> pointOfProtocol = deviceConfigRepository
				.getPointsOfAProtocolVersion(configSearch);

		boolean isDump = false;

		if (CollectionUtils.isNotEmpty(pointOfProtocol)) {

			isDump = true;

			for (ConfigPoint configPoint : configPoints) {

				if (isEmpty(configPoint.getPointId())) {
					generalResponses.add(addRemark(configPoint,
							"Point Id not specified"));
				} else if (isEmpty(configPoint.getPointName())) {
					generalResponses.add(addRemark(configPoint,
							"Point Name not specified"));
				} else if (isEmpty(configPoint.getDisplayName())) {
					generalResponses.add(addRemark(configPoint,
							"Display Name not specified"));
				} else if (isEmpty(configPoint.getPrecedence())) {
					generalResponses.add(addRemark(configPoint,
							"Precedence not specified"));
				} else if (!deviceConfigRepository.isValidPoint(configSearch,
						configPoint)) {
					generalResponses.add(addRemark(configPoint,
							"Point is invalid"));
				}
			}
			if (CollectionUtils.isNotEmpty(generalResponses)) {
				return generalBatchResponse;
			}

		}
		return deviceConfigRepository.assignConfigPointToDevices(confTemplate,
				configureDevice.getSourceIds(), isDump);
	}

	private GeneralResponse updateDeviceConfiguration(Device device,
			List<ConfigPoint> configPoints) {
		ConfigureDevice configureDevice = new ConfigureDevice();
		Set<String> sourceIds = new HashSet<String>();
		sourceIds.add(device.getSourceId());
		configureDevice.setSourceIds(sourceIds);
		DeviceConfigTemplate confTemplate = new DeviceConfigTemplate();

		ConfigurationSearch version = device.getVersion();
		confTemplate.setDeviceMake(version.getMake());
		confTemplate.setDeviceType(version.getDeviceType());
		confTemplate.setDeviceModel(version.getModel());
		confTemplate.setDeviceProtocol(version.getProtocol());
		confTemplate.setDeviceProtocolVersion(version.getVersion());

		confTemplate.setConfigPoints(configPoints);
		confTemplate.setSubId(device.getSubscription().getSubId());
		configureDevice.setConfTemplate(confTemplate);

		GeneralBatchResponse batchResponse = assignConfPointsToDevices(
				configureDevice, device.getSubscription());

		return batchResponse.getResponses().get(0);
	}

	private ConfigurationSearch getConfigSearch(DeviceConfigTemplate configTemp) {
		ConfigurationSearch configSearch = new ConfigurationSearch();
		configSearch.setMake(configTemp.getDeviceMake());
		configSearch.setDeviceType(configTemp.getDeviceType());
		configSearch.setModel(configTemp.getDeviceModel());
		configSearch.setProtocol(configTemp.getDeviceProtocol());
		configSearch.setVersion(configTemp.getDeviceProtocolVersion());
		return configSearch;
	}

	private GeneralResponse addRemark(ConfigPoint configPoint, String remark) {
		GeneralResponse generalResponse = new GeneralResponse();
		generalResponse.setReference(configPoint.getPointId());
		generalResponse.setRemarks(remark);
		return generalResponse;
	}

	private GeneralResponse addRemark(String reference, String remark) {
		GeneralResponse generalResponse = new GeneralResponse();
		generalResponse.setReference(reference);
		generalResponse.setRemarks(remark);
		return generalResponse;
	}

	@Override
	public GeneralBatchResponse assignTemplateToDevices(
			TemplateAssign templateAssign, Subscription subscription) {
		validateMandatoryFields(templateAssign, SOURCE_IDS, TEMPLATE_NAME);
		validateCollection(SOURCE_IDS, templateAssign.getSourceIds());

		StatusMessageDTO isExist = isDeviceConfigTempExist(
				subscription.getSubId(), templateAssign.getTemplateName());
		if (isExist == null || isExist.getStatus() == FALSE) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					TEMPLATE_NAME.getDescription());
		}

		DeviceConfigTemplate confTemplate = deviceConfigRepository
				.getConfTemplate(subscription.getSubId(),
						templateAssign.getTemplateName());
		ConfigurationSearch configSearch = getConfigSearch(confTemplate);
		GeneralBatchResponse generalBatchResponse = new GeneralBatchResponse();
		List<GeneralResponse> generalResponses = new ArrayList<>();
		generalBatchResponse.setResponses(generalResponses);
		for (String sourceId : templateAssign.getSourceIds()) {
			if (!isDeviceConfigurable(sourceId, configSearch, subscription)) {
				generalResponses.add(addRemark(sourceId,
						"Device source id is invalid"));
			}
		}
		if (CollectionUtils.isNotEmpty(generalResponses)) {
			return generalBatchResponse;
		}
		return deviceConfigRepository.assignTemplateToDevices(
				templateAssign.getTemplateName(),
				templateAssign.getSourceIds(), subscription);
	}

	private boolean isDeviceConfigurable(String sourceId,
			ConfigurationSearch configSearch, Subscription subscription) {
		Device device = null;
		try {
			device = deviceService.getDevice(sourceId, subscription);
		} catch (DeviceCloudException e) {
			LOGGER.error("Error occurred in finding device {}", sourceId);
		}
		if (device == null) {
			return false;
		}
		if (!(device.getVersion().getMake().equals(configSearch.getMake())
				&& device.getVersion().getDeviceType()
						.equals(configSearch.getDeviceType())
				&& device.getVersion().getModel()
						.equals(configSearch.getModel())
				&& device.getVersion().getProtocol()
						.equals(configSearch.getProtocol()) && device
				.getVersion().getVersion().equals(configSearch.getVersion()))) {
			return false;
		}
		return true;
	}
}

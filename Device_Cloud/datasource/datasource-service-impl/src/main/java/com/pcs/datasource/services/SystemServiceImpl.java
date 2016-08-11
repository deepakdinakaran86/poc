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

import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_MAKE;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_MODEL;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_PROTOCOL;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_TYPE;
import static com.pcs.datasource.enums.DeviceConfigFields.PROTOCOL_VERSION;
import static com.pcs.datasource.enums.DeviceDataFields.MAKE;
import static com.pcs.datasource.enums.DeviceDataFields.MODEL;
import static com.pcs.datasource.enums.DeviceDataFields.PROTOCOL;
import static com.pcs.datasource.enums.DeviceDataFields.VERSION;
import static com.pcs.datasource.repository.utils.DBConstansts.ACCESS_TYPE;
import static com.pcs.datasource.repository.utils.DBConstansts.DATA_TYPE;
import static com.pcs.datasource.repository.utils.DBConstansts.NW_PROTOCOL;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateResult;
import static com.pcs.devicecloud.enums.Status.SUCCESS;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.AccessType;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DataType;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.Make;
import com.pcs.datasource.dto.Model;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.SystemTag;
import com.pcs.datasource.repository.SystemRepository;
import com.pcs.datasource.repository.utils.DBConstansts;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for service implementation of services related to device cloud system
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	@Qualifier("systemRepoNeo4j")
	SystemRepository systemRepository;

	@Override
	public List<DataType> getAllDataTypes() {
		List<DataType> dataTypes = systemRepository.getAllOfAType(DATA_TYPE,
				DataType.class);
		validateResult(dataTypes);
		return dataTypes;
	}

	@Override
	public List<AccessType> getAllAccessTypes() {
		List<AccessType> accessTypes = systemRepository.getAllOfAType(
				ACCESS_TYPE, AccessType.class);
		validateResult(accessTypes);
		return accessTypes;
	}

	@Override
	public List<NetworkProtocol> getAllNwProtocols() {
		List<NetworkProtocol> nwProtocols = systemRepository.getAllOfAType(
				NW_PROTOCOL, NetworkProtocol.class);
		validateResult(nwProtocols);
		return nwProtocols;
	}

	@Override
	public List<SystemTag> getAllSystemTags(String physicalQty) {
		List<SystemTag> allSystemTags = systemRepository
				.getAllSystemTags(physicalQty);
		validateResult(allSystemTags);
		return allSystemTags;
	}

	@Override
	public StatusMessageDTO createDeviceMake(ConfigurationSearch configuration) {
		validateMandatoryField(MAKE, configuration.getMake());
		if (systemRepository.isDeviceMakeExist(configuration.getMake())) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					MAKE.getDescription());
		}
		try {
			systemRepository.createDeviceMake(configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO updateDeviceMake(String existingMakeName, ConfigurationSearch configuration) {
		validateMandatoryField(MAKE, existingMakeName);
		validateMandatoryField(MAKE, configuration.getMake());
		if (!systemRepository.isDeviceMakeExist(existingMakeName)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED,
					MAKE.getDescription() + " " + existingMakeName
							+ " don't exist.");
		}
		if (systemRepository.isDeviceMakeExist(configuration.getMake())) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					MAKE.getDescription() + " " + configuration.getMake() + " ");
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			systemRepository.updateDeviceMake(existingMakeName, configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO createDeviceModel(ConfigurationSearch configuration) {
		validateMandatoryField(MODEL, configuration.getModel());
		if (systemRepository.isDeviceModelExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					MODEL.getDescription());
		}
		try {
			systemRepository.createDeviceModel(configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO updateDeviceModel(String existingModelName,
			ConfigurationSearch configuration) {
		validateMandatoryField(MODEL, existingModelName);
		validateMandatoryField(MODEL, configuration.getModel());
		if (!systemRepository.isDeviceModelExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED,
					MODEL.getDescription() + " " + existingModelName
							+ " don't exist.");
		}
		if (systemRepository.isDeviceModelExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					MODEL.getDescription() + " " + configuration.getModel() + " ");
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			systemRepository.updateDeviceModel(existingModelName, configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO createDeviceType(ConfigurationSearch configuration) {
		validateMandatoryField(DEVICE_TYPE, configuration.getDeviceType());
		if (systemRepository.isDeviceTypeExist(configuration.getDeviceType())) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					DEVICE_TYPE.getDescription());
		}
		try {
			systemRepository.createDeviceType(configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO updateDeviceType(String existingDeviceTypeName,
			ConfigurationSearch configuration) {
		validateMandatoryField(DEVICE_TYPE, existingDeviceTypeName);
		validateMandatoryField(DEVICE_TYPE, configuration.getDeviceType());
		if (!systemRepository.isDeviceTypeExist(existingDeviceTypeName)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED,
					DEVICE_TYPE.getDescription() + " " + existingDeviceTypeName
							+ " don't exist.");
		}
		if (systemRepository.isDeviceTypeExist(configuration.getDeviceType())) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					DEVICE_TYPE.getDescription() + " " + configuration.getDeviceType()
							+ " ");
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			systemRepository.updateDeviceType(existingDeviceTypeName,
					configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO createDeviceProtocol(
			ConfigurationSearch configuration) {
		validateMandatoryFields(configuration, MAKE, MAKE,
				MODEL, PROTOCOL);
		if (systemRepository.isDeviceProtocolExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					PROTOCOL.getDescription());
		}
		try {
			systemRepository.createDeviceProtocol(configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO updateDeviceProtocol(
			String existingDeviceProtocolName,
			ConfigurationSearch configuration) {
		validateMandatoryField(PROTOCOL, existingDeviceProtocolName);
		validateMandatoryFields(configuration, MAKE, MAKE,
				MODEL, PROTOCOL);
		validateMandatoryField(DEVICE_PROTOCOL, configuration.getProtocol());
		if (!systemRepository.isDeviceProtocolExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED,
					PROTOCOL.getDescription() + " "
							+ existingDeviceProtocolName + " don't exist.");
		}
		if (systemRepository.isDeviceProtocolExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					PROTOCOL.getDescription() + " "
							+ configuration.getProtocol() + " ");
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			systemRepository.updateDeviceProtocol(existingDeviceProtocolName,
					configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO createDeviceProtocolVersion(
			ConfigurationSearch configuration) {
		validateMandatoryFields(configuration, MAKE,
				DEVICE_TYPE, MODEL, PROTOCOL, VERSION);
		if (systemRepository
				.isDeviceProtocolVersionExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					VERSION.getDescription());
		}
		try {
			systemRepository.createDeviceProtocolVersion(configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO updateDeviceProtocolVersion(
			String existingDeviceProtocolVersion,
			ConfigurationSearch configuration) {
		validateMandatoryField(DEVICE_PROTOCOL, existingDeviceProtocolVersion);
		validateMandatoryFields(configuration, MAKE, DEVICE_TYPE,
				MODEL, PROTOCOL,VERSION);
		validateMandatoryField(VERSION, configuration.getProtocol());
		if (!systemRepository.isDeviceProtocolVersionExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED,
					VERSION.getDescription() + " "
							+ existingDeviceProtocolVersion + " don't exist.");
		}
		if (systemRepository.isDeviceProtocolVersionExist(configuration)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
					VERSION.getDescription() + " "
							+ configuration.getProtocol() + " ");
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			systemRepository.updateDeviceProtocolVersion(existingDeviceProtocolVersion,
					configuration);
		} catch (DeviceCloudException ex) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, ex);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * Service Method to get all device makes
	 * 
	 * @return List<Make>
	 */
	public List<Make> getDeviceMakes() {
		List<Make> makes = systemRepository.getAllOfAType(DBConstansts.MAKE,
				Make.class);
		validateResult(makes);
		return makes;
	}

	/**
	 * Service Method to get device types of a device make.
	 * 
	 * @param makeName
	 * @return List<DeviceType>
	 */
	public List<DeviceType> getDeviceTypes(String makeName) {
		validateMandatoryField(MAKE, makeName);
		try {
			makeName = URLDecoder.decode(makeName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					MAKE.getDescription());
		}
		List<DeviceType> deviceTypes = systemRepository
				.getDeviceTypes(makeName);
		validateResult(deviceTypes);
		return deviceTypes;
	}

	/**
	 * Service Method to get device models of a device type
	 * 
	 * @param configuration
	 * @return List<Model>
	 */
	public List<Model> getDeviceModels(ConfigurationSearch configuration) {
		validateMandatoryFields(configuration, MAKE, DEVICE_TYPE);
		List<Model> modeles = systemRepository.getDeviceModels(configuration);
		validateResult(modeles);
		return modeles;
	}

	/**
	 * Service Method to get device models of a device type
	 * 
	 * @param configuration
	 * @return List<DeviceProtocol>
	 */
	public List<DeviceProtocol> getDeviceProtocols(
			ConfigurationSearch configuration) {
		validateMandatoryFields(configuration, MAKE, DEVICE_TYPE, MODEL);
		List<DeviceProtocol> protocols = systemRepository
				.getDeviceProtocols(configuration);
		validateResult(protocols);
		return protocols;
	}

	/**
	 * Service Method to get device models of a device type
	 * 
	 * @param configuration
	 * @return List<ProtocolVersion>
	 */
	public List<ProtocolVersion> getProtocolVersions(
			ConfigurationSearch configuration) {
		validateMandatoryFields(configuration, MAKE, DEVICE_TYPE, MODEL,
				PROTOCOL);
		List<ProtocolVersion> versions = systemRepository
				.getProtocolVersions(configuration);
		validateResult(versions);
		return versions;
	}

	/**
	 * Service Method to get device pints of a protocol version
	 * 
	 * @param configuration
	 * @return List<Point>
	 */
	public List<Point> getProtocolVersionPoint(ConfigurationSearch configuration) {
		validateMandatoryFields(configuration, MAKE, DEVICE_TYPE, MODEL,
				PROTOCOL, VERSION);
		List<Point> points = systemRepository
				.getProtocolVersionPoint(configuration);
		validateResult(points);
		return points;
	}

}

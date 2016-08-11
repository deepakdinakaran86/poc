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

import static com.pcs.datasource.enums.DeviceDataFields.DEVICE_TYPE;
import static com.pcs.datasource.enums.DeviceDataFields.MAKE;
import static com.pcs.datasource.enums.DeviceDataFields.MODEL;
import static com.pcs.datasource.enums.DeviceDataFields.PROTOCOL;
import static com.pcs.datasource.enums.DeviceDataFields.VERSION;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.DATA_NOT_AVAILABLE;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.CommandType;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.repository.DeviceProtocolRepository;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Service
public class DeviceProtocolServiceImpl implements DeviceProtocolService {

	@Autowired
	@Qualifier("dpNeo")
	private DeviceProtocolRepository deviceProtocolRepository;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceProtocolService#updateDeviceRelation
	 * (com.pcs.datasource.dto.dc.Device)
	 */
	@Override
	public void updateDeviceRelation(Device device) {
		deviceProtocolRepository.updateDeviceRelation(device);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceProtocolService#getDeviceProtocol(java
	 * .lang.String)
	 */
	@Override
	public ProtocolVersion getDeviceProtocolVersion(ConfigurationSearch version) {
		return deviceProtocolRepository.getDeviceProtocolVersion(version);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceProtocolService#getCommandsOfDeviceProtocol
	 * (java.lang.String, java.lang.String)
	 */
	public List<CommandType> getCommandsOfDeviceProtocol(
	        ConfigurationSearch configuration) {
		validateMandatoryFields(configuration, MAKE, DEVICE_TYPE, MODEL,
		        PROTOCOL, VERSION);
		List<CommandType> deviceCommands = deviceProtocolRepository
		        .getCommandsOfDeviceProtocol(configuration);
		if (CollectionUtils.isEmpty(deviceCommands)) {
			throw new DeviceCloudException(DATA_NOT_AVAILABLE);
		}
		return deviceCommands;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceProtocolService#recycleUnitId(com.pcs
	 * .datasource.dto.Device)
	 */
	@Override
	public void recycleUnitId(Device device) {
		deviceProtocolRepository.recycleUnitId(device);
	}

}

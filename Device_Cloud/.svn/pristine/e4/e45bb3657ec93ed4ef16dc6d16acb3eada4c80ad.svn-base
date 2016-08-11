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
package com.pcs.galaxy.serviceimpl;

import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.galaxy.enums.DeviceDataFields.ASSET_NAME;
import static com.pcs.galaxy.enums.DeviceDataFields.DISPLAY_NAME;
import static com.pcs.galaxy.enums.DeviceDataFields.POINTNAME;
import static com.pcs.galaxy.enums.DeviceDataFields.POINT_ID;
import static com.pcs.galaxy.enums.DeviceDataFields.SOURCE_ID;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.enums.Status;
import com.pcs.galaxy.conf.CEPMessageConverter;
import com.pcs.galaxy.dto.DeviceThresholdDTO;
import com.pcs.galaxy.repository.DeviceThresholdRepository;
import com.pcs.galaxy.repository.model.ConfigurationId;
import com.pcs.galaxy.repository.model.DeviceThreshold;
import com.pcs.galaxy.services.DeviceThresholdService;

/**
 * DeviceThresholdServiceImpl
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class DeviceThresholdServiceImpl implements DeviceThresholdService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DeviceThresholdServiceImpl.class);

	@Autowired
	DeviceThresholdRepository deviceThresholdRepository;

	@Autowired
	CEPMessageConverter messageConverter;

	@Transactional(propagation = Propagation.REQUIRED)
	public DeviceThresholdDTO saveThreshold(DeviceThresholdDTO threshold) {
		validateMandatoryFields(threshold, SOURCE_ID, POINT_ID, POINTNAME,
		        DISPLAY_NAME);
		isExistThreshold(threshold);
		DeviceThreshold model = deviceThresholdDtoToModel(threshold);
		deviceThresholdRepository.save(model);
		return threshold;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public StatusMessageDTO updateThreshold(DeviceThresholdDTO threshold) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.SUCCESS);
		validateMandatoryFields(threshold, DISPLAY_NAME);
		DeviceThreshold model = validateThreshold(threshold);
		DeviceThreshold updatedModel = updateDtoToModel(model, threshold);
		deviceThresholdRepository.update(updatedModel);
		return status;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public StatusMessageDTO deleteThreshold(DeviceThresholdDTO threshold) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.SUCCESS);
		DeviceThreshold model = validateThreshold(threshold);
		deviceThresholdRepository.delete(model);
		return status;
	}

	private void isExistThreshold(DeviceThresholdDTO threshold) {
		validateMandatoryFields(threshold, SOURCE_ID, POINT_ID, POINTNAME);
		DeviceThreshold model = deviceThresholdDtoToModel(threshold);
		DeviceThreshold dbThreshold = deviceThresholdRepository
		        .getThreshold(model.getId());
		if (dbThreshold != null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST, "Configuration");
		}
	}

	private DeviceThreshold validateThreshold(DeviceThresholdDTO threshold) {
		validateMandatoryFields(threshold, SOURCE_ID, POINT_ID, POINTNAME);
		DeviceThreshold model = deviceThresholdDtoToModel(threshold);
		DeviceThreshold dbThreshold = deviceThresholdRepository
		        .getThreshold(model.getId());
		if (dbThreshold == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return dbThreshold;
	}

	public List<DeviceThresholdDTO> getThresholdForAsset(
	        DeviceThresholdDTO threshold) {
		validateMandatoryFields(threshold, ASSET_NAME);
		List<DeviceThreshold> dbThresholds = deviceThresholdRepository
		        .getForDevice(threshold.getAssetName());
		List<DeviceThresholdDTO> thresholdDtos = null;
		for (DeviceThreshold dbThreshold : dbThresholds) {
			if (thresholdDtos == null)
				thresholdDtos = new ArrayList<DeviceThresholdDTO>();
			DeviceThresholdDTO thresholdDto = deviceThresholdModelToDto(dbThreshold);
			thresholdDtos.add(thresholdDto);
		}
		if (thresholdDtos == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return thresholdDtos;
	}

	public DeviceThresholdDTO getThreshold(DeviceThresholdDTO threshold) {
		validateMandatoryFields(threshold, SOURCE_ID, POINT_ID, POINTNAME);
		ConfigurationId config = new ConfigurationId();
		config.setPointid(threshold.getPointId());
		config.setPointname(threshold.getPointName());
		config.setSourceId(threshold.getSourceId());

		DeviceThreshold dbModel = deviceThresholdRepository
		        .getThreshold(config);
		if (dbModel == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		DeviceThresholdDTO thresholdDto = deviceThresholdModelToDto(dbModel);
		return thresholdDto;
	}

	private DeviceThresholdDTO deviceThresholdModelToDto(DeviceThreshold model) {
		DeviceThresholdDTO dto = null;
		if (model != null) {
			dto = new DeviceThresholdDTO();
			ConfigurationId id = model.getId();
			dto.setPointId(id.getPointid());
			dto.setPointName(id.getPointname());
			dto.setSourceId(id.getSourceId());
			dto.setDeviceId(model.getDeviceId());
			dto.setAssetId(model.getAssetId());
			dto.setAssetName(model.getAssetName());
			dto.setUnit(model.getUnit());
			dto.setMinVal(model.getMinVal());
			dto.setMaxVal(model.getMaxVal());
			dto.setLowerRange(model.getLowerRange());
			dto.setUpperRange(model.getUpperRange());
			dto.setAlarmName(model.getAlarmName());
			dto.setDisplayName(model.getDisplayName());
			dto.setMessage(model.getMessage());
			dto.setMinAlarmMsg(model.getMinAlarmMsg());
			dto.setMaxAlarmMsg(model.getMaxAlarmMsg());
			dto.setRangeAlarmMsg(model.getRangeAlarmMsg());
			dto.setStatus(model.getStatus());
			dto.setEnabled(model.isEnabled());;

		}
		return dto;
	}

	private DeviceThreshold updateDtoToModel(DeviceThreshold model,
	        DeviceThresholdDTO dto) {
		if (dto != null) {
			model.setDeviceId(dto.getDeviceId());
			model.setAssetId(dto.getAssetId());
			model.setAssetName(dto.getAssetName());
			model.setUnit(dto.getUnit());
			model.setMinVal(dto.getMinVal());
			model.setMaxVal(dto.getMaxVal());
			model.setLowerRange(dto.getLowerRange());
			model.setUpperRange(dto.getUpperRange());
			model.setAlarmName(dto.getAlarmName());
			model.setDisplayName(dto.getDisplayName());
			model.setMessage(dto.getMessage());
			model.setMinAlarmMsg(dto.getMinAlarmMsg());
			model.setMaxAlarmMsg(dto.getMaxAlarmMsg());
			model.setRangeAlarmMsg(dto.getRangeAlarmMsg());
			model.setStatus(dto.getStatus());
			model.setEnabled(dto.isEnabled());;
		}
		return model;
	}

	private DeviceThreshold deviceThresholdDtoToModel(DeviceThresholdDTO dto) {
		DeviceThreshold model = null;
		if (dto != null) {
			model = new DeviceThreshold();

			ConfigurationId id = new ConfigurationId();
			id.setPointid(dto.getPointId());
			id.setPointname(dto.getPointName());
			id.setSourceId(dto.getSourceId());
			model.setId(id);

			model.setDeviceId(dto.getDeviceId());
			model.setAssetId(dto.getAssetId());
			model.setAssetName(dto.getAssetName());
			model.setUnit(dto.getUnit());
			model.setMinVal(dto.getMinVal());
			model.setMaxVal(dto.getMaxVal());
			model.setLowerRange(dto.getLowerRange());
			model.setUpperRange(dto.getUpperRange());
			model.setAlarmName(dto.getAlarmName());
			model.setDisplayName(dto.getDisplayName());
			model.setAlarmAttribute(getAlarmAttribute(dto.getDisplayName()));
			model.setMessage(dto.getMessage());
			model.setMinAlarmMsg(dto.getMinAlarmMsg());
			model.setMaxAlarmMsg(dto.getMaxAlarmMsg());
			model.setRangeAlarmMsg(dto.getRangeAlarmMsg());
			model.setStatus(dto.getStatus());
			model.setEnabled(dto.isEnabled());;
		}
		return model;
	}

	private String getAlarmAttribute(String displayName) {

		String alarmAttribute = CEPMessageConverter
		        .getCEPInputMessage(displayName);
		if (StringUtils.isEmpty(alarmAttribute)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        DISPLAY_NAME.getDescription());
		}
		return alarmAttribute;
	}
}

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

import static com.pcs.datasource.enums.AlarmDataFields.ALARM_MESSAGE;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_STATUS;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_TIME;
import static com.pcs.datasource.enums.DeviceDataFields.DATE_RANGE;
import static com.pcs.datasource.enums.DeviceDataFields.DEVICE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.DISPLAY_NAMES;
import static com.pcs.datasource.enums.DeviceDataFields.END_DATE;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.START_DATE;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.pcs.datasource.dto.AlarmDataResponse;
import com.pcs.datasource.dto.AlarmMessage;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.SearchDTO;
import com.pcs.datasource.repository.AlarmDataRepo;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Jul 19, 2015
 */
@Service
public class AlarmServiceImpl implements AlarmService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AlarmServiceImpl.class);

	@Autowired
	AlarmDataRepo alarmDataRepo;

	@Autowired
	DeviceService deviceService;

	@Autowired
	DeviceRepository deviceRepo;

	@Override
	public StatusMessageDTO saveAlarm(AlarmMessage alarmMessage) {
		validateAlarmMessage(alarmMessage);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		try {
			alarmDataRepo.insert(alarmMessage);
		} catch (Exception e) {
			statusMessageDTO.setStatus(Status.FAILURE);
			LOGGER.error("Error saving alarm data of device {} {}",
			        alarmMessage.getSourceId(), e);
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO batchSaveAlarm(List<AlarmMessage> alarmMessages) {
		return null;
	}

	@Override
	public AlarmDataResponse getAlarms(SearchDTO searchDTO) {
		Device device = null;
		validateMandatoryFields(searchDTO, SOURCE_ID, START_DATE, END_DATE,
		        DISPLAY_NAMES);
		if (CollectionUtils.isEmpty(searchDTO.getDisplayNames())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        DISPLAY_NAMES.getDescription());
		}
		try {
			device = deviceService.getDevice(searchDTO.getSourceId());
		} catch (
		        IllegalArgumentException | DeviceCloudException
		        | NullPointerException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		DateTime startDateValue = new DateTime(searchDTO.getStartDate())
		        .toDateTime(DateTimeZone.UTC);
		DateTime endDateValue = new DateTime(searchDTO.getEndDate())
		        .toDateTime(DateTimeZone.UTC);
		validateDateRange(startDateValue, endDateValue);

		AlarmDataResponse response = null;
		try {
			response = alarmDataRepo.getAlarms(device.getDeviceId(),
			        startDateValue, endDateValue, searchDTO.getDisplayNames());
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        DEVICE_ID.getDescription());
		}
		if (response == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
		response.setSourceId(searchDTO.getSourceId());
		return response;
	}

	@Override
	public AlarmDataResponse getAllAlarms(SearchDTO searchDTO) {
		Device device = null;
		validateMandatoryFields(searchDTO, SOURCE_ID, START_DATE, END_DATE);

		try {
			device = deviceService.getDevice(searchDTO.getSourceId());
		} catch (
		        IllegalArgumentException | DeviceCloudException
		        | NullPointerException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		DateTime startDateValue = new DateTime(searchDTO.getStartDate())
		        .toDateTime(DateTimeZone.UTC);
		DateTime endDateValue = new DateTime(searchDTO.getEndDate())
		        .toDateTime(DateTimeZone.UTC);
		validateDateRange(startDateValue, endDateValue);

		List<String> pointNames = new ArrayList<String>();
		if (device.getConfigurations() != null) {
			for (ConfigPoint configPoint : device.getConfigurations()
			        .getConfigPoints()) {
				pointNames.add(configPoint.getDisplayName());
			}
		} else {
			LOGGER.info("No point configuration available for device {}",
			        searchDTO.getSourceId());
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}

		AlarmDataResponse response = null;
		try {
			response = alarmDataRepo.getAlarms(device.getDeviceId(),
			        startDateValue, endDateValue, pointNames);
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        DEVICE_ID.getDescription());
		}
		if (response == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
		response.setSourceId(searchDTO.getSourceId());
		return response;
	}

	private void validateAlarmMessage(AlarmMessage alarmMessage) {
		validateMandatoryFields(alarmMessage, SOURCE_ID, ALARM_STATUS,
		        ALARM_MESSAGE, ALARM_TIME);
		Device device = null;
		try {
			device = deviceService.getDevice(alarmMessage.getSourceId());
			alarmMessage.setSourceId(device.getDeviceId());
		} catch (DeviceCloudException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
	}

	/**
	 * validate the date range input from the input
	 * 
	 * @param startDateValue
	 * @param endDateValue
	 */
	private void validateDateRange(DateTime startDateValue,
	        DateTime endDateValue) {
		if (Days.daysBetween(startDateValue, endDateValue).getDays() < 0) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        DATE_RANGE.getDescription());
		}
		if (!(startDateValue.withTimeAtStartOfDay().equals(startDateValue))) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        START_DATE.getDescription());
		}
		if (!(endDateValue.withTimeAtStartOfDay().equals(endDateValue))) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        END_DATE.getDescription());
		}

	}

	@Override
	public List<AlarmDataResponse> getAlarmsHistory(
	        List<SearchDTO> searchDTOList) {

		if (CollectionUtils.isEmpty(searchDTOList)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INCOMPLETE_REQUEST);
		}
		Device device = null;
		List<AlarmDataResponse> alarmDataResponses = new ArrayList<AlarmDataResponse>();

		// iterate through list of search DTOs
		for (SearchDTO searchDTO : searchDTOList) {
			validateMandatoryFields(searchDTO, SOURCE_ID, START_DATE, END_DATE,
			        DISPLAY_NAMES);
			if (CollectionUtils.isEmpty(searchDTO.getDisplayNames())) {
				// if displayNames list is empty, skip iteration
				continue;
			}
			DateTime startDateValue = new DateTime(searchDTO.getStartDate())
			        .toDateTime(DateTimeZone.UTC);
			DateTime endDateValue = new DateTime(searchDTO.getEndDate())
			        .toDateTime(DateTimeZone.UTC);
			validateDateRange(startDateValue, endDateValue);
			try {
				device = deviceService.getDevice(searchDTO.getSourceId());
			} catch (
			        IllegalArgumentException | DeviceCloudException
			        | NullPointerException e) {
				continue;
			}

			// get alarm data
			AlarmDataResponse response = alarmDataRepo.getAlarmsHistory(
			        device.getDeviceId(), startDateValue, endDateValue,
			        searchDTO.getDisplayNames());
			if (response != null) {
				response.setSourceId(searchDTO.getSourceId());
				alarmDataResponses.add(response);
			}

		}
		return alarmDataResponses.size() <= 0
		        ? Collections.EMPTY_LIST
		        : alarmDataResponses;
	}

	@Override
	public List<AlarmDataResponse> getLatestAlarms(List<SearchDTO> searchDTOList) {
		if (CollectionUtils.isEmpty(searchDTOList)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INCOMPLETE_REQUEST);
		}
		Device device = null;
		List<AlarmDataResponse> alarmDataResponses = new ArrayList<AlarmDataResponse>();

		// iterate through list of search DTOs
		for (SearchDTO searchDTO : searchDTOList) {

			validateMandatoryFields(searchDTO, SOURCE_ID, DISPLAY_NAMES);
			if (CollectionUtils.isEmpty(searchDTO.getDisplayNames())) {
				// if displayNames list is empty, skip iteration
				continue;
			}

			try {
				device = deviceService.getDevice(searchDTO.getSourceId());
			} catch (
			        IllegalArgumentException | DeviceCloudException
			        | NullPointerException e) {
				continue;
			}
			// get alarm data
			AlarmDataResponse response = alarmDataRepo.getLiveAlarms(
			        device.getDeviceId(), searchDTO.getDisplayNames());
			if (response != null) {
				response.setSourceId(searchDTO.getSourceId());
				alarmDataResponses.add(response);
			}

		}
		return alarmDataResponses.size() <= 0
		        ? Collections.EMPTY_LIST
		        : alarmDataResponses;
	}

}

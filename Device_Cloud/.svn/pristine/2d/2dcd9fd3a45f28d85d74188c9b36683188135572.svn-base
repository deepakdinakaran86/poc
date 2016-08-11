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

import static com.pcs.datasource.enums.DeviceDataFields.END_TIME;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.START_TIME;
import static com.pcs.datasource.enums.DeviceDataFields.WRITEBACK_ID;
import static com.pcs.datasource.enums.PQDataFields.STATUS;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.START_END_DATE;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateResult;
import static com.pcs.devicecloud.enums.Status.FAILURE;
import static com.pcs.devicecloud.enums.Status.QUEUED;
import static com.pcs.devicecloud.enums.Status.SUCCESS;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.PersistenceException;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.writeback.BatchCommand;
import com.pcs.datasource.dto.writeback.DeviceCommand;
import com.pcs.datasource.dto.writeback.WriteBackCommand;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.dto.writeback.WriteBackResponse;
import com.pcs.datasource.publisher.MessagePublisher;
import com.pcs.datasource.repository.WriteBackRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * Service Implementation Class of WriteBackLogs
 * 
 * @author Javid Ahammed (pcseg199)
 * @date May 19, 2015
 * @since galaxy-1.0.0
 */
@Service
public class WriteBackLogServiceImpl implements WriteBackLogService {

	@Qualifier("writeBackRepoNeo4j")
	@Autowired
	private WriteBackRepository writeBackRepository;

	@Autowired
	DeviceService deviceService;

	@Autowired
	MessagePublisher messagePublisher;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(WriteBackLogServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.WriteBackLogService#getAllLogsWithRelation
	 * (java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public JSONArray getAllLogsWithRelation(String subId, Long startTime,
	        Long endTime) {
		// validateMandatoryField(SUB_ID, subId);
		// validateTimePeriod(startTime, endTime);
		//
		// // JSONArray jsonArray =
		// writeBackRepository.getAllLogsWithRelation(subId,
		// // startTime, endTime);
		//
		// validateResult(jsonArray);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.WriteBackLogService#getLogsOfSelectedDevices
	 * (java.util.List, com.pcs.datasource.dto.Subscription, java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public List<WriteBackLog> getLogsOfSubscription(List<String> sourceIds,
	        Subscription sub, Long startTime, Long endTime) {

		validateTimePeriod(startTime, endTime);

		List<WriteBackLog> logs = null;
		if (isEmpty(sourceIds)) {
			logs = writeBackRepository.getAllLogs(sub.getSubId(), startTime,
			        endTime);
		} else {
			logs = writeBackRepository.getLogsOfSelectedDevices(sourceIds,
			        sub.getSubId(), startTime, endTime);
		}
		validateResult(logs);
		return logs;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.WriteBackLogService#insertLog(java.lang.String
	 * , com.pcs.datasource.dto.writeback.Command)
	 */
	@Override
	public WriteBackResponse insertLog(WriteBackLog writeBackLog) {
		DeviceCommand command = null;
		try {
			command = writeBackRepository.insertWriteBackLog(writeBackLog);
		} catch (Exception e) {
			LOGGER.error("Error in Inserting writeBackLog", e);
		}
		if (command == null) {
			return createWriteBackPointResp(writeBackLog.getCommand());
		}
		return createWriteBackPointResp(command);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.WriteBackLogService#updateCommandStatus(com
	 * .pcs.datasource.dto.writeback.Command)
	 */
	@Override
	public StatusMessageDTO updateWriteBack(String sourceId,
	        WriteBackCommand writeBackCommand) {

		validateMandatoryField(SOURCE_ID, sourceId);

		validateMandatoryFields(writeBackCommand, WRITEBACK_ID, STATUS);

		Device device = null;
		try {
			device = deviceService.getDevice(sourceId);
		} catch (DeviceCloudException e) {
			LOGGER.debug("Device Not Found with SourceId:{}", sourceId, e);
		}
		if (device == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		try {
			writeBackCommand.setSourceId(sourceId);
			writeBackRepository.updateWriteBack(sourceId, writeBackCommand);
			String datasourceName = device.getDatasourceName();
			String json = objectBuilderUtil.getGson().toJson(writeBackCommand);
			if (isNotEmpty(datasourceName)) {
				messagePublisher.publishToKafkaTopic(datasourceName, json);
			}
		} catch (PersistenceException e) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        WRITEBACK_ID.getDescription());
		} catch (RemoteException e) {
			LOGGER.error("Error in Publishing to {} with exception {}",
			        device.getDatasourceName(), e);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.WriteBackLogService#insertBatch(java.lang
	 * .String)
	 */
	@Override
	public BatchCommand insertBatch(String sourceId) {
		return writeBackRepository.insertBatch(sourceId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.datasource.services.WriteBackLogService#deleteBatch(com.pcs.
	 * datasource.dto.writeback.BatchCommand)
	 */
	@Override
	public void deleteBatch(BatchCommand batchCommand) {
		writeBackRepository.deleteBatch(batchCommand);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.WriteBackLogService#getCurrentExecuting(java
	 * .lang.String)
	 */
	@Override
	public List<DeviceCommand> getCurrentExecuting(String sourceId) {
		validateMandatoryField(SOURCE_ID, sourceId);
		List<DeviceCommand> currentExecuting = writeBackRepository
		        .getCurrentExecuting(sourceId);
		validateResult(currentExecuting);
		return currentExecuting;
	}

	private WriteBackResponse createWriteBackPointResp(DeviceCommand command) {
		WriteBackResponse writeBackPointResponse = new WriteBackResponse();
		Short writeBackId = command.getWriteBackId();
		if (writeBackId == null || writeBackId < -1) {
			writeBackPointResponse.setStatus(FAILURE);
		} else {
			writeBackPointResponse.setRequestId(command.getWriteBackId());
			writeBackPointResponse.setRequestedAt(command.getRequestedAt());
			writeBackPointResponse.setStatus(QUEUED);
		}
		return writeBackPointResponse;
	}

	private void validateTimePeriod(Long startTime, Long endTime) {
		if (startTime == null) {
			throw new DeviceCloudException(FIELD_DATA_NOT_SPECIFIED,
			        START_TIME.getDescription());
		} else if (startTime < 0) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        START_TIME.getDescription());
		} else if (endTime == null) {
			throw new DeviceCloudException(FIELD_DATA_NOT_SPECIFIED,
			        END_TIME.getDescription());
		} else if (endTime < 0) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        END_TIME.getDescription());
		} else if (startTime > endTime) {
			throw new DeviceCloudException(START_END_DATE);
		}
	}

}

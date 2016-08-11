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

import static com.pcs.datasource.enums.DeviceDataFields.COMMAND;
import static com.pcs.datasource.enums.DeviceDataFields.COMMAND_CODE;
import static com.pcs.datasource.enums.DeviceDataFields.DATA;
import static com.pcs.datasource.enums.DeviceDataFields.PAYLOAD;
import static com.pcs.datasource.enums.DeviceDataFields.POINT_ID;
import static com.pcs.datasource.enums.DeviceDataFields.PRIORITY;
import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.ADDITIONAL_FILTERS;
import static com.pcs.datasource.repository.utils.CypherConstants.CUSTOM_SPECS;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.enums.Status.FAILURE;
import static com.pcs.devicecloud.enums.Status.QUEUED;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.WrieBackCommandDTO;
import com.pcs.datasource.dto.writeback.DeviceCommand;
import com.pcs.datasource.dto.writeback.WriteBackCommand;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.dto.writeback.WriteBackResponse;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.enums.DeviceDataFields;
import com.pcs.datasource.enums.WriteBackCommandCodes;
import com.pcs.datasource.enums.WriteBackCommands;
import com.pcs.datasource.publisher.MessagePublisher;
import com.pcs.datasource.repository.utils.CypherConstants;
import com.pcs.datasource.services.utils.CacheUtility;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date Apr 21, 2015
 * @since galaxy-1.0.0
 */
@Service
public class WriteBackCommandServiceImpl implements WriteBackCommandService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(WriteBackCommandServiceImpl.class);

	@Value("${WriteBack.commands.publish.mechanism}")
	private String publishingMechanism;

	@Value("${WriteBack.commands.publish.topic}")
	private String commandTopicName;

	private static final String JMS = "JMS";
	private static final String KAFKA = "Kafka";

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	private CacheUtility cacheUtility;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private WriteBackLogService writeBackLogService;

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	private static final String checkCommandInProgress = "MATCH (a:DEVICE {source_id:'{source_id}'})-[*]->(b:COMMAND) where b.command='{command}' and b.status='QUEUED' {additionalFilters}  return b";

	private static final String customSpecCheck = "and b.custom_specs='{custom_specs}'";

	private static final String pointIdCheck = "and b.point_id={point_id}";

	@Override
	public List<WriteBackCommand> processCommands(
	        List<WriteBackCommand> writeBackCommands) {

		for (WriteBackCommand writeBackCommand : writeBackCommands) {

			try {
				validateMandatoryFields(writeBackCommand, SOURCE_ID, PAYLOAD);
			} catch (DeviceCloudException e) {
				writeBackCommand.setStatus(FAILURE);
				writeBackCommand.setRemarks(e.getErrorMessageDTO()
				        .getErrorMessage());
				continue;
			}

			Device device = validateSourceId(writeBackCommand.getSourceId(),
			        writeBackCommand);
			if (device == null) {
				continue;
			}

			WriteBackLog writeBackLog = new WriteBackLog();
			writeBackLog.setDevice(device);

			DeviceCommand command = writeBackCommand.getPayload();

			if (StringUtils.isEmpty(command.getCommand())) {
				writeBackCommand.setStatus(FAILURE);
				writeBackCommand.setRemarks("Command Not Specified");
				continue;
			}

			if (command.getCustomSpecs() == null
			        || command.getCustomSpecs().size() == 0) {
				writeBackCommand
				        .setRemarks("Custom Specifications Not Specified");
				writeBackCommand.setStatus(FAILURE);
				continue;
			}

			WriteBackCommands commandEnum = getCommandEnum(
			        command.getCommand(), writeBackCommand);

			if (commandEnum == null) {
				continue;
			}

			WrieBackCommandDTO wbCommandDTO = null;
			switch (commandEnum) {

				case SYSTEM_COMMAND :
					wbCommandDTO = validateServerCommand(writeBackCommand);
					break;

				case WRITE_COMMAND :
					wbCommandDTO = validatePointWriteCommand(device,
					        writeBackCommand);
					break;
				default:
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					        COMMAND.getDescription());
			}

			if (wbCommandDTO == null || writeBackCommand.getStatus() == FAILURE) {
				continue;
			}

			Map<String, String> customInfo = command.getCustomInfo();

			if (customInfo != null && customInfo.size() > 0) {
				command.setCustomInfoJSON(objectBuilderUtil.getGson().toJson(
				        customInfo));
				command.setCustomInfo(null);
			}

			Map<String, String> customSpecs = command.getCustomSpecs();

			if (customSpecs != null && customSpecs.size() > 0) {
				command.setCustomSpecsJSON(objectBuilderUtil.getGson().toJson(
				        customSpecs));
				command.setCustomSpecs(null);
			}

			if (writeBackCommand.getStatus() == FAILURE) {
				continue;
			}

			wbCommandDTO.setSourceId(writeBackCommand.getSourceId());
			wbCommandDTO.setPayload(command);
			wbCommandDTO.setVersion(device.getVersion());
			wbCommandDTO.setStatus(QUEUED.getStatus());

			writeBackLog.setCommand(command);
			writeBackLog.setStatus(QUEUED.getStatus());

			WriteBackResponse insertLog = writeBackLogService
			        .insertLog(writeBackLog);

			// Copy status and requestId
			writeBackCommand.setWriteBackId(insertLog.getRequestId());
			writeBackCommand.setRequestedAt(insertLog.getRequestedAt());
			writeBackCommand.setStatus(insertLog.getStatus());

			if (writeBackCommand != null
			        && writeBackCommand.getWriteBackId() > 0) {
				// SetRequestId
				wbCommandDTO.setWriteBackId(writeBackCommand.getWriteBackId());
				wbCommandDTO.getPayload().setStatus(null);
				String json = null;
				try {
					json = objectBuilderUtil.getGson().toJson(wbCommandDTO);
				} catch (Exception e) {
					LOGGER.error(
					        "Error in converting commandDTO to jsonString with exception {}",
					        e);
				}

				if (json == null) {
					writeBackCommand.setStatus(FAILURE);
					writeBackCommand.setRemarks("Internal Error");
				}
				try {
					if (publishingMechanism.equalsIgnoreCase(JMS)) {
						messagePublisher.publishToJMSQueue(commandTopicName,
						        json);
					} else if (publishingMechanism.equalsIgnoreCase(KAFKA)) {
						messagePublisher.publishToKafkaTopic(commandTopicName,
						        json);
					}

					else {
						LOGGER.error("Publishing mechanism not configured properly");
					}

				} catch (RemoteException e) {
					LOGGER.error("Error in Publishing to {} with exception {}",
					        commandTopicName, e);
				}
			}

		}
		return writeBackCommands;

	}

	private WriteBackCommands getCommandEnum(String commandStr,
	        WriteBackCommand writeBackPointResponse) {

		WriteBackCommands wbCommand = null;

		wbCommand = WriteBackCommands.valueOfType(commandStr);
		if (wbCommand == null) {
			wbCommand = WriteBackCommands.valueOfName(commandStr);
			if (wbCommand == null) {
				writeBackPointResponse.setStatus(FAILURE);
				writeBackPointResponse.setRemarks("Command is invalid");
				return null;
			}
		}
		return wbCommand;
	}

	private WrieBackCommandDTO validateServerCommand(
	        WriteBackCommand writeBackResponse) {
		WrieBackCommandDTO wbCommandDTO = new WrieBackCommandDTO();
		Map<String, String> customSpecs = writeBackResponse.getPayload()
		        .getCustomSpecs();
		String commandCode = customSpecs.get(COMMAND_CODE.getVariableName());

		if (isEmpty(commandCode)) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("Command Code is Not Specified");
			return null;
		}

		if (WriteBackCommandCodes.valueOfName(commandCode) == null) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("Command Code is invalid");
			return null;
		}
		DeviceCommand command = new DeviceCommand();
		command.setCommand(writeBackResponse.getPayload().getCommand());
		command.setCustomSpecs(writeBackResponse.getPayload().getCustomSpecs());
		writeBackResponse.setPayload(command);
		return wbCommandDTO;
	}

	private WrieBackCommandDTO validatePointWriteCommand(Device device,
	        WriteBackCommand writeBackResponse) {
		WrieBackCommandDTO wbCommandDTO = new WrieBackCommandDTO();
		DeviceCommand command = writeBackResponse.getPayload();
		if (command.getPointId() == null) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("PointId is Not Specified");
			return null;
		}
		if (isEmpty(command.getValue())) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("Value is Not Specified");
			return null;
		}

		Map<String, String> customSpecs = command.getCustomSpecs();
		String priorityString = customSpecs.get(PRIORITY.getVariableName());
		if (StringUtils.isEmpty(priorityString)) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("Prioriry is Not Specified");
			return null;
		}
		short priority = -1;
		try {
			priority = Short.parseShort(priorityString);
		} catch (NumberFormatException e) {
			LOGGER.debug("priority was not short");
		}
		if (priority < 0 || priority > 15) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("Prioriry is invalid");
			return null;
		}

		try {
			validatePointIdAndSetDataType(device, command);
		} catch (Exception e) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("PointId/PointName is invalid");
			return null;
		}
		String value = command.getValue();
		try {
			validateDataAgainstDataType(command.getDataType(), value);
		} catch (Exception e) {
			writeBackResponse.setStatus(FAILURE);
			writeBackResponse.setRemarks("Value is invalid");
			return null;
		}
		return wbCommandDTO;
	}

	private Device validateSourceId(String sourceId,
	        WriteBackCommand writeBackCommand) {

		Device device = null;
		try {
			device = deviceService.getDevice(sourceId);
			if (device == null) {
				writeBackCommand.setStatus(FAILURE);
				writeBackCommand.setRemarks(SOURCE_ID.getDescription()
				        + DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED
				                .getMessage());
			}
		} catch (DeviceCloudException e) {
			writeBackCommand.setStatus(FAILURE);
			writeBackCommand
			        .setRemarks(SOURCE_ID.getDescription()
			                + DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED
			                        .getMessage());
		}
		return device;
	}

	private void validatePointIdAndSetDataType(Device device,
	        DeviceCommand command) {

		DeviceConfigTemplate configurations = device.getConfigurations();

		if (configurations == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        DeviceDataFields.CONFIGURATION.getDescription());
		}

		List<ConfigPoint> list = (List<ConfigPoint>)configurations
		        .getConfigPoints();

		if (CollectionUtils.isEmpty(list)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        POINT_ID.getDescription());
		}

		short pointId = command.getPointId();
		for (ConfigPoint configPoint : list) {
			short devicePointId = -1;
			try {
				devicePointId = Short.parseShort(configPoint.getPointId());
			} catch (NumberFormatException e) {
				LOGGER.error("Error in Pasrsing pointId {}", e);
			}
			if (devicePointId == pointId
			        && command.getPointName()
			                .equals(configPoint.getPointName())) {
				String type = configPoint.getType();
				if (type == null) {
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					        POINT_ID.getDescription());
				}
				command.setDataType(type);
				break;
			}
		}
		if (StringUtils.isEmpty(command.getDataType())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        POINT_ID.getDescription());
		}
	}

	private void validateDataAgainstDataType(String dataType, String data) {

		DataTypes dataTypeEnum = DataTypes.getDataType(dataType);
		if (dataTypeEnum == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
			        DATA.getDescription());
		}
		switch (dataTypeEnum) {
			case BOOLEAN :
				if (!data.equalsIgnoreCase("true")
				        && !data.equalsIgnoreCase("false")) {
					throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					        DATA.getDescription());
				}
				break;
			case SHORT :

				try {
					Short.parseShort(data);
				} catch (NumberFormatException e) {
					throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					        DATA.getDescription());
				}
				break;
			case INTEGER :

				try {
					Integer.parseInt(data);
				} catch (NumberFormatException e) {
					throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					        DATA.getDescription());
				}
				break;
			case LONG :

				try {
					Long.parseLong(data);
				} catch (NumberFormatException e) {
					throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					        DATA.getDescription());
				}
				break;
			case FLOAT :

				try {
					Float.parseFloat(data);
				} catch (NumberFormatException e) {
					throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					        DATA.getDescription());
				}
				break;
			case DOUBLE :
				try {
					Double.parseDouble(data);
				} catch (NumberFormatException e) {
					throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					        DATA.getDescription());
				}
				break;
			case STRING :

				break;
			default:
				throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
				        DATA.getDescription());
		}
	}

	// TODO to change it to private and use it later
	void checkCommandInProgress(Device device, DeviceCommand command,
	        WriteBackCommand writeBackPointResponse) {

		String query = checkCommandInProgress.replace(
		        CypherConstants.SOURCE_ID, device.getSourceId()).replace(
		        CypherConstants.COMMAND, command.getCommand());

		Short pointId = command.getPointId();
		String customSpecsJSON = command.getCustomSpecsJSON();

		if (pointId != null && pointId > 0) {
			query = query.replace(
			        ADDITIONAL_FILTERS,
			        pointIdCheck.replace(CypherConstants.POINT_ID,
			                pointId.toString()));
		} else if (customSpecsJSON != null) {
			String replaced = customSpecsJSON.replaceAll("\"", "\\\\\"");
			query = query.replace(ADDITIONAL_FILTERS,
			        customSpecCheck.replace(CUSTOM_SPECS, replaced));
		}

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			LOGGER.debug("No Command Found", e);
		}

		if (jsonArray != null && jsonArray.length() > 0
		        && jsonArray.getJSONObject(0) != null) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Command is already QUEUED");
		}

	}

}

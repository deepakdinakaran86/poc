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
import static com.pcs.datasource.enums.DeviceDataFields.LEASE_TIME;
import static com.pcs.datasource.enums.DeviceDataFields.POINT_ID;
import static com.pcs.datasource.enums.DeviceDataFields.PRIORITY;
import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.ADDITIONAL_FILTERS;
import static com.pcs.datasource.repository.utils.CypherConstants.CUSTOM_SPECS;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.enums.Status.FAILURE;
import static com.pcs.devicecloud.enums.Status.QUEUED;
import static java.lang.Short.MAX_VALUE;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
import com.pcs.datasource.dto.G2021CommandDTO;
import com.pcs.datasource.dto.writeback.BatchCommand;
import com.pcs.datasource.dto.writeback.Command;
import com.pcs.datasource.dto.writeback.WriteBackDeviceResponse;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.dto.writeback.WriteBackPointResponse;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.enums.DeviceDataFields;
import com.pcs.datasource.enums.G2021CommandCodes;
import com.pcs.datasource.enums.G2021Commands;
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
public class G2021CommandServiceImpl implements G2021CommandService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(G2021CommandServiceImpl.class);

	@Value("${g2021.commands.publish.mechanism}")
	private String publishingMechanism;

	@Value("${g2021.commands.publish.topic}")
	private String commandTopicName;

	private static final String JMS = "JMS";
	private static final String KAFKA = "Kafka";

	// @Value("${datasource.cache.device}")
	// private String deviceCacheName;

	// @Value("${datasource.cache.device.config}")
	// private String configCacheName;

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

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.G2021CommandService#validateAndSendToBroker
	 * (com.pcs.datasource.dto.CommandDTO)
	 */
	@Override
	public WriteBackDeviceResponse processCommands(String sourceId,
	        List<Command> commands) {

		validateMandatoryField(SOURCE_ID, sourceId);

		validateCollection(DeviceDataFields.COMMANDS, commands);

		Device device = validateSourceId(sourceId);

		WriteBackDeviceResponse writeBackResponse = new WriteBackDeviceResponse();
		writeBackResponse.setSourceId(sourceId);
		List<WriteBackPointResponse> writeBackResponses = new ArrayList<WriteBackPointResponse>();
		// writeBackResponse.setWriteBackResponses(writeBackResponses);

		WriteBackLog writeBackLog = new WriteBackLog();
		writeBackLog.setDevice(device);
		BatchCommand batch = null;
		boolean deleteBatch = false;
		if (commands.size() > 1) {
			batch = writeBackLogService.insertBatch(sourceId);
			writeBackLog.setBatch(batch);
			deleteBatch = true;
		}

		for (Command command : commands) {
			WriteBackPointResponse writeBackPointResponse = new WriteBackPointResponse();
			writeBackResponses.add(writeBackPointResponse);
			writeBackPointResponse.setCommand(command.getCommand());
			writeBackPointResponse.setCustomSpecs(command.getCustomSpecs());
			writeBackPointResponse.setPointId(command.getPointId());
			writeBackPointResponse.setValue(command.getValue());

			if (StringUtils.isEmpty(command.getCommand())) {
				writeBackPointResponse.setRemarks("Command Not Specified");
				continue;
			}

			if (command.getCustomSpecs() == null
			        || command.getCustomSpecs().size() == 0) {
				writeBackPointResponse
				        .setRemarks("Custom Specifications Not Specified");
				continue;
			}

			G2021Commands commandEnum = getCommandEnum(command.getCommand(),
			        writeBackPointResponse);

			if (commandEnum == null) {
				continue;
			}

			G2021CommandDTO g2021CommandDTO = null;
			switch (commandEnum) {

				case POINT_DISCOVERY_REQUEST :
					g2021CommandDTO = validatePointDiscoveryRequest(command,
					        writeBackPointResponse);
					break;

				case SERVER_COMMAND :
					g2021CommandDTO = validateServerCommand(command,
					        writeBackPointResponse);
					break;

				case POINT_INSTANT_SNAPSHOT_REQUEST :
					g2021CommandDTO = validateInstantSnapshotRequest(device,
					        command, writeBackPointResponse);
					break;

				case POINT_WRITE_COMMAND :
					g2021CommandDTO = validatePointWriteCommand(device,
					        command, writeBackPointResponse);
					break;
				default:
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					        COMMAND.getDescription());
			}

			if (g2021CommandDTO == null
			        || writeBackPointResponse.getStatus() == FAILURE) {
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

			// Check whether the same is in the QUEUED
			// TODO to remove after implementing write back response
			// checkCommandInProgress(device, command, writeBackPointResponse);

			if (writeBackPointResponse.getStatus() == FAILURE) {
				continue;
			}
			g2021CommandDTO.setSourceId(sourceId);
			g2021CommandDTO.setCommand(command.getCommand());

			// writeBackLog.setCommand(command);
			command.setStatus(QUEUED.getStatus());

			WriteBackPointResponse insertLog = null;// writeBackLogService
			// .insertLog(writeBackLog);

			// Copy status and requestId
			writeBackPointResponse.setRequestId(insertLog.getRequestId());
			writeBackPointResponse.setRequestedAt(insertLog.getRequestedAt());
			writeBackPointResponse.setStatus(insertLog.getStatus());

			if (writeBackPointResponse != null
			        && writeBackPointResponse.getRequestId() > 0) {
				// SetRequestId
				g2021CommandDTO.setRequestId(writeBackPointResponse
				        .getRequestId());
				String json = null;
				try {
					json = objectBuilderUtil.getGson().toJson(g2021CommandDTO);
				} catch (Exception e) {
					LOGGER.error(
					        "Error in converting commandDTO to jsonString with exception {}",
					        e);
				}

				if (json == null) {
					writeBackPointResponse.setStatus(FAILURE);
					writeBackPointResponse.setRemarks("Internal Error");
					// writeBackLogService.updateWriteBack(sourceId,
					// writeBackPointResponse);
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
				deleteBatch = false;
			} else {
				// TODO to enhance
			}

		}
		if (deleteBatch && batch != null) {
			writeBackLogService.deleteBatch(batch);
		}
		return writeBackResponse;

	}

	private G2021Commands getCommandEnum(String commandStr,
	        WriteBackPointResponse writeBackPointResponse) {

		G2021Commands g2021Command = null;

		g2021Command = G2021Commands.valueOfType(commandStr);
		if (g2021Command == null) {
			g2021Command = G2021Commands.valueOfName(commandStr);
			if (g2021Command == null) {
				writeBackPointResponse.setStatus(FAILURE);
				writeBackPointResponse.setRemarks("Command is invalid");
				return null;
			}
		}
		return g2021Command;
	}

	private G2021CommandDTO validatePointDiscoveryRequest(Command command,
	        WriteBackPointResponse writeBackPointResponse) {

		G2021CommandDTO g2021CommandDTO = new G2021CommandDTO();
		Map<String, String> customSpecs = command.getCustomSpecs();
		String leaseTimeString = customSpecs.get(LEASE_TIME.getVariableName());

		if (isEmpty(leaseTimeString)) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Lease Time is Not Specified");
			return null;
		}

		short leaseTime = -1;
		try {
			leaseTime = Short.parseShort(leaseTimeString);
		} catch (NumberFormatException e) {
			LOGGER.debug("leaseTime was not short");
		}
		if (leaseTime < 1 || leaseTime > MAX_VALUE) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Lease Time is invalid");
			return null;
		}

		g2021CommandDTO.setLeaseTime(leaseTime);
		return g2021CommandDTO;
	}

	private G2021CommandDTO validateServerCommand(Command command,
	        WriteBackPointResponse writeBackPointResponse) {
		G2021CommandDTO g2021CommandDTO = new G2021CommandDTO();
		Map<String, String> customSpecs = command.getCustomSpecs();
		String commandCode = customSpecs.get(COMMAND_CODE.getVariableName());

		if (isEmpty(commandCode)) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Command Code is Not Specified");
			return null;
		}

		if (G2021CommandCodes.valueOfName(commandCode) == null) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Command Code is invalid");
			return null;
		}
		g2021CommandDTO.setCommandCode(commandCode);
		return g2021CommandDTO;
	}

	private G2021CommandDTO validateInstantSnapshotRequest(Device device,
	        Command command, WriteBackPointResponse writeBackPointResponse) {
		G2021CommandDTO g2021CommandDTO = new G2021CommandDTO();
		if (command.getPointId() == null) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("PointId is Not Specified");
			return null;
		}
		try {
			validatePointIdAndSetDataType(device, command);
		} catch (Exception e) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("PointId is invalid");
			return null;
		}

		g2021CommandDTO.setPointId(command.getPointId());
		return g2021CommandDTO;
	}

	private G2021CommandDTO validatePointWriteCommand(Device device,
	        Command command, WriteBackPointResponse writeBackPointResponse) {
		G2021CommandDTO g2021CommandDTO = new G2021CommandDTO();
		if (command.getPointId() == null) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("PointId is Not Specified");
			return null;
		}
		if (isEmpty(command.getValue())) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Value is Not Specified");
			return null;
		}

		Map<String, String> customSpecs = command.getCustomSpecs();
		String priorityString = customSpecs.get(PRIORITY.getVariableName());
		if (StringUtils.isEmpty(priorityString)) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Prioriry is Not Specified");
			return null;
		}
		short priority = -1;
		try {
			priority = Short.parseShort(priorityString);
		} catch (NumberFormatException e) {
			LOGGER.debug("priority was not short");
		}
		if (priority < 0 || priority > 15) {
			/*
			 * throw new DeviceCloudException(
			 * DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			 * PRIORITY.getDescription());
			 */

			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Prioriry is invalid");
			return null;
		}

		try {
			validatePointIdAndSetDataType(device, command);
		} catch (Exception e) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("PointId is invalid");
			return null;
		}
		String value = command.getValue();
		try {
			validateDataAgainstDataType(command.getDataType(), value);
		} catch (Exception e) {
			writeBackPointResponse.setStatus(FAILURE);
			writeBackPointResponse.setRemarks("Value is invalid");
			return null;
		}

		g2021CommandDTO.setDataType(command.getDataType());
		g2021CommandDTO.setPointId(command.getPointId());
		g2021CommandDTO.setPriority(priority);
		g2021CommandDTO.setData(value);
		return g2021CommandDTO;

	}

	private Device validateSourceId(String sourceId) {

		// Cache cache =
		// cacheUtility.getCacheProvider().getCache(deviceCacheName);
		// Device device = cache.get(sourceId, Device.class);
		Device device = null;
		// if (device == null) {
		try {
			device = deviceService.getDevice(sourceId);
			if (device == null) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
				        SOURCE_ID.getDescription());
			}
			// cache.put(sourceId, device);
		} catch (DeviceCloudException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		// }
		return device;
	}

	private void validatePointIdAndSetDataType(Device device, Command command) {

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
			if (devicePointId == pointId) {
				String type = configPoint.getType();
				if (type == null) {
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					        POINT_ID.getDescription());
				}
				command.setDataType(type);
				command.setCustomTag(configPoint.getDisplayName());
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
	void checkCommandInProgress(Device device, Command command,
	        WriteBackPointResponse writeBackPointResponse) {

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

	public static void main(String[] args) {
		WriteBackPointResponse writeBackPointResponse = new WriteBackPointResponse();
		Command command = new Command();
		writeBackPointResponse.setPointId(command.getPointId());
	}
}

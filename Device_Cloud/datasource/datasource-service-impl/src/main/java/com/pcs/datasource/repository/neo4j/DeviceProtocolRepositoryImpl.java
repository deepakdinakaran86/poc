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
package com.pcs.datasource.repository.neo4j;

import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.repository.utils.CypherConstants.MAKE;
import static com.pcs.datasource.repository.utils.CypherConstants.MODEL;
import static com.pcs.datasource.repository.utils.CypherConstants.PROTOCOL;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.TYPE;
import static com.pcs.datasource.repository.utils.CypherConstants.VERSION;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.pcs.datasource.dto.CommandType;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.repository.DeviceProtocolRepository;
import com.pcs.datasource.repository.utils.CypherConstants;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Repository("dpNeo")
public class DeviceProtocolRepositoryImpl implements DeviceProtocolRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String updateDeviceRelation = "MATCH (device:DEVICE {source_id:'{source_id}'}), (make:MAKE {name:'{make}'}),(type:DEVICE_TYPE {name:'{type}'}),(model:MODEL {name:'{model}'}),"
	        + "(protocol:DEVICE_PROTOCOL {name:'{protocol}'}),(version:PROTOCOL_VERSION {name:'{version}'}), make-[:hasType]->type, type-[:hasModel]->model, model-[:talksIn]->protocol, "
	        + "protocol-[:hasVersion]->version, device-[ex:talksIn]->c DELETE ex CREATE device-[r:talksIn]->version;";

	private static final String getDeviceProtocolVersion = "MATCH (make:MAKE {name:'{make}'}),(type:DEVICE_TYPE {name:'{type}'}),(model:MODEL {name:'{model}'}),(protocol:DEVICE_PROTOCOL "
	        + "{name:'{protocol}'}),(version:PROTOCOL_VERSION {name:'{version}'}), make-[:hasType]->type, type-[:hasModel]->model, model-[:talksIn]->protocol, "
	        + "protocol-[:hasVersion]->version return version;";

	private static final String getSupportedCommands = "MATCH (make:MAKE {name:'{make}'}),(type:DEVICE_TYPE {name:'{type}'}),(model:MODEL {name:'{model}'}),"
	        + "(protocol:DEVICE_PROTOCOL {name:'{protocol}'}),(version:PROTOCOL_VERSION {name:'{version}'}), make-[:hasType]->type, type-[:hasModel]->model, model-[:talksIn]->protocol, "
	        + "protocol-[:hasVersion]->version, version-[:canExecute]->(c:COMMAND_TYPE) return c;";

	private static final String recycleProtocol = "MATCH (make:MAKE {name:'{make}'}),(type:DEVICE_TYPE {name:'{type}'}),(model:MODEL {name:'{model}'}),"
	        + "(protocol:DEVICE_PROTOCOL {name:'{protocol}'}),(version:PROTOCOL_VERSION {name:'{version}'}), make-[:hasType]->type, type-[:hasModel]->model, "
	        + "model-[:talksIn]->protocol, protocol-[:hasVersion]->version CREATE (recycle:RECYCLE {unit_id:{unitId}})<-[:hasVersion]-version return recycle;";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.DeviceProtocolRepository#updateDeviceRelation
	 * (com.pcs.datasource.dto.dc.Device)
	 */
	@Override
	public void updateDeviceRelation(Device device) {
		ConfigurationSearch version = device.getVersion();
		String query = updateDeviceRelation
		        .replace(SOURCE_ID, device.getSourceId())
		        .replace(MAKE, version.getMake())
		        .replace(TYPE, version.getDeviceType())
		        .replace(MODEL, version.getModel())
		        .replace(PROTOCOL, version.getProtocol())
		        .replace(VERSION, version.getVersion());
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in Updating Device's DeviceProtocol", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.DeviceProtocolRepository#getDeviceProtcol
	 * (java.lang.String)
	 */
	@Override
	public ProtocolVersion getDeviceProtocolVersion(ConfigurationSearch version) {
		String query = getDeviceProtocolVersion
		        .replace(MAKE, version.getMake());
		query = query.replace(TYPE, version.getDeviceType());
		query = query.replace(MODEL, version.getModel());
		query = query.replace(PROTOCOL, version.getProtocol());
		query = query.replace(VERSION, version.getVersion());

		JSONArray resultJson = null;
		try {
			resultJson = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in Fetching Device Protocol", e);
		}
		if (resultJson == null || resultJson.length() < 1) {
			return null;
		}
		return convertToProtocolVerion(resultJson.getJSONObject(0));
	}

	private ProtocolVersion convertToProtocolVerion(JSONObject deviceJson) {
		JSONArray deviceJsonArray = deviceJson.getJSONArray(ROW.getFieldName());

		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson();
		ProtocolVersion protocolVersion = lowerCaseUnderScoreGson.fromJson(
		        deviceJsonArray.getJSONObject(0).toString(),
		        ProtocolVersion.class);
		return protocolVersion;
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.datasource.repository.DeviceProtocolRepository#
	 * getCommandsOfDeviceProtocol(java.lang.String, java.lang.String)
	 */
	@Override
	public List<CommandType> getCommandsOfDeviceProtocol(
	        ConfigurationSearch configuration) {
		List<CommandType> deviceCommands = new ArrayList<CommandType>();

		String cmdQuery = getSupportedCommands
		        .replace(MAKE, configuration.getMake())
		        .replace(TYPE, configuration.getDeviceType())
		        .replace(MODEL, configuration.getModel())
		        .replace(PROTOCOL, configuration.getProtocol())
		        .replace(VERSION, configuration.getVersion());
		System.out.println("***********" + cmdQuery);

		try {
			JSONArray deviceCommandArray = exexcuteQuery(neo4jURI, cmdQuery,
			        null, ROW.getFieldName());
			if (deviceCommandArray == null) {
				return Collections.emptyList();
			}
			for (int i = 0; i < deviceCommandArray.length(); i++) {
				deviceCommands.add(convertToCommandType(deviceCommandArray
				        .getJSONObject(i)));
			}

		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching supported commands of a deviceType", e);
		}
		return deviceCommands;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.DeviceProtocolRepository#recycleUnitId(
	 * com.pcs.datasource.dto.Device)
	 */
	@Override
	public void recycleUnitId(Device device) {
		String query = recycleProtocol
		        .replace(MAKE, device.getVersion().getMake())
		        .replace(TYPE, device.getVersion().getDeviceType())
		        .replace(MODEL, device.getVersion().getModel())
		        .replace(PROTOCOL, device.getVersion().getProtocol())
		        .replace(VERSION, device.getVersion().getVersion())
		        .replace(CypherConstants.UNIT_ID,
		                String.valueOf(device.getUnitId()));
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException();
		}
	}

	/**
	 * Method to convert to command type
	 * 
	 * @param deviceJson
	 * @return {@link CommandType}
	 */
	private CommandType convertToCommandType(JSONObject deviceJson) {
		JSONArray deviceJsonArray = deviceJson.getJSONArray(ROW.getFieldName());

		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson();
		CommandType commandType = lowerCaseUnderScoreGson.fromJson(
		        deviceJsonArray.getJSONObject(0).toString(), CommandType.class);

		return commandType;
	}

}

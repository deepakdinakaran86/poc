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

import static com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner;
import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.repository.utils.CypherConstants.GET_ALL_NODES_OF_TYPE;
import static com.pcs.datasource.repository.utils.CypherConstants.MAKE;
import static com.pcs.datasource.repository.utils.CypherConstants.MODEL;
import static com.pcs.datasource.repository.utils.CypherConstants.NODE_LABEL;
import static com.pcs.datasource.repository.utils.CypherConstants.PROTOCOL;
import static com.pcs.datasource.repository.utils.CypherConstants.TYPE;
import static com.pcs.datasource.repository.utils.CypherConstants.VERSION;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hazelcast.query.QueryException;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.Model;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.SystemTag;
import com.pcs.datasource.repository.SystemRepository;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Repository("systemRepoNeo4j")
public class SystemRepositoryImpl implements SystemRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private final static String getAllSystemTags = "MATCH (a:SYSTEM_TAG)-[:isOfType]->(b:PHYSICAL_QUANTITY {optionalFilter}) "
			+ "return  collect ({name:a.name,phy_qty:b.name})  as systemTag;";

	private static final String getAllDeviceTypes = "MATCH (make:MAKE {name:'{make}'}),make-[:hasType]->device_type return device_type;";

	private static final String getAllDeviceModels = "MATCH (make:MAKE  {name:'{make}'}),(device_type:DEVICE_TYPE {name:'{type}'}), "
			+ "make-[:hasType]->device_type ,device_type-[:hasModel]->model<-[:hasModel]-make return model";

	private static final String getAllProtocols = "MATCH (make:MAKE  {name:'{make}'}),(device_type:DEVICE_TYPE {name:'{type}'}),"
			+ "(model:MODEL {name:'{model}'}), make-[:hasType]->device_type ,"
			+ "device_type-[:hasModel]->model<-[:hasModel]-make, model-[:talksIn]->protocol return protocol;";

	private static final String getAllVersions = "MATCH (make:MAKE  {name:'{make}'}),(device_type:DEVICE_TYPE {name:'{type}'}),"
			+ "(model:MODEL {name:'{model}'}), (protocol:DEVICE_PROTOCOL {name:'{protocol}'}), make-[:hasType]->device_type,"
			+ "device_type-[:hasModel]->model, model-[:talksIn]->protocol,protocol-[:hasVersion]->version return version;";

	private static final String getAllPoints = "MATCH (make:MAKE  {name:'{make}'}),(device_type:DEVICE_TYPE {name:'{type}'}),"
			+ "(model:MODEL {name:'{model}'}), (protocol:DEVICE_PROTOCOL {name:'{protocol}'}), "
			+ "(version:PROTOCOL_VERSION {name:'{version}'}), make-[:hasType]->device_type,"
			+ "device_type-[:hasModel]->model, model-[:talksIn]->protocol,protocol-[:hasVersion]->version,version-[:hasPoint]->points "
			+ "return {point_id:points.point_id,point_name:points.point_name,access_type:points.access_type,display_name:points.display_name,type:points.data_type} as points;";

	private static final String getMake = "MATCH (m:MAKE) where LOWER(m.name)='{make_name}' return m;";

	private static final String createMake = "CREATE  (m:MAKE {name:'{make_name}'}) return m";

	private static final String updateMake = "MATCH (m:MAKE {name:'{make_name}'}) set m.name='{make_new_name}' return m";

	private static final String getDeviceType = "MATCH (t:DEVICE_TYPE) where LOWER(t.name)='{type_name}' return t;";

	private static final String createDeviceType = "CREATE (t:DEVICE_TYPE {name:'{type_name}'}) return t";

	private static final String createDeviceTypeWithRelation = "MERGE (m:MAKE {name:'{make_name}'}) MERGE (t:DEVICE_TYPE {name:'{type_name}'}) MERGE (m)-[:hasType]->(t) return t;";

	private static final String updateDeviceType = "MATCH (t:DEVICE_TYPE {name:'{type_name}'}) set t.name='{type_new_name}' return t";

	private static final String getModel = "MATCH (mk:MAKE {name:'{make_name}'})-[r:hasModel]->(m:MODEL)<-[r1:hasModel]-(t:DEVICE_TYPE {name:'{type_name}'})"
			+ " where LOWER(m.name)='{model_name}' return m;";

	private static final String createModel = "CREATE (m:MODEL {name:'{model_name}'}) return m";

	private static final String createModelWithRelation = "MERGE (mk:MAKE {name:'{make_name}'}) MERGE (t:DEVICE_TYPE {name:'{type_name}'}) "
			+ "MERGE (mk)-[:hasType]->(t) "
			+ "MERGE (mk)-[:hasModel]->(md:MODEL {name:'{model_name}'})"
			+ "MERGE (t)-[:hasModel]->(md) return md;";

	private static final String updateModel = "MATCH (m:MODEL {name:'{model_name}'}) set m.name='{model_new_name}' return m";

	private static final String getDeviceProtocol = "MATCH (m:MAKE {name:'{make_name}'})-[:hasType]->(t:DEVICE_TYPE {name:'{type_name}'})"
			+ "-[:hasModel]->(md:MODEL {name:'{model_name}'})-[:talksIn]->(dp:DEVICE_PROTOCOL) where LOWER(dp.name)='{protocol_name}' RETURN dp;";

	private static final String createDeviceProtocol = "CREATE (dp:DEVICE_PROTOCOL {name:'{protocol_name}'}) return dp";

	private static final String createDeviceProtocolWithRelation = "MERGE (m:MAKE {name:'{make_name}'}) "
			+ "MERGE (t:DEVICE_TYPE {name:'{type_name}'}) "
			+ "MERGE (md:MODEL {name:'{model_name}'}) "
			+ "MERGE (m)-[:hasType]->(t) "
			+ "MERGE (t)-[:hasModel]->(md) "
			+ "MERGE (m)-[:hasModel]->(md) "
			+ "MERGE (md)-[:talksIn]->(dp:DEVICE_PROTOCOL {name:'{protocol_name}'}) return dp;";

	private static final String updateDeviceProtocol = "MATCH (m:MAKE {name:'{make_name}'})-[:hasType]->(t:DEVICE_TYPE {name:'{type_name}'})"
			+ "-[:hasModel]->(md:MODEL {name:'{model_name}'})-[:talksIn]->(dp:DEVICE_PROTOCOL) "
			+ "where LOWER(dp.name)='{protocol_name}' set dp.name='{protocol_new_name}' RETURN dp;";

	private static final String getDeviceProtocolVersion = "MATCH (m:MAKE {name:'{make_name}'})-"
			+ "[:hasType]->(t:DEVICE_TYPE {name:'{type_name}'})-"
			+ "[:hasModel]->(md:MODEL {name:'{model_name}'})-"
			+ "[:talksIn]->(dp:DEVICE_PROTOCOL {name:'{protocol_name}'})-"
			+ "[:hasVersion]->(pv:PROTOCOL_VERSION) where LOWER(pv.name)='{version_name}' RETURN pv;";

	private static final String createDeviceProtocolVersionWithRelation = "MERGE (m:MAKE {name:'{make_name}'}) "
			+ "MERGE (t:DEVICE_TYPE {name:'{type_name}'}) "
			+ "MERGE (md:MODEL {name:'{model_name}'}) "
			+ "MERGE (dp:DEVICE_PROTOCOL {name:'{protocol_name}'}) "
			+ "MERGE (m)-[:hasType]->(t) "
			+ "MERGE (t)-[:hasModel]->(md) "
			+ "MERGE (m)-[:hasModel]->(md) "
			+ "MERGE (md)-[:talksIn]-(dp) "
			+ "MERGE (dp)-[:hasVersion]->(pv:PROTOCOL_VERSION {name:'{version_name}'}) return pv";

	private static final String createDeviceProtocolVersion = "CREATE (pv:PROTOCOL_VERSION {name:'{version_name}'}) return pv";

	private static final String updateDeviceProtocolVersion = "MATCH (m:MAKE {name:'{make_name}'})-"
			+ "[:hasType]->(t:DEVICE_TYPE {name:'{type_name}'})"
			+ "-[:hasModel]->(md:MODEL {name:'{model_name}'})"
			+ "-[:talksIn]->(dp:DEVICE_PROTOCOL {name:'{protocol_name}'}) "
			+ "-[:hasVersion]->(pv:PROTOCOL_VERSION) "
			+ "where LOWER(pv.name)='{version_name}' set pv.name='{version_new_name}' RETURN pv;";

	@Override
	public <T> List<T> getAllOfAType(String nodeLabel, Class<T> clazz) {
		String getAllDataTypes = GET_ALL_NODES_OF_TYPE.replace(NODE_LABEL,
				nodeLabel);
		JSONArray jsonArray = null;
		List<T> data = new ArrayList<T>();
		try {
			jsonArray = exexcuteQuery(neo4jURI, getAllDataTypes, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException("Error in fetching getAllOfAtype", e);
		}
		if (jsonArray != null) {
			Type type = newParameterizedTypeWithOwner(null, ArrayList.class,
					clazz);
			JSONArray dataTypesJson = jsonArray.getJSONObject(0)
					.getJSONArray(ROW.getFieldName()).getJSONArray(0);
			data = objectBuilderUtil.getGson().fromJson(
					dataTypesJson.toString(), type);
		}
		return data;
	}

	@Override
	public List<SystemTag> getAllSystemTags(String physicalQty) {
		String query = null;
		if (StringUtils.isEmpty(physicalQty)) {
			query = getAllSystemTags.replace("{optionalFilter}", "");
		} else {
			query = getAllSystemTags.replace("{optionalFilter}", "{name:'"
					+ physicalQty + "'}");
		}
		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException("Error in fetching getAllSystemTags", e);
		}
		List<SystemTag> systemTags = null;
		if (jsonArray != null) {
			JSONArray resultJson = jsonArray.getJSONObject(0)
					.getJSONArray(ROW.getFieldName()).getJSONArray(0);

			@SuppressWarnings("serial")
			Type type = new TypeToken<List<SystemTag>>() {
			}.getType();
			systemTags = objectBuilderUtil.getGson().fromJson(
					resultJson.toString(), type);
		}
		return systemTags;
	}

	@Override
	public boolean isDeviceMakeExist(String makeName) {
		makeName = makeName.toLowerCase();
		String query = getMake.replace("{make_name}", makeName);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in checking existence of device make", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void createDeviceMake(ConfigurationSearch configuration) {
		JSONArray makeJsonArray = null;
		try {
			String query = createMake.replace("{make_name}",
					configuration.getMake());
			makeJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in saving device make", e);
		}
		if (makeJsonArray == null) {
			throw new PersistenceException(
					"Error in saving device make , response is null");
		}
	}

	@Override
	public void updateDeviceMake(String uniqueId,
			ConfigurationSearch configuration) {
		JSONArray makeJsonArray = null;
		try {
			String query = updateMake.replace("{make_name}", uniqueId).replace(
					"{make_new_name}", configuration.getMake());
			makeJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in updating device make", e);
		}
		if (makeJsonArray == null) {
			throw new PersistenceException(
					"Error in updating device make , response is null");
		}
	}

	@Override
	public boolean isDeviceTypeExist(String typeName) {
		typeName = typeName.toLowerCase();
		String query = getDeviceType.replace("{type_name}", typeName);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in checking existence of device type", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void createDeviceType(ConfigurationSearch configuration) {
		JSONArray resultJsonArray = null;
		String query = null;
		try {
			if (StringUtils.isNotEmpty(configuration.getMake())) {
				query = createDeviceTypeWithRelation.replace(
						"{make_name}",
						configuration.getMake().replace("{type_name}",
								configuration.getDeviceType()));
			} else {
				query = createDeviceType.replace("{type_name}",
						configuration.getDeviceType());
			}
			resultJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in saving device type", e);
		}
		if (resultJsonArray == null) {
			throw new PersistenceException(
					"Error in saving device type, response is null");
		}
	}

	@Override
	public void updateDeviceType(String uniqueId,
			ConfigurationSearch configuration) {
		JSONArray resultJsonArray = null;
		try {
			String query = updateDeviceType.replace("{type_name}", uniqueId)
					.replace("{type_new_name}", configuration.getDeviceType());
			resultJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in updating device type", e);
		}
		if (resultJsonArray == null) {
			throw new PersistenceException(
					"Error in updating device type, response is null");
		}
	}

	@Override
	public boolean isDeviceModelExist(ConfigurationSearch configuration) {
		String model = configuration.getModel().toLowerCase();
		String query = getModel.replace("{make_name}", configuration.getMake())
				.replace("{type_name}", configuration.getDeviceType())
				.replace("{model_name}", model);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in checking existence of device model", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void createDeviceModel(ConfigurationSearch configuration) {
		JSONArray resJsonArray = null;
		String query = null;
		try {
			if (StringUtils.isNotEmpty(configuration.getMake())
					&& StringUtils.isNotEmpty(configuration.getDeviceType())) {
				query = createModelWithRelation
						.replace("{make_name}", configuration.getMake())
						.replace("{type_name}", configuration.getDeviceType())
						.replace("{model_name}", configuration.getModel());
			} else {
				query = createModel.replace("{model_name}",
						configuration.getModel());
			}
			resJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in saving device model", e);
		}
		if (resJsonArray == null) {
			throw new PersistenceException(
					"Error in saving device model , response is null");
		}
	}

	@Override
	public void updateDeviceModel(String uniqueId,
			ConfigurationSearch configuration) {
		JSONArray makeJsonArray = null;
		try {
			String query = updateModel.replace("{model_name}", uniqueId)
					.replace("{model_new_name}", configuration.getModel());
			makeJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in updating device model", e);
		}
		if (makeJsonArray == null) {
			throw new PersistenceException(
					"Error in updating device model , response is null");
		}
	}

	@Override
	public boolean isDeviceProtocolExist(ConfigurationSearch configuration) {
		String deviceProtocol = configuration.getProtocol().toLowerCase();
		String query = getDeviceProtocol
				.replace("{make_name}", configuration.getMake())
				.replace("{type_name}", configuration.getDeviceType())
				.replace("{model_name}", configuration.getModel())
				.replace("{protocol_name}", deviceProtocol);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in checking existence of device protocol", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void createDeviceProtocol(ConfigurationSearch configuration) {
		JSONArray resJsonArray = null;
		String query = null;
		try {
			if (StringUtils.isNotEmpty(configuration.getMake())
					&& StringUtils.isNotEmpty(configuration.getDeviceType())
					&& StringUtils.isNotEmpty(configuration.getModel())) {
				query = createDeviceProtocolWithRelation
						.replace("{make_name}", configuration.getMake())
						.replace("{type_name}", configuration.getDeviceType())
						.replace("{model_name}", configuration.getModel())
						.replace("protocol_name", configuration.getProtocol());
			} else {
				query = createDeviceProtocol.replace("{model_name}",
						configuration.getProtocol());
			}

			resJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in saving device protocol", e);
		}
		if (resJsonArray == null) {
			throw new PersistenceException(
					"Error in saving device protocol , response is null");
		}
	}

	@Override
	public void updateDeviceProtocol(String uniqueId,
			ConfigurationSearch configuration) {
		JSONArray jsonArray = null;
		try {
			String query = updateDeviceProtocol
					.replace("{make_name}", configuration.getMake())
					.replace("{type_name}", configuration.getDeviceType())
					.replace("{model_name}", configuration.getModel())
					.replace("protocol_name", uniqueId.toLowerCase())
					.replace("{protocol_new_name}", configuration.getProtocol());
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in updating device protocol",
					e);
		}
		if (jsonArray == null) {
			throw new PersistenceException(
					"Error in updating device protocol , response is null");
		}
	}

	@Override
	public boolean isDeviceProtocolVersionExist(
			ConfigurationSearch configuration) {
		String version = configuration.getVersion().toLowerCase();
		String query = getDeviceProtocolVersion
				.replace("{make_name}", configuration.getMake())
				.replace("{type_name}", configuration.getDeviceType())
				.replace("{model_name}", configuration.getModel())
				.replace("{protocol_name}", configuration.getProtocol())
				.replace("{version_name}", version);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in checking existence of device protocol version", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void createDeviceProtocolVersion(ConfigurationSearch configuration) {
		JSONArray resJsonArray = null;
		String query = null;
		try {
			if (StringUtils.isNotEmpty(configuration.getMake())
					&& StringUtils.isNotEmpty(configuration.getDeviceType())
					&& StringUtils.isNotEmpty(configuration.getModel())
					&& StringUtils.isNotEmpty(configuration.getProtocol())) {
				query = createDeviceProtocolVersionWithRelation
						.replace("{make_name}", configuration.getMake())
						.replace("{type_name}", configuration.getDeviceType())
						.replace("{model_name}", configuration.getModel())
						.replace("{protocol_name}", configuration.getProtocol())
						.replace("{version_name}", configuration.getVersion());
			} else {
				query = createDeviceProtocolVersion.replace("{version_name}",
						configuration.getVersion());
			}
			resJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in saving device protocol version", e);
		}
		if (resJsonArray == null) {
			throw new PersistenceException(
					"Error in saving device protocol version , response is null");
		}
	}

	@Override
	public void updateDeviceProtocolVersion(String uniqueId,
			ConfigurationSearch configuration) {
		JSONArray jsonArray = null;
		try {
			String query = updateDeviceProtocolVersion
					.replace("{make_name}", configuration.getMake())
					.replace("{type_name}", configuration.getDeviceType())
					.replace("{model_name}", configuration.getModel())
					.replace("{protocol_name}", configuration.getProtocol())
					.replace("{version_name}", uniqueId.toLowerCase())
					.replace("{version_new_name}", configuration.getVersion());
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in updating device protocol version", e);
		}
		if (jsonArray == null) {
			throw new PersistenceException(
					"Error in updating device protocol version , response is null");
		}
	}

	public List<DeviceType> getDeviceTypes(String makeName) {
		List<DeviceType> deviceTypes = new ArrayList<DeviceType>();
		String query = getAllDeviceTypes.replace(MAKE, makeName);
		JSONArray deviceTypesJsonArray = null;
		try {
			deviceTypesJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in fetching getDeviceModels of a make", e);
		}
		if (deviceTypesJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < deviceTypesJsonArray.length(); i++) {
			DeviceType deviceType = convertToDeviceType(deviceTypesJsonArray
					.getJSONObject(i));
			deviceTypes.add(deviceType);
		}
		return deviceTypes;
	}

	public List<Model> getDeviceModels(ConfigurationSearch configuration) {
		List<Model> models = new ArrayList<Model>();
		String query = getAllDeviceModels
				.replace(MAKE, configuration.getMake());
		query = query.replace(TYPE, configuration.getDeviceType());
		JSONArray modelJsonArray = null;
		try {
			modelJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in fetching getDeviceModels of a device type", e);
		}
		if (modelJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < modelJsonArray.length(); i++) {
			Model model = convertToModel(modelJsonArray.getJSONObject(i));
			models.add(model);
		}
		return models;
	}

	public List<DeviceProtocol> getDeviceProtocols(
			ConfigurationSearch configuration) {
		List<DeviceProtocol> protocols = new ArrayList<DeviceProtocol>();
		String query = getAllProtocols.replace(MAKE, configuration.getMake());
		query = query.replace(TYPE, configuration.getDeviceType());
		query = query.replace(MODEL, configuration.getModel());
		JSONArray protocolJsonArray = null;
		try {
			protocolJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in fetching getDeviceProtocols of a model", e);
		}
		if (protocolJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < protocolJsonArray.length(); i++) {
			DeviceProtocol protocol = convertToProtocol(protocolJsonArray
					.getJSONObject(i));
			protocols.add(protocol);
		}
		return protocols;
	}

	public List<ProtocolVersion> getProtocolVersions(
			ConfigurationSearch configuration) {
		List<ProtocolVersion> versions = new ArrayList<ProtocolVersion>();
		String query = getAllVersions.replace(MAKE, configuration.getMake());
		query = query.replace(TYPE, configuration.getDeviceType());
		query = query.replace(MODEL, configuration.getModel());
		query = query.replace(PROTOCOL, configuration.getProtocol());
		JSONArray versionJsonArray = null;
		try {
			versionJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in fetching getProtocolVersions of a protocol", e);
		}
		if (versionJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < versionJsonArray.length(); i++) {
			ProtocolVersion version = convertToVersion(versionJsonArray
					.getJSONObject(i));
			versions.add(version);
		}
		return versions;
	}

	public List<Point> getProtocolVersionPoint(ConfigurationSearch configuration) {
		List<Point> points = new ArrayList<Point>();
		String query = getAllPoints.replace(MAKE, configuration.getMake());
		query = query.replace(TYPE, configuration.getDeviceType());
		query = query.replace(MODEL, configuration.getModel());
		query = query.replace(PROTOCOL, configuration.getProtocol());
		query = query.replace(VERSION, configuration.getVersion());
		JSONArray pointJsonArray = null;
		try {
			pointJsonArray = exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException(
					"Error in fetching getDevicePoints of a protocol version",
					e);
		}
		if (pointJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < pointJsonArray.length(); i++) {
			Point point = convertToPoint(pointJsonArray.getJSONObject(i));
			points.add(point);
		}
		return points;
	}

	private DeviceType convertToDeviceType(JSONObject deviceJson) {
		JSONArray deviceTypeJsonArray = deviceJson.getJSONArray(ROW
				.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
				.getLowerCaseUnderScoreGson();
		DeviceType deviceType = lowerCaseUnderScoreGson.fromJson(
				deviceTypeJsonArray.getJSONObject(0).toString(),
				DeviceType.class);
		return deviceType;
	}

	private Model convertToModel(JSONObject deviceJson) {
		JSONArray modelJsonArray = deviceJson.getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
				.getLowerCaseUnderScoreGson();
		Model model = lowerCaseUnderScoreGson.fromJson(modelJsonArray
				.getJSONObject(0).toString(), Model.class);
		return model;
	}

	private DeviceProtocol convertToProtocol(JSONObject deviceJson) {
		JSONArray protocolJsonArray = deviceJson.getJSONArray(ROW
				.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
				.getLowerCaseUnderScoreGson();
		DeviceProtocol protocol = lowerCaseUnderScoreGson.fromJson(
				protocolJsonArray.getJSONObject(0).toString(),
				DeviceProtocol.class);
		return protocol;
	}

	private ProtocolVersion convertToVersion(JSONObject deviceJson) {
		JSONArray versionJsonArray = deviceJson
				.getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
				.getLowerCaseUnderScoreGson();
		ProtocolVersion version = lowerCaseUnderScoreGson.fromJson(
				versionJsonArray.getJSONObject(0).toString(),
				ProtocolVersion.class);
		return version;
	}

	private Point convertToPoint(JSONObject deviceJson) {
		JSONArray pointJsonArray = deviceJson.getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
				.getLowerCaseUnderScoreGson();
		Point point = lowerCaseUnderScoreGson.fromJson(pointJsonArray
				.getJSONObject(0).toString(), Point.class);
		return point;
	}

}

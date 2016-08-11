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

import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_MAKE;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_MODEL;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_PROTOCOL;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_PROTOCOL_VERSION;
import static com.pcs.datasource.enums.DeviceConfigFields.DEVICE_TYPE;
import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.SUB_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.prepareCypherArray;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.GeneralBatchResponse;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceConfigRepository;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.datasource.repository.utils.PointExtTrnaslator;
import com.pcs.devicecloud.commons.util.ExcludeFields;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;
import com.pcs.devicecloud.enums.Status;

/**
 * This class is responsible for defining the repository services related to the
 * device point configuration
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 02 Jul 2015
 */
@Repository
public class DeviceConfigRepositoryImpl implements DeviceConfigRepository {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeviceConfigRepositoryImpl.class);

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String getConfigTemp = "MATCH (s:SUBSCRIPTION {sub_id:'{subscription_id}'} )"
			+ "-[:hasTemplate]->(tmp:DEVICE_CONFIG_TEMP {is_deleted:false}) where "
			+ "LOWER(tmp.name)='{temp_name}' return tmp;";

	private static final String createTemplate = "WITH {props} AS tempData MATCH (s:SUBSCRIPTION {sub_id:'{subscription_id}'}) "
			+ " MATCH (mk:MAKE {name:'{device_make}'})"
			+ "-[:hasType]->(t:DEVICE_TYPE {name:'{device_type}'})"
			+ "-[:hasModel]->(md:MODEL {name:'{device_model}'})<-[:hasModel]-mk, "
			+ "md-[:talksIn]->(p:DEVICE_PROTOCOL {name:'{device_protocol}'})"
			+ "-[:hasVersion]->(v:PROTOCOL_VERSION {name:'{proto_version}'}) "
			+ "CREATE (ct:DEVICE_CONFIG_TEMP )<-[:hasTemplate]-v "
			+ " SET ct = tempData "
			+ "CREATE s-[:hasTemplate]->ct  return id(ct);";

	private static final String createConfigPointWithRel = "WITH {props} AS points UNWIND points AS point "
			+ "MATCH (s:SUBSCRIPTION {sub_id:'{subscription_id}'}), "
			+ "(pq:PHYSICAL_QUANTITY {name:point.physical_quantity}), "
			+ "s-[:hasTemplate]->(ct:DEVICE_CONFIG_TEMP {name:'{temp_name}'})<-[r:hasTemplate]-(v:PROTOCOL_VERSION), "
			+ "v-[:hasPoint]->(pnt:POINT {point_id:point.point_id}), "
			+ "(u:UNIT {name:point.unit}) "
			+ "CREATE (pt:CONFIGURED_POINT) "
			+ " SET pt = point "
			+ "CREATE UNIQUE pt-[:containedIn]->pq "
			+ "CREATE UNIQUE pt<-[:hasPoint]-ct "
			+ "CREATE UNIQUE pt<-[:configuredAs]-pnt "
			+ "CREATE UNIQUE pt-[:measuresIn]->u " + "return pt;";

	private static final String createConfigPointWithoutRel = "WITH {props} AS points UNWIND points AS point "
			+ "MATCH (s:SUBSCRIPTION {sub_id:'{subscription_id}'}),"
			+ "(pq:PHYSICAL_QUANTITY {name:point.physical_quantity}), "
			+ "s-[:hasTemplate]->(ct:DEVICE_CONFIG_TEMP {name:'{temp_name}'})<-[r:hasTemplate]-(v:PROTOCOL_VERSION),"
			+ "(u:UNIT {name:point.unit}) "
			+ "CREATE (pt:CONFIGURED_POINT ) "
			+ " SET pt = point "
			+ "CREATE UNIQUE pt-[:containedIn]->pq "
			+ "CREATE UNIQUE pt<-[:hasPoint]-ct "
			+ "CREATE UNIQUE pt-[:measuresIn]->u " + "return pt;";

	private static final String searchProtoVersion = "MATCH (mk:MAKE {name:'{device_make}'})"
			+ "-[:hasType]->(t:DEVICE_TYPE {name:'{device_type}'})"
			+ "-[:hasModel]->(md:MODEL {name:'{device_model}'})<-[:hasModel]-mk,"
			+ "md-[:talksIn]->(p:DEVICE_PROTOCOL {name:'{device_protocol}'})"
			+ "-[:hasVersion]->(v:PROTOCOL_VERSION {name:'{proto_version}'}) return id(v);";

	private static final String checkPointIsValid = "MATCH (v:PROTOCOL_VERSION) where id(v)={node_id} "
			+ "MATCH (p:POINT {point_id:'{point_id}',point_name:'{point_name}'}) MATCH v-[r:hasPoint]->p  return r;";

	private static final String checkParamIsValid = "MATCH (s:SUBSCRIPTION {sub_id:'{subscription_id}'})"
			+ "-[:hasParameter]->(p:PARAMETER {name:'{param_name}'})"
			+ "-[r:containedIn]->(q:PHYSICAL_QUANTITY {name:'{pq_name}'}) return q;";

	private static final String getTempConfigPointDispNames = "MATCH (ct:`DEVICE_CONFIG_TEMP` {name:'{temp_name}'})"
			+ "<-[:hasTemplate]-(s:SUBSCRIPTION {sub_id:'{subscription_id}'}), "
			+ "ct-[:hasPoint]->(pt:CONFIGURED_POINT) return distinct pt.display_name;";

	private static final String deleteConfigPoint = "MATCH (ct:DEVICE_CONFIG_TEMP {name:'{temp_name}'})"
			+ "<-[:hasTemplate]-(s:SUBSCRIPTION {sub_id:'{subscription_id}'}),"
			+ "ct-[r:hasPoint]->(cp:CONFIGURED_POINT)-[r1:containedIn]->(pq:PHYSICAL_QUANTITY), "
			+ "cp-[r2:measuresIn]->(u:UNIT) "
			+ "where cp.display_name in ['{pointIds}'] "
			+ "optional match cp<-[r3:configuredAs]-(pnt:POINT) "
			+ "optional match (d:DEVICE)-[r4:isConfiguredWith]->cp "
			+ " delete r4,r3,r2,r1,r,cp return ct;";

	private static final String updateConfigPointWithRel = "WITH {props} AS points "
			+ "match  (s:SUBSCRIPTION {sub_id:'{subscription_id}'}), "
			+ "s-[:hasTemplate]->(ct:DEVICE_CONFIG_TEMP {name:'{temp_name}'})<-[r:hasTemplate]-(v:PROTOCOL_VERSION) "
			+ "UNWIND points AS point "
			+ "MATCH (v:PROTOCOL_VERSION)-[r:hasTemplate]->ct,"
			+ "ct-[:hasPoint]->(pt:CONFIGURED_POINT {display_name:point.display_name}), "
			+ "pt-[r2:containedIn]->(pq1:PHYSICAL_QUANTITY), "
			+ "pt<-[r1:configuredAs]-(pnt1:POINT), "
			+ "(pq:PHYSICAL_QUANTITY {name:point.physical_quantity}),"
			+ "v-[:hasPoint]->(pnt:POINT {point_id:point.point_id}), "
			+ "pt-[r3:measuresIn]->(u1:UNIT),"
			+ "(u:UNIT {name:point.unit}) "
			+ "SET pt = point "
			+ "delete r3,r2,r1 "
			+ "CREATE UNIQUE pt-[:containedIn]->pq "
			+ "CREATE UNIQUE pt<-[:configuredAs]-pnt "
			+ "CREATE UNIQUE pt-[:measuresIn]->u " + "return pt;";

	private static final String updateConfigPointWithoutRel = "WITH {props} AS points "
			+ "match (ct:DEVICE_CONFIG_TEMP {name:'{temp_name}'})<-[:hasTemplate]-(s:SUBSCRIPTION {sub_id:'{subscription_id}'})  "
			+ "UNWIND points AS point "
			+ "match ct-[:hasPoint]->(pt:CONFIGURED_POINT {point_id:point.point_id}), "
			+ "(pq:PHYSICAL_QUANTITY {name:point.physical_quantity}), "
			+ "pt-[r:containedIn]->(pq1:PHYSICAL_QUANTITY), "
			+ "pt-[r1:measuresIn]->(u1:UNIT),"
			+ "(u:UNIT {name:point.unit}) "
			+ "SET pt = point "
			+ "delete r1,r "
			+ "CREATE UNIQUE pt-[:containedIn]->pq "
			+ "CREATE UNIQUE pt-[:measuresIn]->u " + "return pt;";

	private static final String getDeviceConfiguration = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'})-[:owns]"
			+ "->(device:DEVICE {source_id:'{source_id}'})-[:talksIn]->(pv:PROTOCOL_VERSION )"
			+ "<-[:hasVersion]-(dp:DEVICE_PROTOCOL)<-[:talksIn]-(m:MODEL)<-[:hasModel]-(dt:DEVICE_TYPE)<-[:hasType]-(mk:MAKE), "
			+ "device-[:isConfiguredWith]->(cp:CONFIGURED_POINT) OPTIONAL MATCH "
			+ "device-[:isConfiguredWith]->(dct:DEVICE_CONFIG_TEMP)  "
			+ "return {name:dct.name,device_make:mk.name,device_type:dt.name,"
			+ "device_model:m.name,device_protocol:dp.name,device_protocol_version:pv.name,"
			+ "config_points:collect(cp)};";

	private static final String createConfigPointUnderDumbDevice = "WITH {props} AS points UNWIND points AS point "
			+ "MATCH (dev:DEVICE {source_id:'{source_id}'}) "
			+ "MATCH (m:MAKE {name:'{make}'})-[:hasType]->(dt:DEVICE_TYPE {name:'{type}'})-[:hasModel]->(:MODEL {name:'{model}'})-[:talksIn]-(:DEVICE_PROTOCOL"
			+ "{name:'{protocol}'})-[:hasVersion]->(:PROTOCOL_VERSION {name:'{version}'})- [:hasPoint]->(pnt:POINT {point_id:point.point_id}),"
			+ "(pq:PHYSICAL_QUANTITY {name:point.physical_quantity}),"
			+ "(u:UNIT {name:point.unit}) "
			+ "CREATE (cp:CONFIGURED_POINT "
			+ "{point_id:point.point_id,point_name:point.point_name,display_name:point.display_name,"
			+ "physical_quantity:point.physical_quantity,"
			+ "type:point.type,unit:point.unit,system_tag:point.system_tag,"
			+ "access:point.access,acquisition:point.acquisition,"
			+ "precedence:point.precedence,unit:point.unit,"
			+ "extensions:point.extensions,alarmExtensions:point.alarmExtensions,"
			+ "expression:point.expression} ) "
			+ "CREATE UNIQUE cp<-[:isConfiguredWith]-dev "
			+ "CREATE UNIQUE cp<-[:configuredAs]-pnt "
			+ "CREATE UNIQUE cp-[:containedIn]->pq "
			+ "CREATE UNIQUE cp-[:measuresIn]->u " + "return cp;";

	private static final String createConfigPointUnderIntelligentDevice = "MATCH (a:DEVICE {source_id:'{source_id}'}) "
			+ "CREATE (cp:CONFIGURED_POINT {props}) "
			+ "CREATE UNIQUE a-[:isConfiguredWith]->cp "
			+ "CREATE UNIQUE cp-[:containedIn]->pq return cp;";

	private static final String createConfigPointUnderIntelligentDevice1 = "WITH {props} AS points UNWIND points AS point "
			+ "MATCH (a:DEVICE {source_id:'{source_id}'}),  "
			+ "(pq:PHYSICAL_QUANTITY {name:point.physical_quantity}),"
			+ "(u:UNIT {name:point.unit}) "
			+ "CREATE (cp:CONFIGURED_POINT ) "
			+ " SET cp = point "
			+ "CREATE UNIQUE a-[:isConfiguredWith]->cp "
			+ "CREATE UNIQUE cp-[:containedIn]->pq "
			+ "CREATE UNIQUE cp-[:measuresIn]->u " + "return cp;";

	private static final String getProtcolVersionPoints = "MATCH (pv:PROTOCOL_VERSION {name:'{version}'} )<-[:hasVersion]-"
			+ "(dp:DEVICE_PROTOCOL {name:'{protocol}'})<-[:talksIn]-(m:MODEL {name:'{model}'})<-[:hasModel]-"
			+ "(dt:DEVICE_TYPE {name:'{type}'})<-[:hasType]-(mk:MAKE{name:'{make}'}), "
			+ "pv-[:hasPoint]-(pt:POINT) return collect(pt);";

	private static final String updateDeviceConfigPointRel = "WITH {props} AS points UNWIND points AS point "
			+ "MATCH (s:SUBSCRIPTION {sub_id:'{subscription_id}'}), "
			+ "s-[:hasTemplate]->(ct:DEVICE_CONFIG_TEMP {name:'{temp_name}'})-[:hasPoint]->(cp:CONFIGURED_POINT {display_name:point.display_name}), "
			+ "(d:DEVICE)-[:isConfiguredWith]->ct "
			+ "CREATE UNIQUE d-[:isConfiguredWith]->cp " + "return ct;";

	private static final String updateConfigTemp = "MATCH (s:SUBSCRIPTION {sub_id:'{subscription_id}'} )"
			+ "-[:hasTemplate]->(tmp:DEVICE_CONFIG_TEMP {is_deleted:false}) where "
			+ "LOWER(tmp.name)='{temp_name}' set tmp.description='{temp_des}' return tmp;";

	private String subscriptionId = null;

	@Override
	public boolean isDeviceConfigTempNameExist(String subId, String tempName) {
		tempName = tempName.toLowerCase();
		String query = getConfigTemp.replace("{subscription_id}", subId)
				.replace("{temp_name}", tempName);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in checking existence of config template", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void saveDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemplate, boolean isWithPointRef) {
		this.subscriptionId = subId;
		String parentNodeId = saveTemplate(configTemplate);
		if (parentNodeId == null) {
			throw new PersistenceException(
					"Unable to find config template's node id");
		}
		saveConfigPoint(subId, configTemplate.getName(),
				configTemplate.getConfigPoints(), isWithPointRef);
	}

	private String readNodeId(JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.getFieldName());
		if (!jsonArray.isNull(0)) {
			return String.valueOf(jsonArray.optInt(0));
		}
		return null;
	}

	private String saveTemplate(DeviceConfigTemplate configTemplate) {
		configTemplate.setIsDeleted(false);
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addField(DEVICE_TYPE.getVariableName());
		excludeFields.addField(DEVICE_MAKE.getVariableName());
		excludeFields.addField(DEVICE_MODEL.getVariableName());
		excludeFields.addField(DEVICE_PROTOCOL.getVariableName());
		excludeFields.addField(DEVICE_PROTOCOL_VERSION.getVariableName());
		excludeFields.addField("configPoints");
		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder()
				.setExclusionStrategies(excludeFields);
		Gson gson = builder.create();
		String paramsJson = gson.toJson(configTemplate);

		String query = createTemplate
				.replace("{subscription_id}", this.subscriptionId)
				.replace("{device_make}", configTemplate.getDeviceMake())
				.replace("{device_type}", configTemplate.getDeviceType())
				.replace("{device_model}", configTemplate.getDeviceModel())
				.replace("{device_protocol}",
						configTemplate.getDeviceProtocol())
				.replace("{proto_version}",
						configTemplate.getDeviceProtocolVersion());
		JSONArray tempResult = null;
		try {
			tempResult = exexcuteQuery(neo4jURI, query, paramsJson,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in saving device config template", e);
		}
		if (tempResult == null) {
			throw new PersistenceException(
					"Error in saving device config template , response is null");
		}
		return readNodeId(tempResult.getJSONObject(0));
	}

	/**
	 * @param subId
	 *            - subscription id
	 * @param tempName
	 *            - name of the config template
	 * @param configPoints
	 *            - configuration points
	 * @param isWithPointRef
	 *            - flag to add relation to point nodes
	 */
	private void saveConfigPoint(String subId, String tempName,
			List<ConfigPoint> configPoints, boolean isWithPointRef) {
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addField("customTags");

		String paramsJson = serializeConfigPoint(configPoints);

		JSONArray savePtResult = null;
		String query = "";
		if (isWithPointRef) {
			query = createConfigPointWithRel;
		} else {
			query = createConfigPointWithoutRel;
		}
		query = query.replace("{temp_name}", tempName).replace(
				"{subscription_id}", subId);
		try {
			savePtResult = exexcuteQuery(neo4jURI, query, paramsJson,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in saving config template points", e);
		}
		if (savePtResult == null) {
			throw new PersistenceException(
					"Error in saving config template points , response is null");
		}
	}

	/**
	 * Method to check a config point is valid - config point should be a valid
	 * point of protocol version
	 * 
	 * @param configSearch
	 *            - ConfigurationSearch
	 * @param configPoint
	 *            - point info
	 * @return - true if point is a valid point of protocol version else false
	 */
	@Override
	public boolean isValidPoint(ConfigurationSearch configSearch,
			ConfigPoint configPoint) {
		String versionNodeId = findParentData(configSearch);
		return isValidPoint(versionNodeId, configPoint);
	}

	/**
	 * Method to check a config point is valid - config point should be a valid
	 * point of protocol version
	 * 
	 * @param parentRef
	 *            - parent reference (node id of protocol version)
	 * @param configPoint
	 *            - point info
	 * @return - true if point is a valid point of protocol version else false
	 */
	@Override
	public boolean isValidPoint(String parentRef, ConfigPoint configPoint) {
		if (StringUtils.isNotEmpty(parentRef)) {
			String query = checkPointIsValid.replace("{node_id}", parentRef)
					.replace("{point_id}", configPoint.getPointId())
					.replace("{point_name}", configPoint.getPointName());
			JSONArray jsonArray = null;
			try {
				jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
						ROW.getFieldName());
			} catch (JSONException | IOException e) {
				throw new PersistenceException(
						"Error in checking config point validity against protocol version ",
						e);
			}
			if (jsonArray == null) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Method to check a parameter is valid parameter of the subscription - by
	 * checking the parameter name and physical quantity
	 * 
	 * @param subId
	 *            - subscription id
	 * @param configPoint
	 *            - Configured point
	 * @return - true if parameter is valid else false
	 */
	@Override
	public boolean isValidParameter(String subId, ConfigPoint configPoint) {
		String query = checkParamIsValid.replace("{subscription_id}", subId)
				.replace("{param_name}", configPoint.getParameter())
				.replace("{pq_name}", configPoint.getPhysicalQuantity());
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in checking parameter validity against subscription ",
					e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void updateDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemplate, boolean isWithPointRef) {
		this.subscriptionId = subId;
		Set<String> existingPointDispNames = new HashSet<String>();
		existingPointDispNames = findExistingConfigPointDispNames(configTemplate);
		Set<String> persistPointDispNames = new HashSet<String>();

		List<ConfigPoint> updateConfigList = new ArrayList<ConfigPoint>();
		List<ConfigPoint> newConfigList = new ArrayList<ConfigPoint>();
		for (ConfigPoint configPoint : configTemplate.getConfigPoints()) {
			boolean isConfigExist = false;
			if (!CollectionUtils.isEmpty(existingPointDispNames)) {
				for (String existPointDispName : existingPointDispNames) {
					if (configPoint.getDisplayName().equals(existPointDispName)) {
						isConfigExist = true;
						break;
					}
				}
			}
			if (isConfigExist) {
				updateConfigList.add(configPoint);
			} else {
				newConfigList.add(configPoint);
			}
			persistPointDispNames.add(configPoint.getDisplayName());
		}

		// The returned set contains all elements that are contained by set1 and
		// not contained by set2
		SetView<String> deletePointDispNames = Sets.difference(
				existingPointDispNames, persistPointDispNames);

		if (!CollectionUtils.isEmpty(updateConfigList)) {
			updateExistingPoints(subId, configTemplate.getName(),
					updateConfigList, isWithPointRef);
		}
		if (!CollectionUtils.isEmpty(newConfigList)) {
			saveConfigPoint(subId, configTemplate.getName(), newConfigList,
					isWithPointRef);
			updateDevicePointRel(subId, configTemplate.getName(), newConfigList);
		}
		if (!CollectionUtils.isEmpty(deletePointDispNames)) {
			deleteConfigPoints(configTemplate, deletePointDispNames);
		}
		String desc = StringUtils.isEmpty(configTemplate.getDescription())?"":configTemplate.getDescription();
		String query = updateConfigTemp.replace("{subscription_id}", subId)
				.replace("{temp_name}", configTemplate.getName().toLowerCase())
				.replace("{temp_des}", desc);
		try {
			Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in updating configuration template "+ configTemplate.getName() + " ",
					e);
		}
	}

	private void updateExistingPoints(String subId, String configTemplate,
			List<ConfigPoint> updateConfigList, boolean isWithPointRef) {
		String paramsJson = serializeConfigPoint(updateConfigList);

		JSONArray findPtResult = null;
		String query = "";
		if (isWithPointRef) {
			query = updateConfigPointWithRel.replace("{temp_name}",
					configTemplate).replace("{subscription_id}", subId);
		} else {
			query = updateConfigPointWithoutRel.replace("{temp_name}",
					configTemplate).replace("{subscription_id}", subId);
		}
		try {
			findPtResult = exexcuteQuery(neo4jURI, query, paramsJson,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			new PersistenceException(
					"Error in updating config points in template", e);
		}
		if (findPtResult == null) {
			throw new PersistenceException(
					"Error in updating config template, response is null");
		}
	}

	@Override
	public boolean isValidDeviceConfigTemp(DeviceConfigTemplate configTemp) {
		return false;
	}

	/**
	 * Method to find the parent node of the given configuration template (here
	 * the parent node will be 'PROTOCOL_VERSION' node)
	 * 
	 * @param configTemplate
	 * @return
	 */
	@Override
	public String findParentData(ConfigurationSearch configSearch) {
		JSONArray findPtResult = null;
		Gson gson = objectBuilderUtil.getLowerCaseUnderScoreGson1();
		String paramsJson = gson.toJson(configSearch);
		try {
			String query = searchProtoVersion
					.replace("{device_make}", configSearch.getMake())
					.replace("{device_type}", configSearch.getDeviceType())
					.replace("{device_model}", configSearch.getModel())
					.replace("{device_protocol}", configSearch.getProtocol())
					.replace("{proto_version}", configSearch.getVersion());
			findPtResult = exexcuteQuery(neo4jURI, query, paramsJson,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			new PersistenceException(
					"Error in finding config template's parent node", e);
		}
		if (findPtResult == null) {
			return null;
		}
		return readNodeId(findPtResult.getJSONObject(0));
	}

	private Set<String> findExistingConfigPointDispNames(
			DeviceConfigTemplate configTemp) {
		String query = getTempConfigPointDispNames.replace("{temp_name}",
				configTemp.getName()).replace("{subscription_id}",
				this.subscriptionId);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in checking point existing in template ", e);
		}
		if (jsonArray == null) {
			return new HashSet<String>();
		}
		Set<String> existingPointDispNames = new HashSet<String>();
		JSONArray row = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			row = jsonArray.getJSONObject(i).getJSONArray(ROW.getFieldName());
			if (!row.isNull(0)) {
				existingPointDispNames.add(row.optString(0));
			}
		}
		return existingPointDispNames;
	}

	private void deleteConfigPoints(DeviceConfigTemplate configTemp,
			Set<String> pointDispNames) {
		String pointIdString = Joiner.on("','").join(pointDispNames);
		JSONArray deleteResult = null;
		String query = deleteConfigPoint
				.replace("{temp_name}", configTemp.getName())
				.replace("{subscription_id}", this.subscriptionId)
				.replace("{pointIds}", pointIdString);
		try {
			deleteResult = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in deleting existing config points in template ", e);
		}
		if (deleteResult == null) {
			throw new PersistenceException(
					"Error in deleting configured points , response is null");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.SystemDataRepository#getAllConfTemplates
	 * (java.lang.String, com.pcs.datasource.dto.ConfigurationSearch)
	 */
	@Override
	public List<DeviceConfigTemplate> getAllConfTemplates(String subId,
			ConfigurationSearch conSearch) {
		String query = null;
		if (conSearch != null && conSearch.getMake() != null) {
			query = "MATCH (sub:SUBSCRIPTION {sub_id:'"
					+ subId
					+ "'})-[:hasTemplate]->(dct:DEVICE_CONFIG_TEMP )"
					+ "<-[:hasTemplate]-(pv:PROTOCOL_VERSION {name:'"
					+ conSearch.getVersion()
					+ "'} ) "
					+ "<-[:hasVersion]-(dp:DEVICE_PROTOCOL {name:'"
					+ conSearch.getProtocol()
					+ "'}) "
					+ "<-[:talksIn]-(m:MODEL {name:'"
					+ conSearch.getModel()
					+ "'} ),"
					+ "(mk:MAKE {name:'"+ conSearch.getMake()+ "'})-[:hasModel]->m<-[:hasModel]-(dt:DEVICE_TYPE {name:'"
					+ conSearch.getDeviceType()
					+ "'}),"
					+ "dct-[:hasPoint]-(cp:CONFIGURED_POINT) WITH dct,pv,dp,m,mk,dt,collect(cp) as configPoints "
					+ "RETURN collect({name:dct.name,description:dct.description,device_protocol_version:pv.name,device_make:mk.name,"
					+ "device_protocol:dp.name,device_model:m.name,device_type:dt.name,config_points:configPoints}) as dct ;";
		} else {
			query = "MATCH (sub:SUBSCRIPTION {sub_id:'"
					+ subId
					+ "'})-[:hasTemplate]->(dct:DEVICE_CONFIG_TEMP)"
					+ "<-[:hasTemplate]-(pv:PROTOCOL_VERSION) "
					+ "<-[:hasVersion]-(dp:DEVICE_PROTOCOL) "
					+ "<-[:talksIn]-(m:MODEL),"
					+ "(mk:MAKE)-[:hasModel]->m<-[:hasModel]-(dt:DEVICE_TYPE),"
					+ "dct-[:hasPoint]->(cp:CONFIGURED_POINT) WITH dct,pv,dp,mk,m,dt,collect(cp) as configPoints "
					+ "RETURN collect({name:dct.name,description:dct.description,device_protocol_version:pv.name,device_make:mk.name,"
					+ "device_protocol:dp.name,device_model:m.name,device_type:dt.name,config_points:configPoints}) as dct ;";
		}

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching getAllConfTemplates", e);
		}
		List<DeviceConfigTemplate> confTemplates = null;
		if (jsonArray != null) {
			JSONArray resultJson = jsonArray.getJSONObject(0)
					.getJSONArray(ROW.getFieldName()).getJSONArray(0);

			GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
			builder.registerTypeAdapter(ConfigPoint.class,
					new PointExtTrnaslator());

			Gson gson = builder.create();

			@SuppressWarnings("serial")
			Type type = new TypeToken<List<DeviceConfigTemplate>>() {
			}.getType();
			confTemplates = gson.fromJson(resultJson.toString(), type);
		}
		return confTemplates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.SystemDataRepository#getConfTemplate(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public DeviceConfigTemplate getConfTemplate(String subId,
			String templateName) {

		String query = "MATCH (sub:SUBSCRIPTION {sub_id:'"
				+ subId
				+ "'})-[:hasTemplate]->(dct:DEVICE_CONFIG_TEMP {name:'"
				+ templateName
				+ "'})<-[:hasTemplate]-(pv:PROTOCOL_VERSION) "
				+ "<-[:hasVersion]-(dp:DEVICE_PROTOCOL) <-[:talksIn]-(m:MODEL), "
				+ "(mk:MAKE)-[:hasModel]->m<-[:hasModel]-(dt:DEVICE_TYPE),"
				+ "dct-[:hasPoint]-(cp:CONFIGURED_POINT) "
				+ "RETURN {template_name:dct.name,description:dct.description,"
				+ "device_protocol_version:pv.name,device_make:mk.name,"
				+ "device_protocol:dp.name,device_model:m.name,device_type:dt.name,"
				+ "config_points:collect({display_name:cp.display_name,"
				+ "precedence:cp.precedence,expression:cp.expression,"
				+ "point_id:cp.point_id,point_name:cp.point_name,"
				+ "physical_quantity:cp.physical_quantity,type:cp.type,"
				+ "unit:cp.unit,system_tag:cp.system_tag,"
				+ "access:cp.access,acquisition:cp.acquisition,"
				+ "extensions:cp.extensions,"
				+ "alarmExtensions:cp.alarmExtensions})} as dct ;";

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching getAllConfTemplates", e);
		}
		DeviceConfigTemplate confTemplate = null;
		if (jsonArray != null) {
			JSONObject resultJson = jsonArray.getJSONObject(0)
					.getJSONArray(ROW.getFieldName()).getJSONObject(0);

			GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
			builder.registerTypeAdapter(ConfigPoint.class,
					new PointExtTrnaslator());

			Gson gson = builder.create();
			confTemplate = gson.fromJson(resultJson.toString(),
					DeviceConfigTemplate.class);
		}
		return confTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.SystemDataRepository#detachAllDevice(java
	 * .lang.String, java.util.List)
	 */
	@Override
	public void detachAllDevice(String subId, List<String> confTemplateNames) {
		String query = "MATCH (sub:SUBSCRIPTION {sub_id:'"
				+ subId
				+ "'})-[:hasTemplate]->(dct:DEVICE_CONFIG_TEMP )<-[r:isConfiguredWith]-(dev:DEVICE) where dct.name in "
				+ prepareCypherArray(confTemplateNames)
				+ " set dct.is_deleted=true delete r return dct;";
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching getAllConfTemplates", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.SystemDataRepository#getConfTemplate(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public DeviceConfigTemplate getDeviceConfiguration(String subId,
			String sourceId) {

		String query = getDeviceConfiguration.replace(SUB_ID, subId).replace(
				SOURCE_ID, sourceId);

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching getAllConfTemplates", e);
		}
		DeviceConfigTemplate confTemplate = null;
		if (jsonArray != null) {
			JSONObject resultJson = jsonArray.getJSONObject(0)
					.getJSONArray(ROW.getFieldName()).getJSONObject(0);
			confTemplate = deSerializeConfigPoint(resultJson);
		}

		return confTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.SystemDataRepository#assignConfigPointToDevices
	 * (com.pcs.datasource.dto.DeviceConfigTemplate, java.util.List)
	 */
	@Override
	public GeneralBatchResponse assignConfigPointToDevices(
			DeviceConfigTemplate configTemplate, Set<String> sourceIds,
			boolean isDump) {

		GeneralBatchResponse batchResponse = new GeneralBatchResponse();
		boolean attatchTemplate = configTemplate.getName() == null ? false
				: true;

		List<GeneralResponse> responses = new ArrayList<GeneralResponse>();
		batchResponse.setResponses(responses);
		for (String sourceId : sourceIds) {

			GeneralResponse generalRespone = new GeneralResponse();
			responses.add(generalRespone);
			generalRespone.setReference(sourceId);

			String qeryToDelete = "MATCH (a:DEVICE {source_id:'"
					+ sourceId
					+ "'}) "
					+ "OPTIONAL MATCH a-[r1:isConfiguredWith]->(cp:CONFIGURED_POINT) "
					+ "OPTIONAL MATCH a-[r2:isConfiguredWith]->(cp1:CONFIGURED_POINT)<-[:hasPoint]-(dct:DEVICE_CONFIG_TEMP)  "
					+ "OPTIONAL MATCH cp-[r3:hasParameter]->(param:PARAMETER) "
					+ "OPTIONAL MATCH cp<-[r4:configuredAs]-(p:POINT) "
					+ "OPTIONAL MATCH a-[r5:isConfiguredWith]->(ct:DEVICE_CONFIG_TEMP) "
					+ "OPTIONAL MATCH cp-[r6:containedIn]->(pq:PHYSICAL_QUANTITY) "
					+ "OPTIONAL MATCH cp-[r7:measuresIn]->(u:UNIT) delete r7,r6,r5,r4,r3,r2,r1 return a;";

			JSONArray deviceJson = null;
			try {
				deviceJson = exexcuteQuery(neo4jURI, qeryToDelete, null,
						ROW.getFieldName());
			} catch (JSONException | IOException e) {
				LOGGER.debug("Error in removing existing configured points", e);
			}
			if (deviceJson == null) {
				generalRespone.setStatus(Status.FAILURE);
				generalRespone.setRemarks("Source Id is Invalid");
				break;
			}

			String query = null;

			if (attatchTemplate) {
				query = "MATCH (device:DEVICE {source_id:'" + sourceId
						+ "'}) OPTIONAL MATCH (sub:SUBSCRIPTION {sub_id:'"
						+ configTemplate.getSubId()
						+ "'})-[:hasTemplate]->(ct:DEVICE_CONFIG_TEMP {name:'"
						+ configTemplate.getName()
						+ "'})-[:hasPoint]->(cp:CONFIGURED_POINT) "
						+ "CREATE UNIQUE device-[:isConfiguredWith]->ct  "
						+ "CREATE UNIQUE device-[:isConfiguredWith]->cp  "
						+ "return ct;";
			} else if (isDump) {

				query = createConfigPointUnderDumbDevice
						.replace("{source_id}", sourceId)
						.replace("{subscription_id}", configTemplate.getSubId())
						.replace("{make}", configTemplate.getDeviceMake())
						.replace("{type}", configTemplate.getDeviceType())
						.replace("{model}", configTemplate.getDeviceModel())
						.replace("{protocol}",
								configTemplate.getDeviceProtocol())
						.replace("{version}",
								configTemplate.getDeviceProtocolVersion());
			} else {
				query = createConfigPointUnderIntelligentDevice1.replace(
						"{source_id}", sourceId);
			}

			List<ConfigPoint> configPoints = configTemplate.getConfigPoints();

			String json = serializeConfigPoint(configPoints);
			JSONArray assignJson = null;
			try {
				assignJson = exexcuteQuery(neo4jURI, query, json,
						ROW.getFieldName());
			} catch (JSONException | IOException e) {
				LOGGER.debug("Eror in assigning configured points", e);
			}
			if (assignJson == null) {
				generalRespone.setStatus(Status.FAILURE);
				generalRespone.setRemarks("Source Id is Invalid");
				break;
			}
			generalRespone.setStatus(Status.SUCCESS);
		}
		return batchResponse;
	}

	@Override
	public List<ConfigPoint> getPointsOfAProtocolVersion(
			ConfigurationSearch configurationSearch) {

		String query = getProtcolVersionPoints
				.replace("{make}", configurationSearch.getMake())
				.replace("{type}", configurationSearch.getDeviceType())
				.replace("{model}", configurationSearch.getModel())
				.replace("{protocol}", configurationSearch.getProtocol())
				.replace("{version}", configurationSearch.getVersion());
		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching points of a protocol",
					e);
		}

		@SuppressWarnings("serial")
		Type type = new TypeToken<List<ConfigPoint>>() {
		}.getType();
		List<ConfigPoint> points = objectBuilderUtil.getGson().fromJson(
				jsonArray.getJSONObject(0).getJSONArray(ROW.getFieldName())
						.getJSONArray(0).toString(), type);
		return points;
	}

	private String serializeConfigPoint(List<ConfigPoint> configPoints) {
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addField("customTags");
		// excludeFields.addField("extensions");
		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder()
				.setExclusionStrategies(excludeFields);
		builder.registerTypeAdapter(ConfigPoint.class, new PointExtTrnaslator());
		Gson gson = builder.create();
		String paramsJson = gson.toJson(configPoints);
		return paramsJson;
	}

	private DeviceConfigTemplate deSerializeConfigPoint(JSONObject resultJson) {
		DeviceConfigTemplate confTemplate;

		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
		builder.registerTypeAdapter(ConfigPoint.class, new PointExtTrnaslator());

		Gson gson = builder.create();
		confTemplate = gson.fromJson(resultJson.toString(),
				DeviceConfigTemplate.class);
		return confTemplate;
	}

	private void updateDevicePointRel(String subId, String tempName,
			List<ConfigPoint> newConfigList) {
		JSONArray savePtResult = null;
		String query = "";
		query = updateDeviceConfigPointRel;
		query = query.replace("{temp_name}", tempName).replace(
				"{subscription_id}", subId);
		String paramsJson = serializeConfigPoint(newConfigList);
		try {
			savePtResult = exexcuteQuery(neo4jURI, query, paramsJson,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in updating device config point relation", e);
		}
	}

	@Override
	public GeneralBatchResponse assignTemplateToDevices(String templateName,
			Set<String> sourceIds, Subscription subscription) {
		GeneralBatchResponse batchResponse = new GeneralBatchResponse();
		List<GeneralResponse> responses = new ArrayList<GeneralResponse>();
		batchResponse.setResponses(responses);

		for (String sourceId : sourceIds) {
			GeneralResponse generalRespone = new GeneralResponse();
			responses.add(generalRespone);
			generalRespone.setReference(sourceId);

			String qeryToDelete = "MATCH (d:DEVICE {source_id:'"
					+ sourceId
					+ "'}) "
					+ "OPTIONAL MATCH d-[r1:isConfiguredWith]->(cp:CONFIGURED_POINT) "
					+ "OPTIONAL MATCH a-[r2:isConfiguredWith]->(ct:DEVICE_CONFIG_TEMP) "
					+ "OPTIONAL MATCH cp<-[r3:configuredAs]-(p:POINT) "
					+ "OPTIONAL MATCH cp-[r4:containedIn]->(pq:PHYSICAL_QUANTITY) "
					+ "OPTIONAL MATCH cp-[r5:measuresIn]->(u:UNIT) "
					+ "delete r5,r4,r3,r2,r1 return a";

			JSONArray deviceJson = null;
			try {
				deviceJson = exexcuteQuery(neo4jURI, qeryToDelete, null,
						ROW.getFieldName());
			} catch (JSONException | IOException e) {
				LOGGER.debug(
						"Error in removing existing configured points or template",
						e);
			}
			if (deviceJson == null) {
				generalRespone.setStatus(Status.FAILURE);
				generalRespone.setRemarks("Source Id is Invalid");
				break;
			}

			String query = null;

			query = "MATCH (device:DEVICE {source_id:'" + sourceId
					+ "'}) MATCH (sub:SUBSCRIPTION {sub_id:'"
					+ subscription.getSubId()
					+ "'})-[:hasTemplate]->(ct:DEVICE_CONFIG_TEMP {name:'"
					+ templateName + "'})-[:hasPoint]->(cp:CONFIGURED_POINT) "
					+ "CREATE UNIQUE device-[:isConfiguredWith]->ct  "
					+ "CREATE UNIQUE device-[:isConfiguredWith]->cp  "
					+ "return ct;";
			JSONArray assignJson = null;
			try {
				assignJson = exexcuteQuery(neo4jURI, query, null,
						ROW.getFieldName());
			} catch (JSONException | IOException e) {
				LOGGER.debug("Error in assigning template to device {}",
						sourceId);
			}
			if (assignJson == null) {
				generalRespone.setStatus(Status.FAILURE);
				generalRespone.setRemarks("Source Id is Invalid");
				break;
			}
			generalRespone.setStatus(Status.SUCCESS);
		}
		return batchResponse;
	}

}

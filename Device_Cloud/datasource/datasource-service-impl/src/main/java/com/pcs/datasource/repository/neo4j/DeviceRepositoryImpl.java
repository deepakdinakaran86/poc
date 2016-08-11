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
import static com.pcs.datasource.repository.utils.CypherConstants.NW_PROTOCOL;
import static com.pcs.datasource.repository.utils.CypherConstants.PROTOCOL;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_IDs;
import static com.pcs.datasource.repository.utils.CypherConstants.SUB_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.TYPE;
import static com.pcs.datasource.repository.utils.CypherConstants.UNIT_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.VERSION;
import static com.pcs.datasource.repository.utils.CypherConstants.WRITEBACK_CONF;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DevicePointData;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.datasource.repository.utils.CypherConstants;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 5, 2015
 * @since galaxy-1.0.0
 */
@Repository("deviceRepoNeo4j")
public class DeviceRepositoryImpl implements DeviceRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DeviceRepositoryImpl.class);

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private final int unitIdSeed = 1;

	private static final String getDeviceQuery = "MATCH (device:DEVICE {source_id:'{source_id}'}) "
	        + "OPTIONAL MATCH device<-[:owns]-(sub:SUBSCRIPTION) "
	        + "OPTIONAL MATCH device-[p:transportsIn]->(c:NW_PROTOCOL) "
	        + "OPTIONAL MATCH device-[r:talksIn]->(dp:PROTOCOL_VERSION) "
	        + "OPTIONAL MATCH dp<-[:hasVersion]-(protocol:DEVICE_PROTOCOL) "
	        + "OPTIONAL MATCH protocol<-[:talksIn]-(model:MODEL) "
	        + "OPTIONAL MATCH model<-[:hasModel]-(device_type:DEVICE_TYPE) "
	        + "OPTIONAL MATCH device_type<-[:hasType]-(make:MAKE)-[:hasModel]->model "
	        + "OPTIONAL MATCH (device)-[:isTaggedWith]->(tag) return  {source_id:device.source_id, device_id:device.device_id, unit_id:device.unit_id, is_publishing:device.is_publishing, "
	        + "datasource_name:device.datasource_name,ip:device.ip, connected_port:device.connected_port, writeback_port:device.writeback_port,"
	        + "device_name:device.device_name,latitude:device.latitude,longitude:device.longitude,"
	        + "timezone:device.time_zone,gmt_offset:device.gmt_offset,gmt_offset_sign:device.gmt_offset_sign,URL:device.URL,slot:device.slot,"
	        + "tags:collect(distinct tag),nw_protocol:c, version:{version:dp.name, protocol:protocol.name, model:model.name, deviceType:device_type.name, make:make.name},subscription:sub} as device;";

	private static final String getDeviceQueryWithSub = "MATCH (device:DEVICE {source_id:'{source_id}'}),(sub:SUBSCRIPTION {sub_id:'{sub_id}'}),"
	        + "device<-[:owns]-sub "
	        + "MATCH device-[p:transportsIn]->(c:NW_PROTOCOL) "
	        + "MATCH device-[r:talksIn]->(dp:PROTOCOL_VERSION) "
	        + "MATCH dp<-[:hasVersion]-(protocol:DEVICE_PROTOCOL) "
	        + "MATCH protocol<-[:talksIn]-(model:MODEL) "
	        + "MATCH model<-[:hasModel]-(device_type:DEVICE_TYPE) "
	        + "MATCH device_type<-[:hasType]-(make:MAKE)-[:hasModel]->model "
	        + "OPTIONAL MATCH (device)-[:isTaggedWith]->(tag) return  {source_id:device.source_id, device_id:device.device_id, unit_id:device.unit_id, is_publishing:device.is_publishing, "
	        + "datasource_name:device.datasource_name,ip:device.ip, connected_port:device.connected_port, writeback_port:device.writeback_port,"
	        + "device_name:device.device_name,latitude:device.latitude,longitude:device.longitude,"
	        + "time_zone:device.time_zone,gmt_offset:device.gmt_offset,gmt_offset_sign:device.gmt_offset_sign,url:device.url,slot:device.slot,"
	        + "tags:collect(distinct tag),nw_protocol:c, version:{version:dp.name, protocol:protocol.name, model:model.name, deviceType:device_type.name, make:make.name},subscription:sub} as device;";

	private static final String getAllDeviceQueryFilterTags = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}), sub-[o:owns]->device {tagFilter}  "
	        + "device-[r:talksIn]->(dpv:PROTOCOL_VERSION), "
	        + "dpv<-[:hasVersion]-(protocol:DEVICE_PROTOCOL), "
	        + "protocol<-[:talksIn]-(model:MODEL), "
	        + "model<-[:hasModel]-(device_type:DEVICE_TYPE), "
	        + "device_type<-[:hasType]-(make:MAKE)-[:hasModel]->model "
	        + "OPTIONAL MATCH (device)-[:isTaggedWith]->(tag) "
	        + "OPTIONAL MATCH device-[p:transportsIn]->nwprotocol return {source_id:device.source_id, unit_id:device.unit_id, "
	        + "is_publishing:device.is_publishing, datasource_name:device.datasource_name, ip:device.ip, connected_port:device.connected_port, writeback_port:device.writeback_port , "
	        + "device_name:device.device_name,latitude:device.latitude,longitude:device.longitude, "
	        + "time_zone:device.time_zone,gmt_offset:device.gmt_offset,gmt_offset_sign:device.gmt_offset_sign,url:device.url,slot:device.slot, "
	        + "tags:collect(distinct tag), nw_protocol:nwprotocol, version:{version:dpv.name, protocol:protocol.name,"
	        + "model:model.name, deviceType:device_type.name, make:make.name}} as devices ORDER BY device.source_id;";

	private static final String getAllDeviceQuery = "MATCH (sub:SUBSCRIPTION), sub-[o:owns]->(device:DEVICE) "
	        + "MATCH device-[p:transportsIn]->(c:NW_PROTOCOL) "
	        + "MATCH device-[r:talksIn]->(dp:PROTOCOL_VERSION) "
	        + "MATCH dp<-[:hasVersion]-(protocol:DEVICE_PROTOCOL) "
	        + "MATCH protocol<-[:talksIn]-(model:MODEL) "
	        + "MATCH model<-[:hasModel]-(device_type:DEVICE_TYPE) "
	        + "MATCH device_type<-[:hasType]-(make:MAKE)-[:hasModel]->model "
	        + "OPTIONAL MATCH (device)-[:isTaggedWith]->(tag) "
	        + "return  {source_id:device.source_id, device_id:device.device_id, unit_id:device.unit_id, is_publishing:device.is_publishing,"
	        + "datasource_name:device.datasource_name,ip:device.ip, connected_port:device.connected_port, writeback_port:device.writeback_port,"
	        + "device_name:device.device_name,latitude:device.latitude,longitude:device.longitude,"
	        + "time_zone:device.time_zone,gmt_offset:device.gmt_offset,gmt_offset_sign:device.gmt_offset_sign,url:device.url,slot:device.slot,"
	        + "tags:collect(distinct tag),nw_protocol:c, version:{version:dp.name, protocol:protocol.name, model:model.name, deviceType:device_type.name, make:make.name}} as device;";

	private static final String getAllDeviceQueryByPV = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}), "
	        + "(make:MAKE {name:'{make}'}),"
	        + "(device_type:DEVICE_TYPE {name:'{type}'}),"
	        + "(model:MODEL {name:'{model}'}),"
	        + "(protocol:DEVICE_PROTOCOL {name:'{protocol}'}),"
	        + "(version:PROTOCOL_VERSION {name:'{version}'}),"
	        + "sub-[o:owns]->device,"
	        + "device-[r:talksIn]->version, "
	        + "version<-[:hasVersion]-protocol, "
	        + "protocol<-[:talksIn]-model, "
	        + "model<-[:hasModel]-device_type, "
	        + "device_type<-[:hasType]-make-[:hasModel]->model "
	        + "OPTIONAL MATCH (device)-[:isTaggedWith]->(tag) "
	        + "OPTIONAL MATCH device-[p:transportsIn]->nwprotocol return {source_id:device.source_id, unit_id:device.unit_id, "
	        + "is_publishing:device.is_publishing, datasource_name:device.datasource_name, ip:device.ip, connected_port:device.connected_port, writeback_port:device.writeback_port , "
	        + "device_name:device.device_name,latitude:device.latitude,longitude:device.longitude, "
	        + "time_zone:device.time_zone,gmt_offset:device.gmt_offset,gmt_offset_sign:device.gmt_offset_sign,url:device.url,slot:device.slot, "
	        + "tags:collect(distinct tag), nw_protocol:nwprotocol, "
	        + "version:{version:version.name, protocol:protocol.name, model:model.name, deviceType:device_type.name, make:make.name}} as devices ORDER BY device.source_id;";

	// private static final String updateDeviceProperty =
	// "MATCH (device:DEVICE {source_id:'{source_id}'}) SET device.{propertyName}={propertyValue} return device";

	private static final String createDevice = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}),"
	        + "(nwprotocol:NW_PROTOCOL {name:'{nw_protocol}'}),"
	        + "(make:MAKE {name:'{make}'}),"
	        + "(type:DEVICE_TYPE {name:'{type}'}),"
	        + "(model:MODEL {name:'{model}'}),"
	        + "(protocol:DEVICE_PROTOCOL {name:'{protocol}'}),"
	        + "(version:PROTOCOL_VERSION {name:'{version}'}), "
	        + "make-[:hasType]->type, "
	        + "type-[:hasModel]->model<-[:hasModel]-make, "
	        + "model-[:talksIn]->protocol, "
	        + "protocol-[:hasVersion]->version "
	        + "CREATE (device:DEVICE {props}) "
	        + "CREATE sub-[f:owns]->device "
	        + "CREATE device-[g:talksIn]->version "
	        + "CREATE device-[i:transportsIn]->nwprotocol return device;";

	private static final String createUnsubscribedDevice = "MATCH (nwprotocol:NW_PROTOCOL {name:'{nw_protocol}'}),"
	        + "(make:MAKE {name:'{make}'}),"
	        + "(type:DEVICE_TYPE {name:'{type}'}),"
	        + "(model:MODEL {name:'{model}'}),"
	        + "(protocol:DEVICE_PROTOCOL {name:'{protocol}'}),"
	        + "(version:PROTOCOL_VERSION {name:'{version}'}), "
	        + "make-[:hasType]->type, "
	        + "type-[r:hasModel]->model<-[:hasModel]-make, "
	        + "model-[:talksIn]->protocol, "
	        + "protocol-[:hasVersion]->version "
	        + "CREATE (device:DEVICE {props}) "
	        + "CREATE device-[g:talksIn]->version "
	        + "CREATE device-[i:transportsIn]->nwprotocol return device;";

	private static final String updateDevice = "MATCH (device:DEVICE {source_id:'{source_id}'}),(sub:SUBSCRIPTION {sub_id:'{sub_id}'}),sub-[o:owns]->device SET device={props} return device;";

	private static final String countOfDeviceWithAProtocol = "MATCH (make:MAKE {name:'{make}'}),"
	        + "(type:DEVICE_TYPE {name:'{type}'}),"
	        + "(model:MODEL {name:'{model}'}),"
	        + "(protocol:DEVICE_PROTOCOL {name:'{protocol}'}),"
	        + "(version:PROTOCOL_VERSION {name:'{version}'}), "
	        + "make-[:hasType]->type, "
	        + "type-[:hasModel]->model<-[:hasModel]-make, "
	        + "model-[:talksIn]->protocol, "
	        + "protocol-[:hasVersion]->version "
	        + "OPTIONAL MATCH version-[r:hasVersion]->(recycle:RECYCLE ) WITH MIN(recycle.unit_id) as unitId,version "
	        + "OPTIONAL MATCH version-[r:hasVersion]->(recycle2:RECYCLE {unit_id:unitId}) "
	        + "OPTIONAL MATCH (device:DEVICE)-[:talksIn]->version DELETE r,recycle2 return CASE WHEN unitId is null "
	        + "THEN MAX(device.unit_id)+1 ELSE unitId END as unitId;";

	private static final String updateWritebackConf = "MATCH (device:DEVICE {source_id:'{source_id}'}),(sub:SUBSCRIPTION {sub_id:'{sub_id}'}),sub-[o:owns]->device SET {writebackconf} return device;";

	private static final String getDataSourceName = "MATCH (device:DEVICE {source_id:'{source_id}'}),(sub:SUBSCRIPTION {sub_id:'{sub_id}'}),sub-[o:owns]->device return device.datasource_name;";

	private static final String getAllUnsubDevices = "MATCH (device:`DEVICE`) OPTIONAL MATCH device<-[r:owns]-(s:SUBSCRIPTION) "
	        + "OPTIONAL MATCH device-[:transportsIn]->(nwp:NW_PROTOCOL) "
	        + "OPTIONAL MATCH device-[:talksIn]->(dp:PROTOCOL_VERSION) "
	        + "OPTIONAL MATCH dp<-[:hasVersion]-(protocol:DEVICE_PROTOCOL) "
	        + "OPTIONAL MATCH protocol<-[:talksIn]-(model:MODEL) "
	        + "OPTIONAL MATCH model<-[:hasModel]-(device_type:DEVICE_TYPE) "
	        + "OPTIONAL MATCH device_type<-[:hasType]-(make:MAKE)-[:hasModel]->model "
	        + "OPTIONAL MATCH (device)-[:isTaggedWith]->(tag) with r,device,tag,nwp,dp,protocol,model,device_type,make where r is NULL "
	        + "RETURN {source_id:device.source_id, device_id:device.device_id, unit_id:device.unit_id,is_publishing:device.is_publishing,"
	        + "datasource_name:device.datasource_name,ip:device.ip,connected_port:device.connected_port,writeback_port:device.writeback_port,"
	        + "device_name:device.device_name,latitude:device.latitude,longitude:device.longitude,"
	        + "time_zone:device.time_zone,gmt_offset:device.gmt_offset,gmt_offset_sign:device.gmt_offset_sign,url:device.url,slot:device.slot,"
	        + "tags:collect(distinct tag),"
	        + "nw_protocol:nwp,version:{version:dp.name,protocol:protocol.name,model:model.name,deviceType:device_type.name,make:make.name}} as device;";
	// MATCH (n:`DEVICE`) OPTIONAL MATCH n<-[r:owns]-(s:SUBSCRIPTION) with r,n
	// where r is NULL RETURN n;

	private static final String claimDevice = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}),(device:DEVICE {source_id:'{source_id}'}) CREATE sub-[:owns]->device return device";

	private static final String getDeviceReference = "MATCH (d:DEVICE) where d.source_id IN[{source_ids}] RETURN d.source_id,d.device_id;";

	private static final String getDeviceWithUnitQuery = "MATCH (make:MAKE {name:'{make}'})-[:hasType]-> "
	        + "(device_type:DEVICE_TYPE {name:'{type}'})-[:hasModel]-> "
	        + "(model:MODEL{name:'{model}'})<-[:hasModel]-make, model-[:talksIn]-> "
	        + "(protocol:DEVICE_PROTOCOL{name:'{protocol}'})-[:hasVersion]-> "
	        + "(version:PROTOCOL_VERSION {name:'{version}'})<-[:talksIn]-(device:DEVICE {unit_id:{unitId}}) , "
	        + "OPTIONAL MATCH (device)<-[:owns]-(sub:SUBSCRIPTION) "
	        + "OPTIONAL MATCH (device)-[p:transportsIn]->(c:NW_PROTOCOL) "
	        + "OPTIONAL MATCH (device)-[:isTaggedWith]->(tag) "
	        + "return  {source_id:device.source_id, device_id:device.device_id, unit_id:device.unit_id, is_publishing:device.is_publishing, "
	        + "datasource_name:device.datasource_name,ip:device.ip, connected_port:device.connected_port, writeback_port:device.writeback_port, "
	        + "device_name:device.device_name,latitude:device.latitude,longitude:device.longitude, "
	        + "timezone:device.time_zone,gmt_offset:device.gmt_offset,gmt_offset_sign:device.gmt_offset_sign,URL:device.URL,slot:device.slot, "
	        + "tags:collect(distinct tag),nw_protocol:c, version:{version:version.name, protocol:protocol.name, model:model.name, deviceType:device_type.name, make:make.name},subscription:sub} as device;";

	@Override
	public Device getDevice(String sourceId) {
		String query = getDeviceQuery.replace(SOURCE_ID, sourceId);
		JSONArray deviceJsonArray = null;
		try {
			deviceJsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query,
			        null, ROW.toString());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching device details",
			        e);
		}
		if (deviceJsonArray == null) {
			return null;
		}
		Device device = convertToDevice(deviceJsonArray.getJSONObject(0));
		/*
		 * if (device.getConfigurations() != null) { try {
		 * @SuppressWarnings("serial") Type type = new
		 * TypeToken<List<DevicePointData>>() { }.getType();
		 * List<DevicePointData> configurations = objectBuilderUtil
		 * .getGson().fromJson( device.getConfigurations().toString(), type);
		 * device.setConfigurations(configurations); } catch (Exception e) {
		 * throw new DeviceCloudException(
		 * DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_VALID,
		 * POINT_CONFIGURATIONS.getDescription()); } }
		 */
		return device;
	}

	@Override
	public Device getDevice(String unitId, ConfigurationSearch version) {

		String query = getDeviceWithUnitQuery.replace(MAKE, version.getMake())
		        .replace(TYPE, version.getDeviceType())
		        .replace(MODEL, version.getModel())
		        .replace(PROTOCOL, version.getProtocol())
		        .replace(VERSION, version.getVersion())
		        .replace(UNIT_ID, unitId);

		JSONArray deviceJsonArray = null;
		try {
			deviceJsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query,
			        null, ROW.toString());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching device details",
			        e);
		}
		if (deviceJsonArray == null) {
			return null;
		}
		Device device = convertToDevice(deviceJsonArray.getJSONObject(0));
		return device;
	}

	@Override
	public Device getDevice(String sourceId, Subscription subscription) {
		String query = getDeviceQueryWithSub.replace(SOURCE_ID, sourceId)
		        .replace(SUB_ID, subscription.getSubId());
		JSONArray deviceJsonArray = null;
		try {
			deviceJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.toString());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching device details",
			        e);
		}
		if (deviceJsonArray == null) {
			return null;
		}
		Device device = convertToDevice(deviceJsonArray.getJSONObject(0));
		return device;
	}

	@Override
	public void registerDevice(Device device) {
		Device clone = cloneDevice(device);
		String json = objectBuilderUtil.getLowerCaseUnderScoreGson().toJson(
		        clone);
		String query = createUnsubscribedDevice
		        .replace(NW_PROTOCOL, device.getNetworkProtocol().getName())
		        .replace(MAKE, device.getVersion().getMake())
		        .replace(TYPE, device.getVersion().getDeviceType())
		        .replace(MODEL, device.getVersion().getModel())
		        .replace(PROTOCOL, device.getVersion().getProtocol())
		        .replace(VERSION, device.getVersion().getVersion());

		JSONArray insertDeviceResult = null;
		try {
			insertDeviceResult = exexcuteQuery(neo4jURI, query, json,
			        ROW.getFieldName());
		} catch (Exception e) {
			throw new PersistenceException("Error in creating device", e);
		}

		if (insertDeviceResult == null) {
			throw new PersistenceException(
			        "Error in creating device,Response is null");
		}
	}

	@Override
	public void updateConfigurations(String sourceId,
	        DeviceConfigTemplate deviceConfigTemplate) {
		// TODO To implement or remove
	}

	@Override
	public List<DevicePointData> getDeviceConfig(String sourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Device> getAllDevices() {
		List<Device> devices = new ArrayList<Device>();
		JSONArray devicesJsonArray = null;
		try {
			devicesJsonArray = exexcuteQuery(neo4jURI, getAllDeviceQuery, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching allDevices of DeviceCloud", e);
		}
		if (devicesJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < devicesJsonArray.length(); i++) {
			Device device = convertToDevice(devicesJsonArray.getJSONObject(i));
			device.setConfigurations(null);
			devices.add(device);
		}
		return devices;
	}

	@Override
	public List<Device> getAllDevices(String subId, List<String> tagNames) {
		List<Device> devices = new ArrayList<Device>();
		String query = getAllDeviceQueryFilterTags.replace(SUB_ID, subId);
		if (CollectionUtils.isEmpty(tagNames)) {
			query = query.replace(CypherConstants.TAG_FILTER, ",");
		} else {
			String tagFilter = ",";
			for (String tagName : tagNames) {
				tagFilter += "device-[:isTaggedWith]->(:DEVICE_TAG {name:'"
				        + tagName + "'}),";
			}
			query = query.replace(CypherConstants.TAG_FILTER, tagFilter);
		}
		JSONArray devicesJsonArray = null;
		try {
			devicesJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching allDevices of a Subscription", e);
		}
		if (devicesJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < devicesJsonArray.length(); i++) {
			Device device = convertToDevice(devicesJsonArray.getJSONObject(i));
			device.setConfigurations(null);
			devices.add(device);
		}
		return devices;
	}

	@Override
	public void insertDevice(Device device) {

		Device clone = cloneDevice(device);
		String json = objectBuilderUtil.getLowerCaseUnderScoreGson().toJson(
		        clone);
		String subId = device.getSubscription().getSubId().toString();
		String query = createDevice.replace(CypherConstants.SUB_ID, subId)
		        .replace(NW_PROTOCOL, device.getNetworkProtocol().getName())
		        .replace(MAKE, device.getVersion().getMake())
		        .replace(TYPE, device.getVersion().getDeviceType())
		        .replace(MODEL, device.getVersion().getModel())
		        .replace(PROTOCOL, device.getVersion().getProtocol())
		        .replace(VERSION, device.getVersion().getVersion());

		JSONArray insertDeviceResult = null;
		try {
			insertDeviceResult = exexcuteQuery(neo4jURI, query, json,
			        ROW.getFieldName());
		} catch (Exception e) {
			new PersistenceException("Error in creating device", e);
		}

		if (insertDeviceResult == null) {
			new PersistenceException(
			        "Error in creating device,Response is null");
		}
	}

	@Override
	public void updateDevice(Device device) {
		String query = updateDevice.replace(SOURCE_ID, device.getSourceId())
		        .replace(SUB_ID, device.getSubscription().getSubId());
		Device clone = cloneDevice(device);
		String json = objectBuilderUtil.getLowerCaseUnderScoreGson().toJson(
		        clone);
		try {
			exexcuteQuery(neo4jURI, query, json, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in Updating device", e);
		}
	}

	@Override
	public HashMap<String, String> getDeviceReference(Set<String> sourceIds) {
		String joinedSrcIds = StringUtils.join(sourceIds, "','");
		joinedSrcIds = "'" + joinedSrcIds + "'";
		String query = getDeviceReference.replace(SOURCE_IDs, joinedSrcIds);
		JSONArray resultJsonArray = null;
		try {
			resultJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in getting device references", e);
		}
		if (resultJsonArray == null) {
			return null;
		}

		HashMap<String, String> devReference = new HashMap<String, String>();
		for (int i = 0; i < resultJsonArray.length(); i++) {
			JSONObject deviceJson = resultJsonArray.getJSONObject(i);
			JSONArray deviceJsonArray = deviceJson.getJSONArray(ROW
			        .getFieldName());
			devReference.put(deviceJsonArray.getString(0),
			        deviceJsonArray.getString(1));
		}
		return devReference;
	}

	private Device cloneDevice(Device device) {
		Device clone = new Device();
		clone.setSourceId(device.getSourceId());
		clone.setDatasourceName(device.getDatasourceName());
		clone.setIsPublishing(device.getIsPublishing());
		clone.setConfigurations(device.getConfigurations());
		clone.setTimeZone(device.getTimeZone());
		clone.setUnitId(device.getUnitId());
		clone.setDeviceId(device.getDeviceId());
		clone.setIp(device.getIp());
		clone.setConnectedPort(device.getConnectedPort());
		clone.setWriteBackPort(device.getWriteBackPort());
		clone.setDeviceName(device.getDeviceName());
		clone.setLatitude(device.getLatitude());
		clone.setLongitude(device.getLongitude());
		clone.setTimezone(device.getTimezone());
		clone.setGmtOffset(device.getGmtOffset());
		clone.setURL(device.getURL());
		clone.setSlot(device.getSlot());
		clone.setUserName(device.getUserName());
		clone.setPassword(device.getPassword());
		clone.setToken(device.getToken());
		return clone;
	}

	private Device convertToDevice(JSONObject deviceJson) {
		JSONArray deviceJsonArray = deviceJson.getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson();
		Device device = lowerCaseUnderScoreGson.fromJson(deviceJsonArray
		        .getJSONObject(0).toString(), Device.class);
		if (BooleanUtils.isFalse(device.getIsPublishing())) {
			device.setDatasourceName(null);
		}
		return device;
	}

	@Override
	public Integer generateUnitId(ConfigurationSearch version) {
		try {
			String query = countOfDeviceWithAProtocol.replace(MAKE,
			        version.getMake());
			query = query.replace(TYPE, version.getDeviceType());
			query = query.replace(MODEL, version.getModel());
			query = query.replace(PROTOCOL, version.getProtocol());
			query = query.replace(VERSION, version.getVersion());

			JSONArray maxUnitIdJson = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
			if (maxUnitIdJson == null) {
				throw new NoResultException();
			}
			int availableUnitId = maxUnitIdJson.getJSONObject(0)
			        .getJSONArray(ROW.getFieldName()).getInt(0);
			return availableUnitId;
		} catch (Exception e) {
			LOGGER.error(
			        "Error in Finding unitId,Assuming no devices are created",
			        e);
		}
		return unitIdSeed;
	}

	/**
	 * Service Method for update write back configuration of a device
	 * 
	 * @param sourceId
	 * @param device
	 */
	public void updateWritebackConf(String sourceId, Device device) {

		String query = updateWritebackConf.replace(SOURCE_ID, sourceId)
		        .replace(SUB_ID, device.getSubscription().getSubId());
		String properties = "";

		if (device.getIp() != null) {
			properties += "device.ip='" + device.getIp() + "'";
		}
		if (device.getConnectedPort() != null) {
			if (properties != "") {
				properties += ",";
			}
			properties += "device.connected_port=" + device.getConnectedPort();
		}
		if (device.getWriteBackPort() != null) {
			if (properties != "") {
				properties += ",";
			}
			properties += "device.writeback_port=" + device.getWriteBackPort();
		}

		if (properties != "") {
			query = query.replace(WRITEBACK_CONF, properties);
		}
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in Updating device writeback configurations", e);
		}

	}

	/**
	 * Service Method to get datasource name of a device
	 * 
	 * @param sourceId
	 * @return dataSourceName
	 */
	public String getDatasourceName(Subscription subscription, String sourceId) {
		String query = getDataSourceName.replace(SOURCE_ID, sourceId).replace(
		        SUB_ID, subscription.getSubId());
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
			        ROW.toString());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching device details",
			        e);
		}
		if (jsonArray == null) {
			return null;
		}
		JSONArray deviceJsonArray = jsonArray.getJSONObject(0).getJSONArray(
		        ROW.getFieldName());
		Object object = deviceJsonArray.get(0);
		if (object.equals(null)) {
			return null;
		}
		return object.toString();
	}

	@Override
	public List<Device> getAllDeviceOfProtocol(Subscription subscription,
	        ConfigurationSearch searchDTO) {
		List<Device> devices = new ArrayList<Device>();

		String query = getAllDeviceQueryByPV
		        .replace(SUB_ID, subscription.getSubId())
		        .replace(MAKE, searchDTO.getMake())
		        .replace(TYPE, searchDTO.getDeviceType())
		        .replace(MODEL, searchDTO.getModel())
		        .replace(PROTOCOL, searchDTO.getProtocol())
		        .replace(VERSION, searchDTO.getVersion());
		JSONArray devicesJsonArray = null;
		try {
			devicesJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching getAllDeviceOfProtocol", e);
		}
		if (devicesJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < devicesJsonArray.length(); i++) {
			Device device = convertToDevice(devicesJsonArray.getJSONObject(i));
			devices.add(device);
		}
		return devices;
	}

	@Override
	public void claimDevice(String sourceId, Subscription subscription) {

		String query = claimDevice.replace(SUB_ID, subscription.getSubId())
		        .replace(SOURCE_ID, sourceId);

		JSONArray claimedDevice = null;
		try {
			claimedDevice = exexcuteQuery(neo4jURI, query, null, ROW.toString());
		} catch (JSONException | IOException e) {
			LOGGER.error("Error in claiming the device", e);
		}
		if (claimedDevice == null) {
			throw new PersistenceException("Error in claiming the device");
		}
	}

	@Override
	public List<Device> getAllUnSubscribed() {
		List<Device> devices = new ArrayList<Device>();
		JSONArray devicesJsonArray = null;
		try {
			devicesJsonArray = exexcuteQuery(neo4jURI, getAllUnsubDevices,
			        null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching all unsubscribed devices", e);
		}
		if (devicesJsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < devicesJsonArray.length(); i++) {
			Device device = convertToDevice(devicesJsonArray.getJSONObject(i));
			device.setConfigurations(null);
			devices.add(device);
		}
		return devices;
	}

}

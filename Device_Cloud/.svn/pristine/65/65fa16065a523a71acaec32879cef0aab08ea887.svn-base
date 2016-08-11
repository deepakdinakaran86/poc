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
package com.pcs.datasource.repository.titan;

import static com.pcs.datasource.repository.utils.CypherConstants.prepareCypherArray;
import static com.pcs.datasource.repository.utils.GremlinConstants.CONFIGURED_AS;
import static com.pcs.datasource.repository.utils.GremlinConstants.CONFIGURED_POINT;
import static com.pcs.datasource.repository.utils.GremlinConstants.CONTAINED_IN;
import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE;
import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE_CONFIG_TEMP;
import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE_PROTOCOL;
import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE_TYPE;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_MODEL;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_PARAMETER;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_POINT;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_TEMPLATE;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_TYPE;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_VERSION;
import static com.pcs.datasource.repository.utils.GremlinConstants.IS_CONFIGURED_WITH;
import static com.pcs.datasource.repository.utils.GremlinConstants.MAKE;
import static com.pcs.datasource.repository.utils.GremlinConstants.MEASURES_IN;
import static com.pcs.datasource.repository.utils.GremlinConstants.MODEL;
import static com.pcs.datasource.repository.utils.GremlinConstants.OWNS;
import static com.pcs.datasource.repository.utils.GremlinConstants.PARAMETER;
import static com.pcs.datasource.repository.utils.GremlinConstants.PHYSICAL_QUANTITY;
import static com.pcs.datasource.repository.utils.GremlinConstants.POINT;
import static com.pcs.datasource.repository.utils.GremlinConstants.PROTOCOL_VERSION;
import static com.pcs.datasource.repository.utils.GremlinConstants.SUBSCRIPTION;
import static com.pcs.datasource.repository.utils.GremlinConstants.TALKSIN;
import static com.pcs.datasource.repository.utils.GremlinConstants.UNIT;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResults;
import static com.pcs.devicecloud.enums.Status.FAILURE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.reflect.TypeToken;
import com.pcs.datasource.dto.AlarmExtension;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.GeneralBatchResponse;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.model.udt.DeviceFieldMap;
import com.pcs.datasource.repository.DeviceConfigRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;
import com.pcs.devicecloud.enums.Status;

/**
 * This class is responsible for defining the repository services related to the
 * device point configuration
 * 
 * @author pcseg323(Greeshma)
 * @date Dec 2015
 */
@Repository("deviceConfigTitan")
public class DeviceConfigTitanRepositoryImpl implements DeviceConfigRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String getConfigTemp = "g.V().hasLabel(vertexLabel).has('subId',subId)"
	        + ".out(hasTemplate).hasLabel(deviceConfigTmp).has('isDeleted',false).filter{it.get().value('name').equalsIgnoreCase(tmpName)}";

	private static final String createTemplate = "s = g.V().hasLabel(subscription).has('subId',subId).next();"
	        + "v =  g.V().hasLabel(make,'name',makeName)"
	        + ".out(hasType).hasLabel(deviceTypeLabel).has('name',deviceType)"
	        + ".out(hasModel).hasLabel(deviceModelLabel).has('name',model)"
	        + ".out(talksIn).hasLabel(protocolLabel).has('name',deviceProtocol)"
	        + ".out(hasVersion).hasLabel(versionLabel).has('name',protocolVersion).next();"
	        + "tmp = graph.addVertex(label,vertexLabel,'name',template,'isDeleted',isDeleted);"
	        + "s.addEdge(hasTemplate,tmp);"
	        + "v.addEdge(hasTemplate,tmp);"
	        + "tmp.id()";

	private static final String createConfigPoint = "points = pointsMap; template =g.V().hasLabel(subscription).has('subId',subId).out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tmpName).next();"
	        + "ct = g.V(template.id()).next() ; points.each{value -> pq = g.V().hasLabel(physicalQuantity).filter{it.get().value('name').equalsIgnoreCase(value.get('physicalQuantity'))}.next();"
	        + "u = g.V().hasLabel(unit).filter{it.get().value('name').equalsIgnoreCase(value.get('unit'))}.next(); pt = graph.addVertex(label,configuredPt) ;"
	        + "value.each{key,prop -> pt.property (key , prop)};"
	        + " pt.addEdge(containedIn,pq);ct.addEdge(hasPoint,pt);"
	        + "pt.addEdge(measuresIn,u); }";

	private static final String createConfigPointWithRel = "points = pointsMap; points.each { value ->  pnt = g.V().hasLabel(deviceConfigTmp).has('name',tmpName)"
	        + ".in(hasTemplate).hasLabel(protocolVersion).out(hasPoint).has('pointId',value.get('pointId')).next();"
	        + "cp = g.V().hasLabel(deviceConfigTmp).has('name',tmpName).out('hasPoint').hasLabel(configuredPt).has('pointId',value.get('pointId')).next();"
	        + "pnt.addEdge(configuredAs,cp);}";

	private static final String searchProtoVersion = "g.V().hasLabel(make,'name',makeName)"
	        + ".out(hasType).hasLabel(deviceTypeLabel).has('name',deviceType).out(hasModel).hasLabel(deviceModelLabel).has('name',model)"
	        + ".out(talksIn).hasLabel(protocolLabel).has('name',deviceProtocol).out(hasVersion).hasLabel(versionLabel).has('name',protocolVersion).next().id();";

	private static final String checkPointIsValid = "g.V(vertexId).hasLabel(protoVersion).out(hasPoint).hasLabel(pointLabel).has('pointId',pointId).has('pointName',pointName).valueMap()";

	private static final String checkParamIsValid = "g.V().hasLabel(subscription).has('subId',subId)"
	        + ".out(hasParameter).hasLabel(parameterLabel).has('name',tmpName).out(containedIn).has('name',physicalQuantity).valueMap()";

	private static final String getTempConfigPointDispNames = "g.V().hasLabel(subscription).has('subId',subId)"
	        + ".out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tmpName).out(hasPoint).values('displayName').dedup()";

	private static final String deleteConfigPoint = "template =g.V().hasLabel(subscription).has('subId',subId).out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tmpName).next();"
	        + "g.V(template.id()).out(hasPoint).hasLabel(configuredPt).has('displayName',within({pointIds})).each { "
	        + "g.V(it.id()).outE(containedIn).each{it.remove();};"
	        + "g.V(it.id()).outE(measuresIn).each{it.remove();};"
	        + "if(g.V( it.id()).inE(isConfiguredWith).hasNext()){"
	        + "g.V( it.id()).inE(isConfiguredWith).each{it.remove();};};"
	        + "if(g.V( it.id()).inE(configuredAs).hasNext())"
	        + "{g.V(it.id()).inE(configuredAs).each{it.remove();};};"
	        + "g.V(it.id()).inE(hasPoint).each{it.remove();};"
	        + "g.V(it.id()).drop()};";

	private static final String updateConfigPointWithRel = "points = pointsMap;  template =g.V().hasLabel(subscription).has('subId',subId).out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tmpName).next();"
	        + " points.each{value ->"
	        + "pnt = g.V(template.id()).in(hasTemplate).hasLabel(protocolVersion).out(hasPoint).hasLabel(point).has('pointId',value.get('pointId')).next();"
	        + " cp = g.V(template.id()).out(hasPoint).hasLabel(configuredPt).has('displayName',value.get('displayName')).next();"
	        + "if(g.V(cp.id()).inE(configuredAs).hasNext()){"
	        + "g.V(cp.id()).inE(configuredAs).each{it.remove();}};"
	        + "pnt.addEdge(configuredAs,cp);}";

	private static final String updateConfigPoint = "points = pointsMap;  template = g.V().hasLabel(subscription).has('subId',subId).out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tmpName).next();"
	        + "points.each{value -> pt =  g.V(template.id()).out(hasPoint).hasLabel(configuredPt).has('pointId',value.get('pointId'));"
	        + "if( g.V(pt.id()).outE(containedIn).hasNext()){"
	        + "g.V(pt.id()).outE(containedIn).each{it.remove();}}; "
	        + "if (g.V(pt.id()).outE(measuresIn).hasNext()){"
	        + "g.V(pt.id()).outE(measuresIn).each{it.remove();}};}";

	private static final String updateConfigPointRel = "points = pointsMap;  template = g.V().hasLabel(subscription).has('subId',subId).out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tmpName).next();"
	        + "points.each{value -> pt =  g.V(template.id()).out(hasPoint).hasLabel(configuredPt).has('pointId',value.get('pointId')).next();"
	        + "pq = g.V().hasLabel(physicalQuantity).filter{it.get().value('name').equalsIgnoreCase(value.get('physicalQuantity'))}.next(); "
	        + "u = g.V().hasLabel(unit).filter{it.get().value('name').equalsIgnoreCase(value.get('unit'))}.next(); "
	        + " value.each{key,prop -> pt.property (key , prop)};pt.addEdge(containedIn,pq);pt.addEdge(measuresIn,u);}";

	private static final String getDeviceConfiguration = " g.V().hasLabel(subscription).has('subId',subId).out(owns).hasLabel(device).has('sourceId',sourceId).as('name','configPoints')"
	        + ".out(talksIn).hasLabel(versionLabel).as('deviceProtocolVersion')"
	        + ".in(hasVersion).hasLabel(protocolLabel).as('deviceProtocol')"
	        + ".in(talksIn).hasLabel(deviceModelLabel).as('deviceModel')"
	        + ".in(hasModel).hasLabel(deviceTypeLabel).as('deviceType')"
	        + ".in(hasType).hasLabel(make).as('deviceMake')"
	        + ".select('name','configPoints','deviceProtocolVersion','deviceProtocol','deviceModel','deviceType','deviceMake')"
	        + ".by(out(isConfiguredWith).hasLabel(deviceConfigTmp).values('name').fold())"
	        + ".by(out('isConfiguredWith').hasLabel(configuredPt).valueMap().fold()).by(values('name'))"
	        + ".by(values('name')).by(values('name')).by(values('name')).by(values('name'))";

	private static final String getProtcolVersionPoints = "g.V().hasLabel(make,'name',makeName).out(hasType).hasLabel(deviceTypeLabel).has('name',deviceTypeName)"
	        + ".out(hasModel).hasLabel(deviceModelLabel).has('name',modelName).out(talksIn).hasLabel(protocolLabel).has('name',protocolName)"
	        + ".out(hasVersion).hasLabel(versionLabel).has('name',versionName).out(hasPoint).hasLabel(point).valueMap();";

	private static final String updateDeviceConfigPointRel = "points = pointsMap;  template =g.V().hasLabel(subscription).has('subId',subId).out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tempName).next();"
	        + "points.each{value -> cp = g.V(template.id()).out('hasPoint').hasLabel(configuredPt).has('displayName',value.get('displayName')).next();"
	        + "g.V(template.id()).in(isConfiguredWith).hasLabel(deviceLabel).each{"
	        + "device = g.V(it.id()).next();device.addEdge('isConfiguredWith',cp)}}";

	private static final String updateConfigTemp = "g.V().hasLabel(subscription).has('subId',subId)"
	        + ".out(hasTemplate).hasLabel(deviceConfigTmp).has('isDeleted',false)"
	        + ".filter{it.get().value('name').equalsIgnoreCase(tempName)}.property('description',description)";

	private String subscriptionId = null;

	private static final String deleteConfADevice = " if(g.V(deviceId).out('isConfiguredWith').hasLabel('DEVICE_CONFIG_TEMP').hasNext()){g.V(deviceId).outE('isConfiguredWith').drop();}else{g.V(deviceId).out('isConfiguredWith').drop();};";

	private static final String assignTempToADevice = "template =g.V().hasLabel('SUBSCRIPTION').has('subId',subId).out('hasTemplate').has('name',templateName).next();"
	        + "tempId = template.id();"
	        + "device = g.V().hasLabel('DEVICE').has('sourceId', sourceId).next();"
	        + "device.addEdge('isConfiguredWith',template);"
	        + "g.V(tempId).out('hasPoint').hasLabel('CONFIGURED_POINT').each{device.addEdge('isConfiguredWith',it)}";

	private static final String createConfPointForADevice = "points = pointsMap;  device = device = g.V().hasLabel('DEVICE').has('sourceId', sourceId).next();"
	        + "points.each{value -> "
	        + "pq = g.V().hasLabel('PHYSICAL_QUANTITY').has('name',value.get('physicalQuantity')).next();"
	        + "u = g.V().hasLabel('UNIT').has('name',value.get('unit')).next();"
	        + "cp = graph.addVertex(label,'CONFIGURED_POINT') ;"
	        + "value.each{key,prop -> cp.property (key , prop)};"
	        + "cp.addEdge('containedIn',pq);"
	        + "cp.addEdge('measuresIn',u);"
	        + "device.addEdge('isConfiguredWith',cp)}";

	private static final String createPointRelnToDeviceCp = "points = pointsMap;  deviceId = g.V().hasLabel('DEVICE').has('sourceId', sourceId).next().id();"
	        + "points.each{value -> "
	        + "cp = g.V(deviceId).out('isConfiguredWith').has('pointId',value.get('pointId')).next();"
	        + "pt =g.V(versionId).out('hasPoint').has('pointId',value.get('pointId')).next();"
	        + "pt.addEdge('configuredAs',cp);}";

	private static final String detachTempFromDevices = " g.V().hasLabel('SUBSCRIPTION').has('subId',subId).out('hasTemplate').hasLabel('DEVICE_CONFIG_TEMP').has('name',within(tempNames)).property('isDeleted',true).inE('isConfiguredWith').drop()";

	private static final String getDevice = "deviceId =g.V().hasLabel('DEVICE').has('sourceId', sourceId).next().id()";

	/**
	 * Method to check a device config template name already exists or not
	 * 
	 * @param tempName
	 * @return - true if the template name exists for the subscription else
	 *         false
	 */
	@Override
	public boolean isDeviceConfigTempNameExist(String subId, String tempName) {

		tempName = tempName.toLowerCase();

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("vertexLabel", SUBSCRIPTION);
		params.put("subId", subId);
		params.put("tmpName", tempName);
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);

		params.put("hasTemplate", HAS_TEMPLATE);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getConfigTemp, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return false;
			}

		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return true;
	}

	/**
	 * Method to get device configuration template's parent by traversing
	 * through device make,device type,device model,device protocol and protocol
	 * version.
	 * 
	 * @param configSearch
	 *            - configuration template
	 * @return - a reference string of the parent
	 */

	@Override
	public String findParentData(ConfigurationSearch configSearch) {

		Map<String, Object> params = new HashMap<String, Object>();
		String uniqueId = null;

		params.put("make", MAKE);
		params.put("deviceTypeLabel", DEVICE_TYPE);
		params.put("deviceModelLabel", MODEL);
		params.put("protocolLabel", DEVICE_PROTOCOL);
		params.put("versionLabel", PROTOCOL_VERSION);

		params.put("makeName", configSearch.getMake());
		params.put("deviceType", configSearch.getDeviceType());
		params.put("model", configSearch.getModel());
		params.put("deviceProtocol", configSearch.getProtocol());
		params.put("protocolVersion", configSearch.getVersion());

		params.put("hasType", HAS_TYPE);
		params.put("hasModel", HAS_MODEL);
		params.put("talksIn", TALKSIN);
		params.put("hasVersion", HAS_VERSION);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(searchProtoVersion, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return uniqueId;
			}
			uniqueId = (String)result.getObject().toString();
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return uniqueId;
	}

	/**
	 * Method to check a config point is valid - config point should be a valid
	 * point of protocol version
	 * 
	 * @param configSearch
	 *            - ConfigurationSearch
	 * @param configPoint
	 *            - Configuration point
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
	 *            - parent reference
	 * @param configPoint
	 *            - Configuration point
	 * @return - true if point is a valid point of protocol version else false
	 */
	@Override
	public boolean isValidPoint(String parentRef, ConfigPoint configPoint) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vertexId", Integer.valueOf(parentRef).intValue());
		params.put("protoVersion", PROTOCOL_VERSION);
		params.put("hasPoint", HAS_POINT);
		params.put("pointId", configPoint.getPointId());
		params.put("pointName", configPoint.getPointName());
		params.put("pointLabel", POINT);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(checkPointIsValid, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return false;
			}
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return true;
	}

	/**
	 * Method to check a parameter is valid - parameter of the subscription with
	 * a valid parameter name and physical quantity
	 * 
	 * @param subId
	 *            - subscriptionId
	 * @param configPoint
	 *            - Configure point with parameter info
	 * @return - true if parameter is a valid parameter of subscription else
	 *         false
	 */

	@Override
	public boolean isValidParameter(String subId, ConfigPoint configPoint) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subId", subId);
		params.put("subscription", SUBSCRIPTION);
		params.put("hasParameter", HAS_PARAMETER);
		params.put("containedIn", CONTAINED_IN);
		params.put("tmpName", configPoint.getParameter());
		params.put("physicalQuantity", configPoint.getPhysicalQuantity());
		params.put("parameterLabel", PARAMETER);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(checkParamIsValid, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return false;
			}
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return true;
	}

	/**
	 * Method to check if a device configuration template is valid - by checking
	 * the device make,device type,device model,device protocol and protocol
	 * version . Also the template should be in an hierarchy of
	 * device_make->device_type->device_model->device_protocol->protocol_version
	 * ->config_template
	 * 
	 * @param configTemp
	 *            - configuration template
	 * @return - true if configuration is valid else false
	 */
	@Override
	public boolean isValidDeviceConfigTemp(DeviceConfigTemplate configTemp) {
		return false;
	}

	/**
	 * Method to save device configuration template
	 * 
	 * @param configTemp
	 */

	@Override
	public void saveDeviceConfigTemp(String subId,
	        DeviceConfigTemplate configTemp, boolean isWithPointRef) {
		this.subscriptionId = subId;
		String parentNodeId = saveTemplate(configTemp);
		if (parentNodeId == null) {
			throw new PersistenceException(
			        "Unable to find config template's node id");
		}
		saveConfigPoint(subId, configTemp.getName(),
		        configTemp.getConfigPoints(), isWithPointRef);

	}

	/**
	 * Method to update device configuration template
	 * 
	 * @param configTemp
	 */
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

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
			params.put("subscription", SUBSCRIPTION);
			params.put("subId", subId);
			params.put("hasTemplate", HAS_TEMPLATE);
			params.put("tempName", configTemplate.getName().toLowerCase());
			params.put("description", configTemplate.getDescription());
			Client client = titanSessionManager.getClient();
			client.submit(updateConfigTemp, params);

		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

	/**
	 * Repository method to fetch all the device configuration template of a
	 * subscripotion with an optional filtere on protocol version
	 * 
	 * @param subId
	 * @param conSearch
	 * @return {@link List<DeviceConfigTemplate>}
	 */

	@Override
	public List<DeviceConfigTemplate> getAllConfTemplates(String subId,
	        ConfigurationSearch conSearch) {

		Map<String, Object> params = new HashMap<String, Object>();
		String getAllConfTemplates = null;
		List<DeviceConfigTemplate> deviceConfigTemplates = new ArrayList<DeviceConfigTemplate>();
		if (conSearch != null && conSearch.getMake() != null) {
			getAllConfTemplates = " g.V().hasLabel(make,'name',makeName).as('deviceMake').out(hasType).hasLabel(deviceTypeLabel).has('name',deviceTypeName).as('deviceType')"
			        + ".out(hasModel).hasLabel(deviceModelLabel).has('name',modelName).as('deviceModel')"
			        + ".out(talksIn).hasLabel(protocolLabel).has('name',protocolName).as('deviceProtocol')"
			        + ".out(hasVersion).hasLabel(versionLabel).has('name',versionName).as('deviceProtocolVersion')"
			        + ".out(hasTemplate).hasLabel(deviceConfigTmp).as('name','isDeleted','configPoints')"
			        + ".in(hasTemplate).hasLabel(subscription).has('subId',subId)"
			        + ".select('deviceMake','deviceType','deviceModel','deviceProtocol','deviceProtocolVersion','name','isDeleted','configPoints')"
			        + ".by(values('name')).by(values('name')).by(values('name')).by(values('name')).by(values('name')).by(values('name')).by(values('isDeleted')).by(out(hasPoint).hasLabel(configuredPt).valueMap().fold())";

			params.put("makeName", conSearch.getMake());
			params.put("deviceTypeName", conSearch.getDeviceType());
			params.put("modelName", conSearch.getModel());
			params.put("protocolName", conSearch.getProtocol());
			params.put("versionName", conSearch.getVersion());

		} else {
			getAllConfTemplates = "g.V().hasLabel(make).as('deviceMake').out(hasType).hasLabel(deviceTypeLabel).as('deviceType').out(hasModel).hasLabel(deviceModelLabel).as('deviceModel')"
			        + ".out(talksIn).hasLabel(protocolLabel).as('deviceProtocol').out(hasVersion).hasLabel(versionLabel).as('deviceProtocolVersion')"
			        + ".out(hasTemplate).hasLabel(deviceConfigTmp).as('name','isDeleted','configPoints').in('hasTemplate').hasLabel(subscription).has('subId',subId)"
			        + ".select('deviceMake','deviceType','deviceModel','deviceProtocol','deviceProtocolVersion','name','isDeleted','configPoints').by(values('name'))"
			        + ".by(values('name')).by(values('name')).by(values('name')).by(values('name')).by(values('name')).by(values('isDeleted')).by(out(hasPoint).hasLabel(configuredPt).valueMap().fold())";
		}

		params.put("make", MAKE);
		params.put("hasType", HAS_TYPE);
		params.put("deviceTypeLabel", DEVICE_TYPE);
		params.put("hasModel", HAS_MODEL);
		params.put("deviceModelLabel", MODEL);
		params.put("protocolLabel", DEVICE_PROTOCOL);
		params.put("talksIn", TALKSIN);
		params.put("hasVersion", HAS_VERSION);
		params.put("versionLabel", PROTOCOL_VERSION);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
		params.put("subscription", SUBSCRIPTION);
		params.put("subId", subId);
		params.put("configuredPt", CONFIGURED_POINT);
		params.put("hasPoint", HAS_POINT);

		Client client = titanSessionManager.getClient();
		ResultSet resultSet = client.submit(getAllConfTemplates, params);
		List<Result> resultList;
		try {
			resultList = resultSet.all().get();
			deviceConfigTemplates = getTemplatesFromResults(resultList);
		} catch (InterruptedException e) {
			throw new DeviceCloudException(e);
		} catch (ExecutionException e) {
			throw new DeviceCloudException(e);
		} catch (NoResultException e) {
			return null;
		}
		return deviceConfigTemplates;
	}

	/**
	 * Repository method to fetch device configuration template of the specified
	 * subId and templateName
	 * 
	 * @param subId
	 * @param templateName
	 * @return {@link DeviceConfigTemplate}
	 */

	@Override
	public DeviceConfigTemplate getConfTemplate(String subId,
	        String templateName) {

		DeviceConfigTemplate deviceConfigTemplate = null;
		String getConfTemplate = "g.V().hasLabel(make).as('deviceMake').out(hasType).hasLabel(deviceTypeLabel).as('deviceType').out(hasModel).hasLabel(deviceModelLabel).as('deviceModel')"
		        + ".out(talksIn).hasLabel(protocolLabel).as('deviceProtocol').out(hasVersion).hasLabel(versionLabel).as('deviceProtocolVersion')"
		        + ".out(hasTemplate).hasLabel(deviceConfigTmp).has('name',tmpName).as('name','isDeleted','configPoints').in('hasTemplate').hasLabel(subscription).has('subId',subId)"
		        + ".select('deviceMake','deviceType','deviceModel','deviceProtocol','deviceProtocolVersion','name','isDeleted','configPoints').by(values('name'))"
		        + ".by(values('name')).by(values('name')).by(values('name')).by(values('name')).by(values('name')).by(values('isDeleted')).by(out(hasPoint).hasLabel(configuredPt).valueMap().fold())";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("make", MAKE);
		params.put("hasType", HAS_TYPE);
		params.put("deviceTypeLabel", DEVICE_TYPE);
		params.put("hasModel", HAS_MODEL);
		params.put("deviceModelLabel", MODEL);
		params.put("protocolLabel", DEVICE_PROTOCOL);
		params.put("talksIn", TALKSIN);
		params.put("hasVersion", HAS_VERSION);
		params.put("versionLabel", PROTOCOL_VERSION);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
		params.put("subscription", SUBSCRIPTION);
		params.put("subId", subId);
		params.put("configuredPt", CONFIGURED_POINT);
		params.put("hasPoint", HAS_POINT);
		params.put("tmpName", templateName);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getConfTemplate, params);
			Result result = resultSet.one();
			deviceConfigTemplate = getTemplatesFromResult(result);
		} catch (NoResultException e) {
			return null;
		}
		return deviceConfigTemplate;
	}

	/**
	 * Repository method to detach all the devices from the specified
	 * confTemplates
	 * 
	 * @param confTemplates
	 */
	@Override
	public void detachAllDevice(String subId, List<String> confTemplateNames) {
		String tempNames = "tempNames = "
		        + prepareCypherArray(confTemplateNames) + ";";
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("subId", subId);
			Client client = titanSessionManager.getClient();
			client.submit(tempNames.concat(detachTempFromDevices), params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	/**
	 * Service method to fetch details of a device configuration template
	 * 
	 * @param subId
	 * @param sourceId
	 * @return
	 */
	@Override
	public DeviceConfigTemplate getDeviceConfiguration(String subId,
	        String sourceId) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("subscription", SUBSCRIPTION);
		params.put("deviceTypeLabel", DEVICE_TYPE);
		params.put("deviceModelLabel", MODEL);
		params.put("protocolLabel", DEVICE_PROTOCOL);
		params.put("versionLabel", PROTOCOL_VERSION);
		params.put("device", DEVICE);
		params.put("configuredPt", CONFIGURED_POINT);
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
		params.put("make", MAKE);

		params.put("subId", subId);
		params.put("sourceId", sourceId);

		params.put("hasType", HAS_TYPE);
		params.put("hasModel", HAS_MODEL);
		params.put("talksIn", TALKSIN);
		params.put("hasVersion", HAS_VERSION);
		params.put("isConfiguredWith", IS_CONFIGURED_WITH);
		params.put("owns", OWNS);

		DeviceConfigTemplate deviceConfigTemplate = null;

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getDeviceConfiguration, params);
			Result result = resultSet.one();
			deviceConfigTemplate = getDeviceConfig(result);
		} catch (NoResultException e) {
			return null;
		}
		return deviceConfigTemplate;

	}

	/**
	 * Repository method to assign configured points to devices
	 * 
	 * @param configTemplate
	 * @param sourceIds
	 */

	@Override
	public GeneralBatchResponse assignConfigPointToDevices(
	        DeviceConfigTemplate configTemplate, Set<String> sourceIds,
	        boolean isDump) {
		GeneralBatchResponse batchResponse = new GeneralBatchResponse();
		List<GeneralResponse> responses = new ArrayList<GeneralResponse>();
		batchResponse.setResponses(responses);
		String templateName = configTemplate.getName();
		boolean attatchTemplate = templateName == null ? false : true;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subId", configTemplate.getSubId());
		if (!attatchTemplate) {
			List<Map<String, Object>> listOfMap = getListMap(configTemplate
			        .getConfigPoints());
			params.put("pointsMap", listOfMap);
		} else {
			params.put("templateName", templateName);
		}
		Client client = titanSessionManager.getClient();
		for (String sourceId : sourceIds) {

			GeneralResponse generalRespone = new GeneralResponse();
			responses.add(generalRespone);
			generalRespone.setReference(sourceId);
			params.put("sourceId", sourceId);
			try {
				// to validate the device
				Result result = client.submit(getDevice, params).one();
				params.put("deviceId", result.getInt());

				// to delete existing conf
				client.submit(deleteConfADevice, params).one();
			} catch (Exception e) {
				generalRespone.setStatus(Status.FAILURE);
				generalRespone.setRemarks("Source Id is Invalid");
				continue;
			}

			params.put("subId", configTemplate.getSubId());

			String query = null;

			if (attatchTemplate) {
				query = assignTempToADevice;
			} else if (isDump) {
				params.put("make", MAKE);
				params.put("deviceTypeLabel", DEVICE_TYPE);
				params.put("deviceModelLabel", MODEL);
				params.put("protocolLabel", DEVICE_PROTOCOL);
				params.put("versionLabel", PROTOCOL_VERSION);
				params.put("makeName", configTemplate.getDeviceMake());
				params.put("deviceType", configTemplate.getDeviceType());
				params.put("model", configTemplate.getDeviceModel());
				params.put("deviceProtocol", configTemplate.getDeviceProtocol());
				params.put("protocolVersion",
				        configTemplate.getDeviceProtocolVersion());
				params.put("hasType", HAS_TYPE);
				params.put("hasModel", HAS_MODEL);
				params.put("talksIn", TALKSIN);
				params.put("hasVersion", HAS_VERSION);
				try {
					ResultSet resultSet = client.submit(
					        createConfPointForADevice, params);
					if (resultSet != null) {
						resultSet.all().get();
					}
				} catch (Exception e) {
					generalRespone.setStatus(FAILURE);
					generalRespone.setRemarks("Error in creating CP for ");
					continue;
				}
				query = "versionId = "
				        + searchProtoVersion.concat(createPointRelnToDeviceCp);

			} else {
				query = createConfPointForADevice;
			}
			try {
				ResultSet resultSet = client.submit(query, params);
				if (resultSet != null) {
					resultSet.all().get();
				}
			} catch (Exception e) {
				generalRespone.setStatus(Status.FAILURE);
				generalRespone
				        .setRemarks("Error in Assigning Configured Points");
				continue;
			}
			generalRespone.setStatus(Status.SUCCESS);
		}

		return batchResponse;
	}

	/**
	 * Repository method for fetching all the points under a protocol version
	 * 
	 * @param configurationSearch
	 * @return
	 */
	@Override
	public List<ConfigPoint> getPointsOfAProtocolVersion(
	        ConfigurationSearch configurationSearch) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("make", MAKE);
		params.put("hasType", HAS_TYPE);
		params.put("deviceTypeLabel", DEVICE_TYPE);
		params.put("hasModel", HAS_MODEL);
		params.put("deviceModelLabel", MODEL);
		params.put("protocolLabel", DEVICE_PROTOCOL);
		params.put("talksIn", TALKSIN);
		params.put("hasVersion", HAS_VERSION);
		params.put("versionLabel", PROTOCOL_VERSION);
		params.put("hasPoint", HAS_POINT);
		params.put("point", POINT);
		params.put("makeName", configurationSearch.getMake());
		params.put("deviceTypeName", configurationSearch.getDeviceType());
		params.put("modelName", configurationSearch.getModel());
		params.put("protocolName", configurationSearch.getProtocol());
		params.put("versionName", configurationSearch.getVersion());

		List<ConfigPoint> points = new ArrayList<ConfigPoint>();
		Client client = titanSessionManager.getClient();
		ResultSet resultSet = client.submit(getProtcolVersionPoints, params);
		List<Result> resultList;
		try {
			resultList = resultSet.all().get();
			points = getPoints(resultList);
		} catch (InterruptedException e) {
			throw new DeviceCloudException(e);
		} catch (ExecutionException e) {
			throw new DeviceCloudException(e);
		} catch (NoResultException e) {
			return null;
		}
		return points;
	}

	/**
	 * Repository method to assign configured points to devices
	 * 
	 * @param configTemplate
	 * @param sourceIds
	 * @param Subscription
	 */
	@Override
	public GeneralBatchResponse assignTemplateToDevices(String templateName,
	        Set<String> sourceIds, Subscription subscription) {
		DeviceConfigTemplate configTemplate = new DeviceConfigTemplate();
		configTemplate.setSubId(subscription.getSubId());
		configTemplate.setName(templateName);
		return assignConfigPointToDevices(configTemplate, sourceIds, true);
	}

	private String saveTemplate(DeviceConfigTemplate configTemplate) {

		configTemplate.setIsDeleted(false);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vertexLabel", DEVICE_CONFIG_TEMP);
		params.put("subscription", SUBSCRIPTION);
		params.put("deviceTypeLabel", DEVICE_TYPE);
		params.put("deviceModelLabel", MODEL);
		params.put("protocolLabel", DEVICE_PROTOCOL);
		params.put("versionLabel", PROTOCOL_VERSION);
		params.put("make", MAKE);

		params.put("subId", this.subscriptionId);
		params.put("makeName", configTemplate.getDeviceMake());
		params.put("deviceType", configTemplate.getDeviceType());
		params.put("model", configTemplate.getDeviceModel());
		params.put("deviceProtocol", configTemplate.getDeviceProtocol());
		params.put("protocolVersion", configTemplate.getDeviceProtocolVersion());

		params.put("hasType", HAS_TYPE);
		params.put("hasModel", HAS_MODEL);
		params.put("talksIn", TALKSIN);
		params.put("hasVersion", HAS_VERSION);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("isDeleted", configTemplate.getIsDeleted());

		params.put("template", configTemplate.getName());

		String uniqueId = null;

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(createTemplate, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				throw new NoResultException("Result Object is null");

			}
			uniqueId = (String)result.getObject().toString();
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

		return uniqueId;
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

		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> listOfMap = getListMap(configPoints);

		params.put("subscription", SUBSCRIPTION);
		params.put("subId", subId);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("tmpName", tempName);
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
		params.put("hasPoint", HAS_POINT);
		params.put("physicalQuantity", PHYSICAL_QUANTITY);
		params.put("unit", UNIT);
		params.put("configuredPt", CONFIGURED_POINT);
		params.put("containedIn", CONTAINED_IN);
		params.put("measuresIn", MEASURES_IN);
		params.put("configuredAs", CONFIGURED_AS);
		params.put("protocolVersion", PROTOCOL_VERSION);
		params.put("pointsMap", listOfMap);

		try {

			Client client = titanSessionManager.getClient();
			client.submit(createConfigPoint, params);
			if (isWithPointRef) {
				client.submit(createConfigPointWithRel, params);
			}

		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

	private Set<String> findExistingConfigPointDispNames(
	        DeviceConfigTemplate configTemp) {

		Map<String, Object> params = new HashMap<String, Object>();
		List<String> destList = new ArrayList<>();
		Set<String> existingPointDispNames = new HashSet<String>();

		params.put("subscription", SUBSCRIPTION);

		params.put("subId", this.subscriptionId);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("hasPoint", HAS_POINT);
		params.put("tmpName", configTemp.getName());
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getTempConfigPointDispNames,
			        params);
			try {
				destList = fromResults(resultSet.all().get(), String.class);
			} catch (NoResultException nre) {
				return new HashSet<String>();
			}

			for (int i = 0; i < destList.size(); i++) {
				existingPointDispNames.add(destList.get(i));
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new DeviceCloudException(e);
		}
		return existingPointDispNames;
	}

	private void updateExistingPoints(String subId, String configTemplate,
	        List<ConfigPoint> updateConfigList, boolean isWithPointRef) {

		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> listOfMap = getListMap(updateConfigList);

		params.put("subscription", SUBSCRIPTION);
		params.put("subId", subId);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("tmpName", configTemplate);
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
		params.put("hasPoint", HAS_POINT);
		params.put("physicalQuantity", PHYSICAL_QUANTITY);
		params.put("unit", UNIT);
		params.put("configuredPt", CONFIGURED_POINT);
		params.put("containedIn", CONTAINED_IN);
		params.put("measuresIn", MEASURES_IN);
		params.put("configuredAs", CONFIGURED_AS);
		params.put("protocolVersion", PROTOCOL_VERSION);
		params.put("point", POINT);
		params.put("pointsMap", listOfMap);

		try {

			// TODO
			// implemented as two queries. Needs to revisit the logic
			Client client = titanSessionManager.getClient();
			client.submit(updateConfigPoint, params);
			client.submit(updateConfigPointRel, params);

			if (isWithPointRef) {
				client.submit(updateConfigPointWithRel, params).one();
			}

		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

	private void updateDevicePointRel(String subId, String tempName,
	        List<ConfigPoint> newConfigList) {

		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> listOfMap = getListMap(newConfigList);
		params.put("pointsMap", listOfMap);
		params.put("subscription", SUBSCRIPTION);
		params.put("subId", subId);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("tempName", tempName);
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
		params.put("hasPoint", HAS_POINT);
		params.put("configuredPt", CONFIGURED_POINT);
		params.put("isConfiguredWith", IS_CONFIGURED_WITH);
		params.put("deviceLabel", DEVICE);

		try {

			Client client = titanSessionManager.getClient();
			client.submit(updateDeviceConfigPointRel, params);

		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	private void deleteConfigPoints(DeviceConfigTemplate configTemp,
	        Set<String> pointDispNames) {
		List<String> list = new ArrayList<String>(pointDispNames);
		String pointIdString = "'" + Joiner.on("','").join(list) + "'";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subscription", SUBSCRIPTION);
		params.put("subId", this.subscriptionId);
		params.put("hasTemplate", HAS_TEMPLATE);
		params.put("tmpName", configTemp.getName());
		params.put("deviceConfigTmp", DEVICE_CONFIG_TEMP);
		params.put("hasPoint", HAS_POINT);
		params.put("configuredPt", CONFIGURED_POINT);
		params.put("isConfiguredWith", IS_CONFIGURED_WITH);
		params.put("containedIn", CONTAINED_IN);
		params.put("measuresIn", MEASURES_IN);
		params.put("configuredAs", CONFIGURED_AS);

		try {

			Client client = titanSessionManager.getClient();
			String query = deleteConfigPoint.replace("{pointIds}",
			        pointIdString);
			client.submit(query, params);

		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

	private List<Map<String, Object>> getListMap(List<ConfigPoint> configPoints) {

		List<Map<String, Object>> listOfMap = new ArrayList<>();
		for (ConfigPoint configPoint : configPoints) {
			Map<String, Object> configPointMap = new HashMap<String, Object>();
			if (isNotEmpty(configPoint.getPointId())
			        && isNotBlank(configPoint.getPointId())) {
				configPointMap.put("pointId", configPoint.getPointId());
			}
			if (isNotEmpty(configPoint.getPointName())
			        && isNotBlank(configPoint.getPointName())) {
				configPointMap.put("pointName", configPoint.getPointName());
			}
			if (isNotEmpty(configPoint.getDisplayName())
			        && isNotBlank(configPoint.getDisplayName())) {
				configPointMap.put("displayName", configPoint.getDisplayName());
			}
			if (isNotEmpty(configPoint.getPhysicalQuantity())
			        && isNotBlank(configPoint.getPhysicalQuantity())) {
				configPointMap.put("physicalQuantity",
				        configPoint.getPhysicalQuantity());
			}
			if (isNotEmpty(configPoint.getUnit())
			        && isNotBlank(configPoint.getUnit())) {
				configPointMap.put("unit", configPoint.getUnit());
			}
			if (isNotEmpty(configPoint.getDataType().getDataType())
			        && isNotBlank(configPoint.getDataType().getDataType())) {
				configPointMap.put("type", configPoint.getDataType()
				        .getDataType());
			}
			if (isNotEmpty(configPoint.getExpression())
			        && isNotBlank(configPoint.getExpression())) {
				configPointMap.put("expression", configPoint.getExpression());
			}
			if (isNotEmpty(configPoint.getPrecedence())
			        && isNotBlank(configPoint.getPrecedence())) {
				configPointMap.put("precedence", configPoint.getPrecedence());
			}
			if (isNotEmpty(configPoint.getExtensions())) {
				List<DeviceFieldMap> extensions = configPoint.getExtensions();
				configPointMap.put("extensions", objectBuilderUtil.getGson()
				        .toJson(extensions));
			}
			if (isNotEmpty(configPoint.getAlarmExtensions())) {
				List<AlarmExtension> extensions = configPoint
				        .getAlarmExtensions();
				configPointMap.put("alarmExtensions", objectBuilderUtil
				        .getGson().toJson(extensions));
			}
			if (isNotEmpty(configPoint.getSystemTag())
			        && isNotBlank(configPoint.getSystemTag())) {
				configPointMap.put("systemTag", configPoint.getSystemTag());
			}
			listOfMap.add(configPointMap);
		}

		return listOfMap;
	}

	private List<DeviceConfigTemplate> getTemplatesFromResults(
	        List<Result> resultList) {

		List<DeviceConfigTemplate> destList = new ArrayList<DeviceConfigTemplate>();
		if (resultList == null || CollectionUtils.isEmpty(resultList)) {
			throw new NoResultException("Result Object is null or empty");
		}
		for (Result result : resultList) {
			destList.add(getTemplate(result));
		}
		return destList;

	}

	private DeviceConfigTemplate getTemplatesFromResult(Result result) {
		if (result == null || result.getObject() == null) {
			throw new NoResultException("Result Object is null");
		}
		return getTemplate(result);

	}

	@SuppressWarnings({
	        "unchecked", "rawtypes"})
	private DeviceConfigTemplate getTemplate(Result result) {
		DeviceConfigTemplate deviceConfigTemplate = new DeviceConfigTemplate();
		LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
		        .getObject();
		if (object.get("name") != null && !object.get("name").equals(""))

		{
			deviceConfigTemplate.setName(object.get("name").toString());
		}
		if (object.get("deviceMake") != null
		        && !object.get("deviceMake").equals(""))

		{
			deviceConfigTemplate.setDeviceMake(object.get("deviceMake")
			        .toString());
		}
		if (object.get("deviceType") != null
		        && !object.get("deviceType").equals(""))

		{
			deviceConfigTemplate.setDeviceType(object.get("deviceType")
			        .toString());
		}
		if (object.get("deviceModel") != null
		        && !object.get("deviceModel").equals(""))

		{
			deviceConfigTemplate.setDeviceModel(object.get("deviceModel")
			        .toString());
		}
		if (object.get("deviceProtocol") != null
		        && !object.get("deviceProtocol").equals(""))

		{
			deviceConfigTemplate.setDeviceProtocol(object.get("deviceProtocol")
			        .toString());
		}
		if (object.get("deviceProtocolVersion") != null
		        && !object.get("deviceProtocolVersion").equals(""))

		{
			deviceConfigTemplate.setDeviceProtocolVersion(object.get(
			        "deviceProtocolVersion").toString());
		}
		if (object.get("isDeleted") != null
		        && !object.get("isDeleted").equals(""))

		{
			if (object.get("isDeleted").toString().equalsIgnoreCase("true")) {
				deviceConfigTemplate.setIsDeleted(true);
			}
			if (object.get("isDeleted").toString().equalsIgnoreCase("false")) {
				deviceConfigTemplate.setIsDeleted(false);
			}

		}
		if (object.get("configPoints") != null
		        && !object.get("configPoints").equals(""))

		{
			List configValueList = (List)object.get("configPoints");
			deviceConfigTemplate
			        .setConfigPoints(getConfigPoints(configValueList));

		};
		return deviceConfigTemplate;
	}

	@SuppressWarnings({
	        "unchecked", "rawtypes"})
	private DeviceConfigTemplate getDeviceConfig(Result result) {

		if (result == null || result.getObject() == null) {
			throw new NoResultException("Result Object is null");
		}

		DeviceConfigTemplate deviceConfigTemplate = new DeviceConfigTemplate();
		LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
		        .getObject();
		if (object.get("name") != null && !object.get("name").equals(""))

		{
			if (isNotEmpty((List)object.get("name"))) {
				deviceConfigTemplate.setName(((List)object.get("name")).get(0)
				        .toString());
			}
		}
		if (object.get("deviceMake") != null
		        && !object.get("deviceMake").equals(""))

		{
			deviceConfigTemplate.setDeviceMake(object.get("deviceMake")
			        .toString());
		}
		if (object.get("deviceType") != null
		        && !object.get("deviceType").equals(""))

		{
			deviceConfigTemplate.setDeviceType(object.get("deviceType")
			        .toString());
		}
		if (object.get("deviceModel") != null
		        && !object.get("deviceModel").equals(""))

		{
			deviceConfigTemplate.setDeviceModel(object.get("deviceModel")
			        .toString());
		}
		if (object.get("deviceProtocol") != null
		        && !object.get("deviceProtocol").equals(""))

		{
			deviceConfigTemplate.setDeviceProtocol(object.get("deviceProtocol")
			        .toString());
		}
		if (object.get("deviceProtocolVersion") != null
		        && !object.get("deviceProtocolVersion").equals(""))

		{
			deviceConfigTemplate.setDeviceProtocolVersion(object.get(
			        "deviceProtocolVersion").toString());
		}
		if (object.get("isDeleted") != null
		        && !object.get("isDeleted").equals(""))

		{
			if (object.get("isDeleted").toString().equalsIgnoreCase("true")) {
				deviceConfigTemplate.setIsDeleted(true);
			}
			if (object.get("isDeleted").toString().equalsIgnoreCase("false")) {
				deviceConfigTemplate.setIsDeleted(false);
			}

		}
		if (object.get("configPoints") != null
		        && !object.get("configPoints").equals(""))

		{
			List configValueList = (List)object.get("configPoints");
			deviceConfigTemplate
			        .setConfigPoints(getConfigPoints(configValueList));

		};
		return deviceConfigTemplate;
	}

	@SuppressWarnings({
	        "rawtypes", "unchecked"})
	private List<ConfigPoint> getConfigPoints(List configValueList) {

		List<ConfigPoint> listOfMap = new ArrayList<ConfigPoint>();
		for (Object configObj : configValueList) {

			LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)configObj;
			listOfMap.add(getConfigPoint(object));
		}

		return listOfMap;

	}

	@SuppressWarnings({
		"unchecked"})
	private List<ConfigPoint> getPoints(List<Result> resultList) {
		List<ConfigPoint> configPoints = new ArrayList<ConfigPoint>();

		for (Result result : resultList) {
			LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
			        .getObject();

			configPoints.add(getConfigPoint(object));
		}
		return configPoints;

	}

	@SuppressWarnings({
	        "serial", "rawtypes"})
	private ConfigPoint getConfigPoint(LinkedHashMap<String, Object> object) {

		ConfigPoint configPoint = new ConfigPoint();
		if (object.get("pointId") != null && !object.get("pointId").equals("")) {
			configPoint.setPointId(((List)object.get("pointId")).get(0)
			        .toString());
		}
		if (object.get("pointName") != null
		        && !object.get("pointName").equals("")) {
			configPoint.setPointName(((List)object.get("pointName")).get(0)
			        .toString());
		}
		if (object.get("displayName") != null
		        && !object.get("displayName").equals("")) {
			configPoint.setDisplayName(((List)object.get("displayName")).get(0)
			        .toString());
		}
		if (object.get("parameter") != null
		        && !object.get("parameter").equals("")) {
			configPoint.setParameter(((List)object.get("parameter")).get(0)
			        .toString());
		}
		if (object.get("physicalQuantity") != null
		        && !object.get("physicalQuantity").equals("")) {
			configPoint.setPhysicalQuantity(((List)object
			        .get("physicalQuantity")).get(0).toString());
		}
		if (object.get("type") != null && !object.get("type").equals(""))
			try {
				{

					configPoint.setDataType(((List)object.get("type")).get(0)
					        .toString());
				}

			} catch (Exception e) {
				throw new DeviceCloudException(e);
			}
		if (object.get("unit") != null && !object.get("unit").equals("")) {
			configPoint.setUnit(((List)object.get("unit")).get(0).toString());
		}
		if (object.get("systemTag") != null
		        && !object.get("systemTag").equals("")) {
			configPoint.setSystemTag(((List)object.get("systemTag")).get(0)
			        .toString());
		}
		if (object.get("acquisition") != null
		        && !object.get("acquisition").equals("")) {
			configPoint.setAcquisition(((List)object.get("acquisition")).get(0)
			        .toString());
		}
		if (object.get("access") != null && !object.get("access").equals("")) {
			configPoint.setPointAccessType(((List)object.get("access")).get(0)
			        .toString());
		}
		if (object.get("precedence") != null
		        && !object.get("precedence").equals("")) {
			configPoint.setPrecedence(((List)object.get("precedence")).get(0)
			        .toString());
		}
		if (object.get("accessType") != null
		        && !object.get("accessType").equals("")) {
			configPoint.setPointAccessType(((List)object.get("accessType"))
			        .get(0).toString());
		}
		if (object.get("expression") != null
		        && !object.get("expression").equals("")) {
			configPoint.setExpression(((List)object.get("expression")).get(0)
			        .toString());
		}
		if (object.get("extensions") != null
		        && !object.get("extensions").equals("")) {
			String extensionString = ((List)object.get("extensions")).get(0)
			        .toString();

			Type type = new TypeToken<List<DeviceFieldMap>>() {
			}.getType();
			List<DeviceFieldMap> extensions = objectBuilderUtil.getGson()
			        .fromJson(extensionString, type);
			configPoint.setExtensions(extensions);
		}

		if (object.get("alarmExtensions") != null
		        && !object.get("alarmExtensions").equals("")) {
			String alarmExtensionString = ((List)object.get("alarmExtensions"))
			        .get(0).toString();
			Type type = new TypeToken<List<AlarmExtension>>() {
			}.getType();
			List<AlarmExtension> alarmExtensionList = objectBuilderUtil
			        .getGson().fromJson(alarmExtensionString, type);
			configPoint.setAlarmExtensions(alarmExtensionList);
		}
		return configPoint;
	}

}
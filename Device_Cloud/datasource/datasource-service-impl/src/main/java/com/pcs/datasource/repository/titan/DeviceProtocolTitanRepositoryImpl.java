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

import static com.pcs.datasource.repository.utils.VertexMapper.fromResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.CommandType;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.repository.DeviceProtocolRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH pcseg296
 * @date December 2015
 */
@Repository("dpTitan")
public class DeviceProtocolTitanRepositoryImpl
        implements
            DeviceProtocolRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	private static final String updateDeviceRelation = "g.V().hasLabel('DEVICE').has('sourceId',sourceId).outE('talksIn').next().remove(); "
	        + "version = g.V().hasLabel('MAKE').has('name', make).out('hasType').has('name', type).out('hasModel').has('name', model).out('talksIn').has('name', protocol).out('hasVersion').has('name', version).next();"
	        + "device = g.V().hasLabel('DEVICE').has('sourceId',sourceId).next(); device.addEdge('talksIn',version);";

	private static final String getDeviceProtocolVersion = "g.V().hasLabel('MAKE').has('name', make).out('hasType').has('name', type).out('hasModel').has('name', model).out('talksIn').has('name', protocol).out('hasVersion').has('name', version).valueMap();";

	private static final String getSupportedCommands = "g.V().hasLabel('MAKE').has('name', make).out('hasType').has('name', type).out('hasModel').has('name', model).out('talksIn').has('name', protocol).out('hasVersion').has('name', version).out('canExecute').valueMap();";

	private static final String recycleProtocol = "version = g.V().hasLabel('MAKE').has('name', make).out('hasType').has('name', type).out('hasModel').has('name', model).out('talksIn').has('name', protocol).out('hasVersion').has('name', version).next();"
	        + " recycle = graph.addVertex(label,'RECYCLE','unitId', unitId); version.addEdge('hasUnitId',recycle);";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.DeviceProtocolRepository#updateDeviceRelation
	 * (com.pcs.datasource.dto.dc.Device)
	 */
	@Override
	public void updateDeviceRelation(Device device) {

		ConfigurationSearch version = device.getVersion();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("make", version.getMake());
		params.put("type", version.getDeviceType());
		params.put("model", version.getModel());
		params.put("protocol", version.getProtocol());
		params.put("version", version.getVersion());
		params.put("sourceId", device.getSourceId());

		try {
			titanSessionManager.getClient()
			        .submit(updateDeviceRelation, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
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
		ProtocolVersion protocolVersion = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("make", version.getMake());
		params.put("type", version.getDeviceType());
		params.put("model", version.getModel());
		params.put("protocol", version.getProtocol());
		params.put("version", version.getVersion());

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getDeviceProtocolVersion, params);
			if (resultSet != null) {
				Result result = resultSet.one();
				if (result != null) {
					protocolVersion = fromResult(result, ProtocolVersion.class);
				}
			}
		} catch (NoResultException nre) {
			return protocolVersion;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return protocolVersion;
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.datasource.repository.DeviceProtocolRepository#
	 * getCommandsOfDeviceProtocol(java.lang.String, java.lang.String)
	 */
	@Override
	public List<CommandType> getCommandsOfDeviceProtocol(
	        ConfigurationSearch version) {
		List<CommandType> deviceCommands = null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("make", version.getMake());
		params.put("type", version.getDeviceType());
		params.put("model", version.getModel());
		params.put("protocol", version.getProtocol());
		params.put("version", version.getVersion());

		try {

			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getSupportedCommands, params);
			if (resultSet == null) {
				return deviceCommands;
			}
			deviceCommands = getCommandTypes(resultSet.all().get());
		} catch (NoResultException nre) {
			return deviceCommands;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
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
		ConfigurationSearch version = device.getVersion();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("make", version.getMake());
		params.put("type", version.getDeviceType());
		params.put("model", version.getModel());
		params.put("protocol", version.getProtocol());
		params.put("version", version.getVersion());
		params.put("unitId", device.getUnitId());

		try {
			titanSessionManager.getClient().submit(recycleProtocol, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@SuppressWarnings({
	        "rawtypes", "unchecked"})
	private List<CommandType> getCommandTypes(List<Result> resultList) {

		if (resultList == null || CollectionUtils.isEmpty(resultList)) {
			throw new NoResultException("Result Object is null or empty");
		}
		List<CommandType> commandTypes = new ArrayList<CommandType>();
		for (Result result : resultList) {
			LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
			        .getObject();

			CommandType commandType = new CommandType();
			if (object.get("name") != null && !object.get("name").equals("")) {
				commandType.setName(((List)object.get("name")).get(0)
				        .toString());
			}
			if (object.get("systemCommand") != null
			        && !object.get("systemCommand").equals("")) {
				if (((List)object.get("systemCommand")).get(0).toString()
				        .equalsIgnoreCase("true")) {
					commandType.setSystemCmd(true);
				}
				if (((List)object.get("systemCommand")).get(0).toString()
				        .equalsIgnoreCase("false")) {
					commandType.setSystemCmd(false);
				}
			}
			commandTypes.add(commandType);

		}
		return commandTypes;
	}

}

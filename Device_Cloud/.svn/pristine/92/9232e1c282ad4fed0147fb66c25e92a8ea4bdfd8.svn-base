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

import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE_PROTOCOL;
import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE_TYPE;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_MODEL;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_POINT;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_TYPE;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_VERSION;
import static com.pcs.datasource.repository.utils.GremlinConstants.MAKE;
import static com.pcs.datasource.repository.utils.GremlinConstants.MODEL;
import static com.pcs.datasource.repository.utils.GremlinConstants.POINT;
import static com.pcs.datasource.repository.utils.GremlinConstants.PROTOCOL_VERSION;
import static com.pcs.datasource.repository.utils.GremlinConstants.TALKSIN;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.repository.PointRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for all repo services related to point (raw point)
 * 
 * @author pcseg323(Greeshma)
 * @date Dec 2015
 */
@Repository("pointTitan")
public class PointTitanRepositoryImpl implements PointRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	private static final String searchProtoVersion = "g.V().hasLabel(vertexLabel).has('name',make)"
	        + ".out(hasType).hasLabel(deviceTypeLabel).has('name',deviceType)"
	        + ".out(hasModel).hasLabel(deviceModelLabel).has('name',deviceModel)"
	        + ".out(talksIn).hasLabel(protocolLabel).has('name',deviceProtocol)"
	        + ".out(hasVersion).hasLabel(versionLabel).has('name',protoVersion)"
	        + ".out(hasPoint).hasLabel(pointLabel).count().as('pointCount')";

	@Override
	public boolean doExistPointsInProtocol(ConfigurationSearch configSearch) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vertexLabel", MAKE);
		params.put("deviceTypeLabel", DEVICE_TYPE);
		params.put("deviceModelLabel", MODEL);
		params.put("protocolLabel", DEVICE_PROTOCOL);
		params.put("versionLabel", PROTOCOL_VERSION);
		params.put("pointLabel", POINT);

		params.put("make", configSearch.getMake());
		params.put("deviceType", configSearch.getDeviceType());
		params.put("deviceModel", configSearch.getModel());
		params.put("deviceProtocol", configSearch.getProtocol());
		params.put("protoVersion", configSearch.getVersion());

		params.put("hasType", HAS_TYPE);
		params.put("hasModel", HAS_MODEL);
		params.put("talksIn", TALKSIN);
		params.put("hasVersion", HAS_VERSION);
		params.put("hasPoint", HAS_POINT);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(searchProtoVersion, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return false;
			}
			if ((Integer)result.getObject() > 0) {
				return true;
			}
		} catch (NoResultException nre) {
			return false;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return false;

	}

	public static void main(String[] args) {

		PointTitanRepositoryImpl pointTitanRepositoryImpl = new PointTitanRepositoryImpl();
		pointTitanRepositoryImpl.titanSessionManager = new TitanSessionManager();

		pointTitanRepositoryImpl.titanSessionManager.gremlinAddress = "10.234.31.163";
		pointTitanRepositoryImpl.titanSessionManager.gremlinPort = "8182";

		ConfigurationSearch configurationSearch = new ConfigurationSearch();
		configurationSearch.setMake("Meitrack Group6");
		configurationSearch.setDeviceType("Telematics6");
		configurationSearch.setModel("FMS6");
		configurationSearch.setProtocol("EDCP6");
		configurationSearch.setVersion("1.6.0");

		pointTitanRepositoryImpl.doExistPointsInProtocol(configurationSearch);
	}
}

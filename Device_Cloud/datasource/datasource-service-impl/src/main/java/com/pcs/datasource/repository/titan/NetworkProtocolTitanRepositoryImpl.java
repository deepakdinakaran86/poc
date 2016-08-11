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

import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE;
import static com.pcs.datasource.repository.utils.GremlinConstants.NW_PROTOCOL;
import static com.pcs.datasource.repository.utils.GremlinConstants.TRANSPORTS_IN;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResult;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.repository.NetworkProtocolRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg323 (Greeshma)
 * @date Dec 12 2015
 * @since galaxy-1.0.0
 */
@Repository("nwProtocolTitan")
public class NetworkProtocolTitanRepositoryImpl
        implements
            NetworkProtocolRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	private static final String updateDevice = "g.V().hasLabel(vertexLabel).has('sourceId', sourceId).outE(transportsIn).next().remove(); device = g.V().hasLabel(vertexLabel).has('sourceId', sourceId).next(); npro = g.V().hasLabel(nwProtocol).has('name',nwProtocolName).next(); device.addEdge(transportsIn, npro);";

	private static final String getNwProtocol = "g.V().hasLabel(vertexLabel).has('name',nwProtocolName).valueMap();";

	/**
	 * method to update network protocol
	 * 
	 * @param Device
	 *
	 */

	@Override
	public void updateDeviceNwProtocol(Device device) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("sourceId", device.getSourceId());

		params.put("vertexLabel", DEVICE);
		params.put("nwProtocol", NW_PROTOCOL);
		params.put("nwProtocolName", device.getNetworkProtocol().getName());

		params.put("transportsIn", TRANSPORTS_IN);

		try {
			Client client = titanSessionManager.getClient();
			client.submit(updateDevice, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

	/**
	 * method to update network protocol
	 * 
	 * @param name
	 * 
	 * @return {@link NetworkProtocol}
	 *
	 */
	@Override
	public NetworkProtocol getNwProtocol(String name) {

		Map<String, Object> params = new HashMap<String, Object>();
		NetworkProtocol networkProtocol = null;

		params.put("vertexLabel", NW_PROTOCOL);
		params.put("nwProtocolName", name);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getNwProtocol, params);
			Result result = resultSet.one();
			networkProtocol = fromResult(result, NetworkProtocol.class);
		} catch (NoResultException nre) {
			return networkProtocol;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return networkProtocol;

	}

	public static void main(String[] args) {

		Device device = new Device();
		NetworkProtocol networkProtocol = new NetworkProtocol();
		NetworkProtocolTitanRepositoryImpl protocoTitanlRepositoryImpl = new NetworkProtocolTitanRepositoryImpl();
		device.setSourceId("device_01");
		networkProtocol.setName("TCP");
		device.setNetworkProtocol(networkProtocol);
		// protocoTitanlRepositoryImpl.updateDeviceNwProtocol(device);
		protocoTitanlRepositoryImpl.getNwProtocol("TCP");
	}
}

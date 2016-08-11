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
import static com.pcs.datasource.repository.utils.CypherConstants.NAME;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_ID;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;

import javax.persistence.PersistenceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.repository.NetworkProtocolRepository;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Repository("nwProtocolNeo")
public class NetworkProtocolRepositoryImpl implements NetworkProtocolRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String updateDevice = "MATCH (device:DEVICE {source_id:'{source_id}'}),(nwprotocol:NW_PROTOCOL {name:'{name}'}),"
	        + "device-[ex:transportsIn]->nwprotocol2 DELETE ex CREATE device-[r:transportsIn]->nwprotocol return device;";

	private static final String getNwProtocol = "MATCH (nwprotocol:NW_PROTOCOL {name:'{name}'}) return nwprotocol;";

	/*
	 * (non-Javadoc)
	 * @see com.pcs.datasource.repository.NetworkProtocolRepository#
	 * updateDeviceNwProtocol(com.pcs.datasource.dto.dc.Device)
	 */
	@Override
	public void updateDeviceNwProtocol(Device device) {
		String query = updateDevice.replace(SOURCE_ID, device.getSourceId())
		        .replace(NAME, device.getNetworkProtocol().getName());
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in Updating NwProtocol", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.NetworkProtocolRepository#getNwProtocol
	 * (java.lang.String)
	 */
	@Override
	public NetworkProtocol getNwProtocol(String name) {
		String query = getNwProtocol.replace(NAME, name);

		JSONArray resultJson = null;
		try {
			resultJson = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in Fetching NwProtocol", e);
		}
		if (resultJson == null || resultJson.length() < 1) {
			return null;
		}
		return convertNwProtocol(resultJson.getJSONObject(0));
	}

	private NetworkProtocol convertNwProtocol(JSONObject deviceJson) {
		NetworkProtocol networkProtocol = objectBuilderUtil
		        .getLowerCaseUnderScoreGson().fromJson(
		                deviceJson.getJSONArray(ROW.getFieldName())
		                        .getJSONObject(0).toString(),
		                NetworkProtocol.class);
		return networkProtocol;
	}

	/*
	 * public static void main(String[] args) { NetworkProtocolRepositoryImpl
	 * networkProtocolRepositoryImpl = new NetworkProtocolRepositoryImpl();
	 * networkProtocolRepositoryImpl.neo4jURI = "http://10.234.31.233:7475";
	 * networkProtocolRepositoryImpl.objectBuilderUtil = new
	 * ObjectBuilderUtil(); List<NetworkProtocol> allNwProtocols =
	 * networkProtocolRepositoryImpl .getAllNwProtocols(); NetworkProtocol
	 * networkProtocol = allNwProtocols.get(0);
	 * System.out.println(networkProtocol); }
	 */
}

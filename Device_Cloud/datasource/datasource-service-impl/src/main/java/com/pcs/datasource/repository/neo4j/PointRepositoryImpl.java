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

import java.io.IOException;

import javax.persistence.PersistenceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.repository.PointRepository;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for all repo services related to point (raw point)
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 29 Sep 2015
 */
@Repository("pointNeo")
public class PointRepositoryImpl implements PointRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String searchProtoVersion = "MATCH (mk:MAKE {name:'{device_make}'})"
	        + "-[:hasType]->(t:DEVICE_TYPE {name:'{device_type}'})"
	        + "-[:hasModel]->(md:MODEL {name:'{device_model}'})"
	        + "-[:talksIn]->(p:DEVICE_PROTOCOL {name:'{device_protocol}'})"
	        + "-[:hasVersion]->(v:PROTOCOL_VERSION {name:'{proto_version}'})"
	        + "-[r:hasPoint]->(pt:POINT) return count(pt) as pointcount;";

	@Override
	public boolean doExistPointsInProtocol(ConfigurationSearch configSearch) {
		String query = searchProtoVersion
		        .replace("{device_make}", configSearch.getMake())
		        .replace("{device_type}", configSearch.getDeviceType())
		        .replace("{device_model}", configSearch.getModel())
		        .replace("{device_protocol}", configSearch.getProtocol())
		        .replace("{proto_version}", configSearch.getVersion());
		JSONArray checkPtResult = null;
		try {
			checkPtResult = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in checking existence of points in a protocol version",
			        e);
		}
		if (checkPtResult == null) {
			return false;
		}
		return isExistPOint(checkPtResult.getJSONObject(0));
	}

	private boolean isExistPOint(JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.getFieldName());
		if (!jsonArray.isNull(0)) {
			if (jsonArray.optInt(0) > 0) {
				return true;
			}
		}
		return false;
	}

}

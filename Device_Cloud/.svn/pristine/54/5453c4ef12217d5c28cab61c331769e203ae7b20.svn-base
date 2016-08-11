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
import static com.pcs.datasource.repository.utils.CypherConstants.DATA_TYPE;
import static com.pcs.datasource.repository.utils.CypherConstants.PARAMETER;
import static com.pcs.datasource.repository.utils.CypherConstants.PHYSICAL_QUANTITY;
import static com.pcs.datasource.repository.utils.CypherConstants.SUB_ID;
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
import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.repository.ParameterRepository;
import com.pcs.datasource.repository.utils.CypherConstants;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */

@Repository("paramNeo")
public class ParameterRepositoryImpl implements ParameterRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String createParameter = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}), (pq:PHYSICAL_QUANTITY {name:'{physical_quantity}'}),"
	        + " (dt:DATA_TYPE {name:'{data_type}'}) CREATE (parameter:PARAMETER {name:'{parameter}'}), "
	        + "sub-[:hasParameter]->parameter, parameter-[:containedIn]->pq, parameter-[:isOfType]->dt return parameter;";

	private static final String getParameters = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}),sub-[:hasParameter]->parameter, parameter-[:containedIn]->pq,"
	        + "parameter-[:isOfType]->dt return collect({name:parameter.name,dataType:dt.name,physicalQuantity:pq.name}) as parameters;";

	private static final String getParameter = "MATCH (parameter:PARAMETER {name:'{parameter}'}), (sub:SUBSCRIPTION {sub_id:'{sub_id}'}), "
	        + "sub-[:hasParameter]->parameter, parameter-[:containedIn]->pq, parameter-[:isOfType]->dt return parameter,pq,dt;";

	private static final String getDataType = "MATCH (data_type:DATA_TYPE {name:'{data_type}'}) RETURN data_type;";

	/**
	 * method to create a parameter to the system under a subscription
	 * 
	 * @param parameterDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public void saveParameter(ParameterDTO parameterDTO) {

		String query = createParameter
		        .replace(CypherConstants.SUB_ID,
		                parameterDTO.getSubscription().getSubId() + "")
		        .replace(PHYSICAL_QUANTITY, parameterDTO.getPhysicalQuantity())
		        .replace(DATA_TYPE, parameterDTO.getDataType())
		        .replace(PARAMETER, parameterDTO.getName());
		JSONArray insertDeviceResult = null;
		try {
			insertDeviceResult = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in creating a prameter", e);
		}

		if (insertDeviceResult == null) {
			throw new PersistenceException(
			        "Error in creating prameter,Response is null");
		}
	}

	/**
	 * method to get all parameters of a subscription
	 * 
	 * @param subId
	 * @return List<ParameterDTO>
	 */
	@Override
	public List<ParameterDTO> getParameters(String subId) {
		List<ParameterDTO> parameters = new ArrayList<ParameterDTO>();
		String query = getParameters.replace(SUB_ID, subId);

		JSONArray paramJsonArray = null;
		try {
			paramJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching all parameter of a Subscription", e);
		}
		if (paramJsonArray == null) {
			return Collections.emptyList();
		}
		JSONArray parameterJsonArray = paramJsonArray.getJSONObject(0)
		        .getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson();
		JSONArray jsonArray = parameterJsonArray.getJSONArray(0);
		if (jsonArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < jsonArray.length(); i++) {

			ParameterDTO parameter = lowerCaseUnderScoreGson.fromJson(jsonArray
			        .getJSONObject(i).toString(), ParameterDTO.class);
			parameters.add(parameter);
		}
		return parameters;
	}

	/**
	 * method is to get parameter from db for a subscription
	 * 
	 * @param paramName
	 * @param subId
	 * @return ParameterDTO
	 */
	@Override
	public ParameterDTO getParameter(String paramName, String subId) {
		String query = getParameter.replace(PARAMETER, paramName);
		query = query.replace(SUB_ID, subId);
		JSONArray paramJsonArray = null;
		try {
			paramJsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
			        ROW.toString());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching parameter details", e);
		}
		if (paramJsonArray == null) {
			return null;
		}
		ParameterDTO parameter = convertToParameter(paramJsonArray
		        .getJSONObject(0));
		return parameter;
	}

	@Override
	public boolean isDataTypeExist(String dataType) {
		String query = getDataType.replace(DATA_TYPE, dataType);
		JSONArray dataTypeJsonArray = null;
		try {
			dataTypeJsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query,
			        null, ROW.toString());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching parameter details", e);
		}
		if (dataTypeJsonArray == null) {
			return false;
		}
		return true;
	}

	private ParameterDTO convertToParameter(JSONObject parameterJson) {
		JSONArray parameterJsonArray = parameterJson.getJSONArray(ROW
		        .getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson();
		ParameterDTO parameter = lowerCaseUnderScoreGson.fromJson(
		        parameterJsonArray.getJSONObject(0).toString(),
		        ParameterDTO.class);
		return parameter;
	}
}

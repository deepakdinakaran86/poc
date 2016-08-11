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
package com.pcs.guava.serviceImpl.repository.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.persistence.NoResultException;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Neo4jUtils
 * 
 * @description Utility Class for HierarchyService
 * @author Daniela (PCSEG191)
 * @author Riyas (PCSEG296)
 * @date 18 Oct 2015
 * @since alpine-1.0.0
 */

@Service
public class Neo4jExecuter {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(Neo4jExecuter.class);

	// @Value("${neo4j.rest.2.1.4.syntax}")
	private static final String NEO4J_REST_SYNTAX = "{\"statements\" : 	[ { \"statement\" : \"{cypher}\",\"resultDataContents\" : [ \"{resultSet}\" ],\"parameters\" : {\"props\" : {parameters} } }  ] }";

	public static JSONArray exexcuteQuery(String neo4jURI, String query,
	        String parametersJson, String resultSet) throws IOException,
	        JSONException {
		String jsonResponse = "";
		HttpClient client = new DefaultHttpClient();

		HttpPost request = new HttpPost(neo4jURI
		        + "/db/data/transaction/commit");

		String payload = null;

		payload = prepareCypher(
		        NEO4J_REST_SYNTAX.replace("{resultSet}", resultSet), query,
		        parametersJson);

		StringEntity entity = new StringEntity(payload);
		request.addHeader("content-type",
		        MediaType.APPLICATION_JSON_TYPE.toString());
		request.setEntity(entity);

		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
		        .getEntity().getContent()));

		String line = "";
		while ((line = rd.readLine()) != null) {
			jsonResponse += line;
		}
		LOGGER.debug(jsonResponse);

		JSONObject resultJson = new org.json.JSONObject(jsonResponse);

		JSONArray resultsArray = resultJson.getJSONArray("results");
		JSONArray errorsArray = resultJson.getJSONArray("errors");
		LOGGER.debug("results :" + resultsArray.length());
		JSONObject resultJsonObject = null;
		LOGGER.debug("errors :" + resultJson.getJSONArray("errors").length());

		if (resultsArray.length() > 0) {

			LOGGER.debug("Has Results");

			resultJsonObject = resultsArray.getJSONObject(0);

			if (resultJsonObject.getJSONArray("data").length() <= 0) {
				LOGGER.debug("No Data");
				return null;
			}
			LOGGER.debug("Data Available");
		} else if (errorsArray.length() > 0) {
			throw new NoResultException("Error in Neo4j CYPHER Invocation :"
			        + errorsArray.getJSONObject(0));
		} else {
			throw new NoResultException(
			        "Error in Neo4j Service Invocation Payload :" + payload);
		}
		return resultJsonObject.getJSONArray("data");
	}

	private static String prepareCypher(String restPayload, String query,
	        String parametersJson) {
		String payload;
		if (parametersJson != null) {

			payload = restPayload.replace("{cypher}", query).replace(
			        "{parameters}", parametersJson);
		} else {
			payload = restPayload.replace("{cypher}", query).replace(
			        "{parameters}", "\"not available\"");
		}
		return payload;
	}

}
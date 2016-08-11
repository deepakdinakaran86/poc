package com.pcs.cache.neo4j.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
import org.yaml.snakeyaml.Yaml;

import com.hazelcast.query.QueryException;

/**
 * Neo4jUtils
 * 
 * @description Utility Class for HierarchyService
 * @author Javid Ahammed (PCSEG199)
 * @date 25 August 2014
 * @since galaxy-1.0.0
 */

public class Neo4jExecuter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Neo4jExecuter.class);

	// @Value("${neo4j.rest.2.1.4.syntax}")
	private static final String NEO4J_REST_SYNTAX = "{\"statements\" : 	[ { \"statement\" : \"{cypher}\",\"resultDataContents\" : [ \"{resultSet}\" ],\"parameters\" : {\"props\" : {parameters} } }  ] }";

	private static final String filePath = System.getProperty("user.dir")
			+ File.separator + "config.yaml";

	private static String neo4jURI;

	public static JSONArray exexcuteQuery(String query, String parametersJson,
			String resultSet) throws IOException, JSONException {
		String jsonResponse = "";
		HttpClient client = new DefaultHttpClient();

		if (neo4jURI == null) {
			neo4jURI = getNeo4jURL();
		}

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
			throw new QueryException("Error in Neo4j CYPHER Invocation :"
					+ errorsArray.getJSONObject(0));
		} else {
			throw new QueryException(
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

	/**
    *
    */
	public static String getNeo4jURL() {

		if (neo4jURI == null) {
			try {
				Yaml yaml = new Yaml();
				@SuppressWarnings("unchecked")
				Map<String, Object> load = (HashMap<String, Object>) yaml
						.load(new FileInputStream(filePath));
				neo4jURI = load.get("neo4j.url").toString();
			} catch (Exception e) {
				throw new RuntimeException("Neo4j configuration not found", e);
			}

		}
		return neo4jURI;

	}

}
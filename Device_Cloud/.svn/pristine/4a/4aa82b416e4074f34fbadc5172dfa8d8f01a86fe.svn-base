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
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.utils.UUIDs;
import com.google.gson.Gson;
import com.pcs.datasource.dto.DatasourceDTO;
import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.model.DatasourceRegistration;
import com.pcs.datasource.model.SubscriptionContext;
import com.pcs.datasource.repository.DatasourceRepository;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 26 May 2015
 */

@Repository("dsNeo")
public class DatasourceRepositoryImpl implements DatasourceRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	public static final String registerDatasource = "CREATE (ds:DATA_SOURCE {props}) return ds;";

	public static final String registerParameter = "MATCH (ds:DATA_SOURCE {datasource_name:'{ds_name}'}) "
	        + "CREATE (params:DATA_SOURCE_PARAMETERS {props})  "
	        + "CREATE ds-[:hasParameter]->params return ds;";

	private static final String getAllDatasource = "MATCH (ds:DATA_SOURCE {is_active:true}) return ds;";

	private static final String deleteDsRelations = "MATCH (ds:DATA_SOURCE {datasource_name:'{ds_name}',is_active:true}) "
	        + "OPTIONAL MATCH (ds)-[r:hasParameter]->(x) delete r,x;";

	private static final String getAllParameters = "MATCH (ds:DATA_SOURCE {datasource_name:'{ds_name}',is_active:true}) "
	        + "MATCH (ds)-[r:hasParameter]->(x) return x;";

	private static final String getDatasource = "MATCH (ds:DATA_SOURCE {is_active:true}) where "
	        + "LOWER(ds.datasource_name)='{ds_name}' return ds;";
	// "MATCH (ds:DATA_SOURCE {datasource_name:'{ds_name}',is_active:true}) return ds;";

	private static final String updateDatasource = "MATCH (ds:DATA_SOURCE {datasource_name:'{ds_name}',is_active:true}) "
	        + "SET ds.{propertyName}={propertyValue} return ds;";

	private static final String createContext = "CREATE (ctx:SUBSCRIPTION_CONTEXT {props}) return ctx;";

	private static final String createContextRelations = "MATCH (ctx:SUBSCRIPTION_CONTEXT {context_name:'{ctx_name}'}) "
	        + "MATCH (ds:DATA_SOURCE {datasource_name:'{ds_name}'}) "
	        + "CREATE ds-[:subscribedTo]->ctx return ctx;";

	private static final String getContext = "MATCH (ctx:SUBSCRIPTION_CONTEXT)<-[r:subscribedTo]-(ds:DATA_SOURCE) "
	        + "WITH ctx,ds ORDER by LOWER(ds.datasource_name) WITH ctx,collect(LOWER(ds.datasource_name)) as ff "
	        + "where ff=[{dsNames}] return ctx;";

	private static final String getContexts = "MATCH (ctx:SUBSCRIPTION_CONTEXT)<-[r:subscribedTo]-(ds:DATA_SOURCE) "
	        + "where ds.datasource_name='{ds_name}' return ctx;";

	private static final String deleteDsCtxRelations = "MATCH (ds:DATA_SOURCE {datasource_name:'{ds_name}',is_active:true}) "
	        + "OPTIONAL MATCH (ds)-[r:subscribedTo]->(x) delete r;";
	
	@Override
	public void saveDatasource(DatasourceRegistration datasourceRegistration,
	        List<ParameterDTO> datasourceParameters) {
		saveDatasource(datasourceRegistration);
		saveParameters(datasourceRegistration.getDatasourceName(),
		        datasourceParameters);
	}

	private void saveDatasource(DatasourceRegistration datasourceRegistration) {
		datasourceRegistration.setIsActive(true);

		JSONArray regDsResult = null;
		String paramsJson = objectBuilderUtil.getLowerCaseUnderScoreGson1()
		        .toJson(datasourceRegistration);
		try {
			regDsResult = exexcuteQuery(neo4jURI, registerDatasource,
			        paramsJson, "row");
		} catch (JSONException | IOException e) {
			new PersistenceException("Error in registering datasource", e);
		}
		if (regDsResult == null) {
			new PersistenceException(
			        "Error in registering datasource,Response is null");
		}
	}

	private void saveParameters(String dsName,
	        List<ParameterDTO> datasourceParameters) {
		if (CollectionUtils.isNotEmpty(datasourceParameters)) {
			for (ParameterDTO datasourceParameter : datasourceParameters) {
				JSONArray saveParamResult = null;
				String paramsJson = objectBuilderUtil
				        .getLowerCaseUnderScoreGson1().toJson(
				                datasourceParameter);
				String query = registerParameter.replace("{ds_name}", dsName);
				try {
					saveParamResult = exexcuteQuery(neo4jURI, query,
					        paramsJson, "row");
				} catch (JSONException | IOException e) {
					new PersistenceException("Error in saving parameter", e);
				}
				if (saveParamResult == null) {
					new PersistenceException(
					        "Error in saving parameter,Response is null");
				}
			}
		}
	}

	@Override
	public List<ParameterDTO> getParameters(String datasourceName) {
		List<ParameterDTO> datasourceParameters = new ArrayList<ParameterDTO>();
		String query = getAllParameters.replace("{ds_name}", datasourceName);
		JSONArray getAllParamsArray = null;
		try {
			getAllParamsArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in Updating device", e);
		}
		if (getAllParamsArray == null) {
			return Collections.emptyList();
		}
		for (int i = 0; i < getAllParamsArray.length(); i++) {
			ParameterDTO parameter = convertToDsParams(getAllParamsArray
			        .getJSONObject(i));
			datasourceParameters.add(parameter);
		}
		return datasourceParameters;
	}

	@Override
	public void updateDatasource(DatasourceDTO datasourceDTO) {
		try {
			removeDsRelations(datasourceDTO.getDatasourceName());
			saveParameters(datasourceDTO.getDatasourceName(),
			        datasourceDTO.getParameters());
		} catch (Exception e) {
			throw new PersistenceException("Error in Updating device", e);
		}
	}

	private void removeDsRelations(String datasourceName) {
		String query = deleteDsRelations.replace("{ds_name}", datasourceName);
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in deleting datasource parameter relations", e);
		}
	}

	private void removeDsCtxRelations(String datasourceName) {
		String query = deleteDsCtxRelations
		        .replace("{ds_name}", datasourceName);
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in deleting datasource context relations", e);
		}
	}

	@Override
	public List<DatasourceDTO> getAllDatasource() {
		JSONArray dsJsonArray = null;
		try {
			dsJsonArray = exexcuteQuery(neo4jURI, getAllDatasource, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching all datasources", e);
		}
		if (dsJsonArray == null) {
			return Collections.emptyList();
		}
		List<DatasourceDTO> datasourceDTOs = new ArrayList<DatasourceDTO>();
		for (int i = 0; i < dsJsonArray.length(); i++) {
			DatasourceDTO datasourceDTO = convertToDatasource(dsJsonArray
			        .getJSONObject(i));
			datasourceDTOs.add(datasourceDTO);
		}
		return datasourceDTOs;
	}

	private DatasourceDTO convertToDatasource(JSONObject dsJson) {
		JSONArray dsJsonArray = dsJson.getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson1();
		DatasourceDTO datasource = new DatasourceDTO();
		if (!dsJsonArray.isNull(0)) {
			datasource = lowerCaseUnderScoreGson.fromJson(dsJsonArray
			        .getJSONObject(0).toString(), DatasourceDTO.class);
		}

		return datasource;
	}

	private ParameterDTO convertToDsParams(JSONObject dsJson) {
		JSONArray dsJsonArray = dsJson.getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson1();
		ParameterDTO datasource = new ParameterDTO();
		if (!dsJsonArray.isNull(0)) {
			datasource = lowerCaseUnderScoreGson.fromJson(dsJsonArray
			        .getJSONObject(0).toString(), ParameterDTO.class);
		}
		return datasource;
	}

	@Override
	public boolean isDatasourceExist(String datasourceName) {
		datasourceName = datasourceName.toLowerCase();
		String query = getDatasource.replace("{ds_name}", datasourceName);
		JSONArray dsJsonArray = null;
		try {
			dsJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching all datasources", e);
		}
		if (dsJsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public void deregisterDatasource(String datasourceName) {
		removeDsRelations(datasourceName);
		removeDsCtxRelations(datasourceName);
		String query = updateDatasource.replace("{ds_name}", datasourceName)
		        .replace("{propertyName}", "is_active")
		        .replace("{propertyValue}", "'" + false + "'");
		try {
			JSONArray updatedDatasource = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());

			if (updatedDatasource == null || updatedDatasource.length() < 1) {
				throw new PersistenceException(
				        "Error in deregistering datasource");
			}
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in deregistering datasource",
			        e);
		}
	}

	@Override
	public String createContext(SortedSet<String> datasourceNames) {
		String contextName = getContext(datasourceNames);
		if (StringUtils.isNotBlank(contextName)) {
			return contextName;
		}
		UUID timeBased = UUIDs.timeBased();
		saveContext(timeBased.toString());
		createCtxRelations(timeBased.toString(), datasourceNames);
		return timeBased.toString();
	}

	private String getContext(SortedSet<String> datasourceNames) {
		String joinedDsNames = StringUtils.join(datasourceNames, "','");
		joinedDsNames = "'" + joinedDsNames + "'";
		joinedDsNames = joinedDsNames.toLowerCase();
		String query = getContext.replace("{dsNames}", joinedDsNames);
		JSONArray dsJsonArray = null;
		try {
			dsJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in getting subscription context", e);
		}
		if (dsJsonArray == null) {
			return null;
		}

		SubscriptionContext ctx = convertToCtx(dsJsonArray.getJSONObject(0));
		if (ctx != null) {
			return ctx.getContextName();
		}
		return null;
	}

	private SubscriptionContext convertToCtx(JSONObject ctxJson) {
		JSONArray ctxJsonArray = ctxJson.getJSONArray(ROW.getFieldName());
		Gson lowerCaseUnderScoreGson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson1();
		SubscriptionContext context = lowerCaseUnderScoreGson.fromJson(
		        ctxJsonArray.getJSONObject(0).toString(),
		        SubscriptionContext.class);
		return context;
	}

	private void saveContext(String ctxName) {
		SubscriptionContext context = new SubscriptionContext();
		context.setContextName(ctxName);
		JSONArray saveCtxResult = null;
		String paramsJson = objectBuilderUtil.getLowerCaseUnderScoreGson1()
		        .toJson(context);
		try {
			saveCtxResult = exexcuteQuery(neo4jURI, createContext, paramsJson,
			        "row");
		} catch (JSONException | IOException e) {
			new PersistenceException("Error in creating subscription context",
			        e);
		}
		if (saveCtxResult == null) {
			new PersistenceException(
			        "Error in creating subscription context,Response is null");
		}
	}

	private void createCtxRelations(String contextName,
	        SortedSet<String> datasourceNames) {
		for (String datasourceName : datasourceNames) {
			JSONArray saveCtxRelation = null;
			String query = createContextRelations.replace("{ctx_name}",
			        contextName).replace("{ds_name}", datasourceName);
			try {
				saveCtxRelation = exexcuteQuery(neo4jURI, query, null, "row");
			} catch (JSONException | IOException e) {
				new PersistenceException(
				        "Error in linking datasource with subscription context",
				        e);
			}
			if (saveCtxRelation == null) {
				new PersistenceException(
				        "Error in datasource with subscription context,Response is null");
			}
		}
	}

	@Override
	public List<String> getSubscribedContexts(String datasourceName) {
		List<String> contextNames = new ArrayList<String>();
		String query = getContexts.replace("{ds_name}", datasourceName);
		JSONArray dsJsonArray = null;
		try {
			dsJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching all datasources", e);
		}
		if (dsJsonArray == null) {
			return Collections.emptyList();
		}

		for (int i = 0; i < dsJsonArray.length(); i++) {
			SubscriptionContext ctx = convertToCtx(dsJsonArray.getJSONObject(i));
			contextNames.add(ctx.getContextName());
		}
		return contextNames;
	}

	public static void main(String[] args) {
		SubscriptionContext context = new SubscriptionContext();
		context.setContextName("MyContext");
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();
		String paramsJsonLower = objectBuilderUtil
		        .getLowerCaseUnderScoreGson1().toJson(context);
		String paramsJsonUpper = objectBuilderUtil.getLowerCaseUnderScoreGson()
		        .toJson(context);
		System.out.println("with lower case under score " + paramsJsonLower);
		System.out.println("with upper case camel " + paramsJsonUpper);
	}

}

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
import static com.pcs.datasource.enums.PhyQuantityFields.ID;
import static com.pcs.datasource.enums.PhyQuantityFields.UNIT;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;
import static com.pcs.devicecloud.enums.Status.ACTIVE;
import static com.pcs.devicecloud.enums.Status.DELETED;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcs.datasource.dto.DataType;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.repository.PhyQuantityRepo;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.devicecloud.commons.util.ExcludeFields;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;
import com.pcs.devicecloud.enums.Status;

/**
 * This service interface is responsible for defining all the services related
 * to datastore in which stores device data and also manages cache for the same.
 * This class is responsible for retrieving columnfamily details of particular
 * physical quantity from the device data. If this mapping information already
 * present cache then retrieve from cache otherwise access DB for the same
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 04 Jul 2015
 */
@Repository("pqNeo")
public class PhyQuantityNeoRepoImpl implements PhyQuantityRepo {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private StatusUtil statusUtil;

	private static final String getAllPhyQty = "MATCH (pq:PHYSICAL_QUANTITY {status:'active'}) RETURN pq";

	private static final String getAllPhyQtyByDataType = "MATCH (dt:DATA_TYPE {name:'{data_type}'})  "
	        + "MATCH(pq:PHYSICAL_QUANTITY {status:'active'}) MATCH(pq)-[:isOfType]->(dt) RETURN pq;";

	private static final String getPhyQty = "MATCH (pq:PHYSICAL_QUANTITY) where "
	        + "LOWER(pq.name)='{pq_name}' and pq.status_key<3 RETURN pq;";

	private static final String getPhyQtyByStatus = "MATCH (pq:PHYSICAL_QUANTITY) where "
	        + "LOWER(pq.name)='{pq_name}' and pq.status='{status_key}' RETURN pq;";

	private static final String createPhyQty = "MATCH (dt:DATA_TYPE) where LOWER(dt.name)='{dt_name}' "
	        + "CREATE (pq:PHYSICAL_QUANTITY {props})  "
	        + "CREATE pq-[:isOfType]->dt return pq;";

	private static final String updatePhyQty = "MATCH (pq:PHYSICAL_QUANTITY {name:'{pq_name}'}) "
	        + "SET pq.name = {props}.name, pq.status={props}.status, pq.status_key={props}.status_key return pq;";

	private static final String deletePhyQty = "MATCH (pq:PHYSICAL_QUANTITY {name:'{pq_name}'})-[r:measuresIn]-(u:UNIT) "
	        + "set pq.status='{status_name}', pq.status_key={status_key}, u.status='{status_name}',u.status_key={status_key} RETURN pq;";

	private static final String getPhyQtyByUnit = "MATCH (pq:PHYSICAL_QUANTITY) match pq-[:measuresIn]->"
	        + "(u:UNIT {name:'{unit_name}'})  where LOWER(pq.name)='{pq_name}' RETURN pq ;";
	
	private static final String getPhyQtyByUnitNDatType = "MATCH (dt:DATA_TYPE {name:'{dt_name}'})"
			+ "<-[:isOfType]-(pq:PHYSICAL_QUANTITY {status:'active'})"
			+ "-[:measuresIn]->(u:UNIT {name:'{unit_name}'}) "
			+ "where LOWER(pq.name)='{pq_name}' RETURN pq ;";
	
	private static final String getInPayloadDataTypeParent = "MATCH (dt:DATA_TYPE {name:'{dt_name}'})<-[:isDataType]-(p:DATA_TYPE) RETURN p;";
	
	private static final String getInpayloadPQDatyTypeParent = "MATCH (pq:PHYSICAL_QUANTITY {name:'{pq_name}'})-[:isOfType]->(p:DATA_TYPE)<-[:isDataType]-(parent:DATA_TYPE) RETURN parent;";
			

	@Override
	public boolean isPhyQuantityExist(String phyQuantityName) {
		phyQuantityName = phyQuantityName.toLowerCase();
		String query = getPhyQty.replace("{pq_name}", phyQuantityName);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in checking existence of physical quantity", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public List<PhysicalQuantity> getAllPhysicalQuantity(String dataType) {
		String query = null;
		if (StringUtils.isEmpty(dataType)) {
			query = getAllPhyQty;
		} else {
			query = getAllPhyQtyByDataType.replace("{data_type}", dataType);
		}
		return getAllPhyQuantities(query);
	}

	@Override
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName) {
		JSONArray pqJsonArray = null;
		String query = getPhyQty.replace("{pq_name}",
		        phyQuantityName.toLowerCase());
		try {
			pqJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching details of physical quantity ", e);
		}
		if (pqJsonArray == null || pqJsonArray.length() < 1) {
			return null;
		}
		return convertToPhyQty(pqJsonArray.getJSONObject(0));
	}

	@Override
	public void deletePhyQuantity(PhysicalQuantity phyQuantity) {
		JSONArray pqJsonArray = null;
		String query = deletePhyQty
		        .replace("{pq_name}", phyQuantity.getName())
		        .replace("{status_name}", Status.DELETED.getStatus())
		        .replace("{status_key}",
		                statusUtil.getStatus(DELETED.name()).toString());
		try {
			pqJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in deleting physical quantity ", e);
		}
		if (pqJsonArray == null) {
			throw new PersistenceException(
			        "Error in deleting physical quantity , response is null");
		}
	}

	@Override
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName,
	        Status status) {
		JSONArray pqJsonArray = null;
		phyQuantityName = phyQuantityName.toLowerCase();
		String query = getPhyQtyByStatus.replace("{pq_name}", phyQuantityName)
		        .replace("{status_key}",
		                ACTIVE.getStatus());
		try {
			pqJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching details of physical quantity filtered by status",
			        e);
		}
		if (pqJsonArray == null || pqJsonArray.length() < 1) {
			return null;
		}
		return convertToPhyQty(pqJsonArray.getJSONObject(0));
	}

	@Override
	public void savePhyQuantity(PhysicalQuantity physicalQuantity) {
		persistPhysicalQuantity(physicalQuantity);
	}

	private List<PhysicalQuantity> getAllPhyQuantities(String query) {
		JSONArray pqJsonArray = null;
		try {
			pqJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching physical quantities ",
			        e);
		}
		if (pqJsonArray == null) {
			return Collections.emptyList();
		}
		List<PhysicalQuantity> physicalQuantities = new ArrayList<PhysicalQuantity>();
		for (int i = 0; i < pqJsonArray.length(); i++) {
			physicalQuantities
			        .add(convertToPhyQty(pqJsonArray.getJSONObject(i)));
		}
		return physicalQuantities;
	}

	private void persistPhysicalQuantity(PhysicalQuantity phyQuantity) {
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addExcludeFields(ID.getVariableName(),
		        UNIT.getFieldName());

		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder()
		        .setExclusionStrategies(excludeFields);
		Gson gson = builder.create();
		String paramsJson = gson.toJson(phyQuantity);
		JSONArray pqJsonArray = null;
		try {
			String query = createPhyQty.replace("{dt_name}", phyQuantity
			        .getDataType().name().toLowerCase());
			pqJsonArray = exexcuteQuery(neo4jURI, query, paramsJson,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in saving physical quantity",
			        e);
		}
		if (pqJsonArray == null) {
			throw new PersistenceException(
			        "Error in saving physical quantity , response is null");
		}
	}

	private PhysicalQuantity convertToPhyQty(JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.getFieldName());
		Gson lowerUnderScore = objectBuilderUtil.getLowerCaseUnderScoreGson1();
		PhysicalQuantity physicalQuantity = new PhysicalQuantity();
		if (!jsonArray.isNull(0)) {
			physicalQuantity = lowerUnderScore.fromJson(jsonArray
			        .getJSONObject(0).toString(), PhysicalQuantity.class);
		}
		return physicalQuantity;
	}
	
	private DataType convertToDatyType(JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.getFieldName());
		Gson lowerUnderScore = objectBuilderUtil.getLowerCaseUnderScoreGson1();
		DataType dataType = new DataType();
		if (!jsonArray.isNull(0)) {
			dataType = lowerUnderScore.fromJson(jsonArray
					.getJSONObject(0).toString(), DataType.class);
		}
		return dataType;
	}

	@Override
	public void updatePhyQuantity(String phyQuantityName,
	        PhysicalQuantity newPhyQty) {
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addExcludeFields(ID.getVariableName(),
		        UNIT.getFieldName());

		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder()
		        .setExclusionStrategies(excludeFields);
		Gson gson = builder.create();
		String paramsJson = gson.toJson(newPhyQty);
		JSONArray pqJsonArray = null;
		try {
			String query = updatePhyQty.replace("{pq_name}", phyQuantityName);
			pqJsonArray = exexcuteQuery(neo4jURI, query, paramsJson,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in updating physical quantity", e);
		}
		if (pqJsonArray == null) {
			throw new PersistenceException(
			        "Error in updating physical quantity , response is null");
		}
	}

	@Override
	public boolean isPhysicalQuantityValid(String physicalQuantity, String unit) {
		physicalQuantity = physicalQuantity.toLowerCase();
		unit = unit.toLowerCase();
		String query = getPhyQtyByUnit.replace("{pq_name}", physicalQuantity)
		        .replace("{unit_name}", unit);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in checking existence of physical quantity with unit",
			        e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isPhysicalQuantityValid(String physicalQuantity,
			String unit, DataTypes dataType) {
		physicalQuantity = physicalQuantity.toLowerCase();
		unit = unit.toLowerCase();
		String query = getPhyQtyByUnitNDatType.replace("{pq_name}", physicalQuantity)
				.replace("{unit_name}", unit).replace("{dt_name}", dataType.getDataType());
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in checking existence of physical quantity with unit and data type",
					e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isPhysicalQuantityValidByDataType(String physicalQuantity,
			String unit, DataTypes dataType) {
		physicalQuantity = physicalQuantity.toLowerCase();
		unit = unit.toLowerCase();
		String query = getInPayloadDataTypeParent.replace("{dt_name}", dataType.getDataType());
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in checking existence of physical quantity with unit and data type",
					e);
		}
		if (jsonArray == null) {
			return false;
		}
		
		DataType dataTypeInPayload =  convertToDatyType(jsonArray.getJSONObject(0));
		
		query = getInpayloadPQDatyTypeParent.replace("{pq_name}",physicalQuantity);
		
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
					ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
					"Error in checking existence of physical quantity with unit and data type",
					e);
		}
		if (jsonArray == null) {
			return false;
		}
		
		DataType dataTypeInPayloadPq =  convertToDatyType(jsonArray.getJSONObject(0));
		
		if(dataTypeInPayload.getName().equals(dataTypeInPayloadPq.getName())){
			return true;
		}
		
		
		return false;
	}

}

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
import static com.pcs.datasource.enums.PhyQuantityFields.CONVERSION;
import static com.pcs.datasource.enums.PhyQuantityFields.ID;
import static com.pcs.datasource.enums.PhyQuantityFields.P_ID;
import static com.pcs.datasource.enums.PhyQuantityFields.P_NAME;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;
import static com.pcs.devicecloud.enums.Status.DELETED;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.repository.UnitRepo;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ExcludeFields;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;
import com.pcs.devicecloud.enums.Status;

/**
 * This class is responsible for defining repo(Neo4j) services related to Unit
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 07 Jul 2015
 */
@Repository("unitNeo")
public class UnitRepoNeoImpl implements UnitRepo {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private StatusUtil statusUtil;

	private static final String getAllUnitsOfPhyQty = "MATCH (pq:PHYSICAL_QUANTITY {name:'{pq_name}'})-[r:measuresIn]-(u:UNIT {status:'active'}) "
	        + "where pq.status_key<{sttaus_key} RETURN u ;";

	private static final String getSIUnitByPQName = "MATCH (u:UNIT {is_si:true})<-[r:measuresIn]-(pq:PHYSICAL_QUANTITY {name:'{pq_name}'}) RETURN u;";

	private static final String getUnitDetails = "MATCH (n:`UNIT` {name:'{unit_name}',status:'active'}) RETURN n;";

	private static final String getSiUnit = "MATCH (n:`UNIT` {name:'{unit_name}',is_si:true}) RETURN n ;";

	private static final String createUnit = "CREATE (u:UNIT {props});";

	private static final String createUnitWithPqRelation = "MATCH (pq:PHYSICAL_QUANTITY {name:'{pq_name}'}) "
	        + "SET pq.status='{status_name}' "
	        + "CREATE(u:UNIT {props}) CREATE u<-[r:measuresIn]-pq return u;";

	private static final String updateUnit = " MATCH (u:UNIT {name:'{unit_name}'}) "
	        + "SET u.name={props}.name,u.is_si={props}.is_si,u.conversion={props}.conversion return u ;";

	private static final String deleteUnit = "MATCH (u:UNIT {name:'{unit_name}'}) set u.status='{status_name}', u.status_key={status_key} RETURN u;";

	private static final String getUnit = "MATCH (u:UNIT) where "
	        + "LOWER(u.name)='{unit_name}' and u.status_key<3 RETURN u;";

	@Override
	public void deleteUnit(Unit unit) {
		JSONArray unitJsonArray = null;
		String query = deleteUnit
		        .replace("{unit_name}", unit.getName())
		        .replace("{status_name}", Status.DELETED.getStatus())
		        .replace("{status_key}",
		                statusUtil.getStatus(DELETED.name()).toString());
		try {
			unitJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in deleting unit ", e);
		}
		if (unitJsonArray == null) {
			throw new PersistenceException(
			        "Error in deleting unit , response is null");
		}
	}

	@Override
	public void deleteUnit(String arg0, PhysicalQuantity arg1) {
	}

	@Override
	public List<Unit> getActiveUnits(String arg0, Integer arg1) {
		return null;
	}

	@Override
	public Unit getSIUnitByPQName(String pqName) {
		JSONArray unitsJsonArray = null;
		try {
			String query = getSIUnitByPQName.replace("{pq_name}", pqName);
			unitsJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getVariableName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching si unit of a physical quantity", e);
		}
		if (unitsJsonArray == null || unitsJsonArray.length() < 1) {
			return null;
		}
		return convertToUnit(unitsJsonArray.getJSONObject(0));
	}

	@Override
	public Unit getUnitDetails(String unitName) {
		JSONArray unitsJsonArray = null;
		try {
			String query = getUnitDetails.replace("{unit_name}", unitName);
			unitsJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getVariableName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching details of a unit", e);
		}
		if (unitsJsonArray == null || unitsJsonArray.length() < 1) {
			return null;
		}
		return convertToUnit(unitsJsonArray.getJSONObject(0));
	}

	@Override
	public Unit getUnitDetails(String unitName, String phyQuantityName) {
		return null;
	}

	@Override
	public List<Unit> getUnits(String phyQuantityName) {
		JSONArray unitsJsonArray = null;
		try {
			String query = getAllUnitsOfPhyQty.replace("{pq_name}",
			        phyQuantityName).replace("{sttaus_key}",
			        statusUtil.getStatus(DELETED.name()).toString());
			unitsJsonArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getVariableName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching all units of a physical quantity", e);
		}
		if (unitsJsonArray == null) {
			return Collections.emptyList();
		}
		List<Unit> units = new ArrayList<Unit>();
		for (int i = 0; i < unitsJsonArray.length(); i++) {
			units.add(convertToUnit(unitsJsonArray.getJSONObject(i)));
		}
		return units;
	}

	@Override
	public void persistUnit(Unit unit) {
		JSONArray unitsJsonArray = null;
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addExcludeFields(ID.getVariableName(),
		        P_NAME.getVariableName(), P_ID.getVariableName());
		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder()
		        .setExclusionStrategies(excludeFields);
		Gson gson = builder.create();
		String paramsJson = gson.toJson(unit);
		try {
			unitsJsonArray = exexcuteQuery(neo4jURI, createUnit, paramsJson,
			        "row");
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in creating unit", e);
		}
		if (unitsJsonArray == null) {
			throw new PersistenceException(
			        "Error in creating unit,Response is null");
		}
	}

	@Override
	public void persistUnit(Unit unit, PhysicalQuantity physicalQuantity) {
		JSONArray unitsJsonArray = null;
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addExcludeFields(ID.getVariableName(),
		        P_NAME.getVariableName(), P_ID.getVariableName());
		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder()
		        .setExclusionStrategies(excludeFields);
		Gson gson = builder.create();
		String paramsJson = gson.toJson(unit);
		String query = createUnitWithPqRelation.replace("{pq_name}",
		        physicalQuantity.getName()).replace("{status_name}",
		        physicalQuantity.getStatus());
		try {
			unitsJsonArray = exexcuteQuery(neo4jURI, query, paramsJson, "row");
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in creating unit", e);
		}
		if (unitsJsonArray == null) {
			throw new PersistenceException(
			        "Error in creating unit,Response is null");
		}
	}

	@Override
	public void updateUnit(String uniqueId, Unit unit) {
		ExcludeFields excludeFields = new ExcludeFields();
		excludeFields.addExcludeFields(ID.getVariableName(),
		        P_NAME.getVariableName(), P_ID.getVariableName());

		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder()
		        .setExclusionStrategies(excludeFields);
		Gson gson = builder.create();
		String paramsJson = gson.toJson(unit);
		JSONArray pqJsonArray = null;
		try {
			String query = updateUnit.replace("{unit_name}", uniqueId);
			pqJsonArray = exexcuteQuery(neo4jURI, query, paramsJson,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in updating unity", e);
		}
		if (pqJsonArray == null) {
			throw new PersistenceException(
			        "Error in updating unity , response is null");
		}
	}

	@Override
	public void updateUnit(Unit arg0, PhysicalQuantity arg1) {
	}

	private Unit convertToUnit(JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.getFieldName());
		Gson lowerUnderScore = objectBuilderUtil.getLowerCaseUnderScoreGson1();
		Unit unit = new Unit();
		if (!jsonArray.isNull(0)) {
			unit = lowerUnderScore.fromJson(jsonArray.getJSONObject(0)
			        .toString(), Unit.class);
		}
		if (StringUtils.isNotEmpty(unit.getConversion())) {
			try {
				unit.setConversion(URLDecoder.decode(unit.getConversion(),
				        "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.DESERIALIZATION_FAILED,
				        CONVERSION.getDescription());
			}
		}

		return unit;
	}

	@Override
	public boolean isSIUnit(String unitName) {
		JSONArray unitsJsonArray = null;
		String query = getSiUnit.replace("{unit_name}", unitName);
		try {
			unitsJsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in checking existence of a SI unit", e);
		}
		if (unitsJsonArray == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isUnitExist(String unitName) {
		unitName = unitName.toLowerCase();
		String query = getUnit.replace("{unit_name}", unitName);
		JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in checking existence of unit", e);
		}
		if (jsonArray == null) {
			return false;
		}
		return true;
	}

}

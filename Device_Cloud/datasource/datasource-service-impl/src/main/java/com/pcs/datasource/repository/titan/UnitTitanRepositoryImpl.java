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

import static com.pcs.datasource.repository.utils.VertexMapper.fromResult;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.repository.UnitRepo;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH pcseg296
 * @date December 2015
 */
@Repository("unitTitan")
public class UnitTitanRepositoryImpl implements UnitRepo {

	@Autowired
	private TitanSessionManager titanSessionManager;

	@Autowired
	private StatusUtil statusUtil;

	private static final String createUnit = "graph.addVertex(label,'UNIT','isSi',isSi,'name',name,'symbol',symbol,'conversion',conversion,'statusKey',statusKey,'status',status)";

	private static final String createUnitWithPQ = "pqNode = g.V().hasLabel('PHYSICAL_QUANTITY').has('name', pqName).next(); pqNode.property('statusKey',pqStatusKey); pqNode.property('status',pqStatus); unit = graph.addVertex(label,'UNIT','isSi',isSI,'name',name,'symbol',symbol,'conversion',conversion,'statusKey',uStatusKey,'status',uStatus); pqNode.addEdge('measuresIn',unit) ;";

	private static final String getAllUnitsOfPQ = "g.V().hasLabel('PHYSICAL_QUANTITY').has('name', name).out('measuresIn').valueMap()";

	private static final String getSIUnitByPQName = "g.V().hasLabel('UNIT').has('isSi',true).in('measuresIn').has('name', name).valueMap()";

	private static final String deleteUnit = "g.V().hasLabel('UNIT').has('name',name).property('statusKey',statusKey).property('status',status);";

	private static final String getUnitDetails = "unit = g.V().hasLabel('UNIT').has('name',name).has('statusKey',eq(0)).valueMap();";

	private static final String getSiUnit = "unit = g.V().hasLabel('UNIT').has('name',name).has('isSi', true).valueMap();";

	private static final String updateUnit = "unit = g.V().hasLabel('UNIT').has('name',dbName).next(); unit.property('name', newName); unit.property('isSi', isSi); unit.property('conversion', conversion);";

	private static final String getUnit = "unit = g.V().hasLabel('UNIT').has('name',name).has('statusKey',lt(3)).filter{it.get().value('name').equalsIgnoreCase(name)}.valueMap();";

	@Override
	public void deleteUnit(Unit unit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", unit.getName());
		params.put("statusKey", unit.getStatusKey());
		params.put("status", unit.getStatus());

		try {
			titanSessionManager.getClient().submit(deleteUnit, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
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

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", pqName);

		Unit unit = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getSIUnitByPQName, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return unit;
			}
			unit = fromResult(result, Unit.class);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return unit;
	}

	@Override
	public Unit getUnitDetails(String unitName) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", unitName);

		Unit unit = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getUnitDetails, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return unit;
			}
			unit = fromResult(result, Unit.class);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return unit;
	}

	@Override
	public Unit getUnitDetails(String unitName, String phyQuantityName) {
		return null;
	}

	@Override
	public List<Unit> getUnits(String phyQuantityName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", phyQuantityName);
		List<Unit> units = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getAllUnitsOfPQ, params);
			units = fromResults(resultSet.all().get(), Unit.class);
		} catch (NoResultException e) {
			return units;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return units;
	}

	@Override
	public void persistUnit(Unit unit) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isSI", unit.getIsSi());
		params.put("name", unit.getName());
		params.put("symbol", unit.getSymbol());
		params.put("statusKey", unit.getStatusKey());
		params.put("status", unit.getStatus());
		params.put("conversion", unit.getConversion());

		try {
			titanSessionManager.getClient().submit(createUnit, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@Override
	public void persistUnit(Unit unit, PhysicalQuantity physicalQuantity) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isSI", unit.getIsSi());
		params.put("name", unit.getName());
		if (StringUtils.isEmpty(unit.getSymbol())) {
			unit.setSymbol("");
		}
		params.put("symbol", unit.getSymbol());
		params.put("uStatusKey", unit.getStatusKey());
		params.put("uStatus", unit.getStatus());
		if (StringUtils.isEmpty(unit.getConversion())) {
			unit.setConversion("");
		}
		params.put("conversion", unit.getConversion());

		params.put("pqName", physicalQuantity.getName());
		params.put("pqStatusKey", physicalQuantity.getStatusKey());
		params.put("pqStatus", physicalQuantity.getStatus());

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        createUnitWithPQ, params);
			resultSet.all().get();
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@Override
	public void updateUnit(String uniqueId, Unit unit) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", uniqueId);
		params.put("newName", unit.getName());
		params.put("isSi", unit.getIsSi());
		params.put("conversion", unit.getConversion());

		try {
			titanSessionManager.getClient().submit(updateUnit, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@Override
	public void updateUnit(Unit arg0, PhysicalQuantity arg1) {
	}

	@Override
	public boolean isSIUnit(String unitName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", unitName);

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getSiUnit, params);
			if (resultSet.one() == null) {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return Boolean.TRUE;
	}

	@Override
	public boolean isUnitExist(String unitName) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", unitName.toLowerCase());

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getUnit, params);
			if (resultSet.one() == null) {
				return false;
			}
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return true;
	}

}

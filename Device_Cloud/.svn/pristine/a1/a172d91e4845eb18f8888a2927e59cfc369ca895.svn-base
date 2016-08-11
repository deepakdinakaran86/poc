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
import static com.pcs.devicecloud.enums.Status.ACTIVE;

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
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.repository.PhyQuantityRepo;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH pcseg296
 * @date December 2015
 */
@Repository("pqTitan")
public class PhyQuantityTitanRepositoryImpl implements PhyQuantityRepo {

	@Autowired
	private TitanSessionManager titanSessionManager;

	@Autowired
	private StatusUtil statusUtil;

	private static final String getPhyQtyTitan = "g.V().hasLabel('PHYSICAL_QUANTITY').has('name', name).has('statusKey',lt(3)).filter{it.get().value('name').equalsIgnoreCase(name)}.valueMap()";

	private static final String getAllPhyQtyTitan = "g.V().hasLabel('PHYSICAL_QUANTITY').has('statusKey',statusKey).valueMap()";

	private static final String getAllPhyQtyByDataTypeTitan = "g.V().hasLabel('DATA_TYPE').has('name','{data_type}').in('isOfType').has('statusKey',statusKey).valueMap()";

	private static final String getPhyQtyByStatusTitan = "g.V().hasLabel('PHYSICAL_QUANTITY').has('name', name).has('status',status).filter{it.get().value('name').equalsIgnoreCase(name)}.valueMap()";

	private static final String createPhyQtyTitan = " dataType = g.V().hasLabel('DATA_TYPE').has('name', dt_name).next(); physicalQuantity = graph.addVertex(label,'PHYSICAL_QUANTITY','name',pqName,'dataStore',dataStore,'statusKey',statusKey,'status',status); physicalQuantity.addEdge('isOfType',dataType); ";

	private static final String updatePhyQtyTitan = "  physicalQuantity = g.V().hasLabel('PHYSICAL_QUANTITY').has('name', dbName).property('name', newName).property('statusKey',statusKey).property('status',status)";

	private static final String deletePhyQtyTitan = " physicalQuantity = g.V().hasLabel('PHYSICAL_QUANTITY').has('name', name).valueMap();  physicalQuantity.property('statusKey',statusKey); physicalQuantity.property('status',status); unit.physicalQuantity.in('measuresIn').valueMap(); unit.property('statusKey',statusKey); unit.property('status',status);";

	private static final String getPhyQtyByUnit = "physicalQuantity = g.V().hasLabel('PHYSICAL_QUANTITY').has('name', pqName).filter{it.get().value('name').equalsIgnoreCase(pqName)}.out('measuresIn').has('name', uName).valueMap()";

	@Override
	public boolean isPhyQuantityExist(String phyQuantityName) {
		PhysicalQuantity phyQuantity = getPhyQuantityDetails(phyQuantityName);
		if (phyQuantity == null) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public List<PhysicalQuantity> getAllPhysicalQuantity(String dataType) {
		String query = null;
		if (StringUtils.isEmpty(dataType)) {
			query = getAllPhyQtyTitan;
		} else {
			query = getAllPhyQtyByDataTypeTitan
			        .replace("{data_type}", dataType);
		}
		return getAllPhyQuantities(query);
	}

	@Override
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", phyQuantityName.toLowerCase());
		PhysicalQuantity physicalQuantity = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getPhyQtyTitan, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return physicalQuantity;
			}
			physicalQuantity = fromResult(result, PhysicalQuantity.class);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return physicalQuantity;
	}

	@Override
	public void deletePhyQuantity(PhysicalQuantity phyQuantity) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", phyQuantity.getName());
		params.put("status", phyQuantity.getStatus());
		params.put("statusKey", phyQuantity.getStatusKey());
		try {
			titanSessionManager.getClient().submit(deletePhyQtyTitan, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@Override
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName,
	        Status status) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", phyQuantityName.toLowerCase());
		params.put("status", status);
		PhysicalQuantity physicalQuantity = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getPhyQtyByStatusTitan, params);
			if (resultSet == null) {
				return physicalQuantity;
			}
			physicalQuantity = fromResult(resultSet.one(),
			        PhysicalQuantity.class);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return physicalQuantity;
	}

	@Override
	public void savePhyQuantity(PhysicalQuantity physicalQuantity) {
		persistPhysicalQuantity(physicalQuantity);
	}

	private List<PhysicalQuantity> getAllPhyQuantities(String query) {
		List<PhysicalQuantity> physicalQuantities = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("statusKey", statusUtil.getStatus(ACTIVE.name()));
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query,
			        params);
			physicalQuantities = fromResults(resultSet.all().get(),
			        PhysicalQuantity.class);
		} catch (NoResultException nre) {
			return physicalQuantities;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return physicalQuantities;
	}

	private void persistPhysicalQuantity(PhysicalQuantity phyQuantity) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dt_name", phyQuantity.getDataType().getDataType());
		params.put("pqName", phyQuantity.getName());
		params.put("dataStore", phyQuantity.getDataStore());
		params.put("status", phyQuantity.getStatus());
		params.put("statusKey", phyQuantity.getStatusKey());
		try {
			titanSessionManager.getClient().submit(createPhyQtyTitan, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@Override
	public void updatePhyQuantity(String phyQuantityName,
	        PhysicalQuantity newPhyQty) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", phyQuantityName);
		params.put("newName", newPhyQty.getName());
		params.put("status", newPhyQty.getStatus());
		params.put("statusKey", newPhyQty.getStatusKey());
		try {
			titanSessionManager.getClient().submit(updatePhyQtyTitan, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@Override
	public boolean isPhysicalQuantityValid(String physicalQuantity, String unit) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pqName", physicalQuantity.toLowerCase());
		params.put("uName", unit);

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getPhyQtyByUnit, params);
			if (resultSet.one() == null) {
				return false;
			}
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return true;
	}

	@Override
	public boolean isPhysicalQuantityValid(String physicalQuantity,
			String unit, DataTypes dataType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPhysicalQuantityValidByDataType(String physicalQuantity,
			String unit, DataTypes dataType) {
		// TODO Auto-generated method stub
		return false;
	}

}

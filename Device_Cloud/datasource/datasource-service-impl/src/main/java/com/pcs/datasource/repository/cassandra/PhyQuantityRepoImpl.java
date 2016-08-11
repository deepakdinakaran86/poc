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
package com.pcs.datasource.repository.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lt;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.pcs.datasource.enums.PhyQuantityFields.CONVERSION;
import static com.pcs.datasource.enums.PhyQuantityFields.DATASTORE;
import static com.pcs.datasource.enums.PhyQuantityFields.ID;
import static com.pcs.datasource.enums.PhyQuantityFields.IS_SI;
import static com.pcs.datasource.enums.PhyQuantityFields.NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.PHY_QUAN;
import static com.pcs.datasource.enums.PhyQuantityFields.PHY_QUANTITY;
import static com.pcs.datasource.enums.PhyQuantityFields.P_ID;
import static com.pcs.datasource.enums.PhyQuantityFields.P_NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.STATUS_KEY;
import static com.pcs.datasource.enums.PhyQuantityFields.STATUS_NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.UNIT;
import static com.pcs.devicecloud.enums.Status.ACTIVE;
import static com.pcs.devicecloud.enums.Status.DELETED;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.datastax.driver.core.querybuilder.Update;
import com.datastax.driver.core.utils.UUIDs;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.repository.PhyQuantityRepo;
import com.pcs.datasource.repository.utils.CassandraSessionManager;
import com.pcs.datasource.repository.utils.DataConversion;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.devicecloud.enums.Status;

/**
 * This service interface is responsible for defining all the services related
 * to datastore in which stores device data and also manages cache for the same.
 * This class is responsible for retrieving columnfamily details of particular
 * physical quantity from the device data. If this mapping information already
 * present cache then retrieve from cache otherwise access DB for the same
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

@Service
public class PhyQuantityRepoImpl implements PhyQuantityRepo {

	@Autowired
	private CassandraSessionManager cassandraSessionManager;

	@Autowired
	private DataConversion dataConversion;

	@Autowired
	private StatusUtil statusUtil;

	Statement statement = null;
	Where selectClause = null;

	/**
	 * Method to persist physical quantities, units are not manadatory
	 * 
	 * @param phyQuantityDTO
	 */

	@Override
	public void savePhyQuantity(PhysicalQuantity phyQuantity) {

		BatchStatement batchStatement = new BatchStatement();
		List<Insert> insertStmnts = new ArrayList<Insert>();

		insertStmnts.add(QueryBuilder.insertInto(PHY_QUANTITY.getFieldName())
		        .value(ID.getFieldName(), phyQuantity.getId())
		        .value(DATASTORE.getFieldName(), phyQuantity.getDataStore())
		        .value(PHY_QUAN.getFieldName(), phyQuantity.getName())
		        .value(STATUS_NAME.getFieldName(), phyQuantity.getStatus())
		        .value(STATUS_KEY.getFieldName(), phyQuantity.getStatusKey()));

		if (CollectionUtils.isNotEmpty(phyQuantity.getUnits())) {
			Integer activeStatus = statusUtil.getStatus(ACTIVE.name());
			for (Unit unit : phyQuantity.getUnits()) {
				insertStmnts.add(QueryBuilder.insertInto(UNIT.getFieldName())
				        .value(ID.getFieldName(), UUIDs.timeBased())
				        .value(CONVERSION.getFieldName(), unit.getConversion())
				        .value(IS_SI.getFieldName(), unit.getIsSi())
				        .value(NAME.getFieldName(), unit.getName())
				        .value(P_NAME.getFieldName(), phyQuantity.getName())
				        .value(P_ID.getFieldName(), phyQuantity.getId())
				        .value(STATUS_KEY.getFieldName(), activeStatus)
				        .value(STATUS_NAME.getFieldName(), ACTIVE.name()));

			}
		}

		batchStatement.addAll(insertStmnts);
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        batchStatement);
	}

	/**
	 * Method to get mapping of datastore and physical quantity
	 * 
	 * @param phyQuantityName
	 * @return {@link PhysicalQuantity}
	 * 
	 */

	@Override
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName,
	        Status status) {
		selectClause = select()
		        .all()
		        .from(PHY_QUANTITY.getFieldName())
		        .allowFiltering()
		        .where(eq(PHY_QUAN.getFieldName(), phyQuantityName))
		        .and(eq(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(ACTIVE.name())));
		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		PhysicalQuantity phyQuantity = dataConversion
		        .convertToPhysicalQuantity(resultSet);
		return phyQuantity;
	}

	/**
	 * Method to get ACTIVE physical quantity details by name
	 * 
	 * @param phyQuantityName
	 * @return {@link PhyQuantityDTO}
	 */

	@Override
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName) {
		PhysicalQuantity phyQuantity = null;
		ResultSet resultSet = null;

		selectClause = select()
		        .all()
		        .from(PHY_QUANTITY.getFieldName())
		        .allowFiltering()
		        .where(eq(PHY_QUAN.getFieldName(), phyQuantityName))
		        .and(lt(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())));
		resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		phyQuantity = dataConversion.convertToPhysicalQuantity(resultSet);

		return phyQuantity;
	}

	/**
	 * Method to get ACTIVE physical quantity details by uuid
	 * 
	 * @param phyQuantityName
	 * @return {@link PhyQuantityDTO}
	 */

	/*
	 * @Override public PhysicalQuantity getPhyQuantityDetails(UUID uid) {
	 * selectClause = select().all().from(PHY_QUANTITY.getFieldName())
	 * .allowFiltering().where(eq(ID.getFieldName(), uid)); ResultSet resultSet
	 * = this.cassandraSessionManager.getSession(
	 * "dataSourceCassandra").execute(selectClause); PhysicalQuantity
	 * phyQuantity = dataConversion .convertToPhysicalQuantity(resultSet);
	 * return phyQuantity; }
	 */

	/**
	 * Method to delete (soft delete) physical quantities.
	 * 
	 * @param uuid
	 *            , {@link List}
	 */
	@Override
	public void deletePhyQuantity(PhysicalQuantity phyQuantity) {
		BatchStatement batchStatement = new BatchStatement();
		List<Update.Where> updateStmnts = new ArrayList<Update.Where>();
		Integer activeStatus = statusUtil.getStatus(DELETED.name());
		updateStmnts.add(QueryBuilder
		        .update(PHY_QUANTITY.getFieldName())
		        .with(set(STATUS_NAME.getFieldName(), DELETED.name()))
		        .and(set(STATUS_KEY.getFieldName(), activeStatus))
		        .where(eq(ID.getFieldName(),
		                UUID.fromString(phyQuantity.getId().toString()))));
		if (CollectionUtils.isNotEmpty(phyQuantity.getUnits())) {
			for (Unit unit : phyQuantity.getUnits()) {
				updateStmnts.add(QueryBuilder.update(UNIT.getFieldName())
				        .with(set(STATUS_KEY.getFieldName(), activeStatus))
				        .and(set(STATUS_NAME.getFieldName(), DELETED.name()))
				        .where(eq(ID.getFieldName(), unit.getId())));
			}
		}
		batchStatement.addAll(updateStmnts);
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        batchStatement);
	}

	@Override
	public List<PhysicalQuantity> getAllPhysicalQuantity(String arg0) {
		return null;
	}

	@Override
	public void updatePhyQuantity(String uid,
	        PhysicalQuantity newPhyQty) {
		BatchStatement batchStatement = new BatchStatement();
		List<Update.Where> updateStmnts = new ArrayList<Update.Where>();
		updateStmnts.add(QueryBuilder
		        .update(PHY_QUANTITY.getFieldName())
		        .with(set(PHY_QUAN.getFieldName(), newPhyQty.getName()))
		        .where(eq(ID.getFieldName(),
		                UUID.fromString(uid))));
		/*if (CollectionUtils.isNotEmpty(uid.getUnits())) {
			for (Unit unit : uid.getUnits()) {
				updateStmnts.add(QueryBuilder.update(UNIT.getFieldName())
				        .with(set(P_NAME.getFieldName(), newPhyQty.getName()))
				        .where(eq(ID.getFieldName(), unit.getId())));
			}
		}*/
		batchStatement.addAll(updateStmnts);
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        batchStatement);
	}

	@Override
	public boolean isPhyQuantityExist(String phyQuantityName) {
		ResultSet resultSet = null;
		selectClause = select()
		        .all()
		        .from(PHY_QUANTITY.getFieldName())
		        .allowFiltering()
		        .where(eq(PHY_QUAN.getFieldName(), phyQuantityName))
		        .and(lt(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())));
		resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		if (CollectionUtils.isEmpty(resultSet.all())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isPhysicalQuantityValid(String physicalQuantity, String unit) {
		// TODO Auto-generated method stub
		return false;
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

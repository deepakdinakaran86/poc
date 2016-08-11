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
import static com.pcs.datasource.enums.PhyQuantityFields.ID;
import static com.pcs.datasource.enums.PhyQuantityFields.IS_SI;
import static com.pcs.datasource.enums.PhyQuantityFields.NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.PHY_QUANTITY;
import static com.pcs.datasource.enums.PhyQuantityFields.P_ID;
import static com.pcs.datasource.enums.PhyQuantityFields.P_NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.STATUS_KEY;
import static com.pcs.datasource.enums.PhyQuantityFields.STATUS_NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.UNIT;
import static com.pcs.devicecloud.enums.Status.ACTIVE;
import static com.pcs.devicecloud.enums.Status.DELETED;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.datastax.driver.core.utils.UUIDs;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.repository.UnitRepo;
import com.pcs.datasource.repository.utils.CassandraSessionManager;
import com.pcs.datasource.repository.utils.DataConversion;
import com.pcs.datasource.repository.utils.StatusUtil;

/**
 * This repository implementation is responsible for defining all the services
 * related to units describes physical quantities. this class is responsible for
 * basic CRUD operation on units
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

@Service
public class UnitRepoImpl implements UnitRepo {

	@Autowired
	private CassandraSessionManager cassandraSessionManager;

	@Autowired
	private DataConversion dataConversion;

	@Autowired
	private StatusUtil statusUtil;

	Where selectClause = null;

	/**
	 * method to save unit information of particular physical quantity
	 *
	 * @param unit
	 */
	@Override
    public void persistUnit(Unit unit) {

		Statement statement = QueryBuilder
		        .insertInto(UNIT.getFieldName())
		        .value(ID.getFieldName(), UUIDs.timeBased())
		        .value(CONVERSION.getFieldName(), unit.getConversion())
		        .value(IS_SI.getFieldName(), unit.getIsSi())
		        .value(NAME.getFieldName(), unit.getName())
		        .value(P_NAME.getFieldName(), unit.getpName())
		        .value(P_ID.getFieldName(), unit.getpUuid())
		        .value(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(ACTIVE.name()))
		        .value(STATUS_NAME.getFieldName(), ACTIVE.name());
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        statement);
	}

	/**
	 * Method to persist unit info along with update of status in physical
	 * quantity if any
	 *
	 * @param unit
	 * @param physicalQuantity
	 */
	@Override
    public void persistUnit(Unit unit, PhysicalQuantity physicalQuantity) {

		BatchStatement batchStatement = new BatchStatement();
		Integer activeStatus = statusUtil.getStatus(ACTIVE.name());
		Statement statement = QueryBuilder.insertInto(UNIT.getFieldName())
		        .value(ID.getFieldName(), UUIDs.timeBased())
		        .value(CONVERSION.getFieldName(), unit.getConversion())
		        .value(IS_SI.getFieldName(), unit.getIsSi())
		        .value(NAME.getFieldName(), unit.getName())
		        .value(P_NAME.getFieldName(), unit.getpName())
		        .value(P_ID.getFieldName(), unit.getpUuid())
		        .value(STATUS_KEY.getFieldName(), activeStatus)
		        .value(STATUS_NAME.getFieldName(), ACTIVE.name());
		batchStatement.add(statement);
		statement = QueryBuilder
		        .update(PHY_QUANTITY.getFieldName())
		        .with(set(STATUS_NAME.getFieldName(),
		                physicalQuantity.getStatus()))
		        .and(set(STATUS_KEY.getFieldName(),
		                physicalQuantity.getStatusKey()))
		        .where(eq(ID.getFieldName(), physicalQuantity.getId()));
		batchStatement.add(statement);
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        batchStatement);

	}

	/**
	 * Method to get all active unit information for particular physical
	 * quantity
	 *
	 * @param phyQuantityName
	 * @param status
	 * @return {@link List}
	 */
	@Override
    public List<Unit> getActiveUnits(String phyQuantityName, Integer status) {
		selectClause = select().all().from(UNIT.getFieldName())
		        .allowFiltering()
		        .where(eq(P_NAME.getFieldName(), phyQuantityName))
		        .and(eq(STATUS_KEY.getFieldName(), status));
		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		List<Unit> units = dataConversion.convertToUnitList(resultSet);
		return units;
	}

	/**
	 * Method to update unit info
	 * @param unit
	 */
	@Override
    public void updateUnit(String uniqueId, Unit unit) {
		Statement statement = QueryBuilder.update(UNIT.getFieldName())
		        .with(set(CONVERSION.getFieldName(), unit.getConversion()))
		        .and(set(IS_SI.getFieldName(), unit.getIsSi()))
		        .and(set(NAME.getFieldName(), unit.getName()))
		        .where(eq(ID.getFieldName(), UUID.fromString(uniqueId)));
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        statement);
	}

	/**
	 * Method to update unit info along with update of status in physical
	 * quantity if any
	 *
	 * @param unit
	 * @param physicalQuantity
	 */
	@Override
    public void updateUnit(Unit unit, PhysicalQuantity physicalQuantity) {
		BatchStatement batchStatement = new BatchStatement();
		Statement statement = QueryBuilder.update(UNIT.getFieldName())
		        .with(set(CONVERSION.getFieldName(), unit.getConversion()))
		        .and(set(IS_SI.getFieldName(), unit.getIsSi()))
		        .and(set(NAME.getFieldName(), unit.getName()))
		        .where(eq(ID.getFieldName(), unit.getId()));
		batchStatement.add(statement);
		statement = QueryBuilder
		        .update(PHY_QUANTITY.getFieldName())
		        .with(set(STATUS_NAME.getFieldName(),
		                physicalQuantity.getStatus()))
		        .and(set(STATUS_KEY.getFieldName(),
		                physicalQuantity.getStatusKey()))
		        .where(eq(ID.getFieldName(), physicalQuantity.getId()));
		batchStatement.add(statement);
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        batchStatement);
	}

	/**
	 * method to delete unit information (soft delete) of particular physical
	 * quantity
	 *
	 * @param unit
	 */
	@Override
    public void deleteUnit(Unit unit) {
		Statement statement = QueryBuilder
		        .update(UNIT.getFieldName())
		        .with(set(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())))
		        .and(set(STATUS_NAME.getFieldName(), DELETED.name()))
		        .where(eq(ID.getFieldName(), unit.getId()));
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        statement);
	}

	/**
	 * Method to dele unit info along with update of status in physical quantity
	 * if any
	 *
	 * @param uid
	 * @param physicalQuantity
	 */
	@Override
    public void deleteUnit(String id, PhysicalQuantity physicalQuantity) {
		BatchStatement batchStatement = new BatchStatement();
		Statement statement = QueryBuilder
		        .update(UNIT.getFieldName())
		        .with(set(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())))
		        .and(set(STATUS_NAME.getFieldName(), DELETED.name()))
		        .where(eq(ID.getFieldName(), UUID.fromString(id)));
		batchStatement.add(statement);
		statement = QueryBuilder
		        .update(PHY_QUANTITY.getFieldName())
		        .with(set(STATUS_NAME.getFieldName(),
		                physicalQuantity.getStatus()))
		        .and(set(STATUS_KEY.getFieldName(),
		                physicalQuantity.getStatusKey()))
		        .where(eq(ID.getFieldName(), physicalQuantity.getId()));
		batchStatement.add(statement);
		this.cassandraSessionManager.getSession("dataSourceCassandra").execute(
		        batchStatement);
	}

	/**
	 * Method to get Unit information by name for particular physical qunatity
	 *
	 * @param name
	 * @param phyQuantity
	 * @return {@link Unit}
	 */
	@Override
    public Unit getUnitDetails(String name, String phyQuantity) {
		selectClause = select()
		        .all()
		        .from(UNIT.getFieldName())
		        .allowFiltering()
		        .where(eq(NAME.getFieldName(), name))
		        .and(eq(P_NAME.getFieldName(), phyQuantity))
		        .and(lt(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())));
		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		Unit unit = dataConversion.convertToUnit(resultSet);
		return unit;
	}

	/**
	 * Method to get unit informations for particular physical quantity by
	 * physical quantity Id
	 *
	 * @param uuid
	 * @return {@link List}
	 */
	@Override
    public List<Unit> getUnits(String pUuid) {
		selectClause = select()
		        .all()
		        .from(UNIT.getFieldName())
		        .allowFiltering()
		        .where(eq(P_ID.getFieldName(), UUID.fromString(pUuid)))
		        .and(lt(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())));
		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		List<Unit> units = dataConversion.convertToUnitList(resultSet);
		return units;
	}

	/**
	 * Method to get si unit information by PQ name
	 *
	 * @param name
	 * @return {@link Unit}
	 */
	@Override
    public Unit getSIUnitByPQName(String name) {
		selectClause = select()
		        .all()
		        .from(UNIT.getFieldName())
		        .allowFiltering()
		        .where(eq(P_NAME.getFieldName(), name))
		        .and(eq(IS_SI.getFieldName(), true))
		        .and(lt(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())));
		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		Unit unit = dataConversion.convertToUnit(resultSet);
		return unit;
	}

	/**
	 * Method to get unit info by uuid
	 *
	 * @param id
	 * @return {@link Unit}
	 */
	@Override
    public Unit getUnitDetails(String name) {
		Unit unit = null;
		ResultSet resultSet = null;
		selectClause = select()
		        .all()
		        .from(UNIT.getFieldName())
		        .allowFiltering()
		        .where(eq(NAME.getFieldName(), name))
		        .and(lt(STATUS_KEY.getFieldName(),
		                statusUtil.getStatus(DELETED.name())));
		resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectClause);
		unit = dataConversion.convertToUnit(resultSet);

		return unit;
	}

	/* (non-Javadoc)
	 * @see com.pcs.datasource.repository.UnitRepo#isSIUnit(java.lang.String)
	 */
    @Override
    public boolean isSIUnit(String unitName) {
	    return false;
    }

	@Override
	public boolean isUnitExist(String unitName) {
		// TODO Auto-generated method stub
		return false;
	}

}

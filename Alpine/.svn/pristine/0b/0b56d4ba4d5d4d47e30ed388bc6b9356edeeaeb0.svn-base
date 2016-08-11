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
package com.pcs.alpine.serviceImpl.repository;

import static com.pcs.alpine.services.enums.EMDataFields.IS_DEFAULT;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS_KEY;
import static com.pcs.alpine.services.enums.TableNames.PLATFORM_ENTITY;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.model.PlatformEntity;
import com.pcs.alpine.repo.utils.CassandraDBUtil;

/**
 * GlobalEntityRepository
 * 
 * @description Responsible for providing the 'C R U D' operations for an entity
 * @author Daniela (PCSEG191)
 * @date 21 Aug 2014
 * @since galaxy-1.0.0
 */
@Repository("platformEntityRepository")
public class PlatformEntityRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(PlatformEntityRepository.class);

	@Autowired
	private CassandraDBUtil cassandraDBUtil;

	/**
	 * Responsible for fetching all active global entities
	 * 
	 * @return List<GlobalEntity>
	 */
	@Deprecated
	public List<PlatformEntity> getGlobalEntities(Integer statusKey,
	        Boolean isDefault) {
		List<PlatformEntity> globalEntities = null;
		Select selectEntity = null;

		selectEntity = QueryBuilder.select().from(PLATFORM_ENTITY.toString())
		        .allowFiltering();
		selectEntity.where(QueryBuilder.eq(STATUS_KEY.toString(), statusKey));
		selectEntity.where(QueryBuilder.eq(IS_DEFAULT.toString(), isDefault));

		ResultSet globalEntityResultSet = null;// this.cassandraOperations
		// .query(selectEntity);

		if (globalEntityResultSet != null) {
			// globalEntities = this.cassandraOperations.select(selectEntity,
			// PlatformEntity.class);
		}
		return globalEntities;
	}

	public PlatformEntity getPlatformEntityWithName(String platformEntityType,
	        Integer statusKey) {

		String cqlStatement = "select JSON platform_entity_type,status_key,is_sharable,is_default from platform_entity where platform_entity_type = '"
		        + platformEntityType + "' and status_key = " + statusKey + "";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		PlatformEntity platformEntity = null;

		Row one = null;
		if (resultSet != null) {
			List<Row> array = resultSet.all();
			if (CollectionUtils.isNotEmpty(array)) {
				one = array.get(0);
			}
		}
		if (one != null) {
			String string = one.getString(0);
			try {
				ObjectMapper mapper = new ObjectMapper();
				platformEntity = mapper.readValue(string, PlatformEntity.class);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		return platformEntity;

	}

	public Boolean isValidGlobalEntity(String entityType, Integer statusKey) {
		PlatformEntity globalType = getPlatformEntityWithName(entityType,
		        statusKey);
		if (globalType == null) {
			return false;
		}
		return true;
	}

}

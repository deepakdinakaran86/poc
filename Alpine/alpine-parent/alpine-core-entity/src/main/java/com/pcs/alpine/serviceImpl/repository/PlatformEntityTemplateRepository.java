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

import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS_KEY;
import static com.pcs.alpine.services.enums.TableNames.PLATFORM_ENTITY_TEMPLATE;

import java.util.ArrayList;
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
import com.pcs.alpine.model.PlatformEntityTemplate;
import com.pcs.alpine.repo.utils.CassandraDBUtil;

/**
 * PlatformEntityTemplateRepository
 * 
 * @description Responsible for providing the 'C R U D' operations for
 *              PlatformEntityTemplate
 * 
 * @author Twinkle (pcseg297)
 * @date Aug 2014
 * @since galaxy-1.0.0
 */
@Repository("PlatformEntityTemplateRepository")
public class PlatformEntityTemplateRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(PlatformEntityTemplateRepository.class);

	@Autowired
	private CassandraDBUtil cassandraDBUtil;

	/**
	 * Responsible for fetching the PLatform Template
	 * 
	 * @param entityType
	 * @return List<PlatformEntityTemplate>
	 */
	public List<PlatformEntityTemplate> getPlatformEntityTemplate(
	        String entityType) {
		String cqlStatement = "select JSON * from platform_entity_template where platform_entity_type = '"
		        + entityType + "'";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		List<PlatformEntityTemplate> platformEntityTemplates = new ArrayList<PlatformEntityTemplate>();
		for (Row row : resultSet) {
			ObjectMapper mapper = new ObjectMapper();
			String string = row.getString(0);
			try {
				PlatformEntityTemplate platformEntityTemplate = mapper
				        .readValue(string, PlatformEntityTemplate.class);
				platformEntityTemplates.add(platformEntityTemplate);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

		}
		return platformEntityTemplates;
	}

	/**
	 * Responsible for fetching the Global Template by Type and Template Name
	 * 
	 * @param globalEntityName
	 *            : GlobalEntityName
	 * @return GlobalEntityTemplate
	 */
	public PlatformEntityTemplate getGlobalEntityTemplate(String entityType,
	        String templateName, Integer statusKey) {

		String cqlStatement = "select JSON * from platform_entity_template where platform_entity_type = '"
		        + entityType
		        + "' and platform_entity_template_name = '"
		        + templateName + "' and status_key = " + statusKey + "";

		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		PlatformEntityTemplate platformEntityTemplate = null;
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
				platformEntityTemplate = mapper.readValue(string,
				        PlatformEntityTemplate.class);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		return platformEntityTemplate;
	}

	/**
	 * Responsible for fetching the Global Templates based on globalEntityType
	 * 
	 * @param globalEntityName
	 *            : GlobalEntityName
	 * @return GlobalEntityTemplate
	 */
	@Deprecated
	public List<PlatformEntityTemplate> getGlobalEntityTemplates(
	        String entityType, Integer statusKey) {

		Select selectGlobalTemplate = QueryBuilder.select().from(
		        PLATFORM_ENTITY_TEMPLATE.name());
		selectGlobalTemplate.where(QueryBuilder.eq(PLATFORM_ENTITY_TYPE.name(),
		        entityType));
		selectGlobalTemplate.where(QueryBuilder.eq(STATUS_KEY.toString(),
		        statusKey));
		selectGlobalTemplate.allowFiltering();
		List<PlatformEntityTemplate> globalEntityTemplates = null;// this.cassandraOperations
		// .select(selectGlobalTemplate, PlatformEntityTemplate.class);
		return globalEntityTemplates;
	}

	public Boolean isValidField(String platformEntityType, Integer statusKey,
	        String fieldName, String templateName) {

		String cqlStatement = "select platform_entity_type from platform_entity_template where platform_entity_type = '"
		        + platformEntityType
		        + "' and platform_entity_template_name = '"
		        + templateName
		        + "' and status_key = "
		        + statusKey
		        + " and field_validation contains key '" + fieldName + "'";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);

		Row one = null;
		if (resultSet != null) {
			List<Row> array = resultSet.all();
			if (CollectionUtils.isNotEmpty(array)) {
				one = array.get(0);
			}
		}
		if (one != null) {
			if (one.getString(0) != null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * Responsible for fetching the platform Template by Template Name and
	 * status key
	 * 
	 * @param templateName
	 *            ,statusKey,platformEntityType
	 * @return entityType
	 */
	public String getGlobalEntityTemplateType(String templateName,
	        Integer statusKey, String platformEntityType) {

		String cqlStatement = "select platform_entity_type from platform_entity_template where platform_entity_type = '"
		        + platformEntityType
		        + "' and platform_entity_template_name = '"
		        + templateName
		        + "' and status_key < " + statusKey + "";

		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		String entityType = null;
		Row one = null;
		if (resultSet != null) {
			List<Row> array = resultSet.all();
			if (CollectionUtils.isNotEmpty(array)) {
				one = array.get(0);
			}
		}
		if (one != null) {
			if (one.getString(0) != null) {
				entityType = one.getString(0);;
			}
		}
		return entityType;
	}
}

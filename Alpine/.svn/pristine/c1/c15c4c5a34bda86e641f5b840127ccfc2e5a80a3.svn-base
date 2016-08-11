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

import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALIDATION;
import static com.pcs.alpine.services.enums.EMDataFields.HTML_PAGE;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS_NAME;
import static com.pcs.alpine.services.enums.TableNames.ENTITY_TEMPLATE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.model.EntityTemplate;
import com.pcs.alpine.repo.utils.CassandraDBUtil;
import com.pcs.alpine.services.enums.EMDataFields;

/**
 * EntityTemplateRepository
 * 
 * @description Responsible for providing the 'C R U D' operations for an
 *              Template
 * @author Twinkle (pcseg297)
 * @date Aug 2014
 * @since galaxy-1.0.0
 */
@Repository("EntityTemplateRepository")
public class EntityTemplateRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EntityTemplateRepository.class);

	@Autowired
	private CassandraDBUtil cassandraDBUtil;

	public EntityTemplate saveTemplate(EntityTemplate entityTemplate) {
		Mapper<EntityTemplate> mapper = new MappingManager(
		        cassandraDBUtil.getSession()).mapper(EntityTemplate.class);
		mapper.save(entityTemplate);
		return entityTemplate;
	}

	public void saveTemplates(List<EntityTemplate> entityTemplates) {
		for (EntityTemplate entityTemplate : entityTemplates) {
			saveTemplate(entityTemplate);
		}
	}

	@Deprecated
	public void deleteTemplate(String domain, String entityTemplateName,
	        String globalEntityType, Integer statusKey, String statusName) {
		Update updateStatus = QueryBuilder.update(ENTITY_TEMPLATE.toString());
		updateStatus.where(QueryBuilder.eq(DOMAIN.toString(), domain));
		updateStatus.where(QueryBuilder.eq(ENTITY_TEMPLATE_NAME.toString(),
		        entityTemplateName));
		updateStatus.where(QueryBuilder.eq(PLATFORM_ENTITY_TYPE.toString(),
		        globalEntityType));
		updateStatus.with(QueryBuilder.set(STATUS_KEY.toString(), statusKey));
		updateStatus.with(QueryBuilder.set(STATUS_NAME.toString(), statusName));
		// this.cassandraOperations.execute(updateStatus);

	}

	@Deprecated
	public void updateTemplate(EntityTemplate entityTemplate) {
		Update updateStatus = QueryBuilder.update(ENTITY_TEMPLATE.toString());
		updateStatus.with(QueryBuilder.set(HTML_PAGE.name(),
		        entityTemplate.getHtmlPage()));
		updateStatus.with(QueryBuilder.set(FIELD_VALIDATION.name(),
		        entityTemplate.getFieldValidation()));
		updateStatus.where(QueryBuilder.eq(DOMAIN.name(),
		        entityTemplate.getDomain()));
		updateStatus.where(QueryBuilder.eq(ENTITY_TEMPLATE_NAME.name(),
		        entityTemplate.getEntityTemplateName()));
		updateStatus.where(QueryBuilder.eq(PLATFORM_ENTITY_TYPE.toString(),
		        entityTemplate.getPlatformEntityType()));
		// this.cassandraOperations.execute(updateStatus);

	}

	@Deprecated
	public EntityTemplate getTemplate(UUID uuid) {
		Select selectCustomTemplate = null;
		EntityTemplate entityTemplateResultSet = null;
		selectCustomTemplate = QueryBuilder.select().from(
		        ENTITY_TEMPLATE.toString());
		// selectCustomTemplate.where(QueryBuilder.eq(UUID.toString(), uuid));
		// entityTemplateResultSet = this.cassandraOperations.selectOne(
		// selectCustomTemplate, EntityTemplate.class);
		return entityTemplateResultSet;
	}

	@Deprecated
	public List<EntityTemplate> getTemplate(List<UUID> uuids) {
		Select selectCustomTemplate = null;
		List<EntityTemplate> entityTemplateResultSet = null;
		selectCustomTemplate = QueryBuilder.select().from(
		        ENTITY_TEMPLATE.toString());
		// selectCustomTemplate.where(QueryBuilder.in(UUID.toString(),
		// uuids.toArray()));
		// entityTemplateResultSet = this.cassandraOperations.select(
		// selectCustomTemplate, EntityTemplate.class);
		return entityTemplateResultSet;
	}

	public EntityTemplate getTemplate(EntityTemplate entityTemplate,
	        Integer statusKey) {

		String cqlStatement = "select json * from entity_template WHERE entity_template_name = '"
		        + entityTemplate.getEntityTemplateName()
		        + "' and domain = '"
		        + entityTemplate.getDomain()
		        + "' and platform_entity_type ='"
		        + entityTemplate.getPlatformEntityType()
		        + "' and status_key = " + statusKey + "";

		if (StringUtils.isNotBlank(entityTemplate.getParentTemplate())) {
			cqlStatement = cqlStatement + " and parent_template ='"
			        + entityTemplate.getParentTemplate() + "' ALLOW FILTERING;";
		}

		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		EntityTemplate result = null;
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
				result = mapper.readValue(string, EntityTemplate.class);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return result;
	}

	public Integer getTemplateCounts(EntityTemplate entityTemplate,
	        Integer statusKey) {

		String cqlStatement = "select count(*) from entity_template WHERE entity_template_name = '"
		        + entityTemplate.getEntityTemplateName()
		        + "' and domain = '"
		        + entityTemplate.getDomain()
		        + "' and platform_entity_type ='"
		        + entityTemplate.getPlatformEntityType()
		        + "' and status_key < " + statusKey + "";

		if (StringUtils.isNotBlank(entityTemplate.getParentTemplate())) {
			cqlStatement = cqlStatement + " and parent_template ='"
			        + entityTemplate.getParentTemplate() + "' ALLOW FILTERING;";
		}

		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		Row row = resultSet.one();
		long count = row.getLong("count");
		Integer countI = new Integer((int)count);
		return countI;
	}

	@Deprecated
	public List<EntityTemplate> getTemplatesByDomain(String domain,
	        Integer statusKey) {
		List<EntityTemplate> entityTemplateResultSet = null;
		Select selectTemplates = QueryBuilder.select().from(
		        ENTITY_TEMPLATE.toString());
		selectTemplates.where(QueryBuilder.eq(DOMAIN.toString(), domain));
		selectTemplates
		        .where(QueryBuilder.eq(STATUS_KEY.toString(), statusKey));
		selectTemplates.allowFiltering();
		// entityTemplateResultSet = this.cassandraOperations.select(
		// selectTemplates, EntityTemplate.class);
		return entityTemplateResultSet;
	}

	@Deprecated
	public List<EntityTemplate> getAllTemplatesByDomain(String domain,
	        Boolean includeNonShareable, Integer statusKey) {
		List<EntityTemplate> entityTemplateResultSet = null;
		Select selectTemplates = QueryBuilder.select().from(
		        ENTITY_TEMPLATE.toString());
		selectTemplates.where(QueryBuilder.eq(DOMAIN.toString(), domain));
		selectTemplates
		        .where(QueryBuilder.eq(STATUS_KEY.toString(), statusKey));
		if (!includeNonShareable) {
			selectTemplates.where(QueryBuilder.eq(
			        EMDataFields.IS_SHARABLE.getFieldName(),
			        Boolean.TRUE.booleanValue()));
		}
		selectTemplates.allowFiltering();
		// entityTemplateResultSet = this.cassandraOperations.select(
		// selectTemplates, EntityTemplate.class);
		return entityTemplateResultSet;
	}

	@Deprecated
	public List<EntityTemplate> getTemplateByDomainAndType(String domain,
	        String globalEntityType, Integer statusKey) {
		List<EntityTemplate> entityTemplateResultSet = null;
		Select selectTemplates = QueryBuilder.select().from(
		        ENTITY_TEMPLATE.toString());
		selectTemplates.where(QueryBuilder.eq(DOMAIN.toString(), domain));
		selectTemplates.where(QueryBuilder.eq(
		        PLATFORM_ENTITY_TYPE.getFieldName(), globalEntityType));
		selectTemplates
		        .where(QueryBuilder.lte(STATUS_KEY.toString(), statusKey));
		selectTemplates.allowFiltering();
		// entityTemplateResultSet = this.cassandraOperations.select(
		// selectTemplates, EntityTemplate.class);
		return entityTemplateResultSet;
	}

	@Deprecated
	public List<EntityTemplate> getTemplateByDomainAndTypeAndStatus(
	        String domain, String globalEntityType, Integer statusKey) {
		List<EntityTemplate> entityTemplateResultSet = null;
		Select selectTemplates = QueryBuilder.select().from(
		        ENTITY_TEMPLATE.toString());
		selectTemplates.where(QueryBuilder.eq(DOMAIN.toString(), domain));
		selectTemplates.where(QueryBuilder.eq(
		        PLATFORM_ENTITY_TYPE.getFieldName(), globalEntityType));
		selectTemplates
		        .where(QueryBuilder.eq(STATUS_KEY.toString(), statusKey));
		selectTemplates.allowFiltering();
		// entityTemplateResultSet = this.cassandraOperations.select(
		// selectTemplates, EntityTemplate.class);
		return entityTemplateResultSet;
	}

	@Deprecated
	public void unAllocateTemplate(List<UUID> entityTemplateIds,
	        Integer deletedStatusKey) {
		Update updateStatus = QueryBuilder.update(ENTITY_TEMPLATE.toString());
		// updateStatus.where(QueryBuilder.in(UUID.toString(),
		// entityTemplateIds.toArray()));
		updateStatus.with(QueryBuilder.set(STATUS_KEY.toString(),
		        deletedStatusKey));
		// this.cassandraOperations.execute(updateStatus);

	}

	public Boolean isValidField(String domain, String platformEntityType,
	        Integer statusKey, String fieldName, String templateName) {

		String cqlStatement = "SELECT status_name from entity_template where domain =  '"
		        + domain
		        + "' and platform_entity_type='"
		        + platformEntityType
		        + "' and entity_template_name ='"
		        + templateName
		        + "' and status_key < "
		        + statusKey
		        + " and field_validation CONTAINS  KEY '"
		        + fieldName
		        + "' ALLOW FILTERING;";

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
			String string = null;
			try {
				string = one.getString(0);
				// ObjectMapper mapper = new ObjectMapper();
				// result = mapper.readValue(string, EntityTemplate.class);

			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			if (string != null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Deprecated
	public List<EntityTemplate> getGlobalEntityTypesByDomain(String domain,
	        Integer statusKey) {
		List<EntityTemplate> entityTemplateResultSet = null;
		Select selectTemplates = QueryBuilder.select()
		        .column(EMDataFields.PLATFORM_ENTITY_TYPE.getFieldName())
		        .from(ENTITY_TEMPLATE.toString());
		selectTemplates.where(QueryBuilder.eq(DOMAIN.toString(), domain));
		selectTemplates
		        .where(QueryBuilder.eq(STATUS_KEY.toString(), statusKey));
		selectTemplates.allowFiltering();
		// entityTemplateResultSet = this.cassandraOperations.select(
		// selectTemplates, EntityTemplate.class);
		return entityTemplateResultSet;
	}

	/**
	 * Responsible for fetching the Global Template by Template Name and status
	 * key
	 * 
	 * @param globalEntityName
	 *            : GlobalEntityName
	 * @return entityType
	 */
	public String getPlatformEntityType(String domain, String templateName,
	        Integer statusKey) {

		String cqlStatement = "select platform_entity_type  from entity_template where domain = '"
		        + domain
		        + "' and status_key < "
		        + statusKey
		        + " and entity_template_name = '"
		        + templateName
		        + "' ALLOW FILTERING";

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
			String string = one.getString(0);
			if (string != null) {
				entityType = string;
			}
		}
		return entityType;
	}

	public List<EntityTemplate> getTemplateByDomainAndPlatformEntityType(
	        String domain, String globalEntityType) {

		String cqlStatement = "select json * from entity_template WHERE domain = '"
		        + domain
		        + "' and platform_entity_type ='"
		        + globalEntityType
		        + "'";

		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		List<EntityTemplate> entityTemplateResultSet = new ArrayList<EntityTemplate>();
		for (Row row : resultSet) {
			ObjectMapper mapper = new ObjectMapper();
			String string = row.getString(0);
			try {
				EntityTemplate entityTemplate = mapper.readValue(string,
				        EntityTemplate.class);
				entityTemplateResultSet.add(entityTemplate);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return entityTemplateResultSet;
	}

	/**
	 * 
	 * This method will return entityTemplate from MATERIALIZED VIEW of
	 * entity_template i.e from entity_template_parent
	 * 
	 * @param domainName
	 * @param platformEntityType
	 * @param parentTemplate
	 * @param statusKey
	 * @return List<EntityTemplate>
	 */
	public List<EntityTemplate> findAllTemplateByParentTemplate(
	        String domainName, String platformEntityType,
	        String parentTemplate, Integer statusKey) {

		String cqlStatement = "select json * from entity_template_parent WHERE parent_template = '"
		        + parentTemplate
		        + "' and domain = '"
		        + domainName
		        + "' and platform_entity_type ='"
		        + platformEntityType
		        + "' and status_key < " + statusKey + "";

		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);

		if (resultSet == null) {
			return Collections.emptyList();
		}
		List<EntityTemplate> entityTemplateResultSet = new ArrayList<EntityTemplate>();;
		ObjectMapper mapper = new ObjectMapper();
		for (Row row : resultSet) {
			String string = row.getString(0);
			try {
				EntityTemplate entityTemplate = mapper.readValue(string,
				        EntityTemplate.class);
				entityTemplateResultSet.add(entityTemplate);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return entityTemplateResultSet;
	}

	public void updateStatusBatch(EntityTemplate entityTemplate,
	        Integer existingStatusKey) {
		BatchStatement updateStatusBatch = new BatchStatement();
		Session session = cassandraDBUtil.getSession();

		BoundStatement insertQuery = createInsertTemplateQuery(entityTemplate,
		        session);

		PreparedStatement deleteStatement = session
		        .prepare("Delete from entity_template where domain=? and platform_entity_type=? and status_key=? and entity_template_id=? ");

		BoundStatement deleteQuery = deleteStatement.bind(
		        entityTemplate.getDomain(),
		        entityTemplate.getPlatformEntityType(), existingStatusKey,
		        entityTemplate.getEntityTemplateId());

		updateStatusBatch.add(deleteQuery);
		updateStatusBatch.add(insertQuery);
		session.execute(updateStatusBatch);
	}

	private BoundStatement createInsertTemplateQuery(
	        EntityTemplate entityTemplate, Session session) {

		PreparedStatement insertStatement = session
		        .prepare("INSERT INTO entity_template (domain,platform_entity_type,status_key,entity_template_id,access_scope,domain_type,entity_template_name,"
		                + "field_validation,html_page,identifier_field,is_modifiable,is_sharable,parent_template,platform_entity_template_name,status_name) "
		                + " VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)");

		BoundStatement bind = insertStatement.bind(entityTemplate.getDomain(),
		        entityTemplate.getPlatformEntityType(),
		        entityTemplate.getStatusKey(),
		        entityTemplate.getEntityTemplateId(),
		        entityTemplate.getAccessScope(),
		        entityTemplate.getDomainType(),
		        entityTemplate.getEntityTemplateName(),
		        entityTemplate.getFieldValidation(),
		        entityTemplate.getHtmlPage(),
		        entityTemplate.getIdentifierField(),
		        entityTemplate.getIsModifiable(),
		        entityTemplate.getIsSharable(),
		        entityTemplate.getParentTemplate(),
		        entityTemplate.getPlatformEntityTemplateName(),
		        entityTemplate.getStatusName());
		return bind;
	}

}

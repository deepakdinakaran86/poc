/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLS and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alpine.serviceImpl.repository;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.pcs.alpine.model.Entity;
import com.pcs.alpine.model.udt.FieldMap;
import com.pcs.alpine.repo.utils.CassandraDBUtil;
import com.pcs.alpine.repo.utils.EntityConversion;
import com.pcs.alpine.services.enums.EMDataFields;

/**
 * EntityRepository
 * 
 * @description Responsible for providing the 'C R U D' operations for an entity
 * @author Daniela (PCSEG191)
 * @author Sumaya Baig (PCSEG291)
 * @date 12 Aug 2014
 * @since galaxy-1.0.0
 */
@Repository("entityRepository")
public class EntityRepository {

	@Autowired
	private CassandraDBUtil cassandraDBUtil;

	@Autowired
	private EntityConversion entityConversion;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EntityRepository.class);

	/**
	 * Repository Method for fetching an entity by its identifier
	 * 
	 * @param identifier
	 * @param statusKey
	 * @return Entity
	 */
	@Deprecated
	public Entity getEntityDetails(FieldMap identifier, Integer statusKey) {

		Session session = cassandraDBUtil.getSession();
		String cqlStatement = "SELECT * from entity where identifier={key:'"
		        + identifier.getKey() + "', value:'" + identifier.getValue()
		        + "status_key < " + statusKey + "' allow filtering;";
		ResultSet rs = session.execute(cqlStatement);
		Entity entity = entityConversion.getEntity(rs);

		if (rs == null) {
			return null;
		}
		return entity;
	}

	/**
	 * Repository Method for fetching entities of a domain filtered with
	 * globalEntityType
	 * 
	 * @param domain
	 * @param globalEntityType
	 * @return
	 */
	@Deprecated
	public List<Entity> getEntities(String domain, String globalEntityType) {

		Session session = cassandraDBUtil.getSession();
		PreparedStatement selectStatement = session
		        .prepare("SELECT * FROM entity where domain='?' and  global_entity_type ='?' allow filtering;");
		BoundStatement bind = selectStatement.bind(domain, globalEntityType);
		ResultSet rs = session.execute(bind);
		if (rs == null) {
			return Collections.emptyList();
		}
		return entityConversion.getEntities(rs);
	}

	/**
	 * Responsible for fetching entities with domain,entityType and StatusKey
	 * 
	 * @param domain
	 * @param entityType
	 * @param statusKey
	 * @return
	 */
	public List<Entity> getAllEntities(String domain, String entityType,
	        String templateName, Integer statusKey) {

		String cqlStatement = "SELECT JSON * FROM entity where domain=  '"
		        + domain + "' and entity_template_name= '" + templateName
		        + "' " + " and platform_entity_type= '" + entityType + "' "
		        + " and status_key < " + statusKey;

		Session session = cassandraDBUtil.getSession();
		ResultSet rs = session.execute(cqlStatement);
		if (rs == null) {
			return Collections.emptyList();
		}

		List<Entity> entity = entityConversion.getEntities(rs);
		return entity;
	}

	/**
	 * Responsible for fetching entities with supplied
	 * fieldMap,entityType,domain and statusKey
	 * 
	 * @param fieldMap
	 * @param entityType
	 * @param domain
	 * @param statusKey
	 * @return
	 */
	public List<Entity> getEntitiesByField(FieldMap fieldMap,
	        String entityType, String domain, Integer statusKey,
	        String templateName) {
		List<FieldMap> fields = new ArrayList<>();
		fields.add(fieldMap);
		return getEntitiesByFields(fields, domain, entityType, statusKey,
		        templateName);
	}

	/**
	 * Responsible for fetching entityIds with supplied
	 * fieldMap,entityType,domain and statusKey
	 * 
	 * @param fieldMap
	 * @param entityType
	 * @param domain
	 * @param statusKey
	 * @return
	 */
	public List<String> getEntityIdsByField(FieldMap fieldMap,
	        String entityType, String domain, Integer statusKey) {
		List<FieldMap> fields = new ArrayList<>();
		fields.add(fieldMap);
		return getEntityIdsByFields(fields, domain, entityType, statusKey, null);
	}

	/**
	 * Responsible for fetching entities with supplied
	 * fieldMaps,entityType,domain and statusKey
	 * 
	 * @param fields
	 * @param domain
	 * @param entityType
	 * @param statusKey
	 * @return
	 */
	public List<Entity> getEntitiesByFields(List<FieldMap> fields,
	        String domain, String entityType, Integer statusKey,
	        String entityTemplateName) {

		Session session = cassandraDBUtil.getSession();
		List<Row> rs = queryEntitiesWithFields(session, fields, domain,
		        entityType, statusKey, entityTemplateName);
		if (rs == null || rs.isEmpty()) {
			return Collections.emptyList();
		}
		entityConversion = new EntityConversion();
		List<Entity> entity = entityConversion.getEntities(rs);
		return entity;
	}

	/**
	 * Responsible for fetching entityIds with supplied
	 * fieldMaps,entityType,domain and statusKey
	 * 
	 * @param fields
	 * @param domain
	 * @param entityType
	 * @param statusKey
	 * @return
	 */
	public List<String> getEntityIdsByFields(List<FieldMap> fields,
	        String domain, String entityType, Integer statusKey,
	        String entityTemplateName) {
		Session session = cassandraDBUtil.getSession();
		List<Row> rs = queryEntitiesWithFields(session, fields, domain,
		        entityType, statusKey, entityTemplateName);
		if (rs == null) {
			return Collections.emptyList();
		}
		List<String> entity = getEntityIds(rs);
		return entity;
	}

	@Deprecated
	public List<String> getEntityIds(List<Row> rs) {
		List<String> entities = new ArrayList<String>();
		for (Row row : rs) {
			entities.add(row.getUUID(EMDataFields.ENTITY_ID.getFieldName())
			        .toString());
		}
		return entities;
	}

	/**
	 * Responsible for inserting an entity Row
	 * 
	 * @param entity
	 */
	public void insertEntity(Entity entity) {
		Mapper<Entity> mapper = new MappingManager(cassandraDBUtil.getSession())
		        .mapper(Entity.class);
		mapper.save(entity);
	}

	/**
	 * @param entity
	 * @param session
	 * @return
	 */
	private BoundStatement createInsertEntityBatchQuery(Entity entity,
	        Session session) {

		PreparedStatement insertStatement = session
		        .prepare("INSERT INTO entity(domain,entity_template_name,field_values,"
		                + "platform_entity_type,status_key,status_name,dataprovider,identifier,tree,entity_id) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)");

		BoundStatement bind = insertStatement.bind(entity.getDomain(),
		        entity.getEntityTemplateName(), entity.getFieldValues(),
		        entity.getPlatformEntityType(), entity.getStatusKey(),
		        entity.getStatusName(), entity.getDataprovider(),
		        entity.getIdentifier(), entity.getTree(), entity.getEntityId());
		return bind;
	}

	/**
	 * Repository Method for updating field_values,status_key and status_name of
	 * a single entity
	 * 
	 * 
	 * @param entity
	 */
	public void updateEntity(Entity entity) {
		Session session = cassandraDBUtil.getSession();

		// Status name will not be updated as status key is part of the primary
		// key
		PreparedStatement updateStatement = session
		        .prepare("UPDATE entity SET field_values =?,dataprovider=?,tree=? where domain=? and entity_template_name=? and platform_entity_type=?"
		                + "and status_key=? and entity_id=?");

		BoundStatement bind = updateStatement.bind(entity.getFieldValues(),
		        entity.getDataprovider(), entity.getTree(), entity.getDomain(),
		        entity.getEntityTemplateName(), entity.getPlatformEntityType(),
		        entity.getStatusKey(), entity.getEntityId());
		session.execute(bind);
	}

	private List<Row> queryEntitiesWithFields(Session session,
	        List<FieldMap> fields, String domain, String entityType,
	        Integer statusKey, String entityTemplateName) {
		String fieldValue = "";
		for (FieldMap fieldMap : fields) {
			if (isNotBlank(fieldValue)) {
				fieldValue = fieldValue + " and ";
			}
			fieldValue = fieldValue + " field_values CONTAINS {key:'"
			        + fieldMap.getKey() + "' , value:'"
			        + fieldMap.getValue().replace("'", "''") + "' }";
		}
		String domainNameFilter = "";
		if (isNotBlank(domain)) {
			domainNameFilter = " and domain='" + domain + "'";
		}

		String entityTypeFilter = "";
		if (isNotBlank(entityType)) {
			entityTypeFilter = " and platform_entity_type='" + entityType + "'";
		}

		String entityTemplateFilter = "";
		if (isNotBlank(entityTemplateName)) {
			entityTemplateFilter = "and entity_template_name='"
			        + entityTemplateName + "'";
		}

		String cqlStatement = "SELECT JSON * from entity where status_key<"
		        + statusKey + " and " + fieldValue + domainNameFilter
		        + entityTypeFilter + entityTemplateFilter + " ALLOW FILTERING;";

		ResultSet resultSet = session.execute(cqlStatement);
		return resultSet.all();

	}

	public List<Entity> getEntitiesByField(FieldMap field,
	        String platformEntityType, Integer statusKey) {

		Session session = cassandraDBUtil.getSession();
		List<Row> rs = getEntityByField(session, field, platformEntityType,
		        statusKey);
		List<Entity> entity = entityConversion.getEntities(rs);
		return entity;
	}

	// Not currect
	private List<Row> getEntityByField(Session session, FieldMap fields,
	        String platformEntityType, Integer statusKey) {

		String cqlStatement = "SELECT JSON * from entity where platform_entity_type='"
		        + platformEntityType
		        + "' and status_key <"
		        + statusKey
		        + " and field_values CONTAINS  {key:'"
		        + fields.getKey()
		        + "',value:'" + fields.getValue() + "'}  ALLOW FILTERING;";
		ResultSet resultSet = session.execute(cqlStatement);
		return resultSet.all();

	}

	/**
	 * This method is responsible for returning a single entity with unique
	 * identifiers as per the entity type
	 * 
	 * Assuming identifier field and entity type are mandatory among all the
	 * global entity types,and this will expect a statusKey which can be used to
	 * filter the result with status
	 * 
	 * @param domain
	 * @param entityTemplate
	 * @param globalEntity
	 * @param identifier
	 * @param statusKey
	 * @return
	 */
	public Entity getEntityByIdentifiers(String domain, String entityTemplate,
	        String globalEntity, FieldMap identifier, Integer statusKey) {
		Session session = cassandraDBUtil.getSession();

		String domainCondition = "";
		if (StringUtils.isNotBlank(domain)) {
			domainCondition = " domain = '" + domain + "'";
		}

		String templateCondition = "";
		if (StringUtils.isNotBlank(domain)) {
			templateCondition = " and entity_template_name = '"
			        + entityTemplate + "'";
		}

		String cqlStatement = "SELECT JSON * from entity where "
		        + domainCondition + templateCondition
		        + " and platform_entity_type='" + globalEntity
		        + "' and status_key < " + statusKey
		        + " and identifier = {key: '" + identifier.getKey()
		        + "', value: '" + identifier.getValue() + "'}";

		ResultSet rs = session.execute(cqlStatement);
		Entity entity = entityConversion.getEntity(rs);
		return entity;
	}

	public Integer getEntitiesCount(String domain, String entityType,
	        Integer statusKey, String entityTemplate) {
		String cqlStatement = "SELECT JSON domain FROM entity where domain = '"
		        + domain + "' and entity_template_name='" + entityTemplate
		        + "' and platform_entity_type = '" + entityType
		        + "' and status_key<" + statusKey;
		ResultSet rs = cassandraDBUtil.getSession().execute(cqlStatement);
		return rs.getAvailableWithoutFetching();
	}

	public List<Entity> getEntities(String domain, String entityType,
	        Integer statusKey, String entityTemplate) {

		String cqlStatement = "SELECT JSON * FROM entity where domain = '"
		        + domain + "' and entity_template_name='" + entityTemplate
		        + "' and platform_entity_type = '" + entityType
		        + "' and status_key<" + statusKey;

		ResultSet rs = cassandraDBUtil.getSession().execute(cqlStatement);
		if (rs == null) {
			return Collections.emptyList();
		}
		return entityConversion.getEntities(rs);
	}

	public Integer getEntitiesCountByStatus(String domain, String entityType,
	        Integer statusKey, String entityTemplate) {
		String cqlStatement = "SELECT JSON * FROM entity where domain = '"
		        + domain + "' and entity_template_name='" + entityTemplate
		        + "' and platform_entity_type = '" + entityType
		        + "' and status_key=" + statusKey;

		ResultSet rs = cassandraDBUtil.getSession().execute(cqlStatement);
		return rs.getAvailableWithoutFetching();
	}

	private List<Row> queryEntitiesWithFieldsAndStatus(Session session,
	        List<FieldMap> fields, String domain, String entityType,
	        Integer statusKey, String entityTemplateName) {
		String fieldValue = "";
		for (FieldMap fieldMap : fields) {
			if (isNotBlank(fieldValue)) {
				fieldValue = fieldValue + " and ";
			}
			fieldValue = fieldValue + " field_values CONTAINS {key:'"
			        + fieldMap.getKey() + "' , value:'"
			        + fieldMap.getValue().replace("'", "''") + "' }";
		}

		String domainNameFilter = "";
		if (isNotBlank(domain)) {
			domainNameFilter = "and domain='" + domain + "'";
		}

		String entityTypeFilter = "";
		if (isNotBlank(entityType)) {
			entityTypeFilter = "and platform_entity_type='" + entityType + "'";
		}

		String entityTemplateFilter = "";
		if (isNotBlank(entityTemplateName)) {
			entityTemplateFilter = "and entity_template_name='"
			        + entityTemplateName + "'";
		}

		String cqlStatement = "SELECT JSON * from entity where  status_key ="
		        + statusKey + " and " + fieldValue + domainNameFilter
		        + entityTypeFilter + entityTemplateFilter + " ALLOW FILTERING;";

		ResultSet resultSet = session.execute(cqlStatement);
		return resultSet.all();

	}

	/**
	 * Responsible for fetching entities with supplied
	 * fieldMaps,entityType,domain and statusKey
	 * 
	 * @param fields
	 * @param domain
	 * @param entityType
	 * @param statusKey
	 * @return
	 */
	public List<Entity> getEntitiesByFieldsAndStatus(List<FieldMap> fields,
	        String domain, String entityType, Integer statusKey,
	        String entityTemplateName) {

		Session session = cassandraDBUtil.getSession();
		List<Row> rs = queryEntitiesWithFieldsAndStatus(session, fields,
		        domain, entityType, statusKey, entityTemplateName);
		if (rs == null) {
			return Collections.emptyList();
		}
		List<Entity> entity = entityConversion.getEntities(rs);
		return entity;
	}

	public Integer getEntitiesCountByFields(String domain, String entityType,
	        Integer statusKey, String entityTemplateName, List<FieldMap> fields) {
		Session session = cassandraDBUtil.getSession();
		List<Row> rs = queryEntitiesWithFieldsAndStatus(session, fields,
		        domain, entityType, statusKey, entityTemplateName);
		return rs.size();
	}

	/**
	 * This method is used to change the status for a list of entities
	 * 
	 * @param List
	 *            <Entity> entityList
	 */
	public void insertEntities(List<Entity> entityList) {
		BatchStatement insertBatch = new BatchStatement();
		Session session = cassandraDBUtil.getSession();

		PreparedStatement insertStatement = session
		        .prepare("INSERT INTO entity(domain,entity_template_name,field_values,"
		                + "platform_entity_type,status_key,status_name,dataprovider,identifier,tree,entity_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)");

		for (Entity entity : entityList) {
			BoundStatement bind = insertStatement.bind(entity.getDomain(),
			        entity.getEntityTemplateName(), entity.getFieldValues(),
			        entity.getPlatformEntityType(), entity.getStatusKey(),
			        entity.getStatusName(), entity.getDataprovider(),
			        entity.getIdentifier(), entity.getTree(),
			        entity.getEntityId());
			insertBatch.add(bind);
		}
		session.executeAsync(insertBatch);
	}

	public void updateStatusBatch(Entity entity, Integer existingStatusKey) {
		BatchStatement updateStatusBatch = new BatchStatement();
		Session session = cassandraDBUtil.getSession();

		BoundStatement insertQuery = createInsertEntityBatchQuery(entity,
		        session);
		updateStatusBatch.add(insertQuery);

		PreparedStatement deleteStatement = session
		        .prepare("Delete from entity where domain=? and entity_template_name=? and platform_entity_type=? and status_key=? and entity_id=? ");

		BoundStatement deleteQuery = deleteStatement.bind(entity.getDomain(),
		        entity.getEntityTemplateName(), entity.getPlatformEntityType(),
		        existingStatusKey, entity.getEntityId());

		updateStatusBatch.add(deleteQuery);
		session.execute(updateStatusBatch);
	}

	public Entity getEntityByIdentifier(String domain, String entityTemplate,
	        String globalEntity, FieldMap identifier) {
		Session session = cassandraDBUtil.getSession();

		String domainCondition = "";
		if (StringUtils.isNotBlank(domain)) {
			domainCondition = " domain = '" + domain + "'";
		}

		String templateCondition = "";
		if (StringUtils.isNotBlank(domain)) {
			templateCondition = " and entity_template_name = '"
			        + entityTemplate + "'";
		}

		String cqlStatement = "SELECT JSON * from entity where "
		        + domainCondition + templateCondition
		        + " and platform_entity_type='" + globalEntity
		        + "' and identifier = {key: '" + identifier.getKey()
		        + "', value: '" + identifier.getValue() + "'}";

		ResultSet rs = session.execute(cqlStatement);
		Entity entity = entityConversion.getEntity(rs);
		return entity;
	}

	public Integer getEntitiesCountByFieldsNoStatus(String domain,
	        String entityType, Integer statusKey, String entityTemplateName,
	        List<FieldMap> fields) {
		Session session = cassandraDBUtil.getSession();
		List<Row> rs = queryEntitiesWithFields(session, fields, domain,
		        entityType, statusKey, entityTemplateName);
		return rs.size();
	}

	public static void main(String[] args) {

		// EntityRepository rep = new EntityRepository();
		// FieldMap field = new FieldMap();
		// field.setKey("domain");
		// field.setValue("jll.galaxy");
		//
		// List<FieldMap> list = new ArrayList<FieldMap>();
		// list.add(field);
		//
		// // rep.getEntitiesCountByFields("pcs.galaxy", "TENANT", 3,
		// // "DefaultTenant", list);
		//
		// String ent =
		// "{\"domain\": \"pcs.galaxy1\", \"entity_template_name\": \"DefaultTenant\", \"platform_entity_type\": \"TENANT\", \"status_key\": 0, \"entity_id\": \"jll\", \"dataprovider\": [{\"key\": \"contactEmail\", \"value\": \"danielac@pacificcontrols.net\"}, {\"key\": \"domain\", \"value\": \"jll.galaxy\"}, {\"key\": \"firstName\", \"value\": \"jll\"}, {\"key\": \"lastName\", \"value\": \"jll\"}, {\"key\": \"tenantId\", \"value\": \"jll\"}, {\"key\": \"tenantName\", \"value\": \"jll\"}], \"domain_type\": null, \"field_values\": [{\"key\": \"contactEmail\", \"value\": \"danielac@pacificcontrols.net\"}, {\"key\": \"domain\", \"value\": \"jll.galaxy\"}, {\"key\": \"firstName\", \"value\": \"jll\"}, {\"key\": \"image\", \"value\": null}, {\"key\": \"lastName\", \"value\": \"jll\"}, {\"key\": \"tenantId\", \"value\": \"jll\"}, {\"key\": \"tenantName\", \"value\": \"jll\"}], \"identifier\": {\"key\": \"tenantId\", \"value\": \"jll1\"}, \"status_name\": \"ACTIVE\", \"tree\": {\"key\": \"tenantName\", \"value\": \"jll\"}}";
		// ObjectMapper mapper = new ObjectMapper();
		// try {
		// Entity entity = mapper.readValue(ent, Entity.class);
		// rep.insertEntity(entity);
		// } catch (Exception e) {
		// System.out.println(e);
		// }

		String name = "Driver's Demand Engine - Percent Torque:n0";
		System.out.println(name.replace("'", "''"));
	}

}

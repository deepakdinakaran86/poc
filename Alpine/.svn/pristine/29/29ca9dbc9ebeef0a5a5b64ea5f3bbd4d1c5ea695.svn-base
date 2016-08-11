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
package com.pcs.alpine.serviceImpl.repository.titan;

import static com.pcs.alpine.serviceImpl.repository.utils.GremlinConstants.CHILD;
import static com.pcs.alpine.serviceImpl.repository.utils.GremlinConstants.TENANT;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.model.DistributionEntity;
import com.pcs.alpine.model.HierarchyEntity;
import com.pcs.alpine.model.HierarchyEntityTemplate;
import com.pcs.alpine.model.TaggedEntity;
import com.pcs.alpine.serviceImpl.repository.utils.TitanSessionManager;
import com.pcs.alpine.services.dto.AttachHierarchyDTO;
import com.pcs.alpine.services.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.repository.HierarchyRepository;

/**
 * Implementation class for {@link HierarchyRepository}
 * 
 * @description Service Implementation for hierarchy services
 * @author Greeshma (PCSEG323)
 * @date 5 JAN 2016
 * @since alpine-1.0.0
 */
@Repository("hierarchyTitan")
public class HierarchyRepositoryImpl implements HierarchyRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(HierarchyRepositoryImpl.class);

	@Autowired
	private TitanSessionManager titanSessionManager;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String ADDITIONAL_CONDITIONS = "{additionalConditions}";
	private static final String CHILD_TEMPLATE_NAME = "{childTemplateName}";
	private static final String CHILD_ENTITY_TYPE = "{childEntityType}";
	private static final String STATUS = "{status}";

	private static final String CHILED_HIERARCHY = "childList = [];  g.V().hasLabel(entityType)"
	        + ".has('myDomain',myDomain)"
	        + ".each{g.V(it.id()).repeat(out(child)).until(outE().count().is(0)).as('e').path().each{it.objects().each{g.V(it.id()).has('myDomain',within({children}))"
	        + ".values('myDomain').each{childList.addAll(it)}} }}; return childList.unique()";
	private static final String PARENT_HIERARCHY = "parentList = [];  g.V().hasLabel(entityType)"
	        + ".has('myDomain',myDomain)"
	        + ".each{g.V(it.id()).repeat(__.in(child)).until(inE().count().is(0)).as('e').path().each{it.objects().each{g.V(it.id()).has('myDomain',within({parentDomains}))"
	        + ".values('myDomain').each{parentList.addAll(it)}} }}; return parentList.unique()";
	private static final String TENANT_NODE = "g.V().hasLabel(vertexLabel).has('myDomain',domain).valueMap()";
	private static final String GET_TENANT = "g.V().hasLabel(vertexLabel).has('identifierKey',identifierKey).has('identifierValue',identifierValue).has('templateName',templateName).has('domain',domain).values('myDomain')";
	private static final String GET_TENANTS = "tenants = tenantMap; myDomains = [];"
	        + "tenants.each{value -> myDomains.add(g.V().hasLabel(vertexLabel).has('identifierKey',value.get('identifierKey'))"
	        + ".has('identifierValue',value.get('identifierValue')).has('templateName',value.get('templateName'))"
	        + ".has('domain',value.get('domain')).values('myDomain').fold().next())};return myDomains;";
	private static final String GET_CHILDREN = " childList = [];  g.V().hasLabel(entityType).has('identifierKey',identifierKey).has('identifierValue',identifierValue)"
	        + ".has('domain',domain).has('templateName',templateName)"
	        + ".each{g.V(it.id()).repeat(out(child)).until(outE().count().is(0)).as('e').path().each{it.objects().each{g.V(it.id())."
	        + "{additionalConditions}"
	        + "valueMap(true).each{childList.addAll(it)}} }}; return childList.unique()";
	private static final String GET_CHILDREN_COUNT = "childList = [];  g.V().hasLabel(entityType).has('identifierKey',identifierKey).has('identifierValue',identifierValue)"
	        + ".has('domain',domain).has('templateName',templateName)"
	        + ".each{g.V(it.id()).repeat(out(child)).until(outE().count().is(0)).as('e').path().each{it.objects().each{g.V(it.id())."
	        + "{additionalConditions}"
	        + "valueMap(true).each{childList.addAll(it)}} }}; return childList.unique().size() - 1";
	private static final String CHILD_TEMPLATE_CONDITION = "has('templateName',childTemplateName).";
	private static final String CHILD_ENTITY_TYPE_CONDITION = "hasLabel(childEntityType).";
	private static final String GET_NODES_STATUS_COUNT = "g.V().has('myDomain',myDomain).out(child).hasLabel(entityType).has('templateName',templateName).has('status',status).count()";
	private static final String DISTRIBUTION_COUNT = " countObjList = [];g.V().hasLabel(entityType).has('domain',domain).each{ "
	        + "entityList =[]; "
	        + "countObj = [:]; "
	        + "values = []; "
	        + "g.V(it.id()).repeat(out(child)).times(1).each{ "
	        + "values.add(it.id()); "
	        + "g.V(it.id())."
	        + "{additionalConditions}"
	        + "valueMap(true).each{entityList.add(it)}; "
	        + "g.V(it.id()).repeat(out(child)).until(outE(child).count().is(0)).path().by(id()).each{ "
	        + "it.objects().each{ "
	        + "if(!values.contains(it)){ "
	        + "g.V(it)."
	        + "{additionalConditions}"
	        + "valueMap(true).each{entityList.add(it)}; "
	        + "values.add(it); } } } }; "
	        + "countObj.put(g.V(it.id()).values('identifierValue').next(),entityList.size()); "
	        + "countObjList.add(countObj) };" + "countObjList;";
	private static final String DISTRIBUTION_ENTITIES = "entityList =[]; "
	        + "countObjList = [];"
	        + "g.V().hasLabel(entityType).has('domain',domain).each{"
	        + "values = []; g.V(it.id()).repeat(out(child)).times(1).each{ "
	        + "values.add(it.id()); "
	        + "g.V(it.id())."
	        + "{additionalConditions}"
	        + "valueMap(true).each{entityList.add(it)}; "
	        + "g.V(it.id()).repeat(out(child)).until(outE(child).count().is(0)).path().by(id()).each{ "
	        + "it.objects().each{ "
	        + "if(!values.contains(it)){ "
	        + "g.V(it)."
	        + "{additionalConditions}"
	        + "valueMap(true).each{entityList.add(it)}; values.add(it); } } } }; }"
	        + ";entityList;";
	private static final String DISTRIBUTION_STATUS_CONDITION = "has('status',childStatus). ";
	private static final String GET_NODE_BY_DOMAIN = " g.V().has('myDomain',parentDomain).out(child).has('myDomain',myDomain).values('myDomain')";
	private static final String ATTACH_PARENTS = " hierarchy = hierarchyEntityMap; if(g.V().hasLabel(entityType).has('domain', hierarchy.actor.domain)"
	        + ".has('templateName',hierarchy.actor.templateName).has('identifierKey',hierarchy.actor.identifierKey)"
	        + ".has('identifierValue',hierarchy.actor.identifierValue).hasNext()){"
	        + "childEntity = g.V().hasLabel(entityType).has('domain', hierarchy.actor.domain).has('templateName',hierarchy.actor.templateName)"
	        + ".has('identifierKey',hierarchy.actor.identifierKey).has('identifierValue',hierarchy.actor.identifierValue).next();"
	        + "}"
	        + "else{"
	        + "childEntity = graph.addVertex(label,entityType);hierarchy.actor.each{key,prop -> if(key != 'globalEntityType'){childEntity.property (key , prop)"
	        + "}};"
	        + "};"
	        + "hierarchy.subject.each{value -> "
	        + "if(g.V().hasLabel(value.get('globalEntityType')).has('domain', value.get('domain')).has('templateName',value.get('templateName'))"
	        + ".has('identifierKey',value.get('identifierKey')).has('identifierValue',value.get('identifierValue')).hasNext()){"
	        + "parentEntity = g.V().hasLabel(value.get('globalEntityType')).has('domain', value.get('domain')).has('templateName',value.get('templateName'))"
	        + ".has('identifierKey',value.get('identifierKey')).has('identifierValue',value.get('identifierValue')).next();"
	        + "count = 0;"
	        + "g.V(parentEntity.id()).out(child).each{"
	        + "if(it.id() == childEntity.id() ){"
	        + "count = 1;}};"
	        + "if(count == 0){"
	        + "parentEntity.addEdge(child,childEntity);}}"
	        + "else{"
	        + "parentEntity = graph.addVertex(label,value.get('globalEntityType'));"
	        + "value.each{key,prop ->if(key != 'globalEntityType'){ parentEntity.property (key , prop)}};"
	        + "parentEntity.addEdge(child,childEntity);};"
	        + "g.V(childEntity.id()).property('status','ACTIVE');}";
	private static final String ATTACH_CHILDREN = " hierarchy = hierarchyEntityMap; if(g.V().hasLabel(entityType).has('domain', hierarchy.actor.domain)"
	        + ".has('templateName',hierarchy.actor.templateName).has('identifierKey',hierarchy.actor.identifierKey)"
	        + ".has('identifierValue',hierarchy.actor.identifierValue).hasNext()){"
	        + "parentEntity = g.V().hasLabel(entityType).has('domain', hierarchy.actor.domain).has('templateName',hierarchy.actor.templateName)"
	        + ".has('identifierKey',hierarchy.actor.identifierKey).has('identifierValue',hierarchy.actor.identifierValue).next();"
	        + "}"
	        + "else{"
	        + "parentEntity = graph.addVertex(label,entityType);hierarchy.actor.each{key,prop -> if(key != 'globalEntityType'){"
	        + "parentEntity.property (key , prop)"
	        + "}};"
	        + "};"
	        + "hierarchy.subject.each{value -> "
	        + "if(g.V().hasLabel(value.get('globalEntityType')).has('domain', value.get('domain')).has('templateName',value.get('templateName'))"
	        + ".has('identifierKey',value.get('identifierKey')).has('identifierValue',value.get('identifierValue')).hasNext()){"
	        + "childEntity = g.V().hasLabel(value.get('globalEntityType')).has('domain', value.get('domain')).has('templateName',value.get('templateName'))"
	        + ".has('identifierKey',value.get('identifierKey')).has('identifierValue',value.get('identifierValue')).next();"
	        + "count = 0;"
	        + "g.V(childEntity.id()).in(child).each{"
	        + "if(it.id() == parentEntity.id() ){"
	        + "count = 1;}};"
	        + "if(count == 0){"
	        + "parentEntity.addEdge(child,childEntity);}}"
	        + "else{"
	        + "childEntity = graph.addVertex(label,value.get('globalEntityType'));"
	        + "value.each{key,prop -> if(key != 'globalEntityType'){childEntity.property (key , prop)}};"
	        + "parentEntity.addEdge(child,childEntity);};"
	        + "g.V(childEntity.id()).property('status','ACTIVE');}";
	private static final String UPDATE_NODE = "g.V().hasLabel(entityType).has('identifierKey',identifierKey).has('identifierValue',identifierValue)."
	        + "has('templateName',templateName).has('domain',domain).property('status',entityStatus).valueMap(true)";
	private static final String INSERT_HIERARCHY = "hierarchy = hierarchyEntityMap; node = graph.addVertex(label, entityType); "
	        + "hierarchy.each{key,prop -> node.property(key , prop)};"
	        + "if(g.V().has('myDomain',actorMyDomain).hasNext()){"
	        + "actor = g.V().has('myDomain',actorMyDomain).next(); "
	        + "actor.addEdge(child,node)};" + "node.id();";
	private static final String GET_IMMEDIATE_CHILDREN = " g.V().hasLabel(entityType).has('identifierValue',identifierValue)"
	        + ".has('identifierKey',identifierKey).has('domain',domain)"
	        + ".has('templateName',templateName).out(child)."
	        + "{additionalConditions}" + "valueMap(true)";
	private static final String GET_IMMEDIATE_PARENTS = " g.V().hasLabel(entityType).has('identifierValue',identifierValue)"
	        + ".has('identifierKey',identifierKey).has('domain',domain)"
	        + ".has('templateName',templateName).in(child)."
	        + "{additionalConditions}" + "valueMap(true)";
	private static final String GET_CHILD_TENANT = "childList = []; "
	        + "g.V().hasLabel(entityType).has('myDomain',parentMyDomain).each{"
	        + "g.V(it.id()).repeat(out(child)).until(outE().count().is(0)).as('e').path().each{"
	        + "it.objects().each{g.V(it.id()).hasLabel(entityType).has('myDomain',childMyDomain).valueMap(true).each{"
	        + "childList.addAll(it)}} }}; " + "return childList.unique()";
	private static final String GET_NODE = "g.V().hasLabel(vertexLabel).has('identifierValue',identifierValue)"
	        + ".has('identifierKey',identifierKey).has('domain',domain)"
	        + ".has('templateName',templateName).valueMap()";
	private static final String GET_PARENTS = " parentList = [];  g.V().hasLabel(entityType).has('identifierKey',identifierKey).has('identifierValue',identifierValue)"
	        + ".has('domain',domain).has('templateName',templateName)"
	        + ".each{g.V(it.id()).repeat(__.in(child)).until(inE().count().is(0)).as('e').path().each{it.objects().each{g.V(it.id())."
	        + "{additionalConditions}"
	        + "valueMap(true).each{parentList.addAll(it)}} }}; return parentList.unique()";
	private static final String VALIDATE_TENANT = "g.V().hasLabel(entityType).has('myDomain',parentMyDomain).has('domain',childMyDomain).valueMap()";

	/**
	 * method for attaching multiple parents to a child
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 */
	@Override
	public void attachParents(HierarchyDTO hierarchy) {

		Map<String, Object> listOfMap = getHierarchyEntityMap(hierarchy);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityType", hierarchy.getActor().getPlatformEntity()
		        .getPlatformEntityType());
		params.put("child", CHILD);
		params.put("hierarchyEntityMap", listOfMap);
		try {
			Client client = titanSessionManager.getClient();
			client.submit(ATTACH_PARENTS, params);
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}

	}

	/**
	 * method for attaching multiple children to a parent
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 */
	@Override
	public void attachChildren(HierarchyDTO hierarchy) {

		Map<String, Object> listOfMap = getHierarchyEntityMap(hierarchy);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityType", hierarchy.getActor().getPlatformEntity()
		        .getPlatformEntityType());
		params.put("child", CHILD);
		params.put("hierarchyEntityMap", listOfMap);
		try {
			Client client = titanSessionManager.getClient();
			client.submit(ATTACH_CHILDREN, params);
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
	}

	/**
	 * method for attaching multiple parents to a child
	 * 
	 * @param HierarchyEntity
	 * @return {@link Integer}
	 */
	@Override
	public Integer insertTenantHierarchy(HierarchyEntity hierarchyEntity) {

		LOGGER.debug("<<--Entry insertTenantHierarchy-->>");
		Map<String, Object> listOfMap = getHierarchyEntityMap(hierarchyEntity);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityType", hierarchyEntity.getEntityType());
		params.put("actorMyDomain", hierarchyEntity.getActorTenantDomain());
		params.put("child", CHILD);
		params.put("hierarchyEntityMap", listOfMap);
		Result result = null;
		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(INSERT_HIERARCHY, params);
			result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return result.getInt();
	}

	/**
	 * method for get children hierarchy
	 * 
	 * @param parent
	 * @param Set
	 *            <String> children
	 * @return {@link HashSet<String>}
	 */
	@Override
	public HashSet<String> getChildrenOfEntity(String parent,
	        Set<String> children) {

		HashSet<String> childSet = new HashSet<>();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("entityType", TENANT);
		params.put("myDomain", parent);
		params.put("child", CHILD);

		List<String> list = new ArrayList<String>(children);
		String childDomains = Joiner.on(",").join(list);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(
			        CHILED_HIERARCHY.replace("{children}", childDomains),
			        params);
			List<Result> resultList = resultSet.all().get();
			if (resultList == null || CollectionUtils.isEmpty(resultList)) {
				return new HashSet<String>();
			}

			childSet = getSetFromResultList(resultList);

		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}

		return childSet;
	}

	/**
	 * method for get parent hierarchy
	 * 
	 * @param child
	 * @param Set
	 *            <String> parents
	 * @return {@link HashSet<String>}
	 */
	@Override
	public HashSet<String> getParentsOfEntity(String child, Set<String> parents) {
		HashSet<String> childSet = new HashSet<>();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("entityType", TENANT);
		params.put("myDomain", child);
		params.put("child", CHILD);

		List<String> list = new ArrayList<String>(parents);
		String parentDomains = Joiner.on(",").join(list);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(
			        PARENT_HIERARCHY.replace("{parentDomains}", parentDomains),
			        params);
			List<Result> resultList = resultSet.all().get();
			if (resultList == null || CollectionUtils.isEmpty(resultList)) {
				return new HashSet<String>();
			}

			childSet = getSetFromResultList(resultList);

		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}

		return childSet;
	}

	/**
	 * method for attaching multiple parents to a child
	 * 
	 * @param parentDomain
	 * @param myDomain
	 * @return {@link List<String>}
	 */
	@Override
	public List<String> getHierarchyByTenantDomain(String parentDomain,
	        String myDomain) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("parentDomain", parentDomain);
		params.put("myDomain", myDomain);
		params.put("child", CHILD);

		List<String> hierarchies = new ArrayList<String>();

		Client client = titanSessionManager.getClient();
		ResultSet resultSet = client.submit(GET_NODE_BY_DOMAIN, params);
		List<Result> resultList;
		try {
			resultList = resultSet.all().get();

			if (CollectionUtils.isEmpty(resultList)) {
				return Collections.emptyList();
			}
			hierarchies = getListFromResultList(resultList);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return hierarchies;
	}

	/**
	 * method for check whether particular tenant is exist or not
	 * 
	 * 
	 * @param domain
	 * @return {@link Boolean}
	 */
	@Override
	public boolean isTenantExist(String domain) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("vertexLabel", TENANT);
		params.put("domain", domain);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(TENANT_NODE, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return false;
			}

		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return true;
	}

	@Override
	public boolean isNodeExist(EntityDTO entity) {
		Map<String, Object> params = new HashMap<String, Object>();
		Boolean flag = true;
		params.put("vertexLabel", entity.getPlatformEntity()
		        .getPlatformEntityType());
		params.put("identifierKey", entity.getIdentifier().getKey());
		params.put("templateName", entity.getEntityTemplate()
		        .getEntityTemplateName());
		params.put("identifierValue", entity.getIdentifier().getValue());
		params.put("domain", entity.getDomain().getDomainName());

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(GET_NODE, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				flag = false;
			}

		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return flag;
	}

	/**
	 * method to get tenant domain name for particular entity
	 * 
	 * @param EntityDTO
	 * @return {@link String}
	 */

	@Override
	public String getTenantDomainName(EntityDTO entity) {
		String domain = "";
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("vertexLabel", entity.getPlatformEntity()
		        .getPlatformEntityType());
		params.put("identifierKey", entity.getIdentifier().getKey());
		params.put("templateName", entity.getEntityTemplate()
		        .getEntityTemplateName());
		params.put("identifierValue", entity.getIdentifier().getValue());
		params.put("domain", entity.getDomain().getDomainName());
		List<Result> resultList = null;

		Client client = titanSessionManager.getClient();
		ResultSet resultSet = client.submit(GET_TENANT, params);
		try {
			resultList = resultSet.all().get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		if (CollectionUtils.isEmpty(resultList)) {
			return domain;
		}
		if (resultList.size() > 1) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DUPLICATE_RECORDS);
		}
		domain = (String)resultList.get(0).getObject();
		return domain;

	}

	/**
	 * method to get tenant domain name from entity list
	 * 
	 * @param List
	 *            <EntityDTO> tenants
	 * @return {@link List}
	 */
	@Override
	public List<String> getTenantsDomainNames(List<EntityDTO> tenants) {

		List<String> myDomains = new ArrayList<String>();
		List<Map<String, Object>> listOfMap = getEntityListMap(tenants);
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("vertexLabel", TENANT);
		params.put("tenantMap", listOfMap);

		List<Result> resultList = null;
		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(GET_TENANTS, params);
			resultList = resultSet.all().get();
			if (CollectionUtils.isEmpty(resultList)) {
				return null;
			}

			for (Result result : resultList) {
				if (CollectionUtils.isNotEmpty(result.get(ArrayList.class))) {
					myDomains.add((String)result.get(ArrayList.class).get(0));
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return myDomains;
	}

	/**
	 * method to get children
	 * 
	 * @param HierarchyEntity
	 * @param searchTemplate
	 * @param searchEntityType
	 * @return {@link List<HierarchyEntity>}
	 */

	@Override
	public List<HierarchyEntity> getChildren(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType) {

		String additionalConditions = "";
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		Map<String, Object> params = new HashMap<String, Object>();
		additionalConditions = validateSearchFields(searchTemplate,
		        searchEntityType, additionalConditions,
		        hierarchyEntity.getStatus());
		List<Result> resultList = null;
		params.put("entityType", hierarchyEntity.getEntityType());
		params.put("identifierKey", hierarchyEntity.getIdentifierKey());
		params.put("identifierValue", hierarchyEntity.getIdentifierValue());
		params.put("domain", hierarchyEntity.getDomain());
		params.put("templateName", hierarchyEntity.getTemplateName());
		params.put("child", CHILD);
		params.put("childTemplateName", searchTemplate);
		params.put("childEntityType", searchEntityType);
		params.put("childStatus", hierarchyEntity.getStatus());

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(GET_CHILDREN.replace(
			        ADDITIONAL_CONDITIONS, additionalConditions), params);
			resultList = resultSet.all().get();

			hierarchies = getHierarchyEntitiesFromResults(resultList);

			if (hierarchies == null || CollectionUtils.isEmpty(hierarchies)) {
				throw new NoResultException("Result Object is null or empty");
			}

			Iterator<HierarchyEntity> itr = hierarchies.iterator();
			while (itr.hasNext()) {
				HierarchyEntity entity = itr.next();
				if ((StringUtils.equals(entity.getDomain(),
				        hierarchyEntity.getDomain()))
				        && (StringUtils.equals(entity.getIdentifierKey(),
				                hierarchyEntity.getIdentifierKey()))
				        && (StringUtils.equals(entity.getIdentifierValue(),
				                hierarchyEntity.getIdentifierValue()))
				        && (StringUtils.equals(entity.getTemplateName(),
				                hierarchyEntity.getTemplateName()))
				        && (StringUtils.equals(entity.getEntityType(),
				                hierarchyEntity.getEntityType()))) {
					itr.remove();
				}
			}
		} catch (NoResultException e) {
			throw new NoResultException("Result Object is null or empty");
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return hierarchies;
	}

	/**
	 * method to get children count
	 * 
	 * @param HierarchyEntity
	 * @param searchTemplate
	 * @param searchEntityType
	 * @return {@link Integer}
	 */
	@Override
	public Integer getChildrenCount(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType) {

		String additionalConditions = "";
		Integer count = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		additionalConditions = validateSearchFields(searchTemplate,
		        searchEntityType, additionalConditions,
		        hierarchyEntity.getStatus());
		Result result = null;
		params.put("entityType", hierarchyEntity.getEntityType());
		params.put("identifierKey", hierarchyEntity.getIdentifierKey());
		params.put("identifierValue", hierarchyEntity.getIdentifierValue());
		params.put("domain", hierarchyEntity.getDomain());
		params.put("templateName", hierarchyEntity.getTemplateName());
		params.put("child", CHILD);
		params.put("childTemplateName", searchTemplate);
		params.put("childEntityType", searchEntityType);
		params.put("childStatus", hierarchyEntity.getStatus());

		try {
			Client client = titanSessionManager.getClient();

			ResultSet resultSet = client.submit(GET_CHILDREN_COUNT.replace(
			        ADDITIONAL_CONDITIONS, additionalConditions), params);
			result = resultSet.one();
			if (result == null || result.getObject() == null
			        || result.getInt() < 0) {
				return count;
			}
			count = result.getInt();
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return count;

	}

	/**
	 * method to get children count by status
	 * 
	 * @param domain
	 * @param templateName
	 * @param status
	 * @param entityType
	 * @return {@link Integer}
	 */
	@Override
	public Integer getCountByStatus(String domain, String templateName,
	        String status, String entityType) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityType", entityType);
		params.put("templateName", templateName);
		params.put("child", CHILD);
		params.put("status", status);
		params.put("myDomain", domain);

		Integer count = 0;
		try {
			Client client = titanSessionManager.getClient();

			ResultSet resultSet = client.submit(GET_NODES_STATUS_COUNT, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return count;
			}
			count = result.getInt();
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return count;
	}

	/**
	 * method to update particular node status
	 * 
	 * @param EntityDTO
	 * @return {@link EntityDTO}
	 */
	@Override
	public EntityDTO updateNode(EntityDTO entity) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("entityType", entity.getPlatformEntity()
		        .getPlatformEntityType());
		params.put("identifierKey", entity.getIdentifier().getKey());
		params.put("templateName", entity.getEntityTemplate()
		        .getEntityTemplateName());
		params.put("identifierValue", entity.getIdentifier().getValue());
		params.put("domain", entity.getDomain().getDomainName());
		params.put("entityStatus", entity.getEntityStatus().getStatusName());
		Result result = null;
		EntityDTO entityDTO = new EntityDTO();

		Client client = titanSessionManager.getClient();
		ResultSet resultSet = client.submit(UPDATE_NODE, params);
		result = resultSet.one();
		if (result == null || result.getObject() == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		entityDTO = getEntityFromResult(result);

		return entityDTO;

	}

	/**
	 * method to Get Entity Distribution Count
	 * 
	 * @param tenantDomain
	 * @param parentDomain
	 * @param myDomain
	 * @return {@link List<DistributionEntity>}
	 */
	@Override
	public List<DistributionEntity> getEntityDistributionCount(
	        String parentDomain, String parentEntityType,
	        String searchTemplateName, String searchEntityType, String status) {

		List<DistributionEntity> distributionEntities = new ArrayList<DistributionEntity>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityType", parentEntityType);
		params.put("domain", parentDomain);
		params.put("childTemplateName", searchTemplateName);
		params.put("childEntityType", searchEntityType);
		params.put("child", CHILD);
		params.put("childStatus", status);

		String additionalConditions = "";
		additionalConditions = validateSearchFields(searchTemplateName,
		        searchEntityType, additionalConditions, status);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(DISTRIBUTION_COUNT.replace(
			        ADDITIONAL_CONDITIONS, additionalConditions), params);
			List<Result> resultList;

			resultList = resultSet.all().get();

			distributionEntities = getDistributionEntityFromResults(resultList);

		} catch (NoResultException e) {
			return null;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return distributionEntities;
	}

	/**
	 * method to Get Entity Distribution
	 * 
	 * @param tenantDomain
	 * @param parentDomain
	 * @param myDomain
	 * @return {@link List<HierarchyEntity>}
	 */
	@Override
	public List<HierarchyEntity> getEntityDistribution(String parentDomain,
	        String parentEntityType, String searchTemplateName,
	        String searchEntityType, String status) {

		List<HierarchyEntity> hierarchyEntities = new ArrayList<HierarchyEntity>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityType", parentEntityType);
		params.put("domain", parentDomain);
		params.put("childTemplateName", searchTemplateName);
		params.put("childEntityType", searchEntityType);
		params.put("child", CHILD);
		params.put("childStatus", status);

		String additionalConditions = "";
		additionalConditions = validateSearchFields(searchTemplateName,
		        searchEntityType, additionalConditions, status);

		try {
			Client client = titanSessionManager.getClient();

			ResultSet resultSet = client.submit(DISTRIBUTION_ENTITIES.replace(
			        ADDITIONAL_CONDITIONS, additionalConditions), params);
			List<Result> resultList = resultSet.all().get();
			hierarchyEntities = getHierarchyEntitiesFromResults(resultList);

		} catch (NoResultException e) {
			return null;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return hierarchyEntities;
	}

	/**
	 * method to Get Immediate Children
	 * 
	 * @param HierarchyEntity
	 * @param searchTemplate
	 * @param searchEntityType
	 * @return {@link List<HierarchyEntity>}
	 */
	@Override
	public List<HierarchyEntity> getImmediateChildren(
	        HierarchyEntity hierarchyEntity, String searchTemplate,
	        String searchEntityType) {

		String additionalConditions = "";
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		Map<String, Object> params = new HashMap<String, Object>();
		additionalConditions = validateSearchFields(searchTemplate,
		        searchEntityType, additionalConditions,
		        hierarchyEntity.getStatus());
		List<Result> resultList = null;
		params.put("entityType", hierarchyEntity.getEntityType());
		params.put("identifierKey", hierarchyEntity.getIdentifierKey());
		params.put("identifierValue", hierarchyEntity.getIdentifierValue());
		params.put("domain", hierarchyEntity.getDomain());
		params.put("templateName", hierarchyEntity.getTemplateName());
		params.put("child", CHILD);
		params.put("childTemplateName", searchTemplate);
		params.put("childEntityType", searchEntityType);
		params.put("childStatus", hierarchyEntity.getStatus());

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(GET_IMMEDIATE_CHILDREN.replace(
			        ADDITIONAL_CONDITIONS, additionalConditions), params);
			resultList = resultSet.all().get();

			hierarchies = getHierarchyEntitiesFromResults(resultList);

			if (hierarchies == null || CollectionUtils.isEmpty(hierarchies)) {
				throw new NoResultException("Result Object is null or empty");
			}

		} catch (NoResultException e) {
			throw new NoResultException("Result Object is null or empty");
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return hierarchies;
	}

	/**
	 * method to Get Immediate Parents
	 * 
	 * @param HierarchyEntity
	 * @param searchTemplate
	 * @param searchEntityType
	 * @return {@link List<HierarchyEntity>}
	 */

	@Override
	public List<HierarchyEntity> getImmediateParents(
	        HierarchyEntity hierarchyEntity, String searchTemplate,
	        String searchEntityType) {

		String additionalConditions = "";
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		Map<String, Object> params = new HashMap<String, Object>();
		additionalConditions = validateSearchFields(searchTemplate,
		        searchEntityType, additionalConditions,
		        hierarchyEntity.getStatus());
		List<Result> resultList = null;
		params.put("entityType", hierarchyEntity.getEntityType());
		params.put("identifierKey", hierarchyEntity.getIdentifierKey());
		params.put("identifierValue", hierarchyEntity.getIdentifierValue());
		params.put("domain", hierarchyEntity.getDomain());
		params.put("templateName", hierarchyEntity.getTemplateName());
		params.put("child", CHILD);
		params.put("childTemplateName", searchTemplate);
		params.put("childEntityType", searchEntityType);
		params.put("childStatus", hierarchyEntity.getStatus());

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(GET_IMMEDIATE_PARENTS.replace(
			        ADDITIONAL_CONDITIONS, additionalConditions), params);
			resultList = resultSet.all().get();

			hierarchies = getHierarchyEntitiesFromResults(resultList);

			if (hierarchies == null || CollectionUtils.isEmpty(hierarchies)) {
				throw new NoResultException("Result Object is null or empty");
			}

		} catch (NoResultException e) {
			throw new NoResultException("Result Object is null or empty");
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return hierarchies;
	}

	/**
	 * method to check child domain
	 * 
	 * @param parentDomain
	 * @param domain
	 * @return {@link boolean}
	 */

	@Override
	public boolean isChildDomain(String parentDomain, String domain) {

		boolean flag = true;
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("entityType", TENANT);

		params.put("parentMyDomain", parentDomain);
		params.put("childMyDomain", domain);
		params.put("child", CHILD);
		List<Result> resultList = null;
		List<Result> results = null;
		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(GET_CHILD_TENANT, params);
			resultList = resultSet.all().get();

			if (resultList == null || CollectionUtils.isEmpty(resultList)) {
				results = client.submit(VALIDATE_TENANT, params).all().get();
				if (results == null || CollectionUtils.isEmpty(results)) {
					flag = false;
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return flag;
	}

	@Override
	public List<HierarchyEntity> getParents(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType) {
		String additionalConditions = "";
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		Map<String, Object> params = new HashMap<String, Object>();
		additionalConditions = validateSearchFields(searchTemplate,
		        searchEntityType, additionalConditions,
		        hierarchyEntity.getStatus());
		List<Result> resultList = null;
		params.put("entityType", hierarchyEntity.getEntityType());
		params.put("identifierKey", hierarchyEntity.getIdentifierKey());
		params.put("identifierValue", hierarchyEntity.getIdentifierValue());
		params.put("domain", hierarchyEntity.getDomain());
		params.put("templateName", hierarchyEntity.getTemplateName());
		params.put("child", CHILD);
		params.put("childTemplateName", searchTemplate);
		params.put("childEntityType", searchEntityType);
		params.put("childStatus", hierarchyEntity.getStatus());

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(GET_PARENTS.replace(
			        ADDITIONAL_CONDITIONS, additionalConditions), params);
			resultList = resultSet.all().get();

			hierarchies = getHierarchyEntitiesFromResults(resultList);

			if (hierarchies == null || CollectionUtils.isEmpty(hierarchies)) {
				throw new NoResultException("Result Object is null or empty");
			}

			Iterator<HierarchyEntity> itr = hierarchies.iterator();
			while (itr.hasNext()) {
				HierarchyEntity entity = itr.next();
				if ((StringUtils.equals(entity.getDomain(),
				        hierarchyEntity.getDomain()))
				        && (StringUtils.equals(entity.getIdentifierKey(),
				                hierarchyEntity.getIdentifierKey()))
				        && (StringUtils.equals(entity.getIdentifierValue(),
				                hierarchyEntity.getIdentifierValue()))
				        && (StringUtils.equals(entity.getTemplateName(),
				                hierarchyEntity.getTemplateName()))
				        && (StringUtils.equals(entity.getEntityType(),
				                hierarchyEntity.getEntityType()))) {
					itr.remove();
				}
			}
		} catch (NoResultException e) {
			throw new NoResultException("Result Object is null or empty");
		} catch (Exception e) {
			throw new RuntimeException("Error in HierarchyRepository", e);
		}
		return hierarchies;
	}

	private HashSet<String> getSetFromResultList(List<Result> resultList) {

		HashSet<String> childSet = new HashSet<String>();
		for (Result result : resultList) {
			Object object = result.getObject();
			childSet.add((String)object);
		}
		return childSet;

	}

	private List<String> getListFromResultList(List<Result> resultList) {
		List<String> childList = new ArrayList<String>();
		for (Result result : resultList) {
			Object object = result.getObject();
			childList.add((String)object);
		}
		return childList;
	}

	private List<Map<String, Object>> getEntityListMap(
	        List<EntityDTO> entityDTOList) {

		List<Map<String, Object>> listOfMap = new ArrayList<>();
		for (EntityDTO entityDTO : entityDTOList) {
			listOfMap.add(getEntityMap(entityDTO));
		}
		return listOfMap;
	}

	private Map<String, Object> getEntityMap(EntityDTO entityDTO) {
		Map<String, Object> entityDTOMap = new HashMap<String, Object>();
		if (isNotEmpty(entityDTO.getIdentifier().getKey())
		        && isNotBlank(entityDTO.getIdentifier().getKey())) {
			entityDTOMap.put("identifierKey", entityDTO.getIdentifier()
			        .getKey());
		}
		if (isNotEmpty(entityDTO.getIdentifier().getValue())
		        && isNotBlank(entityDTO.getIdentifier().getValue())) {
			entityDTOMap.put("identifierValue", entityDTO.getIdentifier()
			        .getValue());
		}
		if (isNotEmpty(entityDTO.getEntityTemplate().getEntityTemplateName())
		        && isNotBlank(entityDTO.getEntityTemplate()
		                .getEntityTemplateName())) {
			entityDTOMap.put("templateName", entityDTO.getEntityTemplate()
			        .getEntityTemplateName());
		}
		if (isNotEmpty(entityDTO.getIdentifier().getValue())
		        && isNotBlank(entityDTO.getIdentifier().getValue())) {
			entityDTOMap.put("domain", entityDTO.getDomain().getDomainName());
		}
		if (entityDTO.getEntityStatus() != null) {
			if (isNotEmpty(entityDTO.getEntityStatus().getStatusName())
			        && isNotBlank(entityDTO.getEntityStatus().getStatusName())) {
				entityDTOMap.put("status", entityDTO.getEntityStatus()
				        .getStatusName());
			}
		}
		if (entityDTO.getPlatformEntity() != null) {
			if (isNotEmpty(entityDTO.getPlatformEntity()
			        .getPlatformEntityType())
			        && isNotBlank(entityDTO.getPlatformEntity()
			                .getPlatformEntityType())) {
				entityDTOMap.put("globalEntityType", entityDTO
				        .getPlatformEntity().getPlatformEntityType());
			}
		}
		if (entityDTO.getMyDomain() != null) {
			if (isNotEmpty(entityDTO.getMyDomain().getDomainName())
			        && isNotBlank(entityDTO.getMyDomain().getDomainName())) {
				entityDTOMap.put("myDomain", entityDTO.getMyDomain()
				        .getDomainName());
			}
		}
		return entityDTOMap;
	}

	private Map<String, Object> getHierarchyEntityMap(
	        HierarchyEntity hierarchyEntity) {

		Map<String, Object> entityDTOMap = new HashMap<String, Object>();
		if (isNotEmpty(hierarchyEntity.getIdentifierKey())
		        && isNotBlank(hierarchyEntity.getIdentifierKey())) {
			entityDTOMap.put("identifierKey",
			        hierarchyEntity.getIdentifierKey());
		}
		if (isNotEmpty(hierarchyEntity.getIdentifierValue())
		        && isNotBlank(hierarchyEntity.getIdentifierValue())) {
			entityDTOMap.put("identifierValue",
			        hierarchyEntity.getIdentifierValue());
		}
		if (isNotEmpty(hierarchyEntity.getTemplateName())
		        && isNotBlank(hierarchyEntity.getTemplateName())) {
			entityDTOMap.put("templateName", hierarchyEntity.getTemplateName());
		}
		if (isNotEmpty(hierarchyEntity.getDomain())
		        && isNotBlank(hierarchyEntity.getDomain())) {
			entityDTOMap.put("domain", hierarchyEntity.getDomain());
		}

		if (isNotEmpty(hierarchyEntity.getStatus())
		        && isNotBlank(hierarchyEntity.getStatus())) {
			entityDTOMap.put("status", hierarchyEntity.getStatus());
		}

		if (isNotEmpty(hierarchyEntity.getMyDomain())
		        && isNotBlank(hierarchyEntity.getMyDomain())) {
			entityDTOMap.put("myDomain", hierarchyEntity.getMyDomain());
		}

		return entityDTOMap;
	}

	private Map<String, Object> getHierarchyEntityMap(HierarchyDTO hierarchy) {
		Map<String, Object> hierarchyMap = new HashMap<String, Object>();

		if (hierarchy.getActor() != null) {
			hierarchyMap.put("actor", getEntityMap(hierarchy.getActor()));
		}

		if (CollectionUtils.isNotEmpty(hierarchy.getSubjects())) {
			hierarchyMap.put("subject",
			        getEntityListMap(hierarchy.getSubjects()));
		}
		return hierarchyMap;
	}

	private String validateSearchFields(String searchTemplate,
	        String searchEntityType, String additionalConditions, String status) {
		if (isNotBlank(searchTemplate)) {

			additionalConditions = CHILD_TEMPLATE_CONDITION.replace(
			        CHILD_TEMPLATE_NAME, searchTemplate);
		}
		if (isNotBlank(searchEntityType)) {

			additionalConditions += CHILD_ENTITY_TYPE_CONDITION.replace(
			        CHILD_ENTITY_TYPE, searchEntityType);
		}
		if (isNotBlank(status)) {

			additionalConditions += DISTRIBUTION_STATUS_CONDITION.replace(
			        STATUS, status);
		}

		return additionalConditions;
	}

	private List<HierarchyEntity> getHierarchyEntitiesFromResults(
	        List<Result> resultList) {

		List<HierarchyEntity> destList = new ArrayList<HierarchyEntity>();
		if (resultList == null || CollectionUtils.isEmpty(resultList)) {
			throw new NoResultException("Result Object is null or empty");
		}
		for (Result result : resultList) {
			if (result == null || result.getObject() == null) {
				return null;
			}
			destList.add(getHierarchy(result));
		}
		return destList;

	}

	@SuppressWarnings({
	        "unchecked", "rawtypes"})
	private HierarchyEntity getHierarchy(Result result) {

		HierarchyEntity hierarchyEntity = new HierarchyEntity();
		LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
		        .getObject();
		if (object.get("identifierValue") != null
		        && !object.get("identifierValue").equals(""))

		{
			hierarchyEntity.setIdentifierValue(((List)object
			        .get("identifierValue")).get(0).toString());
		}
		if (object.get("identifierKey") != null
		        && !object.get("identifierKey").equals(""))

		{
			hierarchyEntity
			        .setIdentifierKey(((List)object.get("identifierKey"))
			                .get(0).toString());
		}
		if (object.get("domain") != null && !object.get("domain").equals(""))

		{
			hierarchyEntity.setDomain(((List)object.get("domain")).get(0)
			        .toString());
		}
		if (object.get("templateName") != null
		        && !object.get("templateName").equals(""))

		{
			hierarchyEntity.setTemplateName(((List)object.get("templateName"))
			        .get(0).toString());
		}
		if (object.get("label") != null && !object.get("label").equals(""))

		{
			hierarchyEntity.setEntityType(object.get("label").toString());
		}

		return hierarchyEntity;

	}

	@SuppressWarnings({
	        "rawtypes", "unchecked"})
	private EntityDTO getEntityFromResult(Result result) {

		EntityDTO entityDTO = new EntityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		DomainDTO domainDTO = new DomainDTO();
		GlobalEntityDTO globalEntityDTO = new GlobalEntityDTO();
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		StatusDTO statusDTO = new StatusDTO();

		LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
		        .getObject();
		if (object.get("identifierValue") != null
		        && !object.get("identifierValue").equals("")) {
			fieldMapDTO.setValue(((List)object.get("identifierValue")).get(0)
			        .toString());
		}
		if (object.get("identifierKey") != null
		        && !object.get("identifierValue").equals("")) {
			fieldMapDTO.setKey(((List)object.get("identifierKey")).get(0)
			        .toString());
		}
		entityDTO.setIdentifier(fieldMapDTO);
		if (object.get("domain") != null && !object.get("domain").equals(""))

		{
			domainDTO.setDomainName(((List)object.get("domain")).get(0)
			        .toString());
			entityDTO.setDomain(domainDTO);
		}

		if (object.get("templateName") != null
		        && !object.get("templateName").equals(""))

		{
			entityTemplateDTO.setEntityTemplateName(((List)object
			        .get("templateName")).get(0).toString());
			entityDTO.setEntityTemplate(entityTemplateDTO);
		}
		if (object.get("status") != null && !object.get("status").equals(""))

		{
			statusDTO.setStatusName(((List)object.get("status")).get(0)
			        .toString());
			entityDTO.setEntityStatus(statusDTO);
		}
		if (object.get("label") != null && !object.get("label").equals(""))

		{
			globalEntityDTO.setGlobalEntityType(object.get("label").toString());
			entityDTO.setGlobalEntity(globalEntityDTO);
		}

		return entityDTO;
	}

	@SuppressWarnings("unchecked")
	private List<DistributionEntity> getDistributionEntityFromResults(
	        List<Result> resultList) {
		if (CollectionUtils.isEmpty(resultList)) {
			throw new NoResultException("Result Object is null or empty");
		}
		List<DistributionEntity> destList = new ArrayList<DistributionEntity>();
		for (Result result : resultList) {
			DistributionEntity distributionEntity = new DistributionEntity();
			LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
			        .getObject();
			for (Entry<String, Object> entry : object.entrySet()) {
				distributionEntity.setIdentifierValue(entry.getKey());
				distributionEntity.setCount((Integer)entry.getValue());
			}
			destList.add(distributionEntity);
		}
		return destList;
	}

	@Override
	public List<HierarchyEntity> getAssignableMarkers(
	        HierarchyEntity hierarchyEntity, String searchTemplateName,
	        String markerTemplateName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HierarchyEntity> getAllOwnedMarkersByDomain(
	        HierarchyEntity hierarchyEntity, String searchTemplateName,
	        String markerTemplateName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HierarchyEntity> getTenantsWithinRange(
	        HierarchyEntity hierarchyEntity, HierarchyEntity endEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HierarchyEntity getTenantNode(String domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityDTO updateNode(EntityDTO entity, String dataprovider,
	        String tree) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attach(AttachHierarchyDTO hierarchy) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<HierarchyEntity> getAllEntitySubjects(
	        AttachHierarchySearchDTO attachHierarchySearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HierarchyEntityTemplate> getAllTemplateSubjects(
	        AttachHierarchySearchDTO attachHierarchySearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HierarchyEntity> getImmediateRoot(
	        AttachHierarchySearchDTO attachHierarchySearch) {
		// TODO Auto-generated method stub
		return null;
	}

}
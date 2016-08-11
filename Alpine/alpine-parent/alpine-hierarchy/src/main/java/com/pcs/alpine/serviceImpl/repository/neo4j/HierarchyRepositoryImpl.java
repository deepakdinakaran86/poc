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
package com.pcs.alpine.serviceImpl.repository.neo4j;

import static com.pcs.alpine.serviceImpl.repository.utils.Neo4jExecuter.exexcuteQuery;
import static com.pcs.alpine.services.enums.HMDataFields.ACTOR;
import static com.pcs.alpine.services.enums.HMDataFields.DATA;
import static com.pcs.alpine.services.enums.HMDataFields.DATAPROVIDER;
import static com.pcs.alpine.services.enums.HMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.HMDataFields.IDENTITY_KEY;
import static com.pcs.alpine.services.enums.HMDataFields.IDENTITY_VALUE;
import static com.pcs.alpine.services.enums.HMDataFields.MY_DOMAIN;
import static com.pcs.alpine.services.enums.HMDataFields.RESULTS;
import static com.pcs.alpine.services.enums.HMDataFields.ROW;
import static com.pcs.alpine.services.enums.HMDataFields.TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.TREE;
import static com.pcs.alpine.services.enums.HierarchyNodeTypes.ENTITY;
import static com.pcs.alpine.services.enums.HierarchyNodeTypes.TEMPLATE;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcs.alpine.commons.dto.DomainDTO;
import com.pcs.alpine.commons.dto.GeneralBatchResponse;
import com.pcs.alpine.commons.dto.GeneralResponse;
import com.pcs.alpine.commons.dto.HierarchyTagSearchDTO;
import com.pcs.alpine.commons.dto.StatusDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.model.DistributionEntity;
import com.pcs.alpine.model.HierarchyEntity;
import com.pcs.alpine.model.HierarchyEntityTemplate;
import com.pcs.alpine.model.TaggedEntity;
import com.pcs.alpine.repo.utils.Neo4jUtils;
import com.pcs.alpine.serviceImpl.repository.utils.HierarchyTranslator;
import com.pcs.alpine.serviceImpl.repository.utils.Neo4jExecuter;
import com.pcs.alpine.services.dto.Actor;
import com.pcs.alpine.services.dto.AttachHierarchyDTO;
import com.pcs.alpine.services.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.services.dto.EntitiesByTagsFilter;
import com.pcs.alpine.services.dto.EntitiesByTagsPayload;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;
import com.pcs.alpine.services.dto.HierarchyRelationDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.Subject;
import com.pcs.alpine.services.dto.TagRangePayload;
import com.pcs.alpine.services.enums.HierarchyNodeTypes;
import com.pcs.alpine.services.repository.HierarchyRepository;

/**
 * Implementation class for {@link HierarchyRepository}
 * 
 * @description Service Implementation for hierarchy services
 * @author Daniela (PCSEG191)
 * @author Riyas (PCSEG296)
 * @date 18 Oct 2015
 * @since alpine-1.0.0
 */
@Repository("hierarchyNeo")
public class HierarchyRepositoryImpl implements HierarchyRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(HierarchyRepositoryImpl.class);

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private SubscriptionProfileService profileService;

	private static final String NODE_TAG = "{node}";
	private static final String NODE_NAME_TAG = "{node_name}";
	private static final String IDENTIFIER_KEY_TAG = "{identifierKey}";
	private static final String IDENTIFIER_VALUE_TAG = "{identifierValue}";
	private static final String TEMPLATE_NAME_TAG = "{templateName}";
	private static final String MARKER_TEMPLATE_NAME_TAG = "{markerTemplateName}";
	private static final String DOMAIN_TAG = "{domain}";
	private static final String CHILD_TAG = "{child}";
	private static final String PARENT_TAG = "{parent}";
	private static final String ADDITIONAL_CONDITIONS = "{additionalConditions}";
	private static final String CHILD_TEMPLATE_NAME = "{childTemplateName}";
	private static final String CHILD_ENTITY_TYPE = "{childEntityType}";
	private static final String STATUS = "{status}";
	private static final String ENTITY_TYPE = "{entityType}";
	private static final String END_IDENTIFIER_KEY_TAG = "{endIdentifierKey}";
	private static final String END_IDENTIFIER_VALUE_TAG = "{endIdentifierValue}";
	private static final String END_TEMPLATE_NAME_TAG = "{endTemplateName}";
	private static final String END_DOMAIN_TAG = "{endDomain}";
	private static final String TREE_TAG = "{tree}";
	private static final String DATAPROVIDER_TAG = "{dataprovider}";
	private static final String END_MY_DOMAIN_TAG = "{endMyDomain}";
	private static final String SEARCH_TEMPLATE = "{searchTemplate}";

	private static final String PARENT_ENTITY_TYPE = "{parentEntityType}";
	private static final String PARENT_TEMPLATE_NAME = "{parentTemplateName}";
	private static final String PARENT_DOMAIN_NAME = "{parentDomain}";
	private static final String CHILD_DOMAIN_NAME = "{childDomain}";

	private static final String NODE_MERGE = "MERGE ({node}:{node_name} {identifierKey:'{identifierKey}' ,identifierValue:'{identifierValue}' ,"
	        + "templateName:'{templateName}', domain:'{domain}'}) ";

	// merge query for template node
	private static final String TEMPLATE_NODE_MERGE = "MERGE ({node}:{node_name} {name:'{name}', domain:'{domain}', type:'{type}'}) ";
	private static final String NODE_LINK = "MERGE ({parent})-[:Child]->({child}) ";
	private static final String NODE_ANY_LINK = "MERGE ({parent})-[:{anyLink}]->({child}) ";

	private static final String CHILD_HIERARCHY = "MATCH (child:TENANT {myDomain:'{parent}'})-[r:Child*]->(parent:TENANT) where parent.myDomain in {child} return collect(parent.myDomain)";
	private static final String PARENT_HIERARCHY = "MATCH (child:TENANT {myDomain:'{child}'})<-[r:Child*]-(parent:TENANT) where parent.myDomain in {parent} return collect(parent.myDomain)";
	private static final String TENANT_NODE = "MATCH (node:TENANT {myDomain:'{domain}'}) return node";
	private static final String GET_TENANT = "MATCH (node:TENANT  {identifierKey:'{identifierKey}' ,identifierValue:'{identifierValue}' ,"
	        + "templateName:'{templateName}', domain:'{domain}'}) return node.myDomain";
	private static final String GET_TENANTS = "MATCH (node{count}:TENANT {identifierKey:'{identifierKey}' ,identifierValue:'{identifierValue}' ,"
	        + "templateName:'{templateName}', domain:'{domain}'}) ";

	private static final String GET_CHILDREN = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })-"
	        + "[r:Child*]->(m{childEntityType} {{additionalConditions}} ) return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0]as entityType,m.dataprovider as dataprovider, m.tree as tree,m.status as status  ";

	private static final String GET_CHILDREN_COUNT = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })-"
	        + "[r:Child*]->(m{childEntityType} {{additionalConditions}} ) return count(m);";

	private static final String CHILD_TEMPLATE_CONDITION = " templateName:'{childTemplateName}'";

	private static final String GET_NODES_STATUS_COUNT = "MATCH (n:TENANT {myDomain:'{domain}'})-[r:Child]->(m:{entityType} {templateName:'{templateName}',status:'{status}'}) RETURN count(m);";

	private static final String NODE_MATCH = "MATCH ({node}:{node_name} {identifierKey:'{identifierKey}' ,identifierValue:'{identifierValue}' ,"
	        + "templateName:'{templateName}', domain:'{domain}'}) ";

	private static final String DISTRIBUTION_COUNT = "MATCH (n:{entityType} {domain:'{domain}'})-[r:Child*]->(m:{childEntityType} {templateName: '{childTemplateName}' {additionalConditions} }) RETURN n.identifierValue,count(m);";

	private static final String DISTRIBUTION_ENTITIES = "MATCH (n:{entityType} {domain:'{domain}'})-[r:Child*]->(m:{childEntityType} {templateName: '{childTemplateName}' {additionalConditions} })  RETURN m.domain,m.templateName,m.identifierKey,m.identifierValue,labels(m)[0] as entityType,m.dataprovider as dataprovider, m.tree as tree, m.status as status ";

	private static final String DISTRIBUTION_STATUS_CONDITION = " ,status:'{status}'";
	private static final String STATUS_CONDITION = " status:'{status}'";

	private static final String GET_IMMEDIATE_CHILDREN = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })-"
	        + "[r:Child]->(m{childEntityType} {{additionalConditions}} ) return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0]as entityType, m.dataprovider as dataprovider, m.tree as tree, m.status as status ";

	private static final String GET_IMMEDIATE_PARENTS = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })<-"
	        + "[r:Child]-(m{childEntityType} {{additionalConditions}} ) return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0]as entityType,  m.dataprovider as dataprovider, m.tree as tree, m.status as status ";

	private static final String GET_CHILD_TENANT = "MATCH (n:TENANT {myDomain:'{parent}'})-[r:Child*]->(m:TENANT {myDomain:'{domain}'}) return m;";

	private static final String GET_NODE = "MATCH (node:{entityType}) where node.domain= '{domain}' and "
	        + "node.templateName= '{templateName}' and node.identifierKey = '{identifierKey}' and node.identifierValue = '{identifierValue}' "
	        + "return node";

	private static final String GET_PARENTS = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })<-"
	        + "[r:Child*]-(m{childEntityType} {{additionalConditions}} ) return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0]as entityType ";

	private static final String VALIDATE_TENANT = "MATCH (node:TENANT {myDomain:'{parent}', domain:'{domain}'}) return node";

	private static final String GET_ASSIGNABLE_MARKERS = "MATCH (tenant:`TENANT` {domain:'{domain}',templateName:'{templateName}',identifierKey:'{identifierKey}',identifierValue:'{identifierValue}'})"
	        + "<-[:Child]-(markers:MARKER {templateName:'{markerTemplateName}'})"
	        + "WHERE NOT tenant-[:Child]->(:TENANT)<-[:Child]-markers {additionalConditions}"
	        + "return markers.domain as domainName,markers.templateName as templateName,"
	        + "markers.identifierKey as identifierKey,markers.identifierValue as identifierValue,labels(markers)[0]as entityType,markers.dataprovider as dataprovider, markers.tree as tree, markers.status as status ";

	private static final String NO_OUT_GOING_RELATIONS = "and NOT markers-[:Child]->(:MARKER {templateName:'{markerTemplateName}'})";

	private static final String GET_TENANTS_WITHIN_RANGE = "match p=(start:TENANT {domain:'{domain}',templateName:'{templateName}',identifierKey:'{identifierKey}',identifierValue:'{identifierValue}'})"
	        + "-[:Child*]->"
	        + "(end:TENANT{domain:'{endDomain}',templateName:'{endTemplateName}',identifierKey:'{endIdentifierKey}',identifierValue:'{endIdentifierValue}'})"
	        + "return collect(nodes(p)) ";

	private static final String TENANT_NODE_DETAILS = "MATCH (tenant:TENANT {myDomain:'{domain}'}) return tenant.domain as domainName,tenant.templateName as templateName,tenant.identifierKey as identifierKey,tenant.identifierValue as identifierValue,labels(tenant)[0]as entityType, tenant.dataprovider as dataprovider, tenant.tree as tree, tenant.status as status ";

	private static final String NAME_TAG = "{name}";
	private static final String TYPE_TAG = "{type}";
	private static final String ANY_LINK = "{anyLink}";

	private static final String GET_ALL_ENTITY_SUBJECTS = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })-[r:{anyLink}]->(m) {additionalConditions} return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0] as entityType, m.dataprovider as dataprovider, m.tree as tree, m.status as status ";
	private static final String GET_ALL_ENTITY_SUBJECTS_ACTOR_TEMPLATE = "MATCH (:TEMPLATE {name:'{name}', type:'{type}', domain:'{domain}'})-[:{anyLink}]->(m) {additionalConditions} return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0]as entityType,m.dataprovider as dataprovider, m.tree as tree, m.status as status ";

	private static final String GET_IMMEDIATE_ROOTS_ANY_LINK_ACTOR_ENTITY = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })<-[:{anyLink}]-(m{childEntityType} {{additionalConditions}} ) return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0]as entityType,m.dataprovider as dataprovider, m.tree as tree , m.status as status";
	private static final String GET_IMMEDIATE_ROOTS_ANY_LINK_ACTOR_TEMPLATE = "MATCH (n:TEMPLATE {name:'{name}', domain:'{domain}', type : '{type}'})<-[:{anyLink}]-(m{childEntityType} {{additionalConditions}} ) return m.domain as domainName,m.templateName as templateName,m.identifierKey as identifierKey,m.identifierValue as identifierValue,labels(m)[0]as entityType, m.dataprovider as dataprovider, m.tree as tree, m.status as status ";

	private static final String GET_ALL_TEMPLATE_SUBJECTS = "MATCH (n:{entityType} {identifierValue:'{identifierValue}', domain:'{domain}', identifierKey : '{identifierKey}', templateName :'{templateName}' })-[r:{anyLink}]->(m) WHERE m:TEMPLATE return m.domain as domainName,m.name as name,m.type as type, m.dataprovider as dataprovider, m.tree as tree, m.status as status ";

	private static final String GET_ALL_TAGGED_ENTITIES = "MATCH (n:`MARKER`{templateName:'Geotag', status:'ACTIVE', domain:'{domain}'})<-[Child]-m where m.status='ACTIVE' {additionalConditions} return n.domain as geotagDomain,n.templateName as geotagTemplate,n.identifierKey as geotagIdentifierKey,n.status as geotagStatus,n.identifierValue as geotagIdentifierValue,labels(n)[0] as geotagType ,n.dataprovider as geotagDataprovider,m.domain as entityDomain, m.templateName as entityTemplateName, m.identifierKey as entityIdentifierKey, m.status as entityStatus, m.identifierValue as entityIdentifierValue,labels(m)[0] as entityType,m.dataprovider as entityDataprovider, m.status as status";
	private static final String TAGGED_TEMPLATE_CONDITION = " and m.templateName='{templateName}'";

	private static final String GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN = "MATCH (n:`MARKER`{templateName:'{templateName}',domain:'{domain}'})<-[r:Child]-x RETURN collect(distinct x.templateName)";

	private static final String HIERARCHY_SEARCH_FOR_PARENT_AND_CHILD = "MATCH (parent:{parentEntityType} {templateName:'{parentTemplateName}' ,domain:'{parentDomain}'}) - [r:Child]->(child:{childEntityType} {templateName:'{childTemplateName}' ,domain:'{childDomain}'}) WHERE parent.status<>'DELETED' and child.status<>'DELETED' return collect ({parent:parent,child:child})";

	private static final String GET_ALL_TAGS_BY_RANGE = "MATCH (startNodeEntities)-[startNodeRel:Child]->(endNodeEntities)<-[endNodeRel:attachedTo]-(tagEntities) WHERE ";

	private static final String ATTACH_MULTIPLE_CHILDREN = "WITH props AS points UNWIND points AS point merge (v:MARKER {identifierKey:'{identifierKey}' ,identifierValue:'{identifierValue}',templateName:'{templateName}', domain:'{domain}'}) set v.status ='{status}', v.tree='{tree}',v.dataprovider='{dataprovider}' merge (p:MARKER {identifierKey:point.identifierKey ,identifierValue:point.identifierValue ,templateName:point.templateName, domain:point.domain}) set p = point create v-[r:Child]->p;";

	private static final String GET_ENTITIES_BY_TEMPLATE_BETWEEN_TENANTS = "MATCH p=(start:TENANT {identifierValue:'{identifierValue}',identifierKey:'{identifierKey}', templateName:'{templateName}',domain:'{domain}'})<-[r:Child*]-(end:TENANT{identifierValue:'{endIdentifierValue}',identifierKey:'{endIdentifierKey}',domain:'{endDomain}',templateName:'{endTemplateName}'}) WHERE start.status<>'DELETED' WITH nodes(p) AS points UNWIND points AS point MATCH (n:`MARKER` {templateName:'{searchTemplate}', status:'ACTIVE'} ) where n.domain=point.myDomain return n.domain, n.templateName,n.identifierKey,n.identifierValue,labels(n)[0] as entityType,n.dataprovider,n.tree,n.status ";

	private static final String GET_TAGGED_ENTITY_COUNT = "MATCH (start:MARKER  {domain:'{domain}',templateName:'{tagType}'} )-[r:attachedTo*]->(intermediate:MARKER {domain:'{domain}'}) -[re:Child]->(end:MARKER {domain:'{domain}',templateName:'{endEntityTemplate}'}) WHERE start.status<>'DELETED' and intermediate.status<>'DELETED' and end.status<>'DELETED' and {intermediateNodesCondition} return count(end),start.dataprovider as dataprovider";

	private static final String TAG_TYPE = "{tagType}";
	private static final String END_ENTITY_TEMPLATE = "{endEntityTemplate}";
	private static final String INTERMEDIATE_CONDITION = "{intermediateNodesCondition}";

	@Override
	public void attachParents(HierarchyDTO hierarchy) {
		EntityDTO parent = hierarchy.getActor();

		StringBuffer parentQuery = new StringBuffer(NODE_MERGE
		        .replace(NODE_TAG, "child")
		        .replace(NODE_NAME_TAG,
		                parent.getPlatformEntity().getPlatformEntityType())
		        .replace(IDENTIFIER_KEY_TAG, parent.getIdentifier().getKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                parent.getIdentifier().getValue())
		        .replace(TEMPLATE_NAME_TAG,
		                parent.getEntityTemplate().getEntityTemplateName())
		        .replace(DOMAIN_TAG, parent.getDomain().getDomainName()));

		parentQuery.append(" SET child.status='"
		        + parent.getEntityStatus().getStatusName() + "' ");
		parentQuery.append(", child.dataprovider='"
		        + getDataProvider(parent.getDataprovider()) + "' ");
		parentQuery.append(", child.tree='" + getTree(parent.getTree()) + "' ");
		int count = 0;
		for (EntityDTO subject : hierarchy.getSubjects()) {

			StringBuffer subjectQuery = new StringBuffer(
			        NODE_MERGE
			                .replace(NODE_TAG, "parent" + count)
			                .replace(
			                        NODE_NAME_TAG,
			                        subject.getPlatformEntity()
			                                .getPlatformEntityType())
			                .replace(IDENTIFIER_KEY_TAG,
			                        subject.getIdentifier().getKey())
			                .replace(IDENTIFIER_VALUE_TAG,
			                        subject.getIdentifier().getValue())
			                .replace(
			                        TEMPLATE_NAME_TAG,
			                        subject.getEntityTemplate()
			                                .getEntityTemplateName())
			                .replace(DOMAIN_TAG,
			                        subject.getDomain().getDomainName()));

			subjectQuery.append(NODE_LINK.replace(PARENT_TAG, "parent" + count)
			        .replace(CHILD_TAG, "child"));
			subjectQuery.append(" SET parent" + count + ".status='"
			        + subject.getEntityStatus().getStatusName() + "' ");
			subjectQuery.append(", parent" + count + ".dataprovider='"
			        + getDataProvider(subject.getDataprovider()) + "' ");
			subjectQuery.append(", parent" + count + ".tree='"
			        + getTree(subject.getTree()) + "' ");
			count++;

			parentQuery.append(subjectQuery);
		}
		try {
			Neo4jUtils.queryNeo4j(neo4jURI, parentQuery.toString(), null,
			        ROW.toString());
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
	}

	@Override
	public void attachChildren(HierarchyDTO hierarchy) {

		EntityDTO parent = hierarchy.getActor();

		StringBuffer parentQuery = new StringBuffer(NODE_MERGE
		        .replace(NODE_TAG, "parent")
		        .replace(NODE_NAME_TAG,
		                parent.getPlatformEntity().getPlatformEntityType())
		        .replace(IDENTIFIER_KEY_TAG, parent.getIdentifier().getKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                parent.getIdentifier().getValue())
		        .replace(TEMPLATE_NAME_TAG,
		                parent.getEntityTemplate().getEntityTemplateName())
		        .replace(DOMAIN_TAG, parent.getDomain().getDomainName()));

		parentQuery.append(" SET parent.status='"
		        + parent.getEntityStatus().getStatusName() + "' ");
		parentQuery.append(", parent.dataprovider='"
		        + getDataProvider(parent.getDataprovider()) + "' ");
		parentQuery
		        .append(", parent.tree='" + getTree(parent.getTree()) + "' ");

		int count = 0;
		for (EntityDTO subject : hierarchy.getSubjects()) {

			StringBuffer subjectQuery = new StringBuffer(
			        NODE_MERGE
			                .replace(NODE_TAG, "child" + count)
			                .replace(
			                        NODE_NAME_TAG,
			                        subject.getPlatformEntity()
			                                .getPlatformEntityType())
			                .replace(IDENTIFIER_KEY_TAG,
			                        subject.getIdentifier().getKey())
			                .replace(IDENTIFIER_VALUE_TAG,
			                        subject.getIdentifier().getValue())
			                .replace(
			                        TEMPLATE_NAME_TAG,
			                        subject.getEntityTemplate()
			                                .getEntityTemplateName())
			                .replace(DOMAIN_TAG,
			                        subject.getDomain().getDomainName()));

			subjectQuery.append(NODE_LINK.replace(PARENT_TAG, "parent")
			        .replace(CHILD_TAG, "child" + count));
			subjectQuery.append(" SET child" + count + ".status='"
			        + subject.getEntityStatus().getStatusName() + "' ");
			subjectQuery.append(", child" + count + ".dataprovider='"
			        + getDataProvider(subject.getDataprovider()) + "' ");
			subjectQuery.append(", child" + count + ".tree='"
			        + getTree(subject.getTree()) + "' ");
			count++;
			parentQuery.append(subjectQuery);
		}
		try {
			Neo4jUtils.queryNeo4j(neo4jURI, parentQuery.toString(), null,
			        ROW.toString());
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
	}

	private String getDataProvider(List<FieldMapDTO> dataprovider) {

		if (CollectionUtils.isEmpty(dataprovider)) {
			return new String();
		}
		Gson gson = new Gson();
		String dataproviderString = gson.toJson(dataprovider);
		try {
			dataproviderString = URLEncoder.encode(dataproviderString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error while encoding dataprovider", e);
		}

		return dataproviderString;
	}

	public static void main(String[] args) {
		FieldMapDTO f = new FieldMapDTO();
		f.setKey("name");
		f.setValue("row01column01");

		FieldMapDTO f1 = new FieldMapDTO();
		f1.setKey("type");
		f1.setValue("seat");

		List<FieldMapDTO> fiel = new ArrayList<FieldMapDTO>();
		fiel.add(f);
		fiel.add(f1);
		HierarchyRepositoryImpl h = new HierarchyRepositoryImpl();
		System.out.println(h.getDataProvider(fiel));;
	}

	private String getTree(FieldMapDTO tree) {

		if (tree == null) {
			return new String();
		}
		Gson gson = new Gson();
		String treeString = gson.toJson(tree);
		try {
			treeString = URLEncoder.encode(treeString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error while encoding dataprovider", e);
		}

		return treeString;
	}

	@Override
	public Integer insertTenantHierarchy(HierarchyEntity hierarchyEntity) {
		LOGGER.debug("<<--Entry createANodeWithProperties-->>");
		String query = "CREATE (n:{entityType} {props}) RETURN id(n)".replace(
		        "{entityType}", hierarchyEntity.getEntityType());

		String neo4jResponse = null;
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(DOMAIN.getVariableName(),
			        hierarchyEntity.getDomain());
			parameters.put(IDENTITY_KEY.getVariableName(),
			        hierarchyEntity.getIdentifierKey());
			parameters.put(IDENTITY_VALUE.getVariableName(),
			        hierarchyEntity.getIdentifierValue());
			parameters.put(TEMPLATE_NAME.getVariableName(),
			        hierarchyEntity.getTemplateName());
			parameters.put(MY_DOMAIN.getVariableName(),
			        hierarchyEntity.getMyDomain());
			parameters.put(DATAPROVIDER.getVariableName(),
			        hierarchyEntity.getDataprovider());
			parameters.put(TREE.getVariableName(), hierarchyEntity.getTree());

			parameters.put("status", hierarchyEntity.getStatus());
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, parameters,
			        ROW.toString());
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		int createdNodeId = -1;
		if (neo4jResponse != null) {
			JSONObject resultJson;
			try {
				resultJson = new JSONObject(neo4jResponse);
				JSONArray resultsArray = resultJson.getJSONArray(RESULTS
				        .getFieldName());
				JSONObject resultJsonObject = resultsArray.getJSONObject(0);

				JSONArray jsonArray = resultJsonObject.getJSONArray(DATA
				        .getFieldName());
				createdNodeId = jsonArray.getJSONObject(0)
				        .getJSONArray(ROW.toString()).getInt(0);
			} catch (JSONException e) {
				throw new RuntimeException("Error in HierarchyRepository" + e);
			}
		}
		createRelationship(hierarchyEntity.getActorTenantDomain(),
		        hierarchyEntity.getMyDomain(),
		        hierarchyEntity.getRelationship());

		LOGGER.debug("<<--Exit createANodeWithProperties-->>");

		return createdNodeId;
	}

	private Object createRelationship(String actorMyDomian, String myDomain,
	        String relationship) {

		LOGGER.debug("<<--Entry createRelationshipwithNodes-->>");

		String neo4jResponse = null;
		String query = "MATCH a,b WHERE a.myDomain='{actorMyDomian}' AND b.myDomain='{myDomain}' CREATE (a)-[r:{relationship}]->(b) RETURN r"
		        .replace("{actorMyDomian}", actorMyDomian)
		        .replace("{myDomain}", myDomain)
		        .replace("{relationship}", relationship);
		try {
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		LOGGER.debug("<<--Exit createRelationshipwithNodes-->>");
		return neo4jResponse;
	}

	public List<String> getHierarchyByTenantDomain(String parentDomain,
	        String myDomain) {
		String getHierarchyQuery = "MATCH n-[r:Child]->m WHERE n.myDomain='{parentDomain}' and m.myDomain='{myDomain}' RETURN m.myDomain as myDomain"
		        .replace("{parentDomain}", parentDomain).replace("{myDomain}",
		                myDomain);
		return queryNeo4JAndPrepareEntityIds(getHierarchyQuery);
	}

	private List<String> queryNeo4JAndPrepareEntityIds(String query) {
		List<String> myDomainList = new ArrayList<>();
		try {
			String neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			if (neo4jResponse == null) {
				return Collections.emptyList();
			}
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			jsonArray.toString();
			for (int i = 0; i < jsonArray.length(); i++) {
				Object object = jsonArray.getJSONObject(i)
				        .getJSONArray(ROW.toString()).get(0);
				myDomainList.add(object.toString());
			}
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		return myDomainList;
	}

	@Override
	public HashSet<String> getChildrenOfEntity(String parent,
	        Set<String> children) {

		String parent_query = CHILD_HIERARCHY.replace(PARENT_TAG, parent)
		        .replace(CHILD_TAG, children.toString());
		HashSet<String> childSet = new HashSet<>();
		try {
			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, parent_query, null, ROW.toString());
			if (jsonarray == null) {
				return new HashSet<String>();
			}
			jsonarray = jsonarray.getJSONObject(0).getJSONArray(
			        ROW.getFieldName());

			Gson lowerCaseUnderScoreGson = objectBuilderUtil
			        .getLowerCaseUnderScoreGson();
			childSet = lowerCaseUnderScoreGson.fromJson(
			        jsonarray.getJSONArray(0).toString(), HashSet.class);
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

		return childSet;
	}

	@Override
	public HashSet<String> getParentsOfEntity(String child, Set<String> parents) {

		String parent_query = PARENT_HIERARCHY.replace(CHILD_TAG, child)
		        .replace(PARENT_TAG, parents.toString());
		HashSet<String> parrens = new HashSet<>();
		try {
			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, parent_query, null, ROW.toString());
			if (jsonarray == null) {
				return new HashSet<String>();
			}
			jsonarray = jsonarray.getJSONObject(0).getJSONArray(
			        ROW.getFieldName());

			Gson lowerCaseUnderScoreGson = objectBuilderUtil
			        .getLowerCaseUnderScoreGson();
			parrens = lowerCaseUnderScoreGson.fromJson(jsonarray
			        .getJSONArray(0).toString(), HashSet.class);
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

		return parrens;
	}

	@Override
	public boolean isTenantExist(String domain) {
		String query = TENANT_NODE.replace(DOMAIN_TAG, domain);
		boolean flag = true;
		try {
			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, query, null, ROW.toString());
			if (jsonarray == null) {
				flag = false;
			}
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

		return flag;
	}

	@Override
	public String getTenantDomainName(EntityDTO entity) {
		String query = GET_TENANT
		        .replace(IDENTIFIER_KEY_TAG, entity.getIdentifier().getKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                entity.getIdentifier().getValue())
		        .replace(TEMPLATE_NAME_TAG,
		                entity.getEntityTemplate().getEntityTemplateName())
		        .replace(DOMAIN_TAG, entity.getDomain().getDomainName());
		String domain = "";
		try {

			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, query, null, ROW.toString());

			if (jsonarray != null) {
				jsonarray = jsonarray.getJSONObject(0).getJSONArray(
				        ROW.getFieldName());
				if (jsonarray.length() > 1) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.DUPLICATE_RECORDS);
				}
				domain = jsonarray.getString(0);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

		return domain;
	}

	@Override
	public List<String> getTenantsDomainNames(List<EntityDTO> tenants) {
		StringBuffer query = new StringBuffer();
		StringBuffer returnString = new StringBuffer();
		int count = 0;
		for (EntityDTO tenant : tenants) {
			query.append(GET_TENANTS
			        .replace("{count}", count + "")
			        .replace(IDENTIFIER_KEY_TAG,
			                tenant.getIdentifier().getKey())
			        .replace(IDENTIFIER_VALUE_TAG,
			                tenant.getIdentifier().getValue())
			        .replace(TEMPLATE_NAME_TAG,
			                tenant.getEntityTemplate().getEntityTemplateName())
			        .replace(DOMAIN_TAG, tenant.getDomain().getDomainName()));
			if (!returnString.toString().isEmpty()) {
				returnString.append(", ");
			}
			returnString.append("node" + count + ".myDomain");
			count++;
		}
		query.append(" return " + returnString.toString());
		List<String> myDomains = new ArrayList<String>();
		try {

			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, query.toString(), null, ROW.toString());

			if (jsonarray != null) {
				jsonarray = jsonarray.getJSONObject(0).getJSONArray(
				        ROW.getFieldName());
				for (count = 0; count < jsonarray.length(); count++) {
					myDomains.add(jsonarray.getString(count));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		return myDomains;
	}

	@Override
	public List<HierarchyEntity> getChildren(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType) {
		String query = GET_CHILDREN;
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		query = appendEntityType(query, searchEntityType);
		String additionalConditions = "";
		additionalConditions = appendEntityTemplate(additionalConditions,
		        searchTemplate, hierarchyEntity.getStatus());
		query = query
		        .replace(IDENTIFIER_KEY_TAG, hierarchyEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                hierarchyEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, hierarchyEntity.getTemplateName())
		        .replace(DOMAIN_TAG, hierarchyEntity.getDomain())
		        .replace(ENTITY_TYPE, hierarchyEntity.getEntityType())
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);
		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			if (neo4jResponse != null) {
				JSONObject jsonObject = new JSONObject(neo4jResponse);
				JSONArray jsonArray = jsonObject
				        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
				        .getJSONArray(DATA.getFieldName());
				if (jsonArray.length() > 0) {

					for (int i = 0; i < jsonArray.length(); i++) {
						HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
						        .getJSONObject(i));
						hierarchies.add(childHierarchy);
					}
				}
			} else {
				throw new NoResultException();
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public Integer getChildrenCount(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType) {

		String query = GET_CHILDREN_COUNT;
		query = appendEntityType(query, searchEntityType);
		String additionalConditions = "";
		additionalConditions = appendEntityTemplate(additionalConditions,
		        searchTemplate, hierarchyEntity.getStatus());

		query = query
		        .replace(IDENTIFIER_KEY_TAG, hierarchyEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                hierarchyEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, hierarchyEntity.getTemplateName())
		        .replace(DOMAIN_TAG, hierarchyEntity.getDomain())
		        .replace(ENTITY_TYPE, hierarchyEntity.getEntityType())
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);
		Integer count = 0;
		try {
			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, query, null, ROW.toString());

			if (jsonarray != null) {
				jsonarray = jsonarray.getJSONObject(0).getJSONArray(
				        ROW.getFieldName());

				count = (Integer)jsonarray.get(0);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		return count;
	}

	private HierarchyEntity prepareHierarchyEntity(JSONObject jsonObject)
	        throws JSONException {
		HierarchyEntity hierarchyEntity = new HierarchyEntity();
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.toString());

		Object domainObj = jsonArray.get(0);
		if (domainObj != null) {
			hierarchyEntity.setDomain(domainObj.toString());
		}
		Object templateObj = jsonArray.get(1);
		if (templateObj != null) {
			hierarchyEntity.setTemplateName(templateObj.toString());
		}
		Object keyObj = jsonArray.get(2);
		if (keyObj != null) {
			hierarchyEntity.setIdentifierKey(keyObj.toString());
		}
		Object valueObj = jsonArray.get(3);
		if (valueObj != null) {
			hierarchyEntity.setIdentifierValue(valueObj.toString());
		}
		Object entTypeObj = jsonArray.get(4);
		if (entTypeObj != null) {
			hierarchyEntity.setEntityType(entTypeObj.toString());
		}
		Object dataproviderObj = jsonArray.get(5);
		if (dataproviderObj != null) {
			hierarchyEntity.setDataprovider(dataproviderObj.toString());
		}
		Object treeObj = jsonArray.get(6);
		if (treeObj != null) {
			hierarchyEntity.setTree(treeObj.toString());
		}
		Object statusObj = jsonArray.get(7);
		if (statusObj != null) {
			hierarchyEntity.setStatus(statusObj.toString());
		}
		return hierarchyEntity;
	}

	@Override
	public Integer getCountByStatus(String domain, String templateName,
	        String status, String entityType) {
		String query = GET_NODES_STATUS_COUNT.replace(DOMAIN_TAG, domain)
		        .replace(TEMPLATE_NAME_TAG, templateName)
		        .replace(STATUS, status).replace(ENTITY_TYPE, entityType);
		Integer count = 0;
		try {
			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, query, null, ROW.toString());

			if (jsonarray != null) {
				jsonarray = jsonarray.getJSONObject(0).getJSONArray(
				        ROW.getFieldName());

				count = (Integer)jsonarray.get(0);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		return count;
	}

	@Override
	public EntityDTO updateNode(EntityDTO entity, String dataprovider,
	        String tree) {

		StringBuffer nodeQuery = new StringBuffer(NODE_MATCH
		        .replace(NODE_TAG, "child")
		        .replace(NODE_NAME_TAG,
		                entity.getPlatformEntity().getPlatformEntityType())
		        .replace(IDENTIFIER_KEY_TAG, entity.getIdentifier().getKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                entity.getIdentifier().getValue())
		        .replace(TEMPLATE_NAME_TAG,
		                entity.getEntityTemplate().getEntityTemplateName())
		        .replace(DOMAIN_TAG, entity.getDomain().getDomainName()));

		nodeQuery.append(" SET child.status='"
		        + entity.getEntityStatus().getStatusName()
		        + "',child.dataprovider='" + dataprovider + "',child.tree='"
		        + tree + "' Return child");

		try {
			String queryNeo4j = Neo4jUtils.queryNeo4j(neo4jURI,
			        nodeQuery.toString(), null, ROW.toString());
			if (StringUtils.isBlank(queryNeo4j)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		return entity;
	}

	@Override
	public List<DistributionEntity> getEntityDistributionCount(
	        String parentDomain, String parentEntityType,
	        String searchTemplateName, String searchEntityType, String status) {

		String additionalConditions = "";

		if (isNotBlank(status)) {
			additionalConditions += DISTRIBUTION_STATUS_CONDITION.replace(
			        STATUS, status);
		}

		String query = DISTRIBUTION_COUNT.replace(DOMAIN_TAG, parentDomain)
		        .replace(ENTITY_TYPE, parentEntityType)
		        .replace(CHILD_ENTITY_TYPE, searchEntityType)
		        .replace(CHILD_TEMPLATE_NAME, searchTemplateName)
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);

		List<DistributionEntity> distributions = new ArrayList<DistributionEntity>();
		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			if (StringUtils.isBlank(neo4jResponse)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					DistributionEntity distributedEntity = prepareDistributionEntity(jsonArray
					        .getJSONObject(i));
					distributions.add(distributedEntity);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return distributions;
	}

	private DistributionEntity prepareDistributionEntity(JSONObject jsonObject)
	        throws JSONException {
		DistributionEntity distributionEntity = new DistributionEntity();
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.toString());

		Object identifierValue = jsonArray.get(0);
		if (identifierValue != null) {
			distributionEntity.setIdentifierValue(identifierValue.toString());
		}
		Object countObj = jsonArray.get(1);
		if (countObj != null) {
			distributionEntity.setCount(Integer.parseInt(countObj.toString()));
		}

		return distributionEntity;
	}

	@Override
	public List<HierarchyEntity> getEntityDistribution(String parentDomain,
	        String parentEntityType, String searchTemplateName,
	        String searchEntityType, String status) {
		String additionalConditions = "";

		if (isNotBlank(status)) {
			additionalConditions += DISTRIBUTION_STATUS_CONDITION.replace(
			        STATUS, status);
		}
		String query = DISTRIBUTION_ENTITIES.replace(DOMAIN_TAG, parentDomain)
		        .replace(ENTITY_TYPE, parentEntityType)
		        .replace(CHILD_ENTITY_TYPE, searchEntityType)
		        .replace(CHILD_TEMPLATE_NAME, searchTemplateName)
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);

		List<HierarchyEntity> distributions = new ArrayList<HierarchyEntity>();
		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			if (StringUtils.isBlank(neo4jResponse)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {

					HierarchyEntity distributedEntity = prepareHierarchyEntity(jsonArray
					        .getJSONObject(i));
					distributions.add(distributedEntity);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return distributions;
	}

	@Override
	public List<HierarchyEntity> getImmediateChildren(
	        HierarchyEntity hierarchyEntity, String searchTemplate,
	        String searchEntityType) {
		String query = GET_IMMEDIATE_CHILDREN;
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		query = appendEntityType(query, searchEntityType);
		String additionalConditions = "";
		additionalConditions = appendEntityTemplate(additionalConditions,
		        searchTemplate, hierarchyEntity.getStatus());
		query = query
		        .replace(IDENTIFIER_KEY_TAG, hierarchyEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                hierarchyEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, hierarchyEntity.getTemplateName())
		        .replace(DOMAIN_TAG, hierarchyEntity.getDomain())
		        .replace(ENTITY_TYPE, hierarchyEntity.getEntityType())
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);
		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public List<HierarchyEntity> getImmediateParents(
	        HierarchyEntity hierarchyEntity, String searchTemplate,
	        String searchEntityType) {

		String query = GET_IMMEDIATE_PARENTS;
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		query = appendEntityType(query, searchEntityType);
		String additionalConditions = "";
		additionalConditions = appendEntityTemplate(additionalConditions,
		        searchTemplate, hierarchyEntity.getStatus());
		query = query
		        .replace(IDENTIFIER_KEY_TAG, hierarchyEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                hierarchyEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, hierarchyEntity.getTemplateName())
		        .replace(DOMAIN_TAG, hierarchyEntity.getDomain())
		        .replace(ENTITY_TYPE, hierarchyEntity.getEntityType())
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);

		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public boolean isChildDomain(String parentDomain, String domain) {

		String query = GET_CHILD_TENANT.replace(DOMAIN_TAG, domain).replace(
		        PARENT_TAG, parentDomain);
		boolean flag = false;
		try {
			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, query, null, ROW.toString());
			// if (jsonarray == null) {
			// String valdateDomainQuery = VALIDATE_TENANT.replace(DOMAIN_TAG,
			// domain).replace(PARENT_TAG, parentDomain);
			// org.json.JSONArray jsonarrayDomain = Neo4jExecuter
			// .exexcuteQuery(neo4jURI, valdateDomainQuery, null,
			// ROW.toString());
			// if (jsonarrayDomain == null) {
			// flag = false;
			// }
			//
			// }
			if (jsonarray != null) {
				flag = true;
			}
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

		return flag;
	}

	@Override
	public boolean isNodeExist(EntityDTO entityDTO) {
		String query = GET_NODE
		        .replace(IDENTIFIER_KEY_TAG, entityDTO.getIdentifier().getKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                entityDTO.getIdentifier().getValue())
		        .replace(TEMPLATE_NAME_TAG,
		                entityDTO.getEntityTemplate().getEntityTemplateName())
		        .replace(DOMAIN_TAG, entityDTO.getDomain().getDomainName())
		        .replace(ENTITY_TYPE,
		                entityDTO.getPlatformEntity().getPlatformEntityType());
		boolean flag = true;
		try {
			org.json.JSONArray jsonarray = Neo4jExecuter.exexcuteQuery(
			        neo4jURI, query, null, ROW.toString());
			if (jsonarray == null) {
				flag = false;
			}
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

		return flag;
	}

	@Override
	public List<HierarchyEntity> getParents(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType) {
		String query = GET_PARENTS;
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		query = appendEntityType(query, searchEntityType);
		String additionalConditions = "";
		additionalConditions = appendEntityTemplate(additionalConditions,
		        searchTemplate, hierarchyEntity.getStatus());
		query = query
		        .replace(IDENTIFIER_KEY_TAG, hierarchyEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                hierarchyEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, hierarchyEntity.getTemplateName())
		        .replace(DOMAIN_TAG, hierarchyEntity.getDomain())
		        .replace(ENTITY_TYPE, hierarchyEntity.getEntityType())
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);
		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			if (neo4jResponse != null) {
				JSONObject jsonObject = new JSONObject(neo4jResponse);
				JSONArray jsonArray = jsonObject
				        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
				        .getJSONArray(DATA.getFieldName());
				if (jsonArray.length() > 0) {

					for (int i = 0; i < jsonArray.length(); i++) {
						HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
						        .getJSONObject(i));
						hierarchies.add(childHierarchy);
					}
				}
			} else {
				throw new NoResultException();
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	private String appendEntityType(String query, String searchEntityType) {
		if (isNotBlank(searchEntityType)) {
			query = query.replace(CHILD_ENTITY_TYPE, ":" + searchEntityType);
		} else {
			query = query.replace(CHILD_ENTITY_TYPE, "");
		}

		return query;
	}

	private String appendEntityTemplate(String additionalConditions,
	        String searchTemplate, String statusName) {

		if (isNotBlank(searchTemplate)) {
			additionalConditions = CHILD_TEMPLATE_CONDITION.replace(
			        CHILD_TEMPLATE_NAME, searchTemplate);
		}
		if (isNotBlank(statusName)) {
			if (isNotBlank(additionalConditions)) {
				additionalConditions += DISTRIBUTION_STATUS_CONDITION.replace(
				        STATUS, statusName);
			} else {
				additionalConditions += STATUS_CONDITION.replace(STATUS,
				        statusName);
			}
		}

		return additionalConditions;
	}

	@Override
	public List<HierarchyEntity> getAssignableMarkers(
	        HierarchyEntity hierarchyEntity, String searchTemplateName,
	        String markerTemplateName) {
		String query = GET_ASSIGNABLE_MARKERS;
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		String additionalConditions = "";
		if (isNotEmpty(searchTemplateName)) {
			additionalConditions = NO_OUT_GOING_RELATIONS.replace(
			        MARKER_TEMPLATE_NAME_TAG, searchTemplateName);
		}
		query = query
		        .replace(IDENTIFIER_KEY_TAG, hierarchyEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                hierarchyEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, hierarchyEntity.getTemplateName())
		        .replace(DOMAIN_TAG, hierarchyEntity.getDomain())
		        .replace(ENTITY_TYPE, hierarchyEntity.getEntityType())
		        .replace(MARKER_TEMPLATE_NAME_TAG, markerTemplateName)
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);

		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public List<HierarchyEntity> getAllOwnedMarkersByDomain(
	        HierarchyEntity hierarchyEntity, String searchTemplateName,
	        String markerTemplateName) {
		String query = GET_ASSIGNABLE_MARKERS;
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		String additionalConditions = "";
		if (isNotEmpty(searchTemplateName)) {
			additionalConditions = NO_OUT_GOING_RELATIONS.replace(
			        MARKER_TEMPLATE_NAME_TAG, searchTemplateName);
		}
		query = query
		        .replace(IDENTIFIER_KEY_TAG, hierarchyEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                hierarchyEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, hierarchyEntity.getTemplateName())
		        .replace(DOMAIN_TAG, hierarchyEntity.getDomain())
		        .replace(ENTITY_TYPE, hierarchyEntity.getEntityType())
		        .replace(MARKER_TEMPLATE_NAME_TAG, markerTemplateName)
		        .replace(ADDITIONAL_CONDITIONS, additionalConditions);

		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public List<HierarchyEntity> getTenantsWithinRange(
	        HierarchyEntity startTenantEntity, HierarchyEntity endTenantEntity) {
		String query = GET_TENANTS_WITHIN_RANGE;

		query = query
		        .replace(IDENTIFIER_KEY_TAG,
		                startTenantEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                startTenantEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, startTenantEntity.getTemplateName())
		        .replace(DOMAIN_TAG, startTenantEntity.getDomain())
		        .replace(END_IDENTIFIER_KEY_TAG,
		                endTenantEntity.getIdentifierKey())
		        .replace(END_IDENTIFIER_VALUE_TAG,
		                endTenantEntity.getIdentifierValue())
		        .replace(END_TEMPLATE_NAME_TAG,
		                endTenantEntity.getTemplateName())
		        .replace(END_DOMAIN_TAG, endTenantEntity.getDomain());
		org.json.JSONArray jsonArray = null;
		try {

			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (IOException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		@SuppressWarnings("serial") Type type = new TypeToken<List<HierarchyEntity>>() {
		}.getType();
		List<HierarchyEntity> hierarchies = objectBuilderUtil.getGson()
		        .fromJson(
		                jsonArray.getJSONObject(0)
		                        .getJSONArray(ROW.getFieldName())
		                        .getJSONArray(0).getJSONArray(0).toString(),
		                type);
		return hierarchies;
	}

	@Override
	public HierarchyEntity getTenantNode(String domain) {
		String query = TENANT_NODE_DETAILS.replace(DOMAIN_TAG, domain);
		HierarchyEntity entity = new HierarchyEntity();
		try {

			String neo4jResponse = null;

			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					entity = prepareHierarchyEntity(jsonArray.getJSONObject(i));
				}
			}
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

		return entity;
	}

	@Override
	public void attach(AttachHierarchyDTO hierarchy) {

		Actor actor = hierarchy.getActor();
		Subject subject1 = hierarchy.getSubject();

		StringBuffer parentQuery = null;
		if (hierarchy.getActorType().equals(ENTITY.name())) {
			EntityDTO parent = actor.getEntity();
			parentQuery = new StringBuffer(NODE_MERGE
			        .replace(NODE_TAG, "parent")
			        .replace(NODE_NAME_TAG,
			                parent.getPlatformEntity().getPlatformEntityType())
			        .replace(IDENTIFIER_KEY_TAG,
			                parent.getIdentifier().getKey())
			        .replace(IDENTIFIER_VALUE_TAG,
			                parent.getIdentifier().getValue())
			        .replace(TEMPLATE_NAME_TAG,
			                parent.getEntityTemplate().getEntityTemplateName())
			        .replace(DOMAIN_TAG, parent.getDomain().getDomainName()));

			parentQuery.append(" SET parent.status='"
			        + parent.getEntityStatus().getStatusName() + "' ");
			parentQuery.append(", parent.dataprovider='"
			        + getDataProvider(parent.getDataprovider()) + "' ");
			parentQuery.append(", parent.tree='" + getTree(parent.getTree())
			        + "' ");

		} else if (hierarchy.getActorType().equals(TEMPLATE.name())) {
			EntityTemplateDTO parent = actor.getTemplate();
			parentQuery = new StringBuffer(TEMPLATE_NODE_MERGE
			        .replace(NODE_TAG, "parent")
			        .replace(NODE_NAME_TAG, TEMPLATE.name())
			        .replace(NAME_TAG, parent.getEntityTemplateName())
			        .replace(TYPE_TAG, parent.getPlatformEntityType())
			        .replace(DOMAIN_TAG, parent.getDomain().getDomainName()));
		}

		int count = 0;
		if (hierarchy.getSubjectType().equals(ENTITY.name())) {
			for (EntityDTO subject : subject1.getEntities()) {

				StringBuffer subjectQuery = new StringBuffer(NODE_MERGE
				        .replace(NODE_TAG, "child" + count)
				        .replace(
				                NODE_NAME_TAG,
				                subject.getPlatformEntity()
				                        .getPlatformEntityType())
				        .replace(IDENTIFIER_KEY_TAG,
				                subject.getIdentifier().getKey())
				        .replace(IDENTIFIER_VALUE_TAG,
				                subject.getIdentifier().getValue())
				        .replace(
				                TEMPLATE_NAME_TAG,
				                subject.getEntityTemplate()
				                        .getEntityTemplateName())
				        .replace(DOMAIN_TAG,
				                subject.getDomain().getDomainName()));

				subjectQuery.append(NODE_ANY_LINK.replace(PARENT_TAG, "parent")
				        .replace(CHILD_TAG, "child" + count)
				        .replace(ANY_LINK, hierarchy.getRelationName()));
				subjectQuery.append(" SET child" + count + ".status='"
				        + subject.getEntityStatus().getStatusName() + "' ");
				subjectQuery.append(", child" + count + ".dataprovider='"
				        + getDataProvider(subject.getDataprovider()) + "' ");
				subjectQuery.append(", child" + count + ".tree='"
				        + getTree(subject.getTree()) + "' ");
				count++;
				parentQuery.append(subjectQuery);
			}
		} else if (hierarchy.getSubjectType().equals(TEMPLATE.name())) {
			for (EntityTemplateDTO subject : subject1.getTemplates()) {
				StringBuffer subjectQuery = new StringBuffer(
				        TEMPLATE_NODE_MERGE
				                .replace(NODE_TAG, "child" + count)
				                .replace(NODE_NAME_TAG, TEMPLATE.name())
				                .replace(NAME_TAG,
				                        subject.getEntityTemplateName())
				                .replace(TYPE_TAG,
				                        subject.getPlatformEntityType())
				                .replace(DOMAIN_TAG,
				                        subject.getDomain().getDomainName()));

				subjectQuery.append(NODE_ANY_LINK.replace(PARENT_TAG, "parent")
				        .replace(CHILD_TAG, "child" + count)
				        .replace(ANY_LINK, hierarchy.getRelationName()));
				subjectQuery.append(" SET child" + count + ".status='"
				        + subject.getStatus().getStatusName() + "' ");
				count++;
				parentQuery.append(subjectQuery);
			}
		}

		try {
			LOGGER.info("Query for attaching subjects to an actor : "
			        + parentQuery.toString());
			Neo4jUtils.queryNeo4j(neo4jURI, parentQuery.toString(), null,
			        ROW.toString());
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
	}

	@Override
	public void attachActorsToASubject(AttachHierarchyDTO hierarchy) {
		Actor actor = hierarchy.getActor();
		Subject subject = hierarchy.getSubject();

		StringBuffer subjectQuery = null;
		HierarchyNodeTypes hntSubjectType = HierarchyNodeTypes
		        .getEnum(hierarchy.getSubjectType());
		switch (hntSubjectType) {
			case ENTITY :
				EntityDTO subjectEntity = subject.getEntity();
				subjectQuery = new StringBuffer(NODE_MERGE
				        .replace(NODE_TAG, "child")
				        .replace(
				                NODE_NAME_TAG,
				                subjectEntity.getPlatformEntity()
				                        .getPlatformEntityType())
				        .replace(IDENTIFIER_KEY_TAG,
				                subjectEntity.getIdentifier().getKey())
				        .replace(IDENTIFIER_VALUE_TAG,
				                subjectEntity.getIdentifier().getValue())
				        .replace(
				                TEMPLATE_NAME_TAG,
				                subjectEntity.getEntityTemplate()
				                        .getEntityTemplateName())
				        .replace(DOMAIN_TAG,
				                subjectEntity.getDomain().getDomainName()));

				subjectQuery.append(" SET child.status='"
				        + subjectEntity.getEntityStatus().getStatusName()
				        + "' ");
				subjectQuery.append(", child.dataprovider='"
				        + getDataProvider(subjectEntity.getDataprovider())
				        + "' ");
				subjectQuery.append(", child.tree='"
				        + getTree(subjectEntity.getTree()) + "' ");
				break;
			case TEMPLATE :
				// TODO
				break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
		}

		HierarchyNodeTypes hntActorType = HierarchyNodeTypes.getEnum(hierarchy
		        .getActorType());
		switch (hntActorType) {
			case ENTITY :
				int count = 0;
				for (EntityDTO actorEntity : actor.getEntities()) {
					StringBuffer actorQuery = new StringBuffer(NODE_MERGE
					        .replace(NODE_TAG, "parent" + count)
					        .replace(
					                NODE_NAME_TAG,
					                actorEntity.getPlatformEntity()
					                        .getPlatformEntityType())
					        .replace(IDENTIFIER_KEY_TAG,
					                actorEntity.getIdentifier().getKey())
					        .replace(IDENTIFIER_VALUE_TAG,
					                actorEntity.getIdentifier().getValue())
					        .replace(
					                TEMPLATE_NAME_TAG,
					                actorEntity.getEntityTemplate()
					                        .getEntityTemplateName())
					        .replace(DOMAIN_TAG,
					                actorEntity.getDomain().getDomainName()));

					actorQuery.append(NODE_ANY_LINK
					        .replace(PARENT_TAG, "parent" + count)
					        .replace(CHILD_TAG, "child")
					        .replace(ANY_LINK, hierarchy.getRelationName()));

					actorQuery.append(" SET parent" + count + ".status='"
					        + actorEntity.getEntityStatus().getStatusName()
					        + "' ");
					actorQuery.append(", parent" + count + ".dataprovider='"
					        + getDataProvider(actorEntity.getDataprovider())
					        + "' ");
					actorQuery.append(", parent" + count + ".tree='"
					        + getTree(actorEntity.getTree()) + "' ");
					count++;

					subjectQuery.append(actorQuery);
				}
				break;
			// case TEMPLATE : TODO
			// break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
		}
		try {
			Neo4jUtils.queryNeo4j(neo4jURI, subjectQuery.toString(), null,
			        ROW.toString());
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}

	}

	private String appendAdditionalConditions(String additionalConditions,
	        List<EntityTemplateDTO> searchTemplates) {

		List<String> templateWhereString = new ArrayList<String>();
		Set<String> entityTypeWhereString = new HashSet<String>();

		if (CollectionUtils.isNotEmpty(searchTemplates)) {
			for (EntityTemplateDTO template : searchTemplates) {
				templateWhereString.add(" m.templateName='"
				        + template.getEntityTemplateName() + "' ");
				entityTypeWhereString.add(" m:"
				        + template.getPlatformEntityType() + " ");
			}
			List<String> itemsToJoin = new ArrayList<String>();
			itemsToJoin.addAll(entityTypeWhereString);
			itemsToJoin.addAll(templateWhereString);

			String sr = null;
			for (String item : itemsToJoin) {
				if (isBlank(sr)) {
					sr = item;
				}
				sr += " OR " + item;
			}
			if (StringUtils.isNotBlank(sr)) {
				additionalConditions = " WHERE " + sr;
			}
		} else {
			// search templates not specified
			additionalConditions = " WHERE not m:TEMPLATE ";
		}
		return additionalConditions;
	}

	private String getAllEntitySubjectsActorEntity(String query,
	        AttachHierarchySearchDTO attachHierarchySearch) {

		EntityDTO actorEntity = attachHierarchySearch.getActor().getEntity();

		query = GET_ALL_ENTITY_SUBJECTS;
		String templateSearchCondition = "";
		templateSearchCondition = appendAdditionalConditions(
		        templateSearchCondition,
		        attachHierarchySearch.getSearchTemplates());

		query = query
		        .replace(IDENTIFIER_KEY_TAG,
		                actorEntity.getIdentifier().getKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                actorEntity.getIdentifier().getValue())
		        .replace(TEMPLATE_NAME_TAG,
		                actorEntity.getEntityTemplate().getEntityTemplateName())
		        .replace(DOMAIN_TAG, actorEntity.getDomain().getDomainName())
		        .replace(ENTITY_TYPE,
		                actorEntity.getPlatformEntity().getPlatformEntityType());

		if (isNotBlank(templateSearchCondition)) {
			if (isNotBlank(attachHierarchySearch.getStatusName())) {
				String statusCondition = "AND m.status='{status}'".replace(
				        STATUS, attachHierarchySearch.getStatusName());
				query = query.replace(ADDITIONAL_CONDITIONS,
				        templateSearchCondition + statusCondition);
			} else {
				query = query.replace(ADDITIONAL_CONDITIONS,
				        templateSearchCondition);
			}
		} else {
			if (isNotBlank(attachHierarchySearch.getStatusName())) {
				String statusCondition = "m.status='{status}'".replace(STATUS,
				        attachHierarchySearch.getStatusName());
				query = query.replace(ADDITIONAL_CONDITIONS, statusCondition);
			} else {
				query = query.replace(ADDITIONAL_CONDITIONS,
				        templateSearchCondition);
			}
		}
		query = query
		        .replace(ANY_LINK, attachHierarchySearch.getRelationName());
		return query;

	}

	private String getAllEntitySubjectsActorTemplate(String query,
	        AttachHierarchySearchDTO attachHierarchySearch) {

		EntityTemplateDTO tempalte = attachHierarchySearch.getActor()
		        .getTemplate();

		query = GET_ALL_ENTITY_SUBJECTS_ACTOR_TEMPLATE;
		String templateSearchCondition = "";
		templateSearchCondition = appendAdditionalConditions(
		        templateSearchCondition,
		        attachHierarchySearch.getSearchTemplates());

		query = query.replace(NAME_TAG, tempalte.getEntityTemplateName())
		        .replace(TYPE_TAG, tempalte.getPlatformEntityType())
		        .replace(DOMAIN_TAG, tempalte.getDomain().getDomainName())
		        .replace(ANY_LINK, attachHierarchySearch.getRelationName());

		if (isNotBlank(templateSearchCondition)) {
			if (isNotBlank(attachHierarchySearch.getStatusName())) {
				String statusCondition = "AND m.status='{status}'".replace(
				        STATUS, attachHierarchySearch.getStatusName());
				query = query.replace(ADDITIONAL_CONDITIONS,
				        templateSearchCondition + statusCondition);
			} else {
				query = query.replace(ADDITIONAL_CONDITIONS,
				        templateSearchCondition);
			}
		} else {
			if (isNotBlank(attachHierarchySearch.getStatusName())) {
				String statusCondition = "m.status='{status}'".replace(STATUS,
				        attachHierarchySearch.getStatusName());
				query = query.replace(ADDITIONAL_CONDITIONS, statusCondition);
			} else {
				query = query.replace(ADDITIONAL_CONDITIONS,
				        templateSearchCondition);
			}
		}
		query = query
		        .replace(ANY_LINK, attachHierarchySearch.getRelationName());
		return query;

	}

	@Override
	public List<HierarchyEntity> getAllEntitySubjects(
	        AttachHierarchySearchDTO attachHierarchySearch) {
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();

		String query = null;
		if (attachHierarchySearch.getActorType().equals(ENTITY.name())) {
			query = getAllEntitySubjectsActorEntity(query,
			        attachHierarchySearch);
		} else {
			query = getAllEntitySubjectsActorTemplate(query,
			        attachHierarchySearch);
		}
		try {
			String neo4jResponse = null;
			LOGGER.info("Query for getAllEntitySubjects : " + query.toString());
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonDataArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonDataArray.length() > 0) {
				int j = jsonDataArray.length();
				for (int i = 0; i < j; i++) {
					HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonDataArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	private HierarchyEntityTemplate prepareHierarchyTemplate(
	        JSONObject jsonObject) throws JSONException {
		HierarchyEntityTemplate hierarchyEntityTemplate = new HierarchyEntityTemplate();
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.toString());

		Object domainObj = jsonArray.get(0);
		if (domainObj != null) {
			hierarchyEntityTemplate.setDomain(domainObj.toString());
		}
		Object templateObj = jsonArray.get(1);
		if (templateObj != null) {
			hierarchyEntityTemplate.setName(templateObj.toString());
		}
		Object entTypeObj = jsonArray.get(2);
		if (entTypeObj != null) {
			hierarchyEntityTemplate.setType(entTypeObj.toString());
		}
		return hierarchyEntityTemplate;
	}

	@Override
	public List<HierarchyEntityTemplate> getAllTemplateSubjects(
	        AttachHierarchySearchDTO attachHierarchySearch) {
		List<HierarchyEntityTemplate> hierarchies = new ArrayList<HierarchyEntityTemplate>();

		String query = null;
		if (attachHierarchySearch.getActorType().equals(ENTITY.name())) {
			EntityDTO actorEntity = attachHierarchySearch.getActor()
			        .getEntity();

			query = GET_ALL_TEMPLATE_SUBJECTS;

			query = query
			        .replace(IDENTIFIER_KEY_TAG,
			                actorEntity.getIdentifier().getKey())
			        .replace(IDENTIFIER_VALUE_TAG,
			                actorEntity.getIdentifier().getValue())
			        .replace(
			                TEMPLATE_NAME_TAG,
			                actorEntity.getEntityTemplate()
			                        .getEntityTemplateName())
			        .replace(DOMAIN_TAG,
			                actorEntity.getDomain().getDomainName())
			        .replace(
			                ENTITY_TYPE,
			                actorEntity.getPlatformEntity()
			                        .getPlatformEntityType())
			        .replace(ANY_LINK, attachHierarchySearch.getRelationName());
		}
		try {
			String neo4jResponse = null;
			LOGGER.info("Query for getAllTemplateSubjects : "
			        + query.toString());
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					HierarchyEntityTemplate childHierarchy = prepareHierarchyTemplate(jsonArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public List<HierarchyEntity> getImmediateRoot(
	        AttachHierarchySearchDTO attachHierarchySearch) {

		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		// irrespective of nodeLabel
		String query = null;
		String actorType = attachHierarchySearch.getActorType();
		HierarchyNodeTypes hntActorType = HierarchyNodeTypes.getEnum(actorType);
		switch (hntActorType) {
			case ENTITY :
				EntityDTO actoryEntity = attachHierarchySearch.getActor()
				        .getEntity();
				query = GET_IMMEDIATE_ROOTS_ANY_LINK_ACTOR_ENTITY;
				query = query
				        .replace(IDENTIFIER_KEY_TAG,
				                actoryEntity.getIdentifier().getKey())
				        .replace(IDENTIFIER_VALUE_TAG,
				                actoryEntity.getIdentifier().getValue())
				        .replace(
				                TEMPLATE_NAME_TAG,
				                actoryEntity.getEntityTemplate()
				                        .getEntityTemplateName())
				        .replace(DOMAIN_TAG,
				                actoryEntity.getDomain().getDomainName())
				        .replace(
				                ENTITY_TYPE,
				                actoryEntity.getPlatformEntity()
				                        .getPlatformEntityType())
				        .replace(ANY_LINK,
				                attachHierarchySearch.getRelationName());
				break;
			case TEMPLATE :
				EntityTemplateDTO template = attachHierarchySearch.getActor()
				        .getTemplate();
				query = GET_IMMEDIATE_ROOTS_ANY_LINK_ACTOR_TEMPLATE;
				query = query
				        .replace(NAME_TAG, template.getEntityTemplateName())
				        .replace(TYPE_TAG, template.getPlatformEntityType())
				        .replace(DOMAIN_TAG,
				                template.getDomain().getDomainName())
				        .replace(ANY_LINK,
				                attachHierarchySearch.getRelationName());
				break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ACTOR.getDescription());
		}

		query = appendEntityType(query, StringUtils.EMPTY);
		String additionalConditions = "";
		// irrespective of where clause on template and status
		additionalConditions = appendEntityTemplate(additionalConditions,
		        StringUtils.EMPTY, StringUtils.EMPTY);
		query = query.replace(ADDITIONAL_CONDITIONS, additionalConditions);
		try {
			String neo4jResponse = null;
			LOGGER.info("Query for getImmediateParentsAnyLink : "
			        + query.toString());
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public List<TaggedEntity> getGeoTaggedEntities(String domain,
	        String templateName) {
		String query = GET_ALL_TAGGED_ENTITIES;
		List<TaggedEntity> taggedEntities = new ArrayList<TaggedEntity>();
		String additionalConditions = "";

		if (isNotBlank(templateName)) {
			additionalConditions = TAGGED_TEMPLATE_CONDITION.replace(
			        TEMPLATE_NAME_TAG, templateName);
		}
		query = query.replace(DOMAIN_TAG, domain).replace(
		        ADDITIONAL_CONDITIONS, additionalConditions);

		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					TaggedEntity taggedEntity = prepareTaggedEntity(jsonArray
					        .getJSONObject(i));
					taggedEntities.add(taggedEntity);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}

		return taggedEntities;
	}

	private TaggedEntity prepareTaggedEntity(JSONObject jsonObject)
	        throws JSONException {
		TaggedEntity taggedEntity = new TaggedEntity();
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.toString());

		Object geotagDomainObj = jsonArray.get(0);
		if (geotagDomainObj != null) {
			taggedEntity.setGeotagDomain(geotagDomainObj.toString());
		}
		Object geotagTemplateObj = jsonArray.get(1);
		if (geotagTemplateObj != null) {
			taggedEntity.setGeotagTemplate(geotagTemplateObj.toString());
		}
		Object geotagKeyObj = jsonArray.get(2);
		if (geotagKeyObj != null) {
			taggedEntity.setGeotagIdentifierKey(geotagKeyObj.toString());
		}
		Object geotagStatusObj = jsonArray.get(3);
		if (geotagStatusObj != null) {
			taggedEntity.setGeotagStatus(geotagStatusObj.toString());
		}
		Object geotagValueObj = jsonArray.get(4);
		if (geotagValueObj != null) {
			taggedEntity.setGeotagIdentifierValue(geotagValueObj.toString());
		}
		Object geotagTypeObj = jsonArray.get(5);
		if (geotagTypeObj != null) {
			taggedEntity.setGeotagType(geotagTypeObj.toString());
		}
		Object geotagDataproviderObj = jsonArray.get(6);
		if (geotagDataproviderObj != null) {
			taggedEntity
			        .setGeotagDataprovider(geotagDataproviderObj.toString());
		}

		Object entityDomainObj = jsonArray.get(7);
		if (entityDomainObj != null) {
			taggedEntity.setEntityDomain(entityDomainObj.toString());
		}
		Object entityTemplateObj = jsonArray.get(8);
		if (entityTemplateObj != null) {
			taggedEntity.setEntityTemplateName(entityTemplateObj.toString());
		}
		Object entityKeyObj = jsonArray.get(9);
		if (entityKeyObj != null) {
			taggedEntity.setEntityIdentifierKey(entityKeyObj.toString());
		}
		Object entityStatusObj = jsonArray.get(10);
		if (entityStatusObj != null) {
			taggedEntity.setEntityStatus(entityStatusObj.toString());
		}
		Object entityValueObj = jsonArray.get(11);
		if (entityValueObj != null) {
			taggedEntity.setEntityIdentifierValue(entityValueObj.toString());
		}
		Object entityTypeObj = jsonArray.get(12);
		if (entityTypeObj != null) {
			taggedEntity.setEntityType(entityTypeObj.toString());
		}
		Object entityDataproviderObj = jsonArray.get(13);
		if (entityDataproviderObj != null) {
			taggedEntity
			        .setEntityDataprovider(entityDataproviderObj.toString());
		}

		return taggedEntity;
	}

	@Override
	public List<String> getTemplateNamesofAttachedEntities(String searchDomain,
	        String searchTemplateName) {
		String query = null;

		query = GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN.replace(
		        TEMPLATE_NAME_TAG, searchTemplateName).replace(DOMAIN_TAG,
		        searchDomain);

		org.json.JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(this.neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (IOException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		@SuppressWarnings("serial") Type type = new TypeToken<List<String>>() {
		}.getType();

		List<String> entityTemplateNames = objectBuilderUtil.getGson()
		        .fromJson(
		                jsonArray.getJSONObject(0)
		                        .getJSONArray(ROW.getFieldName())
		                        .getJSONArray(0).toString(), type);
		return entityTemplateNames;
	}

	@Override
	public EntityDTO updateStatus(EntityDTO entity) {
		return constructUpdateStatusQuery(entity);
	}

	/**
	 * @param entity
	 * @return
	 */
	private EntityDTO constructUpdateStatusQuery(EntityDTO entity) {
		StringBuffer nodeQuery = new StringBuffer(NODE_MATCH
		        .replace(NODE_TAG, "child")
		        .replace(NODE_NAME_TAG,
		                entity.getPlatformEntity().getPlatformEntityType())
		        .replace(IDENTIFIER_KEY_TAG, entity.getIdentifier().getKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                entity.getIdentifier().getValue())
		        .replace(TEMPLATE_NAME_TAG,
		                entity.getEntityTemplate().getEntityTemplateName())
		        .replace(DOMAIN_TAG, entity.getDomain().getDomainName()));

		nodeQuery.append(" SET child.status='"
		        + entity.getEntityStatus().getStatusName() + "' Return child");

		try {
			String queryNeo4j = Neo4jUtils.queryNeo4j(neo4jURI,
			        nodeQuery.toString(), null, ROW.toString());
			if (StringUtils.isBlank(queryNeo4j)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
		} catch (IOException | JSONException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		return entity;
	}

	@Override
	public GeneralBatchResponse updateStatus(List<IdentityDTO> entities,
	        String statusName) {

		GeneralBatchResponse batchResponse = new GeneralBatchResponse();
		List<GeneralResponse> responses = new ArrayList<GeneralResponse>();
		batchResponse.setResponses(responses);

		for (IdentityDTO identityDTO : entities) {
			GeneralResponse generalRespone = new GeneralResponse();
			responses.add(generalRespone);

			generalRespone.setReference(identityDTO.getIdentifier().getValue());
			StringBuffer nodeQuery = new StringBuffer(NODE_MATCH
			        .replace(NODE_TAG, "child")
			        .replace(
			                NODE_NAME_TAG,
			                identityDTO.getPlatformEntity()
			                        .getPlatformEntityType())
			        .replace(IDENTIFIER_KEY_TAG,
			                identityDTO.getIdentifier().getKey())
			        .replace(IDENTIFIER_VALUE_TAG,
			                identityDTO.getIdentifier().getValue())
			        .replace(
			                TEMPLATE_NAME_TAG,
			                identityDTO.getEntityTemplate()
			                        .getEntityTemplateName())
			        .replace(DOMAIN_TAG,
			                identityDTO.getDomain().getDomainName()));

			nodeQuery.append(" SET child.status='" + statusName
			        + "' Return child");

			try {
				String queryNeo4j = Neo4jUtils.queryNeo4j(neo4jURI,
				        nodeQuery.toString(), null, ROW.toString());
				if (StringUtils.isBlank(queryNeo4j)) {
					generalRespone.setStatus(Status.FAILURE);
					generalRespone.setRemarks("Entity node is Invalid");
				} else {
					generalRespone.setStatus(Status.SUCCESS);
				}
			} catch (IOException | JSONException e) {
				LOGGER.debug("Error updating the status", e.getMessage());
			}
		}
		return batchResponse;
	}

	@Override
	public List<HierarchyRelationDTO> searchHierarchyRelation(
	        HierarchyRelationDTO hierarchyRelation) {
		StringBuffer nodeQuery = new StringBuffer(
		        HIERARCHY_SEARCH_FOR_PARENT_AND_CHILD
		                .replace(
		                        PARENT_ENTITY_TYPE,
		                        hierarchyRelation.getParent()
		                                .getPlatformEntity()
		                                .getPlatformEntityType())
		                .replace(
		                        PARENT_TEMPLATE_NAME,
		                        hierarchyRelation.getParent()
		                                .getEntityTemplate()
		                                .getEntityTemplateName())
		                .replace(
		                        PARENT_DOMAIN_NAME,
		                        hierarchyRelation.getParent().getDomain()
		                                .getDomainName())
		                .replace(
		                        CHILD_ENTITY_TYPE,
		                        hierarchyRelation.getChild()
		                                .getPlatformEntity()
		                                .getPlatformEntityType())
		                .replace(
		                        CHILD_TEMPLATE_NAME,
		                        hierarchyRelation.getChild()
		                                .getEntityTemplate()
		                                .getEntityTemplateName())
		                .replace(
		                        CHILD_DOMAIN_NAME,
		                        hierarchyRelation.getChild().getDomain()
		                                .getDomainName()));

		List<HierarchyRelationDTO> deSerializeHier = null;
		try {
			LOGGER.info(nodeQuery.toString());
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI,
			        nodeQuery.toString(), null, ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());

			if (jsonArray != null) {
				JSONArray list = (JSONArray)jsonArray.getJSONObject(0)
				        .getJSONArray(ROW.getFieldName()).get(0);
				deSerializeHier = deSerHierarchySerch(list);
			}

		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return deSerializeHier;
	}

	private List<HierarchyRelationDTO> deSerHierarchySerch(JSONArray list) {
		List<HierarchyRelationDTO> entities = new ArrayList<HierarchyRelationDTO>();

		@SuppressWarnings("serial") Type type = new TypeToken<List<HierarchyRelationDTO>>() {
		}.getType();

		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
		builder.registerTypeAdapter(HierarchyRelationDTO.class,
		        new HierarchyTranslator());

		Gson gson = builder.create();
		entities = gson.fromJson(list.toString(), type);

		return entities;
	}

	private static final String X_IDENTIFIERVALUES_LIST = "{xIdentifierValuesList}";
	private static final String X_IDENTIFIERKEY_LIST = "{xIdentifierKeyList}";
	private static final String X_DOMAIN_LIST = "{xDomainList}";
	private static final String X_TEMPLATENAME_LIST = "{xTemplateNameList}";

	@Override
	public Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> getEntitiesByTags(
	        EntitiesByTagsPayload entitiesByTagsPayload) {

		String query = getQueryByMatch(entitiesByTagsPayload);
		LOGGER.info(" Query for getEntitiesByTags : " + query);
		Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> map = fetchEntities(query);
		if (CollectionUtils.sizeIsEmpty(map)) {
			return null;
		}
		return map;
	}

	private static final String GET_ENTITIES_BY_ANY_TAGS = "MATCH (x)-[r:attachedTo]->(m) "
	        + "WHERE x.identifierValue IN {xIdentifierValuesList} "
	        + "AND x.identifierKey IN {xIdentifierKeyList} "
	        + "AND x.domain IN {xDomainList} "
	        + "AND x.templateName IN {xTemplateNameList} ";

	private String getQueryByMatch(EntitiesByTagsPayload entitiesByTagsPayload) {
		String query = null;
		if (entitiesByTagsPayload.getMatch().equals("ANY")) {
			query = new String(GET_ENTITIES_BY_ANY_TAGS);
			if (CollectionUtils.isNotEmpty(entitiesByTagsPayload
			        .getIdentifierValues())) {
				Set<String> xIdentifierValuesList = new HashSet<String>();
				for (String value : entitiesByTagsPayload.getIdentifierValues()) {
					xIdentifierValuesList.add("'" + value + "'");
				}
				query = query.replace(X_IDENTIFIERVALUES_LIST,
				        xIdentifierValuesList.toString());
				System.out.println("xIdentifierValuesList.toString() "
				        + xIdentifierValuesList.toString());
				System.out.println(" X_IDENTIFIERVALUES_LIST "
				        + X_IDENTIFIERVALUES_LIST);
			}
			if (CollectionUtils.isNotEmpty(entitiesByTagsPayload
			        .getIdentifierKeys())) {
				Set<String> xIdentifierKey = new HashSet<String>();
				for (String key : entitiesByTagsPayload.getIdentifierKeys()) {
					xIdentifierKey.add("'" + key + "'");
				}
				query = query.replace(X_IDENTIFIERKEY_LIST,
				        xIdentifierKey.toString());
			}
			if (CollectionUtils.isNotEmpty(entitiesByTagsPayload.getDomains())) {
				Set<String> xDomainList = new HashSet<String>();
				for (String domain : entitiesByTagsPayload.getDomains()) {
					xDomainList.add("'" + domain + "'");
				}
				query = query.replace(X_DOMAIN_LIST, xDomainList.toString());
			}
			if (CollectionUtils.isNotEmpty(entitiesByTagsPayload
			        .getTemplateNames())) {
				Set<String> xTemplateNameList = new HashSet<String>();
				for (String templateName : entitiesByTagsPayload
				        .getTemplateNames()) {
					xTemplateNameList.add("'" + templateName + "'");
				}
				query = query.replace(X_TEMPLATENAME_LIST,
				        xTemplateNameList.toString());
			}
			StringBuilder filterQuery = new StringBuilder(query);
			if (entitiesByTagsPayload.getFilter() != null) {
				// append filter string and return string
				getFilterString(entitiesByTagsPayload.getFilter(), filterQuery);
			}
			query = filterQuery.toString();
		}

		if (entitiesByTagsPayload.getMatch().equals("ALL")) {

			String eachTagMatch = "(x{count})-[:attachedTo]->(m)";
			String mainMatchString = new String();

			String eachTagWhere = "x{count}.identifierValue = '{identifierValue}' AND x{count}.identifierKey = '{identifierKey}' AND x{count}.domain = '{domain}' AND x{count}.templateName = '{templateName}' ";
			String mainWhereString = new String();

			int size = entitiesByTagsPayload.getStartNodes().size();
			if (size > 0) {
				mainMatchString = "MATCH ";
				mainMatchString = mainMatchString
				        + eachTagMatch.replace(COUNT, "0");

				com.pcs.alpine.commons.dto.EntityDTO firstEntity = entitiesByTagsPayload
				        .getStartNodes().get(0);
				mainWhereString = " WHERE ";
				mainWhereString = mainWhereString
				        + eachTagWhere
				                .replace(COUNT, "0")
				                .replace(X_IDENTIFIERVALUE,
				                        firstEntity.getIdentifier().getValue())
				                .replace(X_IDENTIFIERKEY,
				                        firstEntity.getIdentifier().getKey())
				                .replace(X_DOMAIN,
				                        firstEntity.getDomain().getDomainName())
				                .replace(
				                        X_TEMPLATENAME,
				                        firstEntity.getEntityTemplate()
				                                .getEntityTemplateName());

			}
			for (int i = 1; i < size; i++) {
				Integer h = i;
				mainMatchString = mainMatchString + ", "
				        + eachTagMatch.replace(COUNT, h.toString());

				com.pcs.alpine.commons.dto.EntityDTO entity = entitiesByTagsPayload
				        .getStartNodes().get(i);
				mainWhereString = mainWhereString
				        + " AND "
				        + eachTagWhere
				                .replace(COUNT, h.toString())
				                .replace(X_IDENTIFIERVALUE,
				                        entity.getIdentifier().getValue())
				                .replace(X_IDENTIFIERKEY,
				                        entity.getIdentifier().getKey())
				                .replace(X_DOMAIN,
				                        entity.getDomain().getDomainName())
				                .replace(
				                        X_TEMPLATENAME,
				                        entity.getEntityTemplate()
				                                .getEntityTemplateName());
			}

			// List<String> templateNames = new ArrayList<String>();
			// for (String templateName :
			// entitiesByTagsPayload.getFilter().getTemplateNames()) {
			// templateNames.add("'"+templateName+"'");
			// }
			//
			// String filterString = M_FILTERSTRING;
			// filterString = filterString.replace(M_FILTER_TEMPLATENAME,
			// templateNames.toString());

			StringBuilder filterQuery = new StringBuilder();
			if (entitiesByTagsPayload.getFilter() != null) {
				// append filter string and return string
				getFilterString(entitiesByTagsPayload.getFilter(), filterQuery);
			}
			// query = filterQuery.toString();

			query = new String();
			query = query + mainMatchString + mainWhereString
			        + filterQuery.toString();
		}
		return query;
	}

	private static final String X_IDENTIFIERVALUE = "{identifierValue}";
	private static final String X_IDENTIFIERKEY = "{identifierKey}";
	private static final String X_DOMAIN = "{domain}";
	private static final String X_TEMPLATENAME = "{templateName}";
	private static final String M_FILTER_TEMPLATENAME = "{templateNames}";
	private static final String M_FILTERSTRING = " AND (m.templateName IN {templateNames} ) ";
	private static final String COUNT = "{count}";
	private static final String RETURN_STRING = "{count}";

	private Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> fetchEntities(
	        String query) {
		Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> map = new HashMap<String, List<com.pcs.alpine.commons.dto.EntityDTO>>();
		org.json.JSONObject jsonObject = null;
		try {
			jsonObject = Neo4jExecuter.exexcuteQueryAndReturnJsonObject(
			        this.neo4jURI, query, null, ROW.getFieldName());
		} catch (IOException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		org.json.JSONArray dataJsonArray = jsonObject.getJSONArray("data");
		org.json.JSONObject rowJsonObj = dataJsonArray.getJSONObject(0);
		org.json.JSONArray rowJsonArray = rowJsonObj.getJSONArray("row");

		org.json.JSONArray colummsJsonArray = jsonObject
		        .getJSONArray("columns");
		int coulmnsLength = colummsJsonArray.length();

		for (int h = 0; h < coulmnsLength; h++) {
			List<com.pcs.alpine.commons.dto.EntityDTO> entities = new ArrayList<com.pcs.alpine.commons.dto.EntityDTO>();
			org.json.JSONArray array = rowJsonArray.getJSONArray(h);

			int len = array.length();
			for (int i = 0; i < len; i++) {
				Gson gson = objectBuilderUtil.getGson();
				org.json.JSONObject jsonObj = array.getJSONObject(i);

				Object identifierKeyObj = jsonObj.get("identifierKey");
				Object identifierValueObj = jsonObj.get("identifierValue");
				Object templateNameObj = jsonObj.get("templateName");
				Object domainObj = jsonObj.get("domain");
				Object treeObj = jsonObj.get("tree");
				Object dataproviderObj = jsonObj.get("dataprovider");
				Object statusObj = jsonObj.get("status");

				com.pcs.alpine.commons.dto.EntityDTO entityDTO = new com.pcs.alpine.commons.dto.EntityDTO();
				entityDTO
				        .setIdentifier(new com.pcs.alpine.commons.dto.FieldMapDTO(
				                identifierKeyObj.toString(), identifierValueObj
				                        .toString()));

				DomainDTO domain = new DomainDTO();
				domain.setDomainName(domainObj.toString());
				entityDTO.setDomain(domain);

				com.pcs.alpine.commons.dto.EntityTemplateDTO entityTemplate = new com.pcs.alpine.commons.dto.EntityTemplateDTO();
				entityTemplate
				        .setEntityTemplateName(templateNameObj.toString());
				entityDTO.setEntityTemplate(entityTemplate);

				StatusDTO entityStatus = new StatusDTO();
				entityStatus.setStatusName(statusObj.toString());
				entityDTO.setEntityStatus(entityStatus);

				// Set the dataprovider
				if (isNotBlank(dataproviderObj.toString())) {
					String dataproviderString;
					try {
						dataproviderString = URLDecoder.decode(
						        dataproviderObj.toString(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(
						        "Error while decoding dataprovider", e);
					}
					@SuppressWarnings("serial") List<com.pcs.alpine.commons.dto.FieldMapDTO> dataprovider = gson
					        .fromJson(
					                dataproviderString,
					                new TypeToken<List<com.pcs.alpine.commons.dto.FieldMapDTO>>() {
					                }.getType());
					entityDTO.setDataprovider(dataprovider);
				}
				if (isNotBlank(treeObj.toString())) {
					String treeString;
					try {
						treeString = URLDecoder.decode(treeObj.toString(),
						        "UTF-8");
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(
						        "Error while decoding tree : ", e);
					}
					com.pcs.alpine.commons.dto.FieldMapDTO tree = gson
					        .fromJson(
					                treeString,
					                com.pcs.alpine.commons.dto.FieldMapDTO.class);
					entityDTO.setTree(tree);
				}
				entities.add(entityDTO);
			}
			map.put(colummsJsonArray.getString(h), entities);
		}
		return map;
	}

	private void getFilterString(EntitiesByTagsFilter filter,
	        StringBuilder filterQuery) {

		List<String> templateNames = filter.getTemplateNames();
		String returnString = new String();
		if (CollectionUtils.isNotEmpty(templateNames)) {

			List<String> templateNameFilter = new ArrayList<String>();
			for (String name : templateNames) {
				templateNameFilter.add("'" + name + "'");
			}
			filterQuery.append(" AND (m.templateName IN "
			        + templateNameFilter.toString() + " )");

			// return string
			returnString = " RETURN filter(a IN collect(DISTINCT m) WHERE a.templateName = '"
			        + templateNames.get(0) + "') as " + templateNames.get(0);
			int size = templateNames.size();
			for (int i = 1; i < size; i++) {
				returnString = returnString
				        + ", filter(a IN collect(DISTINCT m) WHERE a.templateName = '"
				        + templateNames.get(i) + "') as "
				        + templateNames.get(i);
			}
			returnString = returnString + " ; ";
			filterQuery.append(returnString);
		}
	}

	@Override
	public List<com.pcs.alpine.commons.dto.EntityDTO> getTagsByRange(
	        TagRangePayload payload) {

		List<com.pcs.alpine.commons.dto.EntityDTO> entities = new ArrayList<com.pcs.alpine.commons.dto.EntityDTO>();
		String query = getQuery(payload);
		org.json.JSONArray jsonArray = null;
		try {
			jsonArray = Neo4jExecuter.exexcuteQuery(this.neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (IOException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}

		int length = jsonArray.length();
		for (int i = 0; i < length; ++i) {
			org.json.JSONObject jsonObjRow = jsonArray.getJSONObject(i);
			org.json.JSONArray rowJsonArray = jsonObjRow.getJSONArray(ROW
			        .getFieldName());
			Gson gson = objectBuilderUtil.getGson();
			org.json.JSONObject jsonObj = rowJsonArray.getJSONObject(0);

			Object identifierKeyObj = jsonObj.get("identifierKey");
			Object identifierValueObj = jsonObj.get("identifierValue");
			Object templateNameObj = jsonObj.get("templateName");
			Object domainObj = jsonObj.get("domain");
			Object treeObj = jsonObj.get("tree");
			Object dataproviderObj = jsonObj.get("dataprovider");
			Object statusObj = jsonObj.get("status");

			com.pcs.alpine.commons.dto.EntityDTO entityDTO = new com.pcs.alpine.commons.dto.EntityDTO();
			entityDTO
			        .setIdentifier(new com.pcs.alpine.commons.dto.FieldMapDTO(
			                identifierKeyObj.toString(), identifierValueObj
			                        .toString()));

			DomainDTO domain = new DomainDTO();
			domain.setDomainName(domainObj.toString());
			entityDTO.setDomain(domain);

			com.pcs.alpine.commons.dto.EntityTemplateDTO entityTemplate = new com.pcs.alpine.commons.dto.EntityTemplateDTO();
			entityTemplate.setEntityTemplateName(templateNameObj.toString());
			entityDTO.setEntityTemplate(entityTemplate);

			StatusDTO entityStatus = new StatusDTO();
			entityStatus.setStatusName(statusObj.toString());
			entityDTO.setEntityStatus(entityStatus);

			// Set the dataprovider
			if (isNotBlank(dataproviderObj.toString())) {
				String dataproviderString;
				try {
					dataproviderString = URLDecoder.decode(
					        dataproviderObj.toString(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(
					        "Error while decoding dataprovider", e);
				}
				@SuppressWarnings("serial") List<com.pcs.alpine.commons.dto.FieldMapDTO> dataprovider = gson
				        .fromJson(
				                dataproviderString,
				                new TypeToken<List<com.pcs.alpine.commons.dto.FieldMapDTO>>() {
				                }.getType());
				entityDTO.setDataprovider(dataprovider);
			}
			if (isNotBlank(treeObj.toString())) {
				String treeString;
				try {
					treeString = URLDecoder.decode(treeObj.toString(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("Error while decoding tree : ",
					        e);
				}
				com.pcs.alpine.commons.dto.FieldMapDTO tree = gson.fromJson(
				        treeString,
				        com.pcs.alpine.commons.dto.FieldMapDTO.class);
				entityDTO.setTree(tree);
			}
			entities.add(entityDTO);
		}
		return entities;
	}

	private String getQuery(TagRangePayload payload) {
		String startNodeWhereClause = getStartNodeWhereClause(payload);
		String endNodeWhereClause = getEndNodeWhereClause(payload);
		String tagsWhereClause = getTagWhereClause(payload);

		StringBuilder query = new StringBuilder(GET_ALL_TAGS_BY_RANGE);
		query.append(startNodeWhereClause).append(" AND " + endNodeWhereClause)
		        .append(" AND " + tagsWhereClause)
		        .append(" RETURN DISTINCT tagEntities;");

		LOGGER.info("Query for getTagsByRange : " + query);
		return query.toString();
	}

	private String getStartNodeWhereClause(TagRangePayload payload) {
		HierarchyNodeTypes startNodeType = HierarchyNodeTypes.getEnum(payload
		        .getStartNodeType());
		String startNodeEntityType = null;
		Set<String> startNodeDomainList = new HashSet<String>();
		Set<String> startNodeTemplateList = new HashSet<String>();

		String startNodeWhereClause = "startNodeEntities.domain IN {startNodeDomainList} AND startNodeEntities.templateName IN {startNodeTemplateList}";
		switch (startNodeType) {
			case TEMPLATE :
				com.pcs.alpine.commons.dto.EntityTemplateDTO template = payload
				        .getStartNode().getTemplate();
				startNodeTemplateList.add("'"
				        + template.getEntityTemplateName() + "'");

				profileService.getEndUserDomain();
				startNodeDomainList.add("'"
				        + getDomainName(template.getDomain()).getDomainName()
				        + "'");
				startNodeEntityType = new String(
				        template.getPlatformEntityType());
				startNodeWhereClause = startNodeWhereClause.replace(
				        START_NODE_DOMAIN_LIST, startNodeDomainList.toString())
				        .replace(START_NODE_TEMPLATE_LIST,
				                startNodeTemplateList.toString());
				break;
			case ENTITY :
				com.pcs.alpine.commons.dto.EntityDTO entity = payload
				        .getStartNode().getEntity();
				startNodeTemplateList.add("'"
				        + entity.getEntityTemplate().getEntityTemplateName()
				        + "'");
				startNodeDomainList.add("'"
				        + getDomainName(entity.getDomain()).getDomainName()
				        + "'");
				startNodeEntityType = new String(entity.getPlatformEntity()
				        .getPlatformEntityType());
				Set<String> startNodeIdenValList = new HashSet<String>();
				startNodeIdenValList.add("'"
				        + entity.getIdentifier().getValue() + "'");
				startNodeWhereClause = startNodeWhereClause.replace(
				        START_NODE_DOMAIN_LIST, startNodeDomainList.toString())
				        .replace(START_NODE_TEMPLATE_LIST,
				                startNodeTemplateList.toString());
				String startNodeIdentifierValList = " AND startNodeEntities.identifierValue IN {startNodeIdenValList}";
				startNodeIdentifierValList = startNodeIdentifierValList
				        .replace(START_NODE_IDEN_VAL_LIST,
				                startNodeIdenValList.toString());
				StringBuilder startNodeWhereClauseBuilder = new StringBuilder(
				        startNodeWhereClause);
				startNodeWhereClauseBuilder.append(startNodeIdentifierValList);
				startNodeWhereClause = startNodeWhereClauseBuilder.toString();
				break;
			default:
				// TODO
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED);
		}
		startNodeWhereClause = "startNodeEntities:" + startNodeEntityType
		        + " AND " + startNodeWhereClause;
		LOGGER.info("Start node where clause : " + startNodeWhereClause);
		return startNodeWhereClause;
	}

	private DomainDTO getDomainName(DomainDTO domain) {
		DomainDTO domainDTO = null;
		if (domain != null) {
			if (StringUtils.isNotBlank(domain.getDomainName())) {
				domainDTO = new DomainDTO();
				domainDTO.setDomainName(domain.getDomainName());
			} else {
				domainDTO = new DomainDTO();
				domainDTO.setDomainName(profileService.getEndUserDomain());
			}
		} else {
			domainDTO = new DomainDTO();
			domainDTO.setDomainName(profileService.getEndUserDomain());
		}
		return domainDTO;
	}

	private String getEndNodeWhereClause(TagRangePayload payload) {
		HierarchyNodeTypes endNodeType = HierarchyNodeTypes.getEnum(payload
		        .getEndNodeType());
		String endNodeEntityType = null;
		Set<String> endNodeDomainList = new HashSet<String>();
		Set<String> endNodeTemplateList = new HashSet<String>();

		String endNodeWhereClause = "endNodeEntities.domain IN {endNodeDomainList} AND endNodeEntities.templateName IN {endNodeTemplateList}";
		switch (endNodeType) {
			case TEMPLATE :
				com.pcs.alpine.commons.dto.EntityTemplateDTO template = payload
				        .getEndNode().getTemplate();
				endNodeTemplateList.add("'" + template.getEntityTemplateName()
				        + "'");
				endNodeDomainList.add("'"
				        + getDomainName(template.getDomain()).getDomainName()
				        + "'");
				endNodeEntityType = new String(template.getPlatformEntityType());
				endNodeWhereClause = endNodeWhereClause.replace(
				        END_NODE_DOMAIN_LIST, endNodeDomainList.toString())
				        .replace(END_NODE_TEMPLATE_LIST,
				                endNodeTemplateList.toString());
				break;
			case ENTITY :
				com.pcs.alpine.commons.dto.EntityDTO entity = payload
				        .getEndNode().getEntity();
				endNodeTemplateList.add("'"
				        + entity.getEntityTemplate().getEntityTemplateName()
				        + "'");
				endNodeDomainList.add("'"
				        + getDomainName(entity.getDomain()).getDomainName()
				        + "'");
				endNodeEntityType = new String(entity.getPlatformEntity()
				        .getPlatformEntityType());
				Set<String> endNodeIdenValList = new HashSet<String>();
				endNodeIdenValList.add("'" + entity.getIdentifier().getValue()
				        + "'");
				endNodeWhereClause = endNodeWhereClause.replace(
				        END_NODE_DOMAIN_LIST, endNodeDomainList.toString())
				        .replace(END_NODE_TEMPLATE_LIST,
				                endNodeTemplateList.toString());
				String startNodeIdentifierValList = " AND endNodeEntities.identifierValue IN {endNodeIdenValList}";
				startNodeIdentifierValList = startNodeIdentifierValList
				        .replace(END_NODE_IDEN_VAL_LIST,
				                endNodeIdenValList.toString());
				StringBuilder endNodeWhereClauseBuilder = new StringBuilder(
				        endNodeWhereClause);
				endNodeWhereClauseBuilder.append(startNodeIdentifierValList);
				endNodeWhereClause = endNodeWhereClauseBuilder.toString();
				break;
			default:
				// TODO
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED);
		}
		endNodeWhereClause = "endNodeEntities:" + endNodeEntityType + " AND "
		        + endNodeWhereClause;
		LOGGER.info("End node where clause : " + endNodeWhereClause);
		return endNodeWhereClause;
	}

	private String getTagWhereClause(TagRangePayload payload) {

		String tagsWhereClause = "tagEntities.domain IN {tagDomainList} AND tagEntities.templateName IN {tagTypeTemplateList}";
		String tagEntityType = "MARKER";
		Set<String> tagsDomainList = new HashSet<String>();
		Set<String> tagsTemplateList = new HashSet<String>();

		List<com.pcs.alpine.commons.dto.EntityTemplateDTO> tagTypeTemplates = payload
		        .getTagTypes();
		for (com.pcs.alpine.commons.dto.EntityTemplateDTO tagTypeTemplate : tagTypeTemplates) {
			tagsDomainList.add("'"
			        + getDomainName(tagTypeTemplate.getDomain())
			                .getDomainName() + "'");
			tagsTemplateList.add("'" + tagTypeTemplate.getEntityTemplateName()
			        + "'");
		}

		tagsWhereClause = tagsWhereClause.replace(TAG_DOMAIN_LIST,
		        tagsDomainList.toString()).replace(TAG_TYPE_TEMPLATE_LIST,
		        tagsTemplateList.toString());
		tagsWhereClause = "tagEntities:" + tagEntityType + " AND "
		        + tagsWhereClause;

		LOGGER.info("Tags where clause : " + tagsWhereClause);
		return tagsWhereClause;
	}

	private static final String START_NODE_DOMAIN_LIST = "{startNodeDomainList}";
	private static final String START_NODE_TEMPLATE_LIST = "{startNodeTemplateList}";
	private static final String START_NODE_IDEN_VAL_LIST = "{startNodeIdenValList}";
	private static final String TAG_DOMAIN_LIST = "{tagDomainList}";
	private static final String TAG_TYPE_TEMPLATE_LIST = "{tagTypeTemplateList}";
	private static final String END_NODE_DOMAIN_LIST = "{endNodeDomainList}";
	private static final String END_NODE_TEMPLATE_LIST = "{endNodeTemplateList}";
	private static final String END_NODE_IDEN_VAL_LIST = "{endNodeIdenValList}";

	@Override
	public void attachParentsToMultipleMarkers(
	        List<HierarchyEntity> childrenNodes, List<EntityDTO> parents) {

		List<String> propsList = new ArrayList<String>();
		for (HierarchyEntity entityDTO2 : childrenNodes) {
			String propsQuery = "{identifierKey:'{identifierKey}',identifierValue:'{identifierValue}',templateName:'{templateName}',domain:'{domain}',status:'{status}',dataprovider:'{dataprovider}',tree:'{tree}'}";
			propsQuery = propsQuery.replace("{identifierKey}",
			        entityDTO2.getIdentifierKey());
			propsQuery = propsQuery.replace("{identifierValue}",
			        entityDTO2.getIdentifierValue());
			propsQuery = propsQuery.replace("{templateName}",
			        entityDTO2.getTemplateName());
			propsQuery = propsQuery.replace("{domain}", entityDTO2.getDomain());
			propsQuery = propsQuery.replace("{status}", entityDTO2.getStatus());
			propsQuery = propsQuery.replace("{dataprovider}",
			        entityDTO2.getDataprovider());
			if (isBlank(entityDTO2.getTree())) {
				propsQuery = propsQuery.replace("{tree}", "");
			} else {
				propsQuery = propsQuery.replace("{tree}", entityDTO2.getTree());
			}
			propsList.add(propsQuery);
		}
		StringBuffer parentQuery = new StringBuffer();

		for (EntityDTO subject : parents) {
			StringBuffer subjectQuery = new StringBuffer(
			        ATTACH_MULTIPLE_CHILDREN
			                .replace("props", propsList.toString())
			                .replace(IDENTIFIER_KEY_TAG,
			                        subject.getIdentifier().getKey())
			                .replace(IDENTIFIER_VALUE_TAG,
			                        subject.getIdentifier().getValue())
			                .replace(
			                        TEMPLATE_NAME_TAG,
			                        subject.getEntityTemplate()
			                                .getEntityTemplateName())
			                .replace(DOMAIN_TAG,
			                        subject.getDomain().getDomainName())
			                .replace(STATUS,
			                        subject.getEntityStatus().getStatusName())
			                .replace(TREE_TAG, getTree(subject.getTree()))
			                .replace(DATAPROVIDER_TAG,
			                        getDataProvider(subject.getDataprovider())));

			parentQuery.append(subjectQuery);

			try {
				Neo4jExecuter.exexcuteQuery(neo4jURI, subjectQuery.toString(),
				        null, ROW.getFieldName());
			} catch (IOException e) {
				throw new RuntimeException("Error in HierarchyRepository" + e);
			}
		}

	}

	@Override
	public List<HierarchyEntity> getEntitiesBetweenTenants(
	        HierarchyEntity startTenantEntity, HierarchyEntity endTenantEntity,
	        String searchTemplate) {
		String query = GET_ENTITIES_BY_TEMPLATE_BETWEEN_TENANTS;
		query = query
		        .replace(IDENTIFIER_KEY_TAG,
		                startTenantEntity.getIdentifierKey())
		        .replace(IDENTIFIER_VALUE_TAG,
		                startTenantEntity.getIdentifierValue())
		        .replace(TEMPLATE_NAME_TAG, startTenantEntity.getTemplateName())
		        .replace(DOMAIN_TAG, startTenantEntity.getDomain())
		        .replace(END_IDENTIFIER_KEY_TAG,
		                endTenantEntity.getIdentifierKey())
		        .replace(END_IDENTIFIER_VALUE_TAG,
		                endTenantEntity.getIdentifierValue())
		        .replace(END_TEMPLATE_NAME_TAG,
		                endTenantEntity.getTemplateName())
		        .replace(END_DOMAIN_TAG, endTenantEntity.getDomain())
		        .replace(SEARCH_TEMPLATE, searchTemplate);
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					HierarchyEntity childHierarchy = prepareHierarchyEntity(jsonArray
					        .getJSONObject(i));
					hierarchies.add(childHierarchy);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return hierarchies;
	}

	@Override
	public List<DistributionEntity> getTaggedEntityCount(
	        HierarchyTagSearchDTO hierarchyTagSearchDTO) {
		String query = GET_TAGGED_ENTITY_COUNT;
		query = query
		        .replace(TAG_TYPE, hierarchyTagSearchDTO.getTagType())
		        .replace(DOMAIN_TAG, hierarchyTagSearchDTO.getDomainName())
		        .replace(END_ENTITY_TEMPLATE,
		                hierarchyTagSearchDTO.getEndEntityTemplate());

		String intermediateQueryCondition = "";

		for (String intermediateNode : hierarchyTagSearchDTO
		        .getIntermediateTemplates()) {

			if (isNotBlank(intermediateQueryCondition)) {
				intermediateQueryCondition = intermediateQueryCondition
				        + " OR ";
			}
			intermediateQueryCondition = intermediateQueryCondition
			        + " intermediate.templateName='" + intermediateNode + "'";

			query = query.replace(INTERMEDIATE_CONDITION,
			        intermediateQueryCondition);
		}
		List<DistributionEntity> distributions = new ArrayList<DistributionEntity>();
		try {
			String neo4jResponse = null;
			neo4jResponse = Neo4jUtils.queryNeo4j(neo4jURI, query, null,
			        ROW.toString());
			if (StringUtils.isBlank(neo4jResponse)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
			JSONObject jsonObject = new JSONObject(neo4jResponse);
			JSONArray jsonArray = jsonObject
			        .getJSONArray(RESULTS.getFieldName()).getJSONObject(0)
			        .getJSONArray(DATA.getFieldName());
			if (jsonArray.length() > 0) {

				for (int i = 0; i < jsonArray.length(); i++) {
					DistributionEntity distributedEntity = prepareTagEntity(jsonArray
					        .getJSONObject(i));
					distributions.add(distributedEntity);
				}
			}
		} catch (IOException | JSONException e) {
			throw new NoResultException("Error in HierarchyRepository" + e);
		}
		return distributions;
	}

	private DistributionEntity prepareTagEntity(JSONObject jsonObject)
	        throws JSONException {
		DistributionEntity distributionEntity = new DistributionEntity();
		JSONArray jsonArray = jsonObject.getJSONArray(ROW.toString());

		Object countObj = jsonArray.get(0);
		if (countObj != null) {
			distributionEntity.setCount(Integer.parseInt(countObj.toString()));
		}
		Object dataproviderObj = jsonArray.get(1);

		// Set the dataprovider
		if (isNotBlank(dataproviderObj.toString())) {
			String dataproviderString;
			try {
				dataproviderString = URLDecoder.decode(
				        dataproviderObj.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Error while decoding dataprovider",
				        e);
			}
			distributionEntity.setDataprovider(dataproviderString);
		}
		return distributionEntity;
	}
}

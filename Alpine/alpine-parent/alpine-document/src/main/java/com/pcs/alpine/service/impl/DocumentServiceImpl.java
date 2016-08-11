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
package com.pcs.alpine.service.impl;

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.enums.DocumentDataFields.DOCUMENT;
import static com.pcs.alpine.enums.DocumentDataFields.DOCUMENT_TEMPLATE;
import static com.pcs.alpine.enums.DocumentDataFields.DOCUMENT_TYPE;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.dto.DomainDTO;
import com.pcs.alpine.commons.dto.EntityDTO;
import com.pcs.alpine.commons.dto.EntityTemplateDTO;
import com.pcs.alpine.commons.dto.FieldMapDTO;
import com.pcs.alpine.commons.dto.HierarchyDTO;
import com.pcs.alpine.commons.dto.HierarchySearchDTO;
import com.pcs.alpine.commons.dto.IdentityDTO;
import com.pcs.alpine.commons.dto.PlatformEntityDTO;
import com.pcs.alpine.commons.dto.StatusDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.dto.SubscriptionContextHolder;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.dto.DocumentDTO;
import com.pcs.alpine.dto.HierarchyRelationDTO;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.rest.exception.GalaxyRestException;
import com.pcs.alpine.service.DocumentService;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author Riyas (PCSEG296)
 * @date 12 June 2016
 * @since alpine-1.0.0
 */
@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;
	@Value("${platform.esb.marker.find}")
	private String findMarkerPlatformEndpoint;
	@Value("${platform.esb.marker.list}")
	private String listMarkersPlatformEndpoint;
	@Value("${platform.client.entitytemplate.find}")
	private String entityTemplateFindPlatformEndpoint;
	@Value("${platform.esb.hierarchy.attach}")
	private String attachHierarchyPlatformEndpoint;
	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyChildrenPlatformEndpoint;
	@Value("${platform.esb.hierarchy.search.relation}")
	private String listAllDocumentsAndItsType;

	@Autowired
	SubscriptionProfileService subscriptionProfileService;

	@Override
	public DocumentDTO createDocument(DocumentDTO document) {

		validateMandatoryFields(document, DOCUMENT);

		if (document.getDocument().getDomain() == null
		        || isBlank(document.getDocument().getDomain().getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(domainName);
			document.getDocument().setDomain(domain);
		}

		EntityDTO documentEntity = document.getDocument();
		documentEntity.setEntityTemplate(getDocumentTemplate());

		List<FieldMapDTO> documentFields = documentEntity.getFieldValues();

		EntityDTO validDocumentType = null;
		if (CollectionUtils.isNotEmpty(documentFields)) {
			FieldMapDTO documentTypeMap = new FieldMapDTO();
			documentTypeMap.setKey(DOCUMENT_TYPE.getFieldName());
			String docTypeTemplate = fetchField(documentFields, documentTypeMap);
			if (StringUtils.isNotEmpty(docTypeTemplate)) {
				validateMandatoryFields(document, DOCUMENT_TYPE);
				validDocumentType = validateDocumentType(docTypeTemplate,
				        document.getDocumentType());
			}
		}

		EntityDTO responseDocument = platformClient.post(
		        markerPlatformEndpoint,
		        subscriptionProfileService.getJwtToken(), documentEntity,
		        EntityDTO.class);

		EntityDTO responseDocumentType = null;
		if (validDocumentType != null) {
			responseDocumentType = createDocumentType(validDocumentType);
		}

		document.setDocument(responseDocument);
		if (responseDocumentType != null) {
			document.setDocumentType(responseDocumentType);
			createRealtion(document);
		}

		return document;
	}

	private EntityDTO validateDocumentType(String type, EntityDTO documentType) {

		if (documentType.getDomain() == null
		        || isBlank(documentType.getDomain().getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(domainName);
			documentType.setDomain(domain);
		}
		EntityTemplateDTO template = getDocumentType(type, documentType
		        .getDomain().getDomainName());

		documentType.setEntityTemplate(template);

		return documentType;
	}

	private EntityDTO createDocumentType(EntityDTO documentType) {

		EntityDTO responseDocumentType = platformClient.post(
		        markerPlatformEndpoint,
		        subscriptionProfileService.getJwtToken(), documentType,
		        EntityDTO.class);

		return responseDocumentType;
	}

	private StatusMessageDTO createRealtion(DocumentDTO document) {
		StatusMessageDTO status = null;
		if (document.getDocument() != null
		        && document.getDocumentType() != null) {
			// create hierarchy
			HierarchyDTO hierarchyDTO = new HierarchyDTO();
			hierarchyDTO.setActor(document.getDocument());
			List<EntityDTO> subjects = new ArrayList<EntityDTO>();

			subjects.add(document.getDocumentType());
			hierarchyDTO.setSubjects(subjects);

			try {
				status = platformClient.post(attachHierarchyPlatformEndpoint,
				        subscriptionProfileService.getJwtToken(), hierarchyDTO,
				        StatusMessageDTO.class);
			} catch (GalaxyRestException e) {
				throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
				        "Error in attaching document type entity to document entity");
			}
		}
		return status;
	}

	private EntityTemplateDTO getDocumentType(String documentTypeName,
	        String domainName) {

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName("ACTIVE");

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setDomain(domainDTO);
		entityTemplateDTO.setPlatformEntityType("MARKER");
		entityTemplateDTO.setStatus(statusDTO);
		entityTemplateDTO.setEntityTemplateName(documentTypeName);
		entityTemplateDTO.setParentTemplate(DOCUMENT_TEMPLATE.getDescription());

		String targetUrl = entityTemplateFindPlatformEndpoint;

		try {
			entityTemplateDTO = platformClient.post(targetUrl,
			        subscriptionProfileService.getJwtToken(),
			        entityTemplateDTO, EntityTemplateDTO.class);
		} catch (Exception e) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        DOCUMENT_TYPE.getDescription());
		}

		return entityTemplateDTO;
	}

	private String fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		if (isBlank(field.getValue())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        field.getKey());
		}
		return field.getValue();
	}

	@Override
	public StatusMessageDTO updateDocument(DocumentDTO document) {

		validateMandatoryFields(document, DOCUMENT);

		if (document.getDocument().getDomain() == null
		        || isBlank(document.getDocument().getDomain().getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(domainName);
			document.getDocument().setDomain(domain);
		}

		EntityDTO documentEntity = document.getDocument();
		documentEntity.setEntityTemplate(getDocumentTemplate());

		List<FieldMapDTO> documentFields = documentEntity.getFieldValues();

		EntityDTO validDocumentType = null;
		String docTypeTemplate = null;
		if (CollectionUtils.isNotEmpty(documentFields)) {
			FieldMapDTO documentTypeMap = new FieldMapDTO();
			documentTypeMap.setKey(DOCUMENT_TYPE.getFieldName());
			docTypeTemplate = fetchField(documentFields, documentTypeMap);
			if (StringUtils.isNotEmpty(docTypeTemplate)) {
				validateMandatoryFields(document, DOCUMENT_TYPE);
				validDocumentType = validateDocumentType(docTypeTemplate,
				        document.getDocumentType());
			}
		}
		document.getDocument().setEntityStatus(
		        validateStatus(document.getDocument().getEntityStatus()));
		StatusMessageDTO statusMessageDTO = platformClient.put(
		        markerPlatformEndpoint,
		        subscriptionProfileService.getJwtToken(),
		        document.getDocument(), StatusMessageDTO.class);

		EntityDTO documentType = null;
		if (StringUtils.isNotEmpty(docTypeTemplate)
		        && validDocumentType != null) {
			documentType = getDocumentChild(document.getDocument(),
			        docTypeTemplate);
			documentType.setFieldValues(validDocumentType.getFieldValues());
		}
		if (documentType == null) {

			validDocumentType.setEntityStatus(validateStatus(validDocumentType
			        .getEntityStatus()));

			EntityDTO responseDocumentType = platformClient.post(
			        markerPlatformEndpoint,
			        subscriptionProfileService.getJwtToken(),
			        validDocumentType, EntityDTO.class);
			document.setDocumentType(responseDocumentType);
			createRealtion(document);
		} else {

			documentType.setEntityStatus(validateStatus(documentType
			        .getEntityStatus()));

			platformClient.put(markerPlatformEndpoint,
			        subscriptionProfileService.getJwtToken(), documentType,
			        StatusMessageDTO.class);
		}

		return statusMessageDTO;
	}

	private StatusDTO validateStatus(StatusDTO status) {
		if (status == null) {
			status = new StatusDTO();
		}
		if (StringUtils.isEmpty(status.getStatusName())) {
			status.setStatusName(Status.ACTIVE.name());
		}
		return status;
	}

	@Override
	public List<DocumentDTO> listDocuments(String domain, String documentType) {

		List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
		if (StringUtils.isEmpty(domain)) {
			Subscription subscription = SubscriptionContextHolder.getContext()
			        .getSubscription();
			domain = subscription.getEndUserDomain();
		}
		if (StringUtils.isEmpty(documentType)) {
			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setDomainName(domain);
			documentDTOs = getAllDocuments(domainDTO);
		} else {
			documentDTOs = getAllDocumentsByType(domain, documentType);
		}
		return documentDTOs;
	}

	private List<DocumentDTO> getAllDocumentsByType(String domain,
	        String documentType) {

		HierarchyRelationDTO hierarchyRelationDTO = new HierarchyRelationDTO();
		hierarchyRelationDTO.setParent(getEntity(domain,
		        DOCUMENT_TEMPLATE.getDescription()));
		hierarchyRelationDTO.setChild(getEntity(domain, documentType));

		List<DocumentDTO> documets = new ArrayList<DocumentDTO>();
		List<HierarchyRelationDTO> entityDTOs = platformClient.post(
		        listAllDocumentsAndItsType,
		        subscriptionProfileService.getJwtToken(), hierarchyRelationDTO,
		        List.class, HierarchyRelationDTO.class);
		for (HierarchyRelationDTO entity : entityDTOs) {
			DocumentDTO response = new DocumentDTO();
			response.setDocument(entity.getParent());
			response.setDocumentType(entity.getChild());
			documets.add(response);
		}
		return documets;
	}

	private EntityDTO getEntity(String domain, String entityTemplate) {

		EntityDTO entity = new EntityDTO();
		DomainDTO domainDto = new DomainDTO();
		domainDto.setDomainName(domain);
		entity.setDomain(domainDto);

		PlatformEntityDTO platform = new PlatformEntityDTO();
		platform.setPlatformEntityType("MARKER");
		entity.setPlatformEntity(platform);

		EntityTemplateDTO template = new EntityTemplateDTO();
		template.setEntityTemplateName(entityTemplate);
		entity.setEntityTemplate(template);

		return entity;
	}

	private List<DocumentDTO> getAllDocuments(DomainDTO domainDTO) {

		List<DocumentDTO> documets = new ArrayList<DocumentDTO>();
		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setEntityTemplate(getDocumentTemplate());
		entityDTO.setDomain(domainDTO);
		List<EntityDTO> entityDTOs = platformClient.post(
		        listMarkersPlatformEndpoint,
		        subscriptionProfileService.getJwtToken(), entityDTO,
		        List.class, EntityDTO.class);
		for (EntityDTO entity : entityDTOs) {
			DocumentDTO response = new DocumentDTO();
			response.setDocument(entity);
			documets.add(response);
		}
		return documets;
	}

	private EntityTemplateDTO getDocumentTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(DOCUMENT_TEMPLATE
		        .getFieldName());
		return entityTemplateDTO;
	}

	@Override
	public DocumentDTO findDocument(IdentityDTO identity) {

		DocumentDTO response = new DocumentDTO();
		setEntityTemplate(identity);
		// Get document details
		EntityDTO documentDetails = platformClient.post(
		        findMarkerPlatformEndpoint,
		        subscriptionProfileService.getJwtToken(), identity,
		        EntityDTO.class);

		List<FieldMapDTO> documentFields = documentDetails.getFieldValues();

		EntityDTO documentType = null;
		if (CollectionUtils.isNotEmpty(documentFields)) {
			FieldMapDTO documentTypeMap = new FieldMapDTO();
			documentTypeMap.setKey(DOCUMENT_TYPE.getFieldName());
			String docTypeTemplate = fetchField(documentFields, documentTypeMap);
			if (StringUtils.isNotEmpty(docTypeTemplate)) {
				documentType = getDocumentChild(documentDetails,
				        docTypeTemplate);
			}
		}

		response.setDocument(documentDetails);
		response.setDocumentType(documentType);

		return response;
	}

	private EntityDTO getDocumentChild(EntityDTO document, String docType) {
		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();

		// set actor
		IdentityDTO actor = new IdentityDTO();
		actor.setEntityTemplate(document.getEntityTemplate());
		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType("MARKER");
		actor.setPlatformEntity(platformEntity);
		actor.setDomain(document.getDomain());
		actor.setIdentifier(document.getIdentifier());

		hierarchySearchDTO.setActor(actor);

		// set search template name
		hierarchySearchDTO.setSearchTemplateName(docType);
		hierarchySearchDTO.setSearchEntityType("MARKER");

		// fetch the children of documents to get the document type
		List<EntityDTO> documentTypes = platformClient.post(
		        hierarchyChildrenPlatformEndpoint,
		        subscriptionProfileService.getJwtToken(), hierarchySearchDTO,
		        List.class, EntityDTO.class);

		EntityDTO documentDetails = null;
		if (CollectionUtils.isNotEmpty(documentTypes)) {
			IdentityDTO documentIdentity = entityToIdentity(documentTypes
			        .get(0));

			documentDetails = platformClient.post(findMarkerPlatformEndpoint,
			        subscriptionProfileService.getJwtToken(), documentIdentity,
			        EntityDTO.class);
		}

		return documentDetails;

	}

	private IdentityDTO setEntityTemplate(IdentityDTO identity) {

		validateMandatoryFields(identity);

		if (identity.getEntityTemplate() == null
		        || isBlank(identity.getEntityTemplate().getEntityTemplateName())) {
			EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
			entityTemplate.setEntityTemplateName(DOCUMENT_TEMPLATE
			        .getDescription());
			identity.setEntityTemplate(entityTemplate);
		}
		return identity;
	}

	private IdentityDTO entityToIdentity(EntityDTO entity) {

		IdentityDTO identity = null;
		if (entity != null) {
			identity = new IdentityDTO();
			identity.setDomain(entity.getDomain());
			identity.setPlatformEntity(entity.getPlatformEntity());
			identity.setIdentifier(entity.getIdentifier());
			identity.setEntityTemplate(entity.getEntityTemplate());
		}
		return identity;
	}

}

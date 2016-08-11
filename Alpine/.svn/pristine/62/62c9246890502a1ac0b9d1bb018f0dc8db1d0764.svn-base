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
package com.pcs.alpine.services.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.is.client.dto.DomainDTO;
import com.pcs.alpine.is.client.dto.StandardResponse;
import com.pcs.alpine.is.client.dto.Tenant;
import com.pcs.alpine.is.constants.ConnectionConstants;
import com.pcs.alpine.model.Domain;
import com.pcs.alpine.services.DomainService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.Status;
import com.pcs.alpine.services.repository.DomainRepository;
import com.pcs.galaxy.rest.client.AuthUtil;
import com.pcs.galaxy.rest.client.BaseClient;

/**
 * This class is responsible for domain services
 * 
 * @author pcseg292 Renjith P
 * @author pcseg288 Deepak Dinakaran
 */
@Service
public class DomainServiceImpl implements DomainService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DomainServiceImpl.class);

	@Autowired
	@Qualifier("identityServerWrapperClient")
	private BaseClient client;

	private final Map<String, String> defaultBasicAuthHeader = AuthUtil
	        .getDefaultBasicAuthHeaderMap();

	@Autowired
	private DomainRepository domainRepository;

	@Autowired
	private StatusService statusService;

	/***
	 * Method to create a domain
	 * 
	 * @param domainDTO
	 * @return domainDTO
	 */
	@Override
	public DomainDTO createDomain(DomainDTO domainDTO) {

		// Sanity validation
		ValidationUtils.validateMandatoryFields(domainDTO,
		        EMDataFields.DOMAIN_NAME);

		// validate if domain(or tenant) is already existing in system
		if (domainExists(domainDTO.getDomainName())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
			        EMDataFields.DOMAIN_NAME.getDescription());
		}

		client.post(ConnectionConstants.TENANT_URL_CTX, defaultBasicAuthHeader,
		        getPayload(domainDTO), StandardResponse.class);

		LOGGER.debug("<<--Entry createDomain(DomainDTO domainDTO) -->>");

		Domain domain = new Domain();
		// Business validation
		// validateDomainDTO(domainDTO, domain);
		// validateParentUserCount(domainDTO, domain);
		convertDTO(domainDTO, domain);
		// Set other values for DB
		domain.setStatusKey(getActiveStatus());

		domainRepository.saveDomain(domain);
		convertEntityToDTO(domainDTO, domain);
		LOGGER.debug("<<--Exit createDomain(DomainDTO domainDTO)-->>");
		return domainDTO;
	}

	/***
	 * Method to delete a domain.
	 * 
	 * @param domainDTO
	 * @return statusMessageDTO
	 */
	@Deprecated
	@Override
	public StatusMessageDTO deleteDomain(
	        com.pcs.alpine.services.dto.DomainDTO domainDTO) {
		LOGGER.debug("<<--Entry deactivateDomain(DomainDTO domainDTO) -->>");
		StatusMessageDTO smDTO = new StatusMessageDTO();
		smDTO.setStatus(Status.FAILURE);
		try {
			Domain domain = domainRepository.findDomainByName(domainDTO
			        .getDomainName());
			if (domain == null) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
				        CommonConstants.DOMAIN);
			}
			domainRepository.deleteDomain(domain);
			smDTO.setStatus(Status.SUCCESS);
		} catch (NoResultException  noResultException) {
			LOGGER.error(CommonConstants.DOMAIN.toString()
			        + GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE
			                .toString());
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        CommonConstants.DOMAIN);
		}
		LOGGER.debug("<<--Exit deactivateDomain(DomainDTO domainDTO) -->>");
		return smDTO;
	}

	private void convertEntityToDTO(DomainDTO domainDTO, Domain domain) {
		domainDTO.setDomainName(domain.getName());
		domainDTO.setDomainUrl(domain.getDomainUrl());
		domainDTO.setMaxUsers(String.valueOf(domain.getMaxUsers()));
		domainDTO.setMaxConcurrentUsers(String.valueOf(domain
		        .getMaxConcurrentUsers()));
		domainDTO.setStatus(String.valueOf(domain.getStatusKey()));
	}

	private void convertDTO(DomainDTO domainDTO, Domain domain) {
		domain.setName(domainDTO.getDomainName());
		domain.setDomainUrl(domainDTO.getDomainUrl());
		
		//TODO change this when max users and concurrent user's are required
		domain.setMaxConcurrentUsers(0);
		domain.setMaxUsers(0);

	}

	/**
	 * To check that domain is existing.
	 * 
	 * @param domainName
	 * @return domainExist
	 */
	private boolean domainExists(String domainName) {
		Boolean domainExist = true;
		Domain domain = domainRepository.findDomainByName(domainName);
		if (domain == null) {
			domainExist = false;
		}
		return domainExist;
	}

	private Integer getActiveStatus() {
		return statusService.getStatus(Status.ACTIVE.name());
	}

	/**
	 * Method to create payload for domain. User details need to be set with
	 * domain.
	 * 
	 * @param domainDTO
	 * @return tenant
	 */
	private Tenant getPayload(DomainDTO domainDTO) {
		Tenant tenant = new Tenant();
		tenant.setActivate(true);
		tenant.setDomain(domainDTO.getDomainName());

		List<FieldMapDTO> fields = domainDTO.getEntity().getFieldValues();

		// IS require admin user details while creating tenant
		tenant.setContactEmail(fetchField(fields,
		        new FieldMapDTO(EMDataFields.CONTACT_EMAIL.getVariableName()))
		        .getValue());
		tenant.setAdminUserName("sampleadmin");
		tenant.setAdminPassword("sampleadmin");
		tenant.setFirstName(fetchField(fields,
		        new FieldMapDTO(EMDataFields.FIRST_NAME.getFieldName()))
		        .getValue());
		tenant.setLastName(fetchField(fields,
		        new FieldMapDTO(EMDataFields.LAST_NAME.getFieldName()))
		        .getValue());
		tenant.setUsagePlan("Demo");
		return tenant;
	}

	/**
	 * @Description Responsible for fetch fieldMapDto from a list
	 * @param List
	 *            <FieldMapDTO> fieldMapDTOs
	 * @param FieldMapDTO
	 *            fieldMapDTO
	 * @return fieldMapDTO
	 */
	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}
}
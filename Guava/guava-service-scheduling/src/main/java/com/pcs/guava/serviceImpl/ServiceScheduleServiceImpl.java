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
package com.pcs.guava.serviceImpl;

import static com.pcs.guava.constants.ServiceScheduleConstant.SERVICE_COMPONENT_TEMPLATE;
import static com.pcs.guava.constants.ServiceScheduleConstant.SERVICE_SCHEDULE_TEMPLATE;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER_KEY;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER_VALUE;
import static com.pcs.guava.enums.EMDataFields.MARKER;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.ONETIME;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.PERIODIC;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.RECURRING;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.SERVICE_COMPONENT_NAME;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.SERVICE_SCHEDULE_OCCURANCE_TYPE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.commons.dto.StatusMessageErrorDTO;
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.service.SubscriptionProfileService;
import com.pcs.guava.commons.validation.ValidationUtils;
import com.pcs.guava.commons.dto.DomainDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.EntityTemplateDTO;
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.PlatformEntityDTO;
import com.pcs.guava.commons.dto.StatusDTO;
import com.pcs.guava.constants.ServiceScheduleConstant;
import com.pcs.guava.dto.ServiceScheduleDTO;
import com.pcs.guava.dto.ServiceScheduleESBDTO;
import com.pcs.guava.enums.ServiceSchedulingDataFields;
import com.pcs.guava.enums.Status;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.services.ServiceScheduleService;

/**
 * @description This class is responsible for the ServiceScheduleServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @author Daniela (PCSEG191)
 * @date 20 Apr 2016
 */
@Service
public class ServiceScheduleServiceImpl implements ServiceScheduleService {

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Autowired
	@Qualifier("guavaEsbClient")
	private BaseClient guavaEsbClient;

	@Value("${guava.esb.serviceSchedule}")
	private String serviceSchedulePlatformEndpoint;

	@Value("${guava.esb.serviceSchedule.find}")
	private String findServiceScheduleEndpoint;

	@Value("${platform.esb.marker.list}")
	private String listMarkerEndpoint;

	@Autowired
	SubscriptionProfileService subscriptionProfileService;

	@Override
	public StatusMessageDTO createServiceSchedule(
			ServiceScheduleDTO serviceScheduleDTO) {
		validateFields(serviceScheduleDTO);

		Boolean isUpdate = false;
		EntityDTO entity = guavaEsbClient
				.post(serviceSchedulePlatformEndpoint,
						subscriptionProfileService.getJwtToken(),
						constructServiceScheduleESBPayload(serviceScheduleDTO,
								isUpdate), EntityDTO.class);

		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		if (entity != null) {
			status.setStatus(Status.SUCCESS);
		}

		return status;
	}

	private void validateFields(ServiceScheduleDTO serviceScheduleDTO) {
		// validate the mandatory fields
		ValidationUtils.validateMandatoryFields(serviceScheduleDTO,
				ServiceSchedulingDataFields.SERVICE_SCHEDULE_NAME,
				ServiceSchedulingDataFields.SERVICE_SCHEDULE_OCCURANCE_TYPE);

		ValidationUtils.validateCollection(
				ServiceSchedulingDataFields.SERVICE_COMPONENT_IDENTIFIERS,
				serviceScheduleDTO.getServiceComponentIdentifiers());
		
		//check if the identifiers provided are of Service Component Template
		for ( IdentityDTO serviceComponent : serviceScheduleDTO.getServiceComponentIdentifiers()) {
			if(!serviceComponent.getEntityTemplate().getEntityTemplateName().equalsIgnoreCase(SERVICE_COMPONENT_TEMPLATE)){
				throw new GalaxyException(
						GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED, 
						serviceComponent.getEntityTemplate().getEntityTemplateName());
			}
		}

		// Validate that the occuranceType is One Time,Recurring or Periodic
		String occuranceType = serviceScheduleDTO.getOccuranceType();
		if (!ONETIME.getFieldName().equals(occuranceType)
				&& !RECURRING.getFieldName().equals(occuranceType)
				&& !PERIODIC.getFieldName().equals(occuranceType)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
					SERVICE_SCHEDULE_OCCURANCE_TYPE.getDescription());
		}

		if (serviceScheduleDTO.getDomain() == null
				|| isBlank(serviceScheduleDTO.getDomain().getDomainName())) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(subscriptionProfileService.getSubscription()
					.getEndUserDomain());
			serviceScheduleDTO.setDomain(domain);
		}
	}

	private ServiceScheduleESBDTO constructServiceScheduleESBPayload(
			ServiceScheduleDTO serviceScheduleDTO, Boolean isUpdate) {

		ServiceScheduleESBDTO serviceScheduleESBDTO = new ServiceScheduleESBDTO();

		// Set Service Schedule Entity
		EntityDTO serviceScheduleEntity = new EntityDTO();
		serviceScheduleEntity.setDomain(serviceScheduleDTO.getDomain());
		
		// Set Service Schedule PlatformEntity(for Update)
		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER.getFieldName());
		serviceScheduleEntity.setPlatformEntity(platformEntityDTO);

		// Set Service Schedule Template
		EntityTemplateDTO serviceScheduleTemplate = new EntityTemplateDTO();
		serviceScheduleTemplate
				.setEntityTemplateName(SERVICE_SCHEDULE_TEMPLATE);
		serviceScheduleEntity.setEntityTemplate(serviceScheduleTemplate);

		// Set Service Schedule Field Values
		serviceScheduleEntity
				.setFieldValues(constructServiceScheduleFieldValues(serviceScheduleDTO));

		// Set Service Schedule's Status
		if (serviceScheduleDTO.getServiceScheduleStatus() != null
				&& isNotBlank(serviceScheduleDTO.getServiceScheduleStatus()
						.getStatusName())) {
			serviceScheduleEntity.setEntityStatus(serviceScheduleDTO
					.getServiceScheduleStatus());
		} else {
			// If the request is to Service Schedule's Status
			if (!isUpdate) {
				StatusDTO activeStatus = new StatusDTO();
				activeStatus.setStatusName(Status.ACTIVE.name());
				serviceScheduleEntity.setEntityStatus(activeStatus);
			}
		}
		// For update set the identifier
		if (isUpdate) {
			serviceScheduleEntity.setIdentifier(serviceScheduleDTO
					.getServiceScheduleIdentifier());
		}
		serviceScheduleESBDTO.setServiceSchedule(serviceScheduleEntity);

		// Set the Service Components Identifiers to be Attached
		serviceScheduleESBDTO.setServiceComponentIdentifiers(serviceScheduleDTO
				.getServiceComponentIdentifiers());
		return serviceScheduleESBDTO;
	}

	private List<FieldMapDTO> constructServiceScheduleFieldValues(
			ServiceScheduleDTO serviceScheduleDTO) {
		List<FieldMapDTO> serviceScheduleFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO serviceScheduleName = new FieldMapDTO();
		serviceScheduleName
				.setKey(ServiceSchedulingDataFields.SERVICE_SCHEDULE_NAME
						.getFieldName());
		serviceScheduleName.setValue(serviceScheduleDTO
				.getServiceScheduleName());

		FieldMapDTO serviceScheduleDesc = new FieldMapDTO();
		serviceScheduleDesc.setKey(ServiceSchedulingDataFields.DESCRIPTION
				.getFieldName());
		serviceScheduleDesc.setValue(serviceScheduleDTO.getDescription());

		FieldMapDTO serviceScheduleType = new FieldMapDTO();
		serviceScheduleType
				.setKey(ServiceSchedulingDataFields.SERVICE_SCHEDULE_OCCURANCE_TYPE
						.getFieldName());
		serviceScheduleType.setValue(serviceScheduleDTO.getOccuranceType());

		serviceScheduleFields.add(serviceScheduleName);
		serviceScheduleFields.add(serviceScheduleDesc);
		serviceScheduleFields.add(serviceScheduleType);

		return serviceScheduleFields;
	}

	@Override
	public StatusMessageDTO updateServiceSchedule(
			ServiceScheduleDTO serviceScheduleDTO) {
		validateFields(serviceScheduleDTO);
		ValidationUtils.validateMandatoryFields(serviceScheduleDTO,
				ServiceSchedulingDataFields.SERVICE_SCHEDULE_IDENTIFIER);
		ValidationUtils.validateMandatoryFields(
				serviceScheduleDTO.getServiceScheduleIdentifier(),
				IDENTIFIER_KEY, IDENTIFIER_VALUE);

		Boolean isUpdate = true;

		StatusMessageErrorDTO status = guavaEsbClient
				.put(serviceSchedulePlatformEndpoint,
						subscriptionProfileService.getJwtToken(),
						constructServiceScheduleESBPayload(serviceScheduleDTO,
								isUpdate), StatusMessageErrorDTO.class);
		if (isNotBlank(status.getErrorCode())
				&& status.getErrorCode().equalsIgnoreCase(
						GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED
								.getCode().toString())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
					status.getField());
		}
		// Set status as success
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public ServiceScheduleDTO findServiceSchedule(
			IdentityDTO serviceScheduleIdentity) {
		validateIdentifierFields(serviceScheduleIdentity);

		// Set ServiceSchedule Template for search
		setServiceScheduleEntityTemplate(serviceScheduleIdentity);

		ServiceScheduleDTO serviceScheduleDTO = guavaEsbClient.post(
				findServiceScheduleEndpoint,
				subscriptionProfileService.getJwtToken(),
				serviceScheduleIdentity, ServiceScheduleDTO.class);

		List<String> listOfServiceComponents = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(serviceScheduleDTO
				.getServiceComponentEntities())) {

			for (EntityDTO serviceComponentEntity : serviceScheduleDTO
					.getServiceComponentEntities()) {

				FieldMapDTO serviceComponentNameMap = new FieldMapDTO(
						SERVICE_COMPONENT_NAME.getFieldName());
				String serviceComponentName = fetchField(
						serviceComponentEntity.getDataprovider(),
						serviceComponentNameMap);
				listOfServiceComponents.add(serviceComponentName);
			}
			
			serviceScheduleDTO.setServiceComponentNames(listOfServiceComponents);
			serviceScheduleDTO.getServiceComponentEntities().clear();
		}
		return serviceScheduleDTO;
	}

	private String fetchField(List<FieldMapDTO> fieldMapDTOs,
			FieldMapDTO fieldMapDTO) {
		// populate field<FieldMapDTO> from input EntityDto
		FieldMapDTO field = new FieldMapDTO();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> findAllServiceSchedules(String domainName) {
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getSubscription()
					.getEndUserDomain();
		}
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);

		IdentityDTO serviceScheduleIdentity = new IdentityDTO();
		serviceScheduleIdentity.setDomain(domain);

		// Set ServiceSchedule Template for search
		setServiceScheduleEntityTemplate(serviceScheduleIdentity);

		List<EntityDTO> serviceSchedules = platformClient.post(
				listMarkerEndpoint, subscriptionProfileService.getJwtToken(),
				serviceScheduleIdentity, List.class, EntityDTO.class);

		return serviceSchedules;
	}

	@Override
	public StatusMessageDTO deleteServiceSchedule(
			IdentityDTO serviceScheduleIdentifier) {
		return null;
	}

	private void setServiceScheduleEntityTemplate(
			IdentityDTO serviceScheduleIdentity) {
		EntityTemplateDTO serviceScheduleTemplate = new EntityTemplateDTO();
		serviceScheduleTemplate
				.setEntityTemplateName(ServiceScheduleConstant.SERVICE_SCHEDULE_TEMPLATE);
		serviceScheduleIdentity.setEntityTemplate(serviceScheduleTemplate);
	}

	private void validateIdentifierFields(IdentityDTO serviceScheduleIdentity) {
		ValidationUtils.validateMandatoryFields(serviceScheduleIdentity,
				IDENTIFIER);
		ValidationUtils.validateMandatoryFields(
				serviceScheduleIdentity.getIdentifier(), IDENTIFIER_KEY,
				IDENTIFIER_VALUE);
		if (serviceScheduleIdentity.getDomain() == null
				|| isBlank(serviceScheduleIdentity.getDomain().getDomainName())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());
		}
	}

}

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
import static com.pcs.guava.enums.EMDataFields.DOMAIN;
import static com.pcs.guava.enums.EMDataFields.ENTITY_TEMPLATE;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER_KEY;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER_VALUE;
import static com.pcs.guava.enums.EMDataFields.PLATFORM_ENTITY;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.SERVICE_COMPONENT_FREQUENCY;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.SERVICE_COMPONENT_FREQUENCY_DISTANCE;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.SERVICE_COMPONENT_FREQUENCY_RUNNING_HOURS;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.SERVICE_COMPONENT_FREQUENCY_TIME;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

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
import com.pcs.guava.commons.dto.StatusDTO;
import com.pcs.guava.enums.Status;
import com.pcs.guava.constants.ServiceScheduleConstant;
import com.pcs.guava.dto.ServiceComponentDTO;
import com.pcs.guava.dto.ServiceScheduleESBDTO;
import com.pcs.guava.enums.ServiceSchedulingDataFields;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.services.ServiceComponentService;

/**
 * @description This class is responsible for the ServiceComponentServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @author Daniela (PCSEG191)
 * @date 20 Apr 2016
 */
@Service
public class ServiceComponentServiceImpl implements ServiceComponentService {

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;
	
	@Autowired
	@Qualifier("guavaEsbClient")
	private BaseClient guavaEsbClient;

	@Value("${guava.esb.serviceSchedule.serviceComponent}")
	private String serviceComponentPlatformEndpoint;

	@Value("${guava.esb.serviceSchedule.serviceComponent.find}")
	private String findServiceComponentEndpoint;

	@Value("${platform.esb.marker.list}")
	private String listMarkerEndpoint;

	@Autowired
	SubscriptionProfileService subscriptionProfileService;

	@Override
	public StatusMessageDTO createServiceComponent(
			ServiceComponentDTO serviceComponentDTO) {
		validateFields(serviceComponentDTO);

		// Create requires ServiceItem Identity
		ValidationUtils.validateMandatoryFields(
				serviceComponentDTO.getServiceItemEntity(), PLATFORM_ENTITY,
				DOMAIN, ENTITY_TEMPLATE, IDENTIFIER);

		Boolean isUpdate = false;
		EntityDTO entity = guavaEsbClient.post(
				serviceComponentPlatformEndpoint,
				subscriptionProfileService.getJwtToken(),
				constructServiceComponentESBPayload(serviceComponentDTO,
						isUpdate), EntityDTO.class);

		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		if (entity != null) {
			status.setStatus(Status.SUCCESS);
		}

		return status;
	}

	private void validateFields(ServiceComponentDTO serviceComponentDTO) {
		// validate the mandatory fields
		ValidationUtils.validateMandatoryFields(serviceComponentDTO,
				ServiceSchedulingDataFields.SERVICE_COMPONENT_NAME);

		// Check Frequency
		checkFrequencyType(serviceComponentDTO);

		if (serviceComponentDTO.getDomain() == null
				|| isBlank(serviceComponentDTO.getDomain().getDomainName())) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(subscriptionProfileService.getSubscription()
					.getEndUserDomain());
			serviceComponentDTO.setDomain(domain);
		}
	}

	private void checkFrequencyType(ServiceComponentDTO serviceComponentDTO) {
		// Check if any of the frequency types have been entered(Distance,
		// Running Hours, Time)
		if (isBlank(serviceComponentDTO.getFrequencyInDistance())
				&& isBlank(serviceComponentDTO.getFrequencyInRunningHours())
				&& isBlank(serviceComponentDTO.getFrequencyInTime())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					SERVICE_COMPONENT_FREQUENCY.getDescription());
		}

		// If Notification of Distance is entered but Distance Frequency is not
		// Entered
		if (isNotBlank(serviceComponentDTO.getNotificationInDistance())) {
			if (isBlank(serviceComponentDTO.getFrequencyInDistance())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
						SERVICE_COMPONENT_FREQUENCY_DISTANCE.getDescription());
			}
		}

		// If Notification of Running Hours is entered but Running Hours
		// Frequency is not Entered
		if (isNotBlank(serviceComponentDTO.getNotificationInRunningHours())) {
			if (isBlank(serviceComponentDTO.getFrequencyInRunningHours())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
						SERVICE_COMPONENT_FREQUENCY_RUNNING_HOURS
								.getDescription());
			}
		}

		// If Notification of Time is entered but Time Frequency is not Entered
		if (isNotBlank(serviceComponentDTO.getNotificationInTime())) {
			if (isBlank(serviceComponentDTO.getFrequencyInTime())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
						SERVICE_COMPONENT_FREQUENCY_TIME.getDescription());
			}
		}

		// If Notification of Distance is not entered but Distance Frequency is
		// Entered
		if (isBlank(serviceComponentDTO.getNotificationInDistance())) {
			if (isNotBlank(serviceComponentDTO.getFrequencyInDistance())) {
				Integer freqDistance = Integer.parseInt(serviceComponentDTO
						.getFrequencyInDistance());
				serviceComponentDTO.setNotificationInDistance(String
						.valueOf(freqDistance - 1));
			}
		}

		// If Notification of Running Hours is not entered but Running Hours
		// Frequency is Entered
		if (isBlank(serviceComponentDTO.getNotificationInRunningHours())) {
			if (isNotBlank(serviceComponentDTO.getFrequencyInRunningHours())) {
				Integer freqRunningHours = Integer.parseInt(serviceComponentDTO
						.getFrequencyInRunningHours());
				serviceComponentDTO.setNotificationInRunningHours(String
						.valueOf(freqRunningHours - 1));
			}
		}

		// If Notification of Time is not entered but Time Frequency is Entered
		if (isBlank(serviceComponentDTO.getNotificationInTime())) {
			if (isNotBlank(serviceComponentDTO.getFrequencyInTime())) {
				Integer freqTime = Integer.parseInt(serviceComponentDTO
						.getFrequencyInTime());
				serviceComponentDTO.setNotificationInTime(String
						.valueOf(freqTime - 1));
			}
		}
	}

	private ServiceScheduleESBDTO constructServiceComponentESBPayload(
			ServiceComponentDTO serviceComponentDTO, Boolean isUpdate) {

		ServiceScheduleESBDTO serviceScheduleESBDTO = new ServiceScheduleESBDTO();

		// Set Service Component Entity
		EntityDTO serviceComponentEntity = new EntityDTO();
		serviceComponentEntity.setDomain(serviceComponentDTO.getDomain());

		// Set Service Component Template
		EntityTemplateDTO serviceComponentTemplate = new EntityTemplateDTO();
		serviceComponentTemplate
				.setEntityTemplateName(SERVICE_COMPONENT_TEMPLATE);
		serviceComponentEntity.setEntityTemplate(serviceComponentTemplate);

		serviceComponentEntity
				.setFieldValues(constructServiceComponentFieldValues(serviceComponentDTO));
		if (serviceComponentDTO.getServiceComponentStatus() != null
				&& isNotBlank(serviceComponentDTO.getServiceComponentStatus()
						.getStatusName())) {
			serviceComponentEntity.setEntityStatus(serviceComponentDTO
					.getServiceComponentStatus());
		} else {
			if (!isUpdate) {
				StatusDTO activeStatus = new StatusDTO();
				activeStatus.setStatusName(Status.ACTIVE.name());
				serviceComponentEntity.setEntityStatus(activeStatus);
			}
		}
		// For update set the identifier
		if (isUpdate) {
			serviceComponentEntity.setIdentifier(serviceComponentDTO
					.getServiceComponentIdentifier());
		}
		serviceScheduleESBDTO.setServiceComponent(serviceComponentEntity);
		serviceScheduleESBDTO.setServiceItem(serviceComponentDTO
				.getServiceItemEntity());
		return serviceScheduleESBDTO;
	}

	private List<FieldMapDTO> constructServiceComponentFieldValues(
			ServiceComponentDTO serviceComponentDTO) {
		List<FieldMapDTO> serviceComponentFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO serviceComponentName = new FieldMapDTO();
		serviceComponentName
				.setKey(ServiceSchedulingDataFields.SERVICE_COMPONENT_NAME
						.getFieldName());
		serviceComponentName.setValue(serviceComponentDTO
				.getServiceComponentName());

		FieldMapDTO serviceComponentDesc = new FieldMapDTO();
		serviceComponentDesc.setKey(ServiceSchedulingDataFields.DESCRIPTION
				.getFieldName());
		serviceComponentDesc.setValue(serviceComponentDTO.getDescription());

		FieldMapDTO serviceComponentFrequencyInDistance = new FieldMapDTO();
		serviceComponentFrequencyInDistance
				.setKey(ServiceSchedulingDataFields.SERVICE_COMPONENT_FREQUENCY_DISTANCE
						.getFieldName());
		serviceComponentFrequencyInDistance.setValue(serviceComponentDTO
				.getFrequencyInDistance());

		FieldMapDTO serviceComponentFrequencyInRunningHours = new FieldMapDTO();
		serviceComponentFrequencyInRunningHours
				.setKey(ServiceSchedulingDataFields.SERVICE_COMPONENT_FREQUENCY_RUNNING_HOURS
						.getFieldName());
		serviceComponentFrequencyInRunningHours.setValue(serviceComponentDTO
				.getFrequencyInRunningHours());

		FieldMapDTO serviceComponentFrequencyInTime = new FieldMapDTO();
		serviceComponentFrequencyInTime
				.setKey(ServiceSchedulingDataFields.SERVICE_COMPONENT_FREQUENCY_TIME
						.getFieldName());
		serviceComponentFrequencyInTime.setValue(serviceComponentDTO
				.getFrequencyInTime());

		FieldMapDTO serviceComponentNotificationInDistance = new FieldMapDTO();
		serviceComponentNotificationInDistance
				.setKey(ServiceSchedulingDataFields.SERVICE_COMPONENT_NOTIFICATION_IN_DISTANCE
						.getFieldName());
		serviceComponentNotificationInDistance.setValue(serviceComponentDTO
				.getNotificationInDistance());

		FieldMapDTO serviceComponentNotificationInRunningHours = new FieldMapDTO();
		serviceComponentNotificationInRunningHours
				.setKey(ServiceSchedulingDataFields.SERVICE_COMPONENT_NOTIFICATION_IN_RUNNING_HOURS
						.getFieldName());
		serviceComponentNotificationInRunningHours.setValue(serviceComponentDTO
				.getNotificationInRunningHours());

		FieldMapDTO serviceComponentNotificationInTime = new FieldMapDTO();
		serviceComponentNotificationInTime
				.setKey(ServiceSchedulingDataFields.SERVICE_COMPONENT_NOTIFICATION_IN_TIME
						.getFieldName());
		serviceComponentNotificationInTime.setValue(serviceComponentDTO
				.getNotificationInTime());

		serviceComponentFields.add(serviceComponentName);
		serviceComponentFields.add(serviceComponentDesc);
		serviceComponentFields.add(serviceComponentFrequencyInDistance);
		serviceComponentFields.add(serviceComponentFrequencyInRunningHours);
		serviceComponentFields.add(serviceComponentFrequencyInTime);
		serviceComponentFields.add(serviceComponentNotificationInDistance);
		serviceComponentFields.add(serviceComponentNotificationInRunningHours);
		serviceComponentFields.add(serviceComponentNotificationInTime);

		return serviceComponentFields;
	}

	@Override
	public StatusMessageDTO updateServiceComponent(
			ServiceComponentDTO serviceComponentDTO) {
		validateFields(serviceComponentDTO);
		ValidationUtils.validateMandatoryFields(serviceComponentDTO,
				ServiceSchedulingDataFields.SERVICE_COMPONENT_IDENTIFIER);
		ValidationUtils.validateMandatoryFields(
				serviceComponentDTO.getServiceComponentIdentifier(),
				IDENTIFIER_KEY, IDENTIFIER_VALUE);

		Boolean isUpdate = true;

		StatusMessageErrorDTO status = guavaEsbClient.put(
				serviceComponentPlatformEndpoint,
				subscriptionProfileService.getJwtToken(),
				constructServiceComponentESBPayload(serviceComponentDTO,
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
	public ServiceComponentDTO findServiceComponent(
			IdentityDTO serviceComponentIdentity) {
		validateIdentifierFields(serviceComponentIdentity);

		// Set ServiceComponent Template for search
		setServiceComponentEntityTemplate(serviceComponentIdentity);

		ServiceComponentDTO serviceComponentDTO = platformClient.post(
				findServiceComponentEndpoint,
				subscriptionProfileService.getJwtToken(),
				serviceComponentIdentity, ServiceComponentDTO.class);

		return serviceComponentDTO;
	}

	@Override
	public List<EntityDTO> findAllServiceComponents(String domainName) {
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getSubscription()
					.getEndUserDomain();
		}
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);

		IdentityDTO serviceComponentIdentity = new IdentityDTO();
		serviceComponentIdentity.setDomain(domain);

		// Set ServiceComponent Template for search
		setServiceComponentEntityTemplate(serviceComponentIdentity);

		List<EntityDTO> serviceComponents = platformClient.post(
				listMarkerEndpoint, subscriptionProfileService.getJwtToken(),
				serviceComponentIdentity, List.class, EntityDTO.class);

		return serviceComponents;
	}

	@Override
	public StatusMessageDTO deleteServiceComponent(
			IdentityDTO serviceComponentIdentifier) {
		return null;
	}

	private void setServiceComponentEntityTemplate(
			IdentityDTO serviceComponentIdentity) {
		EntityTemplateDTO serviceComponentTemplate = new EntityTemplateDTO();
		serviceComponentTemplate
				.setEntityTemplateName(ServiceScheduleConstant.SERVICE_COMPONENT_TEMPLATE);
		serviceComponentIdentity.setEntityTemplate(serviceComponentTemplate);
	}

	private void validateIdentifierFields(IdentityDTO serviceComponentIdentity) {
		ValidationUtils.validateMandatoryFields(serviceComponentIdentity,
				IDENTIFIER);
		ValidationUtils.validateMandatoryFields(
				serviceComponentIdentity.getIdentifier(), IDENTIFIER_KEY,
				IDENTIFIER_VALUE);
		if (serviceComponentIdentity.getDomain() == null
				|| isBlank(serviceComponentIdentity.getDomain().getDomainName())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());
		}
	}

}

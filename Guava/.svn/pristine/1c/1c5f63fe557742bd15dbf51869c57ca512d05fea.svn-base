/**
 * Copyright 2016 Pacific Controls Software Services LLC (PCSS). All Rights
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

import static com.pcs.guava.constants.ServiceScheduleConstant.SERVICE_ITEM_TEMPLATE;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER_KEY;
import static com.pcs.guava.enums.EMDataFields.IDENTIFIER_VALUE;
import static com.pcs.guava.enums.EMDataFields.MARKER;
import static com.pcs.guava.enums.ServiceSchedulingDataFields.SERVICE_ITEM_IDENTIFIER;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.guava.commons.dto.DomainDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.EntityTemplateDTO;
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.PlatformEntityDTO;
import com.pcs.guava.commons.dto.StatusDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.commons.dto.StatusMessageErrorDTO;
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.service.SubscriptionProfileService;
import com.pcs.guava.commons.validation.ValidationUtils;
import com.pcs.guava.dto.ServiceItemDTO;
import com.pcs.guava.dto.ServiceScheduleESBDTO;
import com.pcs.guava.enums.ServiceSchedulingDataFields;
import com.pcs.guava.enums.Status;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.services.ServiceItemService;

/**
 * @description This class is responsible for the ServiceItemServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 09 May 2016
 */
@Service
public class ServiceItemServiceImpl implements ServiceItemService {

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Autowired
	@Qualifier("guavaEsbClient")
	private BaseClient guavaEsbClient;

	@Value("${guava.esb.serviceSchedule.serviceItem}")
	private String serviceItemPlatformEndpoint;

	@Value("${guava.esb.serviceSchedule.serviceItem.find}")
	private String findServiceItemEndpoint;

	@Value("${guava.esb.serviceSchedule.serviceItem.attachedServiceComponents}")
	private String attachedServiceComponentsEndpoint;

	@Value("${platform.esb.marker.list}")
	private String listMarkerEndpoint;

	@Autowired
	SubscriptionProfileService subscriptionProfileService;

	@Override
	public StatusMessageDTO createServiceItem(ServiceItemDTO serviceItemDTO) {

		validateFields(serviceItemDTO);
		Boolean isUpdate = false;
		// ServiceScheduleESBDTO serviceScheduleESBDTO =
		// constructServiceItemESBPayload(
		// serviceItemDTO, isUpdate);
		EntityDTO entity = guavaEsbClient.post(serviceItemPlatformEndpoint,
				subscriptionProfileService.getJwtToken(),
				constructServiceItemESBPayload(serviceItemDTO, isUpdate),
				EntityDTO.class);

		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		if (entity != null) {
			status.setStatus(Status.SUCCESS);
		}

		return status;
	}

	private ServiceScheduleESBDTO constructServiceItemESBPayload(
			ServiceItemDTO serviceItemDTO, Boolean isUpdate) {

		ServiceScheduleESBDTO serviceScheduleESBDTO = new ServiceScheduleESBDTO();

		// Set Service Item Entity
		EntityDTO serviceItemEntity = new EntityDTO();
		serviceItemEntity.setDomain(serviceItemDTO.getDomain());

		EntityTemplateDTO serviceItemTemplate = new EntityTemplateDTO();
		serviceItemTemplate.setEntityTemplateName(SERVICE_ITEM_TEMPLATE);
		serviceItemEntity.setEntityTemplate(serviceItemTemplate);

		serviceItemEntity
				.setFieldValues(constructServiceItemFieldValues(serviceItemDTO));
		if (serviceItemDTO.getServiceItemStatus() != null
				&& isNotBlank(serviceItemDTO.getServiceItemStatus()
						.getStatusName())) {
			serviceItemEntity.setEntityStatus(serviceItemDTO
					.getServiceItemStatus());
		} else {
			if (!isUpdate) {
				StatusDTO activeStatus = new StatusDTO();
				activeStatus.setStatusName(Status.ACTIVE.name());
				serviceItemEntity.setEntityStatus(activeStatus);
			}
		}
		// For update set the identifier
		if (isUpdate) {
			serviceItemEntity.setIdentifier(serviceItemDTO
					.getServiceItemIdentifier());
		}
		serviceScheduleESBDTO.setServiceItem(serviceItemEntity);

		// Set the List of Tags to the ESB Payload if list of tags is present
		if (serviceItemDTO.getListOfTags() != null) {
			if (!serviceItemDTO.getListOfTags().isEmpty()) {
				serviceScheduleESBDTO.setListOfTags(serviceItemDTO
						.getListOfTags());
			}
		}

		return serviceScheduleESBDTO;
	}

	private void validateFields(ServiceItemDTO serviceItemDTO) {
		// validate the mandatory fields
		ValidationUtils.validateMandatoryFields(serviceItemDTO,
				ServiceSchedulingDataFields.SERVICE_ITEM_NAME);

		if (serviceItemDTO.getDomain() == null
				|| isBlank(serviceItemDTO.getDomain().getDomainName())) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(subscriptionProfileService.getSubscription()
					.getEndUserDomain());
			serviceItemDTO.setDomain(domain);
		}
	}

	private List<FieldMapDTO> constructServiceItemFieldValues(
			ServiceItemDTO serviceItemDTO) {
		List<FieldMapDTO> serviceItemFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO serviceItemName = new FieldMapDTO();
		serviceItemName.setKey(ServiceSchedulingDataFields.SERVICE_ITEM_NAME
				.getFieldName());
		serviceItemName.setValue(serviceItemDTO.getServiceItemName());

		FieldMapDTO serviceItemDesc = new FieldMapDTO();
		serviceItemDesc.setKey(ServiceSchedulingDataFields.DESCRIPTION
				.getFieldName());
		if (StringUtils.isNotBlank(serviceItemDTO.getDescription())) {
			serviceItemDesc.setValue(serviceItemDTO.getDescription());
		} else {
			serviceItemDesc.setValue("");
		}

		serviceItemFields.add(serviceItemName);
		serviceItemFields.add(serviceItemDesc);

		return serviceItemFields;
	}

	@Override
	public StatusMessageDTO updateServiceItem(ServiceItemDTO serviceItemDTO) {

		validateFields(serviceItemDTO);
		ValidationUtils.validateMandatoryFields(serviceItemDTO,
				SERVICE_ITEM_IDENTIFIER);
		ValidationUtils.validateMandatoryFields(
				serviceItemDTO.getServiceItemIdentifier(), IDENTIFIER_KEY,
				IDENTIFIER_VALUE);

		Boolean isUpdate = true;

		// ServiceScheduleESBDTO serviceScheduleESBDTO =
		// constructServiceItemESBPayload(
		// serviceItemDTO, isUpdate);
		StatusMessageErrorDTO status = platformClient.put(
				serviceItemPlatformEndpoint,
				subscriptionProfileService.getJwtToken(),
				constructServiceItemESBPayload(serviceItemDTO, isUpdate),
				StatusMessageErrorDTO.class);
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
	public ServiceItemDTO findServiceItem(IdentityDTO serviceItemIdentity) {
		validateIdentifierFields(serviceItemIdentity);

		// Set ServiceItem Template for search
		setServiceItemEntityTemplate(serviceItemIdentity);

		ServiceItemDTO serviceItemDTO = guavaEsbClient.post(
				findServiceItemEndpoint,
				subscriptionProfileService.getJwtToken(), serviceItemIdentity,
				ServiceItemDTO.class);

		return serviceItemDTO;
	}

	private void setServiceItemEntityTemplate(IdentityDTO serviceItemIdentity) {
		EntityTemplateDTO serviceItemTemplate = new EntityTemplateDTO();
		serviceItemTemplate.setEntityTemplateName(SERVICE_ITEM_TEMPLATE);
		serviceItemIdentity.setEntityTemplate(serviceItemTemplate);
	}

	private void validateIdentifierFields(IdentityDTO serviceItemIdentity) {
		ValidationUtils
				.validateMandatoryFields(serviceItemIdentity, IDENTIFIER);
		ValidationUtils.validateMandatoryFields(
				serviceItemIdentity.getIdentifier(), IDENTIFIER_KEY,
				IDENTIFIER_VALUE);
		if (serviceItemIdentity.getDomain() == null
				|| isBlank(serviceItemIdentity.getDomain().getDomainName())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());
		}
	}

	@Override
	public List<EntityDTO> findAllServiceItems(String domainName) {

		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getSubscription()
					.getEndUserDomain();
		}
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);

		IdentityDTO serviceItemIdentity = new IdentityDTO();
		serviceItemIdentity.setDomain(domain);

		// Set ServiceItem Template for search
		setServiceItemEntityTemplate(serviceItemIdentity);

		List<EntityDTO> serviceItems = platformClient.post(listMarkerEndpoint,
				subscriptionProfileService.getJwtToken(), serviceItemIdentity,
				List.class, EntityDTO.class);

		return serviceItems;
	}

	@Override
	public List<EntityDTO> getAttachedServiceComponents(
			IdentityDTO serviceItemIdentity) {

		validateIdentifierFields(serviceItemIdentity);

		ServiceScheduleESBDTO serviceScheduleESBDTO = constructServiceItemESBPayload(serviceItemIdentity);

		List<EntityDTO> serviceComponentEntities = guavaEsbClient.post(
				attachedServiceComponentsEndpoint,
				subscriptionProfileService.getJwtToken(),
				constructServiceItemESBPayload(serviceItemIdentity),
				List.class, EntityDTO.class);

		return serviceComponentEntities;
	}

	private ServiceScheduleESBDTO constructServiceItemESBPayload(
			IdentityDTO serviceItemIdentity) {

		ServiceScheduleESBDTO serviceScheduleESBDTO = new ServiceScheduleESBDTO();

		if (serviceItemIdentity.getDomain() == null
				|| isBlank(serviceItemIdentity.getDomain().getDomainName())) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(subscriptionProfileService.getSubscription()
					.getEndUserDomain());
			serviceItemIdentity.setDomain(domain);
		}

		// Set Service Item Entity
		EntityDTO serviceItemEntity = new EntityDTO();
		serviceItemEntity.setDomain(serviceItemIdentity.getDomain());

		EntityTemplateDTO serviceItemTemplate = new EntityTemplateDTO();
		serviceItemTemplate.setEntityTemplateName(SERVICE_ITEM_TEMPLATE);
		serviceItemEntity.setEntityTemplate(serviceItemTemplate);

		PlatformEntityDTO serviceItemPlatformEntityType = new PlatformEntityDTO();
		serviceItemPlatformEntityType.setPlatformEntityType(MARKER
				.getFieldName());
		serviceItemEntity.setPlatformEntity(serviceItemPlatformEntityType);

		serviceItemEntity.setIdentifier(serviceItemIdentity.getIdentifier());

		serviceScheduleESBDTO.setServiceItem(serviceItemEntity);
		return serviceScheduleESBDTO;
	}

	@Override
	public StatusMessageDTO deleteServiceItem(IdentityDTO serviceItemIdentifier) {
		return null;
	}

}

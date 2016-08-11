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
package com.pcs.fms.web.services;

import static com.pcs.fms.web.constants.FMSWebConstants.COMPONENT_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.DESCRIPTION;
import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICE_ITEM_NAME;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.ServiceComponentDTO;
import com.pcs.fms.web.dto.StatusDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.ServiceComponent;

/**
 * @author PCSEG191 Daniela
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class ServiceComponentService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TenantService.class);

	@Value("${fms.services.findServiceComponents}")
	private String findServiceComponentEndpointUri;

	@Value("${fms.services.createServiceComponents}")
	private String createServiceComponentEndpointUri;

	@Value("${fms.services.updateServiceComponents}")
	private String updateServiceComponentEndpointUri;

	@Value("${fms.services.listServiceComponents}")
	private String listServiceComponentEndpointUri;

	/**
	 * Method to view service component details
	 * 
	 * @param
	 * @return
	 */
	public ServiceComponent getServiceComponentDetails(IdentityDTO identity) {
		String findServiceComponentDetailsServiceURI = getServiceURI(findServiceComponentEndpointUri);

		FMSResponse<ServiceComponentDTO> serviceComponent = getPlatformClient()
		        .postResource(findServiceComponentDetailsServiceURI, identity,
		                ServiceComponentDTO.class);
		ServiceComponent component = null;
		if (serviceComponent.getEntity() != null) {
			component = constructServiceCompDTO(serviceComponent.getEntity());
		}
		return component;
	}

	public List<ServiceComponent> getServiceComponents() {
		String domain = FMSAccessManager.getCurrentDomain();
		String getAllServiceCompsServiceURI = getServiceURI(
		        listServiceComponentEndpointUri).replace("{domain}", domain);

		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		FMSResponse<List<EntityDTO>> components = new FMSResponse<List<EntityDTO>>();
		components = getPlatformClient().getResource(
		        getAllServiceCompsServiceURI, entityListType);
		List<ServiceComponent> serviceComps = new ArrayList<ServiceComponent>();

		if (components.getErrorMessage() == null
		        || isBlank(components.getErrorMessage().getErrorMessage())) {
			for (EntityDTO comp : components.getEntity()) {
				serviceComps.add(constructServComp(comp));
			}
		}
		return serviceComps;

	}

	public FMSResponse<StatusMessageDTO> updateServiceComponent(
	        ServiceComponent serviceComponent) {
		String updateServiceComponentURI = getServiceURI(updateServiceComponentEndpointUri);
		// Construct ServiceComponentDTO
		ServiceComponentDTO serviceComponentDTO = constructServiceComponentDTO(serviceComponent);

		// Invoke update endpoint
		return getPlatformClient().putResource(updateServiceComponentURI,
		        serviceComponentDTO, StatusMessageDTO.class);

	}

	public FMSResponse<StatusMessageDTO> createServiceComponent(
	        ServiceComponent serviceComponent, EntityDTO selectedServiceItem) {
		String createServiceComponentURI = getServiceURI(createServiceComponentEndpointUri);
		// Construct ServiceComponentDTO
		ServiceComponentDTO serviceComponentDTO = constructServiceComponentDTO(serviceComponent);
		serviceComponentDTO.setServiceItemEntity(selectedServiceItem);

		// Invoke update endpoint
		return getPlatformClient().postResource(createServiceComponentURI,
		        serviceComponentDTO, StatusMessageDTO.class);

	}

	private ServiceComponentDTO constructServiceComponentDTO(
	        ServiceComponent serviceComponent) {
		ServiceComponentDTO serviceComponentDTO = new ServiceComponentDTO();
		serviceComponentDTO.setServiceComponentName(serviceComponent
		        .getServiceComponentName());
		serviceComponentDTO.setDescription(serviceComponent.getDescription());
		serviceComponentDTO.setFrequencyInDistance(serviceComponent
		        .getFrequencyInDistance());
		serviceComponentDTO.setFrequencyInTime(serviceComponent
		        .getFrequencyInTime());
		serviceComponentDTO.setFrequencyInRunningHours(serviceComponent
		        .getFrequencyInRunningHours());
		serviceComponentDTO.setNotificationInDistance(serviceComponent
		        .getNotificationInDistance());
		serviceComponentDTO.setNotificationInRunningHours(serviceComponent
		        .getNotificationInRunningHours());
		serviceComponentDTO.setNotificationInTime(serviceComponent
		        .getNotificationInTime());
		if (isNotBlank(serviceComponent.getAction())
		        && serviceComponent.getAction().equalsIgnoreCase("Update")) {
			FieldMapDTO componentId = new FieldMapDTO();
			componentId.setKey(IDENTIFIER);
			componentId.setValue(serviceComponent.getIdentifier());
			serviceComponentDTO.setServiceComponentIdentifier(componentId);
		}
		if (isBlank(serviceComponent.getDomain())) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(FMSAccessManager.getCurrentDomain());
			serviceComponentDTO.setDomain(domain);
		}
		// Set status
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName("ACTIVE");
		serviceComponentDTO.setServiceComponentStatus(statusDTO);
		return serviceComponentDTO;
	}

	private ServiceComponent constructServiceCompDTO(ServiceComponentDTO res) {
		ServiceComponent serviceComponent = new ServiceComponent();
		if (res != null) {
			List<FieldMapDTO> fieldValues = res.getServiceItemValues();
			for (FieldMapDTO fieldMapDTO : fieldValues) {
				if (fieldMapDTO.getKey().equals(SERVICE_ITEM_NAME)) {
					serviceComponent.setServiceItemName(fieldMapDTO.getValue());
				}
			}
			StatusDTO status = res.getServiceComponentStatus();
			if (status != null) {
				if (isNotBlank(status.getStatusName())) {
					serviceComponent.setStatus(status.getStatusName());
				}
			}
		}
		serviceComponent.setFrequencyInDistance(res.getFrequencyInDistance());
		serviceComponent.setFrequencyInRunningHours(res
		        .getFrequencyInRunningHours());
		serviceComponent.setFrequencyInTime(res.getFrequencyInTime());
		serviceComponent.setNotificationInDistance(res
		        .getNotificationInDistance());
		serviceComponent.setNotificationInRunningHours(res
		        .getNotificationInRunningHours());
		serviceComponent.setNotificationInTime(res.getNotificationInTime());
		serviceComponent.setDomain(res.getDomain().getDomainName());
		serviceComponent.setIdentifier(res.getServiceComponentIdentifier()
		        .getValue());
		serviceComponent.setServiceComponentName(res.getServiceComponentName());
		serviceComponent.setDescription(res.getDescription());
		serviceComponent.setServiceComponentNameEdit(res
		        .getServiceComponentName());
		return serviceComponent;
	}

	private ServiceComponent constructServComp(EntityDTO res) {
		ServiceComponent serviceComponent = new ServiceComponent();
		if (res != null) {
			for (FieldMapDTO fieldMapDTO : res.getDataprovider()) {
				if (fieldMapDTO.getKey().equals(COMPONENT_NAME)) {
					serviceComponent.setServiceComponentName(fieldMapDTO
					        .getValue());
				} else if (fieldMapDTO.getKey().equals(DESCRIPTION)) {
					serviceComponent.setDescription(fieldMapDTO.getValue());
				}
			}
			serviceComponent.setIdentifier(res.getIdentifier().getValue());
			serviceComponent.setDomain(res.getDomain().getDomainName());
		}
		return serviceComponent;
	}

}

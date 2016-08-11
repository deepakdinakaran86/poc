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

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.constants.FMSWebConstants;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.ServiceItemDTO;
import com.pcs.fms.web.dto.Status;
import com.pcs.fms.web.dto.StatusDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.ServiceModel;

/**
 * @author PCSEG362 Suraj Sugathan
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class ServiceItemService extends BaseService {

	@Value("${fms.services.createServiceItem}")
	private String createServiceItemEndpointUri;

	@Value("${fms.services.findServiceItem}")
	private String findServiceItemEndpointUri;

	@Value("${fms.services.listServiceItems}")
	private String listServiceItemsEndpointUri;

	@Value("${fms.services.updateServiceItem}")
	private String updateServiceItemEndpointUri;

	/**
	 * Method to list all Service Items
	 * 
	 * @param (query) domainName
	 * @return List<EntityTemplateDTO>
	 */
	public FMSResponse<List<EntityDTO>> listServiceItems(String currDomain) {
		String listServiceItemsServiceURI = getServiceURI(
				listServiceItemsEndpointUri).replace("{domain}", currDomain);

		Type serviceItems = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().getResource(listServiceItemsServiceURI,
		        serviceItems);
	}

	/**
	 * Method to get details Service Item
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<ServiceItemDTO> viewServiceItem(ServiceModel serviceModel) {
		String findServiceItemServiceURI = getServiceURI(findServiceItemEndpointUri);

		IdentityDTO identityDTO = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(serviceModel.getItemDomain());
		identityDTO.setDomain(domain);

		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(FMSWebConstants.IDENTIFIER);
		identifier.setValue(serviceModel.getItemIdentifier());

		identityDTO.setIdentifier(identifier);

		return getPlatformClient().postResource(findServiceItemServiceURI,
				identityDTO, ServiceItemDTO.class);
	}

	/**
	 * Method to create a service Item
	 * 
	 * @param ServiceModel
	 * @return StatusMessageDTO
	 */
	public FMSResponse<StatusMessageDTO> createServiceItem(
			ServiceItemDTO serviceItemDTO) {
		String createServiceItemServiceURI = getServiceURI(createServiceItemEndpointUri);

		FMSAccessManager fmsAM = new FMSAccessManager();
		String currDomain = fmsAM.getCurrentDomain();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(currDomain);
		serviceItemDTO.setDomain(domain);

		StatusDTO status = new StatusDTO();
		status.setStatusName(Status.ACTIVE.toString());
		serviceItemDTO.setServiceItemStatus(status);

		return getPlatformClient().postResource(createServiceItemServiceURI,
				serviceItemDTO, StatusMessageDTO.class);
	}

	/**
	 * Method to update a service Item
	 * 
	 * @param ServiceModel
	 * @return StatusMessageDTO
	 */
	public FMSResponse<StatusMessageDTO> updateServiceItem(
			ServiceItemDTO serviceItemDTO) {
		String updateServiceItemServiceURI = getServiceURI(updateServiceItemEndpointUri);

		StatusDTO status = new StatusDTO();
		status.setStatusName(Status.ACTIVE.toString());
		serviceItemDTO.setServiceItemStatus(status);

		return getPlatformClient().putResource(updateServiceItemServiceURI,
				serviceItemDTO, StatusMessageDTO.class);
	}

}

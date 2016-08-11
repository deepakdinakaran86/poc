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

import static com.pcs.fms.web.constants.FMSWebConstants.TENANT;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_ID;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_TEMPLATE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.PlatformEntityDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;

/**
 * @author PCSEG191 Daniela
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class PointMappingService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TenantService.class);

	@Value("${fms.devices.ownedlist}")
	private String ownedDeviceListEndpointUri;

	@Value("${fms.devices.points}")
	private String getDevicePointsEndpointUri;

	@Value("${fms.devices.mapPoints}")
	private String mapPointsEndpointUri;

	/**
	 * Method to get all the owned devices
	 * 
	 * @param
	 * @return
	 */
	public List<EntityDTO> getTenantsDevices() {

		String getOwnedDevicesServiceURI = getServiceURI(ownedDeviceListEndpointUri);

		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		// Get the current token
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());

		String currentDomain = null;
		String currentTenantId = null;

		// Check if a subclient is selected
		if (token.getIsSubClientSelected() != null
		        && token.getIsSubClientSelected()) {
			// get sub tenant selected domain
			currentDomain = token.getCurrentTenant().getDomainName();
			currentTenantId = token.getCurrentTenant().getTenantId();
		} else {
			currentDomain = token.getTenant().getDomainName();
			currentTenantId = token.getTenant().getTenantId();
		}
		// COnstruct payload
		IdentityDTO tenantIdentity = new IdentityDTO();
		FieldMapDTO tenantIdMap = new FieldMapDTO();
		tenantIdMap.setKey(TENANT_ID);

		// TODO remove this
		currentTenantId = "newtenantdan";
		tenantIdMap.setValue(currentTenantId);
		tenantIdentity.setIdentifier(tenantIdMap);

		DomainDTO domain = new DomainDTO();
		// TODO remove this
		currentDomain = "pcs.galaxy";
		domain.setDomainName(currentDomain);
		tenantIdentity.setDomain(domain);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(TENANT_TEMPLATE);
		tenantIdentity.setEntityTemplate(entityTemplateDTO);

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(TENANT);
		tenantIdentity.setPlatformEntity(platformEntity);
		FMSResponse<List<EntityDTO>> devices = new FMSResponse<>();

		try {
			devices = getPlatformClient().postResource(
			        getOwnedDevicesServiceURI, tenantIdentity, entityListType);
		} catch (Exception e) {
			LOGGER.debug("Error fetching devices" + e.getMessage());
		}

		List<EntityDTO> deviceEntities = new ArrayList<EntityDTO>();
		if (devices.getErrorMessage() == null) {
			deviceEntities = devices.getEntity();
		}
		return deviceEntities;
	}

}

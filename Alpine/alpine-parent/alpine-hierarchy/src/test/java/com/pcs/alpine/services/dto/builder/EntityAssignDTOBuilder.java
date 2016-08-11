/**
 * 
 */
package com.pcs.alpine.services.dto.builder;

/**
 * @author PCSEG362
 *
 */

import com.pcs.alpine.services.dto.EntityAssignDTO;
import com.pcs.alpine.services.dto.IdentityDTO;

public class EntityAssignDTOBuilder {

	private EntityAssignDTO entityAssignDTO;

	public EntityAssignDTO aEntityAssignDTO(IdentityDTO actor,
			String markerTemplateName, String searchTemplateName,
			String statusName) {

		if (entityAssignDTO == null) {
			entityAssignDTO = new EntityAssignDTO();
			entityAssignDTO.setActor(actor);
			entityAssignDTO.setMarkerTemplateName(markerTemplateName);
			entityAssignDTO.setSearchTemplateName(searchTemplateName);
			entityAssignDTO.setStatusName(statusName);
		}

		return entityAssignDTO;
	}

	public EntityAssignDTO build() {
		return entityAssignDTO;
	}
}

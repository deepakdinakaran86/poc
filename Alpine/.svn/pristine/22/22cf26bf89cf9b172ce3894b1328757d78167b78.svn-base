package com.pcs.alpine.services.dto.builder;

import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;

public class IdentityDTOBuilder {

	private IdentityDTO identityDTO;

	public IdentityDTO aIdentity(FieldMapDTO identifier, DomainDTO domain,
	        EntityTemplateDTO entityTemplateDTO) {

		if (identityDTO == null) {
			identityDTO = new IdentityDTO();
			identityDTO.setIdentifier(identifier);
			identityDTO.setDomain(domain);
			identityDTO.setEntityTemplate(entityTemplateDTO);
		}
		return identityDTO;
	}

	public IdentityDTO build() {
		return identityDTO;
	}

}

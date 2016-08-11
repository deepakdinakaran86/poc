package com.pcs.alpine.services.dto.builder;

import com.google.gson.Gson;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.GlobalEntityDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.enums.EMDataFields;

public class IdentityDTOBuilder {

	private IdentityDTO identityDTO;

	
	

	public IdentityDTOBuilder anIdentityDTO() {
		if (identityDTO == null) {
			identityDTO = new IdentityDTO();

			GlobalEntityDTO globalEntityDto = new GlobalEntityDTOBuilder()
					.aGlobalEntityDTO(EMDataFields.MARKER.getFieldName())
					.build();

			identityDTO.setGlobalEntity(globalEntityDto);

			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO("jll.pcs.com")
					.build();
			identityDTO.setDomain(domainDto);

			EntityTemplateDTO entityTemplateDto = new EntityTemplateDTOBuilder()
					.anEntityTemplateDTO("JLLcustomTemplate").build();
			identityDTO.setEntityTemplate(entityTemplateDto);

			FieldMapDTO identifier = new IdentifierDTOBuilder()
					.anIdentifierDTO("identifier", "6ecc719d-cdf0-4afc-a4d0-f1853133174f").build();
			identityDTO.setIdentifier(identifier);
		

		}
		return this;
	}
	
	public IdentityDTOBuilder anIdentityDTO(String globalEntity, String domain, String entityTemplateName, String key,String value ) {
		if (identityDTO == null) {
			identityDTO = new IdentityDTO();

			GlobalEntityDTO globalEntityDto = new GlobalEntityDTOBuilder()
					.aGlobalEntityDTO(globalEntity)
					.build();

			identityDTO.setGlobalEntity(globalEntityDto);

			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO(domain)
					.build();
			identityDTO.setDomain(domainDto);

			EntityTemplateDTO entityTemplateDto = new EntityTemplateDTOBuilder()
					.anEntityTemplateDTO(entityTemplateName).build();
			identityDTO.setEntityTemplate(entityTemplateDto);

			FieldMapDTO identifier = new IdentifierDTOBuilder()
					.anIdentifierDTO(key, value).build();
			identityDTO.setIdentifier(identifier);
		

		}
		return this;
	}

	public IdentityDTO build() {
		return identityDTO;
	}


	public static void main(String[] args) {
		System.out.println("********Testing DTO Builder**********");
	
		System.out.println(new Gson().toJson(new IdentityDTOBuilder()
		.anIdentityDTO().build()));
	
	}
}

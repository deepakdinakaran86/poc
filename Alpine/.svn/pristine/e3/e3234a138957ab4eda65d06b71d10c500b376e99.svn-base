package com.pcs.alpine.services.dto.builder;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.GlobalEntityDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.enums.EMDataFields;

public class EntityDTOBuilder {

	private EntityDTO entityDto;
	private IdentityDTO hierarchy;

	private final List<FieldMapDTO> fieldValues = new ArrayList<>();
	// private final String entityName = "INDIA";
	private final String parentEntity = "7a963717-883f-4d6b-b796-82f97a92726c";

	public EntityDTOBuilder anEntityDTO() {
		if (entityDto == null) {
			entityDto = new EntityDTO();
			entityDto.setParentEntityId(parentEntity);

			FieldMapDTO fieldMapDTO = new FieldMapDTOBuilder().aFieldMapDTO()
					.build();
			fieldValues.add(fieldMapDTO);
			entityDto.setFieldValues(fieldValues);

			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO().build();

			EntityTemplateDTO entityTemplateDto = new EntityTemplateDTOBuilder()
					.anEntityTemplateDTO().withDomainDTO(domainDto).build();
			entityDto.setEntityTemplate(entityTemplateDto);

			GlobalEntityDTO globalEntityDto = new GlobalEntityDTOBuilder()
					.aGlobalEntityDTO().build();
			entityDto.setGlobalEntity(globalEntityDto);

			StatusDTO statusDto = new StatusDTOBuilder().aStatusDTO().build();
			entityDto.setEntityStatus(statusDto);
		}
		return this;
	}

	public EntityDTOBuilder anEntityDTO(String entityName) {
		if (entityDto == null) {
			entityDto = new EntityDTO();

			GlobalEntityDTO globalEntityDto = new GlobalEntityDTOBuilder()
					.aGlobalEntityDTO("CUSTOM").build();
			entityDto.setGlobalEntity(globalEntityDto);
			StatusDTO statusDto = new StatusDTOBuilder().aStatusDTO("ACTIVE")
					.build();

			entityDto.setEntityStatus(statusDto);

			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO(
					"jll.pcs.com").build();
			entityDto.setDomain(domainDto);
			EntityTemplateDTO entityTemplateDto = new EntityTemplateDTOBuilder()
					.anEntityTemplateDTO("JLLcustomTemplate", "CUSTOM")
					.withDomainDTO(domainDto).build();
			entityDto.setEntityTemplate(entityTemplateDto);

			FieldMapDTO fieldMapDTO = new FieldMapDTOBuilder().aFieldMapDTO(
					"entityName", entityName).build();
			fieldValues.add(fieldMapDTO);
			entityDto.setFieldValues(fieldValues);
			entityDto.setEntityTemplate(entityTemplateDto);
			FieldMapDTO identifier = new IdentifierDTOBuilder()
					.anIdentifierDTO("identifier",
							"25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
			entityDto.setIdentifier(identifier);

		}
		return this;
	}

	public EntityDTOBuilder withHierarchyDTO() {
		if (entityDto != null) {
			hierarchy = new IdentityDTO();
			hierarchy = new IdentityDTOBuilder().anIdentityDTO(
					EMDataFields.ORGANIZATION.getFieldName(), "pcs.com",
					"PcsOrgTemplate", "orgName", "JLL").build();
			entityDto.setHierarchy(hierarchy);

		}
		return this;
	}

	public EntityDTOBuilder withHierarchyDTO(String entityType, String domain,
			String entityTemplate, String key, String value) {
		if (entityDto != null) {
			hierarchy = new IdentityDTO();
			hierarchy = new IdentityDTOBuilder().anIdentityDTO(entityType,
					domain, entityTemplate, key, value).build();
			entityDto.setHierarchy(hierarchy);

		}
		return this;
	}

	public EntityDTOBuilder withParametersEntityDTO(String globalEntity,
			String domain, String status, String entityTemplate,
			String identifierKey, String identifierValue) {
		if (entityDto == null) {
			entityDto = new EntityDTO();

			GlobalEntityDTO globalEntityDto = new GlobalEntityDTOBuilder()
					.aGlobalEntityDTO(globalEntity).build();

			entityDto.setGlobalEntity(globalEntityDto);

			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO(domain)
					.build();
			entityDto.setDomain(domainDto);

			StatusDTO entityStatus = new StatusDTO();
			entityStatus.setStatusName(status);
			entityDto.setEntityStatus(entityStatus);
			EntityTemplateDTO entityTemplateDto = new EntityTemplateDTOBuilder()
					.anEntityTemplateDTO(entityTemplate)
					.withDomainDTO(domainDto).build();
			entityDto.setEntityTemplate(entityTemplateDto);
			FieldMapDTO entityName = new IdentifierDTOBuilder()
					.anIdentifierDTO("entityName", "Test30").build();
			FieldMapDTO identifier = new IdentifierDTOBuilder()
					.anIdentifierDTO(identifierKey, identifierValue).build();

			fieldValues.add(entityName);
			entityDto.setFieldValues(fieldValues);
			entityDto.setIdentifier(identifier);

		}
		return this;
	}

	public EntityDTOBuilder withParametersEntityDTO(String globalEntity,
			String domain, String status, String entityTemplate,
			List<FieldMapDTO> fieldValues) {
		if (entityDto == null) {
			entityDto = new EntityDTO();
			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO(domain)
					.build();
			StatusDTO entityStatus = new StatusDTO();
			entityDto.setDomain(domainDto);
			entityStatus.setStatusName(status);
			entityDto.setEntityStatus(entityStatus);
			GlobalEntityDTO gdto = new GlobalEntityDTO();
			gdto.setGlobalEntityType(globalEntity);
			entityDto.setGlobalEntity(gdto);
			EntityTemplateDTO entityTemplateDto = new EntityTemplateDTOBuilder()
					.anEntityTemplateDTO(entityTemplate, domain)
					.withDomainDTO(domainDto).build();
			entityDto.setEntityTemplate(entityTemplateDto);
			entityDto.setFieldValues(fieldValues);

		}
		return this;
	}

	public EntityDTOBuilder withParametersUpdateEntityDTO(String domain,
			String status, String entityTemplate, List<FieldMapDTO> fieldValues) {
		if (entityDto == null) {
			entityDto = new EntityDTO();
			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO(domain)
					.build();
			StatusDTO entityStatus = new StatusDTO();
			entityDto.setDomain(domainDto);
			entityStatus.setStatusName(status);
			entityDto.setEntityStatus(entityStatus);

			EntityTemplateDTO entityTemplateDto = new EntityTemplateDTOBuilder()
					.anEntityTemplateDTO(entityTemplate).build();
			entityDto.setEntityTemplate(entityTemplateDto);
			entityDto.setFieldValues(fieldValues);

		}
		return this;
	}

	public EntityDTOBuilder createDomainEntityDTO(String domain, String entityId) {
		if (entityDto == null) {
			entityDto = new EntityDTO();
			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO(domain)
					.build();
			StatusDTO entityStatus = new StatusDTO();
			entityDto.setDomain(domainDto);
			// entityStatus.setStatusName(status);
			// entityDto.setEntityStatus(entityStatus);

			// EntityTemplateDTO entityTemplateDto = new
			// EntityTemplateDTOBuilder()
			// .anEntityTemplateDTO(entityTemplate).build();
			// entityDto.setEntityTemplate(entityTemplateDto);
			// entityDto.setFieldValues(fieldValues);
			entityDto.setEntityId(entityId);

		}
		return this;
	}

	public EntityDTO build() {
		return entityDto;
	}

	public static void main(String[] args) {
		System.out.println("********Testing DTO Builder**********");
		EntityDTO entityDTO = new EntityDTOBuilder().anEntityDTO("BRAZIL")
				.withHierarchyDTO().build();
		System.out.println(new Gson().toJson(entityDTO));

	}

}

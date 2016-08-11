package com.pcs.alpine.services.dto.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.enums.Status;

public class EntityTemplateDTOBuilder {

	private EntityTemplateDTO entityTemplateDto;

	private String entityTemplateName = "assetTemplate";

	public EntityTemplateDTOBuilder anEntityTemplateDTO() {
		if (entityTemplateDto == null) {
			entityTemplateDto = new EntityTemplateDTO();
			entityTemplateDto.setEntityTemplateName(entityTemplateName);
			DomainDTO domainDto = new DomainDTOBuilder().aDomainDTO().build();
			entityTemplateDto.setDomain(domainDto);
			Map<String, String> fieldValidation = new HashMap<String, String>();
		
			fieldValidation
	        .put("entityName",
	                "{\"client\":[],\"server\":[\"uniqueAcrossDomain\"],\"showOnGrid\":true,\"showOnTree\":true}");
			fieldValidation
	        .put("identifier",
	                "{\"client\":[],\"server\":[\"uniqueAcrossDomain\"],\"showOnGrid\":false,\"showOnTree\":false}");
		
			entityTemplateDto.setFieldValidation(fieldValidation);
			entityTemplateDto.setIdentifierField("identifier");
			
			
		}
		return this;
	}
	
	public EntityTemplateDTOBuilder anDeviceEntityTemplateDTO(String entityTemplateName) {
		if (entityTemplateDto == null) {
			entityTemplateDto = new EntityTemplateDTO();
			entityTemplateDto.setEntityTemplateName(entityTemplateName);
			entityTemplateDto.setUuid(UUID.fromString("eaa04840-cf9f-11e4-a0ce-cfd5c989dc9c"));
			DomainDTO domain = new DomainDTO();
			domain.setDomainName("jll.galaxy.com");
			entityTemplateDto.setDomain(domain);
			entityTemplateDto.setGlobalEntityTemplateName("GlobalG2021DeviceTemplate");
			entityTemplateDto.setGlobalEntityType("DEVICE");
			entityTemplateDto.setHtmlPage("check");
			entityTemplateDto.setIdentifierField("hostId");
			entityTemplateDto.setIsModifiable(true);
			entityTemplateDto.setIsSharable(true);
			StatusDTO status = new StatusDTO();
			status.setStatusKey(0);
			status.setStatusName(Status.ACTIVE.name());
			entityTemplateDto.setStatus(status);
			Map<String, String> fieldValidation = new HashMap<String, String>();
			fieldValidation
	        .put("communica tionGateway",
	                "{\"client\":[],\"server\":[],\"showOnGrid\":false,\"showOnTree\":false}");
			fieldValidation
	        .put("confTemplate",
	                "{\"client\":[],\"server\":[],\"showOnGrid\":false,\"showO nTree\":false}");
			fieldValidation
	        .put("hostId",
	                "{\"client\":[],\"server\":[\"mandatory\",\"uniqueAcrossApplication\"],\"showOnGrid\":true,\"showOnTree\":true}");
			fieldValidation
	        .put("make",
	                "{\"client\":[] ,\"server\":[\"mandatory\"],\"showOnGrid\":true,\"showOnTree\":false}");
			fieldValidation
	        .put("model",
	                "{\"client\":[],\"server\":[\"mandatory\"],\"showOnGrid\":true,\"showOnTree\":fal se}");
			fieldValidation
	        .put("protocol",
	                "{\"client\":[],\"server\":[],\"showOnGrid\":true,\"showOnTree\":false}");
			fieldValidation
	        .put("serialNumber",
	                "{\"client\":[],\"server\":[],\"showOnGrid\":true,\"show OnTree\":false}");
			fieldValidation
	        .put("unitId",
	                "{\"client\":[],\"server\":[\"mandatory\",\"uniqueAcrossApplication\"],\"showOnGrid\":false,\"showOnTree\":false}");
			
			fieldValidation
	        .put("unitId",
	                "{\"cl ient\":[],\"server\":[],\"showOnGrid\":true,\"showOnTree\":false}");
						
			
			entityTemplateDto.setFieldValidation(fieldValidation);
			
		}
		return this;
	}

	
	public EntityTemplateDTOBuilder anEntityTemplateDTO(String entityTemplateName) {
		if (entityTemplateDto == null) {
			entityTemplateDto = new EntityTemplateDTO();
			entityTemplateDto.setEntityTemplateName(entityTemplateName);
		
			Map<String, String> fieldValidation = new HashMap<String, String>();
			
			fieldValidation
	        .put("entityName",
	                "{\"client\":[],\"server\":[\"uniqueAcrossDomain\"],\"showOnGrid\":true,\"showOnTree\":true}");
			fieldValidation
	        .put("identifier",
	                "{\"client\":[],\"server\":[\"uniqueAcrossDomain\"],\"showOnGrid\":false,\"showOnTree\":false}");
		
			entityTemplateDto.setFieldValidation(fieldValidation);
			entityTemplateDto.setIdentifierField("identifier");
		}
		return this;
	}
	
	public EntityTemplateDTOBuilder anEntityTemplate(String entityTemplateName,String domainName) {
		if (entityTemplateDto == null) {
			entityTemplateDto = new EntityTemplateDTO();
			entityTemplateDto.setEntityTemplateName(entityTemplateName);
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(domainName);
			entityTemplateDto.setDomain(domain);
			
		}
		return this;
	}
	
	/*public EntityTemplateDTOBuilder anEntityTemplateDTO(String entityTemplateName,String globalEntityType) {
		if (entityTemplateDto == null) {
			entityTemplateDto = new EntityTemplateDTO();
			entityTemplateDto.setEntityTemplateName(entityTemplateName);
			entityTemplateDto.setGlobalEntityType(globalEntityType);
		
			Map<String, String> fieldValidation = new HashMap<String, String>();
			
			fieldValidation
	        .put("entityName",
	                "{\"client\":[],\"server\":[\"uniqueAcrossDomain\"],\"showOnGrid\":true,\"showOnTree\":true}");
			fieldValidation
	        .put("identifier",
	                "{\"client\":[],\"server\":[\"uniqueAcrossDomain\"],\"showOnGrid\":false,\"showOnTree\":false}");
		
			entityTemplateDto.setFieldValidation(fieldValidation);
			entityTemplateDto.setIdentifierField("entityName");
		}
		return this;
	}*/

	public EntityTemplateDTOBuilder anEntityTemplateDTO(String entityTemplateName,String globalEntityType) {
		if (entityTemplateDto == null) {
			entityTemplateDto = new EntityTemplateDTO();
			entityTemplateDto.setEntityTemplateName(entityTemplateName);
			entityTemplateDto.setGlobalEntityType(globalEntityType);
		
			}
		return this;
	}
	
	public EntityTemplateDTOBuilder withDomainDTO(DomainDTO domainDto) {
		if (entityTemplateDto != null) {
			entityTemplateDto.setDomain(domainDto);
		}
		return this;
	}
	public EntityTemplateDTO build() {
		return entityTemplateDto;
	}
}

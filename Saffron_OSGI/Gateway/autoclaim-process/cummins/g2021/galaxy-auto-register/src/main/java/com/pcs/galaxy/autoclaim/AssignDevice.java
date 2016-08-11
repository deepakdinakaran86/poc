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
package com.pcs.galaxy.autoclaim;

import static com.pcs.galaxy.constants.FieldValueConstants.TENANT;
import static com.pcs.galaxy.constants.FieldValueConstants.TENANT_ID;
import static com.pcs.galaxy.constants.FieldValueConstants.TENANT_TEMPLATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hazelcast.util.StringUtil;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.galaxy.dto.DeviceManageDTO;
import com.pcs.galaxy.dto.DomainDTO;
import com.pcs.galaxy.dto.EntityDTO;
import com.pcs.galaxy.dto.EntityRangeDTO;
import com.pcs.galaxy.dto.EntityTemplateDTO;
import com.pcs.galaxy.dto.FieldMapDTO;
import com.pcs.galaxy.dto.HierarchyDTO;
import com.pcs.galaxy.dto.HierarchySearchDTO;
import com.pcs.galaxy.dto.IdentityDTO;
import com.pcs.galaxy.dto.PlatformEntityDTO;
import com.pcs.galaxy.dto.StatusMessageDTO;
import com.pcs.galaxy.token.TokenProvider;

/**
 * AssignDevice
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class AssignDevice {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AssignDevice.class);

	@Value("${autoclaim.login.clientid}")
	private String clientId;
	@Value("${autoclaim.login.clientsecret}")
	private String secret;
	@Value("${autoclaim.login.username}")
	private String userName;
	@Value("${autoclaim.login.password}")
	private String password;
	@Value("${autoclaim.domain}")
	private String supportDomain;
	@Value("${autoclaim.supertenant}")
	private String superTenantDomain;

	@Value("${autoclaim.alpine.hierarchy.attach.children}")
	private String attachChildrenURL;

	@Value("${autoclaim.alpine.hierarchy.parents}")
	private String findParentsURL;

	@Value("${autoclaim.alpine.hierarchy.children}")
	private String findChildren;

	@Value("${autoclaim.alpine.tenant.find}")
	private String findTenantURL;

	@Autowired
	@Qualifier("alpineClient")
	private BaseClient alpineClient;

	@Autowired
	private UnRecognizedDevices unRecDevice;

	public IdentityDTO assignDevice(DeviceManageDTO parentDetail,
	        IdentityDTO device) {

		String superTenant = parentDetail.getSuperTenant();
		String parent = parentDetail.getParentTenant();
		String owner = parentDetail.getOwnerTenant();

		LOGGER.info("Enter into assignDevice");
		IdentityDTO superTenantIdentity = getSuperTenant(superTenant);
		if (superTenantIdentity != null) {
			LOGGER.info("validate super Tenant success");
			if (StringUtil.isNullOrEmpty(parent)) {
				LOGGER.info("Parent Tenant is Null/Empty");
				IdentityDTO ownerTenant = validateOwnerTenant(superTenant,
				        owner);
				if (ownerTenant != null) {
					LOGGER.info("validate owner Tenant success");
					List<IdentityDTO> childrnOfDevice = getParentsOfOwner(
					        superTenant, superTenant, owner);
					deviceToTenants(childrnOfDevice, device);
					LOGGER.info("Exit from assignDevice");
					return ownerTenant;
				} else {
					List<IdentityDTO> childrnOfDevice = new ArrayList<IdentityDTO>();
					childrnOfDevice.add(superTenantIdentity);
					deviceToTenants(childrnOfDevice, device);
					if (parentDetail.getCreateAsset().equals("CREATEASSET")) {
						LOGGER.info("Parent Tenant is Null/Empty, Owner Tenant is Invalid But CREATEASSET Enabled");
						return superTenantIdentity;
					} else {
						parentDetail
						        .setDescription("Parent Tenant is Null/Empty, Owner Tenant is Invalid");
						parentDetail.setDomainName(superTenant + supportDomain);
					}
				}
			} else {
				IdentityDTO parentTenant = getParentTenant(superTenant, parent);
				if (parentTenant != null) {
					LOGGER.info("validate perent Tenant success");

					IdentityDTO ownerTenant = validateOwnerTenant(parent, owner);
					if (ownerTenant != null) {
						LOGGER.info("validate owner Tenant success");
						List<IdentityDTO> childrnOfDevice = getParentsOfOwner(
						        superTenant, parent, owner);
						deviceToTenants(childrnOfDevice, device);
						LOGGER.info("Exit from assignDevice");
						return ownerTenant;
					} else {
						List<IdentityDTO> childrnOfDevice = getParents(
						        superTenantIdentity, parentTenant);
						deviceToTenants(childrnOfDevice, device);
						if (parentDetail.getCreateAsset().equals("CREATEASSET")) {
							LOGGER.info("Owner Tenant is Invalid But CREATEASSET Enabled");
							return parentTenant;
						} else {
							parentDetail
							        .setDescription("Owner Tenant is Invalid");
							parentDetail.setDomainName(parent + supportDomain);
						}
					}
				} else {
					List<IdentityDTO> childrnOfDevice = new ArrayList<IdentityDTO>();
					childrnOfDevice.add(superTenantIdentity);
					deviceToTenants(childrnOfDevice, device);
					if (parentDetail.getCreateAsset().equals("CREATEASSET")) {
						LOGGER.info("Parnt Tenant is Invalid But CREATEASSET Enabled");
						return superTenantIdentity;
					} else {
						parentDetail.setDescription("Parnt Tenant is Invalid");
						parentDetail.setDomainName(superTenant + supportDomain);
					}
				}
			}
		} else {
			parentDetail.setDescription("Super Tenant is Invalid");
			parentDetail.setDomainName(superTenantDomain);
		}
		EntityDTO entity = unRecDevice.prepareUnRecEntity(parentDetail, device);
		unRecDevice.createUnRecDevice(entity);
		return null;
	}

	private StatusMessageDTO deviceToTenants(List<IdentityDTO> tenants,
	        IdentityDTO device) {

		List<EntityDTO> tenantEntities = identitysToEntitys(tenants);
		EntityDTO deviceEntity = identityToEntity(device);
		HierarchyDTO hierarchy = new HierarchyDTO();
		hierarchy.setActor(deviceEntity);
		hierarchy.setSubjects(tenantEntities);

		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		StatusMessageDTO status = null;
		try {
			status = alpineClient.post(attachChildrenURL, header, hierarchy,
			        StatusMessageDTO.class);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return status;
	}

	private List<IdentityDTO> getParentsOfOwner(String superTenant,
	        String parent, String owner) {
		LOGGER.info("Super Tenant : {}, Parent Tenant : {}, Owner Tenant : {}",
		        superTenant, parent, owner);
		IdentityDTO startEntity = identifierDto(superTenantDomain, superTenant);
		IdentityDTO endEntity = identifierDto(parent + supportDomain, owner);
		return getParents(startEntity, endEntity);
	}

	@SuppressWarnings("unchecked")
	private List<IdentityDTO> getParents(IdentityDTO start, IdentityDTO end) {
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		EntityRangeDTO range = new EntityRangeDTO();
		range.setStartEntity(start);
		range.setEndEntity(end);
		List<IdentityDTO> parents = null;
		try {
			parents = alpineClient.post(findParentsURL, header, range,
			        List.class, IdentityDTO.class);
		} catch (Exception e) {
			LOGGER.info("getParents : " + e.getMessage());
		}

		return parents;
	}

	private IdentityDTO getSuperTenant(String superTenant) {
		LOGGER.info("Enter into getSuperTenant");
		IdentityDTO identityDto = getIdentityDto(superTenantDomain, superTenant);
		EntityDTO tenant = getTenant(identityDto);

		return entityToIdentifier(tenant);
	}

	private IdentityDTO entityToIdentifier(EntityDTO entity) {
		IdentityDTO identity = null;
		if (entity != null) {
			identity = new IdentityDTO();
			identity.setDomain(entity.getDomain());
			identity.setIdentifier(entity.getIdentifier());
			identity.setPlatformEntity(entity.getPlatformEntity());
			identity.setEntityTemplate(entity.getEntityTemplate());
		}
		return identity;
	}

	private IdentityDTO getParentTenant(String superTenant, String parent) {
		LOGGER.info("Enter into validateParentTenant");
		HierarchySearchDTO hierarchySearch = new HierarchySearchDTO();
		IdentityDTO identityDto = getIdentityDto(superTenantDomain, superTenant);
		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(TENANT);
		identityDto.setPlatformEntity(platformEntityDTO);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(TENANT_TEMPLATE);
		identityDto.setEntityTemplate(entityTemplateDTO);

		hierarchySearch.setParentIdentity(identityDto);
		hierarchySearch.setSearchTemplateName(TENANT_TEMPLATE);
		hierarchySearch.setSearchEntityType(TENANT);

		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		List<EntityDTO> entity = null;
		try {
			entity = alpineClient.post(findChildren, header, hierarchySearch,
			        List.class, EntityDTO.class);

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}

		for (EntityDTO entityDTO : entity) {
			FieldMapDTO identifier = entityDTO.getIdentifier();
			if (parent.equals(identifier.getValue())) {
				return entityToIdentifier(entityDTO);
			}
		}
		return null;
	}

	private IdentityDTO validateOwnerTenant(String parent, String owner) {
		LOGGER.info("Enter into validateCurrentTenant");
		IdentityDTO identityDto = getIdentityDto(parent + supportDomain, owner);
		EntityDTO tenant = getTenant(identityDto);
		IdentityDTO Identifier = null;
		if (tenant != null) {
			Identifier = entityToIdentifier(tenant);
		}
		return Identifier;
	}

	private EntityDTO getTenant(IdentityDTO identityDTO) {

		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		EntityDTO entity = null;
		try {
			entity = alpineClient.post(findTenantURL, header, identityDTO,
			        EntityDTO.class);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

		return entity;
	}

	private IdentityDTO getIdentityDto(String domainName, String tenant) {

		IdentityDTO identityDTO = new IdentityDTO();

		FieldMapDTO field = new FieldMapDTO();
		field.setKey(TENANT_ID);
		field.setValue(tenant);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);

		identityDTO.setDomain(domain);
		identityDTO.setIdentifier(field);

		return identityDTO;
	}

	private IdentityDTO identifierDto(String domainName, String tenant) {

		IdentityDTO identityDTO = getIdentityDto(domainName, tenant);
		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(TENANT);
		identityDTO.setPlatformEntity(platformEntityDTO);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(TENANT_TEMPLATE);
		identityDTO.setEntityTemplate(entityTemplateDTO);

		return identityDTO;
	}

	private EntityDTO identityToEntity(IdentityDTO identityDTO) {
		EntityDTO entity = new EntityDTO();
		entity.setDomain(identityDTO.getDomain());
		entity.setIdentifier(identityDTO.getIdentifier());
		entity.setPlatformEntity(identityDTO.getPlatformEntity());
		entity.setEntityTemplate(identityDTO.getEntityTemplate());
		return entity;
	}

	private List<EntityDTO> identitysToEntitys(List<IdentityDTO> identitys) {

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (IdentityDTO identity : identitys) {
			EntityDTO entity = identityToEntity(identity);
			entities.add(entity);
		}
		return entities;
	}

}

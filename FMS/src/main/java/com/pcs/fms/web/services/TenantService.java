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

import static com.pcs.fms.web.constants.FMSWebConstants.CONTACT_EMAIL;
import static com.pcs.fms.web.constants.FMSWebConstants.CONTACT_NUMBER;
import static com.pcs.fms.web.constants.FMSWebConstants.DOMAIN;
import static com.pcs.fms.web.constants.FMSWebConstants.EMAIL_ID;
import static com.pcs.fms.web.constants.FMSWebConstants.FIRST_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.LAST_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_ID;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_TEMPLATE;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VALID_FIELD;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.AttachTags;
import com.pcs.fms.web.dto.CoordinatesDTO;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.GeotagDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.PlatformEntityDTO;
import com.pcs.fms.web.dto.Status;
import com.pcs.fms.web.dto.StatusDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.Tag;
import com.pcs.fms.web.dto.TenantAdminDTO;
import com.pcs.fms.web.dto.TenantAdminEmailDTO;
import com.pcs.fms.web.dto.TenantDTO;
import com.pcs.fms.web.model.Tenants;
import com.pcs.fms.web.model.User;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class TenantService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TenantService.class);

	@Value("${fms.services.createTenant}")
	private String createTenantEndpointUri;

	@Value("${fms.services.updateTenant}")
	private String updateTenantEndpointUri;

	@Value("${fms.services.findTenant}")
	private String findTenantEndpointUri;

	@Value("${fms.services.listTenant}")
	private String listTenantEndpointUri;

	@Value("${fms.services.findTenantFeatures}")
	private String findTenantFeaturesEndpointUri;

	@Value("${fms.services.sendTenantAdminEmail}")
	private String sendTenantAdminEmailUrlEndpointUri;

	@Value("${fms.services.findTenantAdminLink}")
	private String findTenantAdminLinkEndpointUri;

	@Value("${fms.services.createTenantAdmin}")
	private String createTenantAdminEndpointUri;

	@Autowired
	private TagService tagService;

	@Autowired
	private GeotagService geotagService;

	/**
	 * Method to list all tenants based on tenantName
	 * 
	 * @param
	 * @return
	 */
	public List<Tenants> getTenants(String domain) {
		domain = FMSAccessManager.getCurrentDomain();
		String getTenantsServiceURI = getServiceURI(createTenantEndpointUri);

		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		FMSResponse<List<EntityDTO>> tenants = null;

		if (isBlank(domain)) {
			tenants = getPlatformClient().getResource(getTenantsServiceURI,
			        entityListType);
		} else {
			tenants = getPlatformClient().getResource(
			        getTenantsServiceURI + "?domain_name=" + domain,
			        entityListType);

		}
		List<Tenants> tenantsPayload = new ArrayList<Tenants>();
		for (EntityDTO tenant : tenants.getEntity()) {
			Tenants tenantView = constructTenantDTO(tenant,
			        tenant.getDataprovider());
			tenantsPayload.add(tenantView);
		}
		return tenantsPayload;
	}

	/**
	 * Method to view tenant details
	 * 
	 * @param
	 * @return
	 */
	public Tenants getTenantDetails(String tenantId, String domain,
	        String myDomain) {
		String findTenantDetailsServiceURI = getServiceURI(findTenantEndpointUri);

		FMSResponse<EntityDTO> tenant = null;
		if (isBlank(domain)) {
			tenant = getPlatformClient().getResource(
			        findTenantDetailsServiceURI + "/" + tenantId,
			        EntityDTO.class);
		} else {
			tenant = getPlatformClient().getResource(
			        findTenantDetailsServiceURI + "/" + tenantId
			                + "?domain_name=" + domain, EntityDTO.class);
		}
		Tenants tenants = new Tenants();
		if (tenant.getEntity() != null) {
			tenants = constructTenantDTO(tenant.getEntity(), tenant.getEntity()
			        .getFieldValues());
			// Find the geotag
			IdentityDTO tenantIdentity = new IdentityDTO(tenant.getEntity());
			FMSResponse<GeotagDTO> tenantGeotag = geotagService
			        .findGeotag(tenantIdentity);

			if (tenantGeotag.getErrorMessage() == null) {
				tenants.setLatitude(tenantGeotag.getEntity().getGeotag()
				        .getLatitude());
				tenants.setLongitude(tenantGeotag.getEntity().getGeotag()
				        .getLongitude());
				tenants.setGeotagPresent(true);
			}
		}
		// Get features
		FMSResponse<List<String>> features = getDomainFeatures(myDomain);
		tenants.setTenantFeatures(features.getEntity());

		return tenants;
	}

	private FMSResponse<List<String>> getDomainFeatures(String domain) {
		String findTenantFeaturesServiceURI = getServiceURI(findTenantFeaturesEndpointUri);

		Type featureListType = new TypeToken<List<String>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		FMSResponse<List<String>> features = null;
		String serviceURI = findTenantFeaturesServiceURI.replace("{domain}",
		        domain);

		features = getPlatformClient().getResource(serviceURI, featureListType);

		return features;
	}

	public Tenants getFeatures(String domain) {
		Tenants tenants = new Tenants();
		// Default features
		List<String> defaultFeatures = new ArrayList<String>();
		defaultFeatures.add("Geo-service Management");
		defaultFeatures.add("Tag Management");
		defaultFeatures.add("Tenant Management");
		defaultFeatures.add("User Management");

		FMSResponse<List<String>> alldomainFeatures = getDomainFeatures(domain);
		alldomainFeatures.getEntity().removeAll(defaultFeatures);
		tenants.setAvailableFeatures(alldomainFeatures.getEntity());
		tenants.setFeatures(defaultFeatures);
		return tenants;
	}

	private Tenants constructTenantDTO(EntityDTO res, List<FieldMapDTO> fields) {
		Tenants tenant = new Tenants();
		if (res != null) {
			EntityDTO entity = res;
			if (entity != null) {
				List<FieldMapDTO> fieldValues = fields;
				for (FieldMapDTO fieldMapDTO : fieldValues) {
					if (fieldMapDTO.getKey().equals(TENANT_NAME)) {
						tenant.setTenantName(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(FIRST_NAME)) {
						tenant.setFirstName(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(LAST_NAME)) {
						tenant.setLastName(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(CONTACT_EMAIL)) {
						tenant.setContactEmail(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(DOMAIN)) {
						tenant.setDomain(fieldMapDTO.getValue());
						tenant.setDomainNameOnEdit(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(TENANT_ID)) {
						tenant.setTenantId(fieldMapDTO.getValue());
					}
				}
				StatusDTO status = entity.getEntityStatus();
				if (status != null) {
					if (isNotBlank(status.getStatusName())) {
						tenant.setStatus(status.getStatusName());
					}
				}
				FieldMapDTO identity = entity.getIdentifier();
				if (identity != null) {
					if (isNotBlank(identity.getValue()))
						tenant.setIdentifier(identity.getValue());
				}
				tenant.setParentDomain(entity.getDomain().getDomainName());
			}
		}
		return tenant;
	}

	// private FMSResponse<GeotagDTO> findTenantGeotag(IdentityDTO
	// tenantIdentity) {
	// String findTenantGeotagServiceURI =
	// getServiceURI(findTenantGeotagEndpointUri);
	//
	// return getPlatformClient().postResource(findTenantGeotagServiceURI,
	// tenantIdentity, GeotagDTO.class);
	//
	// }

	public FMSResponse<StatusMessageDTO> sendTenantAdminEmail(
	        TenantAdminEmailDTO email) {
		String sendResetPWDEmailServerUrl = getServiceURI(sendTenantAdminEmailUrlEndpointUri);
		return getPlatformClient().postResource(sendResetPWDEmailServerUrl,
		        email, StatusMessageDTO.class);
	}

	/**
	 * Method to update tenant
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> updateTenant(Tenants tenant) {
		// Invoke update tenant
		String createTenantServiceURI = getServiceURI(createTenantEndpointUri);
		String tenantId = tenant.getIdentifier();

		List<Tag> tags = new ArrayList<Tag>();
		if (isNotBlank(tenant.getSelectedTags())) {
			Type tag = new TypeToken<List<Tag>>() {
				private static final long serialVersionUID = 5936335989523954928L;
			}.getType();

			Gson gson = new Gson();
			tags = gson.fromJson(tenant.getSelectedTags(), tag);
		}

		if (tenant.getAction().equalsIgnoreCase("Update")) {
			tenant.setTenantId(tenantId);
		}
		EntityDTO tenantEntity = constructEntityDTO(tenant);

		FMSResponse<StatusMessageDTO> upodateStatus = new FMSResponse<StatusMessageDTO>();
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		upodateStatus.setEntity(statusMessageDTO);
		FMSResponse<IdentityDTO> tenantIdentifiers = new FMSResponse<IdentityDTO>();

		if (tenant.getAction().equalsIgnoreCase("Update")) {
			// Update tenant in galaxy
			upodateStatus = getPlatformClient().putResource(
			        createTenantServiceURI + "/" + tenantId + "?domain_name="
			                + tenant.getDomainNameOnEdit(), tenantEntity,
			        StatusMessageDTO.class);
		} else {
			// Set features here
			TenantDTO tenantDTO = new TenantDTO();
			tenantDTO.setDomain(tenantEntity.getDomain());
			tenantDTO.setFeatures(tenant.getTenantFeatures());
			tenantDTO.setFieldValues(tenantEntity.getFieldValues());
			tenantDTO.setEntityStatus(tenantEntity.getEntityStatus());

			// Create tenant in galaxy
			tenantIdentifiers = getPlatformClient().postResource(
			        createTenantServiceURI, tenantDTO, IdentityDTO.class);
			if (tenantIdentifiers.getEntity() != null) {
				statusMessageDTO.setStatus(Status.SUCCESS);
				upodateStatus.setEntity(statusMessageDTO);
				tenant.setParentDomain(tenantIdentifiers.getEntity()
				        .getDomain().getDomainName());
				// Set tenantIdentifiers for geotag
				tenant.setTenantId(tenantIdentifiers.getEntity()
				        .getIdentifier().getValue());
			} else {
				upodateStatus.setErrorMessage(tenantIdentifiers
				        .getErrorMessage());
				return upodateStatus;
			}

		}

		// Check if geotag was existing
		if (tenant.getGeotagPresent() != null && tenant.getGeotagPresent()) {
			GeotagDTO geotagDTO = getGeotagPayload(tenant);
			if (isNotBlank(tenant.getLatitude())
			        && isNotBlank(tenant.getLongitude())) {
				// Update geotag
				// Invoke update geotag API
				geotagService.updateGeotag(geotagDTO);
			}
		} else if (isNotBlank(tenant.getLatitude())
		        && isNotBlank(tenant.getLongitude())) {
			// Create geotag
			GeotagDTO geotagDTO = getGeotagPayload(tenant);
			geotagService.createGeotag(geotagDTO);
		}
		if (!CollectionUtils.isEmpty(tags)) {
			AttachTags attachTags = new AttachTags();
			attachTags.setTags(tags);

			IdentityDTO entityDTO = new IdentityDTO();
			FieldMapDTO identifier = new FieldMapDTO();
			identifier.setKey(TENANT_ID);
			identifier.setValue(tenant.getTenantId());
			entityDTO.setIdentifier(identifier);

			EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
			entityTemplateDTO.setEntityTemplateName(TENANT_TEMPLATE);
			entityDTO.setEntityTemplate(entityTemplateDTO);

			PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
			platformEntityDTO.setPlatformEntityType(TENANT);
			entityDTO.setPlatformEntity(platformEntityDTO);

			// TODO change domain here
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(tenant.getParentDomain());
			entityDTO.setDomain(domain);

			attachTags.setEntity(entityDTO);
			tagService.attachTagsToEntity(attachTags);
		}

		return upodateStatus;

	}

	public FMSResponse<StatusMessageDTO> createTenantAdmin(User user,
	        String setPswdLink) {
		TenantAdminDTO tenantAdminDTO = constructTenantAdminEntityDTO(user,
		        setPswdLink);

		String createTenantAdminServiceURI = getServiceURI(createTenantAdminEndpointUri);

		FMSResponse<StatusMessageDTO> tenantAdminResponse = getPlatformClient()
		        .postResource(createTenantAdminServiceURI, tenantAdminDTO,
		                StatusMessageDTO.class);
		return tenantAdminResponse;

	}

	public String getAdminEmail(IdentityDTO identityDTO) {
		String findTenantAdminServiceURI = getServiceURI(findTenantAdminLinkEndpointUri);
		FMSResponse<EntityDTO> linkEntity = getPlatformClient().postResource(
		        findTenantAdminServiceURI, identityDTO, EntityDTO.class);
		String emailId = null;
		if (linkEntity.getEntity() != null) {
			FieldMapDTO emailField = new FieldMapDTO();
			emailField.setKey(EMAIL_ID);

			// Check if link is valid
			FieldMapDTO validField = new FieldMapDTO();
			validField.setKey(VALID_FIELD);
			String isLinkValid = fetchField(
			        linkEntity.getEntity().getFieldValues(), validField)
			        .getValue();
			// If link is valid fetch email
			if (isLinkValid.equalsIgnoreCase("true")) {
				emailId = fetchField(linkEntity.getEntity().getFieldValues(),
				        emailField).getValue();
			}
		}
		return emailId;
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	/**
	 * @param tenant
	 * @return
	 */
	private GeotagDTO getGeotagPayload(Tenants tenant) {
		CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
		coordinatesDTO.setLatitude(tenant.getLatitude());
		coordinatesDTO.setLongitude(tenant.getLongitude());

		GeotagDTO geotagDTO = new GeotagDTO();
		IdentityDTO entityDTO = new IdentityDTO();
		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(TENANT_ID);
		identifier.setValue(tenant.getTenantId());
		entityDTO.setIdentifier(identifier);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(TENANT_TEMPLATE);
		entityDTO.setEntityTemplate(entityTemplateDTO);

		geotagDTO.setEntity(entityDTO);
		geotagDTO.setGeotag(coordinatesDTO);
		return geotagDTO;
	}

	private EntityDTO constructEntityDTO(Tenants tenant) {
		EntityDTO entityDTO = new EntityDTO();
		List<FieldMapDTO> tenantFields = createFieldMapsFromDevice(tenant);
		entityDTO.setFieldValues(tenantFields);

		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(tenant.getStatus());
		entityDTO.setEntityStatus(statusDTO);

		// Set the domain
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(FMSAccessManager.getCurrentDomain());
		entityDTO.setDomain(domain);
		return entityDTO;
	}

	private List<FieldMapDTO> createFieldMapsFromDevice(Tenants tenant) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(TENANT_NAME, tenant.getTenantName()));
		fieldMaps.add(createFieldMap(CONTACT_EMAIL, tenant.getContactEmail()));
		fieldMaps.add(createFieldMap(FIRST_NAME, tenant.getFirstName()));
		fieldMaps.add(createFieldMap(LAST_NAME, tenant.getLastName()));
		fieldMaps.add(createFieldMap(TENANT_ID, tenant.getTenantId()));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	private TenantAdminDTO constructTenantAdminEntityDTO(User tenant,
	        String setPswdLink) {
		TenantAdminDTO entityDTO = new TenantAdminDTO();
		List<FieldMapDTO> tenantFields = createFieldMapsFromUser(tenant);
		entityDTO.setFieldValues(tenantFields);

		// Set status
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName("ACTIVE");
		entityDTO.setEntityStatus(statusDTO);

		// Set tenantAdminLinkIdentifier
		entityDTO.setTenantAdminLinkIdentifier(tenant.getIdentifier());

		// Set password url
		entityDTO.setSetPasswordUrl(setPswdLink);

		// Set domain
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(tenant.getDomain());
		entityDTO.setDomain(domain);

		return entityDTO;
	}

	private List<FieldMapDTO> createFieldMapsFromUser(User user) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(CONTACT_NUMBER, user.getContactNumber()));
		fieldMaps.add(createFieldMap(EMAIL_ID, user.getTenantAdminEmail()));
		fieldMaps.add(createFieldMap(FIRST_NAME, user.getFirstName()));
		fieldMaps.add(createFieldMap(LAST_NAME, user.getLastName()));
		fieldMaps.add(createFieldMap(USER_NAME, user.getUserName()));
		return fieldMaps;
	}

}

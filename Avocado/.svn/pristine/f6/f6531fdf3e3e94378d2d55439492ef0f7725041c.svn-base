/**
 * \ * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
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
package com.pcs.avocado.serviceimpl;

import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getContext;
import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.avocado.enums.TMDataFields.CREATE_ADMIN_USER;
import static com.pcs.avocado.enums.TMDataFields.CREATE_ADMIN_USER_EMAIL_TEMPLATE;
import static com.pcs.avocado.enums.TMDataFields.CREATE_TENANT_ADMIN_URL;
import static com.pcs.avocado.enums.TMDataFields.DOMAIN;
import static com.pcs.avocado.enums.TMDataFields.DOMAIN_NAME;
import static com.pcs.avocado.enums.TMDataFields.DOT;
import static com.pcs.avocado.enums.TMDataFields.EMAIL;
import static com.pcs.avocado.enums.TMDataFields.EMAIL_ID;
import static com.pcs.avocado.enums.TMDataFields.ENTITY_NAME;
import static com.pcs.avocado.enums.TMDataFields.GALAXY;
import static com.pcs.avocado.enums.TMDataFields.IDENTIFIER;
import static com.pcs.avocado.enums.TMDataFields.MY_DOMAIN;
import static com.pcs.avocado.enums.TMDataFields.RESET_PASSWORD;
import static com.pcs.avocado.enums.TMDataFields.ROLE_NAME;
import static com.pcs.avocado.enums.TMDataFields.SET_PASSWORD_EMAIL;
import static com.pcs.avocado.enums.TMDataFields.SET_PASSWORD_EMAIL_TEMPLATE;
import static com.pcs.avocado.enums.TMDataFields.SET_PASSWORD_URL;
import static com.pcs.avocado.enums.TMDataFields.STRING_FORMAT;
import static com.pcs.avocado.enums.TMDataFields.TENANT_ADMIN_LINK_ID;
import static com.pcs.avocado.enums.TMDataFields.TENANT_ADMIN_ROLE;
import static com.pcs.avocado.enums.TMDataFields.TENANT_ADMIN_USER_TEMPLATE;
import static com.pcs.avocado.enums.TMDataFields.TENANT_DOMAIN;
import static com.pcs.avocado.enums.TMDataFields.TENANT_ID;
import static com.pcs.avocado.enums.TMDataFields.TENANT_NAME;
import static com.pcs.avocado.enums.TMDataFields.TIME_STAMP;
import static com.pcs.avocado.enums.TMDataFields.USER_NAME;
import static com.pcs.avocado.enums.TMDataFields.VALID;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.email.dto.EmailDTO;
import com.pcs.avocado.commons.validation.ValidationUtils;
import com.pcs.avocado.dto.TenantAdminDTO;
import com.pcs.avocado.dto.TenantAdminEmailDTO;
import com.pcs.avocado.dto.TenantAdminEmailESBDTO;
import com.pcs.avocado.dto.TenantDTO;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.service.TenantService;

/**
 * 
 * @description This class is responsible for the TenantServiceImpl
 * 
 * @author Daniela (PCSEG191)
 * @date 11 Jan 2016
 * @since galaxy-1.0.0
 */
@Service
public class TenantServiceImpl implements TenantService {

	@Qualifier("avocadoEsbClient")
	@Autowired
	private BaseClient avocadoClient;

	@Qualifier("platformEsbClient")
	@Autowired
	private BaseClient platformClient;

	@Value("${avocado.esb.tenant.create}")
	private String createTenantEndpointUri;

	@Value("${platform.esb.tenant.update}")
	private String updateTenantEndpointUri;

	@Value("${avocado.esb.tenant.createAdmin}")
	private String createTenantAdminEndpointUri;

	@Value("${avocado.esb.tenant.sendEmail}")
	private String sendTenantAdminEmailEndpointUri;

	@Value("${platform.esb.tenant.find}")
	private String getTenantEndpointUri;

	@Value("${platform.esb.tenant.list}")
	private String getTenantsEndpointUri;

	// private final Map<String, String> defaultBasicAuthHeader = AuthUtil
	// .getDefaultBasicAuthHeaderMap();
	// TODO remove this
	// private Map<String, String> defaultBasicAuthHeader = ImmutableMap
	// .<String, String> builder()
	// .put("x-jwt-assertion",
	// "ll.eyJpc3MiOiJ3c28yLm9yZy9wcm9kdWN0cy9hbSIsImV4cCI6MTQ0MzUwNTQ4OTQzOSwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9zdWJzY3JpYmVyIjoiYXZvY2FkbyIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb25pZCI6IjI3IiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbm5hbWUiOiJhdm9jYWRvIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2FwaWNvbnRleHQiOiIvYWxwaW5ldXNlciIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdmVyc2lvbiI6IjEuMC4wIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy90aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9lbmR1c2VyIjoicGNzYWRtaW5AcGNzLmNvbSIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW5kdXNlclRlbmFudElkIjoiMSIsInN1YnNjcmliZXJEb21haW4iOiJhbHBpbmUuY29tIiwiZW5kVXNlckRvbWFpbiI6InBjcy5hbHBpbmUuY29tIiwic3Vic2NyaWJlck5hbWUiOiJhdm9jYWRvIiwiZW5kVXNlck5hbWUiOiJwY3NhZG1pbiIsIkNvbnN1bWVyS2V5IjoiNkt5djExRkV6a09SM0Y1ejQwcUEzelVCUWJRYSIsInN1YnNjcmliZXJBcHAiOiJhdm9jYWRvIn0=.ll")
	// .build();

	@Override
	public IdentityDTO createTenant(TenantDTO tenant) {

		TenantDTO tenantPayload = constructCreateTenantESBPayload(tenant);
		IdentityDTO messageDTO = avocadoClient.post(
				createTenantEndpointUri, getJwtToken(), tenantPayload,
				IdentityDTO.class);
		return messageDTO;
	}

	@Override
	public StatusMessageDTO createTenantAdmin(TenantAdminDTO tenantAdmin) {
		ValidationUtils.validateMandatoryFields(tenantAdmin,
				TENANT_ADMIN_LINK_ID, DOMAIN, SET_PASSWORD_URL);
		ValidationUtils.validateMandatoryFields(tenantAdmin.getDomain(),
				DOMAIN_NAME);

		// Set tenant admin role
		List<FieldMapDTO> fields = removeField(tenantAdmin.getFieldValues(),
				ROLE_NAME.getFieldName());
		tenantAdmin.setFieldValues(fields);

		FieldMapDTO roleNameMap = new FieldMapDTO();
		roleNameMap.setKey(ROLE_NAME.getFieldName());

		Gson gson = new Gson();
		List<String> roles = new ArrayList<String>();
		roles.add(TENANT_ADMIN_ROLE.getFieldName());
		roleNameMap.setValue(gson.toJson(roles));
		tenantAdmin.getFieldValues().add(roleNameMap);
		TenantAdminDTO tenantPayload = constructCreateTenantAdminPayload(tenantAdmin);
		StatusMessageDTO messageDTO = avocadoClient.post(
				createTenantAdminEndpointUri, getJwtToken(), tenantPayload,
				StatusMessageDTO.class);
		return messageDTO;
	}

	/**
	 * @param tenantAdmin
	 */
	private List<FieldMapDTO> removeField(List<FieldMapDTO> fields,
			String fieldName) {
		for (Iterator<FieldMapDTO> iterator = fields.iterator(); iterator
				.hasNext();) {
			FieldMapDTO field = iterator.next();
			if (field.getKey().equalsIgnoreCase(fieldName)) {
				// Remove the current element from the iterator and the list.
				iterator.remove();
			}
		}
		return fields;
	}

	@Override
	public StatusMessageDTO sendCreateTenantAdminMail(
			TenantAdminEmailDTO tenantAdminEmail) {
		ValidationUtils.validateMandatoryFields(tenantAdminEmail,
				CREATE_TENANT_ADMIN_URL, TENANT_DOMAIN, EMAIL);
		TenantAdminEmailESBDTO tenantAdminEmailDTO = constructCreateTenantAdminEmailESBPayload(tenantAdminEmail);
		StatusMessageDTO messageDTO = avocadoClient.post(
				sendTenantAdminEmailEndpointUri, getJwtToken(),
				tenantAdminEmailDTO, StatusMessageDTO.class);
		return messageDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> findAllTenants(String domain) {
		IdentityDTO tenant = new IdentityDTO();
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domain);
		tenant.setDomain(domainDTO);

		List<EntityDTO> tenants = platformClient.post(getTenantsEndpointUri,
				getJwtToken(), tenant, List.class, EntityDTO.class);
		return tenants;
	}

	@Override
	public EntityDTO findTenant(String tenantId, String domain) {
		IdentityDTO tenantIdentifier = new IdentityDTO();

		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(TENANT_ID.getFieldName());
		identifier.setValue(tenantId);
		tenantIdentifier.setIdentifier(identifier);

		// set domain
		if (isNotBlank(domain)) {
			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setDomainName(domain);
			tenantIdentifier.setDomain(domainDTO);
		}
		EntityDTO tenant = platformClient.post(getTenantEndpointUri,
				getJwtToken(), tenantIdentifier, EntityDTO.class);
		return tenant;
	}

	@Override
	public StatusMessageDTO updateTenant(TenantDTO tenant, String tenantId,
			String domain) {

		// Add tenant name and domain to field values
		FieldMapDTO tenantMap = new FieldMapDTO();
		tenantMap.setKey(TENANT_ID.getFieldName());
		tenantMap.setValue(tenantId);

		List<FieldMapDTO> fields = removeField(tenant.getFieldValues(),
				TENANT_ID.getFieldName());
		tenant.setFieldValues(fields);

		if (isBlank(domain)) {
			// fetch from logged in user's domain
			domain = getContext().getSubscription().getEndUserDomain();
		}
		FieldMapDTO domainMap = new FieldMapDTO();
		domainMap.setKey(DOMAIN.getFieldName());

		List<FieldMapDTO> fieldsDomain = removeField(tenant.getFieldValues(),
				DOMAIN.getFieldName());
		tenant.setFieldValues(fieldsDomain);
		domainMap.setValue(domain);

		// add tenant map and domain map to field values
		tenant.getFieldValues().add(domainMap);
		tenant.getFieldValues().add(tenantMap);

		// Set identifier fields
		tenant.setIdentifier(tenantMap);
		StatusMessageDTO updateStatus = platformClient.put(
				updateTenantEndpointUri, getJwtToken(), tenant,
				StatusMessageDTO.class);
		return updateStatus;
	}

	// Construct create tenant payload
	private TenantDTO constructCreateTenantESBPayload(TenantDTO tenantDTO) {
		List<FieldMapDTO> fields = tenantDTO.getFieldValues();
		FieldMapDTO tenantNameMap = new FieldMapDTO();
		tenantNameMap.setKey(TENANT_NAME.getFieldName());
		tenantNameMap.setValue(fields.get(fields.indexOf(tenantNameMap))
				.getValue());

		FieldMapDTO tenantIdMap = new FieldMapDTO();
		tenantIdMap.setKey(TENANT_ID.getFieldName());
		tenantIdMap
				.setValue(fields.get(fields.indexOf(tenantIdMap)).getValue());
		
		// set tenants current domain and tenant name
		tenantDTO.setMyDomain(createDomainFromTenantId(tenantIdMap.getValue()));
		tenantDTO.setTenantName(tenantNameMap.getValue());
		tenantDTO.setRoleName(TENANT_ADMIN_ROLE.getFieldName());

		if (CollectionUtils.isEmpty(tenantDTO.getTemplates())) {
			tenantDTO.setTemplates(null);
		}
		return tenantDTO;
	}

	private String createDomainFromTenantId(String prefix) {

		String domainName = String.format(STRING_FORMAT.getFieldName(), prefix,
				DOT.getFieldName(), GALAXY);
		return domainName;
	}

	private EntityDTO constructResetPswdPayload(String domain, String userName) {
		EntityDTO resetPswdLink = new EntityDTO();

		FieldMapDTO entityNameMap = new FieldMapDTO();
		entityNameMap.setKey(ENTITY_NAME.getFieldName());
		// set username as entity name
		entityNameMap.setValue(userName);

		// set timestamp
		FieldMapDTO timeStamp = new FieldMapDTO();
		timeStamp.setKey(TIME_STAMP.getFieldName());
		Date date = new Date();
		Long longTime = date.getTime();
		timeStamp.setValue(longTime.toString());

		// set valid
		FieldMapDTO valid = new FieldMapDTO();
		valid.setKey(VALID.getFieldName());
		valid.setValue("true");

		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		fields.add(valid);
		fields.add(timeStamp);
		fields.add(entityNameMap);

		resetPswdLink.setFieldValues(fields);

		// set template name
		EntityTemplateDTO resetTemplate = new EntityTemplateDTO();
		resetTemplate.setEntityTemplateName(RESET_PASSWORD.getFieldName());
		resetPswdLink.setEntityTemplate(resetTemplate);

		// set domain
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domain);
		resetPswdLink.setDomain(domainDTO);

		return resetPswdLink;
	}

	private IdentityDTO constructTenantAdminTemplateIdentifierPayload(
			String domain, String identifier) {
		IdentityDTO identityDTO = new IdentityDTO();
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domain);
		identityDTO.setDomain(domainDTO);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(TENANT_ADMIN_USER_TEMPLATE
				.getFieldName());

		FieldMapDTO identifierMap = new FieldMapDTO();
		identifierMap.setKey(IDENTIFIER.getFieldName());
		identifierMap.setValue(identifier);

		identityDTO.setEntityTemplate(entityTemplateDTO);
		identityDTO.setIdentifier(identifierMap);
		return identityDTO;
	}

	private EmailDTO constructEmail(String content, String toAdd,
			String userName) {
		EmailDTO email = new EmailDTO();
		email.setContent(content);
		email.setTo(userName);
		email.setUserName(userName);
		email.setToAddresses(toAdd);
		email.setSubject(SET_PASSWORD_EMAIL.getFieldName());
		email.setEmailTemplate(SET_PASSWORD_EMAIL_TEMPLATE.getFieldName());
		return email;
	}

	private TenantAdminDTO constructCreateTenantAdminPayload(
			TenantAdminDTO tenantAdminDTO) {

		List<FieldMapDTO> fields = tenantAdminDTO.getFieldValues();

		FieldMapDTO userNameMap = new FieldMapDTO();
		userNameMap.setKey(USER_NAME.getFieldName());
		String userName = fields.get(fields.indexOf(userNameMap)).getValue();

		EntityDTO resetDto = constructResetPswdPayload(tenantAdminDTO
				.getDomain().getDomainName(), userName);
		tenantAdminDTO.setResetPswd(resetDto);

		// Get domain from logged in user's context
		String domain = tenantAdminDTO.getDomain().getDomainName();

		IdentityDTO tenantAdminIdentifier = constructTenantAdminTemplateIdentifierPayload(
				domain, tenantAdminDTO.getTenantAdminLinkIdentifier());
		tenantAdminDTO.setTenantAdminTemplateIdentifier(tenantAdminIdentifier);

		FieldMapDTO emailMap = new FieldMapDTO();
		emailMap.setKey(EMAIL_ID.getFieldName());
		String email = fields.get(fields.indexOf(emailMap)).getValue();

		EmailDTO emailDTO = constructEmail(tenantAdminDTO.getSetPasswordUrl(),
				email, userName);
		tenantAdminDTO.setEmail(emailDTO);
		return tenantAdminDTO;
	}

	private TenantAdminEmailESBDTO constructCreateTenantAdminEmailESBPayload(
			TenantAdminEmailDTO tenantAdminEmailDTO) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();

		FieldMapDTO entityName = new FieldMapDTO();
		entityName.setKey(ENTITY_NAME.getFieldName());
		entityName.setValue(RandomStringUtils.randomAlphanumeric(8));
		fieldMaps.add(entityName);

		FieldMapDTO timeStamp = new FieldMapDTO();
		timeStamp.setKey(TIME_STAMP.getFieldName());
		Date date = new Date();
		Long longTime = date.getTime();
		timeStamp.setValue(longTime.toString());
		fieldMaps.add(timeStamp);

		FieldMapDTO valid = new FieldMapDTO();
		valid.setKey(VALID.getFieldName());
		valid.setValue("true");
		fieldMaps.add(valid);

		FieldMapDTO domain = new FieldMapDTO();
		domain.setKey(MY_DOMAIN.getFieldName());
		domain.setValue(tenantAdminEmailDTO.getTenantDomain());
		fieldMaps.add(domain);

		FieldMapDTO emailMap = new FieldMapDTO();
		emailMap.setKey(EMAIL_ID.getFieldName());
		emailMap.setValue(tenantAdminEmailDTO.getEmail());
		fieldMaps.add(emailMap);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(TENANT_ADMIN_USER_TEMPLATE
				.getFieldName());

		// Get domain from logged in user's context
		String domainName = tenantAdminEmailDTO.getTenantDomain();
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);

		EmailDTO email = new EmailDTO();
		email.setContent(tenantAdminEmailDTO.getCreateTenantAdminUrl());
		email.setEmailTemplate(CREATE_ADMIN_USER_EMAIL_TEMPLATE.getFieldName());
		email.setSubject(CREATE_ADMIN_USER.getFieldName());
		email.setToAddresses(tenantAdminEmailDTO.getEmail());

		TenantAdminEmailESBDTO adminEmailESBDTO = new TenantAdminEmailESBDTO();
		adminEmailESBDTO.setFieldValues(fieldMaps);
		adminEmailESBDTO.setEntityTemplate(entityTemplate);
		adminEmailESBDTO.setDomain(domainDTO);
		adminEmailESBDTO.setEmail(email);

		return adminEmailESBDTO;
	}

}

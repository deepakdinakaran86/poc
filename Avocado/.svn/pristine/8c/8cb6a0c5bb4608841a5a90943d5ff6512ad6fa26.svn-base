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
package com.pcs.avocado.serviceimpl;

import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getContext;
import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.avocado.constants.NameConstants.CONTACT_NUMBER;
import static com.pcs.avocado.constants.NameConstants.EMAIL_ID;
import static com.pcs.avocado.constants.NameConstants.ENTITY_NAME;
import static com.pcs.avocado.constants.NameConstants.FIRST_NAME;
import static com.pcs.avocado.constants.NameConstants.IDENTIFIER;
import static com.pcs.avocado.constants.NameConstants.LAST_NAME;
import static com.pcs.avocado.constants.NameConstants.RESET_PASSWORD_TEMPLATE;
import static com.pcs.avocado.constants.NameConstants.ROLE_NAME;
import static com.pcs.avocado.constants.NameConstants.SET_PASSWORD_MAIL_SUBJECT;
import static com.pcs.avocado.constants.NameConstants.SET_PWD_EMAIL_TEMPLATE;
import static com.pcs.avocado.constants.NameConstants.TIME_STAMP;
import static com.pcs.avocado.constants.NameConstants.USER_NAME;
import static com.pcs.avocado.constants.NameConstants.USER_TEMPLATE;
import static com.pcs.avocado.constants.NameConstants.VALID;
import static com.pcs.avocado.enums.UMDataFields.EMAIL_LINK;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.LinkEmailESBDTO;
import com.pcs.avocado.commons.dto.ReturnFieldsDTO;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.email.dto.EmailDTO;
import com.pcs.avocado.commons.util.ObjectBuilderUtil;
import com.pcs.avocado.constants.NameConstants;
import com.pcs.avocado.dto.ESBUserDTO;
import com.pcs.avocado.dto.SMSStatsDTO;
import com.pcs.avocado.dto.UserDTO;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.enums.UMDataFields;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.service.UserService;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 18 January 2016
 * @since avocado-1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("platformESBClient")
	private BaseClient platformESBClient;

	@Autowired
	@Qualifier("avocadoESBClient")
	private BaseClient avocadoESBClient;

	@Value("${platform.esb.users}")
	private String platformUsersEP;
	
	@Value("${platform.esb.users.find}")
	private String findUserPlatformEP;

	@Value("${platform.esb.users.list}")
	private String listUsersPlatformEP;

	@Value("${platform.esb.users.delete}")
	private String deleteUsersPlatformEP;
	
	@Value("${platform.esb.users.forgotPassword}")
	private String forgotPasswordPlatformEP;
	
	@Value("${platform.esb.users.changePassword}")
	private String changePasswordPlatformEP;
	
	@Value("${avocado.esb.users}")
	private String createUserAvocadoEP;
	
	@Value("${avocado.esb.users.resetPassword}")
	private String resetPasswordAvocadoEP;
	
	@Value("${platform.esb.sms.statistics}")
	private String getSMSStatisticsPlatformEP;
	
	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Override
	public EntityDTO createUser(UserDTO userDTO) {

		EntityDTO userEntity = createUserEntity(userDTO);

		// Mandatory only for create
		validateMandatoryFields(userDTO, EMAIL_LINK);

		ESBUserDTO esbUserDTO = new ESBUserDTO();
		esbUserDTO.setUserEntity(userEntity);
		esbUserDTO.setContactNumber(userDTO.getContactNumber());
		esbUserDTO.setLinkEmailESBDTO(createLinkMarkerPayload(userDTO));

		EntityDTO returnEntity = avocadoESBClient.post(createUserAvocadoEP,
				getJwtToken(), esbUserDTO, EntityDTO.class);

		return returnEntity;
	}

	@Override
	public EntityDTO updateUser(String userName, String domain, UserDTO userDTO) {

		userDTO.setDomain(domain);
		userDTO.setUserName(userName);

		EntityDTO userEntity = createUserEntity(userDTO);

		FieldMapDTO identifier = new FieldMapDTO(NameConstants.USER_NAME,
				userName);
		userEntity.setIdentifier(identifier);

		EntityDTO entity = platformESBClient.put(platformUsersEP,
				getJwtToken(), userEntity, EntityDTO.class);
		return entity;
	}

	@Override
	public StatusMessageDTO deleteUser(String userName, String domain) {

		IdentityDTO identityDTO = getUserIdentity(userName, domain);

		StatusMessageDTO response = platformESBClient.post(
				deleteUsersPlatformEP, getJwtToken(), identityDTO,
				StatusMessageDTO.class);

		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> getUsers(String domainName) {
		// domain cannot be passed as it is HTTP GET
		// FIXME not passing the domain
		List<EntityDTO> entities = null;
		if(isBlank(domainName)) {
			entities = platformESBClient.get(listUsersPlatformEP,
					getJwtToken(), List.class, EntityDTO.class);
		} else {
		entities = platformESBClient.get(listUsersPlatformEP + "?domain_name=" + domainName,
				getJwtToken(), List.class, EntityDTO.class);
		}
		return entities;
	}

	@Override
	public StatusMessageDTO resetPassword(UserDTO userDTO) {

		IdentityDTO identityDTO = new IdentityDTO();
		FieldMapDTO resetPasswordIdentifier = new FieldMapDTO();
		resetPasswordIdentifier.setKey(IDENTIFIER);
		resetPasswordIdentifier.setValue(userDTO
				.getResetPasswordIdentifierLink());

		identityDTO.setIdentifier(resetPasswordIdentifier);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(userDTO.getDomain());
		identityDTO.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(RESET_PASSWORD_TEMPLATE);
		identityDTO.setEntityTemplate(entityTemplate);

		ESBUserDTO esbUserDTO = new ESBUserDTO();
		esbUserDTO.setIdentityDTO(identityDTO);
		esbUserDTO.setUserDTO(userDTO);

		StatusMessageDTO resetPasswordResponse = avocadoESBClient.post(
				resetPasswordAvocadoEP, getJwtToken(), esbUserDTO,
				StatusMessageDTO.class);

		return resetPasswordResponse;
	}
	
	@Override
	public StatusMessageDTO forgotPassword(UserDTO userDTO) {
		
		StatusMessageDTO forgotPasswordResponse = platformESBClient.post(
				forgotPasswordPlatformEP, getJwtToken(), userDTO,
				StatusMessageDTO.class);

		return forgotPasswordResponse;
	}
	
	@Override
	public StatusMessageDTO changePassword(UserDTO userDTO) {
		StatusMessageDTO changePasswordResponse = platformESBClient.post(
				changePasswordPlatformEP, getJwtToken(), userDTO,
				StatusMessageDTO.class);

		return changePasswordResponse;
	}

	@Override
	public EntityDTO getUser(String userName, String domain) {

		IdentityDTO identityDTO = getUserIdentity(userName, domain);

		EntityDTO entity = platformESBClient.post(findUserPlatformEP, 
				getJwtToken(), identityDTO, EntityDTO.class);

		return entity;
	}

	private IdentityDTO getUserIdentity(String userName, String domain) {
		IdentityDTO identityDTO = new IdentityDTO();

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domain);
		identityDTO.setDomain(domainDTO);

		identityDTO.setIdentifier(new FieldMapDTO(USER_NAME, userName));

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(USER_TEMPLATE);
		identityDTO.setEntityTemplate(entityTemplate);

		return identityDTO;
	}

	private EntityDTO createUserEntity(UserDTO userDTO) {

		validateMandatoryFields(userDTO, UMDataFields.USER_NAME,
				UMDataFields.EMAIL_ID);

		EntityDTO userEntity = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		if (isBlank(userDTO.getDomain())) {
			String domainName = getContext().getSubscription()
					.getEndUserDomain();
			domain.setDomainName(domainName);
		} else {
			domain.setDomainName(userDTO.getDomain());
		}
		userEntity.setDomain(domain);

		StatusDTO entityStatus = new StatusDTO();

		if (userDTO.getActive() != null) {
			if (userDTO.getActive()) {
				entityStatus.setStatusName(Status.ACTIVE.name());
			} else {
				entityStatus.setStatusName(Status.INACTIVE.name());
			}
		} else {
			entityStatus.setStatusName(Status.ACTIVE.name());
		}
		userEntity.setEntityStatus(entityStatus);

		List<FieldMapDTO> fiedValues = new ArrayList<FieldMapDTO>();
		userEntity.setFieldValues(fiedValues);

		fiedValues.add(new FieldMapDTO(USER_NAME, userDTO.getUserName()));
		fiedValues.add(new FieldMapDTO(EMAIL_ID, userDTO.getEmailId()));

		String firstName = userDTO.getFirstName();
		fiedValues.add(new FieldMapDTO(FIRST_NAME, firstName));

		String lastName = userDTO.getLastName();
		if (StringUtils.isNotBlank(lastName)) {
			fiedValues.add(new FieldMapDTO(LAST_NAME, lastName));
		}

		String contactNumber = userDTO.getContactNumber();
		fiedValues.add(new FieldMapDTO(CONTACT_NUMBER, contactNumber));

		String roleName = userDTO.getRoleName();
		if (StringUtils.isNotBlank(roleName)) {
			fiedValues.add(new FieldMapDTO(ROLE_NAME, roleName));
		}

		return userEntity;
	}

	private LinkEmailESBDTO createLinkMarkerPayload(UserDTO userDTO) {

		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();

		FieldMapDTO entityName = new FieldMapDTO();
		entityName.setKey(ENTITY_NAME);
		String userName = userDTO.getUserName();
		entityName.setValue(userName);
		fieldMaps.add(entityName);

		FieldMapDTO timeStamp = new FieldMapDTO();
		timeStamp.setKey(TIME_STAMP);
		Date date = new Date();
		Long longTime = date.getTime();
		timeStamp.setValue(longTime.toString());
		fieldMaps.add(timeStamp);

		FieldMapDTO valid = new FieldMapDTO();
		valid.setKey(VALID);
		valid.setValue("true");
		fieldMaps.add(valid);

		FieldMapDTO domain = new FieldMapDTO();
		domain.setKey(NameConstants.MY_DOMAIN);
		domain.setValue(userDTO.getDomain());
		fieldMaps.add(domain);

		FieldMapDTO emailMap = new FieldMapDTO();
		emailMap.setKey(NameConstants.EMAIL_ID);
		String emailId = userDTO.getEmailId();
		emailMap.setValue(emailId);
		fieldMaps.add(emailMap);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(RESET_PASSWORD_TEMPLATE);

		String domainName = userDTO.getDomain();
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);

		EmailDTO email = new EmailDTO();
		email.setContent(userDTO.getEmailLink());
		email.setEmailTemplate(SET_PWD_EMAIL_TEMPLATE);
		email.setSubject(SET_PASSWORD_MAIL_SUBJECT);
		email.setToAddresses(emailId);
		email.setTo(userName);
		email.setUserName(userName);

		email.setUserName(userName);

		LinkEmailESBDTO adminEmailESBDTO = new LinkEmailESBDTO();
		adminEmailESBDTO.setFieldValues(fieldMaps);
		adminEmailESBDTO.setEntityTemplate(entityTemplate);
		adminEmailESBDTO.setDomain(domainDTO);
		adminEmailESBDTO.setEmail(email);

		return adminEmailESBDTO;
	}

	@Override
	public List<SMSStatsDTO> getSMStatistics() {
		List<SMSStatsDTO> smsStatsDTOs = platformESBClient.get(
				getSMSStatisticsPlatformEP, getJwtToken(), 
				List.class, SMSStatsDTO.class);
		
		return smsStatsDTOs;
	}

}
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
package com.pcs.alpine.serviceimpl;

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.enums.UMDataFields.FAILED;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.EMAIL_ID;
import static com.pcs.alpine.services.enums.EMDataFields.EMAIL_LINK;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUE;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUES;
import static com.pcs.alpine.services.enums.EMDataFields.NEWPASSWORD;
import static com.pcs.alpine.services.enums.EMDataFields.PASSWORD;
import static com.pcs.alpine.services.enums.EMDataFields.USER;
import static com.pcs.alpine.services.enums.EMDataFields.USER_NAME;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.email.dto.EmailDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.constants.NameConstants;
import com.pcs.alpine.dto.ESBUserDTO;
import com.pcs.alpine.dto.LinkEmailESBDTO;
import com.pcs.alpine.dto.UserDTO;
import com.pcs.alpine.enums.AlpineISStatus;
import com.pcs.alpine.enums.AlpineUMStatus;
import com.pcs.alpine.enums.UMDataFields;
import com.pcs.alpine.isadapter.constants.ConnectionConstants;
import com.pcs.alpine.isadapter.constants.UserClaimConstants;
import com.pcs.alpine.isadapter.dto.User;
import com.pcs.alpine.isadapter.dto.UserClaim;
import com.pcs.alpine.rest.beans.StandardResponse;
import com.pcs.alpine.rest.client.AuthUtil;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.service.UserService;
import com.pcs.alpine.serviceImpl.validation.EntityValidation;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityStatusCountDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.dto.ValidationJsonStringDTO;
import com.pcs.alpine.services.dto.ValidationTestDTO;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.Status;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CoreEntityService coreEntity;

	@Autowired
	private StatusService statusService;

	@Autowired
	private PlatformEntityTemplateService platformEntityTemplateService;

	@Autowired
	private PlatformEntityService platformEntityService;

	@Autowired
	private EntityTemplateService entityTemplateService;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	@Qualifier("identityServerWrapperClient")
	private BaseClient client;

	private final Map<String, String> defaultBasicAuthHeader = AuthUtil
	        .getDefaultBasicAuthHeaderMap();

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private EntityValidation entityTemplateValidation;

	@Override
	public EntityDTO getUser(IdentityDTO userIdentifier) {
		validateIdentifier(userIdentifier);
		isParentDomain(userIdentifier);
		EntityDTO entity = coreEntity.getEntity(userIdentifier);
		return saveReturnDTO(entity);
	}

	@Override
	public List<EntityDTO> getUsers(String domainName, Boolean isParentDomain) {

		EntitySearchDTO coreEntitySearchDTO = new EntitySearchDTO();
		isParentDomain(isParentDomain);

		DomainDTO domain = new DomainDTO();
		if (isBlank(domainName)) {
			domain.setDomainName(subscriptionProfileService.getEndUserDomain());
		} else {
			domain.setDomainName(domainName);
		}
		coreEntitySearchDTO.setDomain(domain);

		PlatformEntityDTO platformEntityDTO = platformEntityService
		        .getGlobalEntityWithName(USER.getVariableName());
		List<EntityDTO> entities = null;
		try {
			coreEntitySearchDTO.setPlatformEntity(platformEntityDTO);
			entities = coreEntity
			        .getEntityDetailsWithDataprovider(coreEntitySearchDTO);
		} catch (GalaxyException e) {
			if (GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode().equals(
			        e.getCode())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
				        CommonConstants.USER);
			} else {
				throw e;
			}
		}
		return createGetUsersByDomainDTO(entities);
	}

	private void isParentDomain(Boolean isParentDomain) {
		if (isParentDomain != null && isParentDomain) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public EntityDTO saveUser(EntityDTO user) {

		validateSaveUser(user);

		Subscription subscription = subscriptionProfileService
		        .getSubscription();
		if (StringUtils.isNotBlank(subscription.getEndUserDomain())
		        && StringUtils.isNotBlank(subscription.getSubscriberDomain())) {
			if (!subscription.getEndUserDomain().equals(
			        subscription.getSubscriberDomain())) {
				if (StringUtils.isBlank(getDomainName(user.getDomain()))) {
					DomainDTO domain = new DomainDTO();
					domain.setDomainName(subscription.getEndUserDomain());

					user.setDomain(domain);
				}
			} else {
				throw new GalaxyException(GalaxyCommonErrorCodes.NO_ACCESS);
			}
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.NO_ACCESS);
		}
		isParentDomain(user);

		return saveAnyUser(user);

	}

	private void isParentDomain(EntityDTO user) {
		if (user.getIsParentDomain() != null && user.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	private EntityDTO saveAnyUser(EntityDTO user) {
		/**
		 * remove duplicate fields from input Dto
		 */
		Set<FieldMapDTO> set = new HashSet<>();
		set.addAll(user.getFieldValues());
		List<FieldMapDTO> fieldValues = new ArrayList<FieldMapDTO>();
		fieldValues.addAll(set);
		user.setFieldValues(fieldValues);

		// sanity check
		// EntityDTO userEntityDto = sanityCheck(user);
		Boolean isUpdateFlag = false;

		// Set the user template
		user.setEntityTemplate(getUserPlatformTemplate());
		user.getEntityTemplate().setDomain(user.getDomain());
		EntityDTO userEntityDto = entityTemplateValidation.validateTemplate(
		        user, isUpdateFlag);

		/**
		 * fetch domainEntity for entityId which will work as parentEntityId
		 */
		// String domainName = user.getDomain().getDomainName();
		// EntityDTO domainEntity = null;
		// try {
		// domainEntity = coreEntity.getDomainEntity(domainName, sub);
		// } catch (Exception e) {
		// throw new GalaxyException(
		// GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
		// CommonConstants.DOMAIN_NAME);
		// }

		/**
		 * set parentEntityID as domainEntity entityId
		 */
		// userEntityDto.setParentEntityId(domainEntity.getEntityId());
		/**
		 * save user as an Entity
		 */

		userEntityDto.setDomain(userEntityDto.getEntityTemplate().getDomain());

		Boolean isUpdate = Boolean.FALSE;

		client.post(ConnectionConstants.USER_URL_CTX, defaultBasicAuthHeader,
		        getPayload(userEntityDto, isUpdate), StandardResponse.class);

		coreEntity.saveEntity(userEntityDto);

		return saveReturnDTO(userEntityDto);
	}

	@Override
	public EntityDTO updateUser(EntityDTO user) {

		validateUpdateUser(user);

		isParentDomain(user);

		if (StringUtils.isBlank(getDomainName(user.getDomain()))) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(subscriptionProfileService.getEndUserDomain());
			user.setDomain(domain);
		}
		user.setEntityTemplate(getUserPlatformTemplate());
		user.getEntityTemplate().setDomain(user.getDomain());

		/**
		 * fetch user entity
		 */
		IdentityDTO identityDTO = new IdentityDTO(user);
		// Get entry to be updated from DB
		EntityDTO entityFromDB = coreEntity.getEntity(identityDTO);
		if (user.getEntityStatus() == null
		        || isBlank(user.getEntityStatus().getStatusName())) {
			user.setEntityStatus(entityFromDB.getEntityStatus());
		}

		FieldMapDTO fieldMapDTO = new FieldMapDTO();

		// User cannot update password through Update User service
		fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(UMDataFields.PASSWORD.getVariableName());
		// if input fields contains password, remove it
		if (fetchField(user.getFieldValues(), fieldMapDTO).getValue() != null) {
			user.getFieldValues().remove(fieldMapDTO);
		}

		// EntityDTO userDto = updateCheck(user, userDBDto);
		Boolean isUpdateFlag = true;
		EntityDTO userDto = entityTemplateValidation.validateTemplate(user,
		        isUpdateFlag);
		Boolean isUpdate = Boolean.TRUE;
		User userPayload = getPayload(userDto, isUpdate);

		FieldMapDTO identifier = user.getIdentifier();
		userPayload.setUserName(identifier.getValue());

		client.put(ConnectionConstants.USER_URL_CTX, defaultBasicAuthHeader,
		        userPayload, StandardResponse.class);

		coreEntity.updateEntity(userDto);
		EntityDTO returnUser = saveReturnDTO(userDto);

		return returnUser;
	}

	@Override
	public StatusMessageDTO deleteUser(IdentityDTO userIdentifier) {

		StatusMessageDTO smDTO = new StatusMessageDTO();

		PlatformEntityTemplateDTO platformEntityTemplateDTO = platformEntityTemplateService
		        .getPlatformEntityTemplate(EMDataFields.USER.getFieldName());
		EntityTemplateDTO userTemplate = new EntityTemplateDTO();
		userTemplate.setEntityTemplateName(platformEntityTemplateDTO
		        .getPlatformEntityTemplateName());
		userIdentifier.setEntityTemplate(userTemplate);
		validateIdentifier(userIdentifier);

		isParentDomain(userIdentifier);

		EntityDTO entity = coreEntity.getEntity(userIdentifier);

		String deleteStatus = Status.DELETED.name();

		coreEntity.updateStatus(userIdentifier, deleteStatus);
		String userName = userIdentifier.getIdentifier().getValue();
		String domainName = userIdentifier.getDomain().getDomainName();

		String targetUrl = ConnectionConstants.USER_URL_CTX + "/" + userName
		        + "?" + "tenantDomain=" + domainName;
		client.delete(targetUrl, defaultBasicAuthHeader, StandardResponse.class);

		smDTO.setStatus(Status.SUCCESS);
		return smDTO;
	}

	private void isParentDomain(IdentityDTO userIdentifier) {
		if (userIdentifier.getIsParentDomain() != null
		        && userIdentifier.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public EntityDTO saveAdminUser(EntityDTO user) {

		validateSaveUser(user);

		Subscription subscription = subscriptionProfileService
		        .getSubscription();

		if (StringUtils.isNotBlank(subscription.getEndUserDomain())
		        && StringUtils.isNotBlank(subscription.getSubscriberDomain())) {
			if (subscription.getEndUserDomain().equals(
			        subscription.getSubscriberDomain())) {
				if (StringUtils.isBlank(getDomainName(user.getDomain()))) {
					DomainDTO domain = new DomainDTO();

					domain.setDomainName(String.format(
					        CommonConstants.STRING_FORMAT,
					        subscription.getSubscriberApp(),
					        CommonConstants.DOT, CommonConstants.GALAXY));
					user.setDomain(domain);
				}
			} else {
				throw new GalaxyException(GalaxyCommonErrorCodes.NO_ACCESS);
			}
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.NO_ACCESS);
		}

		isParentDomain(user);

		return saveAnyUser(user);
	}

	private User getPayload(EntityDTO userEntityDto, Boolean isUpdate) {
		User user = new User();

		List<FieldMapDTO> fieldValues = userEntityDto.getFieldValues();
		/**
		 * fetch roleName from fieldValues and save in user
		 */
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(UMDataFields.ROLE_NAME.getVariableName());
		String rolesString = fetchField(fieldValues, fieldMapDTO).getValue();
		user.setRoles(getRoles(rolesString));
		user.setUserName(userEntityDto.getIdentifier().getValue());
		user.setUserClaims(getUserClaims(userEntityDto));
		user.setTenantDomain(userEntityDto.getEntityTemplate().getDomain()
		        .getDomainName());
		if (!isUpdate) {
			// fieldMapDTO = new FieldMapDTO();
			// fieldMapDTO.setKey(UMDataFields.PASSWORD.getVariableName());
			// fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
			// user.setPassword(fieldMapDTO.getValue());
			user.setPassword("password");
		}

		return user;
	}

	private ArrayList<String> getRoles(String roleString) {
		ArrayList<String> roles = new ArrayList<>();
		try {
			JSONArray newJArray = new JSONArray(roleString);
			for (int i = 0; i < newJArray.length(); i++) {
				if (StringUtils.isNotBlank(newJArray.getString(i))) {
					roles.add(newJArray.getString(i));
				}
			}
		} catch (Exception e) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.ROLE_NAMES_SHOULD_BE_JSON_ARRAY);
		}
		if (CollectionUtils.isEmpty(roles)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        CommonConstants.ROLE_NAME);
		}
		return roles;
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {

		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	private List<UserClaim> getUserClaims(EntityDTO userDto) {
		List<UserClaim> userClaims = new ArrayList<>();

		List<FieldMapDTO> fieldValues = userDto.getFieldValues();

		// set firstName in usersClaims
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(UMDataFields.FIRST_NAME.getVariableName());
		fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
		if (StringUtils.isNotBlank(fieldMapDTO.getValue())) {
			userClaims.add(getUserClaim(UserClaimConstants.FIRST_NAME,
			        fieldMapDTO.getValue()));
		}

		// set lastName in usersClaims
		fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(UMDataFields.LAST_NAME.getVariableName());
		fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
		if (StringUtils.isNotBlank(fieldMapDTO.getValue())) {
			userClaims.add(getUserClaim(UserClaimConstants.LAST_NAME,
			        fieldMapDTO.getValue()));
		}

		// set primaryEmail in usersClaims
		fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(UMDataFields.EMAIL_ID.getVariableName());
		fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
		if (StringUtils.isNotBlank(fieldMapDTO.getValue())) {
			userClaims.add(getUserClaim(UserClaimConstants.EMAIL_ID,
			        fieldMapDTO.getValue()));
		}

		// set contactNumber in usersClaims
		fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(UMDataFields.CONTACT_NUMBER.getVariableName());
		fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
		if (StringUtils.isNotBlank(fieldMapDTO.getValue())) {
			userClaims.add(getUserClaim(UserClaimConstants.MOBILE_NUMBER,
			        fieldMapDTO.getValue()));
		}

		// set statusName in usersClaims
		if (userDto.getEntityStatus().getStatusName()
		        .equalsIgnoreCase(AlpineUMStatus.ACTIVE.toString())) {

			userClaims.add(getUserClaim(UserClaimConstants.STATUS,
			        AlpineISStatus.TRUE.toString()));
		} else {
			userClaims.add(getUserClaim(UserClaimConstants.STATUS,
			        AlpineISStatus.FALSE.toString()));
		}
		return userClaims;
	}

	private UserClaim getUserClaim(String uri, String value) {
		UserClaim userClaim = null;
		if (StringUtils.isNotBlank(value)) {
			userClaim = new UserClaim();
			userClaim.setUri(uri);
			userClaim.setValue(value);
		}
		return userClaim;
	}

	private void validateSaveUser(EntityDTO entityDTO) {
		ValidationUtils.validateMandatoryFields(entityDTO);

		/**
		 * validate above mentioned fields in input
		 */
		ValidationUtils.validateMandatoryFields(entityDTO,
		        UMDataFields.FIELD_VALUES);

		PlatformEntityDTO globalEntityDTO = platformEntityService
		        .getGlobalEntityWithName(UMDataFields.USER.name());
		entityDTO.setPlatformEntity(globalEntityDTO);

		/**
		 * check entityStatus is valid, either ACTIVE or INACTIVE
		 */
		if (entityDTO.getEntityStatus() != null) {
			ValidationUtils.validateMandatoryField(UMDataFields.STATUS_NAME,
			        entityDTO.getEntityStatus().getStatusName());
			if (!(entityDTO.getEntityStatus().getStatusName()
			        .equalsIgnoreCase(Status.ACTIVE.name()))
			        && !(entityDTO.getEntityStatus().getStatusName()
			                .equalsIgnoreCase(Status.INACTIVE.name()))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        CommonConstants.STATUS_NAME);
			}
		} else {
			StatusDTO status = new StatusDTO();
			status.setStatusName(Status.ACTIVE.name().toUpperCase());
			entityDTO.setEntityStatus(status);
		}

		/**
		 * validate the size of fieldValues
		 */
		ValidationUtils.validateCollection(UMDataFields.FIELD_VALUES,
		        entityDTO.getFieldValues());
	}

	// private EntityDTO sanityCheck(EntityDTO userEntityDTO) {
	// Map<String, String> fieldValidations = null;
	//
	// // fetch Template fieldValidation Map
	// EntityTemplateDTO entityTemplateDTO = fetchTemplate(userEntityDTO);
	// fieldValidations = entityTemplateDTO.getFieldValidation();
	//
	// // fetch fieldMap from input EntityDto
	// List<FieldMapDTO> fieldValuesFromInputDto = userEntityDTO
	// .getFieldValues();
	//
	// List<FieldMapDTO> mapDTOs = new ArrayList<>();
	// FieldMapDTO fieldMapDTO = null;
	// ValidationTestDTO validationTestDTO = new ValidationTestDTO();
	// List<FieldMapDTO> dataproviderMap = new ArrayList<>();
	// FieldMapDTO identifierFieldValue = new FieldMapDTO();
	//
	// // iterate over Template fieldValidations
	// for (String fieldValidationKey : fieldValidations.keySet()) {
	//
	// // fetch fieldValidation string for fieldValidationKey
	// String fieldValidationString = fieldValidations
	// .get(fieldValidationKey);
	//
	// fieldMapDTO = new FieldMapDTO();
	// fieldMapDTO.setKey(fieldValidationKey);
	// // populate field<FieldMapDTO> from input EntityDto
	// fieldMapDTO = fetchField(fieldValuesFromInputDto, fieldMapDTO);
	// validationTestDTO.setFieldMapDTO(fieldMapDTO);
	// // set validationJson String to ValidationTestDTO
	// validationTestDTO.setValidationJsonString(fieldValidationString,
	// objectBuilderUtil.getGson());
	// /**
	// * if server validations are present, do them else save as it is.
	// */
	// if (validationStringCheck(fieldValidationString)) {
	// // do the validation (Mandatory or Unique)
	// checkValidation(validationTestDTO, userEntityDTO.getDomain()
	// .getDomainName());
	//
	// mapDTOs.add(fieldMapDTO);
	// } else {
	// fieldMapDTO = new FieldMapDTO();
	// // fieldValidationString for server validation is null: copy
	// // value from input EntityDTO if input EntityDTO is having value
	// fieldMapDTO.setKey(fieldValidationKey);
	// fieldMapDTO = fetchField(fieldValuesFromInputDto, fieldMapDTO);
	// if (isNotBlank(fieldMapDTO.getValue())) {
	// mapDTOs.add(fieldMapDTO);
	// }
	// }
	// /**
	// * set dataProvider List<DataProvider> if dataProvider property is
	// * TRUE in Template
	// */
	// if (validationTestDTO.getValidationJsonStringDTO().getShowOnGrid()) {
	// if (isBlank(fieldMapDTO.getValue())) {
	// fieldMapDTO.setValue("");
	// }
	// dataproviderMap.add(fieldMapDTO);
	// }
	// /**
	// * Template must not contain showOnTree property
	// */
	//
	// }
	// // set password as userName
	// fieldMapDTO = new FieldMapDTO();
	// fieldMapDTO.setKey(UMDataFields.PASSWORD.toString());
	// fieldMapDTO.setValue("password");
	// // password should be encrypted
	// mapDTOs.remove(fieldMapDTO);
	// mapDTOs.add(fieldMapDTO);
	//
	// // Validate Roles
	// /*
	// * fieldMapDTO = new FieldMapDTO();
	// * fieldMapDTO.setKey(UMDataFields.ROLE_NAME.toString()); String roles =
	// * fetchField(mapDTOs, fieldMapDTO).getValue(); ArrayList<String>
	// * roleList = getRoles(roles);
	// */
	//
	// // replace input dto fielMap with new field map
	// userEntityDTO.setFieldValues(mapDTOs);
	//
	// entityTemplateDTO.setFieldValidation(null);
	// userEntityDTO.setEntityTemplate(entityTemplateDTO);
	//
	// userEntityDTO.setDataprovider(dataproviderMap);
	//
	// // Set the identifier field
	// String identifierField = userEntityDTO.getEntityTemplate()
	// .getIdentifierField();
	// identifierFieldValue.setKey(identifierField);
	// fieldValuesFromInputDto.get(
	// fieldValuesFromInputDto.indexOf(identifierFieldValue))
	// .getValue();
	// identifierFieldValue.setValue(fieldValuesFromInputDto.get(
	// fieldValuesFromInputDto.indexOf(identifierFieldValue))
	// .getValue());
	// userEntityDTO.setIdentifier(identifierFieldValue);
	//
	// return userEntityDTO;
	// }

	private EntityTemplateDTO fetchTemplate(EntityDTO entityDTO) {
		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		// fetch template based on globalEntityType
		PlatformEntityTemplateDTO globalEntityTemplateDTO = platformEntityTemplateService
		        .getPlatformEntityTemplate(UMDataFields.USER.name()
		                .toUpperCase());
		entityTemplate.setEntityTemplateName(globalEntityTemplateDTO
		        .getPlatformEntityTemplateName());
		entityTemplate.setDomain(entityDTO.getDomain());
		entityTemplate.setFieldValidation(globalEntityTemplateDTO
		        .getFieldValidation());
		entityTemplate.setIdentifierField(globalEntityTemplateDTO
		        .getIdentifierField());
		return entityTemplate;
	}

	private Boolean validationStringCheck(String fieldValidationString) {
		Boolean resultFlag = false;
		Gson gson = objectBuilderUtil.getGson();
		ValidationJsonStringDTO validationJsonStringDTO = gson.fromJson(
		        fieldValidationString, ValidationJsonStringDTO.class);
		if (CollectionUtils.isNotEmpty(validationJsonStringDTO.getServer())) {
			resultFlag = true;
		}
		return resultFlag;
	}

	// private void checkValidation(ValidationTestDTO validationTestDTO,
	// String domainName) {
	// List<String> serverValidationArray = validationTestDTO
	// .getValidationJsonStringDTO().getServer();
	// for (String serverValidation : serverValidationArray) {
	// GalaxyValidationDataFields validationType = GalaxyValidationDataFields
	// .getEnum(serverValidation);
	// switch (validationType) {
	// case MANDATORY :
	// ValidationUtils.validateMandatoryField(validationTestDTO
	// .getFieldMapDTO().getKey(), validationTestDTO
	// .getFieldMapDTO().getValue());
	// break;
	// case UNIQUE_ACROSS_DOMAIN :
	// uniqueAcrossDomain(validationTestDTO, domainName);
	// break;
	//
	// case UNIQUE_ACROSS_APPLICATION :
	// uniqueAcrossApplication(validationTestDTO);
	// break;
	//
	// default:
	// // invalid validation TODO
	// break;
	// }
	// }
	// }

	private void uniqueAcrossDomain(ValidationTestDTO validationTestDTO,
	        String domainName) {
		ValidationUtils.validateMandatoryField(validationTestDTO
		        .getFieldMapDTO().getKey(), validationTestDTO.getFieldMapDTO()
		        .getValue());
		// check all the entities except DELETED ones
		// send fieldKey and fieldValue for unique check across
		// domain
		FieldMapDTO field = validationTestDTO.getFieldMapDTO();
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		fields.add(field);
		if (fetchUsersByField(fields, domainName).size() != 0) {
			// multiple entities are present in DB. Unique check
			// violation.
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_IS_NOT_UNIQUE,
			        validationTestDTO.getFieldMapDTO().getKey());
		}
	}

	private void uniqueAcrossApplication(ValidationTestDTO validationTestDTO) {
		ValidationUtils.validateMandatoryField(validationTestDTO
		        .getFieldMapDTO().getKey(), validationTestDTO.getFieldMapDTO()
		        .getValue());
		// check all the entities except DELETED ones
		// send fieldKey and fieldValue for unique check across
		// application
		String domainName = null;
		FieldMapDTO field = validationTestDTO.getFieldMapDTO();
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		fields.add(field);
		if (fetchUsersByField(fields, domainName).size() != 0) {
			// multiple entities are present in DB. Unique check
			// violation.
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_IS_NOT_UNIQUE,
			        validationTestDTO.getFieldMapDTO().getKey());
		}
	}

	private List<EntityDTO> fetchUsersByField(List<FieldMapDTO> fields,
	        String domain) {
		String entityType = UMDataFields.USER.name();
		List<EntityDTO> entityDTOs = null;
		try {
			EntitySearchDTO searchEntity = new EntitySearchDTO();
			searchEntity.setFieldValues(fields);
			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setDomainName(domain);
			searchEntity.setDomain(domainDTO);
			searchEntity.setEntityType(entityType);
			PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
			platformEntityDTO.setPlatformEntityType(entityType);
			searchEntity.setPlatformEntity(setEntityType());
			entityDTOs = coreEntity.getEntitiesByField(searchEntity);
		} catch (GalaxyException e) {
			if (!e.getCode().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode())) {
				throw e;
			}
		}
		return entityDTOs == null ? new ArrayList<EntityDTO>() : entityDTOs;
	}

	private void validateUpdateUser(EntityDTO entityDTO) {
		ValidationUtils.validateMandatoryFields(entityDTO);

		/**
		 * validate above mentioned fields in input
		 */
		ValidationUtils.validateMandatoryFields(entityDTO,
		        UMDataFields.IDENTIFIER, UMDataFields.FIELD_VALUES);

		/**
		 * validate Identifier key-value
		 */
		ValidationUtils.validateMandatoryFields(entityDTO.getIdentifier(),
		        UMDataFields.IDENTIFIER_KEY, UMDataFields.IDENTIFIER_VALUE);

		PlatformEntityDTO globalEntityDTO = platformEntityService
		        .getGlobalEntityWithName(UMDataFields.USER.name());
		entityDTO.setPlatformEntity(globalEntityDTO);

		PlatformEntityTemplateDTO globalEntityTemplateDTO = platformEntityTemplateService
		        .getPlatformEntityTemplate(entityDTO.getPlatformEntity()
		                .getPlatformEntityType());

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(globalEntityTemplateDTO
		        .getPlatformEntityTemplateName());
		entityTemplate.setDomain(entityDTO.getDomain());
		entityDTO.setEntityTemplate(entityTemplate);

		/**
		 * check fieldValues is not blank
		 */
		ValidationUtils.validateCollection(UMDataFields.FIELD_VALUES,
		        entityDTO.getFieldValues());

		/**
		 * check entityStatus is valid
		 */
		if (entityDTO.getEntityStatus() != null) {
			ValidationUtils.validateMandatoryField(UMDataFields.STATUS_NAME,
			        entityDTO.getEntityStatus().getStatusName());
			if (!((AlpineUMStatus.ACTIVE.toString().equalsIgnoreCase(entityDTO
			        .getEntityStatus().getStatusName())) || (AlpineUMStatus.INACTIVE
			        .toString().equalsIgnoreCase(entityDTO.getEntityStatus()
			        .getStatusName())))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        UMDataFields.STATUS_NAME.getDescription());
			}
		}
	}

	// private EntityDTO updateCheck(EntityDTO userInputDto, EntityDTO
	// userDBDto) {
	// List<FieldMapDTO> inputFieldValues = userInputDto.getFieldValues();
	// List<FieldMapDTO> dBFieldValues = userDBDto.getFieldValues();
	//
	// // fetch Template fieldValidation Map
	// EntityTemplateDTO entityTemplateDTO = fetchTemplate(userInputDto);
	// Map<String, String> fieldValidations = entityTemplateDTO
	// .getFieldValidation();
	//
	// FieldMapDTO fieldMapDTO = new FieldMapDTO();
	//
	// // User cannot update password through Update User service
	// fieldMapDTO = new FieldMapDTO();
	// fieldMapDTO.setKey(UMDataFields.PASSWORD.getVariableName());
	// // if input fields contains password, remove it
	// if (fetchField(inputFieldValues, fieldMapDTO).getValue() != null) {
	// inputFieldValues.remove(fieldMapDTO);
	// }
	//
	// Set<FieldMapDTO> fieldsNeedsToBeUpdated = new HashSet<FieldMapDTO>();
	// ValidationTestDTO validationTestDTO = new ValidationTestDTO();
	// for (FieldMapDTO dbfield : dBFieldValues) {
	// FieldMapDTO uiFieldMap = new FieldMapDTO();
	// uiFieldMap = fetchField(inputFieldValues, dbfield);
	//
	// String validationString = fieldValidations.get(dbfield.getKey());
	// validationTestDTO.setValidationJsonString(validationString,
	// objectBuilderUtil.getGson());
	// validationTestDTO.setFieldMapDTO(uiFieldMap);
	//
	// if (validationStringCheck(validationString)) {
	//
	// List<String> serverValidationArray = validationTestDTO
	// .getValidationJsonStringDTO().getServer();
	// for (String serverValidation : serverValidationArray) {
	// GalaxyValidationDataFields validationType = GalaxyValidationDataFields
	// .getEnum(serverValidation);
	// switch (validationType) {
	// case MANDATORY :
	// ValidationUtils
	// .validateMandatoryField(validationTestDTO
	// .getFieldMapDTO().getKey(),
	// validationTestDTO.getFieldMapDTO()
	// .getValue());
	// break;
	// case UNIQUE_ACROSS_DOMAIN :
	// if (!dbfield.getValue().equals(
	// uiFieldMap.getValue())) {
	// uniqueAcrossDomain(validationTestDTO, userDBDto
	// .getDomain().getDomainName());
	// }
	// break;
	//
	// case UNIQUE_ACROSS_APPLICATION :
	// if (!uiFieldMap.getValue().equals(
	// uiFieldMap.getValue())) {
	// uniqueAcrossApplication(validationTestDTO);
	// }
	// break;
	//
	// default:
	// // invalid validation TODO
	// break;
	// }
	// }
	//
	// }
	//
	// }
	//
	// // update on userName is not allowed through Update User service
	// fieldMapDTO = new FieldMapDTO();
	// fieldMapDTO.setKey(UMDataFields.USER_NAME.getVariableName());
	// if (fetchField(inputFieldValues, fieldMapDTO).getValue() != null) {
	// if (fetchField(inputFieldValues, fieldMapDTO).getValue().equals(
	// fetchField(dBFieldValues, fieldMapDTO).getValue())) {
	// inputFieldValues.remove(fieldMapDTO);
	// } else {
	// throw new GalaxyException(
	// AlpineUMErrorCodes.USER_NAME_CANT_UPDATED);
	// }
	// }
	// // iterate over input keys
	// for (FieldMapDTO fieldMapDTO1 : inputFieldValues) {
	// // fetch key from DBFieldValues
	// FieldMapDTO dbFieldMap = new FieldMapDTO();
	// dbFieldMap = fetchField(dBFieldValues, fieldMapDTO1);
	// if (isBlank(dbFieldMap.getKey())) {
	// // if key is not present in DB, ignore than key-value pair
	// continue;
	// }
	//
	// // check input key contains value else throw an exp
	// try {
	// // String value = fieldMapDTO1.getValue().trim(); //Commenting
	// // this to remove a warning(Javid)
	// fieldsNeedsToBeUpdated.remove(fieldMapDTO1);
	// fieldsNeedsToBeUpdated.add(fieldMapDTO1);
	// } catch (NullPointerException ne) {
	// throw new GalaxyException(
	// GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
	// EMDataFields.getDataField(fieldMapDTO1.getKey())
	// .getDescription()
	// + "/"
	// + EMDataFields.FIELD_VALUE.getDescription());
	// }
	// }
	//
	// // fieldsNeedsToBeUpdated contains all the required fields to be updated
	// // in DB, replace them in dbFieldValues
	// dBFieldValues.removeAll(fieldsNeedsToBeUpdated);
	// dBFieldValues.addAll(fieldsNeedsToBeUpdated);
	//
	// userDBDto.setFieldValues(dBFieldValues);
	// List<FieldMapDTO> updateDataProvider = new ArrayList<FieldMapDTO>();
	// for (FieldMapDTO fieldMap1 : userDBDto.getDataprovider()) {
	// FieldMapDTO updatedFieldMap = fetchField(dBFieldValues, fieldMap1);
	// if (isBlank(updatedFieldMap.getValue())) {
	// updatedFieldMap.setValue("");
	// }
	// updateDataProvider.add(updatedFieldMap);
	// }
	// if (CollectionUtils.isNotEmpty(updateDataProvider)) {
	// userDBDto.setDataprovider(updateDataProvider);
	// }
	//
	// if (userInputDto.getEntityStatus() != null) {
	// userDBDto.setEntityStatus(userInputDto.getEntityStatus());
	// }
	//
	// return userDBDto;
	// }

	private void validateIdentifier(IdentityDTO userDTO) {

		/**
		 * validate above mentioned fields in input
		 */
		ValidationUtils.validateMandatoryFields(userDTO,
		        UMDataFields.ENTITY_TEMPLATE, UMDataFields.IDENTIFIER);

		/**
		 * check identifier key-value specified
		 */
		ValidationUtils.validateMandatoryFields(userDTO.getIdentifier(),
		        UMDataFields.IDENTIFIER_KEY, UMDataFields.IDENTIFIER_VALUE);

		if (StringUtils.isBlank(getDomainName(userDTO.getDomain()))) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(subscriptionProfileService.getEndUserDomain());;
			userDTO.setDomain(domain);
		}

		PlatformEntityDTO globalEntity = new PlatformEntityDTO();
		globalEntity.setPlatformEntityType(EMDataFields.USER.getVariableName());
		userDTO.setPlatformEntity(globalEntity);

	}

	private List<EntityDTO> createGetUsersByDomainDTO(List<EntityDTO> users) {
		EntityDTO returnEntityDto = null;
		List<EntityDTO> list = new ArrayList<EntityDTO>();
		for (EntityDTO entityDTO : users) {
			returnEntityDto = new EntityDTO();
			PlatformEntityDTO platformEntity = new PlatformEntityDTO();
			platformEntity.setPlatformEntityType(entityDTO.getPlatformEntity()
			        .getPlatformEntityType());
			returnEntityDto.setPlatformEntity(platformEntity);

			StatusDTO statusDTO = new StatusDTO();
			statusDTO
			        .setStatusName(entityDTO.getEntityStatus().getStatusName());
			returnEntityDto.setEntityStatus(statusDTO);

			EntityTemplateDTO template = new EntityTemplateDTO();
			template.setEntityTemplateName(entityDTO.getEntityTemplate()
			        .getEntityTemplateName());
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(entityDTO.getEntityTemplate().getDomain()
			        .getDomainName());
			template.setDomain(domain);
			returnEntityDto.setEntityTemplate(template);
			returnEntityDto.setDataprovider(entityDTO.getDataprovider());
			returnEntityDto.setIdentifier(entityDTO.getIdentifier());
			list.add(returnEntityDto);
		}
		return list;
	}

	private EntityDTO saveReturnDTO(EntityDTO entity) {
		EntityDTO returnEntity = new EntityDTO();
		returnEntity.setFieldValues(entity.getFieldValues());
		returnEntity.setDataprovider(entity.getDataprovider());
		returnEntity.setIdentifier(entity.getIdentifier());
		returnEntity.setEntityStatus(entity.getEntityStatus());
		returnEntity.setDomain(entity.getDomain());
		returnEntity.setEntityTemplate(entity.getEntityTemplate());
		removePasswordField(returnEntity);
		return returnEntity;
	}

	@Override
	public StatusMessageDTO validateUniqueness(
	        EntitySearchDTO coreEntitySearchDTO) {
		// set global entity type is marker
		coreEntitySearchDTO.setPlatformEntity(setEntityType());
		// Check for domain name in payload and set type as marker
		coreEntitySearchDTO
		        .setDomain(setDomain(coreEntitySearchDTO.getDomain()));
		validateUniquenessPayload(coreEntitySearchDTO);

		isParentDomain(coreEntitySearchDTO);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		try {
			coreEntity.getEntityByField(coreEntitySearchDTO);

		} catch (GalaxyException galaxyException) {
			// 500 error code indicates no data exists
			if (galaxyException.getCode() == 500) {
				statusMessageDTO.setStatus(Status.SUCCESS);
			}
		}
		return statusMessageDTO;
	}

	private void isParentDomain(EntitySearchDTO coreEntitySearchDTO) {
		if (coreEntitySearchDTO.getIsParentDomain() != null
		        && coreEntitySearchDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	private PlatformEntityDTO setEntityType() {
		PlatformEntityDTO markerType = platformEntityService
		        .getGlobalEntityWithName(EMDataFields.USER.getFieldName());
		return markerType;
	}

	private DomainDTO setDomain(DomainDTO domain) {
		if (domain == null) {
			domain = new DomainDTO();
		}
		if (domain.getDomainName() == null || domain.getDomainName().isEmpty()) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			domain.setDomainName(domainName);
		}
		return domain;
	}

	private void validateUniquenessPayload(EntitySearchDTO entitySearchDTO) {
		validateMandatoryFields(entitySearchDTO, DOMAIN, FIELD_VALUES);

		validateMandatoryFields(entitySearchDTO.getDomain(), DOMAIN_NAME);

		ValidationUtils.validateCollection(EMDataFields.FIELD_VALUES,
		        entitySearchDTO.getFieldValues());

		validateMandatoryFields(entitySearchDTO.getFieldValues().get(0),
		        FIELD_KEY, FIELD_VALUE);

	}

	private String getDomainName(DomainDTO domain) {
		String domainName = null;
		if (domain != null) {
			if (StringUtils.isNotBlank(domain.getDomainName())) {
				domainName = domain.getDomainName();
			}
		}
		return domainName;
	}

	private EntityDTO removePasswordField(EntityDTO entity) {

		List<FieldMapDTO> fileds = entity.getFieldValues();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(UMDataFields.PASSWORD.getVariableName());
		fieldMapDTO = fetchField(fileds, fieldMapDTO);
		fileds.remove(fieldMapDTO);

		return entity;
	}

	@Override
	public EntityCountDTO getUserCount(EntitySearchDTO userSearch) {
		// check for domain, if not present fetch from logged in domain
		userSearch.setDomain(setDomain(userSearch.getDomain()));
		// set entity type as USER
		PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
		globalEntityDTO.setPlatformEntityType(UMDataFields.USER.name());
		userSearch.setPlatformEntity(globalEntityDTO);

		PlatformEntityTemplateDTO globalEntityTemplateDTO = platformEntityTemplateService
		        .getPlatformEntityTemplate(UMDataFields.USER.name()
		                .toUpperCase());
		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(globalEntityTemplateDTO
		        .getPlatformEntityTemplateName());
		userSearch.setEntityTemplate(entityTemplate);
		isParentDomain(userSearch);

		Integer count = 0;
		if (userSearch.getEntityTemplate() == null
		        || isBlank(userSearch.getEntityTemplate()
		                .getEntityTemplateName())) {
			count = coreEntity.getEntitiesCountByTypeAndDomain(userSearch);
		} else {
			count = coreEntity.getEntitiesCountByTemplate(userSearch);
		}
		EntityCountDTO countDTO = new EntityCountDTO();
		countDTO.setCount(count);
		return countDTO;
	}

	@Override
	public StatusMessageDTO resetPassword(UserDTO user) {
		// Validate input
		validateMandatoryFields(user, USER_NAME, DOMAIN, PASSWORD);

		isParentDomain(user);

		String urlContext = ConnectionConstants.USER_URL_CTX + "/"
		        + user.getUserName() + "?tenantDomain=" + user.getDomain();

		// Get the data from IS
		User userResponse = client.get(urlContext, defaultBasicAuthHeader,
		        User.class);

		if (userResponse == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        CommonConstants.USER);
		}

		// set to the new password
		userResponse.setPassword(user.getPassword());

		// Remove "Internal/everyone" from roles
		List<String> rolesToAdd = new ArrayList<String>();
		for (String roleName : userResponse.getRoles()) {
			if (!roleName.equalsIgnoreCase("Internal/everyone")) {
				rolesToAdd.add(roleName);
			}
		}
		userResponse.setRoles(rolesToAdd);

		StandardResponse updateStatus = client.put(
		        ConnectionConstants.USER_URL_CTX, defaultBasicAuthHeader,
		        userResponse, StandardResponse.class);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);

		if (updateStatus.getStatus().equalsIgnoreCase(FAILED.getVariableName())) {
			statusMessageDTO.setStatus(Status.FAILURE);
		}
		return statusMessageDTO;
	}

	private void isParentDomain(UserDTO user) {
		if (user.getIsParentDomain() != null && user.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public List<EntityStatusCountDTO> getUserCountByStatus(
	        EntitySearchDTO userSearch) {
		// check for domain, if not present fetch from logged in domain
		userSearch.setDomain(setDomain(userSearch.getDomain()));
		// set entity type as USER
		PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
		globalEntityDTO.setPlatformEntityType(UMDataFields.USER.name());
		userSearch.setPlatformEntity(globalEntityDTO);

		isParentDomain(userSearch);

		List<EntityStatusCountDTO> entityStatusCountDTOs = coreEntity
		        .getEntitiesCountByStatus(userSearch);
		return entityStatusCountDTOs;
	}

	/**
	 * This function is used to get the entity of a user and return it for a
	 * forgot password, so email can be send to the user
	 * 
	 * 
	 **/
	@Override
	public UserDTO forgotPassword(UserDTO userDTO) {

		validateMandatoryFields(userDTO, USER_NAME, EMAIL_ID, EMAIL_LINK);

		// Sets the domain in the DTO from username
		setDomainFromUsername(userDTO);

		// check if user exists in IS
		isValidUserInIS(userDTO);

		// Set DomainDTO
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(userDTO.getDomain());

		// Initialize and set Field Map DTOS
		List<FieldMapDTO> fieldMapDTOs = new ArrayList<FieldMapDTO>();

		// Set User name Field Map DTO
		FieldMapDTO userNameFieldMapDTO = new FieldMapDTO();
		userNameFieldMapDTO.setKey(UMDataFields.USER_NAME.getVariableName());
		userNameFieldMapDTO.setValue(userDTO.getUserName());

		fieldMapDTOs.add(userNameFieldMapDTO);

		// Set EntitySearchDTO
		EntitySearchDTO entitySearchDTO = new EntitySearchDTO();
		entitySearchDTO.setDomain(domainDTO);
		entitySearchDTO.setFieldValues(fieldMapDTOs);

		// Fetch user template
		PlatformEntityTemplateDTO platformEntityTemplateDTO = platformEntityTemplateService
		        .getPlatformEntityTemplate(UMDataFields.USER.name()
		                .toUpperCase());
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(platformEntityTemplateDTO
		        .getPlatformEntityTemplateName());
		entitySearchDTO.setEntityTemplate(entityTemplateDTO);

		entitySearchDTO.setPlatformEntity(setEntityType());

		EntityDTO userEntity = coreEntity.getEntityByField(entitySearchDTO);

		FieldMapDTO emailIdFieldMapDTO = new FieldMapDTO();
		emailIdFieldMapDTO.setKey(UMDataFields.EMAIL_ID.getVariableName());

		emailIdFieldMapDTO = fetchField(userEntity.getFieldValues(),
		        emailIdFieldMapDTO);

		// Comparing given mail ID and one in the DB
		if (!userDTO.getEmailId().equalsIgnoreCase(
		        emailIdFieldMapDTO.getValue())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        CommonConstants.USER);
		}

		ESBUserDTO esbUserDTO = new ESBUserDTO();
		esbUserDTO.setUserEntity(userEntity);
		esbUserDTO.setLinkEmailESBDTO(createLinkMarkerPayload(userDTO));

		userDTO.setEsbUserDTO(esbUserDTO);

		return userDTO;
	}

	private void setDomainFromUsername(UserDTO userDTO) {
		// Extract domain value
		userDTO.setDomain(userDTO.getUserName().substring(
		        userDTO.getUserName().indexOf('@') + 1,
		        userDTO.getUserName().length()));

		// Extract username from DTO
		userDTO.setUserName(userDTO.getUserName().substring(0,
		        userDTO.getUserName().indexOf('@')));
	}

	private void isValidUserInIS(UserDTO userDTO) {
		String urlContext = ConnectionConstants.USER_URL_CTX + "/"
		        + userDTO.getUserName() + "?tenantDomain="
		        + userDTO.getDomain();

		// Get the data from IS
		User userResponse = client.get(urlContext, defaultBasicAuthHeader,
		        User.class);

		if (userResponse == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        CommonConstants.USER);
		}

	}

	private LinkEmailESBDTO createLinkMarkerPayload(UserDTO userDTO) {

		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();

		FieldMapDTO entityName = new FieldMapDTO();
		entityName.setKey(EMDataFields.ENTITY_NAME.getVariableName());
		String userName = userDTO.getUserName();
		entityName.setValue(userName);
		fieldMaps.add(entityName);

		FieldMapDTO timeStamp = new FieldMapDTO();
		timeStamp.setKey(EMDataFields.TIME_STAMP.getVariableName());
		Date date = new Date();
		Long longTime = date.getTime();
		timeStamp.setValue(longTime.toString());
		fieldMaps.add(timeStamp);

		FieldMapDTO valid = new FieldMapDTO();
		valid.setKey(EMDataFields.VALID.getVariableName());
		valid.setValue("true");
		fieldMaps.add(valid);

		FieldMapDTO domain = new FieldMapDTO();
		domain.setKey(EMDataFields.MY_DOMAIN.getVariableName());
		domain.setValue(userDTO.getDomain());
		fieldMaps.add(domain);

		FieldMapDTO emailMap = new FieldMapDTO();
		emailMap.setKey(EMDataFields.EMAIL_ID.getVariableName());
		String emailId = userDTO.getEmailId();
		emailMap.setValue(emailId);
		fieldMaps.add(emailMap);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate
		        .setEntityTemplateName(EMDataFields.RESET_PASSWORD_TEMPLATE
		                .getVariableName());

		String domainName = userDTO.getDomain();
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);

		EmailDTO email = new EmailDTO();
		email.setContent(userDTO.getEmailLink());
		email.setEmailTemplate(NameConstants.SET_PWD_EMAIL_TEMPLATE);
		email.setSubject(NameConstants.SET_PASSWORD_MAIL_SUBJECT);
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
	public StatusMessageDTO changePassword(UserDTO userDTO) {

		// Validate input
		validateMandatoryFields(userDTO, USER_NAME, PASSWORD, NEWPASSWORD);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);

		DomainDTO domainDTO = new DomainDTO();

		domainDTO.setDomainName(subscriptionProfileService.getEndUserDomain());
		userDTO.setDomain(subscriptionProfileService.getEndUserDomain());

		// Extract username from DTO
		userDTO.setUserName(userDTO.getUserName().substring(0,
		        userDTO.getUserName().indexOf('@')));

		isParentDomain(userDTO);

		// checks if password in IS is valid
		isValidPasswordInIS(userDTO);

		// Get the data from IS
		String urlContext = ConnectionConstants.USER_URL_CTX + "/"
		        + userDTO.getUserName() + "?tenantDomain="
		        + userDTO.getDomain();

		User userResponse = client.get(urlContext, defaultBasicAuthHeader,
		        User.class);

		if (userResponse == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        CommonConstants.USER);
		}

		EntityDTO entity = new EntityDTO();

		// Check if user is active or inactive in galaxy
		try {
			entity = coreEntity.getEntityByField(constructEntitySearchDTO(
			        userDTO, domainDTO));
		} catch (GalaxyException galaxyException) {
			// 500 error code indicates no data exists
			if (galaxyException.getCode() == 500) {
				statusMessageDTO.setStatus(Status.FAILURE);
			}
		}

		// check if user is active
		validateActiveUser(entity);

		// set the new password
		userResponse.setPassword(userDTO.getNewPassword());

		// Remove "Internal/everyone" from roles
		List<String> rolesToAdd = new ArrayList<String>();
		for (String roleName : userResponse.getRoles()) {
			if (!roleName.equalsIgnoreCase("Internal/everyone")) {
				rolesToAdd.add(roleName);
			}
		}
		userResponse.setRoles(rolesToAdd);

		StandardResponse updateStatus = client.put(
		        ConnectionConstants.USER_URL_CTX, defaultBasicAuthHeader,
		        userResponse, StandardResponse.class);

		if (updateStatus.getStatus().equalsIgnoreCase(FAILED.getVariableName())) {
			statusMessageDTO.setStatus(Status.FAILURE);
		}

		return statusMessageDTO;
	}

	private void isValidPasswordInIS(UserDTO userDTO) {
		// Authenticate with IS to validate current password

		String authenticateUrlContext = ConnectionConstants.USER_URL_CTX + "/"
		        + "authenticate";

		// Set User DTO of IS for authentication
		User user = new User();
		user.setUserName(userDTO.getUserName());
		user.setPassword(userDTO.getPassword());
		user.setTenantDomain(userDTO.getDomain());

		StandardResponse authenticationStatus = client.post(
		        authenticateUrlContext, defaultBasicAuthHeader, user,
		        StandardResponse.class);

		if (authenticationStatus.getStatus().equalsIgnoreCase(
		        FAILED.getVariableName())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        CommonConstants.USER);
		}

	}

	private EntitySearchDTO constructEntitySearchDTO(UserDTO userDTO,
	        DomainDTO domainDTO) {
		// Initialize and set Field Map DTOS
		List<FieldMapDTO> fieldMapDTOs = new ArrayList<FieldMapDTO>();

		// Set User name Field Map DTO
		FieldMapDTO userNameFieldMapDTO = new FieldMapDTO();
		userNameFieldMapDTO.setKey(UMDataFields.USER_NAME.getVariableName());
		userNameFieldMapDTO.setValue(userDTO.getUserName());

		fieldMapDTOs.add(userNameFieldMapDTO);

		// Set EntitySearchDTO
		EntitySearchDTO entitySearchDTO = new EntitySearchDTO();
		entitySearchDTO.setDomain(domainDTO);
		entitySearchDTO.setFieldValues(fieldMapDTOs);
		EntityTemplateDTO userEntityTemplate = new EntityTemplateDTO();

		PlatformEntityTemplateDTO userTemplate = platformEntityTemplateService
		        .getPlatformEntityTemplate(setEntityType()
		                .getPlatformEntityType());
		userEntityTemplate.setEntityTemplateName(userTemplate
		        .getPlatformEntityTemplateName());
		entitySearchDTO.setEntityTemplate(userEntityTemplate);
		entitySearchDTO.setPlatformEntity(setEntityType());

		return entitySearchDTO;

	}

	private void validateActiveUser(EntityDTO entity) {
		if (StringUtils.isNotBlank(entity.getEntityStatus().getStatusName())) {
			if (entity.getEntityStatus().getStatusName()
			        .equalsIgnoreCase(Status.ACTIVE.getStatus())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
				        CommonConstants.USER);
			}
		}

	}

	private EntityTemplateDTO getUserPlatformTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		PlatformEntityTemplateDTO globalEntityTemplateDTO = platformEntityTemplateService
		        .getPlatformEntityTemplate(USER.getVariableName());
		entityTemplateDTO.setEntityTemplateName(globalEntityTemplateDTO
		        .getPlatformEntityTemplateName());
		return entityTemplateDTO;
	}
}

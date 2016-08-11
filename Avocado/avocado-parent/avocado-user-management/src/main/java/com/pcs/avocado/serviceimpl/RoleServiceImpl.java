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

import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateResult;
import static com.pcs.avocado.enums.UMDataFields.DOMAIN_NAME;
import static com.pcs.avocado.enums.UMDataFields.NEW_ROLE_NAME;
import static com.pcs.avocado.enums.UMDataFields.ROLE;
import static com.pcs.avocado.enums.UMDataFields.ROLE_NAME;
import static com.pcs.avocado.enums.UMDataFields.USER_NAME;
import static com.pcs.avocado.isadapter.constants.ConnectionConstants.ROLE_URL_CTX;
import static com.pcs.avocado.isadapter.constants.ConnectionConstants.USER_URL_CTX;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.dto.SubscriptionContextHolder;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.service.SubscriptionProfileService;
import com.pcs.avocado.constant.CommonConstants;
import com.pcs.avocado.dto.PermissionGroupsDTO;
import com.pcs.avocado.dto.RoleDTO;
import com.pcs.avocado.dto.UserPermissionsDTO;
import com.pcs.avocado.isadapter.dto.Role;
import com.pcs.avocado.isadapter.dto.User;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.service.RoleService;

/**
 * 
 * This class is responsible for Role Service Implementation
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 9 January 2016
 * @since avocado-1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	private static final String INTERNAL = "Internal/";
	private static final String BLANK = "";
	private static final String SLASH = "/";
	private static final String UNDERSCORE = "_";
	private static final String QUESTION = "?";

	private static Logger LOGGER = LoggerFactory
			.getLogger(RoleServiceImpl.class);

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	@Qualifier("platformESBClient")
	private BaseClient platformESBClient;

	@Value("${platform.esb.roles}")
	private String roleEP;
	
	@Value("${platform.esb.roles.all}")
	private String getAllRolesEP;
	
	@Value("${platform.esb.roles.find}")
	private String findRoleEP;
	
	@Value("${platform.esb.role.delete}")
	private String deleteRoleEP;

	@Override
	public StatusMessageDTO saveRole(RoleDTO roleDto) {
		validateMandatoryFields(roleDto, DOMAIN_NAME);
		StatusMessageDTO response = platformESBClient.post(roleEP,
				getJwtToken(), roleDto, StatusMessageDTO.class);
		return response;
	}

	@Override
	public StatusMessageDTO updateRole(String roleName, String domainName,
			RoleDTO roleDTO) {
		roleDTO.setDomainName(domainName);
		roleDTO.setRoleName(roleName);
		roleDTO.setNewRoleName(roleName);

		StatusMessageDTO response = platformESBClient.put(roleEP,
				getJwtToken(), roleDTO, StatusMessageDTO.class);

		return response;
	}

	@Override
	public List<RoleDTO> getRoles(String domainName) {

		@SuppressWarnings("unchecked")
		List<RoleDTO> roles = platformESBClient.get(getAllRolesEP.replace("{domainName}", domainName),
				getJwtToken(), List.class, RoleDTO.class);

		if (CollectionUtils.isEmpty(roles)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		return roles;
	}

	@Override
	public RoleDTO getRole(String roleName, String domainName) {

		// If domain is blank, get the domain from logged in user
		if (StringUtils.isBlank(domainName)) {
			domainName = SubscriptionContextHolder.getContext()
					.getSubscription().getEndUserDomain();
		}

		if (StringUtils.isBlank(roleName)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					ROLE_NAME.getDescription());
		}

		RoleDTO roleDto = platformESBClient.get(findRoleEP.replace("{roleName}", roleName).replace("{domainName}", domainName), getJwtToken(),
				RoleDTO.class);
		return roleDto;
	}

	/*****************************/

	@Override
	public StatusMessageDTO deleteRole(String roleName, String domainName) {
		if (isBlank(roleName)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					CommonConstants.ROLE_NAME);
		}
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}

		StatusMessageDTO response = platformESBClient.delete(deleteRoleEP.replace("{roleName}", roleName).replace("tenantDomain", domainName),
				getJwtToken(), StatusMessageDTO.class);
		return response;
	}

	@Override
	public UserPermissionsDTO getPermissionsOfCurrentUser() {

		String domainName = subscriptionProfileService.getEndUserDomain();
		String userName = subscriptionProfileService.getEndUserName();

		String getUserURL = USER_URL_CTX + SLASH + userName + QUESTION
				+ "tenantDomain=" + domainName;
		User user = platformESBClient
				.get(getUserURL, getJwtToken(), User.class);

		if (user == null) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED,
					USER_NAME.getDescription());
		}

		List<String> roles = user.getRoles();

		if (CollectionUtils.isEmpty(roles)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
					ROLE.getDescription());
		}

		UserPermissionsDTO userPermissionsDTO = new UserPermissionsDTO();
		Set<String> permissionSet = new HashSet<String>();
		Set<String> roleNames = new HashSet<String>();
		String tenantWithUnderscore = domainName + UNDERSCORE;
		for (String roleName : roles) {

			// Avoiding the "Internal" roles from fetching permissions
			if (roleName.contains(INTERNAL)) {
				break;
			}

			String getRoleURL = ROLE_URL_CTX + SLASH + roleName + QUESTION
					+ "tenantDomain=" + domainName;

			Role role = null;
			try {
				role = platformESBClient.get(getRoleURL, getJwtToken(),
						Role.class);
			} catch (Exception e) {
				LOGGER.info("Was not able to get the role with name :{}",
						roleName);
				break;
			}
			if (role != null) {
				roleNames.add(roleName);
				List<String> permissions = role.getPermissions();
				if (CollectionUtils.isNotEmpty(permissions)) {
					/*
					 * Removing Domain Name from the pattern
					 * "domainName_resource/permission"
					 */
					// TODO uncomment after changing to Java 8
					/*
					 * permissions.forEach(permission ->
					 * permissionSet.add(permission
					 * .replace(tenantWithUnderscore, BLANK)));
					 */
					for (String permission : permissions) {
						permissionSet.add(permission.replace(
								tenantWithUnderscore, BLANK));
					}
				}
			}
		}

		validateResult(roleNames);
		userPermissionsDTO.setRoleNames(roleNames);
		userPermissionsDTO.setPermissions(permissionSet);
		return userPermissionsDTO;
	}

	private void validateSaveRoleDto(RoleDTO roleDto) {
		validateMandatoryFields(roleDto, ROLE_NAME);
		isValidRoleName(roleDto.getRoleName(), CommonConstants.ROLE_NAME);
		if (isBlank(roleDto.getDomainName())) {
			roleDto.setDomainName(subscriptionProfileService.getEndUserDomain());
		}
	}

	private void validateUpdateRoleDto(RoleDTO roleDto) {
		validateMandatoryFields(roleDto, ROLE_NAME, NEW_ROLE_NAME);
		if (!roleDto.getRoleName().equals(roleDto.getNewRoleName())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.ROLE_NAME_CANNOT_BE_UPDATED);
		}
		if (isBlank(roleDto.getDomainName())) {
			roleDto.setDomainName(subscriptionProfileService.getEndUserDomain());
		}
	}

	private Role roleDtoToRole(RoleDTO roleDto) {
		Role role = new Role();
		role.setName(roleDto.getRoleName());
		role.setTenantDomain(roleDto.getDomainName());
		role.setPermissions(createPermissions(roleDto));
		return role;
	}

	private List<String> createPermissions(RoleDTO role) {

		ArrayList<String> permissions = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(role.getResources())) {
			for (PermissionGroupsDTO resource : role.getResources()) {
				String resourceName = role.getDomainName() + UNDERSCORE
						+ resource.getResourceName();
				if (CollectionUtils.isNotEmpty(resource.getPermissions())) {
					for (String permission : resource.getPermissions()) {
						permissions.add(resourceName + SLASH + permission);
					}
				}
			}
		}
		return permissions;
	}

	private RoleDTO roleToRoleDto(Role role) {
		RoleDTO roleDto = new RoleDTO();
		roleDto.setRoleName(role.getName());
		roleDto.setDomainName(role.getTenantDomain());
		roleDto.setResources(createResources(role.getPermissions(),
				role.getTenantDomain()));
		return roleDto;
	}

	private List<PermissionGroupsDTO> createResources(
			Collection<String> permissions, String domainName) {

		HashMap<String, List<String>> resourceMap = new HashMap<String, List<String>>();
		if (CollectionUtils.isNotEmpty(permissions)) {
			String tenantWithUnderscore = domainName + UNDERSCORE;
			for (String permission : permissions) {

				String[] split = permission.split(SLASH);
				String domainAndResource = split[0];

				String resourceName = domainAndResource.replace(
						tenantWithUnderscore, BLANK);

				String permissionString = split[1];
				List<String> list = new ArrayList<String>();
				if (resourceMap.containsKey(resourceName)) {
					list = resourceMap.get(resourceName);
					list.add(permissionString);
				} else {
					list.add(permissionString);
				}
				resourceMap.put(resourceName, list);
			}
		}

		List<PermissionGroupsDTO> resources = new ArrayList<PermissionGroupsDTO>();
		for (String resouceName : resourceMap.keySet()) {
			PermissionGroupsDTO resource = new PermissionGroupsDTO();
			resource.setResourceName(resouceName);
			resource.setPermissions(resourceMap.get(resouceName));
			resources.add(resource);
		}
		return resources;
	}

	private void isValidRoleName(String roleName, String filed) {
		if (!roleName.matches("[a-zA-Z0-9\\s_-]{3,30}$")) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED, filed);
		} else if (!StringUtils.isAlphanumeric(String.valueOf(roleName
				.charAt(0)))) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED, filed);
		}
	}
}

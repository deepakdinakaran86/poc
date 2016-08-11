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

import static com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateResult;
import static com.pcs.alpine.enums.UMDataFields.NEW_ROLE_NAME;
import static com.pcs.alpine.enums.UMDataFields.ROLE;
import static com.pcs.alpine.enums.UMDataFields.ROLE_NAME;
import static com.pcs.alpine.enums.UMDataFields.USER_NAME;
import static com.pcs.alpine.isadapter.constants.ConnectionConstants.USER_URL_CTX;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.dto.PermissionGroupsDTO;
import com.pcs.alpine.dto.RoleDTO;
import com.pcs.alpine.dto.UserPermissionsDTO;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.isadapter.constants.ConnectionConstants;
import com.pcs.alpine.isadapter.dto.Role;
import com.pcs.alpine.isadapter.dto.User;
import com.pcs.alpine.rest.beans.StandardResponse;
import com.pcs.alpine.rest.client.AuthUtil;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.service.RoleService;
import com.pcs.alpine.service.UserService;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	private static final String INTERNAL = "Internal/";
	private static final String BLANK = "";
	private static final String SLASH = "/";
	private static final String UNDERSCORE = "_";

	private static Logger LOGGER = LoggerFactory
			.getLogger(RoleServiceImpl.class);

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("identityServerWrapperClient")
	private BaseClient client;

	private final Map<String, String> defaultBasicAuthHeader = AuthUtil
			.getDefaultBasicAuthHeaderMap();

	@Override
	public RoleDTO getRole(String roleName, String domainName, Boolean isParentDomain) {
		if (isBlank(roleName)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					CommonConstants.ROLE_NAME);
		}
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}
		
		isParentDomain(isParentDomain);

		String targetUrl = ConnectionConstants.ROLE_URL_CTX + SLASH + roleName
				+ "?" + "tenantDomain=" + domainName;
		Role role = client.get(targetUrl, defaultBasicAuthHeader, Role.class);
		if (StringUtils.isBlank(role.getName())) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		role.setTenantDomain(domainName);
		RoleDTO roleDto = roleToRoleDto(role);
		return roleDto;
	}

	private void isParentDomain(Boolean isParentDomain) {
		if (isParentDomain != null && isParentDomain) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public List<RoleDTO> getRoles(String domainName, Boolean isParentDomain) {
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}
		
		isParentDomain(isParentDomain);

		String targetUrl = ConnectionConstants.ROLE_URL_CTX + "?"
				+ "tenantDomain=" + domainName;

		ArrayList<RoleDTO> roleDtos = new ArrayList<RoleDTO>();

		@SuppressWarnings("unchecked")
		List<Role> roles = client.get(targetUrl, defaultBasicAuthHeader,
				List.class, Role.class);

		if (CollectionUtils.isEmpty(roles)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		for (Role role : roles) {
			role.setTenantDomain(domainName);
			roleDtos.add(roleToRoleDto(role));
		}
		return roleDtos;
	}

	@Override
	public StatusMessageDTO saveRole(RoleDTO roleDto) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(Status.SUCCESS);
		validateSaveRoleDto(roleDto);
		isParentDomain(roleDto);
		Role role = roleDtoToRole(roleDto);

		StandardResponse response = client.post(
				ConnectionConstants.ROLE_URL_CTX, defaultBasicAuthHeader, role,
				StandardResponse.class);

		if (response.getStatus().equalsIgnoreCase("failed")) {
			// throw new GalaxyException(response.getMessage());
		}
		return statusMessageDTO;
	}

	private void isParentDomain(RoleDTO roleDto) {
		if (roleDto.getIsParentDomain() != null && roleDto.getIsParentDomain()) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public StatusMessageDTO updateRole(RoleDTO roleDto) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(Status.SUCCESS);
		validateUpdateRoleDto(roleDto);
		isParentDomain(roleDto);
		Role role = roleDtoToRole(roleDto);

		StandardResponse response = client.put(
				ConnectionConstants.ROLE_URL_CTX, defaultBasicAuthHeader, role,
				StandardResponse.class);

		if (response.getStatus().equalsIgnoreCase("failed")) {
			// throw new GalaxyException(response.getMessage());
		}
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO deleteRole(String roleName, String domainName) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(Status.SUCCESS);
		if (isBlank(roleName)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					CommonConstants.ROLE_NAME);
		}
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}

		String targetUrl = ConnectionConstants.ROLE_URL_CTX + SLASH + roleName
				+ "?" + "tenantDomain=" + domainName;
		StandardResponse response = client.delete(targetUrl,
				defaultBasicAuthHeader, StandardResponse.class);
		if (response.getStatus().equalsIgnoreCase("failed")) {
			// throw new GalaxyException(response.getMessage());
		}
		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.alpine.service.RoleService#getPermissionsOfCurrentUser()
	 */
	@Override
	public UserPermissionsDTO getPermissionsOfCurrentUser() {

		String domainName = subscriptionProfileService.getEndUserDomain();
		String userName = subscriptionProfileService.getEndUserName();

		String getUserURL = USER_URL_CTX + SLASH + userName + "?"
				+ "tenantDomain=" + domainName;
		User user = client.get(getUserURL, defaultBasicAuthHeader, User.class);

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

			String getRoleURL = ConnectionConstants.ROLE_URL_CTX + SLASH
					+ roleName + "?" + "tenantDomain=" + domainName;

			Role role = null;
			try {
				role = client.get(getRoleURL, defaultBasicAuthHeader,
						Role.class);
			} catch (Exception e) {
				LOGGER.info("Was not able to get the role with name :{}",
						roleName);
				break;
			}
			roleNames.add(roleName);
			List<String> permissions = role.getPermissions();
			if (role != null && CollectionUtils.isNotEmpty(permissions)) {
				/*
				 * Removing Domain Name from the pattern
				 * "domainName_resource/permission"
				 */
				// TODO uncomment after changing to Java 8
				/*
				 * permissions.forEach(permission ->
				 * permissionSet.add(permission .replace(tenantWithUnderscore,
				 * BLANK)));
				 */
				for (String permission : permissions) {
					permissionSet.add(permission.replace(tenantWithUnderscore,
							BLANK));
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

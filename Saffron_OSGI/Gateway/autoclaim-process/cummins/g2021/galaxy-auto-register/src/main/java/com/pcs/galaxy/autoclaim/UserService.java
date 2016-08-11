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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.galaxy.dto.DeviceUser;
import com.pcs.galaxy.dto.DomainDTO;
import com.pcs.galaxy.dto.EntityDTO;
import com.pcs.galaxy.dto.PermissionGroupsDTO;
import com.pcs.galaxy.dto.RoleDTO;
import com.pcs.galaxy.dto.Status;
import com.pcs.galaxy.dto.StatusMessageDTO;
import com.pcs.galaxy.dto.UserDTO;
import com.pcs.galaxy.token.TokenProvider;

/**
 * AssignDevice
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(UserService.class);

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
	@Value("${autoclaim.user.permission}")
	private String permGroName;

	@Value("${autoclaim.avocado.create.user}")
	private String createUser;
	@Value("${autoclaim.emaillink}")
	private String emailLink;
	@Value("${autoclaim.avocado.user.permission}")
	private String userPermission;
	@Value("${autoclaim.alpine.user.permission}")
	private String alpinePermission;
	@Value("${autoclaim.avocado.user.role}")
	private String userRole;

	@Autowired
	@Qualifier("alpineClient")
	private BaseClient alpineClient;

	public void createUser(DeviceUser deviceUser, String domainName,
	        String sourceId) {
		LOGGER.info("Enter into create User sequence.");
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		PermissionGroupsDTO permissionGroup = preparePermissionGroupsDTO(
		        domainName, sourceId);
		StatusMessageDTO updateStatus = updatePermissionGroup(permissionGroup,
		        domainName);
		if (updateStatus.getStatus().equals(Status.SUCCESS)) {
			LOGGER.info("*** Permission Group Updated Successfully ***");
			if (deviceUser.isUserExisting()) {
				LOGGER.info("*** User Exists ***");
				RoleDTO role = getRole(deviceUser.getUsername(), domainName);
				if (role != null) {
					LOGGER.info("*** Role Fetched Successfully ***");
					RoleDTO updatedRoleDTO = updateRoleDTO(role,
					        permissionGroup, sourceId);
					StatusMessageDTO updateRole = updateRole(updatedRoleDTO);
					if (updateRole.getStatus().equals(Status.SUCCESS)) {
						LOGGER.info("*** Role Updated Successfully ***");
					}
				}
			} else {
				LOGGER.info("*** User Does not Exist ***");
				RoleDTO role = prepareRoleDTO(deviceUser.getUsername(),
				        domainName, sourceId);
				StatusMessageDTO roleStatus = createRole(role);
				if (roleStatus.getStatus().equals(Status.SUCCESS)) {
					LOGGER.info("*** Role Created Successfully ***");
					UserDTO user = deviceUserToUserDTO(deviceUser, domainName);
					alpineClient
					        .post(createUser, header, user, EntityDTO.class);
					LOGGER.info("*** User Created Successfully ***");
				}
			}
		}

	}

	private UserDTO deviceUserToUserDTO(DeviceUser deviceUser, String domainName) {
		UserDTO user = null;
		if (deviceUser != null) {
			user = new UserDTO();

			user.setUserName(deviceUser.getUsername());
			if (StringUtils.isBlank(deviceUser.getLastname())) {
				user.setLastName(deviceUser.getUsername());
			} else {
				user.setLastName(deviceUser.getLastname());
			}
			user.setEmailId(deviceUser.getEmail());
			user.setRoleName("[\"" + deviceUser.getUsername() + "\"]");
			user.setEmailLink(emailLink);
			user.setDomain(domainName);
			user.setActive(Boolean.TRUE);
		}
		return user;
	}

	private PermissionGroupsDTO preparePermissionGroupsDTO(String domainName,
	        String sourceId) {

		PermissionGroupsDTO permissionGroup = new PermissionGroupsDTO();
		permissionGroup.setResourceName(permGroName);
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		permissionGroup.setDomain(domain);
		List<String> permissions = new ArrayList<String>();
		permissions.add(sourceId.toLowerCase());
		permissionGroup.setPermissions(permissions);
		return permissionGroup;
	}

	private RoleDTO prepareRoleDTO(String userName, String domainName,
	        String sourceId) {

		RoleDTO role = new RoleDTO();
		role.setRoleName(userName);
		role.setDomainName(domainName);

		List<PermissionGroupsDTO> permissionGroups = new ArrayList<PermissionGroupsDTO>();
		permissionGroups.add(preparePermissionGroupsDTO(domainName, sourceId));

		role.setResources(permissionGroups);

		return role;
	}

	private RoleDTO updateRoleDTO(RoleDTO role,
	        PermissionGroupsDTO permissionGroup, String sourceId) {

		List<PermissionGroupsDTO> resources = role.getResources();
		for (PermissionGroupsDTO resource : resources) {
			if (resource.getResourceName().equalsIgnoreCase(
			        permissionGroup.getResourceName())) {
				resource.setResourceName(permGroName);
				resource.getPermissions().add(sourceId.toLowerCase());
			}
		}
		return role;
	}

	private StatusMessageDTO updatePermissionGroup(
	        PermissionGroupsDTO permissionGroup, String domainName) {

		permissionGroup.setNewResourceName(permissionGroup.getResourceName());
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		permissionGroup.setDomain(domain);
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);
		StatusMessageDTO status = alpineClient.put(alpinePermission, header,
		        permissionGroup, StatusMessageDTO.class);
		return status;

	}

	private StatusMessageDTO createRole(RoleDTO role) {
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);
		StatusMessageDTO status = alpineClient.post(userRole, header, role,
		        StatusMessageDTO.class);
		return status;
	}

	private RoleDTO getRole(String roleName, String domainName) {
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);
		String url = userRole + "/" + roleName + "?domain_name=" + domainName;
		RoleDTO role = alpineClient.get(url, header, RoleDTO.class);
		return role;
	}

	private StatusMessageDTO updateRole(RoleDTO role) {
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);
		String url = userRole + "/" + role.getRoleName() + "?domain_name="
		        + role.getDomainName();
		StatusMessageDTO status = alpineClient.put(url, header, role,
		        StatusMessageDTO.class);
		return status;
	}
}

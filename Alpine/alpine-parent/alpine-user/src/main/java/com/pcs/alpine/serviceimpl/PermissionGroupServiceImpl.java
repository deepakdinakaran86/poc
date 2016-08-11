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

import static com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED;
import static com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.enums.UMDataFields.FAILED;
import static com.pcs.alpine.enums.UMDataFields.PERMISSIONS;
import static com.pcs.alpine.enums.UMDataFields.RESOURCE_NAME;
import static com.pcs.alpine.enums.UMDataFields.RESOURCE_NEW_NAME;
import static com.pcs.alpine.enums.UMDataFields.SUBSCRIBE;
import static com.pcs.alpine.exception.AlpineUMErrorCodes.PERMISSION_DUPLICATED;
import static com.pcs.alpine.exception.AlpineUMErrorCodes.PERMISSION_GROUP_CREATION_FAILED;
import static com.pcs.alpine.exception.AlpineUMErrorCodes.PERMISSION_GROUP_SUBSCRIPTION_FAILED;
import static com.pcs.alpine.exception.AlpineUMErrorCodes.PERMISSION_GROUP_UPDATION_FAILED;
import static com.pcs.alpine.exception.AlpineUMErrorCodes.RESOURCE_UPDATE_ERROR;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.dto.PermissionGroupsDTO;
import com.pcs.alpine.isadapter.constants.ConnectionConstants;
import com.pcs.alpine.isadapter.dto.PermissionGroup;
import com.pcs.alpine.isadapter.dto.Subscription;
import com.pcs.alpine.rest.beans.StandardResponse;
import com.pcs.alpine.rest.client.AuthUtil;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.service.PermissionGroupService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.enums.Status;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author Daniela PCSEG191)
 * @date 25 Oct 2015
 * @since alpine-1.0.0
 */
@Service
public class PermissionGroupServiceImpl implements PermissionGroupService {

	@Autowired
	@Qualifier("identityServerWrapperClient")
	private BaseClient client;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	private final Map<String, String> defaultBasicAuthHeader = AuthUtil
	        .getDefaultBasicAuthHeaderMap();

	/**
	 * 
	 * @Description This method is used to create a permission group and
	 *              subscribe to the permission groups
	 * 
	 * @param PermissionGroupsDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO createPermissionGroup(
	        PermissionGroupsDTO permissionGroupsDTO) {

		// Validate the resource name and permission names
		validateInput(permissionGroupsDTO);

		// Check if domain exists in input else fetch from profile
		String domain = getDomain(permissionGroupsDTO.getDomain());

		isParentDomain(permissionGroupsDTO);

		Boolean resourcePresent = true;
		// Validate if same permission group exists in the domain
		try {
			Boolean isParentDomain = false;
			getPermissionGroup(permissionGroupsDTO.getResourceName(), domain,
			        isParentDomain);
		} catch (GalaxyException galaxyException) {
			if (galaxyException
			        .getErrorMessageDTO()
			        .getErrorCode()
			        .equalsIgnoreCase(
			                GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()
			                        .toString())) {
				resourcePresent = false;
			}
		}
		// same resource name exits
		if (resourcePresent) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
			        RESOURCE_NAME.getDescription());
		}

		// Construct the permissionGroup object
		PermissionGroup permissionGroup = getPermissionGroup(
		        permissionGroupsDTO, domain);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		// Creates permission in IS
		createPermissionIS(domain, permissionGroup);
		return statusMessageDTO;
	}

	private void isParentDomain(PermissionGroupsDTO permissionGroupsDTO) {
		if (permissionGroupsDTO.getIsParentDomain() != null
		        && permissionGroupsDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	/**
	 * 
	 * @Description This method is used to update a permission group
	 * 
	 * @param PermissionGroupsDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO updatePermissionGroup(
	        PermissionGroupsDTO permissionGroupsDTO) {

		validateMandatoryFields(permissionGroupsDTO, RESOURCE_NEW_NAME);
		// validate the input for mandatory fields
		validateInput(permissionGroupsDTO);

		String domain = getDomain(permissionGroupsDTO.getDomain());

		isParentDomain(permissionGroupsDTO);

		if (!permissionGroupsDTO.getNewResourceName().equalsIgnoreCase(
		        permissionGroupsDTO.getResourceName())) {
			// Resource name cannot be updated due to IS limitation
			throw new GalaxyException(RESOURCE_UPDATE_ERROR);

		}
		PermissionGroup permissionGroup = getPermissionGroup(
		        permissionGroupsDTO, domain);

		PermissionGroupsDTO dbPermissionGroup = getPermissionGroup(
		        permissionGroupsDTO.getResourceName(), domain, false);

		for (String uiPermissions : permissionGroup.getPermissions()) {
			if (dbPermissionGroup.getPermissions().contains(uiPermissions)) {
				throw new GalaxyException(PERMISSION_DUPLICATED);
			}
		}

		permissionGroup.getPermissions().addAll(
		        dbPermissionGroup.getPermissions());

		// Create resource/ group name in IS
		StandardResponse updateGroupResponse = client
		        .put(ConnectionConstants.PERMISSION_GROUPS_URL_CTX,
		                defaultBasicAuthHeader, permissionGroup,
		                StandardResponse.class);
		if (updateGroupResponse.getStatus().equalsIgnoreCase(
		        FAILED.getVariableName())) {
			throw new GalaxyException(PERMISSION_GROUP_UPDATION_FAILED);
		}

		// Subscribe to the domain
		Subscription subscription = getDomainSubscription(permissionGroup,
		        domain);
		subscription.setAction(SUBSCRIBE.getVariableName());
		StandardResponse subscriptionResponse = client.post(
		        ConnectionConstants.TENANT_SUBSCRIPTION_URL_CTX,
		        defaultBasicAuthHeader, subscription, StandardResponse.class);
		if (subscriptionResponse.getStatus().equalsIgnoreCase(
		        FAILED.getVariableName())) {
			throw new GalaxyException(PERMISSION_GROUP_SUBSCRIPTION_FAILED);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * 
	 * @Description This method is used to get a permission group
	 * 
	 * @param groupName
	 *            , domainName
	 * @return StatusMessageDTO
	 */
	@Override
	public PermissionGroupsDTO getPermissionGroup(String groupName,
	        String domainName, Boolean isParentDomain) {
		// Check if domain is present in input
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}
		// validate the input
		String urlContext = constructContext(groupName, domainName);

		isParentDomain(isParentDomain);

		// Fetch the permission group from IS
		PermissionGroup getPermissionResponse = client.get(urlContext,
		        defaultBasicAuthHeader, PermissionGroup.class);
		if (CollectionUtils.isEmpty(getPermissionResponse.getPermissions())) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return getPermissionGroupDTO(getPermissionResponse, groupName);
	}

	private void isParentDomain(Boolean isParentDomain) {
		if (isParentDomain != null && isParentDomain) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	/**
	 * 
	 * @Description This method is used to delete a permission group
	 * 
	 * @param groupName
	 *            , domainName
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO deletePermissionGroup(String groupName,
	        String domainName) {

		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}

		String urlContext = constructContext(groupName, domainName);
		// Delete the permission group from IS
		StandardResponse deletePermissionResponse = client.delete(urlContext,
		        defaultBasicAuthHeader, StandardResponse.class);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);

		if (deletePermissionResponse.getStatus().equalsIgnoreCase(
		        FAILED.getVariableName())) {
			statusMessageDTO.setStatus(Status.FAILURE);
			return statusMessageDTO;
		}
		return statusMessageDTO;
	}

	private String constructContext(String groupName, String domainName) {
		if (isBlank(groupName)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        RESOURCE_NAME.getDescription());
		}
		String urlContext = ConnectionConstants.PERMISSION_GROUPS_URL_CTX + "/"
		        + domainName + "_" + groupName;
		return urlContext;
	}

	private PermissionGroup getPermissionGroup(
	        PermissionGroupsDTO permissionGroupsDTO, String domain) {
		PermissionGroup permissionGroup = new PermissionGroup();

		// Append the domain to the group name
		String groupName = domain + "_" + permissionGroupsDTO.getResourceName();
		permissionGroup.setId(groupName);
		List<String> permissions = new ArrayList<>();

		for (String permission : permissionGroupsDTO.getPermissions()) {
			permissions.add(permission);
		}
		permissionGroup.setPermissions(permissions);
		return permissionGroup;
	}

	private String getDomain(DomainDTO domain) {
		String domainName = null;
		if (domain == null || domain.getDomainName() == null
		        || domain.getDomainName().isEmpty()) {
			domainName = subscriptionProfileService.getEndUserDomain();
		} else {
			domainName = domain.getDomainName();
		}
		return domainName;
	}

	private Subscription getDomainSubscription(PermissionGroup permissionGroup,
	        String domain) {
		Subscription subscription = new Subscription();
		subscription.setTenantDomain(domain);
		List<String> permissionGroupsIDs = new ArrayList<>();
		permissionGroupsIDs.add(permissionGroup.getId());
		subscription.setPermissionGroupIDs(permissionGroupsIDs);
		return subscription;
	}

	private PermissionGroupsDTO getPermissionGroupDTO(
	        PermissionGroup permissionGroup, String groupName) {
		PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO();
		permissionGroupsDTO.setResourceName(groupName);
		permissionGroupsDTO.setPermissions(permissionGroup.getPermissions());
		return permissionGroupsDTO;
	}

	private void createPermissionIS(String domain,
	        PermissionGroup permissionGroup) {
		// Create resource/ group name
		StandardResponse createGroupResponse = client
		        .post(ConnectionConstants.PERMISSION_GROUPS_URL_CTX,
		                defaultBasicAuthHeader, permissionGroup,
		                StandardResponse.class);
		if (createGroupResponse.getStatus().equalsIgnoreCase(
		        FAILED.getVariableName())) {
			// Delete the permission group
			deletePermissionGroup(permissionGroup.getId(), domain);
			throw new GalaxyException(PERMISSION_GROUP_CREATION_FAILED);
		}
		// Subscribe to the domain
		Subscription subscription = getDomainSubscription(permissionGroup,
		        domain);
		subscription.setAction(SUBSCRIBE.getVariableName());
		StandardResponse subscriptionResponse = client.post(
		        ConnectionConstants.TENANT_SUBSCRIPTION_URL_CTX,
		        defaultBasicAuthHeader, subscription, StandardResponse.class);
		if (subscriptionResponse.getStatus().equalsIgnoreCase(
		        FAILED.getVariableName())) {
			throw new GalaxyException(PERMISSION_GROUP_SUBSCRIPTION_FAILED);
		}
	}

	private void validateInput(PermissionGroupsDTO permissionGroupsDTO) {

		validateMandatoryFields(permissionGroupsDTO, RESOURCE_NAME);

		// Check if 1st char in resource name is a special char and check if
		// backslash is present
		isValidName(permissionGroupsDTO.getResourceName(),
		        RESOURCE_NAME.getDescription());

		// Check if 1st char in permission name is a special char
		if (permissionGroupsDTO.getPermissions() != null
		        && CollectionUtils.isNotEmpty(permissionGroupsDTO
		                .getPermissions())) {
			Set<String> set = new HashSet<String>();
			for (String pgName : permissionGroupsDTO.getPermissions()) {
				isValidPermissionName(pgName, PERMISSIONS.getDescription());
				Boolean duplicatePermission = false;
				if (!set.add(pgName)) {
					duplicatePermission = true;
				}
				// Check if permissions are duplicated
				if (duplicatePermission) {
					throw new GalaxyException(PERMISSION_DUPLICATED);
				}
			}
		} else {
			throw new GalaxyException(FIELD_DATA_NOT_SPECIFIED,
			        PERMISSIONS.getDescription());
		}
	}

	private void isValidPermissionName(String roleName, String filed) {
		if (!roleName.matches("[a-zA-Z0-9\\s_-]{3,40}$")) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED, filed);
		} else if (!StringUtils.isAlphanumeric(String.valueOf(roleName
		        .charAt(0)))) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED, filed);
		}
	}

	private void isValidName(String roleName, String filed) {
		if (!roleName.matches("[a-zA-Z0-9\\s_-]{3,30}$")) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED, filed);
		} else if (!StringUtils.isAlphanumeric(String.valueOf(roleName
		        .charAt(0)))) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED, filed);
		}
	}

	@Override
	public PermissionGroupsDTO getResources(String domainName,
	        Boolean isParentDomain) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		// If domain is not passed fetch from profile
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}

		isParentDomain(isParentDomain);
		// Fetch details from IS
		String subscriptionURL = ConnectionConstants.TENANT_SUBSCRIPTION_URL_CTX
		        + "/" + domainName;
		Subscription subscription = client.get(subscriptionURL,
		        defaultBasicAuthHeader, Subscription.class);

		if (CollectionUtils.isEmpty(subscription.getPermissionGroupIDs())) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return constructPermissionGroupFromSub(subscription);
	}

	private PermissionGroupsDTO constructPermissionGroupFromSub(
	        Subscription subscription) {
		PermissionGroupsDTO permissionGroupsDTO = new PermissionGroupsDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(subscription.getTenantDomain());
		permissionGroupsDTO.setDomain(domain);

		// remove the domain name prefix while returning to UI
		List<String> resourceList = new ArrayList<String>();
		String domainNamePrefix = domain.getDomainName() + "_";
		for (String resource : subscription.getPermissionGroupIDs()) {
			String replaced = resource.replace(domainNamePrefix, "");
			resourceList.add(replaced);
		}
		permissionGroupsDTO.setResources(resourceList);
		return permissionGroupsDTO;
	}

}

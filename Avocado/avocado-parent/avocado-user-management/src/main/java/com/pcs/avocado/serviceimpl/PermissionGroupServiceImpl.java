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
import static com.pcs.avocado.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.avocado.enums.UMDataFields.FAILED;
import static com.pcs.avocado.enums.UMDataFields.PERMISSIONS;
import static com.pcs.avocado.enums.UMDataFields.RESOURCE_NAME;
import static com.pcs.avocado.enums.UMDataFields.RESOURCE_NEW_NAME;
import static com.pcs.avocado.enums.UMDataFields.SUBSCRIBE;
import static com.pcs.avocado.exception.AvocadoUMErrorCodes.PERMISSION_DUPLICATED;
import static com.pcs.avocado.exception.AvocadoUMErrorCodes.PERMISSION_GROUP_CREATION_FAILED;
import static com.pcs.avocado.exception.AvocadoUMErrorCodes.PERMISSION_GROUP_SUBSCRIPTION_FAILED;
import static com.pcs.avocado.exception.AvocadoUMErrorCodes.PERMISSION_GROUP_UPDATION_FAILED;
import static com.pcs.avocado.exception.AvocadoUMErrorCodes.RESOURCE_UPDATE_ERROR;
import static org.apache.commons.lang.StringUtils.isAlpha;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.dto.SubscriptionContextHolder;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.dto.PermissionGroupsDTO;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.isadapter.constants.ConnectionConstants;
import com.pcs.avocado.isadapter.dto.PermissionGroup;
import com.pcs.avocado.isadapter.dto.Subscription;
import com.pcs.avocado.rest.beans.StandardResponse;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.service.PermissionGroupService;

/**
 * 
 * This class is responsible for service implementation of PermissionGroups
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 11 January 2016
 * @since avocado-1.0.0
 */
@Service
public class PermissionGroupServiceImpl implements PermissionGroupService {

	@Autowired
	@Qualifier("platformESBClient")
	private BaseClient platformESBClient;
	
	@Value("${platform.esb.permissions.create}")
	private String permissionCreateEP;
	
	@Value("${platform.esb.permissions.resources}")
	private String permissionResourcesEP;
	
	@Value("${platform.esb.permissions.permissionGroup}")
	private String permissionPermissionGroupEP;

	@Override
	public StatusMessageDTO createPermissionGroup(
			PermissionGroupsDTO permissionGroupsDTO) {
		validateCollection(PERMISSIONS, permissionGroupsDTO.getPermissions());

		StatusMessageDTO response = platformESBClient.post(
				permissionCreateEP, getJwtToken(), permissionGroupsDTO,
				StatusMessageDTO.class);

		return response;
	}

	@Override
	public PermissionGroupsDTO getResources(String domainName) {
		PermissionGroupsDTO PermissionGroupsDTO = platformESBClient.get(
				permissionResourcesEP.replace("{domainName}",domainName), getJwtToken(), PermissionGroupsDTO.class);
		return PermissionGroupsDTO;
	}

	@Override
	public PermissionGroupsDTO getPermissionGroup(String groupName,
			String domainName) {

		// If domain is blank, get the domain from logged in user
		if (StringUtils.isBlank(domainName)) {
			domainName = SubscriptionContextHolder.getContext()
					.getSubscription().getEndUserDomain();
		}

		if (StringUtils.isBlank(groupName)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					RESOURCE_NAME.getDescription());
		}

		PermissionGroupsDTO permissionGroupsDTO = platformESBClient.get(
				permissionPermissionGroupEP.replace("{domainName}",domainName).replace("{groupName}",groupName), getJwtToken(), PermissionGroupsDTO.class);

		return permissionGroupsDTO;
	}

	/***************************************/

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

	@Override
	public StatusMessageDTO updatePermissionGroup(
			PermissionGroupsDTO permissionGroupsDTO) {

		validateMandatoryFields(permissionGroupsDTO, RESOURCE_NEW_NAME);
		// validate the input for mandatory fields
		validateInput(permissionGroupsDTO);

		String domain = getDomain(permissionGroupsDTO.getDomain());

		// Validate if newResourceName exists
		if (!permissionGroupsDTO.getNewResourceName().equalsIgnoreCase(
				permissionGroupsDTO.getResourceName())) {
			PermissionGroupsDTO newResource = new PermissionGroupsDTO();
			Boolean isNewResourcePresent = true;
			try {
				newResource = getPermissionGroup(
						permissionGroupsDTO.getNewResourceName(), domain);
			} catch (GalaxyException galaxyException) {
				if (galaxyException
						.getErrorMessageDTO()
						.getErrorCode()
						.equalsIgnoreCase(
								GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
										.getCode().toString())) {
					isNewResourcePresent = false;
				}
			}
			// Resource name cannot be updated due to IS limitation
			if (!isNewResourcePresent) {
				throw new GalaxyException(RESOURCE_UPDATE_ERROR);
			}
			// Resource exists with the same name, update not allowed
			if (isNewResourcePresent) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
						RESOURCE_NAME.getDescription());
			}

		}
		PermissionGroup permissionGroup = getPermissionGroup(
				permissionGroupsDTO, domain);

		// Create resource/ group name in IS
		StandardResponse updateGroupResponse = null;
		if (updateGroupResponse.getStatus().equalsIgnoreCase(
				FAILED.getVariableName())) {
			throw new GalaxyException(PERMISSION_GROUP_UPDATION_FAILED);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO deletePermissionGroup(String groupName,
			String domainName) {

		if (isBlank(domainName)) {
			domainName = null;
		}

		String urlContext = constructContext(groupName, domainName);
		// Delete the permission group from IS
		StandardResponse deletePermissionResponse = null;

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
			domainName = null;
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

	private boolean isSpecialCharPresent(String s) {
		Boolean isSpecial = true;
		if (isAlpha(s) || isNumeric(s)) {
			isSpecial = false;
		}
		return isSpecial;
	}

	private boolean isCharStringPresent(String s, String c) {
		boolean p = s.contains(c);
		return p;
	}

	private void createPermissionIS(String domain,
			PermissionGroup permissionGroup) {
		// Create resource/ group name
		StandardResponse createGroupResponse = platformESBClient.put(
				ConnectionConstants.PERMISSION_GROUPS_URL_CTX, getJwtToken(),
				permissionGroup, StandardResponse.class);
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
		StandardResponse subscriptionResponse = null;
		if (subscriptionResponse.getStatus().equalsIgnoreCase(
				FAILED.getVariableName())) {
			throw new GalaxyException(PERMISSION_GROUP_SUBSCRIPTION_FAILED);
		}
	}

	private void validateInput(PermissionGroupsDTO permissionGroupsDTO) {

		validateMandatoryFields(permissionGroupsDTO, RESOURCE_NAME);

		// Check if 1st char in resource name is a special char and check if
		// backslash is present
		Boolean resourceNameInvalid = isSpecialCharPresent(permissionGroupsDTO
				.getResourceName().substring(0, 1));
		Boolean resouceNameWithBackslash = isCharStringPresent(
				permissionGroupsDTO.getResourceName(), "/");

		if (resourceNameInvalid || resouceNameWithBackslash) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
					RESOURCE_NAME.getDescription());
		}
		// Check if 1st char in permission name is a special char
		if (permissionGroupsDTO.getPermissions() != null
				&& CollectionUtils.isNotEmpty(permissionGroupsDTO
						.getPermissions())) {
			Set<String> set = new HashSet<String>();
			for (String pgName : permissionGroupsDTO.getPermissions()) {
				Boolean duplicatePermission = false;
				if (!set.add(pgName)) {
					duplicatePermission = true;
				}
				// Check if permissions are duplicated
				if (duplicatePermission) {
					throw new GalaxyException(PERMISSION_DUPLICATED);
				}
				Boolean pgNameInvalid = isSpecialCharPresent(pgName.substring(
						0, 1));
				Boolean pgNameWithBackslash = isCharStringPresent(pgName, "/");
				if (pgNameInvalid || pgNameWithBackslash) {
					throw new GalaxyException(
							GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
							PERMISSIONS.getDescription());
				}
			}
		}
	}

}

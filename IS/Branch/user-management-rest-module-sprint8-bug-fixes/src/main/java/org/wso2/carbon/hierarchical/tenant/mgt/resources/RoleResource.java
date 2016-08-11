/*
 *  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.hierarchical.tenant.mgt.resources;

import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationHandlerChain;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationStatus;
import org.wso2.carbon.user.api.Permission;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.authorization.DBConstants;
import org.wso2.carbon.user.core.util.DatabaseUtil;
import org.wso2.carbon.user.core.util.UserCoreUtil;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.Utils;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.mgt.permission.ManagementPermissionUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/roles")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource extends AbstractResource {
	private final static Log log = LogFactory.getLog(RoleResource.class);

	@POST
	public Response addRole(
			@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
			Role role) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking addRole, Role name is : " + role.getName());
		}
		try {
			AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
			authChain.addUserAuthHandler(authorization,
					Constants.USER_MGT_PERMISSION);
			authChain.addTenantAuthHandler(authorization, role.getTenantDomain());
			authChain.addPermissionAuthHandler(role.getPermissions(),
					role.getTenantDomain());
			AuthorizationStatus status = authChain.getAuthorizationStatus();
			if (!status.isAuthorized()) {
				return handleResponse(ResponseStatus.FORBIDDEN,
						status.getMessage());
			}
			UserRealm userRealm = getUserRealm(role.getTenantDomain());
			if (userRealm == null) {
				return handleResponse(ResponseStatus.INVALID,
						"invalid tenantDomain");
			}
			UserStoreManager userStoreManager = userRealm.getUserStoreManager();
			if (!userStoreManager.isExistingRole(role.getName())) {
				List<String> rolePermissions = role.getPermissions();
				Permission[] permissions = ManagementPermissionUtil
						.getRoleUIPermissions(role.getName(),
								getResourceIDs(rolePermissions));
				String[] users = null;
				if (role.getUsers() != null) {
					List<String> userList = role.getUsers();
					users = userList.toArray(new String[userList.size()]);
				}
				userStoreManager.addRole(role.getName(), users, permissions,
						false);
			} else {
				return handleResponse(ResponseStatus.INVALID,
						"role : " + role.getName() + " already exists");
			}
			return handleResponse(ResponseStatus.SUCCESS,
					"successfully added role : " + role.getName());
		} catch (Exception e) {
			String msg = "Error while adding role";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@PUT
	public Response updateRole(
			@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
			Role role) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking updateRole, Role name is : "
					+ role.getName());
		}
		try {
			AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
			authChain.addUserAuthHandler(authorization,
					Constants.USER_MGT_PERMISSION);
			authChain.addTenantAuthHandler(authorization, role.getTenantDomain());
			authChain.addPermissionAuthHandler(role.getPermissions(),
					role.getTenantDomain());
			AuthorizationStatus status = authChain.getAuthorizationStatus();
			if (!status.isAuthorized()) {
				return handleResponse(ResponseStatus.FORBIDDEN,
						status.getMessage());
			}
			updateRole(role);
			return handleResponse(ResponseStatus.SUCCESS,
					"successfully updated role : " + role.getName());
		} catch (Exception e) {
			String msg = "Error while updating role";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@DELETE
	@Path("{role-name}")
	public Response deleteRole(
			@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
			@PathParam("role-name") String roleName,
			@QueryParam("tenantDomain") String tenantDomain) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking deleteRole, Role name is : " + roleName);
		}
		try {
			AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
			authChain.addUserAuthHandler(authorization,
					Constants.USER_MGT_PERMISSION);
			authChain.addTenantAuthHandler(authorization, tenantDomain);
			AuthorizationStatus status = authChain.getAuthorizationStatus();
			if (!status.isAuthorized()) {
				return handleResponse(ResponseStatus.FORBIDDEN,
						status.getMessage());
			}
			if (tenantDomain == null) {
				return handleResponse(ResponseStatus.INVALID,
						"missing tenantDomain parameter");
			}
			UserRealm userRealm = getUserRealm(tenantDomain);
			if (userRealm == null) {
				return handleResponse(ResponseStatus.INVALID,
						"invalid tenantDomain");
			}
			userRealm.getUserStoreManager().deleteRole(roleName);
			return handleResponse(ResponseStatus.SUCCESS,
					"successfully delete role : " + roleName);
		} catch (Exception e) {
			String msg = "Error while deleting role";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@GET
	@Path("{role-name}")
	public Response getRole(
			@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
			@PathParam("role-name") String roleName,
			@QueryParam("tenantDomain") String tenantDomain) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking getRole, role name is : " + roleName);
		}
		try {
			AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
			authChain.addUserAuthHandler(authorization,
					Constants.USER_MGT_PERMISSION);
			authChain.addTenantAuthHandler(authorization, tenantDomain);
			AuthorizationStatus status = authChain.getAuthorizationStatus();
			if (!status.isAuthorized()) {
				return handleResponse(ResponseStatus.FORBIDDEN,
						status.getMessage());
			}
			if (tenantDomain == null) {
				return handleResponse(ResponseStatus.INVALID,
						"missing tenantDomain parameter");
			}
			UserRealm userRealm = getUserRealm(tenantDomain);
			if (userRealm == null) {
				return handleResponse(ResponseStatus.INVALID,
						"invalid tenantDomain");
			}
			if (userRealm.getUserStoreManager().isExistingRole(roleName)) {
				Role role = new Role();
				role.setName(roleName);
				List<String> rolePermissions = getRolePermissions(userRealm,
						roleName, tenantDomain);
				role.setPermissions(rolePermissions);
				return Response.ok(role).build();
			} else {
				return handleResponse(ResponseStatus.NOT_EXIST, "Given role '"
						+ roleName + "' " + "does not exist");
			}
		} catch (Exception e) {
			String msg = "Error while retrieving role";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@GET
	public Response getRoles(
			@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
			@QueryParam("tenantDomain") String tenantDomain,
			@QueryParam("userName") String userName) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking getRoleList, tenantDomain is : "
					+ tenantDomain);
		}
		try {
			AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
			authChain.addUserAuthHandler(authorization,
					Constants.USER_MGT_PERMISSION);
			authChain.addTenantAuthHandler(authorization, tenantDomain);
			AuthorizationStatus status = authChain.getAuthorizationStatus();
			if (!status.isAuthorized()) {
				return handleResponse(ResponseStatus.FORBIDDEN,
						status.getMessage());
			}
			if (tenantDomain == null) {
				return handleResponse(ResponseStatus.INVALID,
						"missing tenantDomain parameter");
			}
			UserRealm userRealm = getUserRealm(tenantDomain);
			if (userRealm == null) {
				return handleResponse(ResponseStatus.INVALID,
						"invalid tenantDomain");
			}
			String[] roles;
			if (userName != null && !"".equals(userName)) {
				roles = userRealm.getUserStoreManager().getRoleListOfUser(
						userName);
			} else {
				roles = userRealm.getUserStoreManager().getRoleNames();
			}
			List<Role> roleList = getRoleList(roles, userRealm, tenantDomain);
			return Response.ok().entity(roleList).build();
		} catch (Exception e) {
			String msg = "Error while retrieving roles";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	private void updateRole(Role role) throws Exception {
		try {
			PrivilegedCarbonContext.startTenantFlow();
			String tenantDomain = role.getTenantDomain();
			PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext
					.getThreadLocalCarbonContext();
			int tenantId = Utils.getTenantId(tenantDomain);
			carbonContext.setTenantDomain(tenantDomain);
			carbonContext.setTenantId(tenantId);
			List<String> newPermissions = role.getPermissions();
			List<String> oldPermissions = getRolePermissions(
					getUserRealm(tenantDomain), role.getName(), tenantDomain);
			List<String> updatedPermissions = getUpdatedPermissions(
					newPermissions, oldPermissions);
			ManagementPermissionUtil.updateRoleUIPermission(role.getName(),
					getResourceIDs(updatedPermissions));
		} finally {
			PrivilegedCarbonContext.endTenantFlow();
		}
	}

	private List<String> getUpdatedPermissions(List<String> newPermissions,
			List<String> oldPermissions) {
		List<String> updatedPermissions = new ArrayList<>(oldPermissions);
		for (String newPermission : newPermissions) {
			if (!oldPermissions.contains(newPermission)) {
				updatedPermissions.add(newPermission);
			}
		}
		return updatedPermissions;
	}

	private List<Role> getRoleList(String[] roles, UserRealm userRealm,
			String tenantDomain) {
		List<Role> roleList = new ArrayList<>();
		for (String name : roles) {
			if (!name.contains("Internal/")) {
				Role role = new Role();
				role.setName(name);
				role.setPermissions(getRolePermissions(userRealm, name,
						tenantDomain));
				roleList.add(role);
			}
		}
		return roleList;
	}

	private List<String> getRolePermissions(UserRealm userRealm,
			String roleName, String tenantDomain) {
		List<String> permissionList = null;
		try {
			List<String> lstPermissions = new ArrayList<>();
			List<String> resourceIds = getUIPermissionIds(Utils
					.getTenantId(tenantDomain));
			if (resourceIds != null) {
				for (String resourceId : resourceIds) {
					if (userRealm.getAuthorizationManager().isRoleAuthorized(
							roleName, resourceId,
							CarbonConstants.UI_PERMISSION_ACTION)) {
						lstPermissions.add(resourceId);
					}// authorization check up
				}// loop over resource list
			}// resource ID checkup
			String[] permissions = lstPermissions
					.toArray(new String[lstPermissions.size()]);
			String[] optimizedList = UserCoreUtil
					.optimizePermissions(permissions);
			if (log.isDebugEnabled()) {
				log.debug("Allowed UI Resources for Role: " + roleName);
				for (String resource : optimizedList) {
					log.debug("Resource: " + resource);
				}
			}
			permissionList = Arrays.asList(optimizedList);
		} catch (Exception e) {
			String msg = "Error while retrieving role permissions";
			log.error(msg, e);
		}
		return permissionList;
	}

	private String[] getResourceIDs(List<String> permissions) {
		return permissions.toArray(new String[permissions.size()]);
	}

	private List<String> getUIPermissionIds(int tenantId)
			throws UserStoreException {
		Connection dbConnection = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<String> resourceIds = new ArrayList<>();
		try {
			dbConnection = Utils.getDataSource(Constants.CARBON_DB)
					.getConnection();
			prepStmt = dbConnection
					.prepareStatement(DBConstants.GET_PERMISSION_SQL);
			prepStmt.setString(1, CarbonConstants.UI_PERMISSION_ACTION);
			prepStmt.setInt(2, tenantId);
			rs = prepStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					resourceIds.add(rs.getString(1));
				}
			}
			return resourceIds;
		} catch (Exception e) {
			throw new UserStoreException("Error! " + e.getMessage(), e);
		} finally {
			DatabaseUtil.closeAllConnections(dbConnection, rs, prepStmt);
		}
	}
}

/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * WSO2 Inc. licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.wso2.carbon.hierarchical.tenant.mgt.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationHandlerChain;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationStatus;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.User;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.UserClaim;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;

@Path("/users")
@Consumes({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends AbstractResource {
	private final static Log log = LogFactory.getLog(UserResource.class);

	@POST
	public Response addUser(
	        @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
	        User user) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking addUser, Username is : "
			        + user.getUserName());
		}
		try {
			AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
			authChain.addUserAuthHandler(authorization,
			        Constants.USER_MGT_PERMISSION);
			authChain.addTenantAuthHandler(authorization,
			        user.getTenantDomain());
			AuthorizationStatus status = authChain.getAuthorizationStatus();
			if (!status.isAuthorized()) {
				return handleResponse(ResponseStatus.FORBIDDEN,
				        status.getMessage());
			}
			String tenantDomain = user.getTenantDomain();
			UserRealm userRealm = getUserRealm(tenantDomain);
			if (userRealm == null) {
				return handleResponse(ResponseStatus.INVALID,
				        "invalid tenantDomain : " + tenantDomain);
			}
			UserStoreManager userStoreManager = userRealm.getUserStoreManager();
			String[] roles = user.getRoles().toArray(
			        new String[user.getRoles().size()]);
			Map<String, String> claims = getUserClaims(user.getUserClaims());
			userStoreManager.addUser(user.getUserName(), user.getPassword(),
			        roles, claims, null);
			return handleResponse(ResponseStatus.SUCCESS,
			        "successfully added user : " + user.getUserName());
		} catch (Exception e) {
			String msg = "Error while adding user";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@POST
	@Path("/authenticate")
	public Response authenticate(User user) {
		try {
			String tenantDomain = user.getTenantDomain();
			UserRealm userRealm = getUserRealm(tenantDomain);
			if (userRealm == null) {
				return handleResponse(ResponseStatus.INVALID,
				        "invalid tenantDomain : " + tenantDomain);
			}
			boolean authenticated = userRealm.getUserStoreManager()
			        .authenticate(user.getUserName(), user.getPassword());
			if (authenticated) {
				// authentication success.
				return handleResponse(ResponseStatus.SUCCESS,
				        "authentication sucessful");
			} else {
				return handleResponse(ResponseStatus.AUTHENTICATION_FAILED,
				        "authentication failed");
			}
		} catch (Exception e) {
			String msg = "Error while auhtenticate user";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@PUT
	public Response updateUser(
	        @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
	        User user) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking updateUser, Username is : "
			        + user.getUserName());
		}
		try {
			AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
			authChain.addUserAuthHandler(authorization,
			        Constants.USER_MGT_PERMISSION);
			authChain.addTenantAuthHandler(authorization,
			        user.getTenantDomain());
			AuthorizationStatus status = authChain.getAuthorizationStatus();
			if (!status.isAuthorized()) {
				return handleResponse(ResponseStatus.FORBIDDEN,
				        status.getMessage());
			}
			String tenantDomain = user.getTenantDomain();
			UserRealm userRealm = getUserRealm(tenantDomain);
			if (userRealm == null) {
				return handleResponse(ResponseStatus.INVALID,
				        "invalid tenantDomain : " + tenantDomain);
			}
			UserStoreManager userStoreManager = userRealm.getUserStoreManager();
			if (StringUtils.isNotEmpty(user.getPassword())) {
				userStoreManager.updateCredentialByAdmin(user.getUserName(),
				        user.getPassword());
			}
			updateRolesOfUser(user, userRealm);
			Map<String, String> claims = getUserClaims(user.getUserClaims());
			userStoreManager.setUserClaimValues(user.getUserName(), claims,
			        null);
			return handleResponse(ResponseStatus.SUCCESS,
			        "successfully updated user : " + user.getUserName());
		} catch (Exception e) {
			String msg = "Error while updating user";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@DELETE
	@Path("{user-name}")
	public Response deleteUser(
	        @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
	        @PathParam("user-name") String userName,
	        @QueryParam("tenantDomain") String tenantDomain) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking deleteUser, Username is : " + userName);
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
				        "invalid tenantDomain : " + tenantDomain);
			}
			userRealm.getUserStoreManager().deleteUser(userName);
			return handleResponse(ResponseStatus.SUCCESS,
			        "successfully deleted user : " + userName);
		} catch (Exception e) {
			String msg = "Error while adding user";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@GET
	@Path("{user-name}")
	public Response getUser(
	        @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
	        @PathParam("user-name") String userName,
	        @QueryParam("tenantDomain") String tenantDomain) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking getUserList, tenantDomain is : "
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
			UserStoreManager userStoreManager = userRealm.getUserStoreManager();
			if (userStoreManager.isExistingUser(userName)) {
				User user = new User();
				user.setUserName(userName);
				user.setTenantDomain(tenantDomain);
				user.setRoles(Arrays.asList(userStoreManager
				        .getRoleListOfUser(userName)));
				user.setUserClaims(getUserClaimList(userStoreManager
				        .getUserClaimValues(userName, null)));
				return Response.ok(user).build();
			} else {
				return handleResponse(ResponseStatus.NOT_EXIST,
				        "User does not exist");
			}
		} catch (Exception e) {
			String msg = "Error while retrieving user";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	@GET
	public Response getUserList(
	        @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
	        @QueryParam("tenantDomain") String tenantDomain,
	        @QueryParam("filter") String filter,
	        @QueryParam("roleName") String roleName) {
		if (log.isDebugEnabled()) {
			log.debug("----invoking getUserList, tenantDomain is : "
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
			UserStoreManager userStoreManager = userRealm.getUserStoreManager();
			String[] users;
			if (filter != null && !"".equals(filter)) {
				users = userStoreManager.listUsers(filter, -1);
			} else if (roleName != null && !"".equals(roleName)) {
				users = userStoreManager.getUserListOfRole(roleName);
			} else {
				users = userStoreManager.listUsers("*", -1);
			}
			List<User> userList = getUserList(users);
			return Response.ok(userList).build();
		} catch (Exception e) {
			String msg = "Error while retrieving users";
			log.error(msg, e);
			return handleResponse(ResponseStatus.FAILED, msg);
		}
	}

	private List<User> getUserList(String[] userNames) {
		List<User> userList = new ArrayList<>();
		for (String name : userNames) {
			User user = new User();
			user.setUserName(name);
			userList.add(user);
		}
		return userList;
	}

	private Map<String, String> getUserClaims(Collection<UserClaim> userClaims) {
		Map<String, String> claims = new HashMap<>();
		if (userClaims != null) {
			for (UserClaim userClaim : userClaims) {
				claims.put(userClaim.getUri(), userClaim.getValue());
			}
		}
		return claims;
	}

	private List<UserClaim> getUserClaimList(Claim[] claims) {
		List<UserClaim> userClaims = new ArrayList<>();
		for (Claim claim : claims) {
			UserClaim userClaim = new UserClaim();
			userClaim.setUri(claim.getClaimUri());
			userClaim.setValue(claim.getValue());
			userClaims.add(userClaim);
		}
		return userClaims;
	}

	private void updateRolesOfUser(User user, UserRealm userRealm)
	        throws UserStoreException {
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			String[] newRoles = user.getRoles().toArray(
			        new String[user.getRoles().size()]);
			String[] deletedRoles = getDeletedRoles(newRoles, userRealm,
			        user.getUserName());
			if (deletedRoles.length > 0) {
				userRealm.getUserStoreManager().updateRoleListOfUser(
				        user.getUserName(), deletedRoles, new String[0]);
			}
			userRealm.getUserStoreManager().updateRoleListOfUser(
			        user.getUserName(), new String[0], newRoles);
		}
	}

	private String[] getDeletedRoles(String[] newRoles, UserRealm userRealm,
	        String userName) throws UserStoreException {
		List<String> deletedRoles = new ArrayList<String>();
		// List<String> newRolesList = Arrays.asList(newRoles);
		String[] oldRoles = userRealm.getUserStoreManager().getRoleListOfUser(
		        userName);
		for (String oldRole : oldRoles) {
			if (!userRealm.getRealmConfiguration().getEveryOneRoleName()
			        .equalsIgnoreCase(oldRole)) {
				deletedRoles.add(oldRole);
			}
		}
		return deletedRoles.toArray(new String[deletedRoles.size()]);
	}
}

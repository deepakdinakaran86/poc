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
package org.wso2.carbon.hierarchical.tenant.mgt.authorization;

import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.Role;

/**
 * CarbonPermissionAuthorizationHandler is used to authorize the carbon
 * permissions provided in the permissionStrings. Tenant admin users will be
 * only be allowed to assign USER_MGT_PERMISSION to a particular user. Other
 * Carbon permissions will not be allowed.
 */
public class CarbonPermissionAuthorizationHandler implements
		AuthorizationHandler {

	private String userName;
	private Role role;

	public CarbonPermissionAuthorizationHandler(String userName, Role role) {
		this.userName = userName;
		this.role = role;
	}

	@Override
	public AuthorizationStatus getAuthorizationStatus() throws Exception {
		for (String permissionString : role.getPermissions()) {
			if (permissionString.contains(Constants.USER_MGT_PERMISSION)) {
				AuthorizationHandler userAuthHandler = new UserAuthorizationHandler(
						userName, Constants.ADMIN_PERMISSION_STRING);
				AuthorizationStatus status = userAuthHandler
						.getAuthorizationStatus();
				if (status.isAuthorized()) {
					continue;
				} else {
					return AuthorizationStatus.UNAUTHORIZED
							.setReason("user should have Admin "
									+ "permissions to add carbon user management permission");
				}
			}
			if (permissionString.contains(Constants.ADMIN_PERMISSION_STRING)) {
				return AuthorizationStatus.UNAUTHORIZED
						.setReason("Admin permissions except "
								+ "user management permission, "
								+ "are not allowed");
			}
			if (permissionString
					.contains(Constants.SUPER_ADMIN_PERMISSION_STRING)) {
				return AuthorizationStatus.UNAUTHORIZED
						.setReason("Super Admin permissions are "
								+ "not allowed");
			}
		}
		// All other non carbon permissions are authorized by this handler
		return AuthorizationStatus.AUTHORIZED;
	}
}

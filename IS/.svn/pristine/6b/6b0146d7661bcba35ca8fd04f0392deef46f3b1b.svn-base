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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

/**
 * UserAuthorizationHandler checks if the user has the given permission assigned.
 */
public class UserAuthorizationHandler implements AuthorizationHandler {

    private final static Log log = LogFactory.getLog(UserAuthorizationHandler.class);
    private String userName;
    private String permission;

    public UserAuthorizationHandler(String userName, String permission) {
        this.userName = userName;
        this.permission = permission;
    }

    @Override
    public AuthorizationStatus getAuthorizationStatus() throws Exception {

        String tenantDomain = MultitenantUtils.getTenantDomain(userName);
        String tenantLessUserName = MultitenantUtils.getTenantAwareUsername(userName);
        try {
            //get super tenant context and get realm service which is an osgi service
            RealmService realmService = (RealmService)
                    PrivilegedCarbonContext.getCurrentContext().
                            getOSGiService(RealmService.class);
            if (realmService != null) {
                int tenantId = realmService.getTenantManager().getTenantId(tenantDomain);
                if (tenantId == -1) {
                    log.error("Invalid tenant domain " + tenantDomain);
                    return AuthorizationStatus.UNAUTHORIZED.setReason("Invalid tenant domain " +
                                                                      tenantDomain);
                }
                //get tenant's user realm
                UserRealm userRealm = realmService.getTenantUserRealm(tenantId);
                //TODO: handle this properly.
                if (userRealm.getAuthorizationManager().
                        isUserAuthorized(tenantLessUserName,
                                         permission,
                                         CarbonConstants.UI_PERMISSION_ACTION)) {
                    return AuthorizationStatus.AUTHORIZED;
                } else {
                    return AuthorizationStatus.UNAUTHORIZED.setReason("User is not authorized for" +
                                                                      " the operation");
                }
            } else {
                return AuthorizationStatus.UNAUTHORIZED.setReason("Unknown error");
            }
        } catch (UserStoreException e) {
            return AuthorizationStatus.UNAUTHORIZED.setReason("Unknown error");
        }
    }
}

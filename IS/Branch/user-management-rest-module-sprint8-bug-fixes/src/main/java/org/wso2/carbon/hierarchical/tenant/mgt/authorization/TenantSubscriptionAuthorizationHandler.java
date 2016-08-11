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

import org.wso2.carbon.base.MultitenantConstants;
import org.wso2.carbon.hierarchical.tenant.mgt.Configuration;
import org.wso2.carbon.hierarchical.tenant.mgt.Utils;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.Subscription;
import org.wso2.carbon.hierarchical.tenant.mgt.permissions.TenantPermissionStore;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import java.util.List;

/**
 * TenantSubscriptionAuthorizationHandler checks if the permission groups the parent tenant
 * trying to subscribe the child tenant into, is a subset of the parents subscribed permission
 * groups. Since child tenant should subscribe to a subset of the parents subscribed permission
 * groups. Super tenant will be ignored.
 */
public class TenantSubscriptionAuthorizationHandler implements AuthorizationHandler {

    private String userName;
    private List<String> permissionGroupIds;
    private TenantPermissionStore permissionStore = Configuration.TENANT_PERMISSION_STORE;

    public TenantSubscriptionAuthorizationHandler(String userName,
                                                  List<String> permissionGroupIds) {
        this.userName = userName;
        this.permissionGroupIds = permissionGroupIds;
    }

    @Override
    public AuthorizationStatus getAuthorizationStatus() throws Exception {
        String usersTenantDomain = MultitenantUtils.getTenantDomain(userName);
        int usersTenantId = Utils.getTenantId(usersTenantDomain);
        if (usersTenantId == MultitenantConstants.SUPER_TENANT_ID) {
            return AuthorizationStatus.AUTHORIZED;
        }

        Subscription subscription = permissionStore.getTenantSubscription(usersTenantDomain);
        List<String> subscribedPermGroupIDs = subscription.getPermissionGroupIDs();
        for(String permGroupId : permissionGroupIds) {
            if(!subscribedPermGroupIDs.contains(permGroupId)) {
                String tenantLessUserName = MultitenantUtils.getTenantAwareUsername(userName);
                return AuthorizationStatus.UNAUTHORIZED.setReason("User " +tenantLessUserName
                        + "'s tenant domain " + usersTenantDomain + " is not subscribed to " +
                        "permissionGroupId " + permGroupId);
            }
        }

        return AuthorizationStatus.AUTHORIZED;
    }
}

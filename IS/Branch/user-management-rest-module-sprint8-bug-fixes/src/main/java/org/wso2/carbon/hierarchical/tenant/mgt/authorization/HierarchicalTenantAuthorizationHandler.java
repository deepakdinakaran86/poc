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
import org.wso2.carbon.base.MultitenantConstants;
import org.wso2.carbon.hierarchical.tenant.mgt.Utils;
import org.wso2.carbon.hierarchical.tenant.mgt.hierarchy.TenantHierarchyManager;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

/**
 * HierarchicalTenantAuthorizationHandler is responsible for verifying if the Tenant which the user
 * belongs in is authorized to access the Tenant specified by the given tenantDomain. This
 * considers the Tenant hierarchy relationship.
 */
public class HierarchicalTenantAuthorizationHandler implements AuthorizationHandler {

    private TenantHierarchyManager tenantHierarchyManager = new TenantHierarchyManager();
    private final static Log log = LogFactory.getLog(HierarchicalTenantAuthorizationHandler.class);
    private String userName;
    private String tenantDomain;

    public HierarchicalTenantAuthorizationHandler(String userName, String tenantDomain) {
        this.userName = userName;
        this.tenantDomain = tenantDomain;
    }

    @Override
    public AuthorizationStatus getAuthorizationStatus() throws Exception {

        String userTenantDomain = MultitenantUtils.getTenantDomain(userName);
        try {
            int tenantId = Utils.getTenantId(tenantDomain);
            int userTenantId = Utils.getTenantId(userTenantDomain);
            /*if (tenantHierarchyManager.isParentAssociated(tenantId, parentTenantId)) {
                return AuthorizationStatus.AUTHORIZED;
            } else {
                return AuthorizationStatus.UNAUTHORIZED.setReason("Tenant parent Is not " +
                                                                  "associated");
            }*/
			if (tenantId == userTenantId
					|| userTenantId == MultitenantConstants.SUPER_TENANT_ID) {
				return AuthorizationStatus.AUTHORIZED;
			} else {
				return AuthorizationStatus.UNAUTHORIZED
						.setReason("Logged in user does not belong to the "
								+ "given tenant domain");
			}
        } catch (Exception e) {
            log.error("Tenant of the user " + userName + " is not authorized ", e);
            return AuthorizationStatus.UNAUTHORIZED.setReason("Tenant of the user " + userName +
                                                              " is not authorized ");
        }
    }
}

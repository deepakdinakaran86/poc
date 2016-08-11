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
package org.wso2.carbon.hierarchical.tenant.mgt.permissions;

import org.wso2.carbon.hierarchical.tenant.mgt.bean.PermissionGroup;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.Subscription;

/**
 * permission Store consists of permission groups, which are collection of permissions.
 * Tenants can be subscribed to permission groups.
 */
public interface TenantPermissionStore {

    public void subscribeTenant(String tenantDomain, String permissionGroupID)
            throws PermissionStoreException;

    public void unsubscribeTenant(String tenantDomain, String permissionGroupID)
            throws PermissionStoreException;

    public Subscription getTenantSubscription(String tenantDomain) throws PermissionStoreException;

    public void deleteTenantSubscription(String tenantDomain) throws PermissionStoreException;

    public void addPermissionGroup(PermissionGroup permissionGroup) throws PermissionStoreException;

    public PermissionGroup getPermissionGroup(String groupName) throws PermissionStoreException;

    public void deletePermissionGroup(String groupName) throws PermissionStoreException;
}

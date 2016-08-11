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

import org.wso2.carbon.hierarchical.tenant.mgt.Configuration;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.PermissionGroup;
import org.wso2.carbon.hierarchical.tenant.mgt.permissions.TenantPermissionStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PermissionAdditionAuthorizationHandler checks if the given permissionStrings are already
 * defined in the permission store. Since carbon permissions are not in the permission store they
 * will be ignored.
 */
public class PermissionAdditionAuthorizationHandler implements AuthorizationHandler {

    private TenantPermissionStore permissionStore = Configuration.TENANT_PERMISSION_STORE;
    //unsorted list of permissionStrings where permissionString is a groupName + permission
    private List<String> permissionStrings;
    private String tenantDomain;

    public PermissionAdditionAuthorizationHandler(List<String> permissionStrings, String tenantDomain) {
        this.permissionStrings = permissionStrings;
        this.tenantDomain = tenantDomain;
    }

    @Override
    public AuthorizationStatus getAuthorizationStatus() throws Exception {


        List<PermissionGroup> providedGroupedPermissions = createPermissionGroupList
                (permissionStrings);

        List<String> tenantSubscriptions = permissionStore.getTenantSubscription
                    (tenantDomain).getPermissionGroupIDs();

        for(PermissionGroup groupProvided : providedGroupedPermissions) {

            String providedPermissionGroupId = groupProvided.getId();
            if(!tenantSubscriptions.contains(providedPermissionGroupId)) {
                return AuthorizationStatus.UNAUTHORIZED.setReason("Tenant is not subscribed to " +
                        "given permission group " + providedPermissionGroupId);
            }
            PermissionGroup groupFromStore = permissionStore.getPermissionGroup(
                    providedPermissionGroupId);
            for(String permission: groupProvided.getPermissions()) {
                if(!groupFromStore.getPermissions().contains(permission)){
                    return AuthorizationStatus.UNAUTHORIZED.setReason("the provided " +
                            "permission \"" + permission +
                            "\" is not in the permission group \"" + providedPermissionGroupId +
                            "\"");
                }
            }
        }
        return AuthorizationStatus.AUTHORIZED;
    }

    private boolean isCarbonPermission(String permissionString) {
        if (permissionString.contains(Constants.ADMIN_PERMISSION_STRING)) {
            return true;
        } else if (permissionString.contains(Constants.SUPER_ADMIN_PERMISSION_STRING)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a List of PermissionGroups when a list of permissionStrings are given. In other
     * words convert a list of permissionStrings to the data structure in the permission store
     * The returned list of permissionGroups can be easily used to check with the permission
     * store to see if the permissions are already defined in the store.
     * @param permissionStringList an unsorted list of PermissionStrings.
     * @return List of PermissionGroups
     */
    private List<PermissionGroup> createPermissionGroupList(List<String> permissionStringList) {

        List<PermissionHolder> permissionHolders = new ArrayList<>();
        for (String permissionString : permissionStringList) {
            //ignore carbon permissions since they wont be stored in a permission store.
            if(isCarbonPermission(permissionString)) {
                continue;
            }
            permissionHolders.add(new PermissionHolder(permissionString));
        }
        Collections.sort(permissionHolders);
        List<PermissionGroup> permissionGroupList = new ArrayList<>();
        ArrayList<String> tempPermissions = new ArrayList<>();
        String tempGroupName;

        for (int i = 0; i < permissionHolders.size(); i++) {
            if (i != permissionHolders.size()-1 &&
                    permissionHolders.get(i).groupName.equals(permissionHolders.get(i+1).groupName)) {
                tempPermissions.add(permissionHolders.get(i).permission);
            } else {
                tempPermissions.add(permissionHolders.get(i).permission);
                tempGroupName = permissionHolders.get(i).groupName;
                PermissionGroup permissionGroup = new PermissionGroup();
                permissionGroup.setId(tempGroupName);
                permissionGroup.setPermissions(tempPermissions);
                permissionGroupList.add(permissionGroup);
                tempPermissions = new ArrayList<>();
            }
        }
        return permissionGroupList;
    }

    /**
     * PermissionString is split to get the groupName + permission. PermissionHolder can be used
     * to sort the permissions according to the groupName.
     */
    private class PermissionHolder implements Comparable<PermissionHolder> {
        String groupName;
        String permission;
        PermissionHolder(String permissionString) {
            if (permissionString.startsWith("/")) {
                permissionString = permissionString.substring(1);
            }
            //Assuming all permission strings can be split into group name and permission.
            String[] groupNameAndPermissions = permissionString.split(
                    Configuration.PERMISSION_STRING_SPLITTER, 2);
            this.groupName = groupNameAndPermissions[0];
            this.permission = groupNameAndPermissions[1];
        }
        @Override
        public int compareTo(PermissionHolder other) {
            return this.groupName.compareTo(other.groupName);
        }
    }
}

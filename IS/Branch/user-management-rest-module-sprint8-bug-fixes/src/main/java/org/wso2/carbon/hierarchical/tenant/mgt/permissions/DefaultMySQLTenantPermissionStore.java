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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.base.MultitenantConstants;
import org.wso2.carbon.hierarchical.tenant.mgt.Configuration;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.Utils;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.PermissionGroup;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.Subscription;
import org.wso2.carbon.user.core.util.DatabaseUtil;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultMySQLTenantPermissionStore implements TenantPermissionStore {

    private final static Log log = LogFactory.getLog(DefaultMySQLTenantPermissionStore.class);
    private DataSource dataSource;

    public DefaultMySQLTenantPermissionStore() {
        try {
            this.dataSource = Utils.getDataSource(Configuration.PERMISSION_STORE_DS);
        } catch (Exception e) {
            log.error("Cannot Get data source", e);
        }
    }

    @Override
    public void subscribeTenant(String tenant, String permissionGroupID)
            throws PermissionStoreException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.SUBSCRIBE_TENANT);
            int tenantId = Utils.getTenantId(tenant);
            if (tenantId == MultitenantConstants.INVALID_TENANT_ID) {
                throw new PermissionStoreException("tenant name is not in the System ");
            }
            statement.setInt(1, tenantId);
            statement.setString(2, permissionGroupID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new PermissionStoreException("Cannot add tenant subscription to permission " +
                                               "store ", e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }

    @Override
    public void unsubscribeTenant(String tenant, String permissionGroupID)
            throws PermissionStoreException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.UNSUBSCRIBE_TENANT);
            statement.setInt(1, Utils.getTenantId(tenant));
            statement.setString(2, permissionGroupID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new PermissionStoreException("Cannot unsubscribe tenant from permission " +
                                               "store ", e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }

    @Override
    public Subscription getTenantSubscription(String tenantDomain) throws
                                                                   PermissionStoreException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.GET_TENANT_SUBSCRIPTIONS);
            statement.setInt(1, Utils.getTenantId(tenantDomain));
            resultSet = statement.executeQuery();
            Subscription subscription = new Subscription();
            List<String> permissionGroupIDs = new ArrayList();
            while (resultSet.next()) {
                permissionGroupIDs.add(resultSet.getString(1));
            }
            subscription.setTenantDomain(tenantDomain);
            subscription.setPermissionGroupIDs(permissionGroupIDs);
            return subscription;
        } catch (Exception e) {
            throw new PermissionStoreException("Cannot get tenant subscription from permission " +
                                               "store ", e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, resultSet, statement);
        }
    }

    @Override
    public void deleteTenantSubscription(String tenant) throws PermissionStoreException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.REMOVE_TENANT_SUBSCRIPTION);
            statement.setInt(1, Utils.getTenantId(tenant));
            statement.executeUpdate();
        } catch (Exception e) {
            throw new PermissionStoreException("Cannot delete tenant subscription from " +
                                               "permission store ", e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }

    @Override
    public void addPermissionGroup(PermissionGroup permissionGroup)
            throws PermissionStoreException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<String> permissions = permissionGroup.getPermissions();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.ADD_PERMISSION);
            for (String permission : permissions) {
                statement.setString(1, permissionGroup.getId());
                statement.setString(2, permission);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PermissionStoreException("Cannot add permission group to permission store ",
                                               e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }

    @Override
    public PermissionGroup getPermissionGroup(String groupName) throws PermissionStoreException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.GET_PERMISSION_GROUP);
            statement.setString(1, groupName);
            resultSet = statement.executeQuery();
            List<String> permissions = new ArrayList<>();
            while (resultSet.next()) {
                permissions.add(resultSet.getString(3));
            }
            PermissionGroup permissionGroup = new PermissionGroup();
            permissionGroup.setId(groupName);
            permissionGroup.setPermissions(permissions);
            return permissionGroup;
        } catch (SQLException e) {
            throw new PermissionStoreException("Cannot get permission group from permission store ",
                                               e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, resultSet, statement);
        }
    }

    @Override
    public void deletePermissionGroup(String groupName) throws PermissionStoreException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.DELETE_PERMISSION_GROUP);
            statement.setString(1, groupName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PermissionStoreException("Cannot delete permission group from permission " +
                                               "store ", e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }
}

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
package org.wso2.carbon.hierarchical.tenant.mgt.hierarchy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.hierarchical.tenant.mgt.Configuration;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.Utils;
import org.wso2.carbon.user.core.util.DatabaseUtil;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TenantHierarchyManager {

    private DataSource dataSource;
    private final static Log log = LogFactory.getLog(TenantHierarchyManager.class);

    public TenantHierarchyManager() {
        try {
            this.dataSource = Utils.getDataSource(Configuration.HIERARCHICAL_TENANT_DS);
            this.createSuperTenantIfNotExist();
        } catch (Exception e) {
            log.error("Cannot create TenantHierarchyManager", e);
        }
    }

    public void associateTenant(int tenantID, int parentTenantID, boolean isRestricted)
            throws TenantHierarchyException {
        if (isTenantCreated(tenantID)) {
            throw new TenantHierarchyException("Tenant association is already created");
        }
        if (tenantID != MultitenantConstants.SUPER_TENANT_ID && !isTenantCreated(parentTenantID)) {
            throw new TenantHierarchyException("Cannot create tenant without creating its parent");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.ASSOCIATE_TENANT);
            statement.setInt(1, tenantID);
            //if tenant id is super tenant id, then parent id is also super tenant id.
            statement.setInt(2, tenantID == MultitenantConstants.SUPER_TENANT_ID ? tenantID :
                                parentTenantID);
            statement.setBoolean(3, isRestricted);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new TenantHierarchyException("The tenant cannot be created", e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }

    public void removeTenantAssociation(int tenantID) throws TenantHierarchyException {
        if (!isTenantCreated(tenantID)) {
            throw new TenantHierarchyException("Cannot remove Tenant association since it is " +
                                               "not created");
        }
        if (tenantID == MultitenantConstants.SUPER_TENANT_ID) {
            throw new TenantHierarchyException("Cannot remove Super tenant relationship");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.DISSOCIATE_TENANT);
            statement.setInt(1, tenantID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new TenantHierarchyException("The tenant relationship cannot be removed", e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }

    private boolean isTenantCreated(int tenantID) throws TenantHierarchyException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.CHECK_ASSOCIATION_CREATED);
            statement.setInt(1, tenantID);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new TenantHierarchyException(e);
        } finally {
            DatabaseUtil.closeAllConnections(connection, statement);
        }
    }

    public int getParentID(int tenantID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(Constants.GET_PARENT_TENANT);
            statement.setInt(1, tenantID);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(Constants.PARENT_TENANT_COLUMN_NAME);
        } finally {
            DatabaseUtil.closeAllConnections(connection, resultSet, statement);
        }
    }

    public boolean isParentAssociated(int tenantID, int parentTenantID) throws SQLException {
        if (parentTenantID == MultitenantConstants.SUPER_TENANT_ID) {
            return true;
        }
        while ((tenantID = getParentID(tenantID)) != MultitenantConstants.SUPER_TENANT_ID) {
            if (tenantID == parentTenantID) {
                return true;
            }
        }
        return false;
    }

    private void createSuperTenantIfNotExist() throws TenantHierarchyException {
        if (!isTenantCreated(MultitenantConstants.SUPER_TENANT_ID)) {
            this.associateTenant(MultitenantConstants.SUPER_TENANT_ID,
                                 MultitenantConstants.SUPER_TENANT_ID, true);
        }
    }
}

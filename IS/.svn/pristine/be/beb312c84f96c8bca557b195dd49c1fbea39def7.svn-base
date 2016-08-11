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
package org.wso2.carbon.hierarchical.tenant.mgt;

public class Constants {

    public static final String CONFIG_FILE_NAME = "tenant-hierarchy.properties";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String INITIAL_CONTEXT_FACTORY =
            "org.wso2.carbon.tomcat.jndi.CarbonJavaURLContextFactory";
    public static final String DEFAULT_HIERARCHICAL_TENANT_DS = "jdbc/WSO2HierarchicalTenantDB";
    public static final String CARBON_DB = "jdbc/WSO2CarbonDB";

    public static final String FAILED_STATUS = "failed";
    public static final String SUCCESS_STATUS = "success";

    public final static String SUBSCRIBE_ACTION = "subscribe";
    public final static String UNSUBSCRIBE_ACTION = "unsubscribe";

    public final static String DEFAULT_PERMISSION_STRING_SPLITTER = ":";

    //Carbon Permissions
    public static final String SUPER_ADMIN_PERMISSION_STRING = "/permission/protected";
    public static final String ADMIN_PERMISSION_STRING = "/permission/admin";
    public static final String USER_MGT_PERMISSION = "/permission/admin/configure/security/usermgt/";
    public static final String MODIFY_TENANT_PERMISSION =
            "/permission/protected/manage/modify/tenants";
    public static final String READ_TENANT_PERMISSION = "/permission/admin";

    //SQL Statements

    //Hierarchical Tenancy queries.
    public static final String ASSOCIATE_TENANT = "insert into tenant_association " +
                                                  "(tenant_id, parent_tenant_id, restriction) VALUES (?, ?, ?)";
    public static final String DISSOCIATE_TENANT = "delete from tenant_association where " +
                                                   "tenant_id == ?";
    public static final String GET_PARENT_TENANT = "select parent_tenant_id, " +
                                                   "restriction from tenant_association " +
                                                   "where tenant_id = ?";
    public static final String CHECK_ASSOCIATION_CREATED = "select association_id from " +
                                                           "tenant_association " +
                                                           "where tenant_id = ?";
    public static final String PARENT_TENANT_COLUMN_NAME = "parent_tenant_id";


    //DefaultMySQLTenantPermissionStore SQL queries.
    public static final String ADD_PERMISSION = "insert into permission_groups " +
                                                "(group_name, permission) VALUES (?, ?)";
    public static final String GET_PERMISSION_GROUP = "select * from permission_groups " +
                                                      "where group_name = ?";
	// delete all entries from both permission_group and tenant_subscription
	public static final String DELETE_PERMISSION_GROUP = "delete a.*, b.* from permission_groups a "
			+ "left join tenant_subscription b on a.group_name = b.group_name where a.group_name = ?";

    public static final String SUBSCRIBE_TENANT = "insert into tenant_subscription " +
                                                  "(tenant_id, group_name) VALUES (?, ?)";
    public static final String UNSUBSCRIBE_TENANT = "delete from tenant_subscription " +
                                                    "where tenant_id = ? and group_name = ?";
    public static final String REMOVE_TENANT_SUBSCRIPTION = "delete from tenant_subscription " +
                                                            "where tenant_id = ?";
    public static final String GET_TENANT_SUBSCRIPTIONS = "select group_name from tenant_subscription " +
                                                          "where tenant_id = ?";
}

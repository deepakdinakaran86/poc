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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.hierarchical.tenant.mgt.permissions.DefaultMySQLTenantPermissionStore;
import org.wso2.carbon.hierarchical.tenant.mgt.permissions.TenantPermissionStore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration of the hierarchical tenant management utility.
 */
public class Configuration {

    public static String HIERARCHICAL_TENANT_DS;
    public static String PERMISSION_STORE_DS;
    public static TenantPermissionStore TENANT_PERMISSION_STORE;
    public static String PERMISSION_STRING_SPLITTER;
    private final static Log log = LogFactory.getLog(Configuration.class);

    static {
        try {
            readConfigs();
        } catch (Exception e) {
            log.error("Failed to create the Tenant Hierarchy Configuration.", e);
        }
    }

    private static void readConfigs() throws Exception {

        Properties props = getProperties();
        HIERARCHICAL_TENANT_DS = props.getProperty("hierarchical_tenant_ds",
                Constants.DEFAULT_HIERARCHICAL_TENANT_DS);
        PERMISSION_STORE_DS = props.getProperty("permission_store_ds",
                Constants.DEFAULT_HIERARCHICAL_TENANT_DS);
        PERMISSION_STRING_SPLITTER = props.getProperty("permission_string_splitter",
                Constants.DEFAULT_PERMISSION_STRING_SPLITTER);

        String tps = props.getProperty("tenant_permission_store");
        if(tps != null) {
            Class tpsClass = Class.forName(tps);
            TENANT_PERMISSION_STORE = (TenantPermissionStore) tpsClass.newInstance();
        } else {
            TENANT_PERMISSION_STORE = new DefaultMySQLTenantPermissionStore();
        }
    }

    private static Properties getProperties() throws IOException {
        Properties prop = new Properties();
        String propFileName = Constants.CONFIG_FILE_NAME;
        InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream
                (propFileName);
        prop.load(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propFileName + "' not found" +
                    " in the classpath");
        }
        return prop;
    }
}

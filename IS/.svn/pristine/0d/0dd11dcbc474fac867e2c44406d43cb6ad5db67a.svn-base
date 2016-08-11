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

import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.user.core.service.RealmService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Hashtable;

public class Utils {

    public static int getTenantId(String tenantDomain) throws Exception {
        PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext.
                getThreadLocalCarbonContext();
        RealmService realmService = (RealmService) carbonContext.
                getOSGiService(RealmService.class);
        return realmService.getTenantManager().getTenantId(tenantDomain);

    }

    public static String getTenantDomain(int tenantId) throws Exception {
        try {
            PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext.
                    getThreadLocalCarbonContext();
            RealmService realmService = (RealmService) carbonContext.
                    getOSGiService(RealmService.class);
            return realmService.getTenantManager().getTenant(tenantId).getDomain();
        } catch (Exception e) {
            throw new Exception("Cannot get tenant ID", e);
        }
    }

    public static DataSource getDataSource(String lookupName) throws NamingException,
                                                                     SQLException {
        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, Constants.INITIAL_CONTEXT_FACTORY);
        Context initContext = new InitialContext(environment);
        return (DataSource) initContext.lookup(lookupName);
    }
}

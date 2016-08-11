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
package org.wso2.carbon.hierarchical.tenant.mgt.authentication;

import org.apache.axiom.om.util.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import java.util.ArrayList;
import java.util.TreeMap;

public class BasicAuthHandler implements AuthenticationHandler {
    private final static Log log = LogFactory.getLog(BasicAuthHandler.class);

    @Override
    public boolean isAuthenticated(Message message, ClassResourceInfo classResourceInfo) {
        try {
            //extract authorization header and authenticate.

            //get the map of protocol headers
            TreeMap protocolHeaders = (TreeMap) message.get(Message.PROTOCOL_HEADERS);
            //get the value for Authorization Header
            ArrayList authzHeaders = (ArrayList) protocolHeaders.get(Constants.AUTHORIZATION_HEADER);
            if (authzHeaders != null) {
                //get the authorization header value, if provided
                String authzHeader = (String) authzHeaders.get(0);

                //decode it and extract username and password
                byte[] decodedAuthHeader = Base64.decode(authzHeader.split(" ")[1]);
                String authHeader = new String(decodedAuthHeader);
                String userName = authHeader.split(":")[0];
                String password = authHeader.split(":")[1];
                if (userName != null && password != null) {
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
                                return false;
                            }
                            //get tenant's user realm
                            UserRealm userRealm = realmService.getTenantUserRealm(tenantId);
                            boolean authenticated = userRealm.getUserStoreManager().
                                    authenticate(tenantLessUserName, password);
                            if (authenticated) {
                                //authentication success. set the username for authorization header
                                //and proceed the REST call
                                authzHeaders.set(0, userName);
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } catch (UserStoreException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("Error while trying to authenticate user", e);
            return false;
        }
    }
}

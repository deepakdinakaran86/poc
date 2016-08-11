/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.hierarchical.tenant.mgt.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.authentication.AuthenticationHandler;
import org.wso2.carbon.hierarchical.tenant.mgt.authentication.BasicAuthHandler;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.StandardResponse;

import javax.ws.rs.core.Response;

public class AuthenticationFilter implements RequestHandler {

    private static Log log = LogFactory.getLog(AuthenticationFilter.class);

    public Response handleRequest(Message message, ClassResourceInfo classResourceInfo) {
        AuthenticationHandler authenticationHandler = new BasicAuthHandler();
        boolean isAuthenticated = authenticationHandler.isAuthenticated(message, classResourceInfo);
        if (isAuthenticated) {
            return null;
        } else {
            log.error("Failed authentication");
            return Response.status(401).
                    entity(new StandardResponse(Constants.FAILED_STATUS,
                                                "failed authentication")).build();
        }
    }
}

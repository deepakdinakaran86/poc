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
package org.wso2.carbon.hierarchical.tenant.mgt.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationHandlerChain;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationStatus;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.Subscription;
import org.wso2.carbon.hierarchical.tenant.mgt.permissions.DefaultMySQLTenantPermissionStore;
import org.wso2.carbon.hierarchical.tenant.mgt.permissions.TenantPermissionStore;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tenant-subscription")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class TenantSubscriptionResource extends AbstractResource {

    private final static Log log = LogFactory.getLog(TenantSubscriptionResource.class);
    private final TenantPermissionStore store = new DefaultMySQLTenantPermissionStore();

    @POST
    public Response handleTenantSubscription(
            @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
            Subscription subscription) {
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            String action = subscription.getAction();
            String tenantDomain = subscription.getTenantDomain();
            List<String> permissionGroupIDs = subscription.getPermissionGroupIDs();
            //TODO : check for available permission groups
            if (action != null && !"".equals(action)) {
                switch (action) {
                    case Constants.SUBSCRIBE_ACTION:
                        for (String permissionGroup : permissionGroupIDs) {
                            store.subscribeTenant(tenantDomain, permissionGroup);
                        }
                        break;
                    case Constants.UNSUBSCRIBE_ACTION:
                        for (String permissionGroup : permissionGroupIDs) {
                            store.unsubscribeTenant(tenantDomain, permissionGroup);
                        }
                        break;
                    default:
                        return handleResponse(ResponseStatus.INVALID,
                                              "unknown subscription action");
                }
            } else {
                return handleResponse(ResponseStatus.INVALID,
                                      "Missing  subscription action header");
            }
            return handleResponse(ResponseStatus.SUCCESS, "successfully " + action + "d tenant " +
                    tenantDomain);
        } catch (Exception e) {
            String msg = "Error while handling tenant subscription";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @GET
    @Path("{tenant-domain}")
    public Response getTenantSubscriptions(@HeaderParam(Constants.AUTHORIZATION_HEADER) String
                                                   authorization,
                                           @PathParam("tenant-domain") String tenantDomain) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking getTenantSubscriptions, tenantDomain is : " + tenantDomain);
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            Subscription subscription = store.getTenantSubscription(tenantDomain);
            return Response.ok(subscription).build();
        } catch (Exception e) {
            String msg = "Error while retrieving tenant subscription";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @DELETE
    @Path("{tenant-domain}")
    public Response deleteAllSubscription(
            @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
            @PathParam("tenant-domain") String tenantDomain) {
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            store.deleteTenantSubscription(tenantDomain);
            return handleResponse(ResponseStatus.SUCCESS, "successfully deleted tenant " +
                                                          "subscription for : " + tenantDomain);
        } catch (Exception e) {
            String msg = "Error while deleting tenant subscription for tenantDomain " +
                         tenantDomain + ".";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }
}

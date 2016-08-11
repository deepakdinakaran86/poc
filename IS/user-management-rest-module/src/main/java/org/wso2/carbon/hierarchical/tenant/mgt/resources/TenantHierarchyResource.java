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
import org.wso2.carbon.hierarchical.tenant.mgt.Utils;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationHandlerChain;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationStatus;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.TenantHierarchy;
import org.wso2.carbon.hierarchical.tenant.mgt.hierarchy.TenantHierarchyManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tenant-hierarchy")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class TenantHierarchyResource extends AbstractResource {

    private final static Log log = LogFactory.getLog(TenantHierarchyResource.class);
    private TenantHierarchyManager manager = new TenantHierarchyManager();

    @POST
    public Response setTenantAssociation(@HeaderParam(Constants.AUTHORIZATION_HEADER) String
                                                 authorization, TenantHierarchy hierarchy) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking relateTenantToParent, " +
                      "Tenant Domain is : " + hierarchy.getTenantDomain() + ", " +
                      "Parent Domain is : " + hierarchy.getParentTenantDomain());
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            int childID = Utils.getTenantId(hierarchy.getTenantDomain());
            int parentID = Utils.getTenantId(hierarchy.getParentTenantDomain());
            manager.associateTenant(childID, parentID, false);
            return handleResponse(ResponseStatus.SUCCESS, "successfully added tenant association " +
                                                          ": " + hierarchy.getTenantDomain());
        } catch (Exception e) {
            String msg = "Error while adding tenant hierarchy";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @GET
    @Path("/parent/")
    public Response getParentTenant(@HeaderParam(Constants.AUTHORIZATION_HEADER) String
                                            authorization,
                                    @QueryParam("tenant-domain") String childTenantDomain) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking getParentTenant, child tenant Domain is : " +
                      childTenantDomain);
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.READ_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            int parentId = manager.getParentID(Utils.getTenantId(childTenantDomain));
            return Response.ok(Utils.getTenantDomain(parentId)).build();
        } catch (Exception e) {
            String msg = "Error while retrieving parent tenant";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @DELETE
    @Path("{tenant-domain}")
    public Response unsetTenantAssociation(@HeaderParam(Constants.AUTHORIZATION_HEADER) String
                                                   authorization,
                                           @PathParam("tenant-domain") String tenantDomain) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking unsetTenantAssociation, Tenant domain is : " + tenantDomain);
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            manager.removeTenantAssociation(Utils.getTenantId(tenantDomain));
            return handleResponse(ResponseStatus.SUCCESS, "successfully removed tenant " +
                                                          "association for " + ": " + tenantDomain);
        } catch (Exception e) {
            String msg = "Error while removing tenant association ";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }
}

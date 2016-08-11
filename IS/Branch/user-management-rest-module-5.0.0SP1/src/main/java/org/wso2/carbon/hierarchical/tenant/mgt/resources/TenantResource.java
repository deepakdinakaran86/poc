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

import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.Utils;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationHandlerChain;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationStatus;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.Tenant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.stratos.common.beans.TenantInfoBean;
import org.wso2.carbon.tenant.mgt.services.TenantMgtAdminService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/tenants")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class TenantResource extends AbstractResource {
    private final static Log log = LogFactory.getLog(TenantResource.class);
    private TenantMgtAdminService tenantMgtAdminService = new TenantMgtAdminService();

    @POST
    public Response addTenant(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
                              Tenant tenant) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking addTenant, Tenant Domain is : " + tenant.getDomain());
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            tenantMgtAdminService.addTenant(createTenantInfoBean(tenant));
            return handleResponse(ResponseStatus.SUCCESS, "successfully added tenant " + ": " +
                                                          tenant.getDomain());
        } catch (Exception e) {
            String msg = "Error while adding new tenant";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @PUT
    public Response updateTenant(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
                                 Tenant tenant) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking updateTenant, Tenant Domain is : " + tenant.getDomain());
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            updateTenant(tenant);
            return handleResponse(ResponseStatus.SUCCESS, "successfully updated tenant " + ": " +
                                                          tenant.getDomain());
        } catch (Exception e) {
            String msg = "Error while updating tenant";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    private void updateTenant(Tenant tenant) throws Exception {
        try {
            PrivilegedCarbonContext.startTenantFlow();
            PrivilegedCarbonContext carbonContext =
                    PrivilegedCarbonContext.getThreadLocalCarbonContext();
            carbonContext.setTenantDomain(tenant.getDomain());
            carbonContext.setTenantId(Utils.getTenantId(tenant.getDomain()));
            if (!tenant.isActivate()) {
                tenantMgtAdminService.deactivateTenant(tenant.getDomain());
            } else {
            	tenantMgtAdminService.updateTenant(createTenantInfoBean(tenant));
            }
        } finally {
            PrivilegedCarbonContext.endTenantFlow();
        }
    }

    @GET
    @Path("{tenant-domain}")
    public Response getTenant(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
                              @PathParam("tenant-domain") String tenantDomain) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking getTenant, Tenant Domain is : " + tenantDomain);
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.READ_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            TenantInfoBean tenantInfoBean = tenantMgtAdminService.getTenant(tenantDomain);
            Tenant tenant = createTenantFromInfoBean(tenantInfoBean);
            return Response.ok(tenant).build();
        } catch (Exception e) {
            String msg = "Error while retrieving tenant";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @GET
    public Response getAllTenants(
            @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization) {
        if (log.isDebugEnabled()) {
            log.debug("----invoking getAllTenants");
        }
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.READ_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            TenantInfoBean[] tenantInfoBeans = tenantMgtAdminService.retrieveTenants();
            List<Tenant> tenants = new ArrayList<>();
            for (TenantInfoBean tenantInfoBean : tenantInfoBeans) {
                tenants.add(createTenantFromInfoBean(tenantInfoBean));
            }
            return Response.ok(tenants).build();
        } catch (Exception e) {
            String msg = "Error while retrieving all tenants";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    private TenantInfoBean createTenantInfoBean(Tenant tenant) {
        TenantInfoBean tenantInfoBean = new TenantInfoBean();
        tenantInfoBean.setTenantDomain(tenant.getDomain());
        tenantInfoBean.setActive(tenant.isActivate());
        tenantInfoBean.setAdmin(tenant.getAdminUserName());
        tenantInfoBean.setAdminPassword(tenant.getAdminPassword());
        tenantInfoBean.setEmail(tenant.getContactEmail());
        tenantInfoBean.setFirstname(tenant.getFirstName());
        tenantInfoBean.setLastname(tenant.getLastName());
        try {
            tenantInfoBean.setTenantId(Utils.getTenantId(tenant.getDomain()));
        } catch (Exception ignored) {
        }
        return tenantInfoBean;
    }

    private Tenant createTenantFromInfoBean(TenantInfoBean tenantInfoBean) {
        Tenant tenant = new Tenant();
        tenant.setDomain(tenantInfoBean.getTenantDomain());
        tenant.setActivate(tenantInfoBean.isActive());
        tenant.setContactEmail(tenantInfoBean.getEmail());
        tenant.setAdminUserName(tenantInfoBean.getAdmin());
        tenant.setAdminPassword(tenantInfoBean.getAdminPassword());
        tenant.setFirstName(tenantInfoBean.getFirstname());
        tenant.setLastName(tenantInfoBean.getLastname());
        return tenant;
    }
}

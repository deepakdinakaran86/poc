package org.wso2.carbon.hierarchical.tenant.mgt.authorization;

import java.util.ArrayList;
import java.util.List;

import org.wso2.carbon.hierarchical.tenant.mgt.bean.Role;

public class AuthorizationHandlerChain {

    private List<AuthorizationHandler> chain = new ArrayList<>();

    public AuthorizationStatus getAuthorizationStatus() throws Exception {
        for(AuthorizationHandler handler : chain) {
            AuthorizationStatus status = handler.getAuthorizationStatus();
            if(!status.isAuthorized()) {
                return status;
            }
        }
        return AuthorizationStatus.AUTHORIZED;
    }

    public void addTenantAuthHandler(String userName, String tenantDomain) {
        chain.add(new HierarchicalTenantAuthorizationHandler(userName, tenantDomain));
    }

    public void addPermissionAuthHandler(List<String> permissions, String tenantDomain) {
        chain.add(new PermissionAdditionAuthorizationHandler(permissions, tenantDomain));
    }

    public void addUserAuthHandler(String userName, String permission) {
        chain.add(new UserAuthorizationHandler(userName, permission));
    }

    public void addCarbonPermissionAuthHandler(String userName, Role role) {
        chain.add(new CarbonPermissionAuthorizationHandler(userName, role));
    }

    public void addTenantSubscriptionAuthHandler(String userName,
                                                 List<String> permissionGroupIds) {
        chain.add(new TenantSubscriptionAuthorizationHandler(userName, permissionGroupIds));
    }
}

package org.wso2.sample.user.store.manager.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.sample.user.store.manager.CustomUserStoreManager;

/**
 * @scr.component name="custom.authenticator.dscomponent" immediate=true
 * @scr.reference name="user.realmservice.default"
 * interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="1..1" policy="dynamic" bind="setRealmService"
 * unbind="unsetRealmService"
 * @scr.reference name="registry.service"
 * interface="org.wso2.carbon.registry.core.service.RegistryService"
 * cardinality="1..1" policy="dynamic" bind="setRegistryService" unbind="unsetRegistryService"
 */
public class CustomUserStoreMgtDSComponent {
    private static Log log = LogFactory.getLog(CustomUserStoreMgtDSComponent.class);
    private static RealmService realmService;
    private static RegistryService registryService;
    
    protected void activate(ComponentContext ctxt) {

        CustomUserStoreManager customUserStoreManager = new CustomUserStoreManager();
        ctxt.getBundleContext().registerService(UserStoreManager.class.getName(), customUserStoreManager, null);
        log.info("CustomUserStoreManager bundle activated successfully..");
    }

    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("Custom User Store Manager is deactivated ");
        }
    }

    public static RealmService getRealmService() {
        return realmService;
    }

    public void unsetRealmService(RealmService realmService) {
    	CustomUserStoreMgtDSComponent.realmService = null;
    }

    public void setRealmService(RealmService realmService) {
    	CustomUserStoreMgtDSComponent.realmService = realmService;
    }
    
    public void unsetRegistryService(RegistryService registryService) {
    	CustomUserStoreMgtDSComponent.registryService = null;
    }

    public void setRegistryService(RegistryService registryService) {
    	CustomUserStoreMgtDSComponent.registryService = registryService;
    }

    public static RegistryService getRegistryService() {
        return registryService;
    }
}

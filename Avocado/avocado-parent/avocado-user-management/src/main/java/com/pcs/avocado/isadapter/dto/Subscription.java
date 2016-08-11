package com.pcs.avocado.isadapter.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "action",
        "tenantDomain",
        "permissionGroupIDs"
})
@XmlRootElement(name = "subscription")
public class Subscription {

    @XmlElement(required = true)
    private String action;

    @XmlElement(required = true)
    private String tenantDomain;

    @XmlElement(required = true)
    private List<String> permissionGroupIDs;

    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getPermissionGroupIDs() {
        return permissionGroupIDs;
    }

    public void setPermissionGroupIDs(List<String> permissionGroupIDs) {
        this.permissionGroupIDs = permissionGroupIDs;
    }
}

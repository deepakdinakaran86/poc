package com.pcs.alpine.isadapter.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "userName", "password", "userClaims", "roles", "tenantDomain"})
@XmlRootElement(name = "user")
public class User {
	@XmlElement(required = true)
	private String userName;
	@XmlElement(required = true)
	private String password;
	@XmlElement(required = true)
	private String tenantDomain;
	@XmlElement(required = false)
	private Collection<UserClaim> userClaims;
	@XmlElement(required = false)
	private List<String> roles;

	public List<String> getRoles() {
		if (roles == null) {
			roles = new ArrayList<>();
		}
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTenantDomain() {
		return tenantDomain;
	}

	public void setTenantDomain(String tenantDomain) {
		this.tenantDomain = tenantDomain;
	}

	public Collection<UserClaim> getUserClaims() {
		return userClaims;
	}

	public void setUserClaims(Collection<UserClaim> userClaims) {
		this.userClaims = userClaims;
	}
}

/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.fms.web.manager.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.hazelcast.util.CollectionUtil;

/**
 * @author PCSEG296 RIYAS PH
 * @date JULY 2016
 * @since FMS1.0.0
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Token implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "scope")
	private String scope;

	@XmlElement(name = "token_type")
	private String tokenType;

	@XmlElement(name = "expires_in")
	private String expireIn;

	@XmlElement(name = "access_token")
	private String accessToken;

	@XmlElement(name = "refresh_token")
	private String refreshToken;

	private List<String> roleNames;

	private Map<String, List<String>> premissions;

	private String userName;

	private String userImage;

	private Tenant tenant;

	private Tenant currentTenant;

	private Boolean isSubClientSelected;

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(String expireIn) {
		this.expireIn = expireIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public List<String> getRoleNames() {
		if (CollectionUtil.isEmpty(roleNames)) {
			roleNames = new ArrayList<String>();
		}
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

	public Map<String, List<String>> getPremissions() {
		return premissions;
	}

	public void setPremissions(Map<String, List<String>> premissions) {
		this.premissions = premissions;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Tenant getCurrentTenant() {
		return currentTenant;
	}

	public void setCurrentTenant(Tenant currentTenant) {
		this.currentTenant = currentTenant;
	}

	public Boolean getIsSubClientSelected() {
		if (isSubClientSelected == null) {
			isSubClientSelected = Boolean.FALSE;
		}
		return isSubClientSelected;
	}

	public void setIsSubClientSelected(Boolean isSubClientSelected) {
		this.isSubClientSelected = isSubClientSelected;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

}

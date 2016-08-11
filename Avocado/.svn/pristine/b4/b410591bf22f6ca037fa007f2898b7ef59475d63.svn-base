package com.pcs.avocado.token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.hazelcast.util.CollectionUtil;

@XmlAccessorType(XmlAccessType.FIELD)
public class TokenInfoDTO implements Serializable {

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

	private List<String> premissions;

	private String userName;

	private Tenant tenant;
	
	private Integer errorCode;

	private String errorMessage;
	
	private Long genratedTime;
	
	private Long lastAccessedTime;
	
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

	public List<String> getPremissions() {
		if (CollectionUtil.isEmpty(premissions)) {
			premissions = new ArrayList<String>();
		}
		return premissions;
	}

	public void setPremissions(List<String> premissions) {
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

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getGenratedTime() {
		return genratedTime;
	}

	public void setGenratedTime(Long genratedTime) {
		this.genratedTime = genratedTime;
	}

	public Long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(Long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}


}

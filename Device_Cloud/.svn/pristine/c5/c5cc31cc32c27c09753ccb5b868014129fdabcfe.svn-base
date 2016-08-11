package com.pcs.deviceframework.devicemanager.session;

import java.io.Serializable;
import java.util.Date;

public class SessionInfo implements Serializable{

	private static final long serialVersionUID = -2131029385913755280L;
	private Date lastRequestTime;
	private final Object principal;
	private final Integer sessionId;
	private boolean expired;
	private Long sessionTimeOut;

	public SessionInfo(Object principal, Integer sessionId,
	        Date lastRequestDateTime) {
		this.sessionId = sessionId;
		this.principal = principal;
		this.lastRequestTime = lastRequestDateTime;
		if (this.lastRequestTime == null) {
			this.lastRequestTime = new Date();
		}
		this.expired = false;
	}

	public Date getLastRequestTime() {
		return lastRequestTime;
	}

	public void setLastRequestTime(Date lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Object getPrincipal() {
		return principal;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public Long getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(Long sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public void refreshLastRequest() {
		this.lastRequestTime = new Date();
	}
	
	public void expireSession() {
		this.expired = true;
	}
}

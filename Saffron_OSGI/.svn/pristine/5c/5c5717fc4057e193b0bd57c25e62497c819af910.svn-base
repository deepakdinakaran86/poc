package com.pcs.saffron.manager.authentication;

import com.pcs.saffron.manager.session.SessionInfo;


public class AuthenticationResponse {
	
	private Boolean authenticated;
	private String authenticationMessage;
	private SessionInfo sessionInfo;
	private String subscriptionKey = "Default Subscription";//TODO change as to fetch from the device details
	
	public Boolean isAuthenticated(){
		
		return authenticated;
	}
	
	public void setAuthenticationStatus(Boolean status){
		authenticated = status;
	}
	
	public String getAuthenticationMessage() {
		return authenticationMessage;
	}

	public void setAuthenticationMessage(String authenticationMessage) {
		this.authenticationMessage = authenticationMessage;
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public String getSubscriptionKey() {
		return subscriptionKey;
	}

	public void setSubscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}

}

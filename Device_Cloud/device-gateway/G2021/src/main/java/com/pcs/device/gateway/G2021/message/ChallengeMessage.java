package com.pcs.device.gateway.G2021.message;

public class ChallengeMessage {

	private Integer sessionId;
	private Integer unitId;
	private String subscriptionKey;
	private Integer sessionTimeout;
	/**
	 * @return the sessionId
	 */
	public Integer getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return the unitId
	 */
	public Integer getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	/**
	 * @return the subscriptionKey
	 */
	public String getSubscriptionKey() {
		return subscriptionKey;
	}
	/**
	 * @param subscriptionKey the subscriptionKey to set
	 */
	public void setSubscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}
	/**
	 * @return the sessionTimeout
	 */
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}
	/**
	 * @param sessionTimeout the sessionTimeout to set
	 */
	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	
	
}

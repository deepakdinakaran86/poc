package com.pcs.saffron.g2021.SimulatorEngine.CS.message;

public class ChallengeMessage extends ServerMessage {

	private static final long serialVersionUID = 1L;
	
	private Integer serverReceiveTimeout;
	private String subscriptionKey;
	
	public Integer getServerReceiveTimeout() {
		return serverReceiveTimeout;
	}
	public void setServerReceiveTimeout(Integer serverReceiveTimeout) {
		this.serverReceiveTimeout = serverReceiveTimeout;
	}
	public String getSubscriptionKey() {
		return subscriptionKey;
	}
	public void setSubscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}
}

package com.pcs.saffron.g2021.SimulatorEngine.CS.session;

/**
 * 
 * @author Santhosh
 *
 */

public class SessionInfo {
	
	private static SessionInfo session = null;
	
	private Integer sessionId;
	private Integer unitId;
	private String subscriptionKey;
	private Short dport;
	private Integer dataServerIp;
	private String dataServerDomainName;
	private Byte dataServerHostType;
	private Byte scoreCard;
	private Byte result;
	
	private SessionInfo() {	
	}
	
	public static SessionInfo getInstance(){
		if(session == null){
			session = new SessionInfo();			
		}
		return session;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getSubscriptionKey() {
		return subscriptionKey;
	}

	public void setSubscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}

	public Short getDport() {
		return dport;
	}

	public void setDport(Short dport) {
		this.dport = dport;
	}

	public Integer getDataServerIp() {
		return dataServerIp;
	}

	public void setDataServerIp(Integer dataServerIp) {
		this.dataServerIp = dataServerIp;
	}

	public String getDataServerDomainName() {
		return dataServerDomainName;
	}

	public void setDataServerDomainName(String dataServerDomainName) {
		this.dataServerDomainName = dataServerDomainName;
	}

	public Byte getDataServerHostType() {
		return dataServerHostType;
	}

	public void setDataServerHostType(Byte dataServerHostType) {
		this.dataServerHostType = dataServerHostType;
	}	

	public Byte getScoreCard() {
		return scoreCard;
	}

	public void setScoreCard(Byte scoreCard) {
		this.scoreCard = scoreCard;
	}

	public Byte getResult() {
		return result;
	}

	public void setResult(Byte result) {
		this.result = result;
	}
}

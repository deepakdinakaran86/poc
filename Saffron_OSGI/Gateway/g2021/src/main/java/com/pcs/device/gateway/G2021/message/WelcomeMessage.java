package com.pcs.device.gateway.G2021.message;

public class WelcomeMessage {

	private Integer serverTimestamp;
	private Short dataserverPort;
	private byte dataserverType;
	private String dataserverIP;
	private String dataserverDomainName;
	/**
	 * @return the serverTimestamp
	 */
	public Integer getServerTimestamp() {
		return serverTimestamp;
	}
	/**
	 * @param serverTimestamp the serverTimestamp to set
	 */
	public void setServerTimestamp(Integer serverTimestamp) {
		this.serverTimestamp = serverTimestamp;
	}
	/**
	 * @return the dataserverPort
	 */
	public Short getDataserverPort() {
		return dataserverPort;
	}
	/**
	 * @param dataserverPort the dataserverPort to set
	 */
	public void setDataserverPort(Short dataserverPort) {
		this.dataserverPort = dataserverPort;
	}
	/**
	 * @return the dataserverType
	 */
	public byte getDataserverType() {
		return dataserverType;
	}
	/**
	 * @param dataserverType the dataserverType to set
	 */
	public void setDataserverType(byte dataserverType) {
		this.dataserverType = dataserverType;
	}
	/**
	 * @return the dataserverIP
	 */
	public String getDataserverIP() {
		return dataserverIP;
	}
	/**
	 * @param dataserverIP the dataserverIP to set
	 */
	public void setDataserverIP(String dataserverIP) {
		this.dataserverIP = dataserverIP;
	}
	/**
	 * @return the dataserverDomainName
	 */
	public String getDataserverDomainName() {
		return dataserverDomainName;
	}
	/**
	 * @param dataserverDomainName the dataserverDomainName to set
	 */
	public void setDataserverDomainName(String dataserverDomainName) {
		this.dataserverDomainName = dataserverDomainName;
	}
	
	
}

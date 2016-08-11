package com.pcs.saffron.g2021.SimulatorEngine.CS.message;

import java.sql.Timestamp;

public class WelcomeMessage extends ServerMessage{	
	
	private static final long serialVersionUID = 1L;
	
	private Timestamp timestamp;
	private Short dPort;
	private Byte dataServerHostType;
	private Integer dataServerIp;
	private String dataServerDomainName;
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public Short getdPort() {
		return dPort;
	}
	public void setdPort(Short dPort) {
		this.dPort = dPort;
	}
	public Byte getDataServerHostType() {
		return dataServerHostType;
	}
	public void setDataServerHostType(Byte dataServerHostType) {
		this.dataServerHostType = dataServerHostType;
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
	

}

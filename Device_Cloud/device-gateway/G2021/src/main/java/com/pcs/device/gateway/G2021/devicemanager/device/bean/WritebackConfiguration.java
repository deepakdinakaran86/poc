/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.device.bean;

/**
 * @author pcseg171
 *
 */
public class WritebackConfiguration {
	
	private String sourceId;
	
	private String ip;
	
	private Integer connectedPort;
	
	private Integer writebackPort;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getConnectedPort() {
		return connectedPort;
	}

	public void setConnectedPort(Integer connectedPort) {
		this.connectedPort = connectedPort;
	}

	public Integer getWritebackPort() {
		return writebackPort;
	}

	public void setWritebackPort(Integer writebackPort) {
		this.writebackPort = writebackPort;
	}
	
	

}

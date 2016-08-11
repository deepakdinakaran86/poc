
package com.pcs.saffron.cipher.identity.bean;

public final class Gateway {
	private Boolean autoclaimEnabled;
	private String type;
	private String vendor;
	private String model;
	private String protocol;
	private String version;
	private Integer connectedPort;
	private String deviceIP;
	private Integer writebackPort;
	private String communicationProtocol;
	private static final String DELIMITER = "_";
	
	public String getId() {
		StringBuilder id = new StringBuilder();
		id.append(DELIMITER).append(vendor).append(DELIMITER).
				append(model).append(DELIMITER).
				append(protocol).append(DELIMITER).
				append(version);
		return id.toString();
	}
	public Boolean getAutoclaimEnabled() {
		return autoclaimEnabled;
	}
	public void setAutoclaimEnabled(Boolean autoclaimEnabled) {
		this.autoclaimEnabled = autoclaimEnabled;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}
	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getConnectedPort() {
		return connectedPort;
	}
	public void setConnectedPort(Integer connectedPort) {
		this.connectedPort = connectedPort;
	}
	public String getDeviceIP() {
		return deviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	public Integer getWritebackPort() {
		return writebackPort;
	}
	public void setWritebackPort(Integer writebackPort) {
		this.writebackPort = writebackPort;
	}
	public String getCommunicationProtocol() {
		return communicationProtocol;
	}
	public void setCommunicationProtocol(String communicationProtocol) {
		this.communicationProtocol = communicationProtocol;
	}
	
	
	
}

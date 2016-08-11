package com.pcs.device.gateway.teltonika.devicemanager.bean;

import java.io.Serializable;

public class DeviceProtocolType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7717749369255104024L;
	
	private String deviceType;
	private String protocol;
	
	
	
	public DeviceProtocolType(String protocol, String deviceType) {
		this.protocol = protocol;
		this.deviceType = deviceType;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Override
	public int hashCode() {
		return deviceType != null && protocol != null?deviceType.length()*protocol.length():((Double)Math.random()).intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}else{
			DeviceProtocolType _newObj = (DeviceProtocolType) obj;
			if((_newObj.getDeviceType() == null || _newObj.getDeviceType() == null) ||
					(this.protocol == null || this.deviceType == null)){
				return false;
			}else if((this.deviceType.equalsIgnoreCase(_newObj.getDeviceType())) && 
					(this.protocol.equalsIgnoreCase(_newObj.getProtocol()))){
				return true;
			}else{
				return false;
			}
			
		}
	}
	
}

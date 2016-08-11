/**
 * 
 */
package com.pcs.device.gateway.meitrack.devicemanager.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author pcseg171
 *
 */
@JsonAutoDetect
public class DeviceComnProtocol implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4592456551253466253L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

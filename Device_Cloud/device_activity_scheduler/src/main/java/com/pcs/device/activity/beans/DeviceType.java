/**
 * 
 */
package com.pcs.device.activity.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author pcseg171
 *
 */
@JsonAutoDetect
public class DeviceType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7333970774974943444L;
	
	private String vendor;
	private String name;
	private String model;
	
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

}

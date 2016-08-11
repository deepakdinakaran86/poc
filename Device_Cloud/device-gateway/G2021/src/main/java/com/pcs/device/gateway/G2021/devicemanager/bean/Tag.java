/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author pcseg171
 *
 */
@JsonAutoDetect
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1014632771396899991L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

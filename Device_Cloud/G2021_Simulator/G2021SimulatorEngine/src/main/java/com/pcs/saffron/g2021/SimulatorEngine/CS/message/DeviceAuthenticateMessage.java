package com.pcs.saffron.g2021.SimulatorEngine.CS.message;

/**
 * 
 * @author Santhosh
 *
 */

public class DeviceAuthenticateMessage extends G2021Message{
	
	private static final long serialVersionUID = 1L;
	private String ccKey;

	public DeviceAuthenticateMessage() {
		super();
	}

	public String getCcKey() {
		return ccKey;
	}

	public void setCcKey(String ccKey) {
		this.ccKey = ccKey;
	}
	
	
}

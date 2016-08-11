/**
 * 
 */
package com.pcs.saffron.g2021.SimulatorEngine.CS.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean comprises of configuration required to successfully connect to an external system/device
 * 
 * @author pcseg310, pcseg171
 * @see {@link ConnectorConfigurationBuilder}
 */
public class ConnectorConfiguration {
	
	private String name;
	private String vendor;
	private String model;
	private Integer port = 0;
	private List<Handler> deviceHandlers;
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public String getVendor() {
		return vendor;
	}
	
	/**
	 * @param vendor
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	/**
	 * @return
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return
	 */
	public List<Handler> getDeviceHandlers() {
		deviceHandlers = deviceHandlers == null ? deviceHandlers = new ArrayList<Handler>() : deviceHandlers;
		return deviceHandlers;
	}

	
	/**
	 * @param deviceHandler
	 */
	public void addDeviceHandler(Handler deviceHandler){
		this.getDeviceHandlers().add(deviceHandler);
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

}

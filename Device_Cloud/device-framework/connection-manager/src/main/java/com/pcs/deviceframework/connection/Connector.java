/**
 * 
 */
package com.pcs.deviceframework.connection;

import com.pcs.deviceframework.connection.config.ConnectorConfiguration;
import com.pcs.deviceframework.connection.exception.ConfigurationException;
import com.pcs.deviceframework.connection.exception.InvalidConnectorException;

/**
 * Defines the contract/responsibilities to manage the connections with external
 * systems (devices)
 *
 * @author pcseg171, pcseg310
 */
public interface Connector {
	
	public static final String CONNECTOR_MODE_TCP = "TCP";
	public static final String CONNECTOR_MODE_UDP = "UDP";
	
	/**
	 * Sets the configuration required to initialize the connector
	 * 
	 * @param configuration
	 */
	void setConfiguration(ConnectorConfiguration configuration);

	/**
	 * Retrieves the connector configuration
	 * 
	 * @return
	 */
	ConnectorConfiguration getConfiguration();

	/**
	 * Connects and listen to the configured system/device.This method is
	 * responsible to handle the underlying transport mechanism.
	 * 
	 * @param configuration
	 * @return
	 * @throws InvalidConnectorException
	 * @throws ConfigurationException
	 */
	boolean connect() throws ConfigurationException;
}

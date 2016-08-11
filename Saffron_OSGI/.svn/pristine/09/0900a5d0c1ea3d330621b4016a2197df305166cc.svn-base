/**
 * 
 */
package com.pcs.saffron.connectivity;

import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.exception.InvalidConnectorException;

/**
 * Defines the contract/responsibilities to manage the connections with external
 * systems (devices)
 *
 * @author pcseg171, pcseg310
 */
public interface Connector {
	public static final String CONNECTOR_MODE_TCP = "TCP_CONNECTOR";
	public static final String CONNECTOR_MODE_UDP = "UDP_CONNECTOR";
	public static final String CONNECTOR_PROTOCOL_TCP = "TCP";
	public static final String CONNECTOR_PROTOCOL_UDP = "UDP";
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

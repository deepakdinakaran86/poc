package com.pcs.saffron.connectivity.mqtt;

/**
 * Representation of a message exchanged between a remote client and Trio STB.
 * Implementations of the RemoteMessage will provide message specific
 * information.
 * 
 *  @author renukaradhya
 */
public interface RemoteMessage {

	/**
	 * Obtain the Destination to use when sending a response message to the remote
	 * client that sent this message.
	 * 
	 * @return return path to source device.
	 */
	Destination getReturnPath();
}

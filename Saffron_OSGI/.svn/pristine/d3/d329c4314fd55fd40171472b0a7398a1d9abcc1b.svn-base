package com.pcs.saffron.connectivity.mqtt;

/**
 * Representation of a destination for remote messages to be send
 * 
 * Provides a means to send messages to the remote destination.
 * 
 *  @author renukaradhya
 */
public interface Destination {

    /**
     * Sends a message to the remote client represented by this Destination.
     * 
     * Attempts to send messages are done on a best effort basis and if errors
     * are encountered will fail silently.
     * 
     * @param message
     *            {@link RemoteMessage} to send to the remote client.
     * 
     * @throws IllegalArgumentException
     *             if the type of message being sent is not known to this
     *             destination and cannot be converted to a format appropriate
     *             for sending to the remote client.
     * 
     * @throws NoDeviceConnectedException
     *             if there is no active device connected which is represented
     *             by this object.
     */
    void sendMessage(RemoteMessage message) throws NoDeviceConnectedException;

    /**
     * Reports whether the device represented by this object is still connected.
     * 
     * @return true if the destination represented by this object is still
     *         active, false otherwise.
     */
    boolean isActive();

    /**
     * {@inheritDoc}
     */
    boolean equals(Object compare);

    /**
     * {@inheritDoc}
     */
    int hashCode();
}


package com.pcs.saffron.connectivity.mqtt;

/**
 * Handler to be registered with the RemoteMessageHub to receive and handle
 * messages from remote sources.
 * 
 *  @author renukaradhya
 */
public interface RemoteMessageHandler {

    /**
     * Notifies the listener with the message received from a remote service.
     * There may be multiple commands in one message which are returned by
     * {@link RemoteMessage#getCommands()}
     * 
     * @see org.ocapx.remote.RemoteMessageHub
     * 
     * @param msg
     *            {@link RemoteMessage} that was received by the local STB from
     *            a remote service.
     */
    void notify(RemoteMessage msg,String topic);
}

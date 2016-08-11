package com.pcs.saffron.connectivity.mqtt;


/**
 * Specialization of RemoteMessage for messages that have been received from a
 * MQTT Server.
 * 
 * Exposes MQTT communication protocol specific details.
 * 
 *  @author renukaradhya
 */
public interface MQTTMessage extends RemoteMessage {

    /**
     * Determine if this message is a duplicate of a message which was already
     * sent.
     * 
     * @return TRUE if the message is a duplicate or FALSE if it is not.
     */
    boolean isDuplicate();

    /**
     * Obtain the Qos level for this message as outlined in the mqtt
     * specification
     * 
     * 0: The broker/client will deliver the message once, with no confirmation.
     * 1: The broker/client will deliver the message at least once, with
     * confirmation required. 2: The broker/client will deliver the message
     * exactly once by using a four step handshake.
     * 
     * @return the QOS level of this message.
     */
    int getQoS();
}
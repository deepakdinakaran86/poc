package com.pcs.saffron.connectivity.mqtt;


import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Parser which is capable of parsing information from a message sent from a MQTT Server into
 * a one or many MQTTRemoteMessage objects.
 * 
 *  @author renukaradhya
 */
public interface MQTTMessageTranslator {
	
	/**
	 * Parse a Message from a MQTT Server into one or many MQTTRemoteMessage objects.
	 * 
	 * @param message top be parsed.
	 * 
	 * @return the parsed messages or NULL if the message could not be parsed.
	 */
	MQTTMessage translate(final MqttMessage message,String topic);	
	
	/**
     * Generate an MQTT message from {@link RemoteMessage}. 
     * @param responseMessage
     * @return
     */
	MqttMessage translate(final RemoteMessage message);
}

package com.pcs.saffron.connectivity.mqtt.impl;


import com.pcs.saffron.connectivity.mqtt.Destination;
import com.pcs.saffron.connectivity.mqtt.MQTTMessageTranslator;
import com.pcs.saffron.connectivity.mqtt.NoDeviceConnectedException;
import com.pcs.saffron.connectivity.mqtt.RemoteMessage;

/**
 * Destination what encapsulates the logic used to send a RemoteMessage to a remote
 * client that is subscribed to a MQTT topic.
 * 
 * @author renukaradhya
 */
public class MQTTDestination implements Destination {

	private final MQTTService service;
	
	private final String topic;
	
	private final MQTTMessageTranslator translator;
	
	private static final int HASH_FACTOR = 31;
	
	/**
	 * Constructor
	 * 
	 * @param service to publish messages to.
	 * @param topic that messages will be publish to
	 * @param translator to convert RemoteMessages to a MqttMessaage appropriate for publishing
	 */
    public MQTTDestination(final MQTTService service, final String topic,
            final MQTTMessageTranslator translator) {
		this.service = service;
		this.topic = topic;
		this.translator = translator;
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendMessage(RemoteMessage message) throws NoDeviceConnectedException {
		if (!isActive()) {
		    throw new NoDeviceConnectedException("Destination represented by this object is not active");
		}
		service.publish(topic, message, translator);		
	}
	
    /**
     * Reports whether the device represented by this object is still connected.
     * 
     * @return true if the destination represented by this object is still
     *         active, false otherwise.
     */
    public boolean isActive(){
    	return this.service.isConnected();
    }
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object compare) {
		return compare instanceof MQTTDestination && compare.hashCode() == hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int hashCode(){
		int hashCode = service.hashCode();
		hashCode = HASH_FACTOR * hashCode + topic.hashCode();
		
		return hashCode;
	}
}

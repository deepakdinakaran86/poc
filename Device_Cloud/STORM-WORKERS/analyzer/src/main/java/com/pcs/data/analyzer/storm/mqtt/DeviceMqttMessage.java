/**
 * 
 */
package com.pcs.data.analyzer.storm.mqtt;

import com.pcs.saffron.connectivity.mqtt.Destination;
import com.pcs.saffron.connectivity.mqtt.MQTTMessage;

/**
 * @author pcseg171
 *
 */
public class DeviceMqttMessage implements MQTTMessage {
	
	
	private Destination destination;
	private boolean duplicate;
	private int qos;
	private String message;
	private String identity;
	


	public void setDestination(Destination destination) {
		this.destination = destination;
	}


	public void setQos(int qos) {
		this.qos = qos;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	/* (non-Javadoc)
	 * @see com.pcs.saffron.connectivity.mqtt.RemoteMessage#getReturnPath()
	 */
	@Override
	public Destination getReturnPath() {
		return destination;
	}

	/* (non-Javadoc)
	 * @see com.pcs.saffron.connectivity.mqtt.MQTTMessage#isDuplicate()
	 */
	@Override
	public boolean isDuplicate() {
		return duplicate;
	}

	/* (non-Javadoc)
	 * @see com.pcs.saffron.connectivity.mqtt.MQTTMessage#getQoS()
	 */
	@Override
	public int getQoS() {
		return qos;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

}

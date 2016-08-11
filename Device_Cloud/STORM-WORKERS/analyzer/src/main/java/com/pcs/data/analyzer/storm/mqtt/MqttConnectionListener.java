/**
 * 
 */
package com.pcs.data.analyzer.storm.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.connectivity.mqtt.impl.MQTTService.MQTTServiceListener;

/**
 * @author pcseg171
 *
 */
public class MqttConnectionListener implements MQTTServiceListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MqttConnectionListener.class);

	/* (non-Javadoc)
	 * @see com.pcs.saffron.connectivity.mqtt.impl.MQTTService.MQTTServiceListener#connectionLost()
	 */
	@Override
	public void connectionLost() {
		//TODO implement reconnection logic.
		LOGGER.error("Detected connection loss !!");
	}

}

/**
 * 
 */
package com.pcs.device.gateway.hobbies.handler.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.pcs.device.gateway.hobbies.config.HobbiesGatewayConfiguration;
import com.pcs.device.gateway.hobbies.message.mqtt.DeviceMqttMessage;
import com.pcs.saffron.connectivity.mqtt.MQTTMessage;
import com.pcs.saffron.connectivity.mqtt.MQTTMessageTranslator;
import com.pcs.saffron.connectivity.mqtt.RemoteMessage;

/**
 * @author pcseg171
 *
 */
public class MqttDeviceEventTranslator implements MQTTMessageTranslator {
	

	/* (non-Javadoc)
	 * @see com.pcs.saffron.connectivity.mqtt.MQTTMessageTranslator#translate(org.eclipse.paho.client.mqttv3.MqttMessage, java.lang.String)
	 */
	@Override
	public MQTTMessage translate(MqttMessage message, String topic) {
		DeviceMqttMessage mqttDeviceMessage = new DeviceMqttMessage();
		mqttDeviceMessage.setDuplicate(message.isDuplicate());
		mqttDeviceMessage.setMessage(new String(message.getPayload()));
		mqttDeviceMessage.setQos(message.getQos());
		return mqttDeviceMessage;
	}

	/* (non-Javadoc)
	 * @see com.pcs.saffron.connectivity.mqtt.MQTTMessageTranslator#translate(com.pcs.saffron.connectivity.mqtt.RemoteMessage)
	 */
	@Override
	public MqttMessage translate(RemoteMessage message) {
		DeviceMqttMessage mqttDeviceMessage = (DeviceMqttMessage) message;
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(mqttDeviceMessage.getMessage().getBytes());
		mqttMessage.setQos(Integer.parseInt(HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_FEED_QOS)));
		return mqttMessage;
	}

}

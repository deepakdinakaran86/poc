package com.pcs.device.gateway.hobbies.handler.mqtt;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.hobbies.config.HobbiesGatewayConfiguration;
import com.pcs.saffron.connectivity.mqtt.impl.MQTTService;

public class HobbiesMqttDataConnector {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(HobbiesMqttDataConnector.class);
	
	private static MQTTService mqttService = null;
	private static final MqttDeviceMessageHandler HANDLER = new MqttDeviceMessageHandler();
	private static final String messageTopic = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_FEED_TOPIC);
	private static final  String brokerURL = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_BROKER_URL);
	private static final  Integer qos = Integer.parseInt(HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_FEED_QOS));
	private static final  UUID uuid = UUID.randomUUID();
	private static final  String userName = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_BROKER_USERNAME);
	private static final  String password = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_BROKER_PASSWORD);
	
	public void connectToMQTT() throws Exception{
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mqttService = new MQTTService(new HobbiesMqttConnectionListener(),brokerURL,uuid.toString(),userName,password);
			       if(mqttService.start(true)){
			    	   LOGGER.info("MQTT service started, subscribing to {}",messageTopic);
			    	   mqttService.subscribe(messageTopic, HANDLER, new MqttDeviceMessageTranslator(),qos);
			       }else{
			    	   LOGGER.error("MQTT service could not be started!!");
			       }
				
			}
		}).start();

    	
	}
	
	public void disconectMQTT(){
		LOGGER.info("MQTT service terminating, unsubscribing from {}",messageTopic);
		mqttService.unSubscribe(HANDLER);
	}
	
	
	public static void main(String[] args) throws Exception {
		new HobbiesMqttDataConnector().connectToMQTT();
	}

}

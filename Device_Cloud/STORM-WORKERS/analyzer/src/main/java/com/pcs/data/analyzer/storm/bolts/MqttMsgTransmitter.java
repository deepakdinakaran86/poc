package com.pcs.data.analyzer.storm.bolts;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.storm.mqtt.DeviceMqttMessage;
import com.pcs.data.analyzer.storm.mqtt.MqttConnectionListener;
import com.pcs.data.analyzer.storm.mqtt.MqttDeviceMessageTranslator;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.connectivity.mqtt.impl.MQTTService;
import com.pcs.saffron.manager.enums.MessageType;

public class MqttMsgTransmitter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MqttMsgTransmitter.class);

	private static MQTTService mqttService = null;
	private static final UUID uuid = UUID.randomUUID();

	private static String alarmTopic = "LiveFeeds/Alarm/";
	private static String dataTopic = "LiveFeeds/Data/";
	private static String wbRespTopic = "LiveFeeds/WritebackResponse/";
	private static String generalInfoTopic = "LiveFeeds/General/";
	private final PublisherConfig publisherConfig;

	public MqttMsgTransmitter(String publisherConfFilePath){
		this.publisherConfig = PublisherConfig
				.getNewInstance(publisherConfFilePath);
	}
	
	public MqttMsgTransmitter(PublisherConfig publisherConfig){
		this.publisherConfig = publisherConfig;
		LOGGER.info("MQTT service created!!");
	}
	
	public void publish(DeviceMqttMessage message,MessageType messageType){
		LOGGER.info("In MQTT Publish Method!!");
		mqttService = new MQTTService(new MqttConnectionListener(),
				getBrokerUrl(), uuid.toString(), getUserName(), getPassword());
		if (mqttService.start(true)) {
			LOGGER.info("mqttService.start True!!, publishing to {}",resolveTopic(message.getIdentity(),messageType));
			mqttService.publish(resolveTopic(message.getIdentity(),messageType), message,
					new MqttDeviceMessageTranslator());
			stop();
		} else {
			LOGGER.error("MQTT service could not be started!!");
		}
	}

	private String resolveTopic(String sourceId, MessageType messageType) {
		switch (messageType) {
		case MESSAGE:
			return dataTopic + sourceId;

		case ALARM:
			return alarmTopic + sourceId;

		case WRITEBACK_RESPONSE:
			return wbRespTopic + sourceId;

		default:
			return generalInfoTopic + sourceId;
		}
	}
	
	private String getBrokerUrl(){
		return publisherConfig.getMqttBrokerUrl();
	}
	
	private String getUserName(){
		return publisherConfig.getMqttUserName();
	}
	
	private String getPassword(){
		return publisherConfig.getMqttPassword();
	}
	
	private int getQoS(){
		return Integer.parseInt(publisherConfig.getMqttQoS());
	}

	public void stop() {
		mqttService.stop();
	}

	public static void main(String[] args) throws Exception {
		for(int i=0;i<=100;i++){
		MqttMsgTransmitter mqttMsgTransmitter = new MqttMsgTransmitter("Config//publisherconfig.yaml");
		String msg = "{\"sourceId\":\"211EA729-C7DA-4E87-8FAD-EE0896898B8F\",\"time\":\"1463897971923\",\"receivedTime\":\"1463897937682\",\"points\":[{\"pointId\":\"1\",\"pointName\":\"battery\",\"displayName\":\"battery-1\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"100.0\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"2\",\"pointName\":\"freespace\",\"displayName\":\"freespace-2\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"9\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"3\",\"pointName\":\"AccelerationX\",\"displayName\":\"AccelerationX-3\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-0.018280\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"4\",\"pointName\":\"AccelerationY\",\"displayName\":\"AccelerationY-4\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-0.015854\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"5\",\"pointName\":\"AccelerationZ\",\"displayName\":\"AccelerationZ-5\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-1.008667\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"6\",\"pointName\":\"DeviceMotionX\",\"displayName\":\"DeviceMotionX-6\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-0.000503\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"7\",\"pointName\":\"DeviceMotionY\",\"displayName\":\"DeviceMotionY-7\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-0.001646\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"8\",\"pointName\":\"DeviceMotionZ\",\"displayName\":\"DeviceMotionZ-8\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-0.008248\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"9\",\"pointName\":\"GyroRotationX\",\"displayName\":\"GyroRotationX-9\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-0.055195\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"10\",\"pointName\":\"GyroRotationY\",\"displayName\":\"GyroRotationY-10\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"0.031274\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"11\",\"pointName\":\"GyroRotationZ\",\"displayName\":\"GyroRotationZ-11\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"0.019140\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"12\",\"pointName\":\"MagneticFieldX\",\"displayName\":\"MagneticFieldX-12\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"197.454086\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"13\",\"pointName\":\"MagneticFieldY\",\"displayName\":\"MagneticFieldY-13\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"96.368027\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]},{\"pointId\":\"14\",\"pointName\":\"MagneticFieldZ\",\"displayName\":\"MagneticFieldZ-14\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status float\",\"unit\":\"unitless\",\"data\":\"-578.899353\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[],\"alarmExtensions\":[]}]}";
		ObjectMapper mapper = new ObjectMapper();
		DeviceMqttMessage deviceMqttMessage = new DeviceMqttMessage();
		Message message = mapper.readValue(msg, Message.class);
		deviceMqttMessage.setMessage(message.toString());
		deviceMqttMessage.setIdentity("211EA729-C7DA-4E87-8FAD-EE0896898B8F");
		mqttMsgTransmitter.publish(deviceMqttMessage, MessageType.MESSAGE);
		Thread.sleep(3000);
		}
	}

}

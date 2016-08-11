package com.pcs.device.gateway.hobbies.handler.mqtt;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.pcs.device.gateway.hobbies.config.HobbiesGatewayConfiguration;
import com.pcs.device.gateway.hobbies.event.notifier.DeviceMqttMessageNotifier;
import com.pcs.device.gateway.hobbies.message.HobbiesMqttCommandResponse;
import com.pcs.device.gateway.hobbies.message.mqtt.DeviceMqttMessage;
import com.pcs.saffron.cipher.data.generic.message.GenericEvent;
import com.pcs.saffron.cipher.data.generic.message.GenericMessage;
import com.pcs.saffron.cipher.data.generic.message.TimeseriesData;
import com.pcs.saffron.connectivity.mqtt.RemoteMessage;
import com.pcs.saffron.connectivity.mqtt.RemoteMessageHandler;

public class MqttDeviceMessageHandler implements RemoteMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MqttDeviceMessageHandler.class);

	public void notify(RemoteMessage msg,String topic) {
		DeviceMqttMessage deviceMessage = (DeviceMqttMessage) msg;
		ObjectMapper mapper = new ObjectMapper();
		try {

			GenericMessage actualData = null;
			int resolveTopic = resolveTopic(topic);
			LOGGER.info("Topic {} identified as {}",topic,resolveTopic);
			String message = deviceMessage.getMessage();
			switch (resolveTopic) {
			case 0:
				if(message.startsWith("[") && message.endsWith("]")){
					List<TimeseriesData> deviceData = mapper.readValue(message,
							TypeFactory.defaultInstance().constructCollectionType(List.class,  
									TimeseriesData.class));
					for (GenericMessage timeseriesData : deviceData) {
						timeseriesData.setSourceId(extractTopic(topic,HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_FEED_TOPIC)));
					}
					DeviceMqttMessageNotifier.getInstance().notifyMessagesReception(new ArrayList<GenericMessage>(deviceData));
				}else{
					actualData = mapper.readValue(message, TimeseriesData.class);
					actualData.setSourceId(extractTopic(topic,HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_FEED_TOPIC)));
					DeviceMqttMessageNotifier.getInstance().notifyMessageReception(actualData);
				}
				break;
			case 1:
				if(message.startsWith("[") && message.endsWith("]")){
					List<GenericEvent> deviceData = mapper.readValue(message,
							TypeFactory.defaultInstance().constructCollectionType(List.class,  
									GenericEvent.class));
					for (GenericEvent genericEvent : deviceData) {
						genericEvent.setSourceId(extractTopic(topic,HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_EVENT_FEED_TOPIC)));
					}
					DeviceMqttMessageNotifier.getInstance().notifyEventsReception(deviceData);
				}else{
					actualData = mapper.readValue(message, GenericEvent.class);
					actualData.setSourceId(extractTopic(topic,HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_EVENT_FEED_TOPIC)));
					DeviceMqttMessageNotifier.getInstance().notifyEventReception((GenericEvent) actualData);
				}
				break;
			case 2:
				actualData = mapper.readValue(message, HobbiesMqttCommandResponse.class);
				actualData.setSourceId(extractTopic(topic,HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_COMMAND_RESPONSE_TOPIC)));
				//DeviceMqttMessageNotifier.getInstance().notifyMessageReception(actualData);
				break;
			default:
				break;
			}


		} catch (Exception e) {
			LOGGER.error("Error handling mqtt message",e);
		}

	}

	private int resolveTopic(String topic) throws Exception{
		if(topic.indexOf("events") !=-1){
			return 1;
		}else if (topic.indexOf("commandresponse") !=-1) {
			return 2;
		}else if(topic.indexOf("feed") !=-1){
			return 0;
		}else{
			throw new Exception("Invalid topic "+topic);
		}
	}

	private String extractTopic(String topic,String topicRoot){
		topicRoot = topicRoot.substring(0, (topicRoot.length()-1));//avoiding #
		String sourceTopic = topic.replace(topicRoot, "");
		String sourceId = sourceTopic.indexOf("/")>0?sourceTopic.substring(0,sourceTopic.indexOf("/")):sourceTopic;
		return sourceId.toLowerCase();
	}

}

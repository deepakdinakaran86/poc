package com.pcs.device.gateway.hobbies.handler.mqtt;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.hobbies.config.HobbiesGatewayConfiguration;
import com.pcs.device.gateway.hobbies.message.HobbiesCommand;
import com.pcs.device.gateway.hobbies.message.mqtt.DeviceMqttMessage;
import com.pcs.saffron.connectivity.mqtt.impl.MQTTService;
import com.pcs.saffron.manager.writeback.bean.WriteBackCommand;
import com.pcs.saffron.manager.writeback.enums.CommandType;
import com.pcs.saffron.manager.writeback.listener.CommandListener;

public class HobbiesCommandTransmitter implements CommandListener{


	private static final Logger LOGGER = LoggerFactory.getLogger(HobbiesCommandTransmitter.class);

	private static MQTTService mqttService = null;
	private  MqttDeviceMessageHandler HANDLER = new MqttDeviceMessageHandler();

	private static final  String brokerURL = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_BROKER_URL);
	private static final  Integer qos = Integer.parseInt(HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_FEED_QOS));
	private static final  String userName = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_BROKER_USERNAME);
	private static final  String password = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_BROKER_PASSWORD);

	private void writebackCommand(final WriteBackCommand command) throws Exception{


		if(mqttService == null){
			initMQTTService();
		}
		String messageTopic = HobbiesGatewayConfiguration.getProperty(HobbiesGatewayConfiguration.MQTT_DEVICE_COMMAND_TOPIC)+"/"+command.getSourceId();

		if(mqttService.start(true)){
			try {
				LOGGER.info("Command type {}",command.getPayload().getCommand());
				String commandData = null;
				switch (CommandType.valueOf(command.getPayload().getCommand())) {
				case WRITE_COMMAND:
					HobbiesCommand hobbiesCommand = new HobbiesCommand();
					hobbiesCommand.setCommandId(Short.toString(command.getWriteBackId()));
					hobbiesCommand.setParameterName(command.getPayload().getPointName());
					hobbiesCommand.setParameterValue(command.getPayload().getValue());
					try {
						commandData = new ObjectMapper().writeValueAsString(hobbiesCommand);
					} catch (JsonProcessingException e) {
						LOGGER.error("Cannot cast to command",e);
					}
					break;
				case SYSTEM_COMMAND:
					LOGGER.error("Undefined operation");
					break;

				default:
					break;
				}

				if(commandData != null){
					LOGGER.info("Sending command {}",messageTopic);
					DeviceMqttMessage message = new DeviceMqttMessage();
					message.setMessage(commandData);
					message.setQos(qos);
					mqttService.publish(messageTopic, message, new MqttDeviceMessageTranslator());
				}
			} catch (Exception e) {
				LOGGER.error("Error handling command",e);
			}
			mqttService.stop();
		}else{
			LOGGER.error("MQTT service could not be started!!");
			mqttService = null;
		}


	}

	private static void initMQTTService(){

		if(mqttService == null){
			String clientID = UUID.randomUUID().toString()+"HobbiesCommand";
			LOGGER.info("Client id {}",clientID);
			mqttService = new MQTTService(new HobbiesMqttConnectionListener(),brokerURL,clientID,userName,password);
		}else{
			LOGGER.info("Active MQTT service already exist");
		}
	}

	@Override
	public void handleCommand(WriteBackCommand backCommand) {
		try {
			writebackCommand(backCommand);
		} catch (Exception e) {
			LOGGER.error("Handling Command",e);
		}

	}

	public void disconectMQTT(){
		LOGGER.info("MQTT service terminating!!!");
		mqttService.unSubscribe(HANDLER);
	}

	public static void main(String[] args) {
		System.err.println(CommandType.valueOf("WRITE_COMMAND"));
	}
}

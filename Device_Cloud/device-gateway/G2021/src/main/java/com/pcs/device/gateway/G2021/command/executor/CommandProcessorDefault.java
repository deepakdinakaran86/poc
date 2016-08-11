package com.pcs.device.gateway.G2021.command.executor;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.deviceframework.pubsub.consumers.BaseConsumer;
import com.pcs.deviceframework.pubsub.consumers.delegates.AsynchronousConsumer;

public class CommandProcessorDefault implements MessageListener{

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandProcessorDefault.class);

	private static CommandProcessorDefault _instance = null;

	private CommandProcessorDefault(){

	}

	public static CommandProcessorDefault getInstance(){
		if(_instance == null)
			_instance = new CommandProcessorDefault();
		return _instance;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Illegal Clone Request!!");
	}

	public void doProcess(){
		BaseConsumer baseConsumer = new AsynchronousConsumer();
		baseConsumer.setQueue(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.COMMAND_REGISTER));
		baseConsumer.setUrl(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.COMMAND_REGISTER_URL));
		baseConsumer.setMessageListener(this);
		baseConsumer.listen();
	}

	@Override
	public void onMessage(Message message) {

		try {

			G2021Command command = null;
			if (message instanceof TextMessage) {
				TextMessage commandMessage = (TextMessage) message;
				Gson gson = new Gson();
				command = gson.fromJson(commandMessage.getText(), G2021Command.class);

				G2021DeviceConfiguration configuration = (G2021DeviceConfiguration) G2021DeviceManager.instance().getConfiguration(command.getSourceId());
				G2021CommandExecutor executor = new G2021CommandExecutor();
				command.setSessionId(configuration.getSessionId());
				command.setUnitId(configuration.getUnitId());
				executor.executeCommand(command, configuration);				
			}


		} catch (Exception e) {
			LOGGER.error("Exception receiving messages from the queue",e);
		}

	}



}

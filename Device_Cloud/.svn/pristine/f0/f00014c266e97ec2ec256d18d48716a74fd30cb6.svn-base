package com.pcs.device.gateway.G2021.message.notification;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQNotifier {

	private final static String QUEUE_NAME = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.RABBIT_MQ_LIVE_QUEUE);
	private static final String RABBIT_MQ_IP = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.RABBIT_MQ_HOST);
	private static final Integer RABBIT_MQ_PORT = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.RABBIT_MQ_PORT));
	private static final String RABBIT_MQ_USERNAME = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.RABBIT_MQ_USERNAME);
	private static final String RABBIT_MQ_PASSWORD = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.RABBIT_MQ_PASSWORD);

	public static void notifyMessage(String message) throws IOException,
			UnsupportedEncodingException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(RABBIT_MQ_IP);
	    factory.setPort(RABBIT_MQ_PORT);
	    factory.setUsername(RABBIT_MQ_USERNAME);
	    factory.setPassword(RABBIT_MQ_PASSWORD);
	    
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
	    System.out.println(" [x] Sent '" + message + "'");

	    channel.close();
	    connection.close();
	}
}

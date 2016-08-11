package com.pcs.saffron.deviceregistry.processor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.notification.broker.consumer.CoreConsumer;
import com.pcs.saffron.notification.broker.consumer.async.CoreListenerAdapter;

public class RegistrationHandler extends CoreListenerAdapter {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(RegistrationHandler.class);

	private CoreConsumer consumer;

	public CoreConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(CoreConsumer consumer) {
		consumer.setMessageListener(this);
		this.consumer = consumer;
	}

	public void consumeMessages() {
		consumer.listen();
	}

	@Override
	public void consumeData(List<KafkaStream<byte[], byte[]>> streams) {
		LOGGER.info("Received command stream from kafka new IMPL");
		int i = 0;
		for (@SuppressWarnings("rawtypes") final KafkaStream stream : streams) {
			ExecutorService executor;
			executor = Executors.newFixedThreadPool(10);
			executor.submit(new RegistrationProcessorDaemon(stream, i));
			i++;
		}

	}

}

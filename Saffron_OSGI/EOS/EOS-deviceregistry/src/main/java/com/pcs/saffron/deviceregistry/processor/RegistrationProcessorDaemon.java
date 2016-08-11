package com.pcs.saffron.deviceregistry.processor;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.manager.registration.api.DeviceAutoClaimService;
import com.pcs.saffron.manager.registration.api.DeviceRegistrationService;
import com.pcs.saffron.manager.registration.bean.DeviceRegistrationData;

@SuppressWarnings("rawtypes")
public class RegistrationProcessorDaemon implements Runnable {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(RegistrationProcessorDaemon.class);

	private KafkaStream kafkaStream;
	private int threadNo;

	public RegistrationProcessorDaemon(KafkaStream stream, int threadNo) {
		kafkaStream = stream;
		this.threadNo = threadNo;
	}

	public void run() {
		@SuppressWarnings("unchecked") ConsumerIterator<byte[], byte[]> iterator = kafkaStream
		        .iterator();
		LOGGER.info("Processing Requests");
		try {
			while (iterator.hasNext()) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String deviceRegistrationData = new String(iterator.next()
					        .message());
					LOGGER.info("Device registration request received {}",
					        deviceRegistrationData);
					DeviceRegistrationData registerDeviceData = mapper.readValue(deviceRegistrationData,DeviceRegistrationData.class);
					boolean register = false;
					if (registerDeviceData != null) {

						if (registerDeviceData.getAutoclaimEnabled() != null
						        && registerDeviceData.getAutoclaimEnabled()) {
							LOGGER.info("* Auto Claim Device *");
							DeviceAutoClaimService deviceRegistrationService = new DeviceAutoClaimService();
							register = deviceRegistrationService
							        .register(registerDeviceData);
						} else {
							LOGGER.info("* Registering Device *");
							DeviceRegistrationService deviceRegistrationService = new DeviceRegistrationService();
							register = deviceRegistrationService
							        .register(registerDeviceData);
						}
					} else {
						LOGGER.error("Device registration request is null");
					}
					LOGGER.info("Registration Status : {}", register);
					mapper = null;
					Thread.sleep(50l);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("Error Registering Devices", e);
				}

			}
		} catch (Exception e) {
			LOGGER.error("Error Registering Devices", e);
		}
	}

}

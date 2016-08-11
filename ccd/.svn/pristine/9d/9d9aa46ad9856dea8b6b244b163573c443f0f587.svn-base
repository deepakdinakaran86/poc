package com.pcs.util.faultmonitor.ccd.fault.handler;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;
import com.pcs.saffron.notification.enums.DistributorMode;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultData;
import com.pcs.util.faultmonitor.ccd.fault.decoder.FaultDecoderV1;
import com.pcs.util.faultmonitor.ccd.fault.decoder.FaultDecoderV2;
import com.pcs.util.faultmonitor.configuration.FaultMonitorConfiguration;
import com.pcs.util.faultmonitor.osgi.util.MessageUtil;

/**
 * @author pcseg171
 *
 *Handler class that would check for snapshots from G2021 devices representing fault messages from the equipment.
 */
public class FaultDataHandler extends FileAlterationListenerAdaptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(FaultDataHandler.class);
	
	/*
	 * Checks for a new directory creation, would have to authorize the file and register.
	 */
	@Override
	public void onDirectoryCreate(File directory) {
		LOGGER.info("New directory created {}",directory);
	}

	@Override
	public void onFileCreate(File file) {
		try {
			Thread.sleep(2000);//delay for file creation completion
			
			//List<FaultData> decode = FaultDecoderV1.decode(file);
			List<FaultData> decode = FaultDecoderV2.decode(file);
			LOGGER.info("FaultData size : {}",decode.size());
			List<Serializable> faultDataCollection = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> publishMessage = null;
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
			
			for (FaultData faultData : decode) {
				ObjectMapper mapper = new ObjectMapper();
				LOGGER.info("File Read {}",mapper.writeValueAsString(faultData));
				publishMessage = new KeyedMessage<Object, Object>(FaultMonitorConfiguration.getProperty(FaultMonitorConfiguration.FAULT_MESSAGE_STREAM),
												mapper.writeValueAsString(faultData));
				faultDataCollection.add(publishMessage);
			}
			publisher.publish(faultDataCollection);
		} catch (Exception e) {
			LOGGER.error("Error processing fault file",e);
			
		}
		LOGGER.info("New File created {} with content :*****\n",file.getName());
	}
	
	
}

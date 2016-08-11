package com.pcs.event.scanner.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.event.scanner.config.ScannerConfiguration;
import com.pcs.event.scanner.consumer.AlarmReaderCEPAsyn;
import com.pcs.event.scanner.consumer.MessageTracker;
import com.pcs.event.scanner.osgi.util.MessageUtil;
import com.pcs.jms.jmshelper.consumers.BaseConsumer;
import com.pcs.jms.jmshelper.consumers.common.DefaultConsumerRegistry;
import com.pcs.jms.jmshelper.enums.common.DefaultConsumerModes;
import com.pcs.saffron.notification.handler.NotificationHandler;


public class EventScanActivator implements BundleActivator{

	private static final Logger LOGGER = LoggerFactory.getLogger(EventScanActivator.class);

	ServiceReference notificationService = null;
	BaseConsumer commandConsumer = new DefaultConsumerRegistry().getConsumer(DefaultConsumerModes.ASYNC);
	@Override
	public void start(BundleContext context) throws Exception {
		LOGGER.info("Starting Event Scan");
		
		notificationService = context.getServiceReference(NotificationHandler.class.getName());
		NotificationHandler notificationHandler =(NotificationHandler)context.getService(notificationService);
		MessageUtil.setNotificationHandler(notificationHandler);
		
		new Thread(){
			@Override
			public void run() {
				commandConsumer.setUrl(ScannerConfiguration.getProperty(ScannerConfiguration.REALTIME_DATA_URL));
				commandConsumer.setQueue(ScannerConfiguration.getProperty(ScannerConfiguration.EVENT_VIOLATION));
				commandConsumer.setMessageListener(new AlarmReaderCEPAsyn());
				commandConsumer.listen();
			}
			
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				MessageTracker.listen();
			}
		}.start();
		
		
		LOGGER.info("Scanning For Events ....");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Stoping Event Scan");
		commandConsumer.stopListen();
		LOGGER.info("Event Scan Terminated !!!");
	}
}

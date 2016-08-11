package com.pcs.saffron.notification.osgi;

import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.notification.configuration.ApplicationConfiguration;
import com.pcs.saffron.notification.handler.NotificationHandler;
import com.pcs.saffron.notification.handler.implementation.DiagnosisNotificationHelper;
import com.pcs.saffron.notification.handler.implementation.NotificationHelper;

public class NotificationActivator implements BundleActivator {

	private static final Logger LOGGER  = LoggerFactory.getLogger(NotificationActivator.class);
	ServiceRegistration notificationHandlerService = null;
	ServiceRegistration diagNotificationHandlerService = null;

	
	public void start(BundleContext context) throws Exception {
		LOGGER.info("Registering Notification Service...");
		URL entry = context.getBundle().getEntry("config.yaml");
		LOGGER.info("Config Yaml {}",entry.toString());
		ApplicationConfiguration.init(entry.toString());
		
		NotificationHandler notificationHandler = new NotificationHelper();
		notificationHandlerService = context.registerService(NotificationHandler.class.getName(), notificationHandler, null);
		LOGGER.info("Notification Service Registered Successfully");

		LOGGER.info("Registering Diagnostic Notification Service");
		NotificationHandler diagNotificationHandler = new DiagnosisNotificationHelper();
		diagNotificationHandlerService = context.registerService(DiagnosisNotificationHelper.class.getName(), diagNotificationHandler, null);
		LOGGER.info("Diagnostic Notification Service Registered Successfully");
	}

	public void stop(BundleContext arg0) throws Exception {
		LOGGER.info("Shutting down Notification Service...");
		notificationHandlerService.unregister();
		diagNotificationHandlerService.unregister();
		LOGGER.info("Notification Service Is Terminated !!");
	}
}

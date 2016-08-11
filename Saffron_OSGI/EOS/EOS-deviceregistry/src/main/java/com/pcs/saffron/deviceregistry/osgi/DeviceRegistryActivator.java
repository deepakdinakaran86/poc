package com.pcs.saffron.deviceregistry.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.deviceregistry.consumer.RegistrationTracker;
import com.pcs.saffron.deviceregistry.osgi.util.MessageUtil;
import com.pcs.saffron.notification.handler.NotificationHandler;


public class DeviceRegistryActivator implements BundleActivator{

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceRegistryActivator.class);

	ServiceReference notificationService = null;

	public void start(BundleContext context) throws Exception {
		LOGGER.info("Starting Registration Request Scan");
		
		notificationService = context.getServiceReference(NotificationHandler.class.getName());
		NotificationHandler notificationHandler =(NotificationHandler)context.getService(notificationService);
		MessageUtil.setNotificationHandler(notificationHandler);
		
		new Thread(){
			@Override
			public void run() {
				RegistrationTracker.listen();
			}
			
		}.start();
		LOGGER.info("Scanning For Registration Requests ....");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Stopping Registration Request Scan");
	}
}

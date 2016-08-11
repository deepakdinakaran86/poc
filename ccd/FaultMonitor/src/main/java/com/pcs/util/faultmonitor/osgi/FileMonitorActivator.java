package com.pcs.util.faultmonitor.osgi;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.notification.handler.NotificationHandler;
import com.pcs.util.faultmonitor.ccd.fault.monitor.FaultDataMonitor;
import com.pcs.util.faultmonitor.configuration.FaultMonitorConfiguration;
import com.pcs.util.faultmonitor.osgi.util.MessageUtil;

public class FileMonitorActivator implements BundleActivator{

	private static final Logger LOGGER = LoggerFactory.getLogger(FileMonitorActivator.class);
	
	ServiceReference notificationService = null;
	
	public void start(BundleContext context) throws Exception {
		LOGGER.info("Starting File Monitoring...");
		notificationService = context.getServiceReference(NotificationHandler.class.getName());
		NotificationHandler notificationHandler =(NotificationHandler)context.getService(notificationService);
		MessageUtil.setNotificationHandler(notificationHandler);
		
		FaultDataMonitor.registerDirectory(new File(FaultMonitorConfiguration.getProperty(FaultMonitorConfiguration.SCAN_DIRECTORY)), 
											Long.parseLong(FaultMonitorConfiguration.getProperty(FaultMonitorConfiguration.SCAN_FREQUENCY)));
		FaultDataMonitor.scan();
		LOGGER.info("Now Monitoring Directory : {}",FaultMonitorConfiguration.getProperty(FaultMonitorConfiguration.SCAN_DIRECTORY));
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Terminating File Monitoring...");
		context.ungetService(notificationService);
		FaultDataMonitor.stopScan();
		LOGGER.info("File Monitoring Terminated");
	}
	
}

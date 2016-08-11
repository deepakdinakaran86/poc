package com.pcs.saffron.scheduler.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.scheduler.util.SchedulerUtil;

public class SchedulerActivator implements BundleActivator {

	private static final Logger LOGGER  = LoggerFactory.getLogger(SchedulerActivator.class);
	ServiceRegistration schedulerService = null;

	
	public void start(BundleContext context) throws Exception {
		SchedulerUtil schedulerUtil = new SchedulerUtil();
		schedulerService = context.registerService(SchedulerUtil.class.getName(), schedulerUtil, null);
		LOGGER.info("Notification Service Registered Successfully");

	}

	public void stop(BundleContext arg0) throws Exception {
		LOGGER.info("Shutting down Notification Service...");
		SchedulerUtil.shutdown();
		schedulerService.unregister();
		LOGGER.info("Notification Service Is Terminated !!");
	}
}

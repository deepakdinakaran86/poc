/**
 * 
 */
package com.pcs.device.gateway.meitrack.osgi;

import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.meitrack.bootstrap.Listeners;
import com.pcs.device.gateway.meitrack.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.meitrack.bundle.utils.MessageUtil;
import com.pcs.gateway.meitrack.event.handler.MessageHandler;
import com.pcs.gateway.meitrack.event.notifier.MessageNotifier;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.bundle.util.CacheUtil;
import com.pcs.saffron.notification.handler.NotificationHandler;

/**
 * @author Aneesh
 *
 */
public class MeitrackActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeitrackActivator.class);
	ServiceReference notificationService = null;
	ServiceReference cacheService = null;
	ServiceReference eosManagerService = null;
	Listeners listeners = new Listeners();
	
	public void start(BundleContext context) throws Exception {
		
		LOGGER.info("Starting Meitrack Gatetway...");
		
		URL entry = context.getBundle().getEntry("gateway.properties");
		System.err.println(entry.toString());
		
		notificationService = context.getServiceReference(NotificationHandler.class.getName());
		NotificationHandler notificationHandler =(NotificationHandler)context.getService(notificationService);
		MessageUtil.setNotificationHandler(notificationHandler);
		
		cacheService = context.getServiceReference(CacheProvider.class.getName());
		CacheProvider cacheProvider = (CacheProvider) context.getService(cacheService);
		CacheUtil.setCacheProvider(cacheProvider);
		
		eosManagerService = context.getServiceReference(DeviceManager.class.getName());
		DeviceManagerUtil.setMeitrackDeviceManager((DeviceManager) context.getService(eosManagerService));
		
		MessageNotifier.getInstance().addMessageListener(new MessageHandler());
		
		
		listeners.startFromConfiguration();
		LOGGER.info("Started Meitrack Gateway ...");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Goodbye Meitrack..!!");
		context.ungetService(notificationService);
		context.ungetService(cacheService);
		LOGGER.info("Terminating Meitrack Listeners..!!");
		listeners.stopServers();
		LOGGER.info("Meitrack Listeners Terminated..!!");
	}


}

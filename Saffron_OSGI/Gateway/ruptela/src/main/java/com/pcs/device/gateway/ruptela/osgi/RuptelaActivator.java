/**
 * 
 */
package com.pcs.device.gateway.ruptela.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.ruptela.bootstrap.Listeners;
import com.pcs.device.gateway.ruptela.bundle.utils.CacheUtil;
import com.pcs.device.gateway.ruptela.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.ruptela.bundle.utils.MessageUtil;
import com.pcs.device.gateway.ruptela.event.handler.MessageHandler;
import com.pcs.device.gateway.ruptela.event.notifier.MessageNotifier;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.notification.handler.NotificationHandler;
import com.pcs.saffron.notification.handler.implementation.DiagnosisNotificationHelper;

/**
 * @author Aneesh
 *
 */
public class RuptelaActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(RuptelaActivator.class);
	ServiceReference notificationService = null;
	ServiceReference diagNotificationService = null;
	ServiceReference cacheService = null;
	ServiceReference eosManagerService = null;
	Listeners listeners = new Listeners();
	
	public void start(BundleContext context) throws Exception {
		
		LOGGER.info("Starting Ruptela Gatetway...");
		
		notificationService = context.getServiceReference(NotificationHandler.class.getName());
		NotificationHandler notificationHandler =(NotificationHandler)context.getService(notificationService);
		MessageUtil.setNotificationHandler(notificationHandler);
		
		diagNotificationService = context.getServiceReference(DiagnosisNotificationHelper.class.getName());
		NotificationHandler diagNotificationHandler =(NotificationHandler)context.getService(diagNotificationService);
		MessageUtil.setDiagNotificationHandler(diagNotificationHandler);
		
		cacheService = context.getServiceReference(CacheProvider.class.getName());
		CacheProvider cacheProvider = (CacheProvider) context.getService(cacheService);
		CacheUtil.setCacheProvider(cacheProvider);
		
		eosManagerService = context.getServiceReference(DeviceManager.class.getName());
		DeviceManagerUtil.setRuptelaDeviceManager((DeviceManager) context.getService(eosManagerService));
		
		MessageNotifier.getInstance().addMessageListener(new MessageHandler());
		
		listeners.startFromConfiguration();
		LOGGER.info("Started Ruptela Gateway ...");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Goodbye Ruptela..!!");
		context.ungetService(notificationService);
		context.ungetService(cacheService);
		LOGGER.info("Terminating Ruptela Listeners..!!");
		listeners.stopServers();
		LOGGER.info("Ruptela Listeners Terminated..!!");
	}


}

/**
 * 
 */
package com.pcs.gateway.teltonika.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.bootstrap.Listeners;
import com.pcs.gateway.teltonika.bundle.utils.CacheUtil;
import com.pcs.gateway.teltonika.bundle.utils.DeviceManagerUtil;
import com.pcs.gateway.teltonika.bundle.utils.ExpressionUtil;
import com.pcs.gateway.teltonika.bundle.utils.MessageUtil;
import com.pcs.gateway.teltonika.event.handler.MessageHandler;
import com.pcs.gateway.teltonika.event.notifier.MessageNotifier;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.expressions.engine.ExpressionEngineUtil;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.notification.handler.NotificationHandler;
import com.pcs.saffron.notification.handler.implementation.DiagnosisNotificationHelper;

/**
 * @author Aneesh
 *
 */
public class TeltonikaActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeltonikaActivator.class);
	ServiceReference notificationService = null;
	ServiceReference diagNotificationService = null;
	ServiceReference cacheService = null;
	ServiceReference eosManagerService = null;
	ServiceReference expressionUtil = null;
	Listeners listeners = new Listeners();
	
	public void start(BundleContext context) throws Exception {
		
		LOGGER.info("Starting Teltonika Gatetway...");
		
		expressionUtil = context.getServiceReference(ExpressionEngineUtil.class.getName());
		ExpressionEngineUtil expressionEngine =(ExpressionEngineUtil)context.getService(expressionUtil);
		ExpressionUtil.setExpressionEngine(expressionEngine);
		
		
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
		DeviceManagerUtil.setTeltonikaDeviceManager((DeviceManager) context.getService(eosManagerService));
		
		MessageNotifier.getInstance().addMessageListener(new MessageHandler());
		
		listeners.startFromConfiguration();
		LOGGER.info("Started Teltonika Gateway ...");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Goodbye Teltonika..!!");
		context.ungetService(notificationService);
		context.ungetService(cacheService);
		context.ungetService(expressionUtil);
		LOGGER.info("Terminating Teltonika Listeners..!!");
		listeners.stopServers();
		LOGGER.info("Teltonika Listeners Terminated..!!");
	}


}

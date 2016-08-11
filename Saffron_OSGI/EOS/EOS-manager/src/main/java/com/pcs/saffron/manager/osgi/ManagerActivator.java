/**
 * 
 */
package com.pcs.saffron.manager.osgi;

import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.bundle.util.CacheUtil;
import com.pcs.saffron.manager.bundle.util.MessageUtil;
import com.pcs.saffron.manager.provider.EOSDeviceManager;
import com.pcs.saffron.manager.util.CEPMessageConverter;
import com.pcs.saffron.manager.writeback.receiver.CommandTracker;
import com.pcs.saffron.notification.handler.NotificationHandler;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;

/**
 * @author Aneesh
 *
 */
public class ManagerActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerActivator.class);

	ServiceReference notificationService = null;
	ServiceReference cacheService = null;
	ServiceRegistration managerService = null;
	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		LOGGER.info("Starting EOS Manager...");

		notificationService = context.getServiceReference(NotificationHandler.class.getName());
		NotificationHandler notificationHandler =(NotificationHandler)context.getService(notificationService);
		MessageUtil.setNotificationHandler(notificationHandler);

		DeviceManager eosManager = EOSDeviceManager.instance();
		managerService = context.registerService(DeviceManager.class.getName(), eosManager, null);

		cacheService = context.getServiceReference(CacheProvider.class.getName());
		CacheProvider cacheProvider = (CacheProvider) context.getService(cacheService);
		CacheUtil.setCacheProvider(cacheProvider);

		URL url = context.getBundle().getEntry("ceppointmapping.yaml");
		CEPMessageConverter.initialize(url);
		LOGGER.info("Started  EOS Manager");

		new Thread(new Runnable() {
			public void run() {
				CommandTracker.listen();
			}
		}).start();
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		LOGGER.info("Goodbye  EOS Manager..!!");
		context.ungetService(cacheService);
		managerService.unregister();

	}

}

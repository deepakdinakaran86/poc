package com.pcs.device.gateway.G2021.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.CacheUtil;
import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.bundle.utils.MessageUtil;
import com.pcs.device.gateway.G2021.command.transmitter.G2021CommandListener;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.event.handler.AlarmHandler;
import com.pcs.device.gateway.G2021.event.handler.MessageHandler;
import com.pcs.device.gateway.G2021.event.notifier.MessageNotifier;
import com.pcs.device.gateway.G2021.test.Listeners;
import com.pcs.device.gateway.G2021.utils.SupportedDevices;
import com.pcs.device.gateway.G2021.utils.SupportedDevices.Devices;
import com.pcs.jms.jmshelper.consumers.BaseConsumer;
import com.pcs.jms.jmshelper.consumers.common.DefaultConsumerRegistry;
import com.pcs.jms.jmshelper.enums.common.DefaultConsumerModes;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.notification.handler.NotificationHandler;

public class GatewayActivator implements BundleActivator {


	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayActivator.class);
	private static final String REQUIRED = "y";
	ServiceReference notificationService = null;
	ServiceReference diagNotificationService = null;
	ServiceReference cacheService = null;
	ServiceReference eosManagerService = null;
	Listeners listeners = new Listeners();
	BaseConsumer commandConsumer = new DefaultConsumerRegistry().getConsumer(DefaultConsumerModes.ASYNC);
	
	public void start(BundleContext context) throws Exception {
		
		LOGGER.info("Starting G2021 Gatetway...");
		
		notificationService = context.getServiceReference(NotificationHandler.class.getName());
		NotificationHandler notificationHandler =(NotificationHandler)context.getService(notificationService);
		MessageUtil.setNotificationHandler(notificationHandler);
		
		/*diagNotificationService = context.getServiceReference(DiagnosisNotificationHelper.class.getName());
		NotificationHandler diagNotificationHandler =(NotificationHandler)context.getService(diagNotificationService);
		MessageUtil.setDiagnosisNotificationHandler(diagNotificationHandler);*/
		
		cacheService = context.getServiceReference(CacheProvider.class.getName());
		CacheProvider cacheProvider = (CacheProvider) context.getService(cacheService);
		CacheUtil.setCacheProvider(cacheProvider);
		
		eosManagerService = context.getServiceReference(DeviceManager.class.getName());
		DeviceManagerUtil.setG2021DeviceManager((DeviceManager) context.getService(eosManagerService));
		
		MessageNotifier.getInstance().addMessageListener(new MessageHandler());
		//MessageNotifier.getInstance().addMessageListener(new RabbitMQNotifier());
		MessageNotifier.getInstance().addMessageListener(new AlarmHandler());
		
		String startCommandTracker = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.COMMAND_REGISTER_REQUIRED);
		
		if(startCommandTracker.equalsIgnoreCase(REQUIRED)){
			LOGGER.info("Starting command tracker ...{}",commandConsumer.getClass());
			DeviceManagerUtil.getG2021DeviceManager().registerCommandListener(new G2021CommandListener(), SupportedDevices.getGateway(Devices.EDCP));
			LOGGER.info("Command tracker started !!");
		}else{
			LOGGER.info("Command tracker not enabled, probably starting a data server instance !!");
		}
		
		
		listeners.startFromConfiguration();
		LOGGER.info("Started G2021 Gateway ...");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Goodbye G2021..!!");
		context.ungetService(notificationService);
		//context.ungetService(diagNotificationService);
		context.ungetService(cacheService);
		commandConsumer.stopListen();
		LOGGER.info("Terminating G2021 Listeners..!!");
		listeners.stopServers();
		
		
		DeviceManagerUtil.getG2021DeviceManager().deregisterCommandListener( SupportedDevices.getGateway(Devices.EDCP));
		LOGGER.info("G2021 Listeners Terminated..!!");
	}
}

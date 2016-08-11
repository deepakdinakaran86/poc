/**
 * 
 */
package com.pcs.device.gateway.hobbies.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.hobbies.bootstrap.Listeners;
import com.pcs.device.gateway.hobbies.bundle.utils.CacheUtil;
import com.pcs.device.gateway.hobbies.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.hobbies.bundle.utils.MessageUtil;
import com.pcs.device.gateway.hobbies.event.handler.MessageHandler;
import com.pcs.device.gateway.hobbies.event.handler.MqttDeviceMessageEventHandler;
import com.pcs.device.gateway.hobbies.event.notifier.DeviceMqttMessageNotifier;
import com.pcs.device.gateway.hobbies.event.notifier.MessageNotifier;
import com.pcs.device.gateway.hobbies.handler.mqtt.HobbiesCommandTransmitter;
import com.pcs.device.gateway.hobbies.handler.mqtt.HobbiesMqttCommandResponseConnector;
import com.pcs.device.gateway.hobbies.handler.mqtt.HobbiesMqttDataConnector;
import com.pcs.device.gateway.hobbies.handler.mqtt.HobbiesMqttEventConnector;
import com.pcs.device.gateway.hobbies.utils.SupportedDevices;
import com.pcs.device.gateway.hobbies.utils.SupportedDevices.Devices;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.writeback.listener.CommandListener;
import com.pcs.saffron.notification.handler.NotificationHandler;
import com.pcs.saffron.notification.handler.implementation.DiagnosisNotificationHelper;

/**
 * @author Aneesh
 *
 */
public class HobbiesActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(HobbiesActivator.class);
	ServiceReference notificationService = null;
	ServiceReference diagNotificationService = null;
	ServiceReference cacheService = null;
	ServiceReference eosManagerService = null;
	Listeners listeners = new Listeners();
	HobbiesMqttDataConnector mqttDataConnector = null;
	HobbiesMqttEventConnector mqttEventConnector = null;
	HobbiesMqttCommandResponseConnector mqttCommandResponseConnector = null;
	
	public void start(BundleContext context) throws Exception {
		
		LOGGER.info("Starting Hobbies Gatetway...");
		
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
		DeviceManager hobbiesDeviceManager = (DeviceManager) context.getService(eosManagerService);
		DeviceManagerUtil.setHobbiesDeviceManager(hobbiesDeviceManager);
		
		MessageNotifier.getInstance().addMessageListener(new MessageHandler());
		DeviceMqttMessageNotifier.getInstance().addMessageListener(new MqttDeviceMessageEventHandler());
		listeners.startFromConfiguration();
		
		mqttDataConnector = new HobbiesMqttDataConnector();
		mqttDataConnector.connectToMQTT();
		
		mqttEventConnector = new HobbiesMqttEventConnector();
		mqttEventConnector.connectToMQTT();
		
		mqttCommandResponseConnector = new HobbiesMqttCommandResponseConnector();
		mqttCommandResponseConnector.connectToMQTT();
		
		Gateway androidGateway = SupportedDevices.getGateway(Devices.ANDROID);
		Gateway iosGateway = SupportedDevices.getGateway(Devices.IOS);
		CommandListener commandListener = new HobbiesCommandTransmitter();
		
		hobbiesDeviceManager.registerCommandListener(commandListener, iosGateway);
		hobbiesDeviceManager.registerCommandListener(commandListener, androidGateway);
		
		LOGGER.info("Started Hobbies Gateway ...");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Goodbye Hobbies..!!");
		context.ungetService(notificationService);
		context.ungetService(cacheService);
		LOGGER.info("Terminating Hobbies Listeners..!!");
		listeners.stopServers();
		LOGGER.info("Hobbies Listeners Terminated..!!");
		LOGGER.info("Terminating Hobbies MQTT subscription..!!");
		mqttDataConnector.disconectMQTT();
		mqttEventConnector.disconectMQTT();
		mqttCommandResponseConnector.disconectMQTT();
		
		Gateway androidGateway = SupportedDevices.getGateway(Devices.ANDROID);
		Gateway iosGateway = SupportedDevices.getGateway(Devices.IOS);
		DeviceManagerUtil.getHobbiesDeviceManager().deregisterCommandListener(iosGateway);
		DeviceManagerUtil.getHobbiesDeviceManager().deregisterCommandListener(androidGateway);
		
		LOGGER.info("Hobbies MQTT subscription Terminated..!!");
	}


}

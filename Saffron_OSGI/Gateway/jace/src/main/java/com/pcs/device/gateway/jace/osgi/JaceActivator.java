/**
 * 
 */
package com.pcs.device.gateway.jace.osgi;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.jace.beans.JaceHistoryScheduler;
import com.pcs.device.gateway.jace.beans.JaceScheduler;
import com.pcs.device.gateway.jace.bootstrap.Listeners;
import com.pcs.device.gateway.jace.bundle.utils.CacheUtil;
import com.pcs.device.gateway.jace.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.jace.bundle.utils.JaceSchedulerUtil;
import com.pcs.device.gateway.jace.bundle.utils.MessageUtil;
import com.pcs.device.gateway.jace.event.handler.MessageHandler;
import com.pcs.device.gateway.jace.event.notifier.MessageNotifier;
import com.pcs.device.gateway.jace.utils.SupportedDevices;
import com.pcs.device.gateway.jace.utils.SupportedDevices.Devices;
import com.pcs.device.gateway.jace.writeback.JaceWritebackListener;
import com.pcs.device.gateway.jace.writeback.api.beans.JaceCommand;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.notification.handler.NotificationHandler;
import com.pcs.saffron.notification.handler.implementation.DiagnosisNotificationHelper;
import com.pcs.saffron.scheduler.util.SchedulerUtil;

/**
 * @author Aneesh
 *
 */
public class JaceActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(JaceActivator.class);
	ServiceReference notificationService = null;
	ServiceReference diagNotificationService = null;
	ServiceReference cacheService = null;
	ServiceReference eosManagerService = null;
	ServiceReference schedulerService = null;
	Listeners listeners = new Listeners();
	String schedulerId = null;
	private Scheduler sched;
	
	public void start(BundleContext context) throws Exception {
		
		LOGGER.info("Starting Jace Gatetway...");
		
		schedulerService = context.getServiceReference(SchedulerUtil.class.getName());
		SchedulerUtil schedulerUtil = (SchedulerUtil)context.getService(schedulerService);
		JaceSchedulerUtil.setSchedulerUtil(schedulerUtil);
		
		sched = schedulerUtil.getSched();
		schedulerId = "jace"+System.currentTimeMillis();
		JobDetail dataPollJob = newJob(JaceScheduler.class)   
				.withIdentity("data", schedulerId)   
				.build();

		CronTrigger dataTrigger = newTrigger().withIdentity("datacrontrigger", schedulerId).withSchedule(cronSchedule("0 */5 * * * ?")).build();
		
		JobDetail hisPollJob = newJob(JaceHistoryScheduler.class)   
				.withIdentity("history", schedulerId)   
				.build();

		CronTrigger hisTrigger = newTrigger().withIdentity("historycrontrigger", schedulerId).withSchedule(cronSchedule("0 */2 * * * ?")).build();
		
		sched.scheduleJob(dataPollJob, dataTrigger);
		sched.scheduleJob(hisPollJob, hisTrigger);
		sched.start();
		
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
		DeviceManagerUtil.setJaceDeviceManager((DeviceManager) context.getService(eosManagerService));
		
		MessageNotifier.getInstance().addMessageListener(new MessageHandler());
		
		DeviceManagerUtil.getJaceDeviceManager().registerCommandListener(new JaceWritebackListener(), SupportedDevices.getGateway(Devices.JACE_CONNECTOR));
		
		listeners.startFromConfiguration();
		LOGGER.info("Started Jace Gateway ...");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Goodbye Jace..!!");
		sched.unscheduleJob(new TriggerKey(schedulerId));
		context.ungetService(notificationService);
		context.ungetService(schedulerService);
		context.ungetService(cacheService);
		LOGGER.info("Terminating Jace Listeners..!!");
		DeviceManagerUtil.getJaceDeviceManager().deregisterCommandListener(SupportedDevices.getGateway(Devices.JACE_CONNECTOR));
		listeners.stopServers();
		LOGGER.info("Jace Listeners Terminated..!!");
	}


}

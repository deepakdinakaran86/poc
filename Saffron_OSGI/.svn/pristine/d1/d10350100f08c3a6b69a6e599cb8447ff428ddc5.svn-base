package com.pcs.device.gateway.enevo.onecollect.osgi;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.enevo.onecollect.osgi.utils.EnevoSchedulerUtil;
import com.pcs.device.gateway.enevo.onecollect.scheduler.job.EnevoScheduler;
import com.pcs.saffron.scheduler.util.SchedulerUtil;

public class EnevoActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnevoActivator.class);
	
	ServiceReference schedulerService = null;
	private Scheduler sched;
	
	public void start(BundleContext context) throws Exception {
		LOGGER.info("Starting Enevo");
		schedulerService = context.getServiceReference(SchedulerUtil.class.getName());
		SchedulerUtil schedulerUtil = (SchedulerUtil)context.getService(schedulerService);
		EnevoSchedulerUtil.setSchedulerUtil(schedulerUtil);
		
		sched = schedulerUtil.getSched();
		String id = "enevo"+System.currentTimeMillis();
		JobDetail job = newJob(EnevoScheduler.class)   
				.withIdentity("enevo", id)   
				.build();

		CronTrigger cronTrigger = newTrigger().withIdentity("enevocrontrigger", id).withSchedule(cronSchedule("0 */1 * * * ?")).build();
		
		sched.scheduleJob(job, cronTrigger);
		sched.start();
		
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Stoping Enevo");
		context.ungetService(schedulerService);
	}

}

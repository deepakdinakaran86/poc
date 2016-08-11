package com.pcs.ccd.heartbeat.scheduler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class SchedulerUtil {

	private static SchedulerFactory schedulerFactory = null;   
	private static Scheduler sched = null;
	
	private static final String HEARTBEAT_JOB = "heartbeatMsg";
	private static final String MINUTE_GROUP = "group1";
	
	public static void init() throws Exception{
		
		Properties quatzProps = loadPropertyFile();
		
		 schedulerFactory = new StdSchedulerFactory(quatzProps);   
		
		sched = schedulerFactory.getScheduler();
		JobKey jobKey = new JobKey(HEARTBEAT_JOB,MINUTE_GROUP);
		sched.getJobDetail(jobKey);
		if(sched.getJobDetail(jobKey)==null){
			JobDetail job = newJob(com.pcs.ccd.heartbeat.scheduler.job.Scheduler.class)   
					.withIdentity("heartbeatMsg", "group1")   
					.build();
			
			// every 15 minutes
			CronTrigger cronTrigger = newTrigger().withIdentity("heartbeatMsgTrigger", "group1").withSchedule(cronSchedule("0 0/15 * * * ?")).build();
			
			sched.scheduleJob(job, cronTrigger);
		}
		sched.startDelayed(5);
		
	}
	
	private static Properties loadPropertyFile(){
		Properties prop = new Properties();
		InputStream in = SchedulerUtil.class.getClassLoader().getResourceAsStream("quartz.properties");
		try {
	        prop.load(in);
        } catch (IOException e) {
	        e.printStackTrace();
        }
		return prop;
	}
	
	public static void shutdown() throws Exception{
		if(sched != null)
			sched.shutdown(true);
		sched = null;
		schedulerFactory = null;
	}

	public static void main(String[] args) throws Exception {
		//init();
		SchedulerUtil s = new SchedulerUtil();
	}
}

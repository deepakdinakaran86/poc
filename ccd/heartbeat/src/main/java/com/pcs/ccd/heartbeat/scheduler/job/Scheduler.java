package com.pcs.ccd.heartbeat.scheduler.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.ccd.heartbeat.processor.HeartbeatSender;


public class Scheduler implements Job{

	private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		Date startTime = Calendar.getInstance().getTime();
		LOGGER.info("Hearbeat sender executing at : {}" , startTime);
		HeartbeatSender.start();
		Date endTime = Calendar.getInstance().getTime();
		LOGGER.info("Execution completed at : {}, total time elapsed : {} seconds" , endTime,(endTime.getTime()-startTime.getTime())/1000);
	}
	
}

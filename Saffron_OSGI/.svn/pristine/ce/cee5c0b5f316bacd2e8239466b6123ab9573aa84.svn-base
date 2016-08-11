package com.pcs.device.gateway.enevo.onecollect.scheduler.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.enevo.onecollect.api.BinUpdater;

public class EnevoScheduler implements Job{

	private static final Logger LOGGER = LoggerFactory.getLogger(EnevoScheduler.class);
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		Date startTime = Calendar.getInstance().getTime();
		LOGGER.info("Executing at : {}" , startTime);
		BinUpdater.checkForBinUpdate();
		Date endTime = Calendar.getInstance().getTime();
		LOGGER.info("Execution completed at : {}, total time elapsed : {} seconds" , endTime,(endTime.getTime()-startTime.getTime())/1000);
	}
	
}

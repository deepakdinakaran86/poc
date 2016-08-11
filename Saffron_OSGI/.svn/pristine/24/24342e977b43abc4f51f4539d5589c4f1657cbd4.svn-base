package com.pcs.saffron.scheduler.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerUtil.class);
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();   
	private static Scheduler sched = null;
	
	public SchedulerUtil(){
		try {
			init();
		} catch (Exception e) {
			LOGGER.error("Error initialize scheduler service",e);
		}
	}
	
	private static void init() throws Exception{
		sched = schedulerFactory.getScheduler();
	}

	public static void shutdown() throws Exception{
		if(sched != null)
			sched.shutdown(true);
		sched = null;
		schedulerFactory = null;
	}

	public Scheduler getSched() {
		return sched;
	}
}

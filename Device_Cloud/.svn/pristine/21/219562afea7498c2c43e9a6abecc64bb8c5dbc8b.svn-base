package com.pcs.saffron.g2021.SimulatorEngine.DS.schedular;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class TestScheduler {
	
	private static 	Scheduler scheduler;
	public static void main( String[] args ) throws Exception
    {
		
       	startSc();
       	Thread.sleep(3000);
       	stopSc();

    }
	
	private static void startSc(){
		JobDetail job = new JobDetail();
    	job.setName("dummyJobName");
    	job.setJobClass(Hello.class);
    	
    	//configure the scheduler time
    	SimpleTrigger trigger = new SimpleTrigger();
    	trigger.setName("dummyTriggerName");
    	trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
    	trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    	trigger.setRepeatInterval(1000);
    	
    	//schedule it
    
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
	    	scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	private static void stopSc(){
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}	

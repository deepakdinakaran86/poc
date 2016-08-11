package com.pcs.saffron.g2021.SimulatorEngine.DS.schedular;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Hello implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Hello Quartz!");	
	}
	
	
}

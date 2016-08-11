package com.pcs.saffron.g2021.SimulatorEngine.DS.schedular;

import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor.TimeBasedDataProcessor;

 public class TimeBasedTask implements Runnable {
	
	private RealTimeDataPoints timerDataPoints;

	public TimeBasedTask(RealTimeDataPoints realTimeDataPoints) {
		this.timerDataPoints = realTimeDataPoints;
	}
	

	public void run() {
		//System.out.println("timer schedular has started.."+Thread.currentThread().getName());
		DataServer.presentscheduleTime = System.currentTimeMillis();
		for (DataPoints dPoint : timerDataPoints.getDataPoints()) {
			dPoint.setAllowForProcessing(false);
			dPoint.setHasAlarm(false);
		}
		TimeBasedDataProcessor.processTimeBasedData(timerDataPoints);
	}
}

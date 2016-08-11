package com.pcs.saffron.g2021.SimulatorEngine.DS.schedular;

import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor.COVDataProcessor;

public class COVTask implements Runnable {
	
	private RealTimeDataPoints covDataPoints;

	public COVTask(RealTimeDataPoints realTimeDataPoints) {
		this.covDataPoints = realTimeDataPoints;
	}

	public void run() {
		//System.out.println("cov schedular has started.."+Thread.currentThread().getName());
		for (DataPoints dPoint : covDataPoints.getDataPoints()) {
			dPoint.setAllowForProcessing(false);
			dPoint.setHasAlarm(false);
		}
		
		COVDataProcessor.processCOVBasedData(covDataPoints);
	}
}

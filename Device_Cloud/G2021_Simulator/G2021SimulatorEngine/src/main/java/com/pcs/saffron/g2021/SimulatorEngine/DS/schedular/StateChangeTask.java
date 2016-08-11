package com.pcs.saffron.g2021.SimulatorEngine.DS.schedular;

import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor.StateChangeDataProcessor;

public class StateChangeTask implements Runnable {
	
	private RealTimeDataPoints stateChangeDataPoints;

	public StateChangeTask(RealTimeDataPoints realTimeDataPoints) {
		this.stateChangeDataPoints = realTimeDataPoints;
	}

	public void run() {
		//System.out.println("state change schedular has started..");
		for (DataPoints dPoint : stateChangeDataPoints.getDataPoints()) {
			dPoint.setAllowForProcessing(false);
			dPoint.setHasAlarm(false);
		}
		StateChangeDataProcessor.processStateChangeData(stateChangeDataPoints);
	}
}

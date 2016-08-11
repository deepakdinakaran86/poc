package com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor;

import java.util.Iterator;

import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PAlarmState;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PAlarmType;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PDAQType;

public class StateChangeDataProcessor {	
	

	public static void processStateChangeData(RealTimeDataPoints stateProcessDataPoints){	
		
		System.out.println("Statechange task has started");
		
		try{
			
			if(stateProcessDataPoints != null){				
				
				Iterator<DataPoints> itr = stateProcessDataPoints.getDataPoints().iterator();				
				
				while(itr.hasNext()){	
					DataPoints realData = itr.next();	
					DataServerProcessor.updateDataPointsWithRandomValues(realData);	
					DataServerProcessor.processStateChange(realData);
					if(realData.isAllowForProcessing()){
						realData.setPointCurrentValue(realData.getGeneratedValue());
						if(realData.getPoints().getpAlarmType() == PAlarmType.STATE_CHANGE.getValue()){
							realData.setHasAlarm(true);
							realData.setCurrentAlarmState(PAlarmState.STATECHANGE);						
						}
					}
				}
				
				RealTimeDataProcessor.processRealTimeData(stateProcessDataPoints);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

}

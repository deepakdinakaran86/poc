package com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor;

import java.util.Iterator;

import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;

public class COVDataProcessor{	

	
	
	public static void processCOVBasedData(RealTimeDataPoints covProcessDataPoints){
		
		System.out.println("COV task has started");
		
		try{
			
			if(covProcessDataPoints != null){
				
				Iterator<DataPoints> itr = covProcessDataPoints.getDataPoints().iterator();				
				
				while(itr.hasNext()){	
					DataPoints realData = itr.next();	
					DataServerProcessor.updateDataPointsWithRandomValues(realData);	
					DataServerProcessor.processStateChange(realData);
					if(realData.isAllowForProcessing()){
						DataServerProcessor.checkForAlarm(realData);
					}
				}
				
				RealTimeDataProcessor.processRealTimeData(covProcessDataPoints);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

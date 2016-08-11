package com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor;

import java.util.Iterator;

import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.schedular.DataServer;

public class TimeBasedDataProcessor{

	public static void processTimeBasedData(RealTimeDataPoints TimeDataPoints){
		
		System.out.println("timer task has started");
				
		try{
			if(TimeDataPoints != null){	
				
				Iterator<DataPoints> itr = TimeDataPoints.getDataPoints().iterator();
				
				while(itr.hasNext()){				
					DataPoints realData = itr.next();					
					if(realData.getNextFireTime() <= DataServer.presentscheduleTime){
						System.out.println("time condition satisfied.."+DataServer.presentscheduleTime);
						realData.setNextFireTime(realData.getNextFireTime()+(realData.getPoints().getpDAQTime()*60*1000));
						DataServerProcessor.updateDataPointsWithRandomValues(realData);
						realData.setAllowForProcessing(true);						
						DataServerProcessor.checkForAlarm(realData);
						
					}else{
						System.out.println("time condition failed..."+DataServer.presentscheduleTime);
					}	
				}
							
				RealTimeDataProcessor.processRealTimeData(TimeDataPoints);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}catch (Throwable e) {
			e.printStackTrace();
		}		

	}

}

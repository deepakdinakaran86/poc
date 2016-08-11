package com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor;

import java.util.Vector;

import com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler.AlarmHandler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler.RealTimeDataHandler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.LockUtil;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.eventQueue.EventQueueData;
import com.pcs.saffron.g2021.SimulatorEngine.DS.eventQueue.EventQueueDispatcher;

public class RealTimeDataProcessor {	
	 
	public static void processRealTimeData(RealTimeDataPoints dataPoints){
		
		//DataServerProcessor.printValuesOfList(dataPoints);
		
		synchronized (LockUtil.LOCK) {
			if(dataPoints != null && dataPoints.getDataPoints() != null && (!dataPoints.getDataPoints().isEmpty())){				
				sendDataToServer(dataPoints);
				checkAlaramType(dataPoints);
			}
		}	
		
	}

	private static void sendDataToServer(RealTimeDataPoints realData) {
		try {
			
			RealTimeDataPoints tobeProcessed = null;
			Vector<DataPoints> dataPointList = new Vector<DataPoints>();
			for (DataPoints dataPoint : realData.getDataPoints()) {
				DataPoints dp = null;
				if(dataPoint.isAllowForProcessing()){
					dp = dataPoint;
					dataPointList.add(dp);
				}
			}
			if(dataPointList.size() > 0){
				tobeProcessed = new RealTimeDataPoints();
				tobeProcessed.setDataPoints(dataPointList);
			}
			 
			if(tobeProcessed != null){
				byte[] dataBuffer = RealTimeDataHandler.getData(tobeProcessed);
				if(dataBuffer != null){					
					//ServerMessageNotifier.getInstance().notifyDataServerRequests(SequenceNoGenerator.getSequenceNo(), dataBuffer);
					//TCPClient.sendMessageToServer(dataBuffer);
					pushDataToEventQueue(dataBuffer);
					
				}
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private static void checkAlaramType(RealTimeDataPoints realData) {

		try {
			
			for (DataPoints dataPoint : realData.getDataPoints()) {
				if(dataPoint.isAllowForProcessing()){					
					if(dataPoint.isHasAlarm()){
						sendAlarmToServer(dataPoint);	
					}			
								
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}
	
	
	private static void sendAlarmToServer(DataPoints dataPoint) {
		try {
			byte[] dataBuffer = AlarmHandler.getData(dataPoint);
			if(dataBuffer != null){
			//ServerMessageNotifier.getInstance().notifyDataServerRequests(SequenceNoGenerator.getSequenceNo(), dataBuffer);
			//TCPClient.sendMessageToServer(dataBuffer);
				
				pushDataToEventQueue(dataBuffer);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private static void pushDataToEventQueue(byte[] dataBuffer){
		EventQueueData data = new EventQueueData();
		data.setDataBuffer(dataBuffer);
		data.setRetrialCount(0);
		if(EventQueueDispatcher.getInstance().getData() != null){
			if(EventQueueDispatcher.getInstance().getData().size() < 50){
				EventQueueDispatcher.getInstance().pushtoQueue(data);
			}else{
				EventQueueDispatcher.getInstance().getData().poll();
				EventQueueDispatcher.getInstance().pushtoQueue(data);
			}
		}
	}


	
}

package com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor;

import java.util.HashMap;

import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PDAQType;

public class RealTimeDataMap {
	
	private static RealTimeDataMap realTimeDataObj = null;
	
	private HashMap<PDAQType, RealTimeDataPoints> dataPointsHMap = new HashMap<PDAQType, RealTimeDataPoints>();
	
	private RealTimeDataMap(){
		
	}

	public HashMap<PDAQType, RealTimeDataPoints> getDataPointsHMap() {
		return dataPointsHMap;
	}

	public void setDataPointsHMap(HashMap<PDAQType, RealTimeDataPoints> dataPointsHMap) {
		this.dataPointsHMap = dataPointsHMap;
	}

	public void add(PDAQType key , RealTimeDataPoints realDataPoints){
		dataPointsHMap.put(key, realDataPoints);
	}
	
	public static RealTimeDataMap getInstance(){
		if(realTimeDataObj == null){
			realTimeDataObj =  new RealTimeDataMap();
		}
		return realTimeDataObj;
	}	
	
}

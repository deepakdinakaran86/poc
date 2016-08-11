package com.pcs.saffron.g2021.SimulatorEngine.DS.schedular;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.PointsDiscoveryMesasge;
import com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClient.TCPClient;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor.DataServerProcessor;

public class DataServer {

	private Vector<Points> pointList = new Vector<Points>();
	private static RealTimeDataPoints realTimeData = new RealTimeDataPoints();
	private long timeBasedSchedulingTime;
	private long stateChangeSchedulingTime;
	private long covSchedulingTime;
	private int retrialCount;
	private int retrialTime;
	public static volatile long presentscheduleTime;


	public Vector<Points> getPointList() {
		return pointList;
	}

	public void setPointList(Vector<Points> pointList) {
		this.pointList = pointList;
	}
	
	public long getTimeBasedSchedulingTime() {
		return timeBasedSchedulingTime;
	}

	public void setTimeBasedSchedulingTime(long timeBasedSchedulingTime) {
		this.timeBasedSchedulingTime = timeBasedSchedulingTime;
	}

	public long getStateChangeSchedulingTime() {
		return stateChangeSchedulingTime;
	}

	public void setStateChangeSchedulingTime(long stateChangeSchedulingTime) {
		this.stateChangeSchedulingTime = stateChangeSchedulingTime;
	}

	public long getCovSchedulingTime() {
		return covSchedulingTime;
	}

	public void setCovSchedulingTime(long covSchedulingTime) {
		this.covSchedulingTime = covSchedulingTime;
	}

	public int getRetrialCount() {
		return retrialCount;
	}

	public void setRetrialCount(int retrialCount) {
		this.retrialCount = retrialCount;
	}

	public int getRetrialTime() {
		return retrialTime;
	}

	public void setRetrialTime(int retrialTime) {
		this.retrialTime = retrialTime;
	}

	public void startDataServerProcessing(long timeBasedSchedulingTime,long stateChangeTime,long covTime,int retrialCount,int retrailTime){		
		DataServerProcessor dsp =  new DataServerProcessor();
		realTimeData.setDataPoints(DataServerProcessor.getRealTimeDataPoints(pointList, System.currentTimeMillis()));
		DataServerProcessor.prepareDAQTypeBasedHashMap(realTimeData);
		dsp.processRealTimeData(timeBasedSchedulingTime,stateChangeTime,covTime,retrialCount,retrailTime);
	}

	public static void main(String[] args) {
		DataServer ds = new DataServer();

		
		try { TCPClient.openClientConnection("10.234.31.156", 8191);
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
		

		ds.test();
		ds.setTimeBasedSchedulingTime(30);
		ds.setStateChangeSchedulingTime(30);
		ds.setCovSchedulingTime(30);
		ds.setRetrialTime(1000);
		ds.setRetrialCount(5);		
		ds.startRealDataProcessing(ds);
		
	}

	public void test() {
		ObjectMapper mapper = new ObjectMapper();
		PointsDiscoveryMesasge pointsInfo = null;
		try {
			pointsInfo = mapper.readValue(new File("C://PCS/devicePoints.json"), PointsDiscoveryMesasge.class);
			Vector<Points> v = new Vector<Points>();
			v.addAll(Arrays.asList(pointsInfo.getPoints()));
			setPointList(v);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startRealDataProcessing(DataServer ds) {
		ds.startDataServerProcessing(ds.getTimeBasedSchedulingTime(),ds.getStateChangeSchedulingTime(),ds.getCovSchedulingTime(),ds.getRetrialCount(),ds.getRetrialTime());
	}
	
	public static void stopRealDataProcessing(){
		DataServerProcessor dsp =  new DataServerProcessor();
		dsp.stopProceesingRealTimeData();
	}

}

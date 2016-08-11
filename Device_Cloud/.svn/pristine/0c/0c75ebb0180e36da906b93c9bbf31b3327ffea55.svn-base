package com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PAlarmState;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PAlarmType;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PDAQType;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PDATAType;
import com.pcs.saffron.g2021.SimulatorEngine.DS.generator.BooleanGenerator;
import com.pcs.saffron.g2021.SimulatorEngine.DS.generator.FloatGenerator;
import com.pcs.saffron.g2021.SimulatorEngine.DS.generator.NumberGenerator;
import com.pcs.saffron.g2021.SimulatorEngine.DS.generator.RandomGenerator;
import com.pcs.saffron.g2021.SimulatorEngine.DS.generator.StringGenerator;
import com.pcs.saffron.g2021.SimulatorEngine.DS.schedular.COVTask;
import com.pcs.saffron.g2021.SimulatorEngine.DS.schedular.DataPublisher;
import com.pcs.saffron.g2021.SimulatorEngine.DS.schedular.StateChangeTask;
import com.pcs.saffron.g2021.SimulatorEngine.DS.schedular.TimeBasedTask;

public class DataServerProcessor {

	private static ScheduledExecutorService timeBasedscheduler = Executors.newSingleThreadScheduledExecutor();
	private static ScheduledExecutorService stateChangescheduler = Executors.newSingleThreadScheduledExecutor();
	private static ScheduledExecutorService COVscheduler = Executors.newSingleThreadScheduledExecutor();
	private static ScheduledExecutorService timerServiceScheduler = Executors.newSingleThreadScheduledExecutor();
	public static volatile long presentscheduleTime;
	private static ScheduledFuture<?> timeHandle = null;
	private static ScheduledFuture<?> scHandle = null;
	private static ScheduledFuture<?> covHandle = null;
	private static ScheduledFuture<?> timerHandle = null;
	private static Timer timer = null;
	public static int retrailCount = 0;
	public static int retrailTime = 0;

	public static Vector<DataPoints> getRealTimeDataPoints(Vector<Points> points, long time) {
		DataPoints dPoints = null;
		Vector<DataPoints> dataPoints = new Vector<DataPoints>();

		for (Points point : points) {
			dPoints = new DataPoints();
			long nextTime = point.getpDAQTime() * 60 * 1000;
			dPoints.setPoints(point);
			dPoints.setNextFireTime(nextTime + time);
			dPoints.setPointCurrentValue(null);
			dPoints.setGeneratedValue(null);
			dataPoints.add(dPoints);
		}
		return dataPoints;
	}

	public void processRealTimeData(long timeBasedSchedulingTime,long stateChangeTime,long covTime,int inputRetrialCnt,int inputRetrailTime ) {

		Iterator<PDAQType> iterator = RealTimeDataMap.getInstance().getDataPointsHMap().keySet().iterator();

		//printValuesOfHashMap(RealTimeDataMap.getInstance().getDataPointsHMap());

		System.out.println("-------------------------------------");		
		retrailCount = inputRetrialCnt;
		retrailTime = inputRetrailTime;
		startTimerForEventQueue();

		while (iterator.hasNext()) {
			PDAQType key = iterator.next();
			if (key == PDAQType.TIMEBASED) {				
				TimeBasedTask task = new TimeBasedTask(RealTimeDataMap.getInstance().getDataPointsHMap().get(key));
				timeHandle = timeBasedscheduler.scheduleAtFixedRate(task, 0, timeBasedSchedulingTime,TimeUnit.SECONDS);
				
			} else if (key == PDAQType.STATECHANGE) {

				StateChangeTask task = new StateChangeTask(RealTimeDataMap.getInstance().getDataPointsHMap().get(key));
				  scHandle = stateChangescheduler.scheduleAtFixedRate(task, 0, stateChangeTime,	TimeUnit.SECONDS);
				

			} else if (key == PDAQType.COV) {

				COVTask task = new COVTask(RealTimeDataMap.getInstance().getDataPointsHMap().get(key));
				 covHandle = COVscheduler.scheduleAtFixedRate(task, 0, covTime, TimeUnit.SECONDS);				
			}
		}

	}
	
	private void startTimerForEventQueue() {
		DataPublisher dataPub = new DataPublisher();
		//timer = new Timer();
	  // timer.scheduleAtFixedRate(dataPub, 0, 5*1000);
		
		timerHandle = timerServiceScheduler.scheduleAtFixedRate(dataPub, 0, 5,TimeUnit.SECONDS);
	}

	public void stopProceesingRealTimeData(){	
		try{
			timeBasedscheduler.schedule(new Runnable() {
				public void run() {				
					System.out.println("came into  timeBasedscheduler nested schedular....");
					timeHandle.cancel(true);
				}
			}, 1 * 1, TimeUnit.SECONDS);

			stateChangescheduler.schedule(new Runnable() {
				public void run() {
					scHandle.cancel(true);
				}
			}, 1 * 1, TimeUnit.SECONDS);

			COVscheduler.schedule(new Runnable() {
				public void run() {
					covHandle.cancel(true);
				}
			}, 1 * 1, TimeUnit.SECONDS);
			
			timerServiceScheduler.schedule(new Runnable() {
				public void run() {
					timerHandle.cancel(true);
				}
			}, 1 * 1, TimeUnit.SECONDS);
			
			//timer.cancel();
			//timer.purge();			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public static void prepareDAQTypeBasedHashMap(RealTimeDataPoints realTimePoints) {

		try {
			Vector<DataPoints> tbPoints = new Vector<DataPoints>();
			Vector<DataPoints> scPoints = new Vector<DataPoints>();
			Vector<DataPoints> covPoints = new Vector<DataPoints>();
			for (DataPoints dPoint : realTimePoints.getDataPoints()) {
				if (dPoint.getPoints().getpDAQType() == PDAQType.TIMEBASED.getValue()) {
					tbPoints.add(dPoint);
				} else if (dPoint.getPoints().getpDAQType() == PDAQType.STATECHANGE.getValue()) {
					scPoints.add(dPoint);
				} else if (dPoint.getPoints().getpDAQType() == PDAQType.COV.getValue()) {
					covPoints.add(dPoint);
				}
			}
			RealTimeDataMap.getInstance().add(PDAQType.TIMEBASED, new RealTimeDataPoints(tbPoints));
			RealTimeDataMap.getInstance().add(PDAQType.STATECHANGE, new RealTimeDataPoints(scPoints));
			RealTimeDataMap.getInstance().add(PDAQType.COV, new RealTimeDataPoints(covPoints));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateDataPointsWithRandomValues(DataPoints realData) {
		try {

			if (realData.getPoints().getpDataType() == PDATAType.Boolean.getValue()) {
				RandomGenerator rg = new BooleanGenerator();
				Boolean generatedValue = (Boolean) rg.getRandomGeneratedValue();
				realData.setGeneratedValue(generatedValue);
			} else if (realData.getPoints().getpDataType() == PDATAType.Short.getValue()
					|| realData.getPoints().getpDataType() == PDATAType.Integer.getValue() || realData.getPoints().getpDataType() == PDATAType.Long.getValue()) {
				RandomGenerator rg = new NumberGenerator();
				Short generatedValue = (Short) rg.getRandomGeneratedValue();
				realData.setGeneratedValue(generatedValue);

			} else if (realData.getPoints().getpDataType() == PDATAType.Float.getValue()) {
				RandomGenerator rg = new FloatGenerator();
				Float generatedValue = (Float) rg.getRandomGeneratedValue();
				realData.setGeneratedValue(generatedValue);
			} else if (realData.getPoints().getpDataType() == PDATAType.String.getValue()) {
				RandomGenerator rg = new StringGenerator();
				String generatedValue = (String) rg.getRandomGeneratedValue();
				realData.setGeneratedValue(generatedValue);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void printValuesOfHashMap(HashMap<PDAQType, RealTimeDataPoints> dataPointsHMap) {
		try {
			Iterator<PDAQType> itr = RealTimeDataMap.getInstance().getDataPointsHMap().keySet().iterator();

			while (itr.hasNext()) {
				PDAQType key = itr.next();
				RealTimeDataPoints dataPoints = RealTimeDataMap.getInstance().getDataPointsHMap().get(key);
				if (dataPoints != null) {
					for (DataPoints dPoiints : dataPoints.getDataPoints()) {
						System.out.println("point Id " + dPoiints.getPoints().getpID());
						System.out.println("point current value " + dPoiints.getPointCurrentValue());
						System.out.println("point generated Id " + dPoiints.getGeneratedValue());
						System.out.println("point next fire alarm time " + dPoiints.getNextFireTime());
					}
				}

			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void printValuesOfList(RealTimeDataPoints dataPoints){
		if (dataPoints != null) {
			for (DataPoints dPoiints : dataPoints.getDataPoints()) {
				System.out.println("point Id " + dPoiints.getPoints().getpID());
				System.out.println("point current value " + dPoiints.getPointCurrentValue());
				System.out.println("point generated Id " + dPoiints.getGeneratedValue());
				System.out.println("point next fire alarm time " + dPoiints.getNextFireTime());
			}
		}
	}
	
	public static void processStateChange(DataPoints realData) {

		switch (PDATAType.getDataType(realData.getPoints().getpDataType())) {
		case Boolean:
			if (realData.getPointCurrentValue() == null
					|| ((Boolean) realData.getPointCurrentValue() != (Boolean) realData.getGeneratedValue())) {
				realData.setAllowForProcessing(true);				
			}
			break;

		case Short:
			if (realData.getPointCurrentValue() == null
					|| ((Short) realData.getPointCurrentValue() != (Short) realData.getGeneratedValue())) {
				realData.setAllowForProcessing(true);
			}
			break;
		case Integer:
			if (realData.getPointCurrentValue() == null
					|| ((Short) realData.getPointCurrentValue() != (Short) realData.getGeneratedValue())) {
				realData.setAllowForProcessing(true);
			}
			break;
		case Float:
			if (realData.getPointCurrentValue() == null || (!String.valueOf(realData.getPointCurrentValue())
					.equals(String.valueOf(realData.getGeneratedValue())))) {
				realData.setAllowForProcessing(true);
			}
			break;
		case Long:
			if (realData.getPointCurrentValue() == null
					|| ((Short) realData.getPointCurrentValue() != (Short) realData.getGeneratedValue())) {
				realData.setAllowForProcessing(true);
			}
			break;
		case String:
			if (realData.getPointCurrentValue() == null
					|| (!((String) realData.getPointCurrentValue()).equals(((String) realData.getGeneratedValue())))) {
				realData.setAllowForProcessing(true);
			}
			break;
		default:
			break;
		}

	}
	
	public static void checkForAlarm(DataPoints realData){
		
		switch (PAlarmType.valueOfType(realData.getPoints().getpAlarmType())) {
		case STATE_CHANGE:
			processStateChangeAlarm(realData);
			break;
		case OUT_OF_RANGE:
			processCOVAlarm(realData);
			break;
		case NO_ALARM:
			updateCurrentValue(realData);

		default:
			break;
		}	
	}
	

	public static void processStateChangeAlarm(DataPoints realData) {

		switch (PDATAType.getDataType(realData.getPoints().getpDataType())) {
		case Boolean:
			if (realData.getPointCurrentValue() == null
					|| ((Boolean) realData.getPointCurrentValue() != (Boolean) realData.getGeneratedValue())) {					
				realData.setHasAlarm(true);
				realData.setCurrentAlarmState(PAlarmState.STATECHANGE);
				realData.setPointCurrentValue(realData.getGeneratedValue());
			}
			break;

		case Short:
			if (realData.getPointCurrentValue() == null
					|| ((Short) realData.getPointCurrentValue() != (Short) realData.getGeneratedValue())) {
								
				realData.setHasAlarm(true);
				realData.setCurrentAlarmState(PAlarmState.STATECHANGE);
				realData.setPointCurrentValue(realData.getGeneratedValue());
			}
			break;
		case Integer:
			if (realData.getPointCurrentValue() == null
					|| ((Short) realData.getPointCurrentValue() != (Short) realData.getGeneratedValue())) {
				realData.setHasAlarm(true);
				realData.setCurrentAlarmState(PAlarmState.STATECHANGE);
				realData.setPointCurrentValue(realData.getGeneratedValue());
			}
			break;
		case Float:
			if (realData.getPointCurrentValue() == null || (!String.valueOf(realData.getPointCurrentValue())
					.equals(String.valueOf(realData.getGeneratedValue())))) {
				realData.setHasAlarm(true);
				realData.setCurrentAlarmState(PAlarmState.STATECHANGE);
				realData.setPointCurrentValue(realData.getGeneratedValue());
			}
			break;
		case Long:
			if (realData.getPointCurrentValue() == null
					|| ((Short) realData.getPointCurrentValue() != (Short) realData.getGeneratedValue())) {
				realData.setHasAlarm(true);
				realData.setCurrentAlarmState(PAlarmState.STATECHANGE);
				realData.setPointCurrentValue(realData.getGeneratedValue());
			}
			break;
		case String:
			if (realData.getPointCurrentValue() == null
					|| (!((String) realData.getPointCurrentValue()).equals(((String) realData.getGeneratedValue())))) {
				realData.setHasAlarm(true);
				realData.setCurrentAlarmState(PAlarmState.STATECHANGE);
				realData.setPointCurrentValue(realData.getGeneratedValue());
			}
			break;
		default:
			break;
		}

	}

	public static void processCOVAlarm(DataPoints realData) {

		switch (PDATAType.getDataType(realData.getPoints().getpDataType())) {

		case Short:
			checkAlarmForDecimalValues(realData);
			break;
			
		case Integer:
			checkAlarmForDecimalValues(realData);
			break;
			
		case Float:
			float difference1 = 0.0f;
			boolean flag = false;
			if (realData.getPointCurrentValue() != null && (!String.valueOf(realData.getPointCurrentValue())
					.equals(String.valueOf(realData.getGeneratedValue())))) {
				difference1 = (Float) realData.getPointCurrentValue() - (Float) realData.getGeneratedValue();
				flag = true;
			}else{
				difference1 = (Float)realData.getGeneratedValue();
				flag = true;
			}
			
			if(flag){
				if (difference1 < realData.getPoints().getpAlarm_LT()) {
					realData.setHasAlarm(true);
					realData.setCurrentAlarmState(PAlarmState.ALARM_LT);
				} else if (difference1 > realData.getPoints().getpAlarm_UT()) {
					realData.setCurrentAlarmState(PAlarmState.ALARM_UT);
					realData.setHasAlarm(true);
				}
				
				if((realData.getPoints().getpAlarm_LT() < difference1) && (realData.getPoints().getpAlarm_UT() > difference1)){
					if(realData.getCurrentAlarmState() != null) {
						if(realData.getCurrentAlarmState().getValue() == PAlarmState.ALARM_LT.getValue() || realData.getCurrentAlarmState().getValue() == PAlarmState.ALARM_UT.getValue()){
							realData.setCurrentAlarmState(PAlarmState.NORMALIZED);
							realData.setHasAlarm(true);
						}
					}
				}				
				realData.setPointCurrentValue(realData.getGeneratedValue());	
			}			
			break;
			
		case Long:
			checkAlarmForDecimalValues(realData);
			break;
			
		default:
			break;
		}

	}
	
	private static void checkAlarmForDecimalValues(DataPoints realData){
		int difference = 0;
		boolean flag = false;
		if (realData.getPointCurrentValue() != null
				&& ((Short) realData.getPointCurrentValue() != (Short) realData.getGeneratedValue())) {
			difference = (Short) realData.getPointCurrentValue() - (Short) realData.getGeneratedValue();	
			flag = true;
			
		}else{
			difference = (Short) realData.getGeneratedValue();
			flag = true;
		}
		
		
		if(flag){
			if (difference < realData.getPoints().getpAlarm_LT()) {
				realData.setHasAlarm(true);
				realData.setCurrentAlarmState(PAlarmState.ALARM_LT);
			} else if (difference > realData.getPoints().getpAlarm_UT()) {
				realData.setCurrentAlarmState(PAlarmState.ALARM_UT);
				realData.setHasAlarm(true);
			}
			
			if((realData.getPoints().getpAlarm_LT() < difference) && (realData.getPoints().getpAlarm_UT() > difference)){
				if(realData.getCurrentAlarmState() != null) {
					if(realData.getCurrentAlarmState().getValue() == PAlarmState.ALARM_LT.getValue() || realData.getCurrentAlarmState().getValue() == PAlarmState.ALARM_UT.getValue()){
						realData.setCurrentAlarmState(PAlarmState.NORMALIZED);
						realData.setHasAlarm(true);
					}
				}
			}			
			realData.setPointCurrentValue(realData.getGeneratedValue());
		}		
	}
	
	private static void updateCurrentValue(DataPoints realData){
		realData.setPointCurrentValue(realData.getGeneratedValue());
	}
}

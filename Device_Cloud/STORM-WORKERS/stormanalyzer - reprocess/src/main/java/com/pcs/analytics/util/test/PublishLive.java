package com.pcs.analytics.util.test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.saffron.manager.api.configuration.bean.Status;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.Parameter;
import com.pcs.saffron.manager.enums.MessageType;

public class PublishLive {
	private static CorePublisher corePublisher = null;
	public static void main(String[] args) {
		init();
		//startThreads();
		/*for(int i=1;i<=40;i++){
			jmsPublish();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int i=1;i<=10;i++){
			jmsPublishFalse();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
		for(int i=1;i<=5;i++){
			jmsPublish();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void init(){
		try{
			String name = "distributor";
			//Registry registry = LocateRegistry.getRegistry("192.168.4.9",1099);
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			corePublisher = brokerManager.getPublisher(DistributorMode.JMS);
			//corePublisher.publishToQueue("NEW_QUEUE1",getLiveMessage());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void jmsPublish(){
		try {
			//corePublisher.publishToQueue("PERSIST_QUEUE_test",getDeviceMsg());
			//corePublisher.publishToTopic("8846aad0-dd46-11e5-8d98-5bec0b1ee5af", getMsg1());
			corePublisher.publishToTopic("srsdevice", datasourceDTOFalse());
			//corePublisher.publishToTopic("b201e210-cfbb-11e5-9581-5bec0b1ee5af", getMsg1());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static void jmsPublishGenerator(){
		try {
			corePublisher.publishToTopic("devTest", getMsg1());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static void jmsPublishFalse(){
		try {
			corePublisher.publishToTopic("Testdatasource12", datasourceDTOFalse());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static String getDeviceMsg(){
		String str = "{\"sourceId\":\"b6bc9394-7ccb-4216-abac-1e3f7e5fbaf9\",\"time\":\"1434881778000\",\"receivedTime\":\"null\",\"points\":[{\"pointId\":\"57\",\"pointName\":\"TestPoint_Analog_Readonly\",\"customTag\":\"TestPoint_Analog_Readonly\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"/0\",\"data\":\"47.38494\",\"status\":\"HEALTHY\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"CHANGE OF VALUE BASED ACQUISITION, THRESHOLD AT 0.0\",\"extensionType\":\"Acquisition Mode\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]}]} ";
		return str;
	}

	private static String getMsg(){
		MessageDTO dto = new MessageDTO();
		dto.setDatasourceName("Testdatasource12");
		List<ParameterDTO> parameterDTOs = new ArrayList<ParameterDTO>();
		ParameterDTO p1 = new ParameterDTO();
		p1.setName("latitude");
		p1.setDataType("double");
		p1.setValue(String.valueOf(getRandomLatitude()));
		p1.setTime(new Date().getTime());
		parameterDTOs.add(p1);
		
		ParameterDTO p2 = new ParameterDTO();
		p2.setName("longitude");
		p2.setDataType("double");
		p2.setValue(String.valueOf(getRandomLongitude()));
		p2.setTime(new Date().getTime());
		parameterDTOs.add(p2);
		dto.setParameters(parameterDTOs);

		Gson gson = new Gson();
		String jsonString = gson.toJson(dto);
		System.out.println("latitude,longitude " + jsonString);
		return jsonString;
	}
	
	private static String getMsg1(){
		
		DatasourceDTO d = new  DatasourceDTO();
		d.setDatasourceName("sayirdevice2001");
		d.setMessageType(MessageType.ALARM);
		d.setStatusMessage("Fuel level low");
		d.setStatus(Status.FALSE);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1455537158l);
		d.setReceivedTime(cal.getTime());
		d.setTime(1455537158l);
		List<Parameter>parameters = new ArrayList<Parameter>();
		Parameter p1=new Parameter();
		p1.setName("Fuel Level");
		p1.setDataType("Double");
		p1.setTime(getRandomTime());
		p1.setUnit("litre");
		p1.setValue(getRandomLatitude());
		parameters.add(p1);
		
		d.setParameters(parameters);
		Gson gson = new Gson();
		String jsonString = gson.toJson(d);
		System.out.println(gson.toJson(d));
		return jsonString;
	}
	
	

	private static float getRandomLatitude(){
		float upperBound = 26;
		float lowerBound = 25;
		Random random = new Random();
		float randomNumber = random.nextFloat() * (upperBound - lowerBound) + lowerBound;
		return randomNumber;
	}
	
	private static float getRandomLongitude(){
		float upperBound = 56;
		float lowerBound = 55;
		Random random = new Random();
		float randomNumber = random.nextFloat() * (upperBound - lowerBound) + lowerBound;
		return randomNumber;
	}
	
	
	private static int getRandom(){
		int upperBound = 100;
		int lowerBound = -10;
		Random random = new Random();
		int randomNumber = random.nextInt(upperBound - lowerBound) + lowerBound;
		return randomNumber;
	}
	
	private static long getRandomTime(){
		Random random = new Random();
		int millisInDay = 24*60*60*1000;
		return (long)random.nextInt(millisInDay);
	}
	
	private static Status getStatus(){
		int x=(Math.random()<0.5)?0:1;
		if(x==0){
			return Status.FALSE;
		}
		return Status.TRUE;
	}
	
	private static String datasourceDTO(){
		DatasourceDTO d = new  DatasourceDTO();
		d.setDatasourceName("testTopic");
		d.setMessageType(MessageType.MESSAGE);
		Calendar cal = Calendar.getInstance();
		//cal.setTimeInMillis(1455537158l);
		d.setReceivedTime(cal.getTime());
		d.setTime(getRandomTime());
		List<Parameter>parameters = new ArrayList<Parameter>();
		Parameter p1=new Parameter();
		p1.setName("Speed");
		p1.setDataType("Double");
		p1.setTime(getRandomTime());
		p1.setUnit("km\\h");
		p1.setValue(getRandomLatitude());
		parameters.add(p1);
		
		Parameter p2=new Parameter();
		p2.setName("Angle");
		p2.setDataType("Double");
		p2.setTime(getRandomTime());
		p2.setUnit("degrees");
		p2.setValue(getRandomLongitude());
		parameters.add(p2);
		
		/*Parameter p3=new Parameter();
		p3.setName("Generator Status");
		p3.setDataType("String");
		p3.setTime(getRandomTime());
		p3.setUnit("unitless");
		p3.setValue("ACTIVE");
		parameters.add(p3);*/
		
		
		d.setParameters(parameters);
		Gson gson = new Gson();
		String jsonString = gson.toJson(d);
		System.out.println(gson.toJson(d));
		return jsonString;
	}

	
	private static String datasourceDTOFalse(){
		DatasourceDTO d = new  DatasourceDTO();
		d.setDatasourceName("srsdevice");
		d.setMessageType(MessageType.ALARM);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getRandomTime());
		d.setReceivedTime(cal.getTime());
		d.setStatus(Status.TRUE);
		d.setStatusMessage("Speeding..");
		d.setTime(getRandomTime());
		List<Parameter>parameters = new ArrayList<Parameter>();
		Parameter p1=new Parameter();
		p1.setName("Speed");
		p1.setDataType("Double");
		p1.setTime(getRandomTime());
		p1.setUnit("km\\h");
		p1.setValue(getRandom());
		parameters.add(p1);
		
		Parameter p2=new Parameter();
		p2.setName("Angle");
		p2.setDataType("Double");
		p2.setTime(getRandomTime());
		p2.setUnit("degree");
		p2.setValue(getRandom());
		//parameters.add(p2);
		
		
		d.setParameters(parameters);
		Gson gson = new Gson();
		String jsonString = gson.toJson(d);
		System.out.println(gson.toJson(d));
		return jsonString;
	}

	class SendData implements Runnable{
		public void run(){
			for(int i=0;i<5;i++){
				jmsPublish();
			}
		}
	}
	
	

}

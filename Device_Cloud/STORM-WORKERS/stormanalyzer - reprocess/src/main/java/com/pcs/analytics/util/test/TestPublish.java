/**
 * 
 */
package com.pcs.analytics.util.test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.Parameter;
import com.pcs.saffron.manager.enums.MessageType;

/**
 * @author pcseg129
 *
 */
public class TestPublish {
	private static CorePublisher corePublisher = null;
	
	public static void main(String[] args) {
		init();
		for(int i=1;i<=10;i++){
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
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			corePublisher = brokerManager.getPublisher(DistributorMode.JMS);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void jmsPublish(){
		try {
			corePublisher.publishToTopic("gdevice001", datasourceDTO());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static String datasourceDTO(){
		DatasourceDTO d = new  DatasourceDTO();
		d.setDatasourceName("gdevice001");
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
		
		d.setParameters(parameters);
		Gson gson = new Gson();
		String jsonString = gson.toJson(d);
		System.out.println(gson.toJson(d));
		return jsonString;
	}
	
	private static long getRandomTime(){
		Random random = new Random();
		int millisInDay = 24*60*60*1000;
		return (long)random.nextInt(millisInDay);
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
	


}

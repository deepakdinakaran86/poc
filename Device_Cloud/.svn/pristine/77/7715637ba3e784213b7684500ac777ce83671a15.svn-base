package com.pcs.device.gateway.G2021.test;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.commons.lang.math.NumberUtils;

import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;

public class Test {

	public static void main(String[] args) {
//		((G2021DeviceManager)G2021DeviceManager.instance()).getCacheProvider().getCache("datasource.cache.device.entity").evict("a439c861-e0cf-4286-8f33-038b0e1bfc61");
		//System.out.println("asdas"+ConversionUtils.convertToASCII("13FEB967FA99F"));
		jmsPublish();
	}
	
	private static void jmsPublish(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("10.236.62.106",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CorePublisher corePublisher = brokerManager.getPublisher(DistributorMode.JMS);
			corePublisher.publishToQueue("NEW_QUEUE1","test msg");
			//corePublisher.publishToTopic("36fba767-4a5b-47bc-9432-889c033464d8", getMsg());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

/**
 * 
 */
package com.pcs.analytics.util.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;

/**
 * @author pcseg129
 *
 */
public class KafkaAdvConsumer {
	
	public static void main(String[] args) {
		String name = "distributor";
		Registry registry;
        try {
        	registry = LocateRegistry.getRegistry("localhost",1099);
        	//registry = LocateRegistry.getRegistry("192.168.4.9",1099);
	        DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
	        
	        CorePublisher corePublisher = brokerManager.getPublisher(DistributorMode.KAFKA);
	        
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.KAFKA, ConsumerType.ADVANCED);
			consumer.setTopic("analyzed-message");
			//consumer.setPartitioner(new MessagePartitioner());
			consumer.setPartitionKey("9");
			consumer.setMaxRead(4000);
			consumer.setOffset(111379l);
			KafkaAdvListener advListenerTesting = new KafkaAdvListener();
			advListenerTesting.setConsumer(consumer);
			advListenerTesting.setCorePublisher(corePublisher);
			consumer.listen();
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

}

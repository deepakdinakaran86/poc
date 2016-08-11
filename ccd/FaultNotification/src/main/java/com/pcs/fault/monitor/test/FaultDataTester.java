package com.pcs.fault.monitor.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Message;

import kafka.consumer.KafkaStream;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListener;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;

public class FaultDataTester {
	public FaultDataTester(){
		init();
	}
	
	public static void init() {

		try{
			String name = "distributor";
			//Registry registry = LocateRegistry.getRegistry("localhost",1099);
			Registry registry = LocateRegistry.getRegistry("192.168.4.9",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer coreConsumer = brokerManager.getConsumer(DistributorMode.KAFKA, ConsumerType.HIGH_LEVEL);
			coreConsumer.setTopic("fault_data_stream");
			coreConsumer.setThreadCount(1);
			Properties props = new Properties();
			props.put("group.id", "newgroup");
			coreConsumer.setProperties(props);
			TestListener testing = new TestListener();
			testing.setConsumer(coreConsumer);
			testing.init();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	static class TestListener implements CoreListener{
		private CoreConsumer consumer;

		public CoreConsumer getConsumer() {
			return consumer;
		}

		public void setConsumer(CoreConsumer consumer) {
			consumer.setMessageListener(this);
			this.consumer = consumer;
		}
		

		public void consumeData1(List<Message> arg0) {
	        
        }

		public void onMessage(Message message) {
        }

		public void consumeData(List<KafkaStream<byte[], byte[]>> streams) {
			int i=0;
			for(final KafkaStream stream : streams){
				System.out.println("Started consuming fault data ............");
				ExecutorService executor;
				executor = Executors.newFixedThreadPool(10);
				executor.submit(new FaultDataMessageUtil(stream,i));
				i++;
			}
        }

		public void consumeData(byte[] arg0, long arg1) {
        }
		
		public void init(){
			consumer.listen();
		}

		
		
	}

}

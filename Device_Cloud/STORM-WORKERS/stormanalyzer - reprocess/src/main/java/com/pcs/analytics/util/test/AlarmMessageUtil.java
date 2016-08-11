/**
 * 
 */
package com.pcs.analytics.util.test;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.storm.bolts.AlarmPersistBolt;
import com.pcs.data.analyzer.storm.bolts.AlarmPublishBolt;
import com.pcs.data.analyzer.storm.bolts.DataAnalyzeNPublishBolt;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.message.Message;

/**
 * @author pcseg129
 *
 */
public class AlarmMessageUtil implements Runnable{

	
	private KafkaStream kafkaStream;
	private int threadNo;
	
	public AlarmMessageUtil(KafkaStream stream,int threadNo){
		kafkaStream = stream;
		this.threadNo = threadNo;
	}
	
	public void run(){
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
		System.out.println(iterator.hasNext());
		//this code reads from Kafka until you stop it
		while(iterator.hasNext()){
			String msg = new String(iterator.next().message());
			System.out.println(" i " + threadNo + " : " + msg);
			
			
			ObjectMapper mapper = new ObjectMapper();
			AlarmMessage message = new AlarmMessage();
			String jsonString = null;
			try {
				System.out.println("AlarmMessage in deserialize :" + msg);
				
				message = mapper.readValue(msg, AlarmMessage.class);
				System.out.println("AlarmMessage Object is created from json "+ message);
				
				/*AlarmPublishBolt alarmPublishBolt = new AlarmPublishBolt("Config//publisherconfig.yaml");
				alarmPublishBolt.publishData(message.getSourceId().toString(), message);*/
				
				AlarmPersistBolt alarmPersistBolt = new AlarmPersistBolt();
				//alarmPersistBolt.persistAlarm(message);

			} catch (Exception e) {
				System.err.println("Error in Deserializing");
			}
			
		}
	}
	
	
	private void test(){
		


	}
	
	public static void main(String[] args) {
		
		String msg="{ \"sourceId\": \"spriteDevice\", \"time\": \"1459085904000\", \"receivedTime\": \"1459087076792\", \"alarmMessage\": \"Normal\", \"alarmName\": \"OUT OF RANGE ALARM\", \"alarmType\": \"OUT OF RANGE ALARM\", \"status\": \"false\", \"displayName\": \"Aftertreatment 1 Diesel Exhaust Fluid Tank Level-12\", \"data\": \"15.0\", \"unit\": \"unitless\", \"points\": [ { \"pointId\": \"12\", \"pointName\": \"Aftertreatment 1 Diesel Exhaust Fluid Tank Level\", \"displayName\": \"Aftertreatment 1 Diesel Exhaust Fluid Tank Level-12\", \"unit\": \"unitless\", \"data\": \"15.0\", \"status\": \"null\", \"type\": \"FLOAT\", \"extensions\": [ { \"extensionName\": \"WRITEABLE\", \"extensionType\": \"ACCESS TYPE\" }, { \"extensionName\": \"STATECHANGE ACQUISITION\", \"extensionType\": \"Acquisition Mode\" } ] } ] }";
		ObjectMapper mapper = new ObjectMapper();
		AlarmMessage message = new AlarmMessage();
		String jsonString = null;
		try {
			System.out.println("AlarmMessage in deserialize :" + msg);
			
			message = mapper.readValue(msg, AlarmMessage.class);
			System.out.println("AlarmMessage Object is created from json "+ message);
			
			AlarmPublishBolt alarmPublishBolt = new AlarmPublishBolt("Config//publisherconfig.yaml");
			alarmPublishBolt.publishData(message.getSourceId().toString(), message);
			
			AlarmPersistBolt alarmPersistBolt = new AlarmPersistBolt("Config//publisherconfig.yaml");
			alarmPersistBolt.persistAlarm(message);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error in Deserializing");
		}
		

	}


}


/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.fault.monitor.test1;

import java.io.IOException;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.fault.monitor.bean.FaultData1;
import com.pcs.fault.monitor.bean.FaultResponse;
import com.pcs.fault.monitor.storm.bolts.FaultAnalyzeBolt;
import com.pcs.fault.monitor.storm.bolts.FaultEventPersistBolt;
import com.pcs.fault.monitor.storm.bolts.FaultRespNotifierBolt;
import com.pcs.fault.monitor.storm.bolts.FaultSendBolt;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultData;

/**
 * This class is responsible for reading actual message from the consumed message
 * 
 * @author pcseg129(Seena Jyothish)
 */
public class MessageUtil implements Runnable{
	
	private KafkaStream kafkaStream;
	private int threadNo;
	
	public MessageUtil(KafkaStream stream,int threadNo){
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
			
			//faultEvent(msg);
			fauktResponse(msg);
		}
	}
	
	private void faultEvent(String msg){
		FaultData1 faultData1 = new FaultData1();
		try {
			System.out.println("Message in deserialize :" + msg);
			
            try {
            	faultData1 = new ObjectMapper().readValue(msg, FaultData1.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
           FaultAnalyzeBolt analyzeBolt = new FaultAnalyzeBolt();
           // analyzeBolt.checkFautlt(faultData1);
		} catch (RuntimeException  e) {
			System.err.println("Error in fault event Deserializing");
		}
	}
	
	private void fauktResponse(String msg){
		FaultResponse faultResponse = new FaultResponse();
		try {
			System.out.println("Message in deserialize :" + msg);
            try {
            	faultResponse = new ObjectMapper().readValue(msg, FaultResponse.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
			FaultRespNotifierBolt notifierBolt = new FaultRespNotifierBolt();
			notifierBolt.sendNotification(faultResponse,null);
		} catch (RuntimeException  e) {
			System.err.println("Error in Deserializing");
		}
		
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String msg = "{\"responseVersion\":\"2.100.000\",\"messageType\":\"FC\",\"notificationId\":\"AB123456789876\",\"telematicsBoxId\":\"123456789\",\"telematicsPartnerMessageId\":\"1234567\",\"telematicsPartnerName\":\"Telematics-R-us\",\"customerReference\":\"Customer-X\",\"equipmentId\":\"12345\",\"engineSerialNumber\":\"78787878\",\"VIN\":\"1VIN9876543219876\",\"active\":\"1\",\"datalinkBus\":\"J1939\",\"sourceAddress\":\"0\",\"occurrenceCount\":4,\"latitude\":\"-39.3456789\",\"longitude\":\"30.9876543\",\"altitude\":\"+1234.123456\",\"directionHeading\":\"SW\",\"vehicleDistance\":\"650450\",\"locationTextDescription\":\"In Columbus, IN near I-65\",\"faultCode\":\"2448\",\"faultCodeDescription\":\"Coolant Level Low - Least Severe Level\",\"instructionToFleetMgr\":\"Schedule service at your earliest convenience\",\"instructionToOperator\":\"Call dispatch immediately\",\"additionalInfoToOperator\":\"TBD\",\"derateFlag\":\"1\",\"derateValue1\":\"25 MPH vehicle speed limit in 4 operating hours\",\"derateValue2\":\"10 MPH vehicle speed limit in 2 operating hours\",\"derateValue3\":\"5 MPH vehicle speed limit in 1 operating hours\",\"lampColor\":\"Amber\",\"reportType\":\"Immediate\",\"faultRootCause1\":\"Coolant Level Sensor\",\"faultLikelihood1\":\"0.45\",\"faultRootCause2\":\"Wiring Harness\",\"faultLikelihood2\":\"0.23\",\"faultRootCause3\":\"ECM\",\"faultLikelihood3\":\"0.14\",\"faultRootCause4\":\"ECM Calibration\",\"faultLikelihood4\":\"0.12\",\"shutdownFlag\":\"1\",\"shutdownDescription\":\"This fault code indicates your engine may experience an engine protection shutdown\",\"priority\":\"Service Now\",\"faultCodeCategory\":\"DOC/DPF\",\"additionalDiagnosticInfo\":\"Shop talk\",\"primaryFaultCode\":\"197\",\"primarySPN\":\"111\",\"primaryFMI\":\"17\",\"primaryOccurrenceDateTime\":\"27-Aug-2014 14:26:43.456\",\"primaryFaultCodeDescription\":\"Oil Pressure Low - Most Severe Level\",\"serviceLocatorLink\":\"service.cummins.com\",\"serviceModelName\":\"ISX15 CM2250\",\"generalAssistancePhoneNumber\":\"(888) 555-5555\",\"cumminsName\":\"Cummins\",\"serviceLocatorMarketApplication\":\"Automotive\",\"equipmentSubApplication\":\"Truck\",\"relatedObjects\":[{\"relatedFaultCode\":\"451\",\"relatedSPN\":\"542\",\"relatedFMI\":\"11\",\"relatedOccurrenceDateTime\":\"27-Aug-2014 14:26:43.456\",\"relatedFaultCodeDescription\":\"Oil Pressure Sensor Circuit - Voltage Above Normal or Shorted High\"},{\"relatedFaultCode\":\"123\",\"relatedSPN\":\"321\",\"relatedFMI\":\"10\",\"relatedOccurrenceDateTime\":\"27-Aug-2014 14:26:43.456\",\"relatedFaultCodeDescription\":\"Coolant Temperature Circuit - Voltage Below Normal or Shorted Low\"}]}";
		FaultResponse faultResponse =  new ObjectMapper().readValue(msg, FaultResponse.class);
		FaultRespNotifierBolt notifierBolt = new FaultRespNotifierBolt();
		//notifierBolt.sendNotification("test message one");
    }
	
}


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
package com.pcs.deviceframework.datadist.testing.rmi;

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
import com.pcs.deviceframework.datadist.testing.EventMessage;
import com.pcs.deviceframework.datadist.testing.MessageDTO;
import com.pcs.deviceframework.datadist.testing.ParameterDTO;

/**
 * This class is responsible for testing publishing to JMS msg broker
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class JmsPublish {
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			jmsPublish();
			try {
	            Thread.sleep(100);
            } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
    }

	private static void jmsPublish(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("10.234.31.201",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CorePublisher corePublisher = brokerManager.getPublisher(DistributorMode.JMS);
			corePublisher.publishToQueue("NEW_QUEUE1",getLiveMessage());
			//corePublisher.publishToTopic("36fba767-4a5b-47bc-9432-889c033464d8", getMsg());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static String getMsg(){
	 	MessageDTO dto = new MessageDTO();
	 	dto.setDatasourceName("36fba767-4a5b-47bc-9432-889c033464d8");
	 	List<ParameterDTO> parameterDTOs = new ArrayList<ParameterDTO>();
	 	ParameterDTO p1 = new ParameterDTO();
	 	p1.setName("SysMod");
	 	p1.setDataType("float");
	 	p1.setValue(String.valueOf(getRandom()));
	 	p1.setTime(new Date().getTime());
	 	parameterDTOs.add(p1);
	 	ParameterDTO p2 = new ParameterDTO();
	 	p2.setName("FanSpd");
	 	p2.setDataType("long");
	 	p2.setValue(String.valueOf(getRandom()));
	 	p2.setTime(new Date().getTime());
	 	parameterDTOs.add(p2);
	 	dto.setParameters(parameterDTOs);
	 	
		Gson gson = new Gson();
		String jsonString = gson.toJson(dto);
		return jsonString;
	}
	
	private static int getRandom(){
		int upperBound = 100;
		int lowerBound = -10;
		Random random = new Random();
		int randomNumber = random.nextInt(upperBound - lowerBound) + lowerBound;
		return randomNumber;
	}
	
	public static String getLiveMessage(){
		Gson gson = new Gson();
		EventMessage eventMessage = new EventMessage();
		eventMessage.setEventId(String.valueOf(Calendar.HOUR_OF_DAY));
		eventMessage.setEventSource("Time");
		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.DATE, -3);
		eventMessage.setEventValue(calendar.getTime().toString());

		String jsonString = gson.toJson(eventMessage);
		return jsonString;
	}
}

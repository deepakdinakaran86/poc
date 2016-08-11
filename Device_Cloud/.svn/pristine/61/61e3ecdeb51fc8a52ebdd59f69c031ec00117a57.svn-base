/**
 * 
 */
package com.pcs.weborb.messaging;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import weborb.ORBConstants;
import weborb.config.ORBConfig;
import weborb.management.messaging.DestinationFactory;
import weborb.messaging.v3.MessagingDestination;
import weborb.v3types.core.DataServices;

/**
 * @author pcseg129
 *
 * Oct 17, 2015
 */
public class DynamicDestinationManager {
	private static boolean isInitialized = false;
	private static String jmsHost;
	
	private void init(){
		if(!isInitialized){
			try {
				getPropertyValues();
				isInitialized = true;
			} catch (IOException e) {
				e.printStackTrace();
				System.err.print("Unable to read the property file");
			}
		}
	}

	public void createJmsDestination(String destName, String topicName) {
		init();
		MessagingDestination destination = new MessagingDestination(destName);

		Hashtable<String, Object> jmsProperties = new Hashtable<String, Object>();
		jmsProperties.put(ORBConstants.TRANSACTED_SESSIONS, "false");
		jmsProperties.put(ORBConstants.ACKNOWLEDGE_MODE, "AUTO_ACKNOWLEDGE");
		jmsProperties.put(ORBConstants.MESSAGE_HANDLER,
				"weborb.messaging.v3.jms.JmsObjectMessageFactory");
		jmsProperties.put(ORBConstants.DELIVERY_MODE, "NOT_PERSISTENT");
		jmsProperties.put(ORBConstants.MESSAGE_PRIORITY, "6");

		List<Hashtable<String, String>> arrList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> ht1 = new Hashtable<String, String>();
		ht1.put("name", "java.naming.factory.initial");
		ht1.put("value",
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		arrList.add(ht1);

		Hashtable<String, String> ht2 = new Hashtable<String, String>();
		ht2.put("name", "java.naming.provider.url");
		ht2.put("value", jmsHost);
		arrList.add(ht2);

		Hashtable<String, Object> htProps = new Hashtable<String, Object>();
		htProps.put("property", arrList);

		jmsProperties.put("initial-context-environment", htProps);

		jmsProperties.put("connection-factory", "topicConnectionFactory");

		/*
		 * For dynamically creating destinations, there are 2 dynamic contexts
		 * available, dynamicQueues and dynamicTopics. Which allows you to lookup
		 * queues and topics using JNDI without any configuration. e.g. if you
		 * use dynamicTopics/MyTopic to lookup into JNDI  you
		 * will get back an ActiveMQTopic of the name "MyTopic".
		 */
		jmsProperties
				.put("destination-jndi-name", "dynamicTopics/" + topicName);

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put("jms", jmsProperties);
		properties.put(ORBConstants.MESSAGE_SERVICE_HANDLER,
				"weborb.messaging.v3.jms.JmsServiceHandler");
		properties.put("dynamic-destination", "true");


		destination.setProperties(properties);
		destination.setConfigServiceHandler();

		DataServices dataServices = ORBConfig.getORBConfig().getDataServices();
		dataServices.getDestinationManager().addDestination(destName,
				destination);
		destination.setChannelName("my-polling-amf");
	}
	
	public void createDestination(String destName,String topicName){
		if(!checkDestinationExist(destName)){
			createJmsDestination(destName,topicName);
		}
	}

	public boolean checkDestinationExist(String destName){
		List<String> destinations =  getDynamicDestinations();
		boolean isExist = false;
		for (String dest : destinations) {
			if(dest.length()>0){
				if(destName.equalsIgnoreCase(dest.toString())){
					isExist = true;
					System.out.println("Dynamic dest exist :" + destName);
					break;
				}
			}
		}
		return isExist;
	}
	
	public List<String> getDynamicDestinations(){
		List<MessagingDestination> dynamicDestinations = DestinationFactory.getDynamicDestinations();
		System.out.println("Dynamic dest size :" + dynamicDestinations.size());
		List<String> destNames = new ArrayList<String>();
		for (MessagingDestination messagingDestination : dynamicDestinations) {
			destNames.add(messagingDestination.getName());
		}
		return destNames;
	}
	
	private  void getPropertyValues() throws IOException{
		InputStream inputStream = null;
		try {
			Properties props = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if(inputStream!=null){
				props.load(inputStream);
			}else{
				throw new FileNotFoundException("Config property file "+propFileName + " not found");
			}
			
			jmsHost = props.getProperty("jmsHostUrl");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("Error occurred while reading file");
		}finally{
			inputStream.close();
		}
	}
	
	public static void main(String[] args) {
		DynamicDestinationManager d = new DynamicDestinationManager();
		/*List<String> dynamicDestinations = d.getDynamicDestinations();
		for(String str : dynamicDestinations){
			System.out.println(str);
		}*/
		d.createJmsDestination("test","test");
	}
	
	
}


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
package com.pcs.deviceframework.datadist.util;

import java.util.Arrays;
import java.util.Properties;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.core.ApplicationConfiguration;
import com.pcs.deviceframework.datadist.enums.ConsumerType;

/**
 * This class is responsible for providing various utilities for data distribution
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 23 2015
 */
public class DistributionUtil {
	
	public static Integer getRmiPort(){
		return ApplicationConfiguration.getDistributionConfig().getRmiPort();
	}
	
	public static String getRmiHost(){
		return ApplicationConfiguration.getDistributionConfig().getRmiServerHost();
	}
	
	public static String getJmsHost(){
		return ApplicationConfiguration.getDistributionConfig().getJmsHostUrl();
	}
	
	public static String getKafkaBrokers(){
		return ApplicationConfiguration.getDistributionConfig().getKafkaBrokers();
	}
	
	public static String getKafkaBrokerSeeds(){
		return ApplicationConfiguration.getDistributionConfig().getKafkaBrokerSeeds();
	}
	
	public static String getKafkaPort(){
		return ApplicationConfiguration.getDistributionConfig().getKafkaPort();
	}
	
	public static String getZookeeper(){
		return ApplicationConfiguration.getDistributionConfig().getZookeeper();
	}
	
	public static String getZookeeperSessionTimeout(){
		return ApplicationConfiguration.getDistributionConfig().getZookeeperSessionTimeout();
	}
	
	public static String getZookeeperSyncTime(){
		return ApplicationConfiguration.getDistributionConfig().getZookeeperSyncTime();
	}
	
	public static String getKafkaAutoCommitInterval(){
		return ApplicationConfiguration.getDistributionConfig().getKafkaAutoCommitInterval();
	}
	
	public static String getKafkaAutooffsetReset(){
		return ApplicationConfiguration.getDistributionConfig().getKafkaAutooffsetReset();
	}
	
	public static Properties getKafkaPublishConfig(){
		Properties properties = new Properties();
		properties.put("metadata.broker.list", getKafkaBrokers());
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		properties.put("producers.type", "async");
		return properties;
	}
	
	public static Properties getKafkaHLConsumerConfig(){
		Properties properties = new Properties();
		properties.put("zookeeper.connect", getZookeeper());
		properties.put("zookeeper.session.timeout.ms", getZookeeperSessionTimeout());
		properties.put("zookeeper.sync.time.ms", getZookeeperSyncTime());
		properties.put("auto.commit.enable", "true");
		properties.put("auto.commit.interval.ms", getKafkaAutoCommitInterval());
		properties.put("autooffset.reset", "smallest");
		return properties;
	}
	
	public static CoreConsumer getJmsConsumerConfigured(CoreConsumer coreConsumer){
		coreConsumer.setUrl(getJmsHost());
		return coreConsumer;
	}
	
	public static CoreConsumer getKafkaConsumerConfigured(CoreConsumer coreConsumer,ConsumerType type){
		switch(type){
			case ADVANCED:
				return getKafkaAdvConsumerConfig(coreConsumer);
			case HIGH_LEVEL:
				return getKafkaHLConsumerConfig(coreConsumer);
			default:
				return null;
		}
	}
	
	public static CoreConsumer getKafkaHLConsumerConfig(CoreConsumer coreConsumer){
		coreConsumer.setProperties(getKafkaHLConsumerConfig());
		return coreConsumer;
	}
	public static CoreConsumer getKafkaAdvConsumerConfig(CoreConsumer consumer){
		consumer.setBrokerlist(Arrays.asList(getKafkaBrokerSeeds().split(",")));
		consumer.setPort(Integer.parseInt(getKafkaPort()));
		return consumer;
	}
}


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
package com.pcs.deviceframework.datadist.consumer;

import java.util.List;
import java.util.Properties;

import kafka.javaapi.consumer.ConsumerConnector;
import kafka.producer.Partitioner;

import com.pcs.deviceframework.datadist.consumer.delegates.KafkaAdvancedConsumer;
import com.pcs.deviceframework.datadist.consumer.delegates.KafkaHighlevelConsumer;
import com.pcs.deviceframework.datadist.enums.ConsumerType;


/**
 * This class is responsible for data consumption using Apache Kafka implementation
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class KafkaBaseConsumer extends ConsumerAdapter{

    private static final long serialVersionUID = -128530728671637450L;

	public void setType(String type){
	}
	
	public String getType(){
		return null;
	}
	
	public void setTopic(String topic){
	}
	
	public String getTopic(){
		return null;
	}
	
	public void setPartitionId(Integer partitionId){
	}
	
	public Integer getPartitionId(){
		return null;
	}
	
	public void setOffset(Long offset){
	}
	
	public Long getOffset(){
		return null;
	}
	
	public void setGroupName(String groupName){
	}
	
	public String getGroupName(){
		return null;
	}
	
	public void setZookeeperUrl(String zookeeperUrl){
	}
	
	public String getZookeeperUrl(){
		return null;
	}
	
	public void setBrokerlist(List<String> lstbrokers){
	}
	
	public List<String> getBrokerlist(){
		return null;
	}
	
	public void setPort(Integer port){
	}
	
	public Integer getPort(){
		return null;
	}
	
	public void setProperties(Properties properties){
	}
	
	public Properties getProperties(){
		return null;
	}
	
	public ConsumerConnector setConsumerConnector(){
		return null;
	}
	
	public ConsumerConnector getConsumerConnector(){
		return null;
	}
	
	public void setThreadCount(Integer threadCount){
	}
	
	public Integer getThreadCount(){
		return null;
	}
	
	public void setPartitioner(Partitioner partitioner){
	}
	
	public Partitioner getPartitioner(){
		return null;
	}
	
	public void setPartitionKey(String partitionKey){
	}
	
	public String getPartitionKey(){
		return null;
	}
	
	public void setMaxRead(Integer maxRead){
	}
	
	public Integer getMaxRead(){
		return null;
	}
	
	public void setStarted(boolean started){
	}
	
	public Boolean isStarted(){
		return null;
	}
	
	public CoreConsumer getConsumer(ConsumerType type){
		switch(type){
			case ADVANCED:
				return new KafkaAdvancedConsumer();
			case HIGH_LEVEL:
				return new KafkaHighlevelConsumer();
			default:
				return null;
		}
	}
	
	public void listen(){
		
	}
}

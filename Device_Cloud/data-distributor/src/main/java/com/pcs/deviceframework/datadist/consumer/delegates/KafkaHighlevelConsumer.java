
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
package com.pcs.deviceframework.datadist.consumer.delegates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import com.pcs.deviceframework.datadist.consumer.ConsumerAdapter;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListener;

/**
 * This class is responsible for subscribing to a topic and consume messages published 
 * to the topic using Apache Kafka implementation
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 19 2015
 */
public class KafkaHighlevelConsumer extends ConsumerAdapter {

	private static final long serialVersionUID = 1873455962619263474L;
	private String topic;
	private String groupName;
	private String zookeeperUrl;
	private Integer threadCount;
	private Properties properties;
	private CoreListener messageListener;

	public KafkaHighlevelConsumer(){
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public void setTopic(String topicName) {
		this.topic = topicName;
	}

	@Override
	public String getGroupName() {
		return groupName;
	}

	@Override
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String getZookeeperUrl() {
		return zookeeperUrl;
	}

	@Override
	public void setZookeeperUrl(String zookeeperUrl) {
		this.zookeeperUrl = zookeeperUrl;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public void setProperties(Properties properties) {
		if(this.properties == null){
			this.properties = properties;
		}else{
			this.properties.putAll(properties);
		}
	}

	private ConsumerConfig createConsumerConfig(){
		return new ConsumerConfig(properties);
	}

	@Override
	public Integer getThreadCount() {
		return threadCount;
	}

	@Override
	public void setThreadCount(Integer threadCount) {
		this.threadCount = threadCount;
	}

	@Override
	public void setMessageListener(CoreListener kafkaMessageListener){
		this.messageListener = kafkaMessageListener;
	}

	@Override
	public CoreListener getMessageListener() {
		return messageListener;
	}

	@Override
	public void listen(){
		ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(createConsumerConfig());
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, getThreadCount());
		Map<String, List<KafkaStream<byte[], byte[]>>> messageMap = consumerConnector.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = messageMap.get(topic);
		messageListener.consumeData(streams);
	}

}

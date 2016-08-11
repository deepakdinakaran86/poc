
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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for Kafka specific util methods
 * 
 * @author pcseg129(Seena Jyothish)
 */
public class KafkaUtil {

	private static final Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

	private static List<String> m_replicaBrokers = new ArrayList<String>();
	private static long readOffset;

	public static int getNumberOfPartitions(String topic, String brokerHost){
		int numOfPartitions = -1;
		kafka.javaapi.consumer.SimpleConsumer consumer  = new SimpleConsumer(brokerHost,9093,100000,64 * 1024, "test");
		List<String> topics = Collections.singletonList(topic);
		TopicMetadataRequest topicMetadataRequest = new TopicMetadataRequest(topics);
		TopicMetadataResponse topicMetadataResponse = consumer.send(topicMetadataRequest) ;
		List<TopicMetadata> topicsMetadata = topicMetadataResponse.topicsMetadata();
		for(TopicMetadata metadata : topicsMetadata){
			numOfPartitions = metadata.partitionsMetadata().size();
		}
		consumer.close();
		topics = null;
		topicMetadataRequest = null;
		topicMetadataResponse = null;
		topicsMetadata = null;
		return numOfPartitions;
	}

	public static long getLastOffset(SimpleConsumer consumer,String topic, int partition,long whichTime, String clientName) {
		TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
		Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
		requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
		kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(
				requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
		OffsetResponse response = consumer.getOffsetsBefore(request);

		if (response.hasError()) {
			System.out.println("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition) + response.toString());
			return 0;
		}
		long[] offsets = response.offsets(topic, partition);
		return offsets[0];
	}
	public static SimpleConsumer createSimpleConsumer(List<String> brokers,int port,String topic,int partition){
		// find the meta data about the topic and partition we are interested in
		PartitionMetadata metadata = findLeader(brokers, port, topic, partition);
		if (metadata == null) {
			logger.error("Unable to find metadata for topic " + topic + " and partition " + partition + " so exiting");
			return null;
		}
		if (metadata.leader() == null) {
			logger.error("Unable to find leader for topic " + topic + " and partition " + partition + " so exiting");
			return null;
		}
		String leadBroker = metadata.leader().host();
		String clientName = "Client_" + topic + "_" + partition;

		SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
		return consumer;
	}

	public static ByteBufferMessageSet consumeData(SimpleConsumer consumer,String topic,int partition, int port,long readOffset) throws Exception {
		//readOffset = getLastOffset(consumer,topic, partition, kafka.api.OffsetRequest.EarliestTime(), clientName);
		System.out.println("Offset :  " + readOffset);
		return fetchMessages(consumer,topic,partition,port,readOffset);
	}

	public static ByteBufferMessageSet fetchMessages(SimpleConsumer consumer,String topic,int partition,int port,long readOffset) throws Exception {
		try{
			String clientName = "Client_" + topic + "_" + partition;
			FetchResponse fetchResponse = null;
			while(fetchResponse == null){
				fetchResponse = fetchData(consumer,clientName,topic,partition,readOffset,100000,port);
			}
			if (consumer != null) consumer.close();
			return fetchResponse.messageSet(topic,partition);
		}catch(Exception ex){
			logger.error("Error fetching data from topic",ex);
		}
		return null;
	}

	private static FetchResponse fetchData(SimpleConsumer consumer,String clientName,String topic,int partition,long readOffset, int fetchsize,int a_port) throws Exception{
		int numErrors = 0;
		FetchRequest req = new FetchRequestBuilder()
		.clientId(clientName)
		.addFetch(topic, partition, readOffset, 100000) // Note: this fetchSize of 100000 might need to be increased if large batches are written to Kafka
		.build();
		FetchResponse fetchResponse = consumer.fetch(req);

		if (fetchResponse.hasError()) {
			numErrors++;
			short code = fetchResponse.errorCode(topic, partition);
			logger.error("Error fetching data from the Broker:" + consumer.host() + " Reason: " + code);
			if (numErrors > 5) {
				throw new Exception("Error fetching data ");
			}
			if (code == ErrorMapping.OffsetOutOfRangeCode()) {
				// We asked for an invalid offset. For simple case ask for the last element to reset
				readOffset = getLastOffset(consumer,topic, partition, kafka.api.OffsetRequest.LatestTime(), clientName);
				return null;
			}
			consumer.close();
			consumer = null;
			String leadBroker = findNewLeader(consumer.host(), topic, partition, a_port);
			return null;
		}
		return fetchResponse;
	}

	private static String findNewLeader(String a_oldLeader, String a_topic, int a_partition, int a_port) throws Exception {
		for (int i = 0; i < 3; i++) {
			boolean goToSleep = false;
			PartitionMetadata metadata = findLeader(m_replicaBrokers, a_port, a_topic, a_partition);
			if (metadata == null) {
				goToSleep = true;
			} else if (metadata.leader() == null) {
				goToSleep = true;
			} else if (a_oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
				// first time through if the leader hasn't changed give ZooKeeper a second to recover
				// second time, assume the broker did recover before failover, or it was a non-Broker issue
				//
				goToSleep = true;
			} else {
				return metadata.leader().host();
			}
			if (goToSleep) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
				}
			}
		}
		System.out.println("Unable to find new leader after Broker failure. Exiting");
		throw new Exception("Unable to find new leader after Broker failure. Exiting");
	}

	private static PartitionMetadata findLeader(List<String> a_seedBrokers, int a_port, String a_topic, int a_partition) {
		PartitionMetadata returnMetaData = null;
		loop:
			for (String seed : a_seedBrokers) {
				SimpleConsumer consumer = null;
				try {
					consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024, "leaderLookup");
					List<String> topics = Collections.singletonList(a_topic);
					TopicMetadataRequest req = new TopicMetadataRequest(topics);
					kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);

					List<TopicMetadata> metaData = resp.topicsMetadata();
					for (TopicMetadata item : metaData) {
						for (PartitionMetadata part : item.partitionsMetadata()) {
							if (part.partitionId() == a_partition) {
								returnMetaData = part;
								break loop;
							}
						}
					}
				} catch (Exception e) {
					System.out.println("Error communicating with Broker [" + seed + "] to find Leader for [" + a_topic
							+ ", " + a_partition + "] Reason: " + e);
				} finally {
					if (consumer != null) consumer.close();
				}
			}
		if (returnMetaData != null) {
			m_replicaBrokers.clear();
			for (kafka.cluster.Broker replica : returnMetaData.replicas()) {
				m_replicaBrokers.add(replica.host());
			}
		}
		return returnMetaData;
	}

	public static void main(String[] args) {
		try {
			String topic = "analyzed-message";
			int partition=0;
			int maxReads = 1000;
			String clientName = "Client_" + topic + "_" + "0";

			List<String> brokers = new ArrayList<>();
			brokers.add("192.168.2.7");
			int port = 9092;
			SimpleConsumer consumer = createSimpleConsumer(brokers, port, topic, partition);
			readOffset = getLastOffset(consumer,topic,partition, kafka.api.OffsetRequest.EarliestTime(), clientName);
			System.out.println(" 1 LastOffset " + readOffset);
			ByteBufferMessageSet bufferMessageSet = consumeData(consumer,topic,0,port,1088250l);
			readOffset = getLastOffset(consumer,topic,partition, kafka.api.OffsetRequest.EarliestTime(), clientName);
			System.out.println("LastOffset " + readOffset);
			//readOffset = 1132108l;
			maxReads = showData(maxReads,bufferMessageSet);
			while(maxReads > 0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					logger.error("consume thread interrupted ",ie);
				}
				ByteBufferMessageSet fetchMessages = fetchMessages(consumer,topic,partition,port,readOffset);
				maxReads = showData(maxReads,fetchMessages);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int showData(int maxReads,ByteBufferMessageSet bufferMessageSet){
		try {
			if(bufferMessageSet!=null){
				for (MessageAndOffset messageAndOffset : bufferMessageSet) {
					long currentOffset = messageAndOffset.offset();
					if (currentOffset < readOffset) {
						System.out.println("Found an old offset: " + currentOffset + " Expecting: " + readOffset);
						continue;
					}
					readOffset = messageAndOffset.nextOffset();
					ByteBuffer payload = messageAndOffset.message().payload();

					byte[] bytes = new byte[payload.limit()];
					payload.get(bytes);
					System.out.println(String.valueOf(messageAndOffset.offset()) + ": " + new String(bytes, "UTF-8"));
					maxReads--;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxReads;
	}

}

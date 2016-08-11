
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
package com.pcs.saffron.notification.broker.kafka.consumer.async;

import java.nio.ByteBuffer;
import java.util.List;

import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;
import kafka.producer.Partitioner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.notification.broker.consumer.ConsumerAdapter;
import com.pcs.saffron.notification.broker.consumer.async.CoreListener;
import com.pcs.saffron.notification.broker.util.KafkaUtil;

/**
 * This class is responsible for providing greater control over partition consumption.
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 19 2015
 */
public class KafkaAdvancedConsumer extends ConsumerAdapter {

	private static final long serialVersionUID = 7519893816895324096L;

	private static final Logger logger = LoggerFactory.getLogger(KafkaAdvancedConsumer.class);

	private String mode;
	private Boolean isStarted = false;
	private String topic;
	private Integer paritionId;
	private long offset;
	private String partitionKey;
	private int maxRead;
	private int port;
	private List<String> brokerlist;
	private Partitioner partitioner;
	private SimpleConsumer consumer;
	private CoreListener messageListener;

	@Override
	public List<String> getBrokerlist(){
		return brokerlist;
	}

	@Override
	public void setBrokerlist(List<String> lstbrokers){
		brokerlist = lstbrokers;
	}

	@Override
	public Integer getPort(){
		return port;
	}

	@Override
	public void setPort(Integer port){
		this.port = port;
	}

	@Override
	public void setType(String mode){
		this.mode = mode;
	}

	@Override
	public String getType(){
		return mode;
	}

	@Override
	public void setTopic(String topic){
		this.topic = topic;
	}

	@Override
	public String getTopic(){
		return topic;
	}

	public void setPartitionId(Integer partitionId){
		this.paritionId = partitionId;
	}

	public Integer getPartitionId(){
		return paritionId;
	}

	@Override
	public void setOffset(Long offset){
		this.offset = offset;
	}

	@Override
	public Long getOffset(){
		return offset;
	}

	@Override
	public String getPartitionKey() {
		return partitionKey;
	}

	@Override
	public void setPartitionKey(String partitionKey) {
		this.partitionKey = partitionKey;
	}

	@Override
	public Partitioner getPartitioner() {
		return partitioner;
	}

	@Override
	public void setPartitioner(Partitioner partitioner) {
		this.partitioner = partitioner;
	}

	@Override
	public Integer getMaxRead(){
		return maxRead;
	}

	@Override
	public void setMaxRead(Integer maxRead){
		this.maxRead = maxRead;
	}

	@Override
	public void setStarted(boolean started){
		isStarted = started;
	}

	@Override
	public Boolean isStarted(){
		return isStarted;
	}

	private void createSimpleConsumer(){
		consumer = KafkaUtil.createSimpleConsumer(brokerlist, port, getClientName(), paritionId);
	}

	public void findPartitionId(){
		//return partitioner.partition(partitionKey, KafkaUtil.getNumberOfPartitions(topic));
		paritionId = partitioner.partition(partitionKey, KafkaUtil.getNumberOfPartitions(topic));
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
		findPartitionId();
		createSimpleConsumer();
		while(maxRead > 0){
			try {
				ByteBufferMessageSet bufferMessageSet = null;
				if(!isStarted){
					bufferMessageSet = KafkaUtil.consumeData(consumer, topic, paritionId, port,offset);
					offset = KafkaUtil.getLastOffset(consumer,topic,paritionId, kafka.api.OffsetRequest.EarliestTime(),getClientName());
				}else{
					bufferMessageSet = KafkaUtil.fetchMessages(consumer, topic, paritionId, port, offset);
					offset = KafkaUtil.getLastOffset(consumer,topic,paritionId, kafka.api.OffsetRequest.LatestTime(),getClientName());
				}
				consumeData(bufferMessageSet);
				isStarted = true;
				if(maxRead > 0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
					}
				}
			} catch (Exception e) {
				logger.error("Error consuming data from topic " + topic + " from partition "+ paritionId);
			}
		}
	}

	public void consumeData(ByteBufferMessageSet bufferMessageSet){
		try {
			if(bufferMessageSet!=null){
				for (MessageAndOffset messageAndOffset : bufferMessageSet) {
					long currentOffset = messageAndOffset.offset();
					Long prevOffset = getOffset();
					if (currentOffset < prevOffset) {
						logger.warn("Found an old offset:{} Expecting:{}",currentOffset,prevOffset);
						continue;
					}
					setOffset(messageAndOffset.nextOffset());
					ByteBuffer payload = messageAndOffset.message().payload();

					byte[] payloadCopy = new byte[payload.limit()];
					payload.get(payloadCopy);
					
					messageListener.consumeData(payloadCopy, offset);
					setMaxRead(getMaxRead()-1);
				}
			}
		} catch (Exception e) {
			logger.error("Error reading data from topic " + getTopic() + " from partition "+ getPartitionId());
		}
	}

	private String getClientName(){
		return "Client_" + topic + "_" + paritionId;
	}
}

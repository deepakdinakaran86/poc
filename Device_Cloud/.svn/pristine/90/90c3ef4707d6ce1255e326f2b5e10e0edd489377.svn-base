
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

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import kafka.producer.Partitioner;

import com.pcs.deviceframework.datadist.consumer.listener.CoreListener;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DestinationType;

/**
 * This class is responsible for providing abstraction for data consumption
 * 
 * @author pcseg129(Seena Jyothish)
 * @date March 19 2015
 */
public interface CoreConsumer extends Serializable {
	
	public void setQueue(String queue);
	
	public void setUrl(String url);
	
	public void setQueueSize(Integer size);
	
	public void setDelay(Long delay);
	
	public void setTopic(String topic);
	
	public void setOffset(Long offset);
	
	public void setGroupName(String groupName);
	
	public void setZookeeperUrl(String zookeeperUrl);
	
	public void setBrokerlist(List<String> lstbrokers);
	
	public void setPort(Integer port);
	
	public void setProperties(Properties properties);
	
	public void setThreadCount(Integer threadCount);
	
	public void setPartitioner(Partitioner partitioner);
	
	public void setPartitionKey(String partitionKey);
	
	public void setMaxRead(Integer maxRead);
	
	public void setStarted(boolean started);
	
	public abstract void setMessageListener(CoreListener listener);
	
	public void setDestinationType(DestinationType destinationType);
	
	public String getQueue();
	
	public String getURL();
	
	public Integer getQueueSize();
	
	public Long getDelay();
	
	public String getTopic();
	
	public Long getOffset();

	public String getGroupName();
	
	public String getZookeeperUrl();
	
	public List<String> getBrokerlist();
	
	public Integer getPort();
	
	public Properties getProperties();
	
	public Integer getThreadCount();
	
	public Partitioner getPartitioner();
	
	public String getPartitionKey();
	
	public Integer getMaxRead();
	
	public Boolean isStarted();
	
	public abstract CoreListener getMessageListener();
	
	public CoreConsumer getConsumer(ConsumerType type);
	
	public DestinationType getDestinationType();
	
	public abstract void listen();

}

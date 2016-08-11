
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
package com.pcs.analytics.storm.bolts;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.analytics.distributors.Publisher;
import com.pcs.analytics.distributors.PublisherConfig;

/**
 * This class is responsible for publishing to a JMS topic 
 * 
 * @author pcseg129
 */
public class PublishBolt extends BaseBasicBolt {

    private static final long serialVersionUID = -3147510395523273491L;

	private static Logger LOGGER = LoggerFactory.getLogger(PublishBolt.class);

	private Publisher publisher;
	
	private String publisherConfFile;

	private final PublisherConfig publisherConfig;
	
	private final String queueName;
	
	public PublishBolt(String queueName){
		this.publisherConfig = PublisherConfig
		        .getNewInstance(publisherConfFile);
		this.queueName = queueName;
	}
	
	 public PublishBolt(String publisherConfFilePath,String queueName) {
	    	this.publisherConfFile = publisherConfFilePath;
	    	this.publisherConfig = PublisherConfig
			        .getNewInstance(publisherConfFile);
	    	this.queueName = queueName;
	    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
    	if (input.getValues().size() >= 1) {
    		LOGGER.info("publishing to JMS");
    		publishData(String.valueOf(input.getValues().get(1)));
    	}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    
    }
    
    public void publishData(String message){
    	if(!StringUtils.isBlank(queueName)){
    		if(publisher == null){
    			publisher = new Publisher(publisherConfig);
    		}
    		
    		publisher.publishToJMSQueue(queueName,message);
    	}else{
    		LOGGER.error("Unable to publish , empty queue name");
    	}
    }
    
    public static void main(String[] args) {
    	System.out.println("start bolt");
    	PublishBolt bolt = new PublishBolt(args[0], args[1]);
    	bolt.publishData("hello");
    }
}

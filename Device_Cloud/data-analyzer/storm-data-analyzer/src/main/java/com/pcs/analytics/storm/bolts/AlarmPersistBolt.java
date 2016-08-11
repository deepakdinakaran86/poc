
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

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.analytics.distributors.PublisherConfig;
import com.pcs.analytics.storm.persistence.util.PersistService;
import com.pcs.device.gateway.G2021.message.AlarmMessage;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author PCSEG129(Seena Jyothish)
 * @date 16 Jul 2015
 */
public class AlarmPersistBolt extends BaseBasicBolt{
	
    private static final long serialVersionUID = 8153938370572370903L;
	private static Logger LOGGER = LoggerFactory.getLogger(AlarmPersistBolt.class);
	private PersistService persistService;
	private String publisherConfFile;
	private final PublisherConfig publisherConfig;
	private static long count;

	public AlarmPersistBolt(){
		this.publisherConfig = PublisherConfig
		        .getNewInstance(publisherConfFile);
	}
	
	public AlarmPersistBolt(String publisherConfFilePath){
		this.publisherConfFile = publisherConfFilePath;
    	this.publisherConfig = PublisherConfig
		        .getNewInstance(publisherConfFile);
	}
	
	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
	        TopologyContext context) {
		this.persistService = new PersistService(publisherConfig);
		super.prepare(stormConf, context);
	}
	
	@Override
    public void execute(Tuple input, BasicOutputCollector collector) {
		try {
			List<Object> values = input.getValues();
			if (values.size() >= 1) {
				LOGGER.info("Invoking alarm persisting API");
				persistService.persist((AlarmMessage)values.get(1));
				LOGGER.info("Alarm message in Bolt ################ Count ={}",
				        count++);
			} else {
				LOGGER.error("Tuple Size is less than 2,alarm persist API is not invoked");
			}
		} catch (Exception e) {
			LOGGER.error("Error in alarm persisting bolt Execution", e);
		}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
    

}

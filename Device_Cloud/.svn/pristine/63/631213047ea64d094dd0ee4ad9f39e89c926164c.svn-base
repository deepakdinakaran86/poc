/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.data.analyzer.storm.bolts;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date Mar 31, 2015
 * @since galaxy-1.0.0
 */
public class EventAnalyzerPublisherBolt extends BaseBasicBolt {

	/**
	 *
	 */
	private static final long serialVersionUID = -3925774919135893287L;

	private final String eventAnalyzerQueueName;
	private Publisher publisher;
	private final PublisherConfig publisherConfig;

	public EventAnalyzerPublisherBolt(String eventAnalyzerQueueName) {
		this.publisherConfig = PublisherConfig
		        .getNewInstance("publisherconfig.yaml");
		this.eventAnalyzerQueueName = eventAnalyzerQueueName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple,
	 * backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		publisher.publishToJMSQueue(eventAnalyzerQueueName,
				String.valueOf(input.getValues().get(1)));

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * backtype.storm.topology.IComponent#declareOutputFields(backtype.storm
	 * .topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see backtype.storm.topology.base.BaseBasicBolt#prepare(java.util.Map,
	 * backtype.storm.task.TopologyContext)
	 */
	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
	        TopologyContext context) {
		this.publisher = new Publisher(publisherConfig);
		super.prepare(stormConf, context);
	}

}
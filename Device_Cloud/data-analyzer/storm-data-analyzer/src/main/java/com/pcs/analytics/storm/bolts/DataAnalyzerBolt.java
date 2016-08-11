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
package com.pcs.analytics.storm.bolts;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.analytics.distributors.Publisher;
import com.pcs.analytics.distributors.PublisherConfig;
import com.pcs.analytics.executor.PointIdentifier;
import com.pcs.deviceframework.decoder.data.message.Message;

/**
 * Storm Bolt Implementation for analyzing received data based on configuration
 *
 * @author pcseg129 (Seena Jyothish)
 * @date Apr 8, 2015
 * @since galaxy-1.0.0
 */
public class DataAnalyzerBolt extends BaseBasicBolt {

	private static Logger LOGGER = LoggerFactory
	        .getLogger(DataAnalyzerBolt.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1032140424347578887L;

	private final String analyzedTopicName;
	private Publisher publisher;
	private final PublisherConfig publisherConfig;

	public DataAnalyzerBolt(String analyzedTopicName) {
		this.publisherConfig = PublisherConfig
		        .getNewInstance("publisherconfig.yaml");
		this.analyzedTopicName = analyzedTopicName;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * backtype.storm.topology.IBasicBolt#execute(backtype.storm.tuple.Tuple,
	 * backtype.storm.topology.BasicOutputCollector)
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {

		LOGGER.info("Invoking Point Identifier");

		try {
			Object sourceId = input.getString(0);
			Message message = (Message)input.getValue(1);
			PointIdentifier identifier = new PointIdentifier(sourceId, message);
			message = identifier.identifyPoints();
			publisher.publishToKafkaTopic(analyzedTopicName, message);
			LOGGER.info("Data analyzed for {}", sourceId);
		} catch (Exception e) {
			LOGGER.error("Error in Data Analyzer Bolt Execution", e);
		}

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
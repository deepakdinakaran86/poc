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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.util.DataAnalyzerUtil;
import com.pcs.saffron.cipher.data.message.Message;

/**
 * Storm Bolt Implementation for analyzing received data based on configuration
 *
 * @author pcseg129 (Seena Jyothish)
 * @date Apr 8, 2015
 * @since galaxy-1.0.0
 */
public class DataAnalyzeBolt extends BaseBasicBolt {

	private static Logger LOGGER = LoggerFactory
	        .getLogger(DataAnalyzeBolt.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1032140424347578887L;

	private final String analyzedTopicName;
	private Publisher publisher;
	
	private final PublisherConfig publisherConfig;
	public static final String SOURCE_ID_KEY = "deviceId";
	public static final String DEVICE_MESSAGE_KEY = "message";

	public DataAnalyzeBolt(String publisherConfFilePath,String analyzedTopicName) {
		this.publisherConfig = PublisherConfig
		        .getNewInstance(publisherConfFilePath);
		this.analyzedTopicName = analyzedTopicName;
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		LOGGER.info("Invoking Point Identifier");
		if(input.getValues().get(1)!=null && input.getValues().get(0)!=null){
			try {
				Object sourceId = input.getString(0);
				Message message = (Message)input.getValue(1);
				message = DataAnalyzerUtil.analyzeMessage(message,sourceId);
				pushToPersistTopic(message);
			} catch (Exception e) {
				LOGGER.error("Error in Data Analyzer Bolt Execution", e);
			}
		}else{
			LOGGER.error("Input tuple is null ");
		}
	}
	
	public void pushToPersistTopic(Message message){
		publisher.publishToKafkaTopic(analyzedTopicName, message);
	}

	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(SOURCE_ID_KEY, DEVICE_MESSAGE_KEY));
	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
	        TopologyContext context) {
		this.publisher = new Publisher(publisherConfig);
		super.prepare(stormConf, context);
	}
	
	public static void main(String[] args) throws Exception {
		
	}
}
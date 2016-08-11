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

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.bean.AnalyzedData;
import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.util.DataAnalyzerUtil;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.manager.bean.DataIngestionBean;

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

	public DataAnalyzeBolt(String publisherConfFilePath,
			String analyzedTopicName) {
		this.publisherConfig = PublisherConfig
				.getNewInstance(publisherConfFilePath);
		this.analyzedTopicName = analyzedTopicName;
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		LOGGER.info("Invoking Point Identifier");
		if (input.getValues().get(0) != null) {
			try {
				@SuppressWarnings("unchecked")
				List<Message> messages = (List<Message>) input.getValue(0);
				if (!messages.isEmpty()) {
					for (Message message : messages) {
						Object sourceId = message.getSourceId();
						AnalyzedData analyzeMessage = DataAnalyzerUtil.analyzeMessage(message,sourceId);
						if(analyzeMessage != null){
							message = analyzeMessage.getMessage();
							List<DataIngestionBean> dataIngestionBeans = analyzeMessage.getDataIngestionBean();
							if(!CollectionUtils.isEmpty(dataIngestionBeans)){
								ObjectMapper mapper = new ObjectMapper();
								for (DataIngestionBean dataIngestionBean : dataIngestionBeans) {
									pushToPersistTopic(mapper.writeValueAsString(dataIngestionBean));
								}
							}else{
								LOGGER.error("No valid data found to analyze and ingest");
							}
						}else{
							LOGGER.error("No valid data found to analyze");
						}
					}
				}

			} catch (Exception e) {
				LOGGER.error("Error in Data Analyzer Bolt Execution", e);
			}
		} else {
			LOGGER.error("Input tuple is null ");
		}
	}

	public void pushToPersistTopic(String persistMessage) {
		publisher.publishToKafkaTopic(analyzedTopicName, persistMessage);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
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
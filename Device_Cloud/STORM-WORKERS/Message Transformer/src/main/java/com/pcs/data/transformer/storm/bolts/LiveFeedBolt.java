package com.pcs.data.transformer.storm.bolts;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.data.transformer.distributors.Publisher;
import com.pcs.data.transformer.distributors.PublisherConfig;
import com.pcs.saffron.cipher.data.message.Message;

public class LiveFeedBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 8894306965062726010L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(LiveFeedBolt.class);

	private Publisher publisher;

	private final PublisherConfig publisherConfig;
	
	private final String topicName;

	public LiveFeedBolt(String publisherConfFilePath,String topicName) {
		this.publisherConfig = PublisherConfig
				.getNewInstance(publisherConfFilePath);
		this.topicName = topicName;
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		if(input.getValues().get(1)!=null && input.getValues().get(0)!=null){
			Message message = (Message) input.getValue(1);
			String sourceId = input.getString(0);
			publishData(sourceId, message);
		}else{
			LOGGER.error("Input tuple is null ");
		}
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

	public void publishData(String sourceId, Message message) {
		if (!StringUtils.isBlank(sourceId)) {
			if (publisher == null) {
				publisher = new Publisher(publisherConfig);
			}
			LOGGER.info("Publishing mqtt message of source id {} to topic {}",message.getSourceId().toString(),topicName);
			message.setSourceId(sourceId);
			publisher.publishToKafkaTopic(topicName, message);
		} else {
			LOGGER.error("Unable to publish , empty sourceId");
		}
	}
}

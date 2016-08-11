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

import com.pcs.analytics.distributors.Publisher;
import com.pcs.analytics.distributors.PublisherConfig;
import com.pcs.deviceframework.decoder.data.message.Message;

public class DataPersistBolt extends BaseBasicBolt {

	private static Logger LOGGER = LoggerFactory.getLogger(DataPersistBolt.class);
	private static final long serialVersionUID = 1032140424347578887L;

	private Publisher publisher;
	private final PublisherConfig publisherConfig;

	private static long count;

	/**
	 * @param publisherConfiFilePath
	 */
    public DataPersistBolt(String publisherConfFilePath) {
    	this.publisherConfig = PublisherConfig
		        .getNewInstance(publisherConfFilePath);
    }

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		
		try {
			List<Object> values = input.getValues();
			if (values.size() >= 1) {
				LOGGER.info("Invoking Persisting API");
				publisher.persist((Message)values.get(1));
				LOGGER.info("Message in Bolt ################ Count ={}",
				        count++);
			} else {
				LOGGER.error("Tuple Size is less than 2,Persist API is not invoked");
			}
		} catch (Exception e) {
			LOGGER.error("Error in Persisting Bolt Execution", e);
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

}
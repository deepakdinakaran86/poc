package com.pcs.data.store.topology.aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.Aggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;

public class DatastoreAggregator implements Aggregator<List<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9028446716525009618L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DatastoreAggregator.class);
	private List<String> datastoreMessages ;
	int partitionId;
	
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		LOGGER.info("Preparing partition ID");
		partitionId = context.getPartitionIndex();
	}

	@Override
	public void cleanup() {
		LOGGER.info("Resetting datastore messages");
		datastoreMessages = new ArrayList<String>();
	}

	@Override
	public List<String> init(Object batchId, TridentCollector collector) {
		LOGGER.info("Initializing datastore messages");
		datastoreMessages = new ArrayList<String>();
		return datastoreMessages;
	}

	@Override
	public void aggregate(List<String> val, TridentTuple tuple,
			TridentCollector collector) {
		LOGGER.info("Adding values to the datastore collection {}",val);
		datastoreMessages.add("{\"sourceId\":\"bfe61a61-7c32-4dea-b640-8b0e5cad1edc\",\"deviceTime\":\"1449917563000\","
				+ "\"deviceDate\":\"1449878400000\",\"insertTime\":\"1449917684777\",\"displayName\":\"Latitude\",\"data\":"
				+ "\"24.9388535\",\"datastore\":\"status_double\",\"dataType\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"READONLY\","
				+ "\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"systemTag\":\"\"}");
		//datastoreMessages.addAll(val);
	}

	@Override
	public void complete(List<String> val, TridentCollector collector) {
		LOGGER.info("emiting datastore messages and resetting");
		collector.emit(new Values(datastoreMessages));
		datastoreMessages = new ArrayList<String>();
	}

}

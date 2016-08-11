package com.pcs.data.store.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;


public class SimpleFunction extends BaseFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1395288630700423296L;
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFunction.class);
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		LOGGER.info("Tuple information {}",tuple);
		collector.emit(tuple);
	}
	
}

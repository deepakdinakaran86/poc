package com.pcs.data.store.utils;

import java.util.Map;

import storm.trident.state.State;
import storm.trident.state.StateFactory;
import backtype.storm.task.IMetricsContext;

public class SimpleStateFactory implements StateFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7717884221808883233L;

	@Override
	public State makeState(Map conf, IMetricsContext metrics,
			int partitionIndex, int numPartitions) {
		return new SimpleState();
	}

}

/**
 * 
 */
package com.pcs.data.store.utils;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.data.store.scheme.HeartBeatMessageScheme;

import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.state.StateUpdater;
import storm.trident.tuple.TridentTuple;

/**
 * @author pcseg171
 *
 */
public class SimpleStateUpdater implements StateUpdater<SimpleState> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4916827951922773847L;
	private static final Logger Logger = LoggerFactory.getLogger(SimpleStateUpdater.class);

	/* (non-Javadoc)
	 * @see storm.trident.operation.Operation#prepare(java.util.Map, storm.trident.operation.TridentOperationContext)
	 */
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see storm.trident.operation.Operation#cleanup()
	 */
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see storm.trident.state.StateUpdater#updateState(storm.trident.state.State, java.util.List, storm.trident.operation.TridentCollector)
	 */
	@Override
	public void updateState(SimpleState state, List<TridentTuple> tuples,
			TridentCollector collector) {
		Logger.info("Batch Formation Starts Here for {} records",tuples.size());
		Logger.info("=========================================================================");
		for (TridentTuple tridentTuple : tuples) {
			Logger.info("Batch from {} with data to persist is {}",tridentTuple);
		}
		Logger.info("=========================================================================");
		Logger.info("Batch Formation Ends Here for {} records",tuples.size());
		// TODO Auto-generated method stub

	}

}

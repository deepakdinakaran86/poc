package com.pcs.deviceframework.pubsub.consumers.common;

import com.pcs.deviceframework.pubsub.consumers.BaseConsumer;
import com.pcs.deviceframework.pubsub.consumers.ConsumerRegistry;
import com.pcs.deviceframework.pubsub.consumers.delegates.AsynchronousConsumer;
import com.pcs.deviceframework.pubsub.consumers.delegates.DefaultConsumer;
import com.pcs.deviceframework.pubsub.enums.ConsumerModes;
import com.pcs.deviceframework.pubsub.enums.common.DefaultConsumerModes;

/**
 * Default consumer registry for registering ASYNC/DEFAULT consumers.
 * @author PCSEG171
 *
 */
public class DefaultConsumerRegistry implements ConsumerRegistry {

	@Override
	public BaseConsumer getConsumer(ConsumerModes consumerModes) {
		DefaultConsumerModes mode = (DefaultConsumerModes) consumerModes;
		switch (mode) {
			case DEFAULT:
				return new DefaultConsumer();
			case ASYNC:
				return new AsynchronousConsumer();
			default:
				return null;
		}
	}

}

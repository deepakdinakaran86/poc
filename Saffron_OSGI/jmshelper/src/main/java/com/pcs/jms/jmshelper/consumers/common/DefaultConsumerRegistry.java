package com.pcs.jms.jmshelper.consumers.common;

import com.pcs.jms.jmshelper.consumers.BaseConsumer;
import com.pcs.jms.jmshelper.consumers.ConsumerRegistry;
import com.pcs.jms.jmshelper.consumers.delegates.AsynchronousConsumer;
import com.pcs.jms.jmshelper.consumers.delegates.DefaultConsumer;
import com.pcs.jms.jmshelper.enums.ConsumerModes;
import com.pcs.jms.jmshelper.enums.common.DefaultConsumerModes;

/**
 * Default consumer registry for registering ASYNC/DEFAULT consumers.
 * @author PCSEG171
 *
 */
public class DefaultConsumerRegistry implements ConsumerRegistry {

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

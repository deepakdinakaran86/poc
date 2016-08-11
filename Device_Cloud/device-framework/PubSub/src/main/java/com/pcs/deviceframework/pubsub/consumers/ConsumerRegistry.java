/**
 * 
 */
package com.pcs.deviceframework.pubsub.consumers;

import com.pcs.deviceframework.pubsub.enums.ConsumerModes;

/**
 * @author PCSEG171
 *
 */
public interface ConsumerRegistry {

	/**
	 *  This interface provides provision to register Application specific consumers.
	 * API users will have to implement this Interface to register their Message Consumerss.
	 * @param consumerModes
	 * @return
	 */
	public BaseConsumer getConsumer(ConsumerModes consumerModes);
}

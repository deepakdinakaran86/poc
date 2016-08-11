/**
 * 
 */
package com.pcs.jms.jmshelper.consumers;

import com.pcs.jms.jmshelper.enums.ConsumerModes;

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

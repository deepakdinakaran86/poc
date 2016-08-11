/**
 * 
 */
package com.pcs.jms.jmshelper.publishers;

import java.util.Set;

import com.pcs.jms.jmshelper.enums.PublisherModes;

/**
 * @author PCSEG171
 *
 */
public interface PublisherRegistry {

	/**
	 * This interface provides provision to register Application specific publishers.
	 * API users will have to implement this Interface to register their Message Publishers.
	 * @param modes - PublisherModes
	 * @return
	 */
	public BasePublisher getPublisher(PublisherModes modes);
	
	/**
	 * This method provides provision to select a specific publisher form a list of publishers.
	 * 
	 * @param mode - mode of the publisher required
	 * @param publishers set of publishers from which the publisher has to be fetched.
	 * @return
	 */
	public Set<BasePublisher> selectPublisher(PublisherModes mode,Set<BasePublisher> publishers);
}

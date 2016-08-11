/**
 * 
 */
package com.pcs.cache;

import java.util.Collection;

/**
 * Defines the responsibilities of a cache provider.Cache provider supports both
 * in-memory/centralized and distributed caching semantics.
 * 
 * @author pcseg310 (Rakesh)
 *
 */
public interface CacheManager {

	/**
	 * Retrieves the cache for the given name.
	 * 
	 * @param name
	 * @return
	 */
	Cache getCache(String name);

	/**
	 * Retrieves a collection of cache names available in the cache system.If
	 * nothing available it returns an empty collection.
	 * 
	 * @return
	 */
	Collection<String> getDistributedCacheNames();
	
	/**
	 * Sets the required configuration to initialize the provider
	 * 
	 * @param config
	 */
	void setConfiguration(CacheConfiguration config);
	
	/**
	 * Retrieves the configuration
	 * 
	 * @return
	 */
	CacheConfiguration getConfiguration();
}

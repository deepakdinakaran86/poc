/**
 * 
 */
package com.pcs.saffron.cache.base;

import java.util.concurrent.TimeUnit;

/**
 * @author pcseg310 (Rakesh)
 *
 */
public interface Cache {

	/**
	 * Retrieves the name of the cache instance
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retrieves a cached object for the given key
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	<T> T get(Object key, Class<T> type);
	
	
	/**
	 * @param key
	 * @return
	 */
	Object get(Object key);
	

	/**
	 * Put/add object in the cache for the given key.
	 * 
	 * @param key
	 * @param value
	 */
	void put(Object key, Object value);

	/**
	 * Puts an object in the cache for a desired time period.The object will get
	 * evicted after that.
	 * 
	 * @param key
	 * @param value
	 * @param ttl
	 * @param timeunit
	 */
	void put(Object key, Object value, long ttl, TimeUnit timeunit);

	/**
	 * Put/add object in the cache for the given key if the object is not
	 * present already.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	Object putIfAbsent(Object key, Object value);

	/**
	 * Remove the value for the given key from the cache
	 * 
	 * @param key
	 */
	void evict(Object key);

	/**
	 * Clears the cache
	 */
	void clear();

}

/**
 * 
 */
package com.pcs.saffron.cache.inmem;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cache.base.CacheProviderConfiguration;

/**
 * @author pcseg310
 *
 */
public class InMemory implements CacheProvider {

	private final ConcurrentMap<String, Cache> storeMap = new ConcurrentHashMap<String, Cache>(16);
	private static InMemory instance;
	
	public InMemory() {
		super();
	}
	
	public static final InMemory instance() {
		if (instance == null) {
			synchronized (InMemory.class) {
				if (instance == null) {
					instance = new InMemory();
				}
			}
		}
		return instance;
	}
	
	
	public Cache getCache(String name) {
		Cache cache = this.storeMap.get(name);
		if (cache == null) {
			synchronized (this.storeMap) {
				cache = this.storeMap.get(name);
				if (cache == null) {
					// create new cache with given name
					cache =  new InMemoryCache(name);
					this.storeMap.put(name, cache);
				}
			}
		}
		return cache;
	}

	public List<String> getDistributedCacheNames() {
		return Collections.emptyList();
	}

	
	public void setConfiguration(CacheProviderConfiguration config) {
	}

	
	public CacheProviderConfiguration getConfiguration() {
		return null;
	}

}

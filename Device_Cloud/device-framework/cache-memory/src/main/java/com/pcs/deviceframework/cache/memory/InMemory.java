/**
 * 
 */
package com.pcs.deviceframework.cache.memory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.cache.CacheProviderConfiguration;

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
	
	@Override
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

	@Override
	public List<String> getDistributedCacheNames() {
		return Collections.emptyList();
	}

	@Override
	public void setConfiguration(CacheProviderConfiguration config) {
	}

	@Override
	public CacheProviderConfiguration getConfiguration() {
		return null;
	}

}

/**
 * 
 */
package com.pcs.cache.memory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.pcs.cache.Cache;

/**
 * @author pcseg310
 *
 */
public class InMemoryCache implements Cache {
	
	private ConcurrentMap<Object,Object> nativeCache = new ConcurrentHashMap<Object, Object>();
	
	public InMemoryCache(String name) {
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		return (T) nativeCache.get(key);
	}

	@Override
	public void put(Object key, Object value) {
		nativeCache.put(key, value);
	}

	@Override
	public Object putIfAbsent(Object key, Object value) {
		return nativeCache.putIfAbsent(key, value);
	}

	@Override
	public void evict(Object key) {
		nativeCache.remove(key);
	}

	@Override
	public void clear() {
		nativeCache.clear();
	}

	@Override
    public void put(Object key, Object value, long ttl, TimeUnit timeunit) {
	    //TODO should handle the expiration logic
		nativeCache.put(key, value);
    }

}

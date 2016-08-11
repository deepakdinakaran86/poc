/**
 * 
 */
package com.pcs.saffron.cache.inmem;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.pcs.saffron.cache.base.Cache;

/**
 * @author pcseg310
 *
 */
public class InMemoryCache implements Cache {
	
	private ConcurrentMap<Object,Object> nativeCache = new ConcurrentHashMap<Object, Object>();
	
	public InMemoryCache(String name) {
	}

	
	public String getName() {
		return null;
	}

	
	public <T> T get(Object key, Class<T> type) {
		return (T) nativeCache.get(key);
	}

	
	public void put(Object key, Object value) {
		nativeCache.put(key, value);
	}

	
	public Object putIfAbsent(Object key, Object value) {
		return nativeCache.putIfAbsent(key, value);
	}

	
	public void evict(Object key) {
		nativeCache.remove(key);
	}

	
	public void clear() {
		nativeCache.clear();
	}

	
    public void put(Object key, Object value, long ttl, TimeUnit timeunit) {
	    //TODO should handle the expiration logic
		nativeCache.put(key, value);
    }


	public Object get(Object key) {
		// TODO Auto-generated method stub
		return nativeCache.get(key);
	}

}

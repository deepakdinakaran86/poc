package com.pcs.cache.factory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pcs.cache.CacheConfiguration;
import com.pcs.cache.CacheManager;
import com.pcs.cache.hazelcast.HazelCast;
import com.pcs.cache.memory.InMemory;

/**
 * @author pcseg310
 *
 */
public class CacheManagerFactory {

	private static final ConcurrentMap<CacheManagerType, CacheManager> providerStore = new ConcurrentHashMap<CacheManagerType, CacheManager>(
	        16);
	private static final CacheManagerFactory instance = new CacheManagerFactory();

	private CacheManagerFactory() {
	}

	public static final CacheManagerFactory instance() {
		return instance;
	}

	@Override
    protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public final CacheManager getManager(CacheManagerType type,
	        CacheConfiguration configuration) {
		CacheManager provider = fromStore(type);

		if (provider == null) {
			provider = createNewProvider(type, configuration);
			toStore(type, provider);
		}

		return provider;

	}

	private CacheManager fromStore(CacheManagerType type) {
		CacheManager provider = null;
		if (type != null) {
			provider = providerStore.get(type);
		}
		return provider;
	}

	private void toStore(CacheManagerType type, CacheManager provider) {
		if (provider != null) {
			providerStore.put(type, provider);
		}
	}

	private CacheManager createNewProvider(CacheManagerType type,
	        CacheConfiguration configuration) {

		CacheManager newProvider = null;
		switch (type) {
			case HZ :
				newProvider = new HazelCast(configuration);
				break;
			case InMemory :
				newProvider = new InMemory();
				break;
			default:
				newProvider = new InMemory();
				break;

		}
		return newProvider;
	}
}

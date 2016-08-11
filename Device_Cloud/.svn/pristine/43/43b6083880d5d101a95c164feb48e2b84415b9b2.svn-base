/**
 *
 */
package com.pcs.data.analyzer.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cache.base.CacheProviderConfiguration;
import com.pcs.saffron.cache.hazelcast.HazelCast;

/**
 * @author pcseg310
 *
 */
public class CacheManager {

	private static final ConcurrentMap<CacheProviderType, CacheProvider> providerStore = new ConcurrentHashMap<CacheProviderType, CacheProvider>(
	        16);
	private static final CacheManager instance = new CacheManager();

	private CacheManager() {
	}

	public static final CacheManager instance() {
		return instance;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public final CacheProvider getProvider(CacheProviderType type,
	        CacheProviderConfiguration configuration) {
		CacheProvider provider = fromStore(type);

		if (provider == null) {
			provider = createNewProvider(type, configuration);
			toStore(type, provider);
		}

		return provider;

	}

	private CacheProvider fromStore(CacheProviderType type) {
		CacheProvider provider = null;
		if (type != null) {
			provider = providerStore.get(type);
		}
		return provider;
	}

	private void toStore(CacheProviderType type, CacheProvider provider) {
		if (provider != null) {
			providerStore.put(type, provider);
		}
	}

	private CacheProvider createNewProvider(CacheProviderType type,
	        CacheProviderConfiguration configuration) {

		CacheProvider newProvider = null;
		switch (type) {
			case HZ :
				newProvider = new HazelCast(configuration);
			default:
				newProvider = new HazelCast(configuration);
				break;

		}
		return newProvider;
	}
}

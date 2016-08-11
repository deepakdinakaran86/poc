/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 *
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 *
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.cache.CacheProviderConfiguration;
import com.pcs.deviceframework.cache.hazelcast.HazelCast;
import com.pcs.deviceframework.cache.memory.InMemory;

/**
 * CacheManager
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
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
				// newProvider.setConfiguration(configuration);
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

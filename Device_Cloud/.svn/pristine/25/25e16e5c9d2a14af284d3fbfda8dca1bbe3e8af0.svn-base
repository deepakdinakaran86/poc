package com.pcs.deviceframework.cache.hazelcast;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.cache.CacheProviderConfiguration;

/**
 * @author pcseg310
 *
 */
public class HazelCast implements CacheProvider {

	private final ConcurrentMap<String, Cache> store = new ConcurrentHashMap<String, Cache>(
	        16);
	private HazelcastInstance hazelcastInstance;
	private HazelCastConfiguration config;

	public HazelCast(CacheProviderConfiguration config) {
		super();
		setConfiguration(config);
		intialize();
	}

	public void intialize() {
		if (config != null) {
			ClientConfig config = null;
			try {
				config = new XmlClientConfigBuilder(
				        this.config.getConfigResourcePath()).build();
			} catch (IOException e) {}
			this.hazelcastInstance = HazelcastClient.newHazelcastClient(config);
		} else {
			// default xml config
			this.hazelcastInstance = HazelcastClient.newHazelcastClient();
		}
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = store.get(name);
		if (cache == null) {
			final IMap<Object, Object> map = hazelcastInstance.getMap(name);
			cache = new HazelCastCache(map);
			final Cache currentCache = store.putIfAbsent(name, cache);
			if (currentCache != null) {
				cache = currentCache;
			}
		}
		return cache;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Collection<String> getDistributedCacheNames() {
		Set<String> cacheNames = new HashSet<String>();
		final Collection<DistributedObject> distributedObjects = hazelcastInstance
		        .getDistributedObjects();
		for (DistributedObject distributedObject : distributedObjects) {
			if (distributedObject instanceof IMap) {
				final IMap<?, ?> map = (IMap)distributedObject;
				cacheNames.add(map.getName());
			}
		}
		return cacheNames;
	}

	@Override
	public void setConfiguration(CacheProviderConfiguration config) {
		if (config instanceof HazelCastConfiguration) {
			this.config = (HazelCastConfiguration)config;
		}
	}

	@Override
	public CacheProviderConfiguration getConfiguration() {
		return this.config;
	}

}

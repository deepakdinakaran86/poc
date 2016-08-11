package com.pcs.cache.hazelcast;

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
import com.pcs.cache.Cache;
import com.pcs.cache.CacheConfiguration;
import com.pcs.cache.CacheManager;

/**
 * A Hazel cast impl of cachemanager.
 * 
 * @author pcseg310
 *
 */
public class HazelCast implements CacheManager {

	private final ConcurrentMap<String, Cache> store = new ConcurrentHashMap<String, Cache>(16);
	private HazelcastInstance hazelcastInstance;
	private HazelCastConfiguration config;

	public HazelCast(CacheConfiguration config) {
		super();
		setConfiguration(config);
		intialize();
	}

	public void intialize() {
		if (config != null) {
			ClientConfig config = null;
			try {
				config = new XmlClientConfigBuilder(this.config.getConfigResourcePath()).build();
			} catch (IOException e) {
			}
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
		final Collection<DistributedObject> distributedObjects = hazelcastInstance.getDistributedObjects();
		for (DistributedObject distributedObject : distributedObjects) {
			if (distributedObject instanceof IMap) {
				final IMap<?, ?> map = (IMap) distributedObject;
				cacheNames.add(map.getName());
			}
		}
		return cacheNames;
	}

	@Override
	public void setConfiguration(CacheConfiguration config) {
		if (config instanceof HazelCastConfiguration) {
			this.config = (HazelCastConfiguration) config;
		}
	}

	@Override
	public CacheConfiguration getConfiguration() {
		return this.config;
	}

}

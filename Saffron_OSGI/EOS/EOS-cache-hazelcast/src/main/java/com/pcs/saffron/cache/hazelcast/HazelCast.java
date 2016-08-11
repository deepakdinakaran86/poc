package com.pcs.saffron.cache.hazelcast;

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
import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cache.base.CacheProviderConfiguration;

/**
 * @author pcseg310
 *
 */
public class HazelCast implements CacheProvider {

	private final ConcurrentMap<String, Cache> store = new ConcurrentHashMap<String, Cache>(
			16);
	private HazelcastInstance hazelcastInstance;
	private HazelCastConfiguration config;
	private String configPath;

	/**
	 * @return the configPath
	 */
	public String getConfigPath() {
		return configPath;
	}

	/**
	 * @param configPath the configPath to set
	 */
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public HazelCast(){
		this.configPath = "hazelcast-client.xml";
		initialize();
	}

	public HazelCast(CacheProviderConfiguration config) {
		super();
		setConfiguration(config);
		initialize();
	}

	public HazelCast(String configPath){
		this.configPath = configPath;
		config = new HazelCastConfiguration();
		config.setConfigResourcePath(this.configPath);
		initialize();
	}

	public void initialize() {
		
		if (config != null) {
			ClientConfig config = null;
			try {
				config = new XmlClientConfigBuilder(
						this.config.getConfigResourcePath()).build();
				config.setClassLoader(getClass().getClassLoader());
			} catch (IOException e) {}
			this.hazelcastInstance = HazelcastClient.newHazelcastClient(config);
		} else {
			// default xml config
			this.hazelcastInstance = HazelcastClient.newHazelcastClient();
		}
	}


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


	public void setConfiguration(CacheProviderConfiguration config) {
		if (config instanceof HazelCastConfiguration) {
			this.config = (HazelCastConfiguration)config;
		}
	}


	public CacheProviderConfiguration getConfiguration() {
		return this.config;
	}


	/*protected ClassLoader getInvokerClassLoader() {
		return Bundle.this.getBundleContext().getInvokerBundleContext().getBundle().adapt(BundleWiring.class).getClassLoader();
	}

	protected void addInvokerClassLoader(ClassLoader cl) {
		((CompositeClassLoader) getInstance().getConfig().getClassLoader()).add(cl);
	}*/
}

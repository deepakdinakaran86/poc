package com.pcs.cache.hazelcast;
import com.pcs.cache.CacheConfiguration;

/**
 * @author pcseg310
 *
 */
public class HazelCastConfiguration implements CacheConfiguration {

	private static final long serialVersionUID = 3981540021171580949L;
	
	private String configResourcePath;

	public String getConfigResourcePath() {
		return configResourcePath;
	}

	public void setConfigResourcePath(String configResourcePath) {
		this.configResourcePath = configResourcePath;
	}
	
}

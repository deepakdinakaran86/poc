package com.pcs.device.gateway.G2021.bundle.utils;

import com.pcs.saffron.cache.base.CacheProvider;

public final class CacheUtil {


	private static CacheProvider cacheProvider = null;

	/**
	 * @return the cacheProvider
	 */
	public static CacheProvider getCacheProvider() {
		return cacheProvider;
	}

	/**
	 * @param cacheProvider the cacheProvider to set
	 */
	public static void setCacheProvider(CacheProvider cacheProvider) {
		
		if(CacheUtil.cacheProvider == null)
			CacheUtil.cacheProvider = cacheProvider;
	}

	/**
	 * @param cacheProvider the cacheProvider to set
	 */
	public static void resetCacheProvider(CacheProvider cacheProvider) {
		if(cacheProvider == null)
			CacheUtil.cacheProvider = null;
	}

}

package com.pcs.saffron.manager.bundle.util;

import com.pcs.saffron.cache.base.CacheProvider;

public final class CacheUtil {


	private static CacheProvider cacheProvider;

	/**
	 * @return the cacheProvider
	 */
	public static CacheProvider getCacheProvider() {
		/*if(cacheProvider == null)
			return new HazelCast();*/
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

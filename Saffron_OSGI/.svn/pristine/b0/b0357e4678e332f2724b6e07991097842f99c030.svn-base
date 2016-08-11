package com.pcs.saffron.metrics.bundle.util;

import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cache.hazelcast.HazelCast;



public final class CacheUtil {

	public static final String UNIT_SYMBOL_CACHE = "unitsymbol_physicalquantity";
	public static final String UNIT_NAME_CACHE = "unit_physicalquantity";
	public static final String PHYSICAL_QUANTITY = "physical_quantity";
	public static final String UNIT_SYMBOL_NAME_CACHE = "unitsymbol_unit";

	private static CacheProvider cacheProvider;

	/**
	 * @return the cacheProvider
	 */
	public static CacheProvider getCacheProvider() {
		if(cacheProvider == null)//temp for storm
			cacheProvider = new HazelCast();
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

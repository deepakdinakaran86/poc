package com.pcs.saffron.metrics.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.metrics.bundle.util.CacheUtil;

public final class PhysicalQuantityResolver {


	public static final String GENERIC_QUANTITY = "generic_quantity";
	private static final Logger LOGGER = LoggerFactory.getLogger(PhysicalQuantityResolver.class);
	public static final String UNITLESS = "unitless";




	/**
	 * @param key unit name or unit symbol
	 * @return physical quantity, if exist for the unit name or unit symbol
	 * generic_quantity if physical quantity cannot be found for the unit name or unit symbol.
	 * 
	 * 
	 */
	public static String resolve(String key){
		String pqByUnit = resolveByUnit(key);
		if(pqByUnit == null){
			String pqBySymbol = resolveBySymbol(key);
			if(pqBySymbol == null){
				return GENERIC_QUANTITY;
			}else{
				return pqBySymbol;
			}
		}else{
			return pqByUnit;
		}

	}

	/**
	 * @param unit
	 * @return Physical Quantity corresponding to the unit name
	 */
	private static String resolveByUnit(String unit){
		Cache pqCache = CacheUtil.getCacheProvider().getCache(CacheUtil.UNIT_NAME_CACHE);
		if(pqCache != null && !unit.equalsIgnoreCase(UNITLESS)){
			String physicalQuantity = pqCache.get(unit, String.class);
			if(physicalQuantity != null){
				return physicalQuantity;
			}else{
				return null;
			}
		}else{
			return null;
		}

	}

	/**
	 * 
	 * @param symbol
	 * @return Physical Quantity corresponding to the unit symbol
	 */
	private static String resolveBySymbol(String symbol){
		Cache pqCache = CacheUtil.getCacheProvider().getCache(CacheUtil.UNIT_SYMBOL_CACHE);
		if(pqCache != null){
			String physicalQuantity = pqCache.get(symbol, String.class);
			if(physicalQuantity != null){
				return physicalQuantity;
			}else{
				return null;
			}
		}else{
			return null;
		}

	}

	public static String resolveUnit(String unitSymbol) {
		Cache unitNameCache = CacheUtil.getCacheProvider().getCache(CacheUtil.UNIT_SYMBOL_NAME_CACHE);
		if(unitNameCache != null){
			String unitName = unitNameCache.get(unitSymbol, String.class);
			if(unitName != null){
				return unitName;
			}else{
				return unitSymbol;
			}
		}else{
			LOGGER.info("Unit symbol is either the unit name or cannot be resolved {}",unitSymbol);
			return unitSymbol;
		}

	}
	
	public static void main(String[] args) {
		String resolve = resolve("steradian");
		System.err.println(resolve);
	}
}

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
package com.pcs.datasource.services.utils;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pcs.datasource.cache.CacheManager;
import com.pcs.datasource.cache.CacheProviderType;
import com.pcs.datasource.dto.ConfigDTO;
import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.cache.CacheProviderConfiguration;
import com.pcs.deviceframework.cache.hazelcast.HazelCastConfiguration;

/**
 * This utility class to process cache
 * 
 * @author pcseg323 (Greeshma)
 * @date April 2015
 * @since galaxy-1.0.0
 */

@Component
public class CacheUtility {

	private static final Logger logger = LoggerFactory
	        .getLogger(CacheUtility.class);

	private CacheProvider cacheProvider;

	private ConfigDTO configDTO;

	private boolean initzd = false;

	@Value("${cache.provider}")
	private String cacheProviderName;

	@Value("${cache.provider.configpath}")
	private String cacheProviderConfig;

	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}

	@PostConstruct
	private void init() {
		if (!initzd) {
			readConfig();
			setCacheProvider();
		}
	}

	private void setCacheProvider() {
		CacheProviderType providertype = resolveCacheProvider();
		CacheProviderConfiguration config = buildCacheConfiguration(providertype);
		cacheProvider = CacheManager.instance().getProvider(providertype,
		        config);
	}

	private CacheProviderConfiguration buildCacheConfiguration(
	        CacheProviderType providertype) {
		CacheProviderConfiguration config = null;
		switch (providertype) {
			case HZ :
				config = new HazelCastConfiguration();
				String configResourcePath = configDTO
				        .getCacheproviderConfigPath();
				((HazelCastConfiguration)config)
				        .setConfigResourcePath(configResourcePath);
				break;
			case InMemory :
				break;
			default:
				break;

		}
		return config;
	}

	private CacheProviderType resolveCacheProvider() {
		CacheProviderType providertype = null;
		String cacheProviderType = configDTO.getCacheprovider();
		providertype = CacheProviderType.valueOfName(cacheProviderType);
		return providertype;
	}

	private void readConfig() {
		try {
			configDTO = new ConfigDTO();
			configDTO.setCacheprovider(cacheProviderName);
			configDTO.setCacheproviderConfigPath(cacheProviderConfig);
		} catch (Exception ex) {
			logger.error("Error while reading application configuration", ex);
		}
	}

}

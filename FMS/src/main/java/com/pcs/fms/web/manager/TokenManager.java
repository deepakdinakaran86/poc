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
package com.pcs.fms.web.manager;

import java.util.HashMap;

import com.pcs.fms.web.manager.dto.Token;
import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.cache.hazelcast.HazelCast;

/**
 * @author PCSEG296 RIYAS PH
 * @date JULY 2016
 * @since FMS1.0.0
 * 
 */
public final class TokenManager {

	private static final String TOKEN_CACHE_NAME = "fms.cache";
	private static final String TOKEN_NAME = "token";

	private static HazelCast hazelCast;

	static {
		hazelCast = new HazelCast();
	}

	public static Token getToken(String identifier) {
		Cache cache = hazelCast.getCache(TOKEN_CACHE_NAME);
		Token token = null;
		HashMap<String, Object> userCache = (HashMap<String, Object>)cache
		        .get(identifier);
		if (userCache != null) {
			token = (Token)userCache.get(TOKEN_NAME);
		}
		return token;
	}

	public static void setToken(Token token, String identifier) {
		Cache cache = hazelCast.getCache(TOKEN_CACHE_NAME);
		HashMap<String, Object> userCache = new HashMap<>();
		userCache.put(TOKEN_NAME, token);
		cache.put(identifier, userCache);
	}

	public static void invalidateToken(String identifier) {
		Cache cache = hazelCast.getCache(TOKEN_CACHE_NAME);
		cache.evict(identifier);
	}

}

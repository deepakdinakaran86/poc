package com.pcs.guava.token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import sun.misc.BASE64Encoder;

import com.pcs.cache.Cache;
import com.pcs.cache.CacheConfiguration;
import com.pcs.cache.CacheManager;
import com.pcs.cache.factory.CacheManagerFactory;
import com.pcs.cache.factory.CacheManagerType;
import com.pcs.cache.hazelcast.HazelCastConfiguration;
import com.pcs.galaxy.rest.client.BaseClient;
import com.pcs.galaxy.rest.exception.GalaxyRestException;
import com.pcs.guava.token.config.TokenManagerConfiguration;

/**
 * @author pcseg310
 * 
 */
public final class TokenManager {

	private static CacheManager cacheManager;

	private static final String TOKEN_CACHE_NAME = "apimanager.token.cache";
	private static final String AUTHORIZATION = "Authorization";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_VALUE = "application/x-www-form-urlencoded";

	private TokenManager() {
	}

	static {
		CacheManagerType type = resolveCacheManagerType("HZ");
		CacheConfiguration cacheConfig = resolveCacheConfiguration(type,
		        "hazelcast-client.xml");
		cacheManager = CacheManagerFactory.instance().getManager(type,
		        cacheConfig);
	}

	public static TokenInfoDTO getToken(String consumerKey, String secretKey) {
		if (consumerKey == null || secretKey == null) {
			throw new IllegalArgumentException(
			        "ConsumerKey and SecretKey is must, should not be null");
		}

		BaseClient client = getPlatformClient();
		Map<String, String> header = new HashMap<String, String>();
		String authToken = getAuthorizationCode(consumerKey, secretKey);
		header.put(AUTHORIZATION, authToken);
		header.put(CONTENT_TYPE, CONTENT_VALUE);
		TokenInfoDTO token = client.post("token?grant_type=client_credentials",
		        header, null, TokenInfoDTO.class);

		return token;
	}

	public static TokenInfoDTO getToken(String consumerKey, String secretKey,
	        String sessionId) {
		if (consumerKey == null || secretKey == null) {
			throw new IllegalArgumentException(
			        "ConsumerKey and SecretKey is must, should not be null");
		}

		String cacheKey = sessionId;

		Cache tokenCache = cacheManager.getCache(TOKEN_CACHE_NAME);
		TokenInfoDTO token = tokenCache.get(cacheKey, TokenInfoDTO.class);

		if (token == null) {

			BaseClient client = getPlatformClient();
			Map<String, String> header = new HashMap<String, String>();

			String authToken = getAuthorizationCode(consumerKey, secretKey);

			header.put(AUTHORIZATION, authToken);
			header.put(CONTENT_TYPE, CONTENT_VALUE);
			token = client.post("token?grant_type=client_credentials", header,
			        null, TokenInfoDTO.class);

			long tokenExpiresIn = Long.valueOf(token.getExpireIn());
			if (tokenExpiresIn > 0) {
				tokenCache.put(cacheKey, token, tokenExpiresIn,
				        TimeUnit.SECONDS);
			}
		}
		return token;
	}

	public static TokenInfoDTO getToken(String consumerKey, String secretKey,
	        String userName, String password, String identifier) {
		// key used to cache the token for this user.
		String cacheKey = identifier;

		Cache tokenCache = cacheManager.getCache(TOKEN_CACHE_NAME);
		TokenInfoDTO token = tokenCache.get(cacheKey, TokenInfoDTO.class);

		if (token == null) {
			BaseClient client = getPlatformClient();

			Map<String, String> header = new HashMap<String, String>();

			String applicationToken = getAuthorizationCode(consumerKey,
			        secretKey);

			header.put(AUTHORIZATION, applicationToken);
			header.put(CONTENT_TYPE, CONTENT_VALUE);
			try {
				token = client.post("token?grant_type=password&username="
				        + userName + "&password=" + password, header, null,
				        TokenInfoDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
				throw new GalaxyRestException();
			}

			try {
				BaseClient platformClient = getUserClient();
				header = new HashMap<String, String>();
				header.put(AUTHORIZATION, "Bearer " + token.getAccessToken());
				UserPermissionsDTO user = platformClient.get(
				        "users/1.0.0/users/permissions/current", header,
				        UserPermissionsDTO.class);
				if (user != null) {
					token.setUserName(userName.substring(0,
					        userName.indexOf('@')));
					List<String> roleNames = new ArrayList<String>();
					roleNames.addAll(user.getRoleNames());
					token.setRoleNames(roleNames);
					List<String> permissions = new ArrayList<String>();
					permissions.addAll(user.getPermissions());
					token.setPremissions(permissions);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// TODO
			/* Code for fetching Tenant Name, template of tenant domain */
			try {
				BaseClient platformClient = getTenantClient();
				header = new HashMap<String, String>();
				header.put(AUTHORIZATION, "Bearer " + token.getAccessToken());

				EntityDTO domainEntityDTO = platformClient.get(
				        "tenants/1.0.0/tenants/domain/info", header,
				        EntityDTO.class);
				Tenant tenant = new Tenant();

				if (domainEntityDTO != null) {
					tenant.setGlobalEntityType(domainEntityDTO
					        .getGlobalEntity().getGlobalEntityType());
					tenant.setDomainName(domainEntityDTO.getDomain()
					        .getDomainName());
					tenant.setEntityTemplateName(domainEntityDTO
					        .getEntityTemplate().getEntityTemplateName());
					tenant.setTenantName(domainEntityDTO.getIdentifier()
					        .getValue());
					tenant.setTenantId(domainEntityDTO.getIdentifier()
					        .getValue());
					tenant.setCurrentDomain(domainEntityDTO.getHierarchy()
					        .getDomain().getDomainName());
					token.setTenant(tenant);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// TODO

			long tokenExpiresIn = Long.valueOf(token.getExpireIn());
			if (tokenExpiresIn > 0) {
				tokenCache.put(cacheKey, token, tokenExpiresIn,
				        TimeUnit.SECONDS);
			}
		}

		return token;
	}

	public static void revoke(String consumerKey, String secretKey,
	        String refreshToken, String identifier) {
		// key used to cache the token for this user.
		BaseClient client = getPlatformClient();

		Map<String, String> header = new HashMap<String, String>();

		String applicationToken = getAuthorizationCode(consumerKey, secretKey);

		header.put(AUTHORIZATION, applicationToken);
		header.put(CONTENT_TYPE, CONTENT_VALUE);
		try {
			client.post("revoke?token=" + refreshToken, header, null, null);
			invalidateToken(identifier);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GalaxyRestException();
		}

	}

	public static void invalidateToken(String identifier) {
		Cache tokenCache = cacheManager.getCache(TOKEN_CACHE_NAME);
		tokenCache.evict(identifier);
	}

	public static TokenInfoDTO getToken(String identifier) {
		Cache tokenCache = cacheManager.getCache(TOKEN_CACHE_NAME);
		return tokenCache.get(identifier, TokenInfoDTO.class);
	}

	private static String getAuthorizationCode(String consumerKey,
	        String secretKey) {
		String authToken = consumerKey + ":" + secretKey;
		BASE64Encoder base64Encoder = new BASE64Encoder();
		authToken = "Basic "
		        + base64Encoder.encode(authToken.getBytes()).trim();
		return authToken;
	}

	private static BaseClient getPlatformClient() {
		BaseClient client = null;
		client = new BaseClient(
		        TokenManagerConfiguration
		                .getProperty(TokenManagerConfiguration.API_MANAGER_GET_TOKEN_URL));
		client.intialize();
		return client;
	}

	private static BaseClient getUserClient() {
		BaseClient client = null;
		client = new BaseClient(
		        TokenManagerConfiguration
		                .getProperty(TokenManagerConfiguration.API_PLATFORM_USER_URL));
		client.intialize();
		return client;
	}

	private static BaseClient getTenantClient() {
		BaseClient client = null;
		client = new BaseClient(
		        TokenManagerConfiguration
		                .getProperty(TokenManagerConfiguration.API_PLATFORM_TENANT_URL));
		client.intialize();
		return client;
	}

	private static CacheManagerType resolveCacheManagerType(String name) {
		return CacheManagerType.valueOf(name);
	}

	private static CacheConfiguration resolveCacheConfiguration(
	        CacheManagerType type, String configPath) {
		CacheConfiguration cc = null;
		switch (type) {
			case HZ :
				cc = new HazelCastConfiguration();
				((HazelCastConfiguration)cc).setConfigResourcePath(configPath);
				break;
			case InMemory :
				break;
			default:
				break;
		}
		return cc;
	}

}

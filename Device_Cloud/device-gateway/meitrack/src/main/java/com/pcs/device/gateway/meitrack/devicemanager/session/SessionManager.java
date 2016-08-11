/**
 * 
 */
package com.pcs.device.gateway.meitrack.devicemanager.session;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.pcs.device.gateway.meitrack.config.MeitrackGatewayConfiguration;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.devicemanager.session.SessionInfo;

/**
 * Session manager is responsible to handle the session lifecycle.It depends on
 * {@link CacheProvider} to store the session information for a time period.
 * 
 * @author pcseg310
 *
 */
public final class SessionManager {

	private CacheProvider cacheProvider;

	public SessionManager(CacheProvider cacheProvider) {
		this.setCacheProvider(cacheProvider);
	}

	/**
	 * Invalidates the current session for the given principal
	 * 
	 * @param principal
	 * @return
	 */
	public boolean invalidateSession(Object principal) {
		boolean invalidated = false;
		evictSession(principal);
		invalidated = true;
		return invalidated;
	}

	/**
	 * Creates a new expirable {@link SessionInfo} for the given principal
	 * 
	 * @param principal
	 * @return
	 */
	public SessionInfo createNewSession(Object principal) {
		Integer sessionId = createRandomSessionId();
		SessionInfo session = new SessionInfo(principal, sessionId, DateTime.now().toDate());
		session.setSessionTimeOut(getTimeOutValueInSeconds());
		registerSession(principal, session);

		return session;
	}

	/**
	 * Refresh the current {@link SessionInfo} for the given principal.
	 * 
	 * @param principal
	 * @return
	 */
	public SessionInfo refreshSession(Object principal) {
		SessionInfo session = getSessionByPrincipal(principal);
		if (session != null) {
			session.refreshLastRequest();
			registerSession(principal, session);
		}
		return session;
	}

	/**
	 * Retrieves the current {@link SessionInfo} for the given principal
	 * 
	 * @param principal
	 * @return
	 */
	public SessionInfo getSessionByPrincipal(Object principal) {
		Cache deviceSessionCache = getCacheProvider().getCache(MeitrackGatewayConfiguration.DEVICE_SESSION_CACHE);
		return deviceSessionCache.get(principal, SessionInfo.class);
	}

	private void registerSession(Object principal, SessionInfo session) {
		Long timeout = getTimeOutValueInSeconds();

		Cache deviceSessionCache = getCacheProvider().getCache(MeitrackGatewayConfiguration.DEVICE_SESSION_CACHE);
		deviceSessionCache.put(principal, session, timeout, TimeUnit.SECONDS);
	}

	private Integer createRandomSessionId() {
		Random random = new Random();
		return random.nextInt(Integer.MAX_VALUE);
	}

	private void evictSession(Object unitId) {
		Cache deviceSessionCache = getCacheProvider().getCache(MeitrackGatewayConfiguration.DEVICE_SESSION_CACHE);
		deviceSessionCache.evict(unitId);
	}

	private Long getTimeOutValueInSeconds() {
		Long timeout = Long.valueOf(MeitrackGatewayConfiguration
		        .getProperty(MeitrackGatewayConfiguration.DEVICE_SESSION_CACHE_TIMEOUT));
		return timeout;
	}

	private CacheProvider getCacheProvider() {
		return cacheProvider;
	}

	private void setCacheProvider(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}
}

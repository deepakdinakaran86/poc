/**
 * 
 */
package com.pcs.saffron.manager.session;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bundle.util.CacheUtil;
import com.pcs.saffron.manager.config.ManagerConfiguration;

/**
 * Session manager is responsible to handle the session lifecycle.It depends on
 * {@link CacheProvider} to store the session information for a time period.
 * 
 * @author pcseg310
 *
 */
public final class SessionManager {


	public SessionManager(CacheProvider cacheProvider) {
		getCacheProvider();
	}

	/**
	 * Invalidates the current session for the given principal
	 * 
	 * @param principal
	 * @return
	 */
	public boolean invalidateSession(Object principal,Gateway gateway) {
		boolean invalidated = false;
		evictSession(principal,gateway);
		invalidated = true;
		return invalidated;
	}

	/**
	 * Creates a new expirable {@link SessionInfo} for the given principal
	 * 
	 * @param principal
	 * @return
	 */
	public SessionInfo createNewSession(Object principal,Gateway gateway) {
		Integer sessionId = createRandomSessionId();
		SessionInfo session = new SessionInfo(principal, sessionId, DateTime.now().toDate());
		session.setSessionTimeOut(getTimeOutValueInSeconds(gateway));
		registerSession(principal, session,gateway);

		return session;
	}

	/**
	 * Refresh the current {@link SessionInfo} for the given principal.
	 * 
	 * @param principal
	 * @return
	 */
	public SessionInfo refreshSession(Object principal,Gateway gateway) {
		SessionInfo session = getSessionByPrincipal(principal,gateway);
		if (session != null) {
			session.refreshLastRequest();
			registerSession(principal, session,gateway);
		}
		return session;
	}

	/**
	 * Retrieves the current {@link SessionInfo} for the given principal
	 * 
	 * @param principal
	 * @return
	 */
	public SessionInfo getSessionByPrincipal(Object principal,Gateway gateway) {
		Cache deviceSessionCache = getCacheProvider().getCache(getDeviceSessionCache(gateway));
		return deviceSessionCache.get(principal, SessionInfo.class);
	}

	

	private void registerSession(Object principal, SessionInfo session,Gateway gateway) {
		Long timeout = getTimeOutValueInSeconds(gateway);

		Cache deviceSessionCache = getCacheProvider().getCache(getDeviceSessionCache(gateway));
		deviceSessionCache.put(principal, session, timeout, TimeUnit.SECONDS);
	}

	private Integer createRandomSessionId() {
		Random random = new Random();
		return random.nextInt(Integer.MAX_VALUE);
	}

	private void evictSession(Object unitId,Gateway gateway) {
		Cache deviceSessionCache = getCacheProvider().getCache(getDeviceSessionCache(gateway));
		deviceSessionCache.evict(unitId);
	}

	private Long getTimeOutValueInSeconds(Gateway gateway) {
		Long timeout = Long.valueOf(ManagerConfiguration
		        .getProperty(ManagerConfiguration.DEVICE_SESSION_CACHE_TIMEOUT));
		return timeout;
	}


	
	private String getDeviceSessionCache(Gateway gateway) {
		return ManagerConfiguration.DEVICE_SESSION_CACHE+gateway.getId();
	}
	
	private CacheProvider getCacheProvider() {
		return CacheUtil.getCacheProvider();
	}
}

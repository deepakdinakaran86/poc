package com.pcs.deviceframework.devicemanager;

import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.deviceframework.devicemanager.authentication.AuthenticationResponse;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;
import com.pcs.deviceframework.devicemanager.session.SessionInfo;

/**
 * Defines the responsibilities of a Device-Manager.
 * 
 * @author pcseg171, pcseg310
 *
 */
public abstract class DeviceManager {

	/**
	 * Retrieves {@link CacheProvider} of the cache system managed by the device
	 * manager.The consumers of device manager can consume some of the permitted
	 * services provided by the device manager from the cache.
	 * 
	 * @return
	 */
	public abstract CacheProvider getCacheProvider();

	/**
	 * Authenticates the source.
	 * 
	 * @param source
	 * @return
	 */
	public abstract AuthenticationResponse authenticate(Object sourceId);

	/**
	 * Retrieves a collection of configuration for a device.If nothing
	 * available,it returns an empty collection
	 * 
	 * @param source
	 * @return
	 */
	public abstract DeviceConfiguration getConfiguration(Object sourceId);
	
	/**
	 * Retrieves session information for a given principal id.
	 * 
	 * @param principal
	 * @return
	 */
	public abstract SessionInfo getSessionByPrincipal(Object principal);
	
	/**
	 * Invalidates the session for the given principal id
	 * 
	 * @param principal
	 * @return
	 */
	public abstract boolean invalidateSession(Object principal);
	
}

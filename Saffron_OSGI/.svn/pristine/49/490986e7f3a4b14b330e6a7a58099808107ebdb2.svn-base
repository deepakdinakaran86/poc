package com.pcs.saffron.manager;

import java.util.HashMap;
import java.util.List;

import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cipher.data.generic.message.GenericEvent;
import com.pcs.saffron.cipher.data.generic.message.GenericMessage;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.message.status.MessageCompletion;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;
import com.pcs.saffron.manager.bean.DeviceConfiguration;
import com.pcs.saffron.manager.bean.WriteResponseMessage;
import com.pcs.saffron.manager.bean.WritebackConfiguration;
import com.pcs.saffron.manager.session.SessionInfo;
import com.pcs.saffron.manager.writeback.listener.CommandListener;

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
	public abstract AuthenticationResponse authenticate(Device device,Gateway gateway);

	/**
	 * Retrieves a collection of configuration for a device.If nothing
	 * available,it returns an empty collection
	 * 
	 * @param source
	 * @return
	 */
	public abstract DeviceConfiguration getConfiguration(Object sourceId,Gateway gateway);

	/**
	 * Retrieves session information for a given principal id.
	 * 
	 * @param principal
	 * @return
	 */
	public abstract SessionInfo getSessionByPrincipal(Object principal,Gateway gateway);

	/**
	 * Invalidates the session for the given principal id
	 * 
	 * @param principal
	 * @return
	 */
	public abstract boolean invalidateSession(Object principal,Gateway gateway);

	public abstract HashMap<String, Point> getPointsByProtocolAndDeviceType(Gateway gateway);

	public abstract void refreshDeviceConfiguration(DefaultConfiguration configuration,Gateway gateway);

	public abstract void updateDeviceWritebackConfig(WritebackConfiguration writebackConfiguration,Gateway gateway );

	public abstract boolean registerDatasource(DefaultConfiguration deviceConfiguration,Gateway gateway );

	public abstract void publishData(Message message,DefaultConfiguration deviceConfiguration,Gateway gateway);
	
	public abstract void notifyMessage(Message message,DefaultConfiguration deviceConfiguration,MessageCompletion messageCompletion,Gateway gateway);
	public abstract void notifyMessages(List<Message> messages,DefaultConfiguration deviceConfiguration,MessageCompletion messageCompletion,Gateway gateway);
	
	public abstract void sendWriteResponse(WriteResponseMessage writeResponse );
	
	public abstract void setDeviceConfiguration(DefaultConfiguration configuration,Gateway gateway );
	
	public abstract void notifyAlarm(AlarmMessage alarm, Gateway gateway);
	public abstract void notifyAlarms(List<AlarmMessage> alarms, Gateway gateway);
	
	public abstract void notify(Object identifier, Gateway gateway);
	
	public abstract void notifyGenericMessage(GenericMessage genericMessage, Gateway gateway);
	public abstract void notifyGenericMessages(List<GenericMessage> genericMessages, Gateway gateway);
	
	public abstract void notifyGenericAlarm(GenericEvent genericEvent, Gateway gateway);
	
	public abstract void notifyGenericAlarms(List<GenericEvent> genericEvents, Gateway gateway);
	
	public abstract void registerCommandListener(CommandListener commandListener , Gateway gateway);
	public abstract void deregisterCommandListener(Gateway gateway);


}

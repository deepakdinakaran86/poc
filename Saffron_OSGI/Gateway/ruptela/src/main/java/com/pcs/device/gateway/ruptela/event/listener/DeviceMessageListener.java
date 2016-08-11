/**
 * 
 */
package com.pcs.device.gateway.ruptela.event.listener;

import java.util.EventListener;

import com.pcs.device.gateway.ruptela.event.DeviceMessageEvent;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;

/**
 * @author PCSEG171
 *
 */
public interface DeviceMessageListener  extends EventListener{
/**
 * 
 * @param deviceMessageEvent
 */
public void messageReady(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration defaultConfiguration,Gateway gateway);

public void newMessageReceived(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration defaultConfiguration,Gateway gateway);

public void historyMessageReceived(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration defaultConfiguration,Gateway gateway);

public void alarmReceived(DeviceMessageEvent deviceMessageEvent,DefaultConfiguration defaultConfiguration,Gateway gateway);

public void addPublisher(CorePublisher basePublisher);

}

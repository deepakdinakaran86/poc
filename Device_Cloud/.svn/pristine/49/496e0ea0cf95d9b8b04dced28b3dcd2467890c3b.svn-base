/**
 * 
 */
package com.pcs.device.gateway.teltonika.event.listener;

import java.util.EventListener;

import com.pcs.device.gateway.teltonika.event.DeviceMessageEvent;
import com.pcs.deviceframework.pubsub.publishers.BasePublisher;

/**
 * @author PCSEG171
 *
 */
public interface DeviceMessageListener  extends EventListener{
/**
 * 
 * @param deviceMessageEvent
 */
public void messageReady(DeviceMessageEvent deviceMessageEvent);

public void newMessageReceived(DeviceMessageEvent deviceMessageEvent);

public void historyMessageReceived(DeviceMessageEvent deviceMessageEvent);

public void addPublisher(BasePublisher basePublisher);
}

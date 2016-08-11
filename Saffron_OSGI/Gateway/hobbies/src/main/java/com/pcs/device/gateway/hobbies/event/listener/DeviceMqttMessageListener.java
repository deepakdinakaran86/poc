/**
 * 
 */
package com.pcs.device.gateway.hobbies.event.listener;

import java.util.EventListener;

import com.pcs.device.gateway.hobbies.event.DeviceMqttEvent;

/**
 * @author PCSEG171
 *
 */
public interface DeviceMqttMessageListener  extends EventListener{
/**
 * 
 * @param deviceMessageEvent
 */
public void messageReady(DeviceMqttEvent deviceMessageEvent);

}

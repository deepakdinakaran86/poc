/**
 * 
 */
package com.pcs.device.gateway.teltonika.devicemanager.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author pcseg310
 *
 */
@JsonSerialize
public enum StatusType {

	ACTIVE,INACTIVE;
}

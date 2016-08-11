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
package com.pcs.datasource.enums;

import com.pcs.datasource.exception.InvalidDeviceTypeException;

/**
 * DeviceType
 * 
 * @description holds different type of devices communicating to the device
 * 
 * @author Greeshma (pcseg323)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public enum DeviceType {

	G2021("G2021"), TELTONIKA("Teltonika");

	private String deviceType;

	private DeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public static DeviceType getDeviceType(String value) {

		for (DeviceType v : values()) {
			if (v.getDeviceType().equalsIgnoreCase(value)) {
				return v;
			}
		}
		throw new InvalidDeviceTypeException();
	}
}

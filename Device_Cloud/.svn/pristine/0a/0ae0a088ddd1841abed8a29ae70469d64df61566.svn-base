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

/**
 * CommandCode
 * 
 * @description holds different type of commands for the communicationg devices
 * 
 * @author Greeshma (pcseg323)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public enum CommandCode {

	POINT_DISCOVERY_REQ("Point Discovery Request"), SYSTEM_CMD_REQ(
	        "System Command Request"), POINT_INSTANT_SNAPSHOT_REQ(
	        "Point Instant Snapshot Request"), POINT_WRITE_CMD(
	        "Point Write Command");

	private String commandCode;

	private String getCommandCode() {
		return commandCode;
	}

	private CommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public static CommandCode getCommandCode(String value) {

		if (value == null) {
			return null;
		}

		for (CommandCode v : values()) {
			if (v.getCommandCode().equalsIgnoreCase(value)) {
				return v;
			}
		}
		return null;
	}

}

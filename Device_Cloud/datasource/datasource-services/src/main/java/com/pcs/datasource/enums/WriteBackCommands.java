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
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date Apr 21, 2015
 * @since galaxy-1.0.0
 */
public enum WriteBackCommands {

	SYSTEM_COMMAND("0X20", "SYSTEM_COMMAND"),

	WRITE_COMMAND("0x22", "WRITE_COMMAND");

	private String type;
	private String name;
	private static final WriteBackCommands[] values = values();

	WriteBackCommands(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static WriteBackCommands valueOfType(String type) {
		if (type == null) {
			return null;
		}
		for (WriteBackCommands g2021MsgType : values) {
			if (type.equalsIgnoreCase(g2021MsgType.getType())) {
				return g2021MsgType;
			}
		}
		return null;
	}

	public static WriteBackCommands valueOfName(String name) {
		if (name == null) {
			return null;
		}
		for (WriteBackCommands g2021MsgType : values) {
			if (name.equalsIgnoreCase(g2021MsgType.getName())) {
				return g2021MsgType;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

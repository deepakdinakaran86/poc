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
public enum G2021CommandCodes {

	REBOOT("Reboot"),

	SYNC("Sync"),

	APP_SAVE("App Save");

	private String name;
	private static final G2021CommandCodes[] values = values();

	G2021CommandCodes(String name) {
		this.name = name;
	}

	public static G2021CommandCodes valueOfName(String name) {
		if (name == null) {
			return null;
		}
		for (G2021CommandCodes g2021MsgType : values) {
			if (name.equalsIgnoreCase(g2021MsgType.getName())) {
				return g2021MsgType;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

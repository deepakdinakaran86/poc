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
package com.pcs.avocado.enums;

/**
 * DataType
 *
 * @description holds different type of devices communicating to the device
 *
 * @author Greeshma (pcseg323)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public enum DataTypes {

	BOOLEAN("Boolean"),

	SHORT("Short"),

	INTEGER("Integer"),

	LONG("Long"),

	DOUBLE("Double"),

	FLOAT("Float"),	

	STRING("String");

	private String dataType;

	public String getDataType() {
		return dataType;
	}

	private DataTypes(String deviceType) {
		this.dataType = deviceType;
	}

	public static DataTypes getDataType(String value) {

		for (DataTypes v : values()) {
			if (v.getDataType().equalsIgnoreCase(value)) {
				return v;
			}
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(DataTypes.getDataType("boolean"));
	}
}

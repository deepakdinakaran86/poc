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
package com.pcs.alpine.enums;

/**
 * GalaxyValidationDataFields
 * 
 * @description This class defines enums being used for types of field
 *              validation
 * @author Twinkle (pcseg297)
 * @date Dec 2014
 * @since galaxy-1.0.0
 */
public enum GalaxyValidationDataFields {

	MANDATORY("mandatory"), UNIQUE_ACROSS_DOMAIN("uniqueAcrossDomain"),
	UNIQUE_ACROSS_APPLICATION("uniqueAcrossApplication"), SHOW_ON_TREE(
	        "showOnTree"), SHOW_ON_GRID("showOnGrid"), UNIQUE_ACROSS_HIERARCHY(
	        "uniqueAcrossHierarchy"), UNIQUE_ACROSS_TEMPLATE(
	        "uniqueAcrossTemplate");

	private String fieldName;

	private GalaxyValidationDataFields(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return this.fieldName;
	}

	private String getFieldName() {
		return fieldName;
	}

	public static GalaxyValidationDataFields getEnum(String value) {
		for (GalaxyValidationDataFields v : values()) {
			if (v.getFieldName().equalsIgnoreCase(value)) {
				return v;
			}
		}
		throw new IllegalArgumentException();
	}

}

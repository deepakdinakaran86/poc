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
package com.pcs.guava.enums;

import com.pcs.guava.commons.validation.DataFields;

/**
 * PoiTypeDataFields Enum
 * 
 * @author Greeshma (PCSEG323)
 * 
 */
public enum PoiTypeDataFields implements DataFields {

	DOMAIN_NAME("domainName", "domainName", "Domain Name"),

	POI_TYPE("poiType", "poiType", "POI Type"),

	POI_TYPES("poiTypes", "poiTypes", "POI Types"),

	DESCRIPTION("description", "description", "Description"),

	IDENTIFIER("identifier", "identifier", "Identifier"),

	PARENT_TEMPLATE("parentTemplate", "parentTemplate", "Parent Template"),

	KEY("key", "key", "Key");

	private String fieldName;
	private String variableName;
	private String description;

	private PoiTypeDataFields(String fieldName, String variableName,
			String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getVariableName() {
		return variableName;
	}

	@Override
	public String getDescription() {
		return description;
	}

}

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
 * PoiDataFields Enum
 * 
 * @author Greeshma (PCSEG323)
 * 
 */
public enum PoiDataFields implements DataFields {

	POI_NAME("poiName", "poiName", "POI Name"),

	DESCRIPTION("description", "description", "Description"),

	IDENTIFIER("identifier", "identifier", "Identifier"),

	RADIUS("radius", "radius", "Radius"),

	LATITUDE("latitude", "latitude", "latitude"),

	LONGITUDE("longitude", "longitude", "longitude"),

	POI_ENTITY_TEMPLATE("entityTemplate", "entityTemplate",
			"Poi Entity Template"),

	POI_IDENTIFIER("identifier", "identifier", "Poi Identifier"),

	POI_IDENTIFIER_KEY("key", "key", "Poi Identifier Key"),

	POI_IDENTIFIER_VALUE("value", "value", "Poi Identifier Value"),

	POI_ENTITY_TEMPLATE_NAME("entity_template_name", "entityTemplateName",
			"Poi Entity Template Name"),

	POI_TEMPLATE("Poi", "Poi", "Poi"), POI("Poi", "Poi", "Poi");
	;

	private String fieldName;
	private String variableName;
	private String description;

	private PoiDataFields(String fieldName, String variableName,
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

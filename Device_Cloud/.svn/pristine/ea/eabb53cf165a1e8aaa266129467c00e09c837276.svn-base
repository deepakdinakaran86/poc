
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.datasource.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

/**
 * This class is responsible for datasource parameter fields
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date 07 Apr 2015
 */
public enum ParameterFields implements DataFields {
	
	NAME("parameter_name", "name", "Parameter Name"),
	DATA_TYPE("parameter_type", "dataType", "Parameter Type"),
	VALUE("value", "value", "value");
	
	private String fieldName;
	private String variableName;
	private String description;
	
	public String getFieldName() {
		return fieldName;
	}
	public String getVariableName() {
		return variableName;
	}
	public String getDescription() {
		return description;
	}
	
	private ParameterFields(String fieldName,String variableName,String description){
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

}

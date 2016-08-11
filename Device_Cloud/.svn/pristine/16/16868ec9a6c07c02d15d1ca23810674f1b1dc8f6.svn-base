
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
package com.pcs.devicecloud.commons.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129
 */
public class ExcludeFields implements ExclusionStrategy {

	private Set<String> fields = new HashSet<String>();
	
    public Set<String> getFields() {
		return fields;
	}

	public void setFields(Set<String> fields) {
		this.fields = fields;
	}
	
	public void addField(String fieldName){
		fields.add(fieldName);
	}

	@Override
    public boolean shouldSkipField(FieldAttributes field) {
    	for(String fieldName:fields){
    		if(field.getName().equals(fieldName)){
    			return true;
    		}
    	}
	    return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
	    return false;
    }
    
    public void addExcludeFields(String... fieldNames){
    	fields.addAll(Arrays.asList(fieldNames));
    }

}

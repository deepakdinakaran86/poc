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
package com.pcs.alpine.commons.rest.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * That way it is now clear at compile time which attributes are supported by a
 * given GenericDTO instance
 * 
 * @author pcseg297
 */
public class GenericDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> attributes = new LinkedHashMap<String, Object>();

	public static GenericDTO getInstance() {
		return new GenericDTO();
	}

	public GenericDTO set(String identifier, Object value) {
		attributes.put(identifier, value);
		return this;
	}

	public Object get(String identifier) {
		Object theValue = attributes.get(identifier);
		return theValue;
	}

	public Object remove(String identifier) {
		Object theValue = attributes.remove(identifier);
		return theValue;
	}

	public void clear() {
		attributes.clear();
	}

	public int size() {
		return attributes.size();
	}

	public Set<String> getAttributes() {
		return attributes.keySet();
	}

	public boolean contains(String identifier) {
		return attributes.containsKey(identifier);
	}

	@Override
	public String toString() {
		return attributes.entrySet().toString();
	}
}
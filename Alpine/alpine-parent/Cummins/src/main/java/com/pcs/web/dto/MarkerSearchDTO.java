package com.pcs.web.dto;

import java.io.Serializable;
import java.util.List;

public class MarkerSearchDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2601637715386385477L;
	private List<FieldMapDTO> fieldValues;

	public List<FieldMapDTO> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldMapDTO> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
	

}

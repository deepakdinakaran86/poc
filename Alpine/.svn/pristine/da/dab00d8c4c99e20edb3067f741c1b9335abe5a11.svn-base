package com.pcs.alpine.services.dto;

import com.google.gson.Gson;
import com.pcs.alpine.services.dto.FieldMapDTO;

public class ValidationTestDTO {
	
	private FieldMapDTO fieldMapDTO;
	private String validationJsonString;
	
	private ValidationJsonStringDTO validationJsonStringDTO;
	
	public FieldMapDTO getFieldMapDTO() {
		return fieldMapDTO;
	}
	public void setFieldMapDTO(FieldMapDTO fieldMapDTO) {
		this.fieldMapDTO = fieldMapDTO;
	}
	public String getValidationJsonString(Gson gson) {
		validationJsonString = gson.toJson(validationJsonStringDTO);
		return validationJsonString;
	}
	public void setValidationJsonString(String validationJsonString, Gson gson) {
		this.validationJsonStringDTO = 
				gson.fromJson(validationJsonString, ValidationJsonStringDTO.class);
		this.validationJsonString = validationJsonString;
	}
	public ValidationJsonStringDTO getValidationJsonStringDTO() {
		return validationJsonStringDTO;
	}
	public void setValidationJsonStringDTO(
			ValidationJsonStringDTO validationJsonStringDTO) {
		this.validationJsonStringDTO = validationJsonStringDTO;
	}
	
	

}

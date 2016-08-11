package com.pcs.alpine.services.dto.builder;

import com.pcs.alpine.services.dto.FieldMapDTO;

public class FieldMapDTOBuilder {

	private FieldMapDTO fieldMapDto;

	private String key = "imei";
	private String value = "881234567890142";
	
	public FieldMapDTOBuilder aFieldMapDTO() {
		if (fieldMapDto == null) {
			fieldMapDto = new FieldMapDTO();
			fieldMapDto.setKey(key);
			fieldMapDto.setValue(value);
		}
		return this;
	}

	public FieldMapDTOBuilder aFieldMapDTO(String key, String value) {
		if (fieldMapDto == null) {
			fieldMapDto = new FieldMapDTO();
			fieldMapDto.setKey(key);
			fieldMapDto.setValue(value);
		}
		return this;
	}
	public FieldMapDTO build() {
		return fieldMapDto;
	}
}

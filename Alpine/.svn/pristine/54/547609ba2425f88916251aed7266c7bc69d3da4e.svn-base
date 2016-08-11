package com.pcs.alpine.services.dto.builder;


import com.pcs.alpine.services.dto.GlobalEntityDTO;
import com.pcs.alpine.services.enums.EMDataFields;

public class GlobalEntityDTOBuilder {

	private GlobalEntityDTO globalEntityDto;

	private String globalEntityType = EMDataFields.ORGANIZATION.getFieldName();

	public GlobalEntityDTOBuilder aGlobalEntityDTO() {
		if (globalEntityDto == null) {
			globalEntityDto = new GlobalEntityDTO();
			globalEntityDto.setGlobalEntityType(globalEntityType);
		}
		return this;
	}
	public GlobalEntityDTOBuilder aGlobalEntityDTO(String globalEntityType) {
		if (globalEntityDto == null) {
			globalEntityDto = new GlobalEntityDTO();
			globalEntityDto.setGlobalEntityType(globalEntityType);
		}
		return this;
	}
	public GlobalEntityDTO build() {
		return globalEntityDto;
	}
}

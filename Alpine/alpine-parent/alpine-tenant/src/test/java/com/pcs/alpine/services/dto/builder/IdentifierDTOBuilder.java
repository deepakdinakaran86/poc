package com.pcs.alpine.services.dto.builder;

import com.pcs.alpine.services.dto.FieldMapDTO;

public class IdentifierDTOBuilder {
	private FieldMapDTO identifier;
	
	private  String key = "orgName";
	private  String value = "JLL";
	
	public IdentifierDTOBuilder anIdentifierDTO() {
		if(identifier==null){
			identifier=new FieldMapDTO();
			identifier.setKey(key);
			identifier.setValue(value);
		}
		return this;
	}

	public IdentifierDTOBuilder anIdentifierDTO(String key, String value) {
		if(identifier==null){
			identifier=new FieldMapDTO();
			identifier.setKey(key);
			identifier.setValue(value);
		}
		return this;
	}
	
	public FieldMapDTO build() {
		return identifier;
	}
}

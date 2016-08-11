package com.pcs.alpine.services.dto.builder;

import com.pcs.alpine.services.dto.EntityRangeDTO;
import com.pcs.alpine.services.dto.IdentityDTO;

public class EntityRangeDTOBuilder {
	
	private EntityRangeDTO entityRangeDTO;
	
	public EntityRangeDTO aEntityRangeDTO(IdentityDTO startEntity, IdentityDTO endEntity) {
		
		if (entityRangeDTO == null) {
			entityRangeDTO = new EntityRangeDTO();
			entityRangeDTO.setStartEntity(startEntity);
			entityRangeDTO.setEndEntity(endEntity);
		}

		return entityRangeDTO;
	}
	
	public EntityRangeDTO build() {
		return entityRangeDTO;
	}

}

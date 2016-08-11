/**
 * Create an EntityDTO  for input payload in test case
 */
package com.pcs.alpine.services.dto.builder;

import java.util.List;

import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.GlobalEntityDTO;
import com.pcs.alpine.services.dto.StatusDTO;

/**
 * @author PCSEG362
 * 
 */
public class EntityDTOBuilder {

	private EntityDTO entityDTO;

	public EntityDTO aEntityDTO(DomainDTO domainDTO,
			EntityTemplateDTO entityTemplateDTO,
			GlobalEntityDTO globalEntityDTO, FieldMapDTO identityMapDTO,
			List<FieldMapDTO> entityDataprovider,
			List<FieldMapDTO> entityFieldValues,
			StatusDTO entityStatus) {

		if (entityDTO == null) {
			entityDTO = new EntityDTO();
			entityDTO.setDomain(domainDTO);
			entityDTO.setEntityTemplate(entityTemplateDTO);
			entityDTO.setGlobalEntity(globalEntityDTO);
			entityDTO.setIdentifier(identityMapDTO);
			entityDTO.setDataprovider(entityDataprovider);
			entityDTO.setFieldValues(entityFieldValues);
			entityDTO.setEntityStatus(entityStatus);
		}

		return entityDTO;
	}

	public EntityDTO build() {
		return entityDTO;
	}

}

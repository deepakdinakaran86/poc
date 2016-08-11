/**
 * 
 */
package com.pcs.alpine.services.dto.builder;

import java.util.List;

import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;

/**
 * @author PCSEG362
 * 
 */
public class HierarchyDTOBuilder {

	private HierarchyDTO hierarchyDTO;

	public HierarchyDTO aHierachyDTO(EntityDTO actor, List<EntityDTO> subjects) {

		if (hierarchyDTO == null) {
			hierarchyDTO = new HierarchyDTO();
			hierarchyDTO.setActor(actor);
			hierarchyDTO.setSubjects(subjects);
		}

		return hierarchyDTO;
	}

	public HierarchyDTO build() {
		return hierarchyDTO;
	}

}

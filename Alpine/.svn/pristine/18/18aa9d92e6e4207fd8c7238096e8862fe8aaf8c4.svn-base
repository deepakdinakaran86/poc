/**
 * 
 */
package com.pcs.alpine.services.dto.builder;

import com.pcs.alpine.services.dto.HierarchySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;

/**
 * @author PCSEG362
 * 
 */
public class HierarchySearchDTOBuilder {

	private HierarchySearchDTO hierarchySearchDTO;

	public HierarchySearchDTO aHierachySearchDTO(IdentityDTO actor,
			String searchTemplateName, String searchEntityType,
			String statusName) {

		if (hierarchySearchDTO == null) {
			hierarchySearchDTO = new HierarchySearchDTO();
			hierarchySearchDTO.setActor(actor);
			hierarchySearchDTO.setSearchTemplateName(searchTemplateName);
			hierarchySearchDTO.setSearchEntityType(searchEntityType);
			hierarchySearchDTO.setStatusName(statusName);
		}

		return hierarchySearchDTO;
	}
	
	public HierarchySearchDTO bHierachySearchDTO(String domain,
			String searchTemplateName, String searchEntityType,
			String statusName) {

		if (hierarchySearchDTO == null) {
			hierarchySearchDTO = new HierarchySearchDTO();
			hierarchySearchDTO.setDomain(domain);
			hierarchySearchDTO.setSearchTemplateName(searchTemplateName);
			hierarchySearchDTO.setSearchEntityType(searchEntityType);
			hierarchySearchDTO.setStatusName(statusName);
		}

		return hierarchySearchDTO;
	}
	
	public HierarchySearchDTO cHierachySearchDTO(IdentityDTO parentIdentity,
			String searchTemplateName, String searchEntityType) {

		if (hierarchySearchDTO == null) {
			hierarchySearchDTO = new HierarchySearchDTO();
			hierarchySearchDTO.setParentIdentity(parentIdentity);
			hierarchySearchDTO.setSearchTemplateName(searchTemplateName);
			hierarchySearchDTO.setSearchEntityType(searchEntityType);
		}

		return hierarchySearchDTO;
	}
	
	public HierarchySearchDTO dHierachySearchDTO(IdentityDTO parentIdentity,
			String searchTemplateName, String searchEntityType, String statusName) {

		if (hierarchySearchDTO == null) {
			hierarchySearchDTO = new HierarchySearchDTO();
			hierarchySearchDTO.setParentIdentity(parentIdentity);
			hierarchySearchDTO.setSearchTemplateName(searchTemplateName);
			hierarchySearchDTO.setSearchEntityType(searchEntityType);
			hierarchySearchDTO.setStatusName(statusName);
		}

		return hierarchySearchDTO;
	}

	public HierarchySearchDTO build() {
		return hierarchySearchDTO;
	}

}

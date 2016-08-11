package com.pcs.alpine.services.dto.builder;

import java.util.List;

import com.pcs.alpine.dto.PermissionGroupsDTO;
import com.pcs.alpine.services.dto.DomainDTO;

public class PermissionGroupsDTOBuilder {

	private PermissionGroupsDTO permissionGroup;

	public PermissionGroupsDTOBuilder aPermissionGroup(String resourceName, List<String> permissions,String domain) {
		if (permissionGroup == null) {
			permissionGroup = new PermissionGroupsDTO();
			permissionGroup.setResourceName(resourceName);
			DomainDTO domainDto = new DomainDTO();
			domainDto.setDomainName(domain);
			permissionGroup.setDomain(domainDto);
			permissionGroup.setPermissions(permissions);
		}
		return this;
	}

	public PermissionGroupsDTO build() {
		return permissionGroup;
	}


	//	public static void main(String[] args) {
	//		System.out.println("********Testing DTO Builder**********");
	//	
	//		System.out.println(new Gson().toJson(new PermissionGroupsDTOBuilder()
	//		.aPermissionGroup().build()));
	//	
	//	}
}

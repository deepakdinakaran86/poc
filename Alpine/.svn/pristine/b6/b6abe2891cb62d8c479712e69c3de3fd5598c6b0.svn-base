package com.pcs.alpine.services.dto.builder;

import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.enums.EMDataFields;

public class DomainDTOBuilder {

	private DomainDTO domainDto;

	private final String domainName = "pcs.com";
	private final String type = EMDataFields.ORGANIZATION.getFieldName();

	public DomainDTOBuilder aDomainDTO() {
		if (domainDto == null) {
			domainDto = new DomainDTO();
			domainDto.setDomainName(domainName);
			domainDto.setType(type);
		}
		return this;
	}

	public DomainDTOBuilder aDomainDTO(String domainName) {
		if (domainDto == null) {
			domainDto = new DomainDTO();
			domainDto.setDomainName(domainName);
			}
		return this;
	}
	public DomainDTO build() {
		return domainDto;
	}
}

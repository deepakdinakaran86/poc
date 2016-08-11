package com.pcs.alpine.services.dto.builder;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.pcs.alpine.services.dto.StatusDTO;

public class StatusDTOBuilder {

	private StatusDTO statusDto;
	private String statusName = "ACTIVE";

	public StatusDTOBuilder aStatusDTO() {
		if (statusDto == null) {
			statusDto = new StatusDTO();
			statusDto.setStatusName(statusName);
		}
		return this;
	}

	public StatusDTOBuilder aStatusDTO(String statusName) {
		if (statusDto == null) {
			statusDto = new StatusDTO();
			statusDto.setStatusName(statusName);
		}
		return this;
	}
	public StatusDTOBuilder withStatus(String statusValue) {
		if (statusDto != null && isNotBlank(statusValue)) {
			statusDto.setStatusName(statusValue);
		}
		return this;
	}

	public StatusDTO build() {
		return statusDto;
	}
}

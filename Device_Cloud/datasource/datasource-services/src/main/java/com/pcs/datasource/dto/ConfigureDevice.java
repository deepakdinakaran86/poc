package com.pcs.datasource.dto;

import java.io.Serializable;
import java.util.Set;

public class ConfigureDevice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7495348839310161669L;

	private DeviceConfigTemplate confTemplate;

	private Set<String> sourceIds;

	public DeviceConfigTemplate getConfTemplate() {
		return confTemplate;
	}

	public void setConfTemplate(DeviceConfigTemplate confTemplate) {
		this.confTemplate = confTemplate;
	}

	public Set<String> getSourceIds() {
		return sourceIds;
	}

	public void setSourceIds(Set<String> sourceIds) {
		this.sourceIds = sourceIds;
	}

	
}

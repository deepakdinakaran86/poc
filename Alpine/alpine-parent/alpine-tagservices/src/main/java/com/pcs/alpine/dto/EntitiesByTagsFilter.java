package com.pcs.alpine.dto;

import java.io.Serializable;
import java.util.List;

public class EntitiesByTagsFilter implements Serializable {

    private static final long serialVersionUID = -6764529651188105693L;

    private List<String> templateNames;

	public List<String> getTemplateNames() {
		return templateNames;
	}

	public void setTemplateNames(List<String> templateNames) {
		this.templateNames = templateNames;
	}
}

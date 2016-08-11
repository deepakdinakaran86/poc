package com.pcs.alpine.commons.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FieldDTO {
	private String name;
	private String description;
	private Map<String, String> validation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getValidation() {
		return validation;
	}

	public void setValidation(Map<String, String> validation) {
		this.validation = validation;
	}

}

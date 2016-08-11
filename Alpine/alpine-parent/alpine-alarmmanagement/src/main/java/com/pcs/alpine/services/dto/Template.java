package com.pcs.alpine.services.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author pcseg369
 *
 */
public class Template {

	private String name;
	
	private String description;
	
	private List<ParameterDTO> parameters = new ArrayList<ParameterDTO>();

	public Template(String name, String description,
			List<ParameterDTO> parameters) {
		super();
		this.name = name;
		this.description = description;
		this.parameters = parameters;
	}

	public Template() {
		super();
	}

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

	public List<ParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterDTO> parameters) {
		this.parameters = parameters;
	}
	
	
}
package com.pcs.alpine.services.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author pcseg369
 *
 */
public class ParameterListDTO {

	private List<ParameterDTO> parameters = new ArrayList<ParameterDTO>();

	public ParameterListDTO() {
		super();
	}

	public ParameterListDTO(List<ParameterDTO> params) {
		super();
		this.parameters = params;
	}

	public List<ParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterDTO> parameters) {
		this.parameters = parameters;
	}

}
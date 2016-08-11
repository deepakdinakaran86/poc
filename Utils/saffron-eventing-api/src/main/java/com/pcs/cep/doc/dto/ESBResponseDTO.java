package com.pcs.cep.doc.dto;

import java.io.Serializable;

public class ESBResponseDTO implements Serializable {

	private static final long serialVersionUID = -1444169176891973470L;

	private String errorMessage;
	
	private String description;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

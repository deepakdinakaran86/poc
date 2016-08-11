package com.pcs.alpine.services.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HierarchyReturnDTO {
	
	private Integer count;
	private String identifierValue;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getIdentifierValue() {
		return identifierValue;
	}

	public void setIdentifierValue(String identifierValue) {
		this.identifierValue = identifierValue;
	}
	

}

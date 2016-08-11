package com.pcs.alpine.services.dto;

/**
 * 
 * @author pcseg369
 *
 */
public class Domain {

	private String name;
	
	private String description;

	public Domain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Domain(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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

}
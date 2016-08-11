package com.pcs.alpine.services.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author pcseg369
 *
 */
public class DomainListDTO {

	private List<Domain> domains = new ArrayList<Domain>();

	public DomainListDTO(List<Domain> domains) {
		super();
		this.domains = domains;
	}

	public DomainListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}
	
}

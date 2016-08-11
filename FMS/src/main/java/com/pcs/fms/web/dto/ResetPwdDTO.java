package com.pcs.fms.web.dto;

import java.io.Serializable;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
public class ResetPwdDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6549593710348606191L;
	private String domainName;
	private String identifier;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}

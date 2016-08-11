/**
 *
 */
package com.pcs.alpine.services.enums;

/**
 * Status
 * 
 * @description Holds the Status information
 * @author Anish Prabhakaran (PCSEG271)
 * @date 24th Aug 2014
 * @since galaxy-1.0.0
 */
public enum Status {

	ACTIVE("active"), INACTIVE("inactive"), DELETED("deleted"), SUCCESS(
	        "success"), FAILURE("failure"), UNALLOCATED("unallocated"),
	DISCONTINUED("discontinued"), PARTIAL("partial"), PARTIALLY_SUCCESS(
	        "PARTIALLY SUCCESS"), FAIL("FAIL"), PENDING("PENDING"), ALL("all");

	private String status;

	Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

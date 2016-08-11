/**
 *
 */
package com.pcs.fms.web.dto;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
public enum Status {

	ACTIVE("active"), INACTIVE("inactive"), DELETED("deleted"), SUCCESS(
	        "success"), FAILURE("failure"), UNALLOCATED("unallocated"),
	DISCONTINUED("discontinued"), PARTIAL("partial"), PARTIALLY_SUCCESS(
	        "PARTIALLY SUCCESS"), FAIL("FAIL"), PENDING("PENDING"), AVAILABLE(
	        "available"), NOT_AVAILABLE("Not Available");

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

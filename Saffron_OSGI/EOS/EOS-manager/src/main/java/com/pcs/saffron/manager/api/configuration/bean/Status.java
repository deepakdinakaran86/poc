/**
 *
 */
package com.pcs.saffron.manager.api.configuration.bean;

/**
 * Status
 * 
 * @description Holds the Status information
 * @author Riyas PH (pcseg296)
 * 
 */
public enum Status {

	ACTIVE("active"), INACTIVE("inactive"), DELETED("deleted"), SUCCESS(
	        "success"), FAILURE("failure"), PENDING("PENDING"), PARTIAL(
	        "Partial"), QUEUED("QUEUED"), TRUE("true"), FALSE("false"),UNSUPPORTED("unsupported"),
	        SUBSCRIBED("subscribed"),UNSUBSCRIBED("unsubscribed"),NOT_AVAIALABLE("Not Available"),FAULTY("Faulty");

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

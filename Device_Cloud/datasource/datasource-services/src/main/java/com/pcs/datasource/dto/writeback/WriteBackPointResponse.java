/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 *
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 *
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.dto.writeback;

import java.io.Serializable;
import java.util.Map;

import com.pcs.devicecloud.enums.Status;

/**
 * This class is responsible for ..(Short Description)
 *
 * @author pcseg199
 * @date May 27, 2015
 * @since galaxy-1.0.0
 */
public class WriteBackPointResponse  implements Serializable {

	private static final long serialVersionUID = 7565375488544958267L;
	private String command;
	private Status status;
	private Short pointId;
	private Short requestId;
	private String value;
	private Long requestedAt;
	private Long updatedAt;
	private String remarks;
	private Map<String,String> customSpecs;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Short getPointId() {
		return pointId;
	}
	public void setPointId(Short pointId) {
		this.pointId = pointId;
	}
	public Short getRequestId() {
		return requestId;
	}
	public void setRequestId(Short requestId) {
		this.requestId = requestId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getRequestedAt() {
		return requestedAt;
	}
	public void setRequestedAt(Long requestedAt) {
		this.requestedAt = requestedAt;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Map<String, String> getCustomSpecs() {
		return customSpecs;
	}
	public void setCustomSpecs(Map<String, String> customSpecs) {
		this.customSpecs = customSpecs;
	}
	


}

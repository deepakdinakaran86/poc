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

import com.pcs.devicecloud.enums.Status;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg296
 */
public class WriteBackCommand implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3342759183314134850L;

	private String sourceId;
	private Short writeBackId;
	private String type;
	private String typeCode;
	private DeviceCommand payload;
	private Status status;
	private String remarks;
	private Long requestedAt;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Short getWriteBackId() {
		return writeBackId;
	}

	public void setWriteBackId(Short writeBackId) {
		this.writeBackId = writeBackId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public DeviceCommand getPayload() {
		return payload;
	}

	public void setPayload(DeviceCommand payload) {
		this.payload = payload;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(Long requestedAt) {
		this.requestedAt = requestedAt;
	}

}

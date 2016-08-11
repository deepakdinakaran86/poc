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
package com.pcs.datasource.dto;

import java.io.Serializable;

import com.pcs.datasource.dto.writeback.DeviceCommand;

/**
 * CommandDTO
 * 
 * @description DTO which Comprises informations for commands
 * 
 * @author Greeshma (pcseg323)
 * @date March 2015
 * @since galaxy-1.0.0
 */
public class WrieBackCommandDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sourceId;
	private Short writeBackId;
	private String type;
	private DeviceCommand payload;
	private ConfigurationSearch version;
	private String status;

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

	public DeviceCommand getPayload() {
		return payload;
	}

	public void setPayload(DeviceCommand payload) {
		this.payload = payload;
	}

	public ConfigurationSearch getVersion() {
		return version;
	}

	public void setVersion(ConfigurationSearch version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
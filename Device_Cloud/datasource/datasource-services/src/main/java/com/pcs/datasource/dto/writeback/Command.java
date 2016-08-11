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
import java.util.HashMap;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for ..(Short Description)
 *
 * @author pcseg199
 * @date May 20, 2015
 * @since galaxy-1.0.0
 */
public class Command implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3342759183314134850L;

	@SerializedName("command")
	private String command;

	@SerializedName("request_id")
	private Short requestId;

	@SerializedName("requested_at")
	private Long requestedAt;

	@SerializedName("value")
	private String value;

	@SerializedName("data_type")
	private String dataType;

	@SerializedName("point_id")
	private Short pointId;

	private Map<String, String> customInfo;

	@SerializedName("custom_info")
	private String customInfoJSON;

	private Map<String, String> customSpecs;

	@SerializedName("custom_specs")
	private String customSpecsJSON;

	@SerializedName("custom_tag")
	private String customTag;

	@SerializedName("status")
	private String status;

	@SerializedName("updated_at")
	private String updatedAt;
	
	private String remarks;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Short getRequestId() {
		return requestId;
	}

	public void setRequestId(Short requestId) {
		this.requestId = requestId;
	}

	public Long getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(Long requestedAt) {
		this.requestedAt = requestedAt;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Short getPointId() {
		return pointId;
	}

	public void setPointId(Short pointId) {
		this.pointId = pointId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Map<String, String> getCustomInfo() {
		return customInfo;
	}

	public void setCustomInfo(Map<String, String> customInfo) {
		this.customInfo = customInfo;
	}

	public String getCustomInfoJSON() {
		return customInfoJSON;
	}

	public void setCustomInfoJSON(String customInfoJSON) {
		this.customInfoJSON = customInfoJSON;

	}

	public Map<String, String> getCustomSpecs() {
		return customSpecs;
	}

	public void setCustomSpecs(Map<String, String> customSpecs) {
		this.customSpecs = customSpecs;
	}

	public String getCustomSpecsJSON() {
		return customSpecsJSON;
	}

	public void setCustomSpecsJSON(String customSpecsJSON) {
		this.customSpecsJSON = customSpecsJSON;
	}

	public String getCustomTag() {
		return customTag;
	}

	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public static void main(String[] args) {
		Command command = new Command();

		Map<String, String> customInfo1 = new HashMap<String, String>();
		customInfo1.put("user", "javid");

		command.setCustomInfo(customInfo1);

		Gson gson = new GsonBuilder().setFieldNamingPolicy(
		        FieldNamingPolicy.UPPER_CAMEL_CASE).create();

		String json = gson.toJson(command);

		System.out.println(json);

	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}

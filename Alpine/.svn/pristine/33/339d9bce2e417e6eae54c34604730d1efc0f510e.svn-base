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
package com.pcs.alpine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Global Entity
 * 
 * @description POJO for entity
 * @author Daniela (pcseg191)
 * @date 11 Aug 2014
 * @since galaxy-1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformEntity {

	@JsonProperty("platform_entity_type")
	private String platformEntityType;

	@JsonProperty("status_key")
	private String statusKey;

	@JsonProperty("is_sharable")
	private Boolean isSharable;

	@JsonProperty("is_default")
	private Boolean isDefault;

	@JsonProperty("description")
	private String description;

	@JsonProperty("type_code")
	private String typeCode;

	@JsonProperty("access_scope")
	private String accessScope;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getPlatformEntityType() {
		return platformEntityType;
	}

	public void setPlatformEntityType(String platformEntityType) {
		this.platformEntityType = platformEntityType;
	}

	public String getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}

	public Boolean getIsSharable() {
		return isSharable;
	}

	public void setIsSharable(Boolean isSharable) {
		this.isSharable = isSharable;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getAccessScope() {
		return accessScope;
	}

	public void setAccessScope(String accessScope) {
		this.accessScope = accessScope;
	}

}

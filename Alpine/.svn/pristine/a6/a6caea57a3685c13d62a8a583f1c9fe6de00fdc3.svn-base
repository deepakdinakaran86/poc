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
package com.pcs.alpine.services.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * EntityTemplateDTO
 *
 * @description DTO for Entity Template
 * @author Daniela (pcseg191)
 * @date 24 Nov 2014
 * @since galaxy-1.0.0
 */
// @JsonInclude(JsonInclude.Include.NON_NULL) //TODO fasterxml
@XmlRootElement(name = "platformEntityTemplate")
public class PlatformEntityTemplateDTO {

	private String platformEntityType;
	private Map<String, String> fieldValidation;
	private String platformEntityTemplateName;
	private String htmlPage;
	private String identifierField;
	private Boolean isModifiable;
	private Boolean isSharable;
	private Integer statusKey;

	public String getPlatformEntityType() {
		return platformEntityType;
	}

	public void setPlatformEntityType(String globalEntityType) {
		this.platformEntityType = globalEntityType;
	}

	public String getPlatformEntityTemplateName() {
		return platformEntityTemplateName;
	}

	public void setPlatformEntityTemplateName(String platformEntityTemplateName) {
		this.platformEntityTemplateName = platformEntityTemplateName;
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public Map<String, String> getFieldValidation() {
		return fieldValidation;
	}

	public void setFieldValidation(Map<String, String> fieldValidation) {
		this.fieldValidation = fieldValidation;
	}

	public String getIdentifierField() {
		return identifierField;
	}

	public void setIdentifierField(String identifierField) {
		this.identifierField = identifierField;
	}

	public Boolean getIsModifiable() {
		return isModifiable;
	}

	public void setIsModifiable(Boolean isModifiable) {
		this.isModifiable = isModifiable;
	}

	public Boolean getIsSharable() {
		return isSharable;
	}

	public void setIsSharable(Boolean isSharable) {
		this.isSharable = isSharable;
	}

	public Integer getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(Integer statusKey) {
		this.statusKey = statusKey;
	}

}

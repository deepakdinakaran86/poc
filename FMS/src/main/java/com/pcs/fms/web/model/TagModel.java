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
package com.pcs.fms.web.model;

import java.io.Serializable;
import java.util.List;

import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.FieldMapDTO;

/**
 * DTO for Tag
 * 
 * @author Twinkle (PCSEG297)
 * @date May 2016
 * @since galaxy-1.0.0
 */
public class TagModel implements Serializable {

	private static final long serialVersionUID = -6764529651188105693L;

	private String tagName;
	private String tagType;
	private DomainDTO domain;
	private String tagFieldValues;
	private List<String> assignToTemplates;
	private String tagId;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}


	public List<String> getAssignToTemplates() {
		return assignToTemplates;
	}

	public void setAssignToTemplates(List<String> assignToTemplates) {
		this.assignToTemplates = assignToTemplates;
	}

	public String getTagFieldValues() {
		return tagFieldValues;
	}

	public void setTagFieldValues(String tagFieldValues) {
		this.tagFieldValues = tagFieldValues;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

}

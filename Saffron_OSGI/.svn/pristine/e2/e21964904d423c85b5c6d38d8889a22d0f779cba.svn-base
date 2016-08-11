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
package com.pcs.galaxy.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date JUNE 2016
 * @since GALAXY-1.0.0
 */
@XmlRootElement
public class TagConfiguration implements Serializable {

	private static final long serialVersionUID = 2811654069853319306L;

	private IdentityDTO tag;
	private List<EntityTemplateDTO> templates;
	private List<EntityDTO> entities;

	private List<IdentityDTO> tags;
	private IdentityDTO entity;

	public IdentityDTO getTag() {
		return tag;
	}

	public void setTag(IdentityDTO tag) {
		this.tag = tag;
	}

	public List<EntityTemplateDTO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<EntityTemplateDTO> templates) {
		this.templates = templates;
	}

	public List<EntityDTO> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityDTO> entities) {
		this.entities = entities;
	}

	public List<IdentityDTO> getTags() {
		return tags;
	}

	public void setTags(List<IdentityDTO> tags) {
		this.tags = tags;
	}

	public IdentityDTO getEntity() {
		return entity;
	}

	public void setEntity(IdentityDTO entity) {
		this.entity = entity;
	}

}
